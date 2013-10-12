/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.components;

import fsams.FSAMS;
import fsams.gui.View;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author FSAMS Team
 */
public class ComponentManager {
    private ArrayList<FSAMSComponent1D> components = new ArrayList();
    private FSAMSComponent1D selectedComponent = null;
    
    public ComponentManager() {
        
    }
    
    public void addComponent(FSAMSComponent1D component) {
        components.add(component);
    }
    
    public void removeComponent(FSAMSComponent1D component) {
        components.remove(component);
    }
    
    public FSAMSComponent1D getComponent(int x, int y, int width, int height) {
        double worldX = FSAMS.view.toWorldCoordinateX(x, width, height);
        double worldY = FSAMS.view.toWorldCoordinateY(y, width, height);
        for(FSAMSComponent1D component : components)
            if(component.isSelected(worldX, worldY))
                return component;
        return null;
    }
    
    public void selectComponent(FSAMSComponent1D newSelectedComponent) {
        if(selectedComponent!=null)
            selectedComponent.setSelected(false);
        selectedComponent = newSelectedComponent;
        if(selectedComponent!=null)
            selectedComponent.setSelected(true);
        FSAMS.mainWindow.repaint();
    }
    
    public void deleteSelectedComponent() {
        if(selectedComponent!=null) {
            components.remove(selectedComponent);
            selectedComponent = null;
            FSAMS.mainWindow.repaint();
        }
    }
    
    public void paint(Graphics g) {
        for(FSAMSComponent1D component : components){
            component.paint(g, FSAMS.view);
        }
    }
}