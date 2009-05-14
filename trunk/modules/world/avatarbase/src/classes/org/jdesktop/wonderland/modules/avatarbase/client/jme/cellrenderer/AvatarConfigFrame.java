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
package org.jdesktop.wonderland.modules.avatarbase.client.jme.cellrenderer;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.resource.ResourceLocator;
import com.jme.util.resource.ResourceLocatorTool;
import imi.character.CharacterAttributes;
import imi.character.avatar.FemaleAvatarAttributes;
import imi.character.avatar.MaleAvatarAttributes;
import imi.gui.JPanel_BasicOptions;
import imi.gui.SceneEssentials;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.jme.ClientContextJME;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.modules.avatarbase.client.AvatarConfigManager;
import org.jdesktop.wonderland.modules.avatarbase.client.AvatarConfigManager.AvatarManagerListener;
import org.jdesktop.wonderland.modules.avatarbase.client.cell.AvatarConfigComponent;
import org.jdesktop.wonderland.modules.avatarbase.client.jme.cellrenderer.AvatarImiJME.RelativeResourceLocator;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;

/**
 *
 * @author paulby
 */
public class AvatarConfigFrame extends javax.swing.JFrame {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("org/jdesktop/wonderland/modules/avatarbase/client/resources/Bundle");

    private AvatarImiJME avatarRenderer;
    private AvatarConfigManager avatarManager;

    private String currentAvatarSelection = null;

    private Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    private Cursor normalCursor = Cursor.getDefaultCursor();

    private boolean enableCustomisation = false;

