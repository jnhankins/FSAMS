/*
 * TODO
 */
package fsams.components;

import fsams.gui.View;
import java.awt.Graphics;

/**
 *
 * @author FSAMS Team
 */
public abstract class FSAMSComponent1D {
    protected int x1, y1; //center of components
    protected int selectionRadius; //how far from point?
    protected boolean isSelected;
    
    //constructors
    public FSAMSComponent1D() {
        x1 = 0;
        y1 = 0;
        selectionRadius = 10;
        isSelected = false;
    }
    public FSAMSComponent1D(int x, int y, int radius) {
        x1 = x;
        y1 = y;
        selectionRadius = radius;
        isSelected = false;
    }
    public FSAMSComponent1D(FSAMSComponent1D component) {
        x1 = component.x1;
        y1 = component.y1;
        selectionRadius = component.selectionRadius;
        isSelected = false;
    }
    //setters
    public void setP1(int x, int y) {
        x1 = x;
        y1 = y;
    }
    public void setX1(int x) {
        x1 = x;
    }    
    public void setY1(int y) {
        y1 = y;
    }
    public void setSelectionRadius(int radius) {
        selectionRadius = radius;
    }
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    //getters
    public int getX1() {
        return x1;
    } 
    public int getY1() {
        return y1;
    }
    public int getSelectionRadius(){
        return selectionRadius;
    }
    public boolean isSelected() {
        return isSelected;
    }
    
    // Returns true if the distance from the clicked point to the center of this
    // component is less than or equal to this component's selection radius.
    public boolean isSelected(double x, double y) {
        return (x1-x)*(x1-x)+(y1-y)*(y1-y) <= selectionRadius*selectionRadius;
    }
    public abstract String getType();
    public abstract void paint(Graphics g, View v);
}
