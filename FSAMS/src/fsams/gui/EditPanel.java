package fsams.gui;

import fsams.FSAMS;
import fsams.grid.ComponentType;
import fsams.grid.Grid;
import fsams.grid.Tile;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

/**
 *
 * @author FSAMS Team
 */
public class EditPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private final FSAMS fsams;
    private double centerX;
    private double centerY;
    private double scale;
    private final double zoomFactor = 1.5;
    private Grid grid;
    private boolean mouseDrag;
    private double mouseX;
    private double mouseY;
    
    private ComponentType nextComponentType;
    
    /**
     * Constructs a new EditPanel to render the simulation and edit area
     * @param fsams Needs FSAMS to communicate with other GUI objects
     */
    public EditPanel(FSAMS fsams) {
        this.fsams = fsams;
        centerX = 0;
        centerY = 0;
        scale = 50.0;
        nextComponentType = null;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        setFocusable(true);
        mouseDrag = false;
    }
    
    /**
     * sets the 
     * @param grid
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Sets the nextComponentType from FSAMS to the selected component type
     * button, otherwise sets it to null.
     * @param type setNextComponentType from ComponentsPanel to FSAMS to EditPanel
     */
    public void setNextComponentType(ComponentType type) {
        nextComponentType = type;
    }
    
    /*
    The following toScreenX/YfromGridX/Y and toGridX/YfromScreenX/Y does a
    translation of the grid world to screen coordinates.
    */
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
    
    /**
     * Gets the scale of the world
     * @return returns scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * Zooms In the world
     */
    public void zoomIn() {
        scale *= zoomFactor;
    }
    
    /**
     * Zooms Out the world
     */
    public void zoomOut() {
        scale /= zoomFactor;
    }
    
    @Override
    public void paint(Graphics g) {
        synchronized(grid) {
            Render.draw(this, g, grid, fsams.isSimulationRunning());
        }
    }
    

    @Override
    public void mousePressed(MouseEvent me) {
        requestFocusInWindow();
        mouseDrag = false;
        mouseX = me.getX();
        mouseY = me.getY();
    }
    /*
    IF moused was dragged
        do nothing
    ELSE IF simulation is not running
        IF left mouse button was pressed
            get the grid coordinates
            IF coordinates are on the grid AND a componentType is selected
                add the component to the grid coordinates
        ELSE IF right mouse button was pressed
            get the grid coordinates
            IF coordinates are on the grid
                remove all components from the grid coordinates
    ELSE IF simulation is running
        IF left mouse button is pressed
            get the grid coordinates
            IF coordinates are on the grid
                get the tile at the grid coordinates
                IF tile has a door, equipment, alarm, sprinkler
                    toggle the componenets
                    repaint
    */
    @Override public void mouseReleased(MouseEvent me) {
        
        if(mouseDrag)
            return;
        if(!fsams.isSimulationRunning()) {
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
                            case Sprinkler:
                                grid.addComponent(ComponentType.Sprinkler, grid_x, grid_y);
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
                            case Intruder:
                                grid.addComponent(ComponentType.Intruder, grid_x, grid_y);
                                repaint();
                                break;
                            case Camera:
                                grid.addComponent(ComponentType.Camera, grid_x, grid_y);
                                repaint();
                                break;                        }
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
                                grid.removeDoor(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Left
                            } else { // down-right
                                grid.removeWall(grid_x,  grid_y,  grid_x+1,grid_y  ); // Down
                                grid.removeDoor(grid_x+1,grid_y,  grid_x+1,grid_y+1); // Down
                            }
                        }
                    }
                }
                repaint();
            }
        } else { // Simulation is running
            if (me.getButton() == MouseEvent.BUTTON3) {
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
                    
                    Tile t = grid.getTiles()[grid_x][grid_y];

                    int wallGrabRadius = (int)(scale/10.0);
                    if(mx-xL>wallGrabRadius && xR-mx>wallGrabRadius && 
                       yD-my>wallGrabRadius && my-yU>wallGrabRadius) {
                        
                        if(t.getEquipment()) t.setEquipmentActive(!t.getEquipmentActive());
                        if(t.getFireAlarm()) t.setFireAlarmActive(!t.getFireAlarmActive());
                        if(t.getSprinkler()) t.setSuppressorActive(!t.getSuppressorActive());
                    } else {
                        int x1 = mx-xL;
                        int y1 = my-yU;
                        int x2 = xR-mx;
                        int y2 = y1;
                        if(x1>y1) { // up-right
                            if(x2>y2) { // up-left
                                if(t.getDoorU()) t.setLockU(!t.getLockU());
                            } else { // down-right
                                if(t.getDoorR()) t.setLockR(!t.getLockR());
                            }
                        } else { // down-left
                            if(x2>y2) { // up-left
                                if(t.getDoorL()) t.setLockL(!t.getLockL());
                            } else { // down-right
                                if(t.getDoorD()) t.setLockD(!t.getLockD());
                            }
                        }
                    }
                }
                repaint();
            }
        }
    }
    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    
    
    @Override public void keyTyped(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) {
        final int panAmount = 20;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            centerX -= panAmount;
            repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            centerX += panAmount;
            repaint();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            centerY -= panAmount;
            repaint();
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            centerY += panAmount;
            repaint();
        }
        if(e.getKeyChar() == '+' || e.getKeyChar() == '=') {
            zoomIn();
            repaint();
        }
        if(e.getKeyChar() == '-' || e.getKeyChar() == '_') {
            zoomOut();
            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        double newMouseX = e.getX();
        double newMouseY = e.getY();

        double dX = newMouseX - mouseX;
        double dY = newMouseY - mouseY;
        
        final double dragThreshold = 6;
        if(Math.sqrt(dX*dX+dY*dY) > dragThreshold) {
            double newCenterX = centerX - (newMouseX - mouseX);
            double newCenterY = centerY - (newMouseY - mouseY);
            centerX = newCenterX;
            centerY = newCenterY;
            repaint();
            mouseX = newMouseX;
            mouseY = newMouseY;
            mouseDrag = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent me) {
        int notches = me.getWheelRotation();
        if(notches<0) {
            zoomIn();
        }
        if(notches>0) {
            zoomOut();
        }
        repaint();
    }
}
