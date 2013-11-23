package fsams.components;

import fsams.gui.View;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author FSAMS Team
 */
public class Wall extends FSAMSComponent2D{
    public Wall() {
        super();
    }
    public Wall(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }
    public Wall(Wall wall) {
        super(wall);
    }
    
    @Override
    public String getType() {
        return "Wall";
    }
    
    @Override
    public String toString() {
        return "Wall("+getX1()+","+getY1()+","+getX2()+","+getY2()+")";
    }

    @Override
    public FSAMSComponent1D copy() {
        return new Wall(this);
    }

    @Override
    public void update(ComponentManager components, double dTime) {
        
    }

    @Override
    public void draw(Graphics g, View v) {
    }
}