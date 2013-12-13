/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams;

import fsams.grid.*;
import static fsams.grid.ComponentType.Camera;
import fsams.gui.TimerPanel;
import fsams.pathfinding.AStarPathFinder;
import fsams.pathfinding.Path;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import static javax.swing.JComponent.TOOL_TIP_TEXT_KEY;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Handles all of the logic for FSAMS
 * @author FSAMS Team
 */
public class Simulation extends Thread{
    // Simulation State Flags
    private boolean timerUsed;
    private boolean startFlag;
    private boolean isProgRunning;
    private boolean isSimRunning;
    private AStarPathFinder finder;
    private Path path;
    // Data Model
    private Grid grid;
    // GUI Component
    private final JPanel panel;
    private TimerPanel timerP;
    private ArrayList<Tile> exits;
    private ArrayList<Tile> cameras;
    private ArrayList<Tile> equipments;
    private boolean cameraSees;
    
    /**
     * Constructs a new Simulation in the specified panel.
     * @param panel the panel used to display the Simulation.
     */
    public Simulation(JPanel panel) {
        this.panel = panel;
        startFlag = false;
        isProgRunning = true;
        isSimRunning = false;
        timerUsed = false;
    }
    
    protected void setTimerP(TimerPanel timerP) {
        this.timerP = timerP;
    }
    
    /**
     * Checks to see if Simulation is running.
     * @return true if Simulation is running.
     */
    public boolean isSimRunning(){
        return isSimRunning;
    }
    
    /**
     * Displays a default message when no action has been taken before the timer has run out.
     */
    public void timeOut() {
        setSuppressionAll(true);
        setAlarmAll(true);
        setEquipmentAll(false);
        JOptionPane.showMessageDialog(panel, "Emergency services have been notified.\n"
                + "All alarms have been activated.\n"
                + "All sprinklers are engaged.\n"
                + "All equipment has been shutdown.");
    }

    /**
     * Starts the simulation.
     * @param grid the grid to be used for the Simulation.
     */
    public void startSim(Grid grid) {
        
        startFlag = !isSimRunning;
        if(startFlag) {
            timerUsed = false;
            System.out.println("hello I'm starting");
            Tile[][] tiles = grid.getTiles();
            exits = new ArrayList<>();
            cameras = new ArrayList<>();
            equipments = new ArrayList<>();
            for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                    Tile tile = tiles[grid_x][grid_y];
                    if(tile.getExit()) {
                        exits.add(tile);                    
                    }
                    if(tile.getCamera()) {
                        cameras.add(tile);                    
                    }
                    if(tile.getEquipment()) {
                        equipments.add(tile);
                    }
                }
            }
            for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                    tiles[grid_x][grid_y].reset();
                }
            }
            this.grid = grid;
            finder = new AStarPathFinder(grid, 5000, false);
        }
    }
    
    /**
     * Stops the simulation
     */
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
                    //System.out.println(currTime);
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
                        simCamera();
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
                                if(tile.getSprinkler()) {
                                    simSuppressor(grid_x, grid_y, elapTime, true);
                                }
                                if(tile.getIntruder()){
                                    simIntruder(grid_x,grid_y,elapTime,currTime);
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
    
/*
IF left adjacent tile is open
    spread to top tile
IF bottom adjacent tile is open
    spread to bottom tile
IF right adjacent tile is open
    spread to right tile
IF top adjacent tile is open
    spread to top tile
*/
    private void simFire(int grid_x, int grid_y, double elapTime) {
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

/*
stochastically add fire to a tile
if the tile has a human agent
    destroy the human agent
if the tile has an intruder
    destroy the intruder
*/
    private void simBurnTile(int grid_x, int grid_y, double elapTime) {
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
            if(tile.getIntruder()) {
                tile.setIntruder(false);
                tile.setIntruderFleeing(false);
                System.out.println("Congratulations! Intruder is no longer a threat.");
            }
            tile.setFire(true);
        }
    }
    
/*
if there is at least one entry in a list of exits
    set lowest cost to get to an ext to maximum value
    for each entry in the list of exits
        calculate the cost to get to that exit
        if this cost is lower than the lowest cost
            set lowest cost to this new cost
    return the exit with the lowest cost
if there are no entries in a list of exits
    return null
    
*/
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
 
/*
if there is at least one entry in a list of exits
    set lowest cost to get to an exit to maximum value
    for each entry in the list of exits
        for each tile adjacent tile to this entry
            calculate the cost to get to that tile
            if this cost is lower than the lowest cost
                set lowest cost to this new cost
    return the tile with the lowest cost
if there are no entries in a list of exits
    return null    
*/
    Tile closestEquipment(int grid_x, int grid_y) {
        if(!equipments.isEmpty()) {
            int index = -1;
            float lowestCost = Float.MAX_VALUE;
            int xIndex = 0;
            int yIndex = 0;
            for(Tile equipment : equipments) {
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        if ((x == 0) && (y == 0)) {
                            continue;
                        }
                        if ((x != 0) && (y != 0)) {
                            continue;
                        }      
                        if((equipment.grid_x + x >=0) && (equipment.grid_x + x < Grid.grid_width) && (equipment.grid_y + y >= 0) && (equipment.grid_y + y < Grid.grid_height)) {
                            Path path = finder.findPath(grid_x, grid_y, equipment.grid_x + x, equipment.grid_y + y);
                            if (path == null) {
                                continue;
                            }
                            float cost = path.getLength();
                            if(cost < lowestCost) {
                                index = equipments.indexOf(equipment);
                                lowestCost = cost;
                                xIndex = equipment.grid_x + x;
                                yIndex = equipment.grid_y + y;
                            }
                        }
                    }
                }
                if (index == -1) {
                    return null;
                }
            }
            Tile tile = new Tile(grid.getTiles()[xIndex][yIndex]);
            return tile;
            
        }
        return null;
    }
            
/*
if an intruder has not yet visited equipment
    if there is at least one entry in a list of equipment
        set lowest cost to get to an equipment to maximum value
        for each entry in the list of equipment
            for each tile adjacent tile to this entry
                calculate the cost to get to that tile
                if this cost is lower than the lowest cost
                    set lowest cost to this new cost
        return the tile with the lowest cost
    if there are no entries in a list of exits
        return null
if an intruder has already visited equipment
    if there is at least one entry in a list of exits
        set lowest cost to get to an ext to maximum value
        for each entry in the list of exits
            for each tile adjacent tile to this entry
                calculate the cost to get to that tile
                if this cost is lower than the lowest cost
                    set lowest cost to this new cost
        return the tile with the lowest cost
    if there are no entries in a list of exits
        return null
*/   
    private void simIntruder(int grid_x, int grid_y, double elapTime, long currTime) {
        Tile tiles[][] = grid.getTiles();
        final double speed = 1; //Tiles per second
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if ((x == 0) && (y == 0)) {
                    continue;
                }
                if ((x != 0) && (y != 0)) {
                    continue;
                }
                if((grid_x + x >=0) && (grid_x + x < Grid.grid_width) && (grid_y + y >= 0) && (grid_y + y < Grid.grid_height)) {
                    if (tiles[grid_x + x][grid_y + y].getEquipment()) {
                        tiles[grid_x][grid_y].setIntruderFleeing(true);
                    }
                }
            }
        }
        if((currTime - tiles[grid_x][grid_y].getLastMoveTime()) < 1000.0/speed)
            return;
        if (!tiles[grid_x][grid_y].getIntruderFleeing()) {
            Tile closestEquipment = closestEquipment(grid_x, grid_y);
                //locate closest equipment first if any, else path = null
            path = null;
            if(closestEquipment != null) {
                    path = finder.findPath(grid_x, grid_y, closestEquipment.grid_x, closestEquipment.grid_y);
            }
                //There exist a path to an exit
            if (path != null) {
                moveIntruder(grid_x, grid_y, path.getX(1), path.getY(1), currTime, tiles);
            }
        }
        else {
            Tile exit = closestExit(grid_x, grid_y);
            //locate closest exits first if any, else path = null
            path = null;
            if(exit!=null) {
                path = finder.findPath(grid_x, grid_y, exit.grid_x, exit.grid_y);
            }
            //There exist a path to an exit
            if (path != null) {
                moveIntruder(grid_x, grid_y, path.getX(1), path.getY(1), currTime, tiles);
            }
            //cannot reach exit - no exit in building
        }
    }

