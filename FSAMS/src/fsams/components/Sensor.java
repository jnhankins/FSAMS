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
    }

    @Override
    public FSAMSComponent1D copy() {
        return new Sensor(this);
    }

    @Override
    public void update(ComponentManager components, double dTime) {
        
    }
}
