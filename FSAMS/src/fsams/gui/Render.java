/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fsams.gui;

import fsams.grid.Grid;
import fsams.grid.Tile;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
     * @param isSimRunning true if the simulation is running
     */
public static void draw(EditPanel editP, Graphics g, Grid grid, boolean isSimRunning) {
    // Clear the drawing area
        g.setColor(Color.black);
        g.fillRect(0,0,editP.getWidth(),editP.getHeight());

        // Draw the grid
        int grayscale = 50;
        g.setColor(new Color(grayscale,grayscale,grayscale));
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
        // Get the scale
        double scale = editP.getScale();
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
                int wallwidth = (int)(scale/16.0);
                int doorwidth = (int)(scale/32.0);
                g.setColor(new Color(205,133,63));
                if(tile.getWallD()) {
                    g.fillRect(xL, yD-wallwidth, (int)(scale), wallwidth*2);
                }
                if(tile.getWallU()) {
                    g.fillRect(xL, yU-wallwidth, (int)(scale), wallwidth*2);
                }
                if(tile.getWallR()) {
                    g.fillRect(xR-wallwidth, yU, wallwidth*2, (int)(scale));
                }
                if(tile.getWallL()) {
                    g.fillRect(xL-wallwidth, yU, wallwidth*2, (int)(scale));
                }
                // Draw Doors
                if(tile.getDoorD()) {
                    g.setColor(tile.getLockD()?Color.PINK:Color.MAGENTA);
                    g.fillRect(xL, yD-doorwidth, (int)(scale), doorwidth*2);
                }
                if(tile.getDoorU()) {
                    g.setColor(tile.getLockU()?Color.PINK:Color.MAGENTA);
                    g.fillRect(xL, yU-doorwidth, (int)(scale), doorwidth*2);
                }
                if(tile.getDoorR()) {
                    g.setColor(tile.getLockR()?Color.PINK:Color.MAGENTA);
                    g.fillRect(xR-doorwidth, yU, doorwidth*2, (int)(scale));
                }
                if(tile.getDoorL()) {
                    g.setColor(tile.getLockL()?Color.PINK:Color.MAGENTA);
                    g.fillRect(xL-doorwidth, yU, doorwidth*2, (int)(scale));
                }
                // Draw Components
                int x = (int)((xL+xR)/2.0);
                int y = (int)((yU+yD)/2.0);
                final double radius = 0.3*scale;
                //Draw HumanAgents
                if(tile.getHumanAgent()) {
                    g.setColor(Color.pink);
                    
                    int armwidth = (int)(scale/8.0);
                    int armup = (int)(scale/8.0);
                    int bodyheight = (int)(scale/4.0);
                    double headsize = scale/4.0;
                    int legwidth = (int)(scale/8.0);
                    
                    g.drawLine(x-armwidth, y-armup, x+armwidth, y-armup);
                    g.drawLine(x,y+bodyheight,x,y-bodyheight);
                    g.drawOval(x-(int)(headsize/2.0), y-bodyheight-(int)(headsize), (int)headsize, (int)headsize);
                    g.drawLine(x,y+bodyheight,x+legwidth,y+(int)(scale/2.0));
                    g.drawLine(x,y+bodyheight,x-legwidth,y+(int)(scale/2.0));
                    
                }
                // Draw Intruders
                if(tile.getIntruder()) {
                    g.setColor(Color.red);
                    int armwidth = (int)(scale/8.0);
                    int armup = (int)(scale/8.0);
                    int bodyheight = (int)(scale/4.0);
                    double headsize = scale/4.0;
                    int legwidth = (int)(scale/8.0);
                    
                    g.drawLine(x-armwidth, y-armup, x+armwidth, y-armup);
                    g.drawLine(x,y+bodyheight,x,y-bodyheight);
                    g.drawOval(x-(int)(headsize/2.0), y-bodyheight-(int)(headsize), (int)headsize, (int)headsize);
                    g.drawLine(x,y+bodyheight,x+legwidth,y+(int)(scale/2.0));
                    g.drawLine(x,y+bodyheight,x-legwidth,y+(int)(scale/2.0));
                    
                    double browfactor = scale/32.0;
                    g.drawLine(x-(int)browfactor,y-bodyheight-(int)(headsize/2.0+browfactor),x-(int)(2*browfactor),y-bodyheight-(int)(headsize/2.0+2*browfactor));
                    g.drawLine(x+(int)browfactor,y-bodyheight-(int)(headsize/2.0+browfactor),x+(int)(2*browfactor),y-bodyheight-(int)(headsize/2.0+2*browfactor));
                }
                // Draw Fires
                if(tile.getFire()) {
                    if(isSimRunning)
                        g.setColor(new Color(255,(int)(Math.random()*200),0));
                    else
                        g.setColor(Color.red);
                    g.fillRect(xL, yU, (int)scale, (int)scale);
                }
                if(tile.getSuppression()) {
                    g.setColor(Color.blue);
                    int numDrops = 5;
                    int dropRadius = (int)(scale/4.0);
                    for(int i=0; i<numDrops; i++) {
                        int dropx = x+(int)(Math.random()*scale-scale/2.0);
                        int dropy = y+(int)(Math.random()*scale-scale/2.0);
                        g.fillOval(dropx, dropy, dropRadius, dropRadius);
                    }
                }
                // Draw FireSensors
                if(tile.getFireSensor()) {
                    g.setColor(tile.getFireSensorActive()?Color.white:Color.green);
                    int x1 = (int)(x-radius);
                    int y1 = (int)(y-radius);
                    g.drawRect(x1,y1,(int)(2*radius),(int)(2*radius));
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                    double fontsize = scale/2.0;
                    g2.setFont(new Font("Ariel", Font.PLAIN, (int)fontsize));
                    g2.drawString("S",x-(int)(fontsize/3.0),y+(int)(fontsize/3.0));
                }

                // Draw FireAlarms
                if(tile.getFireAlarm()) {
                    g.setColor(tile.getFireAlarmActive()?Color.white:Color.yellow);
                    int x1 = (int)(x-radius);
                    int y1 = (int)(y-radius);
                    g.drawOval(x1,y1,(int)(2*radius),(int)(2*radius));
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                    double fontsize = scale/2.0;
                    g2.setFont(new Font("Ariel", Font.PLAIN, (int)fontsize));
                    g2.drawString("A",x-(int)(fontsize/3.0),y+(int)(fontsize/3.0));
                }
                // Draw Sprinkler
                if(tile.getSprinkler()) {
                    g.setColor(Color.cyan);
                    int x1 = (int)(x-radius);
                    int y1 = (int)(y-radius);
                    g.drawOval(x1,y1,(int)(2*radius),(int)(2*radius));
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                    double fontsize = scale/2.0;
                    g2.setFont(new Font("Ariel", Font.PLAIN, (int)fontsize));
                    g2.drawString("S",x-(int)(fontsize/3.0),y+(int)(fontsize/3.0));
                }
                // Draw Equipment
                if(tile.getEquipment()){
                    g.setColor(tile.getEquipmentActive()?Color.yellow:Color.orange);
                    g.drawRoundRect(xL, yU, (int)scale, (int)scale, (int)radius, (int)radius);
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                    double fontsize = scale/2.0;
                    g2.setFont(new Font("Ariel", Font.PLAIN, (int)fontsize));
                    g2.drawString("E",x-(int)(fontsize/3.0),y+(int)(fontsize/3.0));
                }
                // Draw Exits
                if(tile.getExit()) {
                    g.setColor(Color.green);
                    g.fillRect(xL, yU, (int)scale, (int)scale);
                    g.setColor(Color.black);
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                    double fontsize = scale/2.0;
                    g2.setFont(new Font("Ariel", Font.PLAIN, (int)fontsize));
                    g2.drawString("Exit",x-(int)(fontsize),y+(int)(fontsize/3.0));
                }
                // Draw Cameras
                if(tile.getCamera()) {
                    g.setColor(Color.yellow);
                    int x1 = (int)(x-radius);
                    int y1 = (int)(y-radius);
                    g.drawOval(x1,y1,(int)(2*radius),(int)(2*radius));
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                    double fontsize = scale/2.0;
                    g2.setFont(new Font("Ariel", Font.PLAIN, (int)fontsize));
                    g2.drawString("C",x-(int)(fontsize/2.5),y+(int)(fontsize/3.0));
                }
            }
        }
    }
}
