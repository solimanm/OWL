/*
 * InCallDialog.java
 *
 * Created on April 20, 2009, 2:19 PM
 */

package org.jdesktop.wonderland.modules.audiomanager.client;

import org.jdesktop.wonderland.client.ClientContext;

import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;

import org.jdesktop.wonderland.common.auth.WonderlandIdentity;

import org.jdesktop.wonderland.common.cell.CellID;

import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatBusyMessage;
import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatInfoRequestMessage;
import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatHoldMessage;
import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatJoinMessage;
import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatJoinAcceptedMessage;
import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatLeaveMessage;
import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatMessage;
import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatMessage.ChatType;

import org.jdesktop.wonderland.modules.presencemanager.client.PresenceManager;
import org.jdesktop.wonderland.modules.presencemanager.client.PresenceManagerFactory;
import org.jdesktop.wonderland.modules.presencemanager.client.PresenceManagerListener;
import org.jdesktop.wonderland.modules.presencemanager.client.PresenceManagerListener.ChangeType;
import org.jdesktop.wonderland.modules.presencemanager.common.PresenceInfo;

import org.jdesktop.wonderland.modules.avatarbase.client.jme.cellrenderer.NameTagNode;
import org.jdesktop.wonderland.modules.avatarbase.client.jme.cellrenderer.NameTagNode.EventType;

import org.jdesktop.wonderland.client.comms.WonderlandSession;

import java.io.IOException;

import java.util.ArrayList;

import java.util.concurrent.ConcurrentHashMap;

import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import java.awt.Point;

/**
 *
 * @author  jp
 */
public class InCallDialog extends javax.swing.JFrame implements KeypadListener, 
	PresenceManagerListener {

    private static final Logger logger =
        Logger.getLogger(InCallDialog.class.getName());

    private AudioManagerClient client;
    private WonderlandSession session;
    private CellID cellID;

    private String group;

    private PresenceInfo presenceInfo;

    /** Creates new form InCallDialog */
    public InCallDialog() {
        initComponents();
    }

    public InCallDialog(AudioManagerClient client, WonderlandSession session, 
	    CellID cellID, String group, ChatType chatType) {

        this.client = client;
        this.session = session;
	this.cellID = cellID;
	this.group = group;
	
	initComponents();

	if (chatType == ChatType.SECRET) {
	    secretRadioButton.setSelected(true);
	} else if (chatType == ChatType.PRIVATE) {
	    privateRadioButton.setSelected(true);
	} else if (chatType == ChatType.PUBLIC) {
	    publicRadioButton.setSelected(true);
	}

        PresenceManager pm = PresenceManagerFactory.getPresenceManager(session);

        pm.addPresenceManagerListener(this);

	presenceInfo = pm.getPresenceInfo(cellID);

	memberList.setListData(new String[0]);

	client.addInCallDialog(group, this);
	session.send(client, new VoiceChatInfoRequestMessage(group));

	setVisible(true);
    }

    private ArrayList<MemberChangeListener> listeners = new ArrayList();

    public void addMemberChangeListener(MemberChangeListener listener) {
	synchronized (listeners) {
	   listeners.add(listener);
	}
    }

    public void removeMemberChangeListener(MemberChangeListener listener) {
	synchronized (listeners) {
	   listeners.remove(listener);
	}
    }

    private ArrayList<PresenceInfo> members = new ArrayList();

    public ArrayList<PresenceInfo> getMembers() {
	return members;
    }

    public void setMembers(PresenceInfo[] memberList) {
	synchronized (members) {
	    members.clear();
	
	    for (int i = 0; i < memberList.length; i++) {
		members.add(memberList[i]);
	    }
	}

	setMemberList();
    }

    private void setMemberList() {
	ArrayList<String> memberList = new ArrayList();

	synchronized (this) {
	    for (PresenceInfo member : members) {
	        memberList.add(NameTagNode.getDisplayName(
		    member.usernameAlias, member.isSpeaking, member.isMuted));
	    }

	    this.memberList.setListData(memberList.toArray(new String[0]));
	}
    }

    public void addMember(PresenceInfo member) {
	synchronized (members) {
	    if (members.contains(member)) {
	        return;
	    }

	    members.add(member);
	    setMemberList();

	    for (MemberChangeListener listener : listeners) {
		listener.memberAdded(member);
	    }
	}
    }
    
    public void removeMember(PresenceInfo member) {
	synchronized (members) {
	    members.remove(member.usernameAlias);

	    setMemberList();

	    for (MemberChangeListener listener : listeners) {
		listener.memberRemoved(member);
	    }
	}
    }

    public void presenceInfoChanged(PresenceInfo info, ChangeType type) {
	setPresenceInfo(info);
        setMemberList();
    }

    public void aliasChanged(String previousAlias, PresenceInfo info) {
	setPresenceInfo(info);
        setMemberList();
    }

    private void setPresenceInfo(PresenceInfo info) {
	if (members.contains(info)) {
	    members.remove(info);
	    members.add(info);
	} 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        memberList = new javax.swing.JList();
        keyPadButton = new javax.swing.JButton();
        addUserButton = new javax.swing.JButton();
        holdButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        secretRadioButton = new javax.swing.JRadioButton();
        privateRadioButton = new javax.swing.JRadioButton();
        publicRadioButton = new javax.swing.JRadioButton();
        endCallButton = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 15));
        jLabel1.setText("In Call");

        memberList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(memberList);

        keyPadButton.setText("KeyPad");
        keyPadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyPadButtonActionPerformed(evt);
            }
        });

        addUserButton.setText("Add User...");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        holdButton.setText("Hold");
        holdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Privacy:");

        buttonGroup1.add(secretRadioButton);
        secretRadioButton.setText("Secret");
        secretRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secretRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(privateRadioButton);
        privateRadioButton.setText("Private");
        privateRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                privateRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(publicRadioButton);
        publicRadioButton.setText("Public");
        publicRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publicRadioButtonActionPerformed(evt);
            }
        });

        endCallButton.setText("End Call");
        endCallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endCallButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(157, 157, 157))
            .add(layout.createSequentialGroup()
                .add(46, 46, 46)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(secretRadioButton))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, holdButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, keyPadButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                        .add(6, 6, 6)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(layout.createSequentialGroup()
                                .add(privateRadioButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(publicRadioButton))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, addUserButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, endCallButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(26, 26, 26)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(secretRadioButton)
                    .add(privateRadioButton)
                    .add(publicRadioButton))
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(keyPadButton))
                    .add(layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(addUserButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 25, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(holdButton)
                    .add(endCallButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private KeypadDialog keypad;

private void keyPadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyPadButtonActionPerformed
    if (keypad == null) {
	keypad = new KeypadDialog(this);
	keypad.setListener(this);
	if (addUserDialog == null) {
            keypad.setLocation(new Point((int) getLocation().getX() + getWidth(), (int) getLocation().getY()));
	} else {
            keypad.setLocation(new Point((int) addUserDialog.getLocation().getX() + addUserDialog.getWidth(), 
		(int) addUserDialog.getLocation().getY()));
	}
    }

    keypad.setVisible(true);
}//GEN-LAST:event_keyPadButtonActionPerformed

public void keypadPressed(char key) {
    System.out.println("Got key " + key);
}

private AddUserDialog addUserDialog;

private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
    if (addUserDialog == null) {
	addUserDialog = new AddUserDialog(client, session, cellID, group, this);

	if (keypad == null) {
	    addUserDialog.setLocation(new Point((int) getLocation().getX() + getWidth(), (int) getLocation().getY()));
	} else {
	    addUserDialog.setLocation(new Point((int) keypad.getLocation().getX() + keypad.getWidth(), 
		(int) keypad.getLocation().getY()));
	}
    }

    addUserDialog.setVisible(true);
}//GEN-LAST:event_addUserButtonActionPerformed

private HoldDialog holdDialog;

private void holdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holdButtonActionPerformed
    if (holdDialog == null) {
	holdDialog = new HoldDialog(client, session, group, this);
	Point location = new Point((int) getLocation().getX(), 
	    (int) (getLocation().getY() + getHeight() - holdDialog.getHeight()));
	holdDialog.setLocation(location);
    }
    holdDialog.setVisible(true);
    setVisible(false);
    setHold(true);
}//GEN-LAST:event_holdButtonActionPerformed

