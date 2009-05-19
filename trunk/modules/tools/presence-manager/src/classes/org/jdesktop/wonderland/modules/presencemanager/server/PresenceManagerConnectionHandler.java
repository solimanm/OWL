/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath" 
 * exception as provided by Sun in the License file that accompanied 
 * this code.
 */
package org.jdesktop.wonderland.modules.presencemanager.server;

import org.jdesktop.wonderland.common.messages.Message;

import org.jdesktop.wonderland.modules.presencemanager.common.PresenceInfo;

import org.jdesktop.wonderland.modules.presencemanager.common.PresenceManagerConnectionType;

import org.jdesktop.wonderland.modules.presencemanager.common.messages.PresenceInfoAddedMessage;
import org.jdesktop.wonderland.modules.presencemanager.common.messages.PresenceInfoRemovedMessage;
import org.jdesktop.wonderland.modules.presencemanager.common.messages.ClientConnectMessage;
import org.jdesktop.wonderland.modules.presencemanager.common.messages.ClientConnectResponseMessage;

import org.jdesktop.wonderland.common.comms.ConnectionType;
import org.jdesktop.wonderland.server.comms.ClientConnectionHandler;
import org.jdesktop.wonderland.server.comms.CommsManager;
import org.jdesktop.wonderland.server.comms.CommsManagerFactory;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;
import org.jdesktop.wonderland.server.comms.WonderlandClientSender;

import org.jdesktop.wonderland.common.auth.WonderlandIdentity;

import java.math.BigInteger;

import java.util.logging.Logger;

import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import java.io.IOException;
import java.io.Serializable;

import com.sun.sgs.app.ManagedObject;

/**
 * Presence Manager
 * 
 * @author jprovino
 */
public class PresenceManagerConnectionHandler 
        implements ClientConnectionHandler, Serializable, ManagedObject {

    private static final Logger logger =
            Logger.getLogger(PresenceManagerConnectionHandler.class.getName());
    
    private ConcurrentHashMap<BigInteger, ArrayList<PresenceInfo>> sessions = 
    	new ConcurrentHashMap();

    public PresenceManagerConnectionHandler() {
        super();
    }

    public ConnectionType getConnectionType() {
        return PresenceManagerConnectionType.CONNECTION_TYPE;
    }

    public void registered(WonderlandClientSender sender) {
	logger.fine("Presence Server manager connection registered");
    }

    public void clientConnected(WonderlandClientSender sender, 
	    WonderlandClientID clientID, Properties properties) {

	logger.fine("client connected...");
    }

    public void messageReceived(WonderlandClientSender sender, 
	    WonderlandClientID clientID, Message message) {

	if (message instanceof ClientConnectMessage) {
	    ClientConnectMessage msg = (ClientConnectMessage) message;

	    ArrayList<PresenceInfo> presenceInfoList = new ArrayList();

	    presenceInfoList.add(msg.getPresenceInfo());

	    sessions.put(clientID.getID(), presenceInfoList);

	    sendPresenceInfo(sender, clientID, msg.isConnected());
	    return;
	}

	if (message instanceof PresenceInfoAddedMessage) {
	    PresenceInfo presenceInfo = ((PresenceInfoAddedMessage) message).getPresenceInfo();

	    ArrayList<PresenceInfo> presenceInfoList = sessions.get(clientID.getID());

	    presenceInfoList.add(presenceInfo);

	    sender.send(message);
	    return;
	} 

	if (message instanceof PresenceInfoRemovedMessage) {
	    PresenceInfo presenceInfo = ((PresenceInfoRemovedMessage) message).getPresenceInfo();

	    ArrayList<PresenceInfo> presenceInfoList = sessions.get(clientID.getID());

	    presenceInfoList.remove(presenceInfo);

	    sender.send(message);
	    return;
	}

        throw new UnsupportedOperationException("Unknown message: " + message);
    }

    public void clientDisconnected(WonderlandClientSender sender, WonderlandClientID clientID) {
	ArrayList<PresenceInfo> presenceInfoArrayList = sessions.get(clientID.getID());

	PresenceInfo[] presenceInfoArray = presenceInfoArrayList.toArray(new PresenceInfo[0]);

	for (int i = 0; i < presenceInfoArray.length; i++) {
	    PresenceInfo info = presenceInfoArray[i];

	    if (info.clientID != null && info.clientID.equals(clientID.getID())) {
		logger.info("Client disconnected.  Removing presence info for " + info);
	        presenceInfoArrayList.remove(info);
		sender.send(new PresenceInfoRemovedMessage(info));
	    }
	}
    }

    private void sendPresenceInfo(WonderlandClientSender sender, WonderlandClientID clientID, boolean isConnected) {
	/*
         * Send back all of the PresenceInfo data to the new client
         */
        Iterator<BigInteger> it = sessions.keySet().iterator();

        while (it.hasNext()) {
            BigInteger id = it.next();

            if (clientID.getID().equals(id)) {
                continue;
            }

            ArrayList<PresenceInfo> presenceInfoList = sessions.get(id);

            sender.send(clientID, new ClientConnectResponseMessage(presenceInfoList, isConnected));
        }
    }

}
