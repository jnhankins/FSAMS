/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fsams.gui;

import fsams.Simulation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author FSAMS Team
 */
public class ControlAreaPanel extends JPanel implements ActionListener {
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
    private JButton callSecurity;
    
    /**
     * Creates a new ControlAreaPanel to control the simulation after the
     * simulation begins.
     * @param sim
     */
    public ControlAreaPanel(Simulation sim) {
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
        callSecurity = new JButton();
        
        
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
        callSecurity.addActionListener(this);
        
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
        
        row++;
        callSecurity.setText("Call Security");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(callSecurity, gridBagConstraints);
        
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
        callSecurity.setToolTipText("Call security.");
        
        doorLock.setActionCommand("doorLock");
        doorUnlock.setActionCommand("doorUnlock");
        sprinklerEnable.setActionCommand("sprinklerEnable");
        sprinklerDisable.setActionCommand("sprinklerDisable");
        alarmEnable.setActionCommand("alarmEnable");
        alarmDisable.setActionCommand("alarmDisable");
        equipmentEnable.setActionCommand("equipmentEnable");
        equipmentDisable.setActionCommand("equipmentDisable");
        masterReset.setActionCommand("masterReset");
        callEmergency.setActionCommand("callEmergency");
        callSecurity.setActionCommand("callSecurity");
        
        stopSim();
    }
    

    public void actionPerformed(ActionEvent ae) {
        sim.stopTimer();
        switch(ae.getActionCommand()) {
            case "doorLock":
                sim.setLockAll(true);
                break;
            case "doorUnlock": 
                sim.setLockAll(false);
                break;
            case "sprinklerEnable":
                sim.setSuppressionAll(true);
                break;
            case "sprinklerDisable":
                sim.setSuppressionAll(false);
                break;
            case "alarmEnable":
                sim.setAlarmAll(true);
                break;
            case "alarmDisable":
                sim.setAlarmAll(false);
                break;
            case "equipmentEnable":
                sim.setEquipmentAll(true);
                break;
            case "equipmentDisable":
                sim.setEquipmentAll(false);
                break;
            case "masterReset":
                sim.setLockAll(false);
                sim.setSuppressionAll(false);
                sim.setAlarmAll(false);
                //TODO sensor
                sim.setEquipmentAll(true);
                break;
            case "callEmergency":
                JOptionPane.showMessageDialog(new JFrame(), "Emergency services have been notified.");
                break;
            case "callSecurity":
                if (sim.getCameraSees())
                    JOptionPane.showMessageDialog(new JFrame(), "Security has been notified about suspicious activity.");
                if (!sim.getCameraSees()) 
                    JOptionPane.showMessageDialog(new JFrame(), "No suspicious activity to report.");
                break;        
        }
     }
    
    /**
     * Enables the buttons when the simulation starts
     */
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
        callSecurity.setEnabled(true);
    }

    /**
     * Disables the buttons when the simulation stops
     */
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
        callSecurity.setEnabled(false);
    }
}
