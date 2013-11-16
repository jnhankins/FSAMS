package fsams.components;

import fsams.gui.View;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author FSAMS Team
 */
public class Sensor extends FSAMSComponent1D {
    public Sensor() {
        super();
    }
    public Sensor(double x1, double y1) {
        super(x1, y1);
    }
    public Sensor(Sensor sensor) {
        super(sensor);
    }
    
    @Override
    public String getType() {
        return "Sensor";
    }

    @Override
    public void draw(Graphics g, View v) {
        double width = g.getClipBounds().width;
        double height = g.getClipBounds().height;
        
        final double radius = 0.5;
        double screenX1 = v.toScreenCoordinateX(getX1()-radius/2.0, width, height);
        double screenY1 = v.toScreenCoordinateY(getY1()+radius/2.0, width, height);
        double screenW = radius*v.getScale()* Math.min(width, height)/2.0;
        
        if(isSelected) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.pink);
        }
        
        g.drawOval( (int)screenX1,
                    (int)screenY1,
                    (int)screenW,
                    (int)screenW);
    }

    @Override
    public FSAMSComponent1D copy() {
        return new Sensor(this);
    }

    @Override
    public void update(ComponentManager components, double dTime) {
        
    }
}
