package org.jdesktop.wonderland.modules.audiomanager.client;

import org.jdesktop.wonderland.modules.audiomanager.common.messages.VoiceChatInfoRequestMessage;

import org.jdesktop.wonderland.client.comms.WonderlandSession;

import org.jdesktop.wonderland.modules.avatarbase.client.jme.cellrenderer.NameTagNode;

import org.jdesktop.wonderland.modules.presencemanager.common.PresenceInfo;

import java.util.ArrayList;

import java.util.logging.Logger;

/*
 * HoldDialog.java
 *
 * Created on April 22, 2009, 12:45 PM
 */



/**
 *
 * @author  jp
 */
public class HoldDialog extends javax.swing.JFrame implements MemberChangeListener {

    private static final Logger logger =
        Logger.getLogger(HoldDialog.class.getName());

    private AudioManagerClient client;
    private WonderlandSession session;

    private String group;

    private InCallDialog inCallDialog;

    /** Creates new form HoldDialog */
    public HoldDialog() {
        initComponents();
    }

    public HoldDialog(AudioManagerClient client, WonderlandSession session,
            String group, InCallDialog inCallDialog) {

	this.client = client;
	this.session = session;
	this.group = group;
	this.inCallDialog = inCallDialog;

        initComponents();

	setTitle(group);

	client.addMemberChangeListener(group, this);

        session.send(client, new VoiceChatInfoRequestMessage(group));
    }

    private ArrayList<PresenceInfo> members = new ArrayList();

    public void setMemberList() {
	String memberText = "";

	for (PresenceInfo member : members) {
	    memberText += NameTagNode.getDisplayName(
                member.usernameAlias, member.isSpeaking, member.isMuted);

	    memberText += " ";
	}

	this.memberText.setText(memberText);
    }

    public void memberChange(PresenceInfo info, boolean added) {
	if (added) {
	    members.add(info);
	} else {
	    members.remove(info);
	}

	setMemberList();
    }

    public void setMemberList(PresenceInfo[] presenceInfo) {
	members.clear();

	for (int i = 0; i < presenceInfo.length; i++) {
	     members.add(presenceInfo[i]);
	}

	setMemberList();
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
        memberText = new javax.swing.JTextField();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("On Hold:");

        memberText.setEditable(false);
        memberText.setText("                                                                   ");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(memberText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(memberText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
    inCallDialog.setVisible(true);
    inCallDialog.setHold(false);
    setVisible(false);
}//GEN-LAST:event_formMouseClicked

private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
    inCallDialog.setVisible(true);
    inCallDialog.setHold(false);
    setVisible(false);
}//GEN-LAST:event_formMousePressed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    inCallDialog.endHeldCall();
}//GEN-LAST:event_formWindowClosing

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HoldDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField memberText;
    // End of variables declaration//GEN-END:variables

}
