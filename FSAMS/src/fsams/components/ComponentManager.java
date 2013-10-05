/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.components;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author FSAMS Team
 */
public class ComponentManager {
    private ArrayList<FSAMSComponent1D> components = new ArrayList();
    
    public ComponentManager(){
        
    }
    
    public void addComponent(FSAMSComponent1D component){
        components.add(component);
    }
    
    public void removeComponent(FSAMSComponent1D component){
        components.remove(component);
    }
    
    public FSAMSComponent1D selectComponent(int x, int y) throws Exception{
        for(FSAMSComponent1D component : components){
            if(component.isSelected(x, y))
                return component;
        }
        
        throw new Exception("no component selected");
    }
    
    public void paint(Graphics g){
        for(FSAMSComponent1D component : components){
            component.paint(g);
        }
    }
}
