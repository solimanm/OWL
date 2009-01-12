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
package org.jdesktop.wonderland.modules.orb.client.cell;

import org.jdesktop.wonderland.client.cell.Cell;

import org.jdesktop.wonderland.modules.orb.common.messages.OrbEndCallMessage;
import org.jdesktop.wonderland.modules.orb.common.messages.OrbMuteCallMessage;
import org.jdesktop.wonderland.modules.orb.common.messages.OrbSetVolumeMessage;

import org.jdesktop.wonderland.client.cell.ChannelComponent;

import org.jdesktop.wonderland.client.softphone.SoftphoneControlImpl;

/**
 *
 * @author  jp
 */
public class OrbDialog extends javax.swing.JDialog {

    private OrbCell orbCell;
   
    private ChannelComponent channelComp;

    private OrbMessageHandler orbMessageHandler;

    /** Creates new form OrbDialog */
    public OrbDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public OrbDialog(OrbCell orbCell, ChannelComponent channelComp, 
	    OrbMessageHandler orbMessageHandler) {

	this.orbCell = orbCell;
	this.channelComp = channelComp;
	this.orbMessageHandler = orbMessageHandler;
	
	initComponents();

	setTitle(orbCell.getCellID().toString());
	setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        endCallButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        volumeSlider = new javax.swing.JSlider();
        attachButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        muteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        endCallButton.setText("End Call");
        endCallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endCallButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        volumeSlider.setMajorTickSpacing(1);
        volumeSlider.setMaximum(10);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setValue(4);
        volumeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volumeSliderStateChanged(evt);
            }
        });

        attachButton.setText("Attach");
        attachButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Volume:");

        muteButton.setText("Mute");
        muteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muteButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(volumeSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 287, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(muteButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(30, 30, 30)
                        .add(attachButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(22, 22, 22))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addContainerGap(335, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(endCallButton)
                .addContainerGap(325, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(23, 23, 23)
                        .add(volumeSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(42, 42, 42)
                        .add(jLabel1)))
                .add(33, 33, 33)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(endCallButton)
                    .add(okButton)
                    .add(muteButton)
                    .add(attachButton))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void endCallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endCallButtonActionPerformed
    channelComp.send(new OrbEndCallMessage(orbCell.getCellID()));
    orbCell.removeMouseListener();
}//GEN-LAST:event_endCallButtonActionPerformed

private void attachButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachButtonActionPerformed
    if (attachButton.getText().equals("Attach")) {
        System.out.println("Attach Orb...");
	attachButton.setText("Detach");
    } else {
        System.out.println("Detach Orb...");
	attachButton.setText("Attach");
    }
}//GEN-LAST:event_attachButtonActionPerformed

private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    setVisible(false);
    orbCell.removeMouseListener();
}//GEN-LAST:event_okButtonActionPerformed

private void volumeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volumeSliderStateChanged
    if (volumeSlider.getValueIsAdjusting()) {
        return;
    }
    
    int volume = volumeSlider.getValue();
    
    SoftphoneControlImpl sc = SoftphoneControlImpl.getInstance();

    channelComp.send(new OrbSetVolumeMessage(orbCell.getCellID(), sc.getCallID(), volume));
}//GEN-LAST:event_volumeSliderStateChanged

private void muteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muteButtonActionPerformed
    boolean mute;

    if (muteButton.getText().equals("Mute")) {
	muteButton.setText("Unmute");
	mute = true;
    } else {
	muteButton.setText("Mute");
	mute = false;
    }

    channelComp.send(new OrbMuteCallMessage(orbCell.getCellID(), mute));
}//GEN-LAST:event_muteButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OrbDialog dialog = new OrbDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attachButton;
    private javax.swing.JButton endCallButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton muteButton;
    private javax.swing.JButton okButton;
    private javax.swing.JSlider volumeSlider;
    // End of variables declaration//GEN-END:variables

}
