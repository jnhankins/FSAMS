package fsams.grid;

import fsams.grid.Component.*;
import fsams.pathfinding.AStarPathFinder;
import fsams.pathfinding.Mover;
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
        for (Component c : tiles[x][y].getComponents()) {
            if (c instanceof Fire) {
                return true;
            }
        }
        return false;    
    }

    @Override
    public float getCost(int sx, int sy, int tx, int ty) {
        return 1;
    }
    
    public class Tile {
        //TODO replace ArrayList with boolean values
        ArrayList<Component> components;
        boolean wallU, wallD, wallL, wallR;
        
        public Tile() {
            components = new ArrayList<>();
            wallU = false;
            wallD = false;
            wallL = false;
            wallR = false;
        }
        public Tile(Tile tile) {
            components = new ArrayList<>();
            for(Component component: tile.getComponents()) {
                if(component instanceof Sensor) {
                    components.add(new Sensor());
                } else if(component instanceof Fire) {
                    components.add(new Fire());
                } else if(component instanceof HumanAgent){
                    components.add(new HumanAgent());
                } else if(component instanceof Exit){
                    components.add(new Exit());
                } else if(component instanceof Suppressor){
					components.add(new Suppressor());
				}
            }
            wallU = tile.wallU;
            wallD = tile.wallD;
            wallL = tile.wallL;
            wallR = tile.wallR;
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
        if (comp instanceof HumanAgent) {
            comp.finder = new AStarPathFinder(this, 500, false);
            comp.path = comp.finder.findPath(x, y, 1, 1);
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