/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fsams.gui;

import fsams.grid.Grid;
import fsams.grid.Tile;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Handles all rendering for the simulation.
 * @author FSAMS Team
 */
public class Render {

    /**
     * renders each tile and its components
     * @param editP the panel to render the simulation.
     * @param g the graphics to draw for the components.
     * @param grid the tile grid on which the simulation runs.
     */
    public static void draw(EditPanel editP, Graphics g, Grid grid) {
        // Clear the drawing area
            g.setColor(Color.black);
            g.fillRect(0,0,editP.getWidth(),editP.getHeight());

            // Draw the grid
            g.setColor(Color.gray);
            int left = editP.toScreenXfromGridX(0,Grid.grid_width);
            int right = editP.toScreenXfromGridX(Grid.grid_width,Grid.grid_width);
            int bottom = editP.toScreenYfromGridY(0,Grid.grid_height);
            int top = editP.toScreenYfromGridY(Grid.grid_height,Grid.grid_height);
            // Verticle
            for(int grid_x=0; grid_x<=Grid.grid_width; grid_x++) {
                int x = editP.toScreenXfromGridX(grid_x,Grid.grid_width);
                g.drawLine(x, top, x, bottom);
            }
            // Horizontal
            for(int grid_y=0; grid_y<=Grid.grid_height; grid_y++) {
                int y = editP.toScreenYfromGridY(grid_y,Grid.grid_height);
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
                    int xL = editP.toScreenXfromGridX(grid_x,Grid.grid_width);
                    int xR = editP.toScreenXfromGridX(grid_x+1,Grid.grid_width);
                    int yD = editP.toScreenYfromGridY(grid_y,Grid.grid_height);
                    int yU = editP.toScreenYfromGridY(grid_y+1,Grid.grid_height);
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
                    final double sensor_radius = 0.1*editP.getScale();
                    final double alarm_radius = 0.15*editP.getScale();
                    final double suppression_radius = 0.2*editP.getScale();
                    final double human_radius = 0.3*editP.getScale();
                    //Draw HumanAgents
                    if(tile.getHumanAgent()) {
                        g.setColor(Color.pink);
                        int x1 = (int)(x-human_radius);
                        int y1 = (int)(y-human_radius);
                        g.drawOval(x1,y1,(int)(2*human_radius),(int)(2*human_radius));
                    }
                    // Draw Intruders
                    if(tile.getIntruder()) {
                        g.setColor(tile.getIntruderFleeing()?Color.yellow:Color.red);
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
                        g.setColor(tile.getFireSensorActive()?Color.white:Color.green);
                        int x1 = (int)(x-sensor_radius);
                        int y1 = (int)(y-sensor_radius);
                        g.drawOval(x1,y1,(int)(2*sensor_radius),(int)(2*sensor_radius));
                    }
                    
                    // Draw FireAlarms
                    if(tile.getFireAlarm()) {
                        g.setColor(tile.getFireAlarmActive()?Color.white:Color.yellow);
                        int x1 = (int)(x-alarm_radius);
                        int y1 = (int)(y-alarm_radius);
                        g.drawOval(x1,y1,(int)(2*alarm_radius),(int)(2*alarm_radius));
                    }
                    // Draw Suppressor
                    if(tile.getSprinkler()) {
                        g.setColor(Color.yellow);
                        int x1 = (int)(x-suppression_radius);
                        int y1 = (int)(y-suppression_radius);
                        g.drawRect(x1,y1,(int)(2*suppression_radius),(int)(2*suppression_radius));
                    }
                    // Draw Equipment
                    if(tile.getEquipment()){
                        g.setColor(tile.getEquipmentActive()?Color.yellow:Color.orange);
                        g.drawLine(xL,yU,xR,yD);
                        g.drawLine(xL,yD,xR,yU);
                        g.drawRoundRect(xL, yU, (int)editP.getScale(), (int)editP.getScale(), (int)sensor_radius, (int)sensor_radius);
                    }
                    // Draw Exits
                    if(tile.getExit()) {
                        g.setColor(Color.green);
                        g.fillRect(xL, yU, (int)editP.getScale(), (int)editP.getScale());
                    }
                    // Draw Cameras
                    if(tile.getCamera()) {
                        g.setColor(Color.blue);
                        g.drawRoundRect(xL, yU, (int)editP.getScale(), (int)editP.getScale(), (int)sensor_radius, (int)sensor_radius);
                    }
                }
            }
    }
}
