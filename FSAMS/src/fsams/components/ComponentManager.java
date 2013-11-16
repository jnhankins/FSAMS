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
    public ComponentManager(ComponentManager componentManager) {
        components = new ArrayList();
        for(FSAMSComponent1D component: componentManager.components) {
            components.add(component.copy());
        }
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
    
    public void update(double dTime){
        for(FSAMSComponent1D component : components) {
            component.update(this, dTime);
        }
    }
    
    public void drawComponents(Graphics g, View v) {
        for(FSAMSComponent1D component : components) {
            component.draw(g,v);
        }
    }
}