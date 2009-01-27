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
package org.jdesktop.wonderland.modules.microphone.common;

import org.jdesktop.wonderland.common.cell.state.CellClientState;

/**
 * The MicrophoneCellSetup class is the cell that renders a microphone cell in
 * world.
 * 
 * @author jkaplan
 */
public class MicrophoneCellClientState extends CellClientState {

    private String name;

    private double fullVolumeRadius;

    private double activeRadius;
    private String activeRadiusType;

    /** Default constructor */
    public MicrophoneCellClientState() {
    }
    
    public MicrophoneCellClientState(String name, double fullVolumeRadius,
	    double activeRadius, String activeRadiusType) {

	this.name = name;
	this.fullVolumeRadius = fullVolumeRadius;
	this.activeRadius = activeRadius;
	this. activeRadiusType = activeRadiusType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFullVolumeRadius(double fullVolumeRadius) {
        this.fullVolumeRadius = fullVolumeRadius;
    }

    public double getFullVolumeRadius() {
        return fullVolumeRadius;
    }

    public void setActiveRadius(double activeRadius) {
	this.activeRadius = activeRadius;
    }

    public double getActiveRadius() {
	return activeRadius;
    }

    public void setActiveRadiusType(String activeRadiusType) {
	this.activeRadiusType = activeRadiusType;
    }

    public String getActiveRadiusType() {
	return activeRadiusType;
    }

}