    /** Creates new form AvatarConfigFrame */
    public AvatarConfigFrame(AvatarImiJME avatarRenderer) {
        this.avatarRenderer = avatarRenderer;
        initComponents();
        SceneEssentials scene = new SceneEssentials();
        scene.setAvatar(avatarRenderer.getAvatarCharacter());
        scene.setWM(ClientContextJME.getWorldManager());

        // Disable the import button, it requires the assets dir to be simlinked in core
        importB.setEnabled(false);

        // Test code using IMI BasicOptions panel to customise avatar
        if (enableCustomisation) {
            JPanel_BasicOptions basicOptions = new JPanel_BasicOptions(this);
            basicOptions.setSceneData(scene);
            basicOptions.avatarCheck();
            basicOptions.setBaseURL(avatarRenderer.getAvatarCharacter().getAttributes().getBaseURL());
            scrollPane.getViewport().add(basicOptions);
            customiseB.setEnabled(true);
        }

        WonderlandSession session = avatarRenderer.getCell().getCellCache().getSession();
        avatarManager = AvatarConfigManager.getAvatarConfigManager();

        DefaultListModel listModel = (DefaultListModel) avatarList.getModel();

        Iterable<String> avatars = avatarManager.getAvatars();
        for(String a : avatars) {
            listModel.addElement(a);
        }

        avatarManager.addAvatarManagerListener(new AvatarManagerListener() {
            public void avatarAdded(String name) {
                ((DefaultListModel) avatarList.getModel()).addElement(name);
            }

            public void avatarRemoved(String name) {
                ((DefaultListModel) avatarList.getModel()).removeElement(name);
            }
        });

        defaultAvatarTF.setText(AvatarConfigManager.getAvatarConfigManager().getDefaultAvatarName());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderGrou = new javax.swing.ButtonGroup();
        customiseFrame = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        saveB = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        chooseAvatarPanel = new javax.swing.JPanel();
        avatarListScrollPane = new javax.swing.JScrollPane();
        avatarList = new javax.swing.JList();
        deleteB = new javax.swing.JButton();
        viewB = new javax.swing.JButton();
        defaultAvatarTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        customiseB = new javax.swing.JButton();
        createAvatarPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        maleRB = new javax.swing.JRadioButton();
        femaleRB = new javax.swing.JRadioButton();
        addB = new javax.swing.JButton();
        avatarNameTF = new javax.swing.JTextField();
        randomizeB = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        importB = new javax.swing.JButton();

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(scrollPane, java.awt.BorderLayout.CENTER);

        customiseFrame.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        saveB.setText(bundle.getString("Save")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .add(saveB)
                .add(148, 148, 148))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .add(saveB)
                .addContainerGap())
        );

        customiseFrame.getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        setTitle(bundle.getString("Edit_Avatar")); // NOI18N

        chooseAvatarPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("My Avatars"));

        avatarList.setModel(new DefaultListModel());
        avatarList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        avatarList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                avatarListValueChanged(evt);
            }
        });
        avatarListScrollPane.setViewportView(avatarList);

        deleteB.setText(bundle.getString("Delete")); // NOI18N
        deleteB.setToolTipText(bundle.getString("Delete_selected_avatar")); // NOI18N
        deleteB.setEnabled(false);
        deleteB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBActionPerformed(evt);
            }
        });

        viewB.setText(bundle.getString("Apply")); // NOI18N
        viewB.setToolTipText(bundle.getString("Apply_selected_avatar")); // NOI18N
        viewB.setEnabled(false);
        viewB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBActionPerformed(evt);
            }
        });

        jLabel2.setText(bundle.getString("Current_Avatar:")); // NOI18N

        customiseB.setText(bundle.getString("Customise")); // NOI18N
        customiseB.setEnabled(false);
        customiseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customiseBActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout chooseAvatarPanelLayout = new org.jdesktop.layout.GroupLayout(chooseAvatarPanel);
        chooseAvatarPanel.setLayout(chooseAvatarPanelLayout);
        chooseAvatarPanelLayout.setHorizontalGroup(
            chooseAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(chooseAvatarPanelLayout.createSequentialGroup()
                .add(14, 14, 14)
                .add(chooseAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(chooseAvatarPanelLayout.createSequentialGroup()
                        .add(avatarListScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 159, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(6, 6, 6)
                        .add(chooseAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(customiseB)
                            .add(viewB)
                            .add(deleteB)))
                    .add(chooseAvatarPanelLayout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(defaultAvatarTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(170, Short.MAX_VALUE))
        );

        chooseAvatarPanelLayout.linkSize(new java.awt.Component[] {deleteB, viewB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        chooseAvatarPanelLayout.setVerticalGroup(
            chooseAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(chooseAvatarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(chooseAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(avatarListScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 186, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(chooseAvatarPanelLayout.createSequentialGroup()
                        .add(viewB)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(deleteB)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(customiseB)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chooseAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(defaultAvatarTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(21, 21, 21))
        );

        createAvatarPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Avatar"));

        jLabel1.setText(bundle.getString("Avatar_Name:")); // NOI18N

        genderGrou.add(maleRB);
        maleRB.setSelected(true);
        maleRB.setText(bundle.getString("Male")); // NOI18N

        genderGrou.add(femaleRB);
        femaleRB.setText(bundle.getString("Female")); // NOI18N

        addB.setText(bundle.getString("Add_to_My_Avatars")); // NOI18N
        addB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBActionPerformed(evt);
            }
        });

        avatarNameTF.setText(bundle.getString("my_avatar")); // NOI18N

        randomizeB.setText(bundle.getString("Randomize")); // NOI18N
        randomizeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeBActionPerformed(evt);
            }
        });

        jLabel4.setText(bundle.getString("Gender:")); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/jdesktop/wonderland/modules/avatarbase/client/resources/Bundle"); // NOI18N
        importB.setText(bundle.getString("Import...")); // NOI18N
        importB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importBActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout createAvatarPanelLayout = new org.jdesktop.layout.GroupLayout(createAvatarPanel);
        createAvatarPanel.setLayout(createAvatarPanelLayout);
        createAvatarPanelLayout.setHorizontalGroup(
            createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAvatarPanelLayout.createSequentialGroup()
                .add(43, 43, 43)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(addB)
                    .add(createAvatarPanelLayout.createSequentialGroup()
                        .add(randomizeB)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(importB))
                    .add(createAvatarPanelLayout.createSequentialGroup()
                        .add(maleRB)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(femaleRB)))
                .addContainerGap(61, Short.MAX_VALUE))
            .add(createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(createAvatarPanelLayout.createSequentialGroup()
                    .add(17, 17, 17)
                    .add(jLabel1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(avatarNameTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .add(8, 8, 8)))
        );
        createAvatarPanelLayout.setVerticalGroup(
            createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAvatarPanelLayout.createSequentialGroup()
                .add(38, 38, 38)
                .add(createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maleRB)
                    .add(jLabel4)
                    .add(femaleRB))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(randomizeB)
                    .add(importB))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addB)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(createAvatarPanelLayout.createSequentialGroup()
                    .add(4, 4, 4)
                    .add(createAvatarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel1)
                        .add(avatarNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(105, Short.MAX_VALUE)))
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(chooseAvatarPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(createAvatarPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(chooseAvatarPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 263, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAvatarPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void randomizeBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomizeBActionPerformed
        final JFrame f = this;
        f.setCursor(waitCursor);

        EventQueue.invokeLater(new Runnable() {
            public void run() {

                WlAvatarCharacter avatarCharacter;
                CharacterAttributes attributes;
                String name = avatarNameTF.getText();

                if (femaleRB.isSelected())
                    attributes = new FemaleAvatarAttributes(name, true);
                else
                    attributes = new MaleAvatarAttributes(name, true);

                WonderlandSession session = avatarRenderer.getCell().getCellCache().getSession();
                ServerSessionManager manager = session.getSessionManager();
                String serverHostAndPort = manager.getServerNameAndPort();
                attributes.setBaseURL("wla://avatarbaseart@"+serverHostAndPort+"/");

                LoadingInfo.startedLoading(avatarRenderer.getCell().getCellID(), name);
                try {
                    WorldManager wm = ClientContextJME.getWorldManager();
                    avatarCharacter = new WlAvatarCharacter(attributes, wm);
                } finally {
                    LoadingInfo.finishedLoading(avatarRenderer.getCell().getCellID(), name);
                }

                avatarRenderer.changeAvatar(avatarCharacter);
                f.setCursor(normalCursor);
           }
        });

    }//GEN-LAST:event_randomizeBActionPerformed

    private void addBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBActionPerformed
        final JFrame f = this;
        f.setCursor(waitCursor);
        try {
            if (avatarManager.exists(avatarNameTF.getText())) {
                int option = JOptionPane.showConfirmDialog(this, bundle.getString("An_avatar_with_name_exists,_overwrite_?"), bundle.getString("Avatar_Exists"), JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.CANCEL_OPTION) {
                    avatarNameTF.selectAll();
                    return;
                }
            }

            avatarRenderer.getAvatarCharacter().getAttributes().setName(avatarNameTF.getText());
            avatarManager.saveAvatar(avatarNameTF.getText(), avatarRenderer.getAvatarCharacter());
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    avatarList.setSelectedValue(avatarNameTF.getText(), rootPaneCheckingEnabled);
                    applyToServer(avatarNameTF.getText());
                }
            });
            
        } catch (ContentRepositoryException ex) {
            Logger.getLogger(AvatarConfigFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AvatarConfigFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            f.setCursor(normalCursor);
        }
}//GEN-LAST:event_addBActionPerformed

    private void applyToServer(final String selected) {
        if (selected.equals(currentAvatarSelection))
            return;

        final JFrame f = this;
        f.setCursor(waitCursor);

        Thread t = new Thread() {
            public void run() {
                try {
                    AvatarConfigManager.getAvatarConfigManager().setDefaultAvatarName(selected);
                    AvatarConfigComponent configComponent = avatarRenderer.getCell().getComponent(AvatarConfigComponent.class);
                    URL selectedURL = AvatarConfigManager.getAvatarConfigManager().getNamedAvatarServerURL(
                                    (String)avatarList.getSelectedValue(),
                                    avatarRenderer.getCell().getCellCache().getSession().getSessionManager());
                    if (selectedURL!=null)
                        configComponent.requestConfigChange(selectedURL);
                    else
                        Logger.getLogger(AvatarConfigFrame.class.getName()).warning(bundle.getString("Unable_to_apply_null_default_avatar"));
                    defaultAvatarTF.setText(selected);
                    currentAvatarSelection = selected;
                } finally {
                    f.setCursor(normalCursor);
                }
            }
        };
        t.start();
    }

    private void avatarListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_avatarListValueChanged
        if (evt.getValueIsAdjusting())
            return;

        viewB.setEnabled(true);
        deleteB.setEnabled(true);
//        applyDefaultB.setEnabled(true);
    }//GEN-LAST:event_avatarListValueChanged

    private void deleteBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBActionPerformed
        String selected = (String) avatarList.getSelectedValue();
        if (selected == null)
            return;

        int index = avatarList.getSelectedIndex();
        avatarManager.deleteAvatar(selected);

        if (index>1)
            avatarList.setSelectedIndex(index-1);
        else
            avatarList.setSelectedIndex(0);
    }//GEN-LAST:event_deleteBActionPerformed

    private void viewBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBActionPerformed
        String selected = (String) avatarList.getSelectedValue();

        if (selected==null || selected.equals(currentAvatarSelection))
            return;

        final JFrame f = this;
        f.setCursor(waitCursor);
        URL selectedURL = AvatarConfigManager.getAvatarConfigManager().getNamedAvatarURL((String)avatarList.getSelectedValue());
        WonderlandSession session = avatarRenderer.getCell().getCellCache().getSession();
        ServerSessionManager manager = session.getSessionManager();
        String serverHostAndPort = manager.getServerNameAndPort();
        final WlAvatarCharacter avatarCharacter = new WlAvatarCharacter(selectedURL,
                ClientContextJME.getWorldManager(),
                "wla://avatarbaseart@"+serverHostAndPort+"/");

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    avatarRenderer.changeAvatar(avatarCharacter);
                } finally {
                    f.setCursor(normalCursor);
                }
            }
        });

        applyToServer(selected);
}//GEN-LAST:event_viewBActionPerformed

    private void customiseBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customiseBActionPerformed
        customiseFrame.setVisible(true);
}//GEN-LAST:event_customiseBActionPerformed

    private void importBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importBActionPerformed
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           System.out.println("You chose to open this file: " +
                chooser.getSelectedFile().getName());
            URL selectedURL;
            try {
                selectedURL = chooser.getSelectedFile().toURI().toURL();
                final WlAvatarCharacter avatarCharacter = new WlAvatarCharacter(selectedURL,
                        ClientContextJME.getWorldManager(),
                        "");
                final JFrame f = this;
                f.setCursor(waitCursor);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            avatarRenderer.changeAvatar(avatarCharacter);
                        } finally {
                            f.setCursor(normalCursor);
                        }
                    }
                });
            } catch (MalformedURLException ex) {
                Logger.getLogger(AvatarConfigFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_importBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addB;
    private javax.swing.JList avatarList;
    private javax.swing.JScrollPane avatarListScrollPane;
    private javax.swing.JTextField avatarNameTF;
    private javax.swing.JPanel chooseAvatarPanel;
    private javax.swing.JPanel createAvatarPanel;
    private javax.swing.JButton customiseB;
    private javax.swing.JFrame customiseFrame;
    private javax.swing.JTextField defaultAvatarTF;
    private javax.swing.JButton deleteB;
    private javax.swing.JRadioButton femaleRB;
    private javax.swing.ButtonGroup genderGrou;
    private javax.swing.JButton importB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton maleRB;
    private javax.swing.JButton randomizeB;
    private javax.swing.JButton saveB;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton viewB;
    // End of variables declaration//GEN-END:variables

}
