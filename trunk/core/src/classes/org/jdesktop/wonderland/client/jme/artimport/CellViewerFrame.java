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
package org.jdesktop.wonderland.client.jme.artimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.CellComponent;
import org.jdesktop.wonderland.client.cell.CellManager;
import org.jdesktop.wonderland.client.cell.CellStatusChangeListener;
import org.jdesktop.wonderland.client.cell.RootCell;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.common.cell.CellStatus;

/**
 *
 * @author  paulby
 */
public class CellViewerFrame extends javax.swing.JFrame {

    private ArrayList<Cell> rootCells = new ArrayList();
    private DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("Root");
    private HashMap<Cell, DefaultMutableTreeNode> nodes = new HashMap();
    
    /** Creates new form CellViewerFrame */
    public CellViewerFrame(WonderlandSession session) {
        initComponents();
        CellManager.getCellManager().addCellStatusChangeListener(new CellStatusChangeListener() {

            public void cellStatusChanged(Cell cell, CellStatus status) {
                System.out.println("Status changed "+cell+"  "+status);
                
                DefaultMutableTreeNode node = nodes.get(cell);
                
                switch(status) {
                    case DISK :
                        if (node!=null)
                            ((DefaultTreeModel)cellTree.getModel()).removeNodeFromParent(node);
                        break;
                    case BOUNDS :
                        DefaultMutableTreeNode parentNode = nodes.get(cell.getParent());
                        if (parentNode==null && !(cell instanceof RootCell)) {
                            System.err.println("******* Null parent "+cell.getParent());
                        } else {
                            if (node==null) {
                                node = new DefaultMutableTreeNode(cell);
                                nodes.put(cell, node);
                                if (cell instanceof RootCell)
                                    parentNode = treeRoot;
                                ((DefaultTreeModel)cellTree.getModel()).insertNodeInto(node, parentNode, parentNode.getChildCount());
                            }
                        }
                        break;
                }
            }
            
        });
        
        refreshCells(session);
        ((DefaultTreeModel)cellTree.getModel()).setRoot(treeRoot);
    }
    
    /**
     * Get the  cells from the cache and update the UI
     */
    private void refreshCells(WonderlandSession session) {
        CellCache cache = ClientContext.getCellCache(session);
        
        for(Cell rootCell : cache.getRootCells()) {
            rootCells.add(rootCell);
        }
        
        populateJTree();
    }
    
    private void populateJTree() {
        for(Cell rootCell : rootCells) {            
            treeRoot.add(createJTreeNode(rootCell));
        }
    }
    
    private DefaultMutableTreeNode createJTreeNode(Cell cell) {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(cell);
        nodes.put(cell, ret);
        System.out.println("PUT "+cell);
        
        List<Cell> children = cell.getChildren();
        for(Cell child : children)
            ret.add(createJTreeNode(child));
        
        return ret;
    }
    
    private void populateCellPanelInfo(Cell cell) {
        if (cell==null) {
            cellClassNameTF.setText(null);
            cellNameTF.setText(null);
            DefaultListModel listModel = (DefaultListModel) cellComponentList.getModel();
            listModel.clear();
        } else {
            cellClassNameTF.setText(cell.getClass().getName());
            cellNameTF.setText(cell.getName());
            DefaultListModel listModel = (DefaultListModel) cellComponentList.getModel();
            listModel.clear();
            for(CellComponent c : cell.getComponents()) {
                listModel.addElement(c.getClass().getName());
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        cellTree = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        cellInfoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cellComponentList = new javax.swing.JList();
        cellClassNameTF = new javax.swing.JTextField();
        cellNameTF = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cell Viewer");

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(300);

        cellTree.setMaximumSize(new java.awt.Dimension(400, 57));
        cellTree.setMinimumSize(new java.awt.Dimension(100, 0));
        cellTree.setPreferredSize(new java.awt.Dimension(200, 57));
        cellTree.setRootVisible(false);
        cellTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                cellTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(cellTree);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jLabel1.setText("Cell Class :");

        jLabel2.setText("Cell Name :");

        jLabel3.setText("Cell Components :");

        cellComponentList.setModel(new DefaultListModel());
        jScrollPane2.setViewportView(cellComponentList);

        cellClassNameTF.setText("jTextField1");

        cellNameTF.setText("jTextField1");

        org.jdesktop.layout.GroupLayout cellInfoPanelLayout = new org.jdesktop.layout.GroupLayout(cellInfoPanel);
        cellInfoPanel.setLayout(cellInfoPanelLayout);
        cellInfoPanelLayout.setHorizontalGroup(
            cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(cellInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(cellNameTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .add(cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(cellClassNameTF)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cellInfoPanelLayout.setVerticalGroup(
            cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(cellInfoPanelLayout.createSequentialGroup()
                .add(23, 23, 23)
                .add(cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(cellClassNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(cellNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cellInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addContainerGap(210, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cell Info", cellInfoPanel);

        jSplitPane1.setRightComponent(jTabbedPane1);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cellTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_cellTreeValueChanged
    // Tree selection
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                       cellTree.getLastSelectedPathComponent();

    if (node == null) {
        //Nothing is selected.	
        return;
    }

    Cell cell = (Cell) node.getUserObject();
    System.out.println("Got cell "+cell);
    populateCellPanelInfo(cell);

}//GEN-LAST:event_cellTreeValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cellClassNameTF;
    private javax.swing.JList cellComponentList;
    private javax.swing.JPanel cellInfoPanel;
    private javax.swing.JTextField cellNameTF;
    private javax.swing.JTree cellTree;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
