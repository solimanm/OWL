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
package org.jdesktop.wonderland.client.jme;

import org.jdesktop.mtgame.Entity;
import com.jme.scene.CameraNode;
import com.jme.scene.Node;
import org.jdesktop.mtgame.AWTInputComponent;
import org.jdesktop.mtgame.CameraComponent;
import org.jdesktop.mtgame.InputManager;
import org.jdesktop.mtgame.ProcessorComponent;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.Cell.RendererType;
import org.jdesktop.wonderland.client.cell.TransformChangeListener;
import org.jdesktop.wonderland.client.jme.cellrenderer.CellRendererJME;
import org.jdesktop.wonderland.client.jme.input.InputManager3D;
import org.jdesktop.wonderland.common.ExperimentalAPI;

/**
 * 
 * Manages the view into the 3D world. The JME Camera is internal to this
 * class, it is associated with a Cell and a CameraProcessor is defined which
 * specified how the camera tracks the cell. For example ThirdPersonCameraProcessor
 * makes the camera follow the origin of the cell from behind and above the origin
 * giving a third person view.
 * 
 * TODO currently the camera processor is hardcoded to ThirdPerson
 *
 * @author paulby
 */
@ExperimentalAPI
public class ViewManager {
    
    private static ViewManager viewManager=null;

    private CameraNode cameraNode;
    private CameraProcessor cameraProcessor = null;
    private CameraComponent cameraComponent = null;
            
    /**
     * The width and height of our 3D window
     */
    private int width = 800;
    private int height = 600;
    private float aspect = 800.0f/600.0f;
    
    private Cell attachCell = null;
    private CellListener listener = null;
    private SimpleAvatarControls eventProcessor = null;
    
    ViewManager() {  
        createCameraEntity(JmeClientMain.getWorldManager());
        listener = new CellListener();
    }
    
    public static ViewManager getViewManager() {
        if (viewManager==null)
            viewManager = new ViewManager();
        
        return viewManager;
    }
    
    /**
     * Attach the view to the specified cell
     * @param cell
     */
    public void attach(Cell cell) {
        if (attachCell!=null) {
            System.err.println("VIEW ALREADY ATTACHED TO CELL (BUT CONTINUE ANYWAY)");
            return;
//            throw new RuntimeException("View already attached to cell");
        }
        
        Entity entity = ((CellRendererJME)cell.getCellRenderer(RendererType.RENDERER_JME)).getEntity();

        if (eventProcessor==null) {
            // Create the input listener and process to control the avatar
            WorldManager wm = JmeClientMain.getWorldManager();
//            AWTInputComponent eventListener = (AWTInputComponent) wm.getInputManager().createInputComponent(InputManager.MOUSE_EVENTS | InputManager.KEY_EVENTS);
            eventProcessor = new SimpleAvatarControls(cell, wm);
            eventProcessor.setRunInRenderer(true);

            // Chaining the camera here does not seem to work...
            eventProcessor.addToChain(cameraProcessor);
        }

        // Set initial camera position
        cameraProcessor.viewMoved(cell.getWorldTransform());
        
        entity.addComponent(ProcessorComponent.class, eventProcessor);
        attachCell = cell;
        attachCell.addTransformChangeListener(listener);
    }
    
    /**
     * Detach the view from the cell it's currently attached to.
     */
    public void detach() {
        if (attachCell!=null)
            throw new RuntimeException("View not attached to cell");
        
        Entity entity = ((CellRendererJME)attachCell.getCellRenderer(RendererType.RENDERER_JME)).getEntity();
        entity.removeComponent(SimpleAvatarControls.class);
        
        attachCell.removeTransformChangeListener(listener);
        attachCell = null;
    }
    
    /**
     * TODO
     * 
     * Set the processor used to position the camera. Examples would be
     * First and Third person.
     */
    public void setCameraProcessor(CameraProcessor cameraProcessor) {
        throw new RuntimeException("Not Implemented yet");
    }
    
    protected void createCameraEntity(WorldManager wm) {
        Node cameraSG = createCameraGraph(wm);
        
        // Add the camera
        Entity camera = new Entity("DefaultCamera");
        cameraComponent = wm.getRenderManager().createCameraComponent(cameraSG, cameraNode, width, height, 45.0f, aspect, 1.0f, 1000.0f, true);
        cameraComponent.setCameraSceneGraph(cameraSG);
        cameraComponent.setCameraNode(cameraNode);
        camera.addComponent(CameraComponent.class, cameraComponent);
        
        cameraProcessor = new ThirdPersonCameraProcessor(cameraNode, wm);
        camera.addComponent(ProcessorComponent.class, cameraProcessor);
        
        wm.addEntity(camera);         
    }
    
    /**
     * Return the CameraComponent. This is an internal api.
     * 
     * @return
     * @InternalAPI
     */
    CameraComponent getCameraComponent() {
        return cameraComponent;
    }
    
    private Node createCameraGraph(WorldManager wm) {
        Node cameraSG = new Node("MyCamera SG");        
        cameraNode = new CameraNode("MyCamera", null);
        cameraSG.attachChild(cameraNode);
        
        return (cameraSG);
    }

    /**
     * Listen for movement of the view cell
     */
    class CellListener implements TransformChangeListener {

        public void transformChanged(Cell cell) {
            cameraProcessor.viewMoved(cell.getWorldTransform());
        }
        
    }
}
