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
    
    private JButton doorLock;
    private JButton doorUnlock;
    private JButton sprinklerEnable;
    private JButton sprinklerDisable;
    private JButton alarmEnable;
    private JButton alarmDisable;
    private JButton equipmentEnable;
    private JButton equipmentDisable;
    private JButton masterReset;
    private JButton callEmergency;
    
    public ControlAreaPanel(FSAMS fsams, Simulation sim) {
        this.fsams = fsams;
        this.sim = sim;
        initComponents();
    }
    
    private void initComponents() {
        doorLock = new JButton();
        doorUnlock = new JButton();
        sprinklerEnable = new JButton();
        sprinklerDisable = new JButton();
        alarmEnable = new JButton();
        alarmDisable = new JButton();
        equipmentEnable = new JButton();
        equipmentDisable = new JButton();
        masterReset = new JButton();
        callEmergency = new JButton();
        
        
        doorLock.addActionListener(this);
        doorUnlock.addActionListener(this);
        sprinklerEnable.addActionListener(this);
        sprinklerDisable.addActionListener(this);
        alarmEnable.addActionListener(this);
        alarmDisable.addActionListener(this);
        equipmentEnable.addActionListener(this);
        equipmentDisable.addActionListener(this);
        masterReset.addActionListener(this);
        callEmergency.addActionListener(this);
        
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;
        
        int row=0;
        doorLock.setText("Lock Doors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(doorLock, gridBagConstraints);
        doorUnlock.setText("Unlock Doors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(doorUnlock, gridBagConstraints);
        
        row++;
        sprinklerEnable.setText("Activate Sprinklers");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(sprinklerEnable, gridBagConstraints);
        sprinklerDisable.setText("Deactivate Sprinklers");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(sprinklerDisable, gridBagConstraints);
        
        row++;
        alarmEnable.setText("Activate Alarms");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(alarmEnable, gridBagConstraints);
        alarmDisable.setText("Deactivate Alarms");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(alarmDisable, gridBagConstraints);
        
        row++;
        equipmentEnable.setText("Power Equipment");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(equipmentEnable, gridBagConstraints);
        equipmentDisable.setText("Shutdown Equipment");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(equipmentDisable, gridBagConstraints);
        
        row++;
        masterReset.setText("Master Reset");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(masterReset, gridBagConstraints);
        
        row++;
        callEmergency.setText("Call Emergency Serviecs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(callEmergency, gridBagConstraints);
        
        doorLock.setToolTipText("Lock all doors.");
        doorUnlock.setToolTipText("Unlock all doors.");
        sprinklerEnable.setToolTipText("Activate all sprinklers.");
        sprinklerDisable.setToolTipText("Deactivate all sprinklers.");
        alarmEnable.setToolTipText("Activate all alarms.");
        alarmDisable.setToolTipText("Deactivate all alarms.");
        equipmentEnable.setToolTipText("Enable all equipment.");
        equipmentDisable.setToolTipText("Disable all equipment.");
        masterReset.setToolTipText("Reset all system components to their initial state.");
        callEmergency.setToolTipText("Call emergency services.");
        
        stopSim();
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        
        
        if(src == doorLock) {
            sim.setLockAll(true);
        } 
        else if(src == doorUnlock) { 
            sim.setLockAll(false);
        }
        else if(src == sprinklerEnable) {
            sim.setSuppressionAll(true);
        }
        else if(src == sprinklerDisable) {
            sim.setSuppressionAll(false);
        }
        else if(src == alarmEnable) {
            sim.setAlarmAll(true);
        }
        else if(src == alarmDisable) {
            sim.setAlarmAll(false);
        }
        else if(src == equipmentEnable) {
            sim.setEquipmentAll(true);
        }
        else if(src == equipmentDisable) {
            sim.setEquipmentAll(false);
        }
        else if(src == masterReset) {
            sim.setLockAll(false);
            sim.setSuppressionAll(false);
            sim.setAlarmAll(false);
            //TODO sensor
            sim.setEquipmentAll(true);
        }
        else if(src == callEmergency) {
            JOptionPane.showMessageDialog(new JFrame(), "Emergency services have been notified.");
        }
     }
    
    public void startSim() {
        doorLock.setEnabled(true);
        doorUnlock.setEnabled(true);
        sprinklerEnable.setEnabled(true);
        sprinklerDisable.setEnabled(true);
        alarmEnable.setEnabled(true);
        alarmDisable.setEnabled(true);
        equipmentEnable.setEnabled(true);
        equipmentDisable.setEnabled(true);
        masterReset.setEnabled(true);
        callEmergency.setEnabled(true);
    }
    public void stopSim() {
        doorLock.setEnabled(false);
        doorUnlock.setEnabled(false);
        sprinklerEnable.setEnabled(false);
        sprinklerDisable.setEnabled(false);
        alarmEnable.setEnabled(false);
        alarmDisable.setEnabled(false);
        equipmentEnable.setEnabled(false);
        equipmentDisable.setEnabled(false);
        masterReset.setEnabled(false);
        callEmergency.setEnabled(false);
    }
}
