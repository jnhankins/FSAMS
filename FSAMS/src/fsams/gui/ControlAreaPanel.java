/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fsams.gui;

import fsams.FSAMS;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Hector
 */
public class ControlAreaPanel extends JPanel implements ActionListener {
    private final FSAMS fsams;
    private JButton callEmergency;
    private JButton lockDoors;
    private JButton activateSprinklers;
    private JButton activateAlarms;
    private JButton masterReset;
    private JButton eShutDown;
    private JButton zoomIn;
    private JButton zoomOut;
    
    public ControlAreaPanel(FSAMS fsams) {
        this.fsams = fsams;
        initComponents();
    }
    
    private void initComponents() {
        callEmergency = new JButton();
        lockDoors = new JButton();
        activateSprinklers = new JButton();
        activateAlarms = new JButton();
        masterReset = new JButton();
        eShutDown = new JButton();
        zoomIn = new JButton();
        zoomOut = new JButton();
        
        callEmergency.addActionListener(this);
        lockDoors.addActionListener(this);
        activateSprinklers.addActionListener(this);
        activateAlarms.addActionListener(this);
        masterReset.addActionListener(this);
        eShutDown.addActionListener(this);
        zoomIn.addActionListener(this);
        zoomOut.addActionListener(this);
        
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;
        int row=0;
        
        callEmergency.setText("Emergency");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(callEmergency, gridBagConstraints);

        lockDoors.setText("Lock Doors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(lockDoors, gridBagConstraints);

        activateSprinklers.setText("Sprinklers");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(activateSprinklers, gridBagConstraints);

        activateAlarms.setText("Alarms");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(activateAlarms, gridBagConstraints);

        eShutDown.setText("Equipment Off");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(eShutDown, gridBagConstraints);
        
        masterReset.setText("Reset");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(masterReset, gridBagConstraints);
        
        zoomIn.setText("Zoom In");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(zoomIn, gridBagConstraints);
        
        zoomOut.setText("Zoom Out");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(zoomOut, gridBagConstraints);
        
        callEmergency.setToolTipText("Notifies Police and Fire Departmant Services.");
        lockDoors.setToolTipText("Locks all doors in the event of a security breach.");
        activateSprinklers.setToolTipText("Turns on all sprinklers.");
        activateAlarms.setToolTipText("Turns on all alarms");
        masterReset.setToolTipText("Resets all systems: doors, sprinklers, and alarms.");
        eShutDown.setToolTipText("Shuts down all equipment.");
  
        
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if(src == callEmergency) {
            JOptionPane.showMessageDialog(new JFrame(), "Emergency Services Have Been Notified.");
        } else if(src == lockDoors) {
            JOptionPane.showMessageDialog(new JFrame(), "All Doors Have Been Locked.");
            fsams.grid.setLockAll(true);
            fsams.repaint();
        } else if(src == activateSprinklers) {          
            JOptionPane.showMessageDialog(new JFrame(), "All Fire Suppression Systems Have Been Activated.");
            fsams.grid.setSuppressionAll(true);
            fsams.repaint();
        } else if (src == activateAlarms) {
            JOptionPane.showMessageDialog(new JFrame(), "All Alarms Have Been Activated.");
             fsams.grid.setAlarmAll(true);
             fsams.repaint();
        } else if (src == masterReset) {
            JOptionPane.showMessageDialog(new JFrame(), "System Has Been Reset.");
            fsams.grid.setLockAll(false);
            fsams.grid.setAlarmAll(false);
            fsams.grid.setSuppressionAll(false);
            fsams.repaint();
        } else if (src == eShutDown) {
            JOptionPane.showMessageDialog(new JFrame(), "All Equipment Has Been Shut Down.");
        } else if (src == zoomIn) {
            fsams.zoomIn();
            fsams.repaint();
        } else if (src == zoomOut) {
            fsams.zoomOut();
            fsams.repaint();
        }
        
     }
}
