package fsams.components;

import fsams.gui.View;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author FSAMS Team
 */
public class Alarm extends FSAMSComponent1D {
    public Alarm() {
        super();
    }
    public Alarm(double x1, double y1) {
        super(x1, y1);
    }
    public Alarm(Alarm alarm) {
        super(alarm);
    }
    
    @Override
    public String getType() {
        return "Alarm";
    }

    @Override
    public void draw(Graphics g, View v) {
    }

    @Override
    public FSAMSComponent1D copy() {
        return new Alarm(this);
    }

    @Override
    public void update(ComponentManager components, double dTime) {
        
    }
}
