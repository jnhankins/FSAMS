package fsams.components;

import fsams.gui.View;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author FSAMS Team
 */
public class Elevator extends FSAMSComponent2D{
    public Elevator() {
        super();
    }
    public Elevator(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }
    public Elevator(Elevator elevator) {
        super(elevator);
    }
    
    @Override
    public String getType() {
        return "Elevator";
    }
    
    @Override
    public String toString() {
        return "Elevator("+getX1()+","+getY1()+","+getX2()+","+getY2()+")";
    }

    @Override
    public FSAMSComponent1D copy() {
        return new Elevator(this);
    }

    @Override
    public void update(ComponentManager components, double dTime) {
        
    }

    @Override
    public void draw(Graphics g, View v) {
    }
}