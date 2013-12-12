package fsams.gui;

import fsams.Simulation;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author FSAMS Team
 */
public final class TimerPanel extends JPanel {
    private final Simulation sim;
    private JLabel label;
    private TimerThread timer;

    /**
     * Creates a new TimerPanel to display a timer when the simulation begins
     * @param sim
     */
    public TimerPanel(Simulation sim) {
        this.sim = sim;
        timer = new TimerThread(this);
        initComponents();
    }
    

    private void initComponents(){
        
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
    
    /**
     * Starts the timer
     */
    public void start() {
        if(!timer.isAlive()){
            timer = new TimerThread(this);
            timer.start();
        }
    }

    /**
     * Stops the timer
     */
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
            
            /*
            WHILE Systems CurrentTimeInMilliseconds < endTime AND !shouldStop
                format the (endTime - CurrentTimeInMilliseconds) to mm:ss:SSS
                where mm are minutes and ss are seconds and SSS are microseconds
                display the time
                sleep for 100 to allow other threads access data
            END WHILE
            SET TIMER TO 00:00:00
            IF(!shouldStop)
                calls the simulation function timeOut()
            set shouldStop to false
            */
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
