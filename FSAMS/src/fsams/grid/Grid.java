package fsams.grid;

import fsams.pathfinding.TileBasedMap;

public class Grid implements TileBasedMap {
    public static final int grid_width = 20;
    public static final int grid_height = 20;
    
    private Tile tiles[][];
    private boolean[][] visited = new boolean[grid_width][grid_height];

    
    public Grid() {
        tiles = new Tile[grid_width][grid_height];
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                tiles[grid_x][grid_y] = new Tile(grid_x, grid_y);
            }
        }
    }
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
        if(tiles[x][y].getFire() || tiles[x][y].getHumanAgent() || tiles[x][y].getEquipment()) {
            return true;
        }
        return false;    
    }

    @Override
    public float getCost(int sx, int sy, int tx, int ty) {
        return 1;
    }
    
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
            case Suppressor: 
                t.setSuppressor(true); 
                break;
            case FireAlarm:
                t.setFireAlarm(true);
                break;
            case Equipment:
                t.setEquipment(true);
                break;
        }
    }
    
    public void removeComponents(int x, int y) {
        if(x<0 || grid_width<=x || y<0 || grid_height<=y)
            throw new IllegalArgumentException("Illegal grid position: "+x+","+y);
        Tile t = tiles[x][y];
        t.setExit(false);
        t.setFire(false);
        t.setFireSensor(false);
        t.setHumanAgent(false);
        t.setSuppressor(false);
        t.setFireAlarm(false);
        t.setEquipment(false);
    }
    
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
    public void setSuppressionAll(boolean active) {
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                Tile tile = tiles[grid_x][grid_y];
                if(tile.getSuppressor()) {
                    tile.setSuppressorActive(active);
                }
            }   
        }
    }
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
    
    public Tile[][] getTiles() {
        return tiles;
    }

}