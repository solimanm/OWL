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
package org.jdesktop.wonderland.modules.appbase.client.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import org.jdesktop.wonderland.client.jme.MainFrame;
import org.jdesktop.wonderland.modules.appbase.client.WindowGraphics2D;
import org.jdesktop.wonderland.modules.appbase.client.DrawingSurface;
import org.jdesktop.wonderland.modules.appbase.client.App;
import org.jdesktop.wonderland.modules.appbase.client.swing.WindowSwingEmbeddedToolkit.WindowSwingEmbeddedPeer;
import com.sun.embeddedswing.EmbeddedPeer;
import java.awt.Point;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jdesktop.wonderland.client.jme.JmeClientMain;
import org.jdesktop.wonderland.common.ExperimentalAPI;

/**
 * A 2D window in which a Swing panel can be displayed. Use <code>setComponent</code> to specify the Swing panel.
 */

// TODO: currently this has JME dependencies. It would be nice to do this in a graphics library independent fashion.
@ExperimentalAPI
public class WindowSwing extends WindowGraphics2D {

    private static final Logger logger = Logger.getLogger(WindowSwing.class.getName());

    /** The Swing component which is displayed in this window */
    protected Component component;
    
    /** The Swing Embedder object */
    protected WindowSwingEmbeddedPeer embeddedPeer;

    /** The size of the window */
    protected Dimension size;

    /** 
     * Create a new instance of WindowSwing.
     * @param app The application to which this window belongs.
     * @param width The window width (in pixels).
     * @param height The window height (in pixels).
     * @param topLevel Whether the window is top-level (e.g is decorated) with a frame.
     * @param pixelScale The size of the window pixels.
     * @param surface The drawing surface on which the creator will draw
     * @throws InstantiationException if the windows world view cannot be created.
     */
    public WindowSwing (App app, int width, int height, boolean topLevel, Vector2f pixelScale) 
	throws InstantiationException 
    {
        super(app, width, height, topLevel, pixelScale, new DrawingSurface(width, height));
	initializeSurface();
    }

    /** Specify the Swing component displayed in this window */
    public void setComponent(Component component) {
        if (this.component == component) {
            return;
        }
        this.component = component;
        if (embeddedPeer != null) {
            embeddedPeer.dispose();
            embeddedPeer = null;
        }
        if (component != null) {
            checkContainer();

	    // TODO: may eventually need this
            //FocusHandler.addNotify(this);
        }

	setSize(size);
        embeddedPeer.repaint();
    }

    /** Returned the Swing component displayed in this window */
    public final Component getComponent() { 
        return component;
    }

    /** First time initialization */
    private void checkContainer() {
        if (component == null) {
            if (embeddedPeer != null) {
                embeddedPeer.dispose();
                embeddedPeer = null;
            }
            return;
        }
	
	// TODO: is this sufficient?
	// Old: JPanel embeddedParent = Main.getLg3dConnector();
	MainFrame frame = JmeClientMain.getFrame();
	System.err.println("Main frame = " + frame);
	JPanel embeddedParent = frame.getMainPanel();
	System.err.println("embeddedPeerParent (JPanel) = " + embeddedParent);
	if (embeddedParent == null) {
	    logger.warning("Embedded parent is null");
	    return;
	}

        if (embeddedParent != null) {
            if (embeddedPeer != null && embeddedPeer.getParentComponent() != embeddedParent) {
                embeddedPeer.dispose();
                embeddedPeer = null;
            }
            if (embeddedPeer == null) {
		WindowSwingEmbeddedToolkit embeddedToolkit = 
		    WindowSwingEmbeddedToolkit.getWindowSwingEmbeddedToolkit();
                embeddedPeer = embeddedToolkit.embed(embeddedParent, component);
                embeddedPeer.setWindowSwing(this);

		// TODO: may eventually need this
                //embeddedPeer.setFocusTraversalPolicy(FocusHandler.getFocusTraversalPolicy());
            }
        }
    }

    public final EmbeddedPeer getEmbeddedPeer () {
	return embeddedPeer;
    }

    public void setSize(int width, int height) {
        setSize(new Dimension(width, height));
    }

    public void setSize(Dimension size) {
	this.size = size;
	/* TODO: Igor says: this is wrong. Can cause infinite recursion
	if (component != null) {
	    component.setSize(size);
	}
	*/
        if (embeddedPeer != null) {
            embeddedPeer.setSize(size);
            embeddedPeer.validate();
        }
    }

    protected void paint(Graphics2D g) {
	/* TODO: obsolete
	if (drawingSurface != null) {
	    drawingSurface.paint(g);
	}
	*/
    }

    /* TODO
    public final Rectangle2D getBounds(AffineTransform transform) {
        checkContainer();
        if (embeddedPeer == null) {
            return new Rectangle2D.Float();
        }
        embeddedPeer.validate();
        Rectangle2D bounds = 
            new Rectangle(0, 0, component.getWidth(), component.getHeight());
        if (transform != null && !transform.isIdentity()) {
            bounds = transform.createTransformedShape(bounds).getBounds2D();
        }
        return bounds;
    }
    */

    /**
     * Transform the given 3D point in local coordinates into the corresponding point
     * in the coordinate space of the embedded Swing component.
     * @param p3f The point to transform.
     * @return the 2D position of p3f in the component, or null if p3f is outside the component
     */
    Point calcPositionInComponent(Vector3f p3f) {
	return calcWorldPositionInPixelCoordinates (p3f);
    }
}