/*
if a human agent is near a fire, activated alarm, active suppressor, or another running human agent
    set the first human agent to start running
if a human agent is set to running
    find a path to the closest exit
    if there is no path available to an exit
        if fire spreads to the tile above the human agent
            move down
        if fire spreads to the tile to the left of the human agent
            move right
        if fire spreads to the tile to the right of the human agent
            move left
        if fire spreads to the tile below the human agent
            move up
*/        
    private void simHumanAgent(int grid_x, int grid_y, double elapTime, long currTime) {
        Tile tiles[][] = grid.getTiles();
        final double speed = 1; //Tiles per second
        
        if((currTime - tiles[grid_x][grid_y].getLastMoveTime()) < 1000.0/speed)
            return;

        final int activationRadius = 5;
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
                if(0<=x && x<Grid.grid_width && 0<=y && y<Grid.grid_height
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
                if(0<=x && x<Grid.grid_width && 0<=y && y<Grid.grid_height
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
                if(0<=x && x<Grid.grid_width && 0<=y && y<Grid.grid_height
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
                if(0<=x && x<Grid.grid_width && 0<=y && y<Grid.grid_height
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

/*
if the tile the intruder is moving to does not contain an exit
    add new intruder to the grid
    set new tile to contain an intruder
    if the intruder was fleeing
        set new tile intruder to fleeing
remove intruder from old tile
remove fleeing boolean from old tile
set the time since last move to 0 in old tile
*/    
    private void moveIntruder(int oldX, int oldY, int newX, int newY, long currTime, Tile tiles[][]){
        if(!tiles[newX][newY].getExit()) {
            grid.addComponent(ComponentType.Intruder, newX, newY);
            tiles[newX][newY].setIntruderFleeing(tiles[oldX][oldY].getIntruderFleeing());
            tiles[newX][newY].setLastMoveTime(currTime);
        }
        tiles[oldX][oldY].setIntruderFleeing(false);
        tiles[oldX][oldY].setIntruder(false);//use new method
        tiles[oldX][oldY].setLastMoveTime(0);
    }
    
/*
if the tile the human agent is moving to does not contain an exit
    add new human agent to the grid
    set new tile to contain a human agent
    flag new tile to have human agent active
remove intruder from old tile
remove fleeing boolean from old tile
set the time since last move to 0 in old tile
*/
    private void moveHumanAgent(int oldX, int oldY, int newX, int newY, long currTime, Tile tiles[][]){
        if(!tiles[newX][newY].getExit()) {
            grid.addComponent(ComponentType.HumanAgent, newX, newY);
            tiles[newX][newY].setHumanAgentActive(true);
            tiles[newX][newY].setLastMoveTime(currTime);
        }
        tiles[oldX][oldY].setHumanAgentActive(false);
        tiles[oldX][oldY].setHumanAgent(false);//use new method
        tiles[oldX][oldY].setLastMoveTime(0);
    }
    
/*
for each tile within 3 tiles of the sensor
    if the tiles are within the grid
        if a tile has a fire
            call actions for fire detected
            start timer
*/
    private void simFireSensor(int grid_x, int grid_y, double elapTime) {
        final int sensorRadius = 3;
        for (int x = grid_x - sensorRadius; x <= grid_x + sensorRadius; x++) {
            for (int y = grid_y - sensorRadius; y <= grid_y + sensorRadius; y++) {
                if((x >=0) && (x < Grid.grid_width) && (y >= 0) && (y < Grid.grid_height)) {
                    if (grid.getTiles()[x][y].getFire()) {
                        fireDetected(grid_x, grid_y);
                        if(!timerUsed){
                            timerP.start();
                            timerUsed = true;
                        }                        
                    }
                }
            }
        }
    }
    
    private void simCamera() {
        final int sensorRadius = 3;
        boolean sees = false;
        for (Tile camera : cameras) {
            for (int x = camera.grid_x - sensorRadius; x <= camera.grid_x + sensorRadius; x++) {
                for (int y = camera.grid_y - sensorRadius; y <= camera.grid_y + sensorRadius; y++) {
                    if((x >=0) && (x < Grid.grid_width) && (y >= 0) && (y < Grid.grid_height)) {  
                        if ((grid.getTiles()[x][y].getHumanAgent()) || (grid.getTiles()[x][y].getIntruder())) {
                                sees = true;
                        }

                    }
                }
            }
        }
        cameraSees = sees;
    }
    
/*
for each tile in grid
    if the tile contains a fire alarm
        set alarm to activate
*/
    private void fireDetected(int sensorX, int sensorY) {
        for (int x = 0; x < grid.grid_width; x++) {
            for (int y = 0; y < grid.grid_height; y++) {
                if (grid.getTiles()[x][y].getFireAlarm()) {
                   grid.getTiles()[x][y].setFireAlarmActive(true);
                }
            }
        }
    }
/*
SET activationRadius
FOR each tile within the activation radius around the suppressor
    IF the tile has a fire
        SET the sprinkler to activate
SET suppression radius
IF the sprinker is active
    supress in all tiles within supression radius
    set fire in supressed tiles to false
*/
    private void simSuppressor(int grid_x, int grid_y, double elapTime, boolean turnOn){
        final int activationRadius = 1;
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

    /**
     * Sets all doors to be locked or unlocked.
     * @param locked true for locked, false for unlocked.
     */
    public void setLockAll(boolean locked) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setLockAll(locked);
            }
        }
    }

    /**
     * Sets all fire suppression to be active or inactive.
     * @param active true for active, false for inactive.
     */
    public void setSuppressionAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setSuppressionAll(active);
            }
        }
    }

    /**
     * Sets all alarms to be active or inactive
     * @param active true for active, false for inactive.
     */
    public void setAlarmAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setAlarmAll(active);
            }
        }
    }

    /**
     * Sets all equipment to be active or inactive.
     * @param active true for active, false for inactive.
     */
    public void setEquipmentAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setEquipmentAll(active);
            }
        }
    }

    /**
     * Sets all sensors to be active or inactive.
     * @param active true for active, false for inactive.
     */
    public void setSensorsAll(boolean active) {
        if(grid!=null) {
            synchronized(grid) {
                grid.setSensorsAll(active);
            }
        }
    }
    
    public boolean getCameraSees() {
        return cameraSees;
    }
    
    public void stopTimer() {
        timerP.stop();
    }
}
