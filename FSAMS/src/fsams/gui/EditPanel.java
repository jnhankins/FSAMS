package fsams.gui;

import fsams.FSAMS;
import fsams.grid.ComponentType;
import fsams.grid.Grid;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class EditPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    private final FSAMS fsams;
    private double centerX;
    private double centerY;
    private double scale;
    private final double zoomFactor = 1.5;
    private Grid grid;
    private boolean controlDown;
    private boolean mousePressed;
    private double mouseX;
    private double mouseY;
    
    private ComponentType nextComponentType;
    
    public EditPanel(FSAMS fsams) {
        this.fsams = fsams;
        centerX = 0;
        centerY = 0;
        scale = 50.0;
        nextComponentType = null;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        controlDown = false;
        mousePressed = false;
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
    
    public double getScale() {
        return scale;
    }
    public void zoomIn() {
        scale *= zoomFactor;
    }
    
    public void zoomOut() {
        scale /= zoomFactor;
    }
    
    @Override
    public void paint(Graphics g) {
        synchronized(grid) {
            Render.draw(this, g, grid);
        }
    }
    

    @Override
    public void mousePressed(MouseEvent me) {
        requestFocusInWindow();
        mousePressed = true;
        mouseX = me.getX();
        mouseY = me.getY();
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
                        case Equipment:
                            grid.addComponent(ComponentType.Equipment, grid_x, grid_y);
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
            }
            repaint();
        }
    }
    @Override public void mouseReleased(MouseEvent e) { 
        mousePressed = false;
    }
    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown()) {
            controlDown = true;
            System.out.println(controlDown);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!e.isControlDown()) {
            controlDown = false;
            System.out.println(controlDown);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mousePressed && controlDown) {
            double newMouseX = e.getX();
            double newMouseY = e.getY();
            
            double newCenterX = centerX - (newMouseX - mouseX);
            double newCenterY = centerY - (newMouseY - mouseY);
            centerX = newCenterX;
            centerY = newCenterY;
            repaint();
            mouseX = newMouseX;
            mouseY = newMouseY;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
}
