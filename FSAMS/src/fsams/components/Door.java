package fsams.components;

import fsams.gui.View;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author FSAMS Team
 */
public class Door extends FSAMSComponent2D{
    public Door() {
        super();
    }
    public Door(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }
    public Door(Door door) {
        super(door);
    }
    
    @Override
    public String getType() {
        return "Door";
    }
    
    @Override
    public String toString() {
        return "Door("+getX1()+","+getY1()+","+getX2()+","+getY2()+")";
    }

    @Override
    public FSAMSComponent1D copy() {
        return new Door(this);
    }

    @Override
    public void update(ComponentManager components, double dTime) {
        
    }

    @Override
    public void draw(Graphics g, View v) {
    }
}