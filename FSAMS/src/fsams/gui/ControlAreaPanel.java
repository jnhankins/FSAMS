/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fsams.gui;

import fsams.FSAMS;
import fsams.Simulation;
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
    private final Simulation sim;
    private JButton callEmergency;
    private JButton lockDoors;
    private JButton activateSprinklers;
    private JButton activateAlarms;
    private JButton masterReset;
    private JButton eShutDown;
    
    public ControlAreaPanel(FSAMS fsams, Simulation sim) {
        this.fsams = fsams;
        this.sim = sim;
        initComponents();
    }
    
    private void initComponents() {
        callEmergency = new JButton();
        lockDoors = new JButton();
        activateSprinklers = new JButton();
        activateAlarms = new JButton();
        masterReset = new JButton();
        eShutDown = new JButton();
        
        callEmergency.addActionListener(this);
        lockDoors.addActionListener(this);
        activateSprinklers.addActionListener(this);
        activateAlarms.addActionListener(this);
        masterReset.addActionListener(this);
        eShutDown.addActionListener(this);
        
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
        
        callEmergency.setToolTipText("Notifies Police and Fire Departmant Services.");
        lockDoors.setToolTipText("Locks all doors in the event of a security breach.");
        activateSprinklers.setToolTipText("Turns on all sprinklers.");
        activateAlarms.setToolTipText("Turns on all alarms");
        masterReset.setToolTipText("Resets all systems: doors, sprinklers, and alarms.");
        eShutDown.setToolTipText("Shuts down all equipment.");
        
        callEmergency.setEnabled(false);
        lockDoors.setEnabled(false);
        activateSprinklers.setEnabled(false);
        activateAlarms.setEnabled(false);
        masterReset.setEnabled(false);
        eShutDown.setEnabled(false);
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if(src == callEmergency) {
            JOptionPane.showMessageDialog(new JFrame(), "Emergency Services Have Been Notified.");
        } else if(src == lockDoors) {
            JOptionPane.showMessageDialog(new JFrame(), "All Doors Have Been Locked.");
            sim.setLockAll(true);
            fsams.repaint();
        } else if(src == activateSprinklers) {          
            JOptionPane.showMessageDialog(new JFrame(), "All Fire Suppression Systems Have Been Activated.");
            sim.setSuppressionAll(true);
            fsams.repaint();
        } else if (src == activateAlarms) {
            JOptionPane.showMessageDialog(new JFrame(), "All Alarms Have Been Activated.");
             sim.setAlarmAll(true);
             fsams.repaint();
        } else if (src == masterReset) {
            JOptionPane.showMessageDialog(new JFrame(), "System Has Been Reset.");
            sim.setLockAll(false);
            sim.setAlarmAll(false);
            sim.setSuppressionAll(false);
            sim.setSensorsAll(false);
            fsams.repaint();
        } else if (src == eShutDown) {
            JOptionPane.showMessageDialog(new JFrame(), "All Equipment Has Been Shut Down.");
        } 
        
     }
    
    public void startSim() {
        callEmergency.setEnabled(true);
        lockDoors.setEnabled(true);
        activateSprinklers.setEnabled(true);
        activateAlarms.setEnabled(true);
        masterReset.setEnabled(true);
        eShutDown.setEnabled(true);
    }
    public void stopSim() {
        callEmergency.setEnabled(false);
        lockDoors.setEnabled(false);
        activateSprinklers.setEnabled(false);
        activateAlarms.setEnabled(false);
        masterReset.setEnabled(false);
        eShutDown.setEnabled(false);
    }
}
