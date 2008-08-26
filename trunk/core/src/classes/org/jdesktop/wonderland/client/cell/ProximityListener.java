/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2008, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * $State$
 */
package org.jdesktop.wonderland.client.cell;

import com.jme.bounding.BoundingVolume;

/**
 * Listener for the view entering/exiting the proximity bounds of a cell.
 * Enter/Exit is triggered when the origin of the ViewCell enters/exits the
 * bounds of a cell.
 * 
 * @author paulby
 */
public interface ProximityListener {

    /**
     * The origin of the view cell for this client has entered the cells bounds
     * @param entered true if this is an enter event, false if its exit
     * @param cell the cell associated with the proximity listener
     * @param proximityVolume the bounding volume entered/exited
     * @param proximityIndex the index of the bounding volume in the ProximityComponent
     */
    public void viewEnterExit(boolean entered, Cell cell, BoundingVolume proximityVolume, int proximityIndex);
    
}
