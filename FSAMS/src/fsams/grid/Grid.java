package fsams.grid;

import fsams.pathfinding.AStarPathFinder;
import fsams.pathfinding.Path;
import fsams.pathfinding.TileBasedMap;

/**
 * The Grid class stores information about the building in a 2D grid of tiles.
 * Each tile is specified by its column, x, and row, y, and keeps track of what
 * kind of components are in the tile and whether or not the tile has walls
 * and doors.
 * @author FSAMS Team
 */
public class Grid implements TileBasedMap {

    /**
     * The number of columns in the grid.
     */
    public static final int grid_width = 100;
    
    /**
     * The number of rows in the grid.
     */
    public static final int grid_height = 100;
    
    private final Tile tiles[][];
    private final boolean[][] visited = new boolean[grid_width][grid_height];;
    private AStarPathFinder finder;

    /**
     * Constructs a new empty grid.
     */
    public Grid() {
        tiles = new Tile[grid_width][grid_height];
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                tiles[grid_x][grid_y] = new Tile(grid_x, grid_y);
            }
        }
    }

    /**
     * Constructs a new grid by copying the specified grid.
     * @param grid The grid to copy.
     */
    public Grid(Grid grid) {
        tiles = new Tile[grid_width][grid_height];
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                tiles[grid_x][grid_y] = new Tile(grid.getTiles()[grid_x][grid_y]);
            }
        }
    }

    @Override
    public int getWidthInTiles() {
        return grid_width;
    }

    @Override
    public int getHeightInTiles() {
        return grid_height;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        visited[x][y] = true;
    }
    
    //0 up 1 down 2 left 3 right
    @Override
    public boolean wallBlocked(int x, int y, int direction) {
        boolean up, down, left, right;
        
        Tile t = tiles[x][y];
        
        up = t.getWallU() || t.getLockU();
        down = t.getWallD() || t.getLockD();
        left = t.getWallL() || t.getLockL();
        right = t.getWallR() || t.getLockR();
        
        if (direction == 0 && down)
            return true;
        else if (direction == 1 && up)
            return true;
        else if (direction == 2 && right)
            return true;
        else if (direction == 3 && left)
            return true;
        
        return false;
        
    }
    
    @Override
    public boolean blocked(int x, int y) {
        if(tiles[x][y].getFire() || tiles[x][y].getHumanAgent() || tiles[x][y].getEquipment()
                || tiles[x][y].getIntruder()) {
            return true;
        }
        return false;    
    }

    @Override
    public float getCost(int sx, int sy, int tx, int ty) {
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if ((x == 0) && (y == 0)) {
                    continue;
                }
                if ((x != 0) && (y != 0)) {
                    continue;
                }      
                if((tx + x >=0) && (tx + x < grid_width) && (ty + y >= 0) && (ty + y < grid_height)) {
                    if (tiles[tx + x][ty + y].getFire()) {
                        return 5;
                    }
                }
            }
        }
        return 1;
    }
    
    /**
     * Adds the specified component to the tile at the specified coordinates.
     * @param comp The type of component to add.
     * @param x The column of the tile.
     * @param y The row of the tile.
     */
    public void addComponent(ComponentType comp, int x, int y) {
        if(x<0 || grid_width<=x || y<0 || grid_height<=y)
            throw new IllegalArgumentException("Illegal grid position: "+x+","+y);
        Tile t = tiles[x][y];
        switch(comp) {
            case Exit:       
                t.setExit(true); 
                break;
            case HumanAgent: 
                t.setHumanAgent(true); 
                break;
            case Fire:
                t.setFire(true);
                break;
            case FireSensor: 
                t.setFireSensor(true);
                break;
            case Sprinkler: 
                t.setSprinkler(true); 
                break;
            case FireAlarm:
                t.setFireAlarm(true);
                break;
            case Equipment:
                t.setEquipment(true);
                break;
            case Intruder:
                t.setIntruder(true);
                break;
            case Camera:
                t.setCamera(true);
                break;
        }
    }
    
    /**
     * Removes all components from the specified tile.
     * @param x The column of the tile.
     * @param y The row of the tile.
     */
    public void removeComponents(int x, int y) {
        if(x<0 || grid_width<=x || y<0 || grid_height<=y)
            throw new IllegalArgumentException("Illegal grid position: "+x+","+y);
        Tile t = tiles[x][y];
        t.setExit(false);
        t.setFire(false);
        t.setFireSensor(false);
        t.setHumanAgent(false);
        t.setSprinkler(false);
        t.setFireAlarm(false);
        t.setEquipment(false);
        t.setHumanAgent(false);
        t.setIntruder(false);
        t.setCamera(false);
    }
    
    /**
     * Adds a wall at the specified grid coordinates.
     * The location of the wall is given by two tile corner coordinates (x1,y1) 
     * and (x2,y2). Note that smallest corner coordinate of a tile is in its
     * lower left corner.
     * @param x1 The column of the first corner.
     * @param y1 The row of the second corner.
     * @param x2 The column of the first corner.
     * @param y2 The row of the second corner.
     */
    public void addWall(int x1, int y1, int x2, int y2) {
        if((x1!=x2) && (y2!=y2))
            throw new IllegalArgumentException("Walls must be horizontal or vertical");
        if(x1<0 || grid_width<x1 || y1<0 || grid_height<y1)
            throw new IllegalArgumentException("Illegal grid position: "+x1+","+y1);
        if(x2<0 || grid_width<x2 || y2<0 || grid_height<y2)
            throw new IllegalArgumentException("Illegal grid position: "+x2+","+y2);
        if(x1==x2 && y1==y2)
            return;
        // Verticle
        if(x1==x2) {
            for(int y=Math.min(y1,y2); y<Math.max(y1,y2); y++) {
                // Left tile
                if(x1-1>=0) {
                    tiles[x1-1][y].setWallR(true);
                }
                // Right tile
                if(x1<grid_width) {
                    tiles[x1][y].setWallL(true);
                }
            }
        }
        // Horizontal
        else {
            for(int x=Math.min(x1,x2); x<Math.max(x1,x2); x++) {
                // Bottom tile
                if(y1-1>=0) {
                    tiles[x][y1-1].setWallU(true);
                }
                // Top Tile
                if(y2<grid_width) {
                    tiles[x][y1].setWallD(true);
                }
            }
        }
    }
    
    /**
     * Adds a door at the specified grid coordinates.
     * The location of the door  is given by two tile corner coordinates (x1,y1) 
     * and (x2,y2). Note that smallest corner coordinate of a tile is in its
     * lower left corner.
     * @param x1 The column of the first corner.
     * @param y1 The row of the second corner.
     * @param x2 The column of the first corner.
     * @param y2 The row of the second corner.
     */
    public void addDoor(int x1, int y1, int x2, int y2) {
        if((x1!=x2) && (y2!=y2))
            throw new IllegalArgumentException("Walls must be horizontal or vertical");
        if(x1<0 || grid_width<x1 || y1<0 || grid_height<y1)
            throw new IllegalArgumentException("Illegal grid position: "+x1+","+y1);
        if(x2<0 || grid_width<x2 || y2<0 || grid_height<y2)
            throw new IllegalArgumentException("Illegal grid position: "+x2+","+y2);
        if(x1==x2 && y1==y2)
            return;
        // Verticle
        if(x1==x2) {
            for(int y=Math.min(y1,y2); y<Math.max(y1,y2); y++) {
                // Left tile
                if(x1-1>=0) {
                    tiles[x1-1][y].setDoorR(true);
                }
                // Right tile
                if(x1<grid_width) {
                    tiles[x1][y].setDoorL(true);
                }
            }
        }
        // Horizontal
        else {
            for(int x=Math.min(x1,x2); x<Math.max(x1,x2); x++) {
                // Bottom tile
                if(y1-1>=0) {
                    tiles[x][y1-1].setDoorU(true);
                }
                // Top Tile
                if(y2<grid_width) {
                    tiles[x][y1].setDoorD(true);
                }
            }
        }
    }

    /**
     * Removes any walls at the specified grid coordinates.
     * The location of the wall is given by two tile corner coordinates (x1,y1) 
     * and (x2,y2). Note that smallest corner coordinate of a tile is in its
     * lower left corner.
     * @param x1 The column of the first corner.
     * @param y1 The row of the second corner.
     * @param x2 The column of the first corner.
     * @param y2 The row of the second corner.
     */
    public void removeWall(int x1, int y1, int x2, int y2) {
        if((x1!=x2) && (y2!=y2))
            throw new IllegalArgumentException("Walls must be horizontal or vertical");
        if(x1<0 || grid_width<x1 || y1<0 || grid_height<y1)
            throw new IllegalArgumentException("Illegal grid position: "+x1+","+y1);
        if(x2<0 || grid_width<x2 || y2<0 || grid_height<y2)
            throw new IllegalArgumentException("Illegal grid position: "+x2+","+y2);
        if(x1==x2 && y1==y2)
            return;
        // Verticle
        if(x1==x2) {
            for(int y=Math.min(y1,y2); y<Math.max(y1,y2); y++) {
                // Left tile
                if(x1-1>=0) {
                    tiles[x1-1][y].setWallR(false);
                }
                // Right tile
                if(x1<grid_width) {
                    tiles[x1][y].setWallL(false);
                }
            }
        }
        // Horizontal
        else {
            for(int x=Math.min(x1,x2); x<Math.max(x1,x2); x++) {
                // Bottom tile
                if(y1-1>=0) {
                    tiles[x][y1-1].setWallU(false);
                }
                // Top Tile
                if(y2<grid_width) {
                    tiles[x][y1].setWallD(false);
                }
            }
        }
    }
    
    /**
     * Removes any doors at the specified grid coordinates.
     * The location of the door  is given by two tile corner coordinates (x1,y1) 
     * and (x2,y2). Note that smallest corner coordinate of a tile is in its
     * lower left corner.
     * @param x1 The column of the first corner.
     * @param y1 The row of the second corner.
     * @param x2 The column of the first corner.
     * @param y2 The row of the second corner.
     */
    public void removeDoor(int x1, int y1, int x2, int y2) {
        if((x1!=x2) && (y2!=y2))
            throw new IllegalArgumentException("Walls must be horizontal or vertical");
        if(x1<0 || grid_width<x1 || y1<0 || grid_height<y1)
            throw new IllegalArgumentException("Illegal grid position: "+x1+","+y1);
        if(x2<0 || grid_width<x2 || y2<0 || grid_height<y2)
            throw new IllegalArgumentException("Illegal grid position: "+x2+","+y2);
        if(x1==x2 && y1==y2)
            return;
        // Verticle
        if(x1==x2) {
            for(int y=Math.min(y1,y2); y<Math.max(y1,y2); y++) {
                // Left tile
                if(x1-1>=0) {
                    tiles[x1-1][y].setDoorR(false);
                }
                // Right tile
                if(x1<grid_width) {
                    tiles[x1][y].setDoorL(false);
                }
            }
        }
        // Horizontal
        else {
            for(int x=Math.min(x1,x2); x<Math.max(x1,x2); x++) {
                // Bottom tile
                if(y1-1>=0) {
                    tiles[x][y1-1].setDoorU(false);
                }
                // Top Tile
                if(y2<grid_width) {
                    tiles[x][y1].setDoorD(false);
                }
            }
        }
    }
    
    /**
     * Sets all of the doors in the grid to be either locked or unlocked.
     * @param locked If true, all doors will be locked, otherwise unlocked.
     */
    public void setLockAll(boolean locked) {
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                Tile tile = tiles[grid_x][grid_y];
                if(tile.getDoorD()) tile.setLockD(locked);
                if(tile.getDoorU()) tile.setLockU(locked);
                if(tile.getDoorL()) tile.setLockL(locked);
                if(tile.getDoorR()) tile.setLockR(locked);
            }
        }
    }

    /**
     * Turns on or off all of the sprinklers in the grid.
     * @param active If true, all sprinklers will be turned on, otherwise off.
     */
    public void setSuppressionAll(boolean active) {
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                Tile tile = tiles[grid_x][grid_y];
                if(tile.getSprinkler()) {
                    tile.setSuppressorActive(active);
                }
            }   
        }
    }

    /**
     * Turns on or off all of the alarms in the grid.
     * @param active If true, all alarms will be turned on, otherwise off.
     */
    public void setAlarmAll(boolean active) {
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                Tile tile = tiles[grid_x][grid_y];
                if(tile.getFireAlarm()) {
                    tile.setFireAlarmActive(active);
                }
            }   
        }
    }

    /**
     * Turns on or off all of the equipment in the grid.
     * @param active If true, all equipment will be turned on, otherwise off.
     */
    public void setEquipmentAll(boolean active) {
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                Tile tile = tiles[grid_x][grid_y];
                if(tile.getEquipment()) {
                    tile.setEquipmentActive(active);
                }
            }   
        }
    }
    
    /**
     * Activates or deactivates all of the sensors in the grid.
     * Activating a sensor simulates that the sensor has detected a fire. 
     * @param active If true, all sensors will be activated, otherwise deactivated.
     */
    public void setSensorsAll(boolean active) {
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                Tile tile = tiles[grid_x][grid_y];
                if(tile.getFireSensor()) {
                    tile.setFireSensorActive(active);
                }
            }   
        }
    }
    
    /**
     * Returns a 2D array of every tiles in this grid.
     * @return A 2D array of every tiles in the grid.
     */
    public Tile[][] getTiles() {
        return tiles;
    }
}