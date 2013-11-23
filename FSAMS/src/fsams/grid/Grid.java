package fsams.grid;

import java.util.ArrayList;

public class Grid {
    public static final int grid_width = 20;
    public static final int grid_height = 20;
    
    private Tile tiles[][];
    
    
    public Grid() {
        tiles = new Tile[grid_width][grid_height];    
        for(int grid_x=0; grid_x<tiles.length; grid_x++) {
            for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                tiles[grid_x][grid_y] = new Tile();
            }
        }
    }
    
    public class Tile {
        ArrayList<Component> components;
        boolean wallU, wallD, wallL, wallR;
        
        public Tile() {
            components = new ArrayList<>();
            wallU = false;
            wallD = false;
            wallL = false;
            wallR = false;
        }
        
        public ArrayList<Component> getComponents() {
            return components;
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
    }
    
    public void addComponent(Component comp, int x, int y) {
        if(x<0 || grid_width<=x || y<0 || grid_height<=y)
            throw new IllegalArgumentException("Illegal grid position: "+x+","+y);
        Tile t = tiles[x][y];
        t.components.add(comp);
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