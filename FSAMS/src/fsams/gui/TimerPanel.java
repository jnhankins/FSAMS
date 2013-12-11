/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fsams.gui;

import fsams.FSAMS;
import fsams.Simulation;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author FSAMS Team
 */
public final class TimerPanel extends JPanel {
    private final FSAMS fsams;
    private final Simulation sim;
    private JLabel label;
    private TimerThread timer;
    
    
    public TimerPanel(FSAMS fsams, Simulation sim) {
        this.fsams = fsams;
        this.sim = sim;
        timer = new TimerThread(this);
        initComponents();
    }
    
    public void initComponents(){
        
        label = new JLabel("00:00:000");//the start timer
        label.setFont(new Font("Serif", Font.PLAIN, 36));
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;
        
        int row = 0;
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(label, gridBagConstraints);
    }
    
    public void start() {
        if(!timer.isAlive()){
            timer = new TimerThread(this);
            timer.start();
        }
    }
    public void stop() {
        if(timer.isAlive())
            timer.shouldStop = true;
    }
    
    private class TimerThread extends Thread {
        TimerPanel panel;
        long endTime;
        boolean shouldStop;
        
        TimerThread(TimerPanel panel) {
            this.panel = panel;
            shouldStop = false;
        }
        
        @Override
        public void run() {
            shouldStop = false;
            
            final long timerLengthSec = 30;
            endTime = System.currentTimeMillis() + 1000*timerLengthSec;
            
            while(System.currentTimeMillis()<endTime && !shouldStop) {
                Date date = new Date(endTime - System.currentTimeMillis());
                SimpleDateFormat dt1 = new SimpleDateFormat("mm:ss:SSS");
                label.setText("<html><font color='red'>" + dt1.format(date) + "</font></html>");
                panel.repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    break;
                }
            }
            label.setText("00:00:000");
            panel.repaint();
            if(!shouldStop) {
                sim.timeOut();
            }
            
            shouldStop = false;
        }
    }
}
