/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fsams.grid;

import fsams.grid.Component.Fire;
import fsams.grid.Grid.Tile;
import fsams.pathfinding.Mover;
import fsams.pathfinding.TileBasedMap;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Hector
 */
public class GridMap implements TileBasedMap {

    private int HEIGHT;
    private int WIDTH;
    private Grid grid;
    private boolean[][] visited = new boolean[WIDTH][HEIGHT];
    
    public GridMap(Grid grid){
        HEIGHT = Grid.grid_height;
        WIDTH = Grid.grid_width;
        this.grid = grid;
    }
      
    @Override
    public int getWidthInTiles() {
        return WIDTH;
    }

    @Override
    public int getHeightInTiles() {
        return HEIGHT;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        visited[x][y] = true;
    }

    
    @Override
    public boolean blocked(Mover mover, int x, int y) {
        Tile tile = grid.getTiles()[x][y];
        for(Component component : tile.getComponents()) {
            if(component instanceof Fire){
                return true;
            }
        }
        return false;
    }
    
    //0 up, 1 down, 2 left, 3 right
    @Override
    public boolean wallBlocked(Mover mover, int x, int y, int direction) {
        Tile tile = grid.getTiles()[x][y];
        
        if(direction == 0 && tile.wallD)
            return true;
        else if(direction == 1 && tile.wallU)
            return true;
        else if(direction == 2 && tile.wallR)
            return true;
        else if(direction == 3 && tile.wallL)
            return true;
        return false;
    }

    @Override
    public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
        return 1;
    }
    
    public void clearVisited() {
        for (int x=0; x<getWidthInTiles(); x++) {
            for (int y=0; y<getHeightInTiles(); y++) {
                visited[x][y] = false;
            }
        }
    }
    
    public boolean visited(int x, int y) {
        return visited[x][y];
    }
}
