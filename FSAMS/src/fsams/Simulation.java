/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams;

import fsams.grid.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Simulation extends Thread{
    // Simulation State Flags
    private boolean startFlag;
    private boolean isProgRunning;
    private boolean isSimRunning;
    // Data Model
    private Grid grid;
    // GUI Component
    private JPanel panel;
    
    public Simulation(JPanel panel) {
        this.panel = panel;
        startFlag = false;
        isProgRunning = true;
        isSimRunning = false;
    }
    
    public boolean isSimRunning(){
        return isSimRunning;
    }

    public void startSim(Grid grid) {
        startFlag = !isSimRunning;
        System.out.println("hello I'm starting");
        if(!isSimRunning) {
            this.grid = grid;
        }
    }
    
    public void stopSim(){
        isSimRunning = false;
        System.out.println("goodbye I'm stopping");
    }
    
    @Override
    public void run() {
        while(isProgRunning){
            if(startFlag){
                startFlag = false;
                isSimRunning = true;
                long lastTime = System.currentTimeMillis();
                while(isSimRunning){
                    long currTime = System.currentTimeMillis();
                    double elapTime = (currTime - lastTime)/1000.0;
                    
                    Grid.Tile[][] tiles = grid.getTiles();
                    // Draw components
                    for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                        for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                            // Get the tile
                            Grid.Tile tile = tiles[grid_x][grid_y];
                            // Get the components
                            ArrayList<Component> components = tile.getComponents();
                            for(Component component: components) {
                                if(component instanceof Fire) {
                                    simFire(grid_x,grid_y);
                                }
                            }
                        }
                    }
                    
                    panel.repaint();
                    lastTime = currTime;
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
    
    public void simFire(int grid_x, int grid_y) {
        final double spread_probability = 0.5;
        if(grid_x-1>=0 && !grid.getTiles()[grid_x][grid_y].getWallL()) {
            int x = grid_x-1;
            int y = grid_y;
            if(Math.random()<spread_probability) {
                
            }
        }
        if(grid_y-1>=0) {
            int x = grid_x;
            int y = grid_y-1;
            if(Math.random()<spread_probability) {
                
            }
        }
        if(grid_x+1<Grid.grid_width) {
            int x = grid_x+1;
            int y = grid_y;
            if(Math.random()<spread_probability) {
                
            }
        }
        if(grid_y+1<Grid.grid_height) {
            int x = grid_x;
            int y = grid_y+1;
            if(Math.random()<spread_probability) {
                
            }
        }
    }
}
