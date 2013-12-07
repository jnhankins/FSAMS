/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams;

import fsams.grid.*;
import fsams.pathfinding.AStarPathFinder;
import fsams.pathfinding.Path;
import java.util.ArrayList;
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
    private final JPanel panel;
    private ArrayList<Tile> exits;
    
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
        if(startFlag) {
            System.out.println("hello I'm starting");
            Tile[][] tiles = grid.getTiles();
            exits = new ArrayList<>();
            for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                    Tile tile = tiles[grid_x][grid_y];
                    if(tile.getExit()) {
                        exits.add(tile);                    
                    }
                }
            }
            System.out.println("number of exits = " + exits.size());
            for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                    tiles[grid_x][grid_y].reset();
                }
            }
            this.grid = grid;
            finder = new AStarPathFinder(grid, 5000, false);
        }
    }
    
    public void stopSim(){
        isSimRunning = false;
        System.out.println("goodbye I'm stopping");
    }
    
    @Override
    public void run() {
        final int numFrameTimes = 10;
        long frameTimes[] = new long[numFrameTimes];
        int frameTimePos = 0;
        
        while(isProgRunning){
            if(startFlag){
                startFlag = false;
                isSimRunning = true;
                long lastTime = System.currentTimeMillis();
                while(isSimRunning){
                    long currTime = System.currentTimeMillis();
                    if((currTime-lastTime)<10) {
                        try {
                            Thread.sleep(10-(currTime-lastTime));
                        } catch (InterruptedException ex) {}
                        currTime = System.currentTimeMillis();
                    }
                    double elapTime = (currTime - lastTime)/1000.0;
                    if(false) { // show fps
                        frameTimes[frameTimePos] = currTime;
                        System.out.println(numFrameTimes*1000.0/(frameTimes[frameTimePos]-frameTimes[(frameTimePos+1)%numFrameTimes]));
                        frameTimePos = (frameTimePos+1)%numFrameTimes;
                    }
                    synchronized(grid) {
                        Tile[][] tiles = grid.getTiles();
                        // Update components
                        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                                // Get the tile
                                Tile tile = tiles[grid_x][grid_y];
                                // Get the components
                                if(tile.getFire()) {
                                    simFire(grid_x,grid_y,elapTime);
                                }
                                if(tile.getHumanAgent()){
                                    simHumanAgent(grid_x,grid_y,elapTime,currTime);
                                }
                                if (tile.getFireSensor()) {
                                    simFireSensor(grid_x, grid_y, elapTime);
                                }
                                if(tile.getSuppressor()) {
                                    simSuppressor(grid_x, grid_y, elapTime, true);
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
        Tile t = grid.getTiles()[grid_x][grid_y];
        // Left
        if(grid_x-1>=0 && !t.getWallL() && !t.getLockL()) {
            int x = grid_x-1;
            int y = grid_y;
            simBurnTile(x,y,elapTime);
        }
        // Down
        if(grid_y-1>=0 && !t.getWallD() && !t.getLockD()) {
            int x = grid_x;
            int y = grid_y-1;
            simBurnTile(x,y,elapTime);
        }
        // Right
        if(grid_x+1<Grid.grid_width && !t.getWallR() && !t.getLockR()) {
            int x = grid_x+1;
            int y = grid_y;
            simBurnTile(x,y,elapTime);
        }
        // Up
        if(grid_y+1<Grid.grid_height && !t.getWallU() && !t.getLockU()) {
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
            if(tile.getHumanAgent()) {
                tile.setHumanAgent(false);
                tile.setHumanAgentActive(false);
                System.out.println("Someone died (x_x)");
            }
            tile.setFire(true);
        }
    }
    
    //ideal is to find closest exit...for now just find one
    Tile closestExit(int grid_x, int grid_y) {
        if(!exits.isEmpty()) {
            int index = -1;
            float lowestCost = Float.MAX_VALUE;
            for(Tile exit : exits) {
                Path path = finder.findPath(grid_x, grid_y, exit.grid_x, exit.grid_y);
                if (path == null) {
                    continue;
                }
                float cost = path.getLength();
                System.out.println(cost);
                if(cost < lowestCost) {
                    index = exits.indexOf(exit);
                    lowestCost = cost;
                }
            }
            if (index == -1) {
                return null;
            }
            return exits.get(index);
        }
        return null; 
   }
    
    void simHumanAgent(int grid_x, int grid_y, double elapTime, long currTime) {
        Tile tiles[][] = grid.getTiles();
        final double speed = 1; //Tiles per second
        
        if((currTime - tiles[grid_x][grid_y].getLastMoveTime()) < 1000.0/speed)
            return;

        final int activationRadius = 2;
        for (int x = grid_x - activationRadius; x <= grid_x + activationRadius && !tiles[grid_x][grid_y].getHumanAgentActive(); x++) {
           for (int y = grid_y - activationRadius; y <= grid_y + activationRadius && !tiles[grid_x][grid_y].getHumanAgentActive(); y++) {
               if(x >=0 && x < Grid.grid_width && y >= 0 && y < Grid.grid_height) {
                   Tile t = grid.getTiles()[x][y];
                   if(t.getFire() || t.getFireAlarmActive() || t.getSuppressorActive() || t.getHumanAgentActive()) {
                       tiles[grid_x][grid_y].setHumanAgentActive(true);
                   }
               }
           }
        }
        
        if(tiles[grid_x][grid_y].getHumanAgentActive()) {
            Tile exit = closestExit(grid_x, grid_y);
            //locate closest exits first if any, else path = null
            path = null;
            if(exit!=null) {
                path = finder.findPath(grid_x, grid_y, exit.grid_x, exit.grid_y);
            }
            //There exist a path to an exit
            if (path != null) {
                moveHumanAgent(grid_x, grid_y, path.getX(1), path.getY(1), currTime, tiles);
            }
            //cannot reach exit - no exit in building
            else {
                boolean fireU, fireD, fireL, fireR;
                boolean openU, openD, openL, openR;
                fireU = fireD = fireL = fireR = false;
                openU = openD = openL = openR = false;

                Tile t = tiles[grid_x][grid_y];

                int x;
                int y;
                // Left
                x = grid_x-1;
                y = grid_y;
                if(0<=x && x<=Grid.grid_width && 0<=y && y<Grid.grid_height
                    && !t.getWallL() && !t.getLockL()
                    && !tiles[x][y].getEquipment() && !tiles[x][y].getHumanAgent()) {
                    openL = true;
                    if(tiles[x][y].getFire()) {
                        fireL = true;
                        openL = false;
                    }

                }
                // Down
                x = grid_x;
                y = grid_y-1;
                if(0<=x && x<=Grid.grid_width && 0<=y && y<Grid.grid_height
                    && !t.getWallD() && !t.getLockD()
                    && !tiles[x][y].getEquipment() && !tiles[x][y].getHumanAgent()) {
                    openD = true;
                    if(tiles[x][y].getFire()) {
                        fireD = true;
                        openD = false;
                    }
                }
                // Right
                x = grid_x+1;
                y = grid_y;
                if(0<=x && x<=Grid.grid_width && 0<=y && y<Grid.grid_height
                    && !t.getWallR() && !t.getLockR()
                    && !tiles[x][y].getEquipment() && !tiles[x][y].getHumanAgent()) {
                    openR = true;
                    if(tiles[x][y].getFire()) {
                        fireR = true;
                        openR = false;
                    }
                }
                // Up
                x = grid_x;
                y = grid_y+1;
                if(0<=x && x<=Grid.grid_width && 0<=y && y<Grid.grid_height
                    && !t.getWallU() && !t.getLockU()
                    && !tiles[x][y].getEquipment() && !tiles[x][y].getHumanAgent()) {
                    openU = true;
                    if(tiles[x][y].getFire()) {
                        fireU = true;
                        openU = false;
                    }
                }

                //if all on fire or non on fire do nothing
                if(!fireU && !fireD && !fireL && !fireR) {
                    return;
                }

                int numOpen = (openU ? 1:0) + (openD ? 1:0) + (openL ? 1:0) + (openR ? 1:0);
                switch(numOpen){
                    case 0:
                        return;
                    case 1:            
                        if(openU) moveHumanAgent(grid_x, grid_y, grid_x, grid_y+1, currTime, tiles);
                        else if(openD) moveHumanAgent(grid_x,grid_y,grid_x,grid_y-1, currTime, tiles);
                        else if(openL) moveHumanAgent(grid_x,grid_y,grid_x-1,grid_y, currTime, tiles);
                        else if(openR) moveHumanAgent(grid_x,grid_y,grid_x+1,grid_y, currTime, tiles);
                        return;
                    case 2:
                        if(openU) moveHumanAgent(grid_x,grid_y,grid_x,grid_y+1, currTime, tiles);
                        else if(openD) moveHumanAgent(grid_x,grid_y,grid_x,grid_y-1, currTime, tiles);
                        else if(openL) moveHumanAgent(grid_x,grid_y,grid_x-1,grid_y, currTime, tiles);
                        else if(openR) moveHumanAgent(grid_x,grid_y,grid_x+1,grid_y, currTime, tiles);
                        return;
                    case 3:
                        if(fireD) moveHumanAgent(grid_x,grid_y,grid_x,grid_y+1, currTime, tiles);
                        else if(fireU) moveHumanAgent(grid_x,grid_y,grid_x,grid_y-1, currTime, tiles);
                        else if(fireR) moveHumanAgent(grid_x,grid_y,grid_x-1,grid_y, currTime, tiles);
                        else if(fireL) moveHumanAgent(grid_x,grid_y,grid_x+1,grid_y, currTime, tiles);
                        return;
                }
            }
        }
    }

    public void moveHumanAgent(int oldX, int oldY, int newX, int newY, long currTime, Tile tiles[][]){
        if(!tiles[newX][newY].getExit()) {
            grid.addComponent(ComponentType.HumanAgent, newX, newY);
            tiles[newX][newY].setHumanAgentActive(true);
            tiles[newX][newY].setLastMoveTime(currTime);
        }
        tiles[oldX][oldY].setHumanAgentActive(false);
        tiles[oldX][oldY].setHumanAgent(false);//use new method
        tiles[oldX][oldY].setLastMoveTime(0);
    }
    
    public void simFireSensor(int grid_x, int grid_y, double elapTime) {
        final int sensorRadius = 3;
        for (int x = grid_x - sensorRadius; x <= grid_x + sensorRadius; x++) {
            for (int y = grid_y - sensorRadius; y <= grid_y + sensorRadius; y++) {
                if(x >=0 && x < Grid.grid_width && y >= 0 && y < Grid.grid_height) {
                    if (grid.getTiles()[x][y].getFire()) {
                        fireDetected(grid_x, grid_y);
                    }
                }
            }
        }
    }
    
    public void fireDetected(int sensorX, int sensorY) {
        System.out.println("Fire detected at (" + sensorX + ", " + sensorY + ")");
    }
    
    public void simSuppressor(int grid_x, int grid_y, double elapTime, boolean turnOn){
        final int activationRadius = 2;
        Tile[][] tiles = grid.getTiles();
        for (int x = grid_x - activationRadius; x <= grid_x + activationRadius && !tiles[grid_x][grid_y].getSuppressorActive(); ++x) {
            for (int y = grid_y - activationRadius; y <= grid_y + activationRadius && !tiles[grid_x][grid_y].getSuppressorActive(); ++y) {
                if (x >=0 && x < Grid.grid_width && y >= 0 && y < Grid.grid_height) {
                    if (tiles[x][y].getFire()) {
                        tiles[grid_x][grid_y].setSuppressorActive(true);
                    }
                }
            }
        }
        final int suppressionRadius = 2;
        if(tiles[grid_x][grid_y].getSuppressorActive()) {
            for (int x = grid_x - suppressionRadius; x <= grid_x + suppressionRadius; ++x) {
                for (int y = grid_y - suppressionRadius; y <= grid_y + suppressionRadius; ++y) {
                    if(x >=0 && x < Grid.grid_width && y >= 0 && y < Grid.grid_height) {
                        final double suppress_probability = 0.99;
                        final double suppress_timeframe = 0.25;
                        double distance = (x-grid_x)*(x-grid_x)+(y-grid_y)*(y-grid_y);
                        distance = distance*distance;
                        if(distance==0)
                            distance = 1;
                        double prob = 1 - Math.pow(1.0-suppress_probability,elapTime/suppress_timeframe); // p'=1-(1-p)^(t'/t)
                        prob = prob/distance;
                        
                        if(Math.random()<prob) {
                            grid.getTiles()[x][y].setFire(false);
                            grid.getTiles()[x][y].setSuppression(true);
                        }
                    }
                }
            }
        }
    }
    
    
    public void setLockAll(boolean locked) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setLockAll(locked);
            }
        }
    }
    public void setSuppressionAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setSuppressionAll(active);
            }
        }
    }
    public void setAlarmAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setAlarmAll(active);
            }
        }
    }
    public void setEquipmentAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setEquipmentAll(active);
            }
        }
    }
    public void setSensorsAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setSensorsAll(active);
            }
        }
    }
}
