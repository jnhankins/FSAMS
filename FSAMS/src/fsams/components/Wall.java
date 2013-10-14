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
    public void draw(Graphics g, View v) {
        double width = g.getClipBounds().width;
        double height = g.getClipBounds().height;
        double screenX1 = v.toScreenCoordinateX(getX1(), width, height);
        double screenY1 = v.toScreenCoordinateY(getY1(), width, height);
        double screenX2 = v.toScreenCoordinateX(getX2(), width, height);
        double screenY2 = v.toScreenCoordinateY(getY2(), width, height);
        
        if(isSelected) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.red);
        }
        
        g.drawLine((int)screenX1, (int)screenY1, (int)screenX2, (int)screenY2);
    }
    
    @Override
    public String toString() {
        return "Wall("+getX1()+","+getY1()+","+getX2()+","+getY2()+")";
    }
}