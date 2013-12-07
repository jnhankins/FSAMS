package fsams.gui;

import fsams.FSAMS;
import fsams.grid.ComponentType;
import fsams.grid.Grid;
import fsams.grid.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class EditPanel extends JPanel implements MouseListener {
    private final FSAMS fsams;
    private double centerX;
    private double centerY;
    private double scale;
    
    private Grid grid;
    
    private ComponentType nextComponentType;
    //private boolean justSelected;
    //private int justSelectedX;
    //private int justSelectedY;
    
    public EditPanel(FSAMS fsams) {
        this.fsams = fsams;
        centerX = 0;
        centerY = 0;
        scale = 50.0;
        nextComponentType = null;
        addMouseListener(this);
    }
    
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    public void setNextComponentType(ComponentType type) {
        nextComponentType = type;
    }
    
    int toScreenXfromGridX(int grid_x, int grid_width) {
        return (int)(grid_x*scale+getWidth()/2.0-centerX-grid_width*scale/2.0);
    }
    int toScreenYfromGridY(int grid_y, int grid_height) {
        return (int)(-grid_y*scale+getHeight()/2.0-centerY+grid_height*scale/2.0);
    }
    int toGridXfromScreenX(int x, int grid_width) {
        return (int)((x+grid_width*scale/2.0+centerX-getWidth()/2.0)/scale);
    }
    int toGridYfromScreenY(int y, int grid_height) {
        return (int)((y-getHeight()/2.0+centerY-grid_height*scale/2.0)/(-scale));
    }
    
    @Override
    public void paint(Graphics g) {
        synchronized(grid) {
            // Clear the drawing area
            g.setColor(Color.black);
            g.fillRect(0,0,getWidth(),getHeight());

            // Draw the grid
            g.setColor(Color.gray);
            int left = toScreenXfromGridX(0,Grid.grid_width);
            int right = toScreenXfromGridX(Grid.grid_width,Grid.grid_width);
            int bottom = toScreenYfromGridY(0,Grid.grid_height);
            int top = toScreenYfromGridY(Grid.grid_height,Grid.grid_height);
            // Verticle
            for(int grid_x=0; grid_x<=Grid.grid_width; grid_x++) {
                int x = toScreenXfromGridX(grid_x,Grid.grid_width);
                g.drawLine(x, top, x, bottom);
            }
            // Horizontal
            for(int grid_y=0; grid_y<=Grid.grid_height; grid_y++) {
                int y = toScreenYfromGridY(grid_y,Grid.grid_height);
                g.drawLine(left, y, right, y);
            }

            // Get the tiles
            Tile[][] tiles = grid.getTiles();
            // Draw components
            for(int grid_x=0; grid_x<tiles.length; grid_x++) {
                for(int grid_y=0; grid_y<tiles[grid_x].length; grid_y++) {
                    // Get the tile
                    Tile tile = tiles[grid_x][grid_y];
                    // Get the corners of the grid
                    int xL = toScreenXfromGridX(grid_x,Grid.grid_width);
                    int xR = toScreenXfromGridX(grid_x+1,Grid.grid_width);
                    int yD = toScreenYfromGridY(grid_y,Grid.grid_height);
                    int yU = toScreenYfromGridY(grid_y+1,Grid.grid_height);
                    // Draw Walls
                    g.setColor(Color.red);
                    if(tile.getWallD()) {
                        g.drawLine(xL, yD, xR, yD);
                    }
                    if(tile.getWallU()) {
                        g.drawLine(xL, yU, xR, yU);
                    }
                    if(tile.getWallR()) {
                        g.drawLine(xR, yD, xR, yU);
                    }
                    if(tile.getWallL()) {
                        g.drawLine(xL, yD, xL, yU);
                    }
                    // Draw Doors
                    if(tile.getDoorD()) {
                        g.setColor(tile.getLockD()?Color.PINK:Color.MAGENTA);
                        g.drawLine(xL, yD, xR, yD);
                    }
                    if(tile.getDoorU()) {
                        g.setColor(tile.getLockU()?Color.PINK:Color.MAGENTA);
                        g.drawLine(xL, yU, xR, yU);
                    }
                    if(tile.getDoorR()) {
                        g.setColor(tile.getLockR()?Color.PINK:Color.MAGENTA);
                        g.drawLine(xR, yD, xR, yU);
                    }
                    if(tile.getDoorL()) {
                        g.setColor(tile.getLockL()?Color.PINK:Color.MAGENTA);
                        g.drawLine(xL, yD, xL, yU);
                    }
                    // Draw Components
                    int x = (int)((xL+xR)/2.0);
                    int y = (int)((yU+yD)/2.0);
                    final double sensor_radius = 0.1*scale;
                    final double human_radius = 0.3*scale;
                    //Draw HumanAgents
                    if(tile.getHumanAgent()){
                        g.setColor(Color.pink);
                        int x1 = (int)(x-human_radius);
                        int y1 = (int)(y-human_radius);
                        g.drawOval(x1,y1,(int)(2*human_radius),(int)(2*human_radius));
                    }
                    // Draw Fires
                    if(tile.getFire()) {
                        g.setColor(Color.red);
                        g.drawLine(xL,yU,xR,yD);
                        g.drawLine(xL,yD,xR,yU);
                    }
                    if(tile.getSuppression()) {
                        g.setColor(Color.blue);
                        g.drawLine(xL,yU,xR,yD);
                        g.drawLine(xL,yD,xR,yU);
                    }
                    // Draw FireSensors
                    if(tile.getFireSensor()) {
                        g.setColor(Color.green);
                        int x1 = (int)(x-sensor_radius);
                        int y1 = (int)(y-sensor_radius);
                        g.drawOval(x1,y1,(int)(2*sensor_radius),(int)(2*sensor_radius));
                    }
                    
                    // Draw FireAlarms
                    if(tile.getFireAlarm()) {
                        g.setColor(Color.yellow);
                        int x1 = (int)(x-sensor_radius);
                        int y1 = (int)(y-sensor_radius);
                        g.drawOval(x1,y1,(int)(2*sensor_radius),(int)(2*sensor_radius));
                    }
                    // Draw Suppressor
                    if(tile.getSuppressor()) {
                        g.setColor(Color.yellow);
                        int x1 = (int)(x-sensor_radius);
                        int y1 = (int)(y-sensor_radius);
                        g.drawRect(x1,y1,(int)(2*sensor_radius),(int)(2*sensor_radius));
                    }
                    // Draw Exits
                    if(tile.getExit()) {
                        g.setColor(Color.green);
                        g.fillRect(xL, yU, (int)scale, (int)scale);
                    }
                }
            }
        }
    }
    

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON1) { 
            synchronized(grid) {
                int grid_x = toGridXfromScreenX(me.getX(),Grid.grid_width);
                int grid_y = toGridYfromScreenY(me.getY(),Grid.grid_height);
                if(grid_x<0 || Grid.grid_width<=grid_x || grid_y<0 || Grid.grid_height<=grid_y)
                    return;
                if(nextComponentType != null) {
                    switch(nextComponentType) {
                        case Wall: {
                            int xL = toScreenXfromGridX(grid_x,Grid.grid_width);
                            int xR = toScreenXfromGridX(grid_x+1,Grid.grid_width);
                            int yD = toScreenYfromGridY(grid_y,Grid.grid_height);
                            int yU = toScreenYfromGridY(grid_y+1,Grid.grid_height);
                            int mx = me.getX();
                            int my = me.getY();
                            int x1 = mx-xL;
                            int y1 = my-yU;
                            int x2 = xR-mx;
                            int y2 = y1;
                            if(x1>y1) { // up-right
                                if(x2>y2) { // up-left
                                    grid.addWall(grid_x  ,grid_y+1,grid_x+1,grid_y+1); // Up
                                } else { // down-right
                                    grid.addWall(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Right
                                }
                            } else { // down-left
                                if(x2>y2) { // up-left
                                    grid.addWall(grid_x,  grid_y,  grid_x,  grid_y+1); // Left
                                } else { // down-right
                                    grid.addWall(grid_x,  grid_y,  grid_x+1,grid_y  ); // Down
                                }
                            }
                            repaint();
                            break;
                        }
                        case Door: {
                            int xL = toScreenXfromGridX(grid_x,Grid.grid_width);
                            int xR = toScreenXfromGridX(grid_x+1,Grid.grid_width);
                            int yD = toScreenYfromGridY(grid_y,Grid.grid_height);
                            int yU = toScreenYfromGridY(grid_y+1,Grid.grid_height);
                            int mx = me.getX();
                            int my = me.getY();
                            int x1 = mx-xL;
                            int y1 = my-yU;
                            int x2 = xR-mx;
                            int y2 = y1;
                            if(x1>y1) { // up-right
                                if(x2>y2) { // up-left
                                    grid.removeWall(grid_x  ,grid_y+1,grid_x+1,grid_y+1);
                                    grid.addDoor(   grid_x  ,grid_y+1,grid_x+1,grid_y+1); // Up
                                } else { // down-right
                                    grid.removeWall(grid_x+1,grid_y,  grid_x+1,grid_y+1);
                                    grid.addDoor(   grid_x+1,grid_y,  grid_x+1,grid_y+1); // Right
                                }
                            } else { // down-left
                                if(x2>y2) { // up-left
                                    grid.removeWall(grid_x,  grid_y,  grid_x,  grid_y+1);
                                    grid.addDoor(   grid_x,  grid_y,  grid_x,  grid_y+1); // Left
                                } else { // down-right
                                    grid.removeWall(grid_x,  grid_y,  grid_x+1,grid_y  );
                                    grid.addDoor(   grid_x,  grid_y,  grid_x+1,grid_y  ); // Down
                                }
                            }
                            repaint();
                            break;
                        }
                        case FireSensor:
                            grid.addComponent(ComponentType.FireSensor, grid_x, grid_y);
                            repaint();
                            break;
                        case Fire:
                            grid.addComponent(ComponentType.Fire, grid_x, grid_y);
                            repaint();
                            break;
                        case HumanAgent:
                            grid.addComponent(ComponentType.HumanAgent, grid_x, grid_y);
                            repaint();
                            break;
                        case Suppressor:
                            grid.addComponent(ComponentType.Suppressor, grid_x, grid_y);
                            repaint();
                            break;
                        case Exit:
                            grid.addComponent(ComponentType.Exit, grid_x, grid_y);
                            repaint();
                            break;
                        case FireAlarm:
                            grid.addComponent(ComponentType.FireAlarm, grid_x, grid_y);
                            repaint();
                            break;
                    }
           
                }
             
            }
        }
        else if (me.getButton() == MouseEvent.BUTTON3) {
            synchronized(grid) {
                int grid_x = toGridXfromScreenX(me.getX(),Grid.grid_width);
                int grid_y = toGridYfromScreenY(me.getY(),Grid.grid_height);
                if(grid_x<0 || Grid.grid_width<=grid_x || grid_y<0 || Grid.grid_height<=grid_y)
                    return;
                
                int xL = toScreenXfromGridX(grid_x,Grid.grid_width);
                int xR = toScreenXfromGridX(grid_x+1,Grid.grid_width);
                int yD = toScreenYfromGridY(grid_y,Grid.grid_height);
                int yU = toScreenYfromGridY(grid_y+1,Grid.grid_height);
                int mx = me.getX();
                int my = me.getY();
                
                int wallGrabRadius = (int)(scale/10.0);
                
                if(mx-xL>wallGrabRadius && xR-mx>wallGrabRadius && 
                   yD-my>wallGrabRadius && my-yU>wallGrabRadius) {
                    grid.removeComponents(grid_x, grid_y);
                } else {
                    int x1 = mx-xL;
                    int y1 = my-yU;
                    int x2 = xR-mx;
                    int y2 = y1;
                    if(x1>y1) { // up-right
                        if(x2>y2) { // up-left
                            grid.removeWall(grid_x  ,grid_y+1,grid_x+1,grid_y+1); // Up
                            grid.removeDoor(grid_x  ,grid_y+1,grid_x+1,grid_y+1); // Up
                        } else { // down-right
                            grid.removeWall(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Right
                            grid.removeDoor(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Right
                        }
                    } else { // down-left
                        if(x2>y2) { // up-left
                            grid.removeWall(grid_x,  grid_y,  grid_x,  grid_y+1); // Left
                            grid.removeDoor(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Right
                        } else { // down-right
                            grid.removeWall(grid_x,  grid_y,  grid_x+1,grid_y  ); // Down
                            grid.removeDoor(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Right
                        }
                    }
                }
                
                
                /*
                if(nextComponentType != null) {
                    Component comp;
                    switch(nextComponentType) {
                        case Wall:
                            int xL = toScreenXfromGridX(grid_x,Grid.grid_width);
                            int xR = toScreenXfromGridX(grid_x+1,Grid.grid_width);
                            int yD = toScreenYfromGridY(grid_y,Grid.grid_height);
                            int yU = toScreenYfromGridY(grid_y+1,Grid.grid_height);
                            int mx = me.getX();
                            int my = me.getY();
                            int x1 = mx-xL;
                            int y1 = my-yU;
                            int x2 = xR-mx;
                            int y2 = y1;
                            if(x1>y1) { // up-right
                                if(x2>y2) { // up-left
                                    grid.removeWall(grid_x  ,grid_y+1,grid_x+1,grid_y+1); // Up
                                } else { // down-right
                                    grid.removeWall(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Right
                                }
                            } else { // down-left
                                if(x2>y2) { // up-left
                                    grid.removeWall(grid_x,  grid_y,  grid_x,  grid_y+1); // Left
                                } else { // down-right
                                    grid.removeWall(grid_x,  grid_y,  grid_x+1,grid_y  ); // Down
                                }
                            }
                                                     
                            
                            repaint();
                            break;
                        case FireSensor:
                            grid.removeComponent(new FireSensor(), grid_x, grid_y);
                            repaint();
                            break;
                        case Fire:
                            grid.removeComponent(new Fire(), grid_x, grid_y);
                            repaint();
                            break;
                        case HumanAgent:
                            grid.removeComponent(new HumanAgent(), grid_x, grid_y);
                            repaint();
                            break;
                        case Suppressor:
                            grid.removeComponent(new Suppressor(), grid_x, grid_y);
                            repaint();
                            break;
                        case Exit:
                            Exit exit = new Exit();
                            grid.removeComponent(exit, grid_x, grid_y);
                            exit.location_x = grid_x;
                            exit.location_y = grid_y;
                            repaint();
                            break;
                    }
                    //justSelected = true;
                    //justSelectedX = me.getX();
                    //justSelectedY = me.getY();
                }
                */
            }
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) {}
    
}
