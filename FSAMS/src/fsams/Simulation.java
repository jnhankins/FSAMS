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
import fsams.pathfinding.PathFinder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Simulation extends Thread{
    // Simulation State Flags
    private boolean startFlag;
    private boolean isProgRunning;
    private boolean isSimRunning;
    private AStarPathFinder finder;
    private Path path;
    // Data Model
    private Grid grid;
    // GUI Component
    private JPanel panel;
    private ArrayList<Exit> exits;
    
    public Simulation(JPanel panel) {
        this.panel = panel;
        startFlag = false;
        isProgRunning = true;
        isSimRunning = false;
        exits = new ArrayList<Exit>();
    }
    
    public boolean isSimRunning(){
        return isSimRunning;
    }

    public void startSim(Grid grid) {
        startFlag = !isSimRunning;
        System.out.println("hello I'm starting");
        Grid.Tile[][] tiles = grid.getTiles();
        // Draw components
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                Grid.Tile tile = tiles[grid_x][grid_y];
                if (tile.getExit()) {
                    Exit newExit = new Exit();
                    newExit.location_x = grid_x;
                    newExit.location_y = grid_y;
                    exits.add(newExit);                    
                }
            }
        }   
        System.out.println("number of exits = " + exits.size());
        if(!isSimRunning) {
            this.grid = grid;
            finder = new AStarPathFinder(grid, 500, false);

        }
    }
    
    public void stopSim(){
        isSimRunning = false;
        System.out.println("goodbye I'm stopping");
    }
    
    @Override
    public void run() {
        final int numFrameTimes = 100;
        long frameTimes[] = new long[numFrameTimes];
        int frameTimePos = 0;
        
        while(isProgRunning){
            if(startFlag){
                startFlag = false;
                isSimRunning = true;
                long lastTime = System.currentTimeMillis();
                while(isSimRunning){
                    long currTime = System.currentTimeMillis();
                    double elapTime = (currTime - lastTime)/1000.0;
                    if(elapTime<0.01){
                        try {
                            Thread.sleep((long)(1000*(0.01-elapTime)));
                        } catch (InterruptedException ex) {}
                        elapTime = (currTime - lastTime)/1000.0;
                    }
                    
                    if(false) { // show fps
                        frameTimes[frameTimePos] = currTime;
                        System.out.println(numFrameTimes*1000.0/(frameTimes[frameTimePos]-frameTimes[(frameTimePos+1)%numFrameTimes]));
                        frameTimePos = (frameTimePos+1)%numFrameTimes;
                    }
                    
                    synchronized(grid) {
                        Grid.Tile[][] tiles = grid.getTiles();
                        // Draw components
                        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                                // Get the tile
                                Grid.Tile tile = tiles[grid_x][grid_y];
                                // Get the components
                                    if(tile.getFire()) {
                                        simFire(grid_x,grid_y,elapTime);
                                    }
                                    else if(tile.getExit()){
                         //               Exit exit = (Exit)component;
                         //               System.out.println(exit.location_x + " " + exit.location_y);
                                    }
                                    else if(tile.getHumanAgent()){
                                        try {
                                            sleep(1000);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        //simHumanAgent(grid_x,grid_y,elapTime);
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
            if(tile.getFire()) {
                    return;
                }
                // TODO check for other types of components.
            
            tile.setFire(true);
        }
    }
    
    //ideal is to find closest exit...for now just find one
    Exit closestExit(int grid_x, int grid_y) {
        if (exits != null) {
            int index = 0;
            float lowestCost = finder.getMovementCost(grid_x, grid_y, exits.get(0).location_x, exits.get(0).location_y);
            for (Exit exit : exits) {
                float cost = finder.getMovementCost(grid_x, grid_y, exit.location_x, exit.location_y);
                if (cost < lowestCost) {
                    index = exits.indexOf(exit);
                    lowestCost = finder.getMovementCost(grid_x, grid_y, exit.location_x, exit.location_y);
                }
            }
            return exits.get(index);
        }
        return null; 
   }
    
    boolean simHumanAgent(int grid_x, int grid_y, double elapTime) {
        Tile tiles[][] = grid.getTiles();

        Exit exit = closestExit(grid_x, grid_y);
        //locate closest exits first if any, else path = null
        if(exits != null) {//fix
            path = finder.findPath(grid_x, grid_y, exit.location_x, exit.location_y);
            HumanAgent newHuman = new HumanAgent();
            //There exist a path to an exit
            if (path != null) {
                grid.addComponent(newHuman, path.getX(1), path.getY(1));
                tiles[grid_x][grid_y].setHumanAgent(false);
            }
        }
        //cannot reach exit - no exit in building
        else{
            boolean fireU, fireD, fireL, fireR;
            boolean openU, openD, openL, openR;
            fireU = fireD = fireL = fireR = false;
            openU = openD = openL = openR = false;
            // Left
            if(grid_x-1>=0 && !tiles[grid_x][grid_y].getWallL()) {
                openL = true;
                int x = grid_x-1;
                int y = grid_y;
                if(tiles[grid_x][grid_y].getFire()) {
                    fireL = true;
                    openL = false;
                }
                
            }
            // Down
            if(grid_y-1>=0 && !tiles[grid_x][grid_y].getWallD()) {
                openD = true;
                int x = grid_x;
                int y = grid_y-1;
                if(tiles[grid_x][grid_y].getFire()) {
                    fireD = true;
                    openD = false;
                }
                
            }
            // Right
            if(grid_x+1<Grid.grid_width && !tiles[grid_x][grid_y].getWallR()) {
                openR = true;
                int x = grid_x+1;
                int y = grid_y;
                if(tiles[grid_x][grid_y].getFire()) {
                    fireR = true;
                    openR = false;
                }
                
            }
            // Up
            if(grid_y+1<Grid.grid_height && !tiles[grid_x][grid_y].getWallU()) {
                openU = true;
                int x = grid_x;
                int y = grid_y+1;
                if(tiles[grid_x][grid_y].getFire()) {
                    fireU = true;
                    openU = false;
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
                    tiles[grid_x][grid_y].setHumanAgent(false);
                    if(openU) tiles[grid_x][grid_y+1].setHumanAgent(true);
                    else if(openD) tiles[grid_x][grid_y-1].setHumanAgent(true);
                    else if(openL) tiles[grid_x-1][grid_y].setHumanAgent(true);
                    else if(openR) tiles[grid_x+1][grid_y].setHumanAgent(true);
                    return true;
                case 2:
                    tiles[grid_x][grid_y].setHumanAgent(false);
                    if(openU) tiles[grid_x][grid_y+1].setHumanAgent(true);
                    else if(openD) tiles[grid_x][grid_y-1].setHumanAgent(true);
                    else if(openL) tiles[grid_x-1][grid_y].setHumanAgent(true);
                    else if(openR) tiles[grid_x+1][grid_y].setHumanAgent(true);
                    return true;
                case 3:
                    tiles[grid_x][grid_y].setHumanAgent(false);
                    if(fireD) tiles[grid_x][grid_y+1].setHumanAgent(true);
                    if(fireU) tiles[grid_x][grid_y-1].setHumanAgent(true);
                    if(fireR) tiles[grid_x-1][grid_y].setHumanAgent(true);
                    if(fireL) tiles[grid_x+1][grid_y].setHumanAgent(true);
                    return true;
            }
        }
        
        return false;
    }

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
        finder = new AStarPathFinder(grid, 500, false);
        path = finder.findPath(x, y, 1, 1);
        HumanAgent newHuman = new HumanAgent();
        if (path != null) {
            grid.addComponent(newHuman, path.getX(1), path.getY(1));
            tiles[x][y].setHumanAgent(false);
        }
    }
}
