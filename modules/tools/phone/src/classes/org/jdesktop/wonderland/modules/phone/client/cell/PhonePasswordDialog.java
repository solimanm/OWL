/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., All Rights Reserved
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
package org.jdesktop.wonderland.modules.phone.client.cell;

import java.util.ResourceBundle;
import org.jdesktop.wonderland.common.cell.CellID;

import org.jdesktop.wonderland.client.cell.ChannelComponent;

import org.jdesktop.wonderland.modules.phone.common.messages.LockUnlockMessage;

import javax.swing.JDialog;

/**
 *
 * @author  jp
 */
public class PhonePasswordDialog extends JDialog {

    private CellID phoneCellID;  
    private ChannelComponent channelComp;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/phone/client/cell/resources/Bundle");
    
    public PhonePasswordDialog(JDialog parent, CellID phoneCellID, ChannelComponent channelComp) {
        super(parent, false);

	this.phoneCellID = phoneCellID;
	this.channelComp = channelComp;

        initComponents();
        getRootPane().setDefaultButton(phonePasswordOkButton);

        invalidPasswordLabel.setVisible(false);
    }

    public void setLocked(final boolean locked) {
	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                invalidPasswordLabel.setVisible(false);
                keepUnlockedCheckBox.setEnabled(locked);
                keepUnlockedLabel.setEnabled(keepUnlockedCheckBox.isSelected());
                if (locked) {
                    phonePasswordOkButton.setText(BUNDLE.getString("Unlock"));
                    dialogLabel.setText(
                            BUNDLE.getString("Enter_Unlock_Password"));
                } else {
                    phonePasswordOkButton.setText(BUNDLE.getString("Lock"));
                    dialogLabel.setText(
                            BUNDLE.getString("Enter_Lock_Password"));
                }
            }
	});
    }

    public boolean getKeepUnlocked() {
        return keepUnlockedCheckBox.isSelected();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passwordLabel = new javax.swing.JLabel();
        phonePasswordOkButton = new javax.swing.JButton();
        phonePasswordCancelButton = new javax.swing.JButton();
        keepUnlockedCheckBox = new javax.swing.JCheckBox();
        phonePasswordText = new javax.swing.JPasswordField();
        invalidPasswordLabel = new javax.swing.JLabel();
        dialogLabel = new javax.swing.JLabel();
        keepUnlockedLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/phone/client/cell/resources/Bundle"); // NOI18N
        setTitle(bundle.getString("PhonePasswordDialog.title")); // NOI18N
        setResizable(false);

        passwordLabel.setFont(new java.awt.Font("Dialog", 0, 13));
        passwordLabel.setText(bundle.getString("PhonePasswordDialog.passwordLabel.text")); // NOI18N

        phonePasswordOkButton.setFont(new java.awt.Font("Dialog", 0, 13));
        phonePasswordOkButton.setText(bundle.getString("PhonePasswordDialog.phonePasswordOkButton.text")); // NOI18N
        phonePasswordOkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phonePasswordOkButtonActionPerformed(evt);
            }
        });

        phonePasswordCancelButton.setFont(new java.awt.Font("Dialog", 0, 13));
        phonePasswordCancelButton.setText(bundle.getString("PhonePasswordDialog.phonePasswordCancelButton.text")); // NOI18N
        phonePasswordCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phonePasswordCancelButtonActionPerformed(evt);
            }
        });

        keepUnlockedCheckBox.setFont(new java.awt.Font("Dialog", 0, 13));
        keepUnlockedCheckBox.setText(bundle.getString("PhonePasswordDialog.keepUnlockedCheckBox.text")); // NOI18N
        keepUnlockedCheckBox.setEnabled(false);
        keepUnlockedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keepUnlockedCheckBoxActionPerformed(evt);
            }
        });

        phonePasswordText.setFont(new java.awt.Font("Dialog", 0, 13));
        phonePasswordText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phonePasswordTextActionPerformed(evt);
            }
        });
        phonePasswordText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                phonePasswordTextKeyTyped(evt);
            }
        });

        invalidPasswordLabel.setFont(new java.awt.Font("Dialog", 0, 13));
        invalidPasswordLabel.setForeground(new java.awt.Color(255, 0, 0));
        invalidPasswordLabel.setText(bundle.getString("PhonePasswordDialog.invalidPasswordLabel.text")); // NOI18N

        dialogLabel.setFont(new java.awt.Font("Dialog", 0, 13));
        dialogLabel.setText(bundle.getString("PhonePasswordDialog.dialogLabel.text")); // NOI18N

        keepUnlockedLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        keepUnlockedLabel.setText(bundle.getString("PhonePasswordDialog.keepUnlockedLabel.text")); // NOI18N
        keepUnlockedLabel.setEnabled(false);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(dialogLabel)
                    .add(layout.createSequentialGroup()
                        .add(passwordLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(keepUnlockedCheckBox)
                            .add(layout.createSequentialGroup()
                                .add(phonePasswordText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(invalidPasswordLabel)))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(153, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(17, 17, 17)
                        .add(phonePasswordOkButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(phonePasswordCancelButton))
                    .add(keepUnlockedLabel))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {phonePasswordCancelButton, phonePasswordOkButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(dialogLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(passwordLabel)
                    .add(phonePasswordText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(invalidPasswordLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(keepUnlockedCheckBox)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(keepUnlockedLabel)
                .add(15, 15, 15)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phonePasswordOkButton)
                    .add(phonePasswordCancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void phonePasswordOkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phonePasswordOkButtonActionPerformed

        String s = phonePasswordOkButton.getText();

	boolean lock = false;

	if (s.equals(BUNDLE.getString("Lock"))) {
	    lock = true;
	}

        LockUnlockMessage msg = new LockUnlockMessage(phoneCellID, phonePasswordText.getText(),
            lock, keepUnlockedCheckBox.isSelected());

        channelComp.send(msg);
    }//GEN-LAST:event_phonePasswordOkButtonActionPerformed

    private void phonePasswordCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phonePasswordCancelButtonActionPerformed
        password = null;
        setVisible(false);
    }//GEN-LAST:event_phonePasswordCancelButtonActionPerformed

    public void invalidPassword() {
	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        	invalidPasswordLabel.setVisible(true);
	    }
	});
    }

    private void keepUnlockedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keepUnlockedCheckBoxActionPerformed
        keepUnlockedLabel.setEnabled(keepUnlockedCheckBox.isSelected());
    }//GEN-LAST:event_keepUnlockedCheckBoxActionPerformed

    private void phonePasswordTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phonePasswordTextKeyTyped
        password = new String(phonePasswordText.getPassword());

        if (password != null && password.length() > 0) {
            phonePasswordOkButton.setEnabled(true);
            phonePasswordOkButton.setSelected(true);
            getRootPane().setDefaultButton(phonePasswordOkButton);
        } else {
            phonePasswordOkButton.setEnabled(false);
            invalidPasswordLabel.setVisible(false);
        }
    }//GEN-LAST:event_phonePasswordTextKeyTyped

private void phonePasswordTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phonePasswordTextActionPerformed
    phonePasswordOkButton.doClick();
}//GEN-LAST:event_phonePasswordTextActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dialogLabel;
    private javax.swing.JLabel invalidPasswordLabel;
    private javax.swing.JCheckBox keepUnlockedCheckBox;
    private javax.swing.JLabel keepUnlockedLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton phonePasswordCancelButton;
    private javax.swing.JButton phonePasswordOkButton;
    private javax.swing.JPasswordField phonePasswordText;
    // End of variables declaration//GEN-END:variables
    String password;
}
