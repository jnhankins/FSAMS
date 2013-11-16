/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.logic;

import fsams.components.ComponentManager;
import javax.swing.JPanel;

public class Simulation extends Thread{
    
    private boolean startFlag;
    private boolean isProgRunning;
    private boolean isSimRunning;

    private ComponentManager components;
    private JPanel panel;
    
    public Simulation(JPanel panel) {
        startFlag = false;
        isProgRunning = true;
        isSimRunning = false;
        this.panel = panel;
    }
    
    public boolean isSimRunning(){
        return isSimRunning;
    }
    
    public ComponentManager getComponents(){
        return components;
    }

    public void startSim(ComponentManager components) {
        startFlag = !isSimRunning;
        System.out.println("hello I'm starting");
        if(!isSimRunning)
            this.components = new ComponentManager(components);
    }
    
    public void stopSim(){
        isSimRunning = false;
        System.out.println("goodbye I'm stopping");
    }
//    public void exit(){
//        stopSim();
//        isRunning = false;
//    }

    @Override
    public void run() {
        while(isProgRunning){
            if(startFlag){
                startFlag = false;
                isSimRunning = true;
                long currentTime = System.currentTimeMillis();

                while(isSimRunning){
                    double elapseTime = (System.currentTimeMillis() - currentTime)/1000.0;
                    currentTime = System.currentTimeMillis();
                    components.update(elapseTime);
                    panel.repaint();

                }
                isSimRunning = false;
               }
               try {
                   Thread.sleep(100);
               } catch (InterruptedException ex) {
                   System.out.println("SOMETHING WENT REALLY WRONG: "+ex);
               }
        }
    }
}
