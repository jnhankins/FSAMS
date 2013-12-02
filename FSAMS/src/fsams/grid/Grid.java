package fsams.grid;

import fsams.grid.Component.*;
import fsams.pathfinding.TileBasedMap;
import java.util.ArrayList;

public class Grid implements TileBasedMap{
    public static final int grid_width = 20;
    public static final int grid_height = 20;
    
    private Tile tiles[][];
    private boolean[][] visited = new boolean[grid_width][grid_height];

    
    public Grid() {
        tiles = new Tile[grid_width][grid_height];
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                tiles[grid_x][grid_y] = new Tile();
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
        
        up = tiles[x][y].wallU;
        down = tiles[x][y].wallD;
        left = tiles[x][y].wallL;
        right = tiles[x][y].wallR;
        
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
        if (tiles[x][y].fire || tiles[x][y].humanAgent) {
            return true;
        }
    return false;    
    }

    @Override
    public float getCost(int sx, int sy, int tx, int ty) {
        return 1;
    }
    
    public class Tile {
        //TODO replace ArrayList with boolean values
        boolean wallU, wallD, wallL, wallR;
        boolean fire, fireSensor, humanAgent,exit, suppressor;
        
        public Tile() {
            wallU = false;
            wallD = false;
            wallL = false;
            wallR = false;
            fire = false;
            fireSensor = false;
            humanAgent = false;
            exit = false;
            suppressor = false;
        }
        public Tile(Tile tile) {
            wallU = tile.wallU;
            wallD = tile.wallD;
            wallL = tile.wallL;
            wallR = tile.wallR;
            fire = tile.fire;
            fireSensor = tile.fireSensor;
            humanAgent = tile.humanAgent;
            exit = tile.exit;
            suppressor = tile.suppressor;
        }
        
        public boolean getWallU() {
            return wallU;
        }
        public boolean getWallD() {
            return wallD;
        }
        public boolean getWallL() {
            return wallL;
        }
        public boolean getWallR() {
            return wallR;
        }

        public boolean getFire() {
            return fire;
        }
        public void setFire(boolean status) {
            fire = status;
        }
        
        public boolean getFireSensor() {
            return fireSensor;
        }
        
        public boolean getExit() {
            return exit;
        }

        public boolean getHumanAgent() {
            return humanAgent;
        }
        
        public void setHumanAgent(boolean status) {
            humanAgent = status;
        }
        
        public boolean getSuppressor() {
            return suppressor;
        }
        
    }
    
    public void addComponent(Component comp, int x, int y) {
        if(x<0 || grid_width<=x || y<0 || grid_height<=y)
            throw new IllegalArgumentException("Illegal grid position: "+x+","+y);
        Tile t = tiles[x][y];
        if (comp instanceof Exit) {
            tiles[x][y].exit = true;
        }
        
        else if (comp instanceof HumanAgent) {
            tiles[x][y].humanAgent = true;
        }
        else if (comp instanceof Fire) {
            tiles[x][y].fire = true;
        }
        else if (comp instanceof FireSensor) {
            tiles[x][y].fireSensor = true;
        }
        else if (comp instanceof Suppressor) {
            tiles[x][y].suppressor = true;
        }
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
                    tiles[x1-1][y].wallR = true;
                }
                // Right tile
                if(x1<grid_width) {
                    tiles[x1][y].wallL = true;
                }
            }
        }
        // Horizontal
        else {
            for(int x=Math.min(x1,x2); x<Math.max(x1,x2); x++) {
                // Bottom tile
                if(y1-1>=0) {
                    tiles[x][y1-1].wallU = true;
                }
                // Top Tile
                if(y2<grid_width) {
                    tiles[x][y1].wallD = true;
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
                    tiles[x1-1][y].wallR = false;
                }
                // Right tile
                if(x1<grid_width) {
                    tiles[x1][y].wallL = false;
                }
            }
        }
        // Horizontal
        else {
            for(int x=Math.min(x1,x2); x<Math.max(x1,x2); x++) {
                // Bottom tile
                if(y1-1>=0) {
                    tiles[x][y1-1].wallU = false;
                }
                // Top Tile
                if(y2<grid_width) {
                    tiles[x][y1].wallD = false;
                }
            }
        }
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }

}