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
package org.jdesktop.wonderland.modules.textchat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * A list of users that someone can start a chat with.
 *
 * @author Jordan Slott <jslott@dev.java.net>
 * @author nsimpson
 */
public class TextChatPanel extends javax.swing.JPanel {

    private Logger logger = Logger.getLogger(TextChatPanel.class.getName());
    private TextChatConnection textChatConnection = null;
    private String localUser = null;
    private String remoteUser = null;

    public TextChatPanel() {
        initComponents();

        // Listen for a Return on the text entry field, and click the Send
        // button
        messageTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        // Listen for the click of the Send button, and send the message to
        // the server. We immediately display the message locally since it is
        // not mirrored from the server for the sending client.
        sendButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String text = messageTextField.getText();
                textChatConnection.sendTextMessage(text, localUser, remoteUser);
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        messageTextField.setText("");
                    }
                });
                appendTextMessage(text, localUser);
            }
        });
    }

    /**
     * Adds a text message, given the user name and message to the chat window.
     */
    public void appendTextMessage(String message, String userName) {
        final String msg = userName + ": " + message + "\n";
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                messageTextArea.append(msg);
                messageTextArea.setCaretPosition(messageTextArea.getText().length());
            }
        });
    }

    /**
     * Makes the text frame active, giving the text chat connection, the name
     * of the local user and the name of the remote user (empty string if for
     * all users).
     *
     * @param connection The text chat communications connection
     * @param localUser The user name of this user
     * @param remoteUser The user name of the other user
     */
    public void setActive(TextChatConnection connection, String localUser,
            String remoteUser) {
        this.textChatConnection = connection;
        this.localUser = localUser;
        this.remoteUser = remoteUser;
        final String user = remoteUser;

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                messageTextField.setEnabled(true);
                sendButton.setEnabled(true);
            }
        });
    }

    /**
     * Deactivates the chat by displaying a message and turning off the GUI.
     */
    public void deactivate() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                String date = new SimpleDateFormat("h:mm a").format(new Date());
                String msg = "--- User " + remoteUser + " has left the world at " +
                        date + " ---\n";
                messageTextArea.append(msg);
                messageTextField.setEnabled(false);
                sendButton.setEnabled(false);
            }
        });
    }

    /**
     * Re-activates the chat by displaying a message and turning on the GUI.
     */
    public void reactivate() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                String date = new SimpleDateFormat("h:mm a").format(new Date());
                String msg = "--- User " + remoteUser + " has joined the world at " +
                        date + " ---\n";
                messageTextArea.append(msg);
                messageTextField.setEnabled(true);
                sendButton.setEnabled(true);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        textEntryPanel = new javax.swing.JPanel();
        messageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(360, 135));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        messageTextArea.setColumns(20);
        messageTextArea.setEditable(false);
        messageTextArea.setFont(messageTextArea.getFont().deriveFont(messageTextArea.getFont().getStyle() | java.awt.Font.BOLD));
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(6);
        messageTextArea.setTabSize(4);
        messageTextArea.setWrapStyleWord(true);
        messageTextArea.setFocusable(false);
        jScrollPane1.setViewportView(messageTextArea);

        messageTextField.setFont(messageTextField.getFont().deriveFont(messageTextField.getFont().getStyle() | java.awt.Font.BOLD));
        messageTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        messageTextField.setEnabled(false);

        sendButton.setText("Send");
        sendButton.setEnabled(false);

        javax.swing.GroupLayout textEntryPanelLayout = new javax.swing.GroupLayout(textEntryPanel);
        textEntryPanel.setLayout(textEntryPanelLayout);
        textEntryPanelLayout.setHorizontalGroup(
            textEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, textEntryPanelLayout.createSequentialGroup()
                .addComponent(messageTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton)
                .addContainerGap())
        );
        textEntryPanelLayout.setVerticalGroup(
            textEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(sendButton))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textEntryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(textEntryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JButton sendButton;
    private javax.swing.JPanel textEntryPanel;
    // End of variables declaration//GEN-END:variables
}
