package fsams.components;

import fsams.gui.View;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author FSAMS Team
 */
public class ComponentManager {
    private ArrayList<FSAMSComponent1D> components;
    
    public static enum ComponentType {
        Wall,
        Sensor
    }
    
    public ComponentManager() {
        components = new ArrayList();
    }
    
    public void addComponent(FSAMSComponent1D component) {
        components.add(component);
    }
    
    public void removeComponent(FSAMSComponent1D component) {
        components.remove(component);
    }
    
    public FSAMSComponent1D getComponent(double worldX, double worldY) {
        for(int i=components.size()-1; i>=0; i--)
            if(components.get(i).isSelected(worldX, worldY))
                return components.get(i);
        return null;
    }
    
    public void drawComponents(Graphics g, View v) {
        for(FSAMSComponent1D component : components) {
            component.draw(g,v);
        }
    }
}