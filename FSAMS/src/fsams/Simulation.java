/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams;

import fsams.grid.*;
import fsams.grid.Component.*;
import fsams.grid.Grid.Tile;
import fsams.pathfinding.AStarPathFinder;
import fsams.pathfinding.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                    
                    synchronized(grid) {
                        Grid.Tile[][] tiles = grid.getTiles();
                        // Draw components
                        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                                // Get the tile
                                Grid.Tile tile = tiles[grid_x][grid_y];
                                // Get the components
                                ArrayList<Component> components = tile.getComponents();
                                for(int comp_i=0; comp_i<components.size(); comp_i++) {
                                    Component component = components.get(comp_i);
                                    if(component instanceof Fire) {
                                        simFire(grid_x,grid_y,elapTime);
                                    }
                                    else if(component instanceof HumanAgent){
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if(simHumanAgent((HumanAgent)component, grid_x,grid_y,elapTime))
                                            comp_i--;
                                    }
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
    
    public void simFire(int grid_x, int grid_y, double elapTime) {
        // Left
        if(grid_x-1>=0 && !grid.getTiles()[grid_x][grid_y].getWallL()) {
            int x = grid_x-1;
            int y = grid_y;
            simBurnTile(x,y,elapTime);
        }
        // Down
        if(grid_y-1>=0 && !grid.getTiles()[grid_x][grid_y].getWallD()) {
            int x = grid_x;
            int y = grid_y-1;
            simBurnTile(x,y,elapTime);
        }
        // Right
        if(grid_x+1<Grid.grid_width && !grid.getTiles()[grid_x][grid_y].getWallR()) {
            int x = grid_x+1;
            int y = grid_y;
            simBurnTile(x,y,elapTime);
        }
        // Up
        if(grid_y+1<Grid.grid_height && !grid.getTiles()[grid_x][grid_y].getWallU()) {
            int x = grid_x;
            int y = grid_y+1;
            simBurnTile(x,y,elapTime);
        }
    }
    
    public void simBurnTile(int grid_x, int grid_y, double elapTime) {
        // There is a burn_probability % chance of the tile catching fire in burn_timeframe seconds.
        final double burn_probability = 0.75;
        final double burn_timeframe = 10.0;
        double prob = 1 - Math.pow(1.0-burn_probability,elapTime/burn_timeframe); // p'=1-(1-p)^(t'/t)
        if(Math.random()<prob) {
            Tile tile = grid.getTiles()[grid_x][grid_y];
            ArrayList<Component> components = tile.getComponents();
            for(Component component: components) {
                if(component instanceof Fire) {
                    return;
                }
                // TODO check for other types of components.
            }
            components.add(new Fire());
        }
    }
    
    
    boolean simHumanAgent(HumanAgent human, int grid_x, int grid_y, double elapTime) {
        tracePath(human, grid_x, grid_y);
        return false; 
   }
/*    boolean simHumanAgent(HumanAgent human, int grid_x, int grid_y, double elapTime) {
        Tile tiles[][] = grid.getTiles();
        //cannot reach exit - no exit in building
        if(true){
            boolean fireU, fireD, fireL, fireR;
            boolean openU, openD, openL, openR;
            fireU = fireD = fireL = fireR = false;
            openU = openD = openL = openR = false;
            // Left
            if(grid_x-1>=0 && !tiles[grid_x][grid_y].getWallL()) {
                openL = true;
                int x = grid_x-1;
                int y = grid_y;
                for(Component component: tiles[x][y].getComponents()) {
                    if(component instanceof Fire) {
                        fireL = true;
                        openL = false;
                        break;
                    }
                }
            }
            // Down
            if(grid_y-1>=0 && !tiles[grid_x][grid_y].getWallD()) {
                openD = true;
                int x = grid_x;
                int y = grid_y-1;
                for(Component component: tiles[x][y].getComponents()) {
                    if(component instanceof Fire) {
                        fireD = true;
                        openD = false;
                        break;
                    }
                }
            }
            // Right
            if(grid_x+1<Grid.grid_width && !tiles[grid_x][grid_y].getWallR()) {
                openR = true;
                int x = grid_x+1;
                int y = grid_y;
                for(Component component: tiles[x][y].getComponents()) {
                    if(component instanceof Fire) {
                        fireR = true;
                        openR = false;
                        break;
                    }
                }
            }
            // Up
            if(grid_y+1<Grid.grid_height && !tiles[grid_x][grid_y].getWallU()) {
                openU = true;
                int x = grid_x;
                int y = grid_y+1;
                for(Component component: tiles[x][y].getComponents()) {
                    if(component instanceof Fire) {
                        fireU = true;
                        openU = false;
                        break;
                    }
                }
            }
            
            //if all on fire or non on fire do nothing
            if(!fireU && !fireD && !fireL && !fireR) {
                return false;
            }
            
            int numOpen = (openU ? 1:0) + (openD ? 1:0) + (openL ? 1:0) + (openR ? 1:0);
            switch(numOpen){
                case 0:
                    return false;
                case 1:
                    tiles[grid_x][grid_y].getComponents().remove(human);
                    if(openU) tiles[grid_x][grid_y+1].getComponents().add(human);
                    else if(openD) tiles[grid_x][grid_y-1].getComponents().add(human);
                    else if(openL) tiles[grid_x-1][grid_y].getComponents().add(human);
                    else if(openR) tiles[grid_x+1][grid_y].getComponents().add(human);
                    return true;
                case 2:
                    tiles[grid_x][grid_y].getComponents().remove(human);
                    if(openU) tiles[grid_x][grid_y+1].getComponents().add(human);
                    else if(openD) tiles[grid_x][grid_y-1].getComponents().add(human);
                    else if(openL) tiles[grid_x-1][grid_y].getComponents().add(human);
                    else if(openR) tiles[grid_x+1][grid_y].getComponents().add(human);
                    return true;
                case 3:
                    tiles[grid_x][grid_y].getComponents().remove(human);
                    if(fireD) tiles[grid_x][grid_y+1].getComponents().add(human);
                    if(fireU) tiles[grid_x][grid_y-1].getComponents().add(human);
                    if(fireR) tiles[grid_x-1][grid_y].getComponents().add(human);
                    if(fireL) tiles[grid_x+1][grid_y].getComponents().add(human);
                    return true;
            }
        }
        //There exist an exit
        
        
        return false;//
        
    }
*/
    //given a list x,y coordinates to follow simulate path
/*    public boolean tracePath(HumanAgent human, Path path){
        Tile tiles[][] = grid.getTiles();
        
        
        tiles[path.getX(1)][grid_y].getComponents().remove(human);
        tiles[next.x][next.y].getComponents().add(human);
        return false;
    }
    
*/
    public void tracePath(HumanAgent human, int x, int y) {
        Tile tiles[][] = grid.getTiles();
        human.finder = new AStarPathFinder(grid, 500, false);
        human.path = human.finder.findPath(x, y, 1, 1);
        HumanAgent newHuman = new HumanAgent();
        if (human.path != null) {
            grid.addComponent(newHuman, human.path.getX(1), human.path.getY(1));
            tiles[x][y].getComponents().remove(human);
        }
 
    }
}
