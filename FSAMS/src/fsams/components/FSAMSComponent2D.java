/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.components;

/**
 *
 * @author FSAMS Team
 */
public abstract class FSAMSComponent2D extends FSAMSComponent1D {
    private int x2, y2;
    //constructors
    public FSAMSComponent2D() {
        super();
        x2 = 0;
        y2 = 0;
    }
    public FSAMSComponent2D(int x1, int y1, int x2, int y2, int radius) {
        super(x1, y1, radius);
        this.x2 = x2;
        this.y2 = y2;
    }
    public FSAMSComponent2D(FSAMSComponent2D component) {
        super(component);
        x2 = component.x2;
        y2 = component.y2;
    }
    //setters
    public void setP2(int x, int y) {
        x2 = x;
        y2 = y;
    }
    public void setX2(int x) {
        x2 = x;
    }    
    public void setY2(int y) {
        y2 = y;
    }
    
    //getters
    public int getX2() {
        return x2;
    } 
    public int getY2() {
        return y2;
    }

    
    // Returns true if the distance from the clicked point to the center of this
    // component is less than or equal to this component's selection radius.
    @Override
    public boolean isSelected(double x, double y) {
        if((x1-x)*(x1-x)+(y1-y)*(y1-y) <= selectionRadius*selectionRadius ||
           (x2-x)*(x2-x)+(y2-y)*(y2-y) <= selectionRadius*selectionRadius)
            return true;
        
        // Translate the coordinates to be centered arround (x1,y1)
        double xp = x-x1;
        double yp = y-y1;
        double x2p = x2-x1;
        double y2p = y2-y1;
        
        // Rotate the coordinates so that (x2,y2) is on the positive x-axis
        double theta = Math.atan2(x2p,y2p);
        double c = Math.cos(theta);
        double s = Math.sin(theta);
        double xpp = xp*s+yp*c;
        double ypp = xp*c-yp*s;
        double x2pp = x2p*s+y2p*c;
        double y2pp = x2p*c-y2p*s;
        
        // Check to see if the mouse click occured within the selection rectangle
        return 0<=xpp && xpp<=x2pp && Math.abs(ypp)<=selectionRadius;
    }
}
