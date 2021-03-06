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
package org.jdesktop.wonderland.client.jme;

import org.jdesktop.mtgame.EntityComponent;
import org.jdesktop.wonderland.client.cell.Cell;

/**
 * An Entity component that provides a reference to the cell 
 * that created the entity
 * 
 * @author paulby
 */
public class CellRefComponent extends EntityComponent {

    private Cell cell;
    
    public CellRefComponent(Cell cell) {
        this.cell = cell;
    }
    
    /**
     * Get the cell referenced by this component
     */ 
    public Cell getCell() {
        return cell;
    }
}
