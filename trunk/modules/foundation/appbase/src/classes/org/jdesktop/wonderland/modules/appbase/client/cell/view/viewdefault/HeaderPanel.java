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
package org.jdesktop.wonderland.modules.appbase.client.cell.view.viewdefault;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author dj
 */
public class HeaderPanel extends javax.swing.JPanel {

    public interface Container {
        // TODO: add call backs to FrameHeaderSwing
        public void close ();
    }

    private Container container;

    public void setContainer (Container container) {
        this.container = container;
    }

    /** Creates new form HeaderPanel */
    public HeaderPanel() {
        initComponents();

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(32767, 29));
        setMinimumSize(new java.awt.Dimension(50, 29));

        jLabel1.setText("Window Title");

        jLabel2.setText("Controller");

        jButton1.setFont(new java.awt.Font("DejaVu Sans", 0, 10));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/jdesktop/wonderland/modules/appbase/client/cell/view/viewdefault/resources/window-close.png"))); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(22, 22, 22)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 202, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .add(36, 36, 36)
                .add(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel2)
                        .add(jLabel1)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    public void setBackground (Color color) {
        super.setBackground(color);
        if (jLabel1 != null) {
            jLabel1.setBackground(color);
        }
        if (jLabel2 != null) {
            jLabel2.setBackground(color);
        }
        if (jButton1 != null) {
            jButton1.setBackground(color);
        }
    }

    @Override
    public void addMouseListener (MouseListener listener) {
        super.addMouseListener(listener);
        if (jLabel1 != null) {
            jLabel1.addMouseListener(listener);
        }
        if (jLabel2 != null) {
            jLabel2.addMouseListener(listener);
        }
        if (jButton1 != null) {
            jButton1.addMouseListener(listener);
        }
    }

    @Override
    public void removeMouseListener (MouseListener listener) {
        super.removeMouseListener(listener);
        if (jLabel1 != null) {
            jLabel1.removeMouseListener(listener);
        }
        if (jLabel2 != null) {
            jLabel2.removeMouseListener(listener);
        }
        if (jButton1 != null) {
            jButton1.removeMouseListener(listener);
        }

    }

    @Override
    public void addMouseMotionListener (MouseMotionListener listener) {
        super.addMouseMotionListener(listener);
        if (jLabel1 != null) {
            jLabel1.addMouseMotionListener(listener);
        }
        if (jLabel2 != null) {
            jLabel2.addMouseMotionListener(listener);
        }
        if (jButton1 != null) {
            jButton1.addMouseMotionListener(listener);
        }
    }

    @Override
    public void removeMouseMotionListener (MouseMotionListener listener) {
        super.removeMouseMotionListener(listener);
        if (jLabel1 != null) {
            jLabel1.removeMouseMotionListener(listener);
        }
        if (jLabel2 != null) {
            jLabel2.removeMouseMotionListener(listener);
        }
        if (jButton1 != null) {
            jButton1.removeMouseMotionListener(listener);
        }
    }

    public void setTitle (String title) {
        if (title == null) {
            title = " ";
        }
        if (jLabel1 != null) {
            jLabel1.setText(title);
        }
    }

    public void setController (String controller) {
        if (controller == null) {
            controller = " ";
        }
        if (jLabel1 != null) {
            jLabel2.setText(controller);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (container != null) {
            container.close();
        }
    }

}
        