public void setHold(boolean onHold) {
    session.send(client, new VoiceChatHoldMessage(group, presenceInfo, onHold));
}

private void endCallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endCallButtonActionPerformed
    endCall();
}//GEN-LAST:event_endCallButtonActionPerformed

private void endCall() {
    client.removeInCallDialog(group);
    session.send(client, new VoiceChatLeaveMessage(group, presenceInfo));

    if (addUserDialog != null) {
	addUserDialog.setVisible(false);
    }

    if (keypad != null) {
	keypad.setVisible(false);
    }

    setVisible(false);
}

private void secretRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secretRadioButtonActionPerformed
    session.send(client, new VoiceChatJoinMessage(group, presenceInfo, new PresenceInfo[0], ChatType.SECRET));
}//GEN-LAST:event_secretRadioButtonActionPerformed

private void privateRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privateRadioButtonActionPerformed
    session.send(client, new VoiceChatJoinMessage(group, presenceInfo, new PresenceInfo[0], ChatType.PRIVATE));
}//GEN-LAST:event_privateRadioButtonActionPerformed

private void publicRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publicRadioButtonActionPerformed
    session.send(client, new VoiceChatJoinMessage(group, presenceInfo, new PresenceInfo[0], ChatType.PUBLIC));
}//GEN-LAST:event_publicRadioButtonActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    endCall();
}//GEN-LAST:event_formWindowClosing

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InCallDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUserButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton endCallButton;
    private javax.swing.JButton holdButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keyPadButton;
    private javax.swing.JList memberList;
    private javax.swing.JRadioButton privateRadioButton;
    private javax.swing.JRadioButton publicRadioButton;
    private javax.swing.JRadioButton secretRadioButton;
    // End of variables declaration//GEN-END:variables

}
