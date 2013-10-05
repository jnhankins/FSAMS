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
    public boolean isSelected(int x, int y) {
        if((x1-x)*(x1-x)+(y1-y)*(y1-y) <= selectionRadius*selectionRadius ||
           (x2-x)*(x2-x)+(y2-y)*(y2-y) <= selectionRadius*selectionRadius)
            return true;
        double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        double sine = (y2 - y1)/d;
        double cosine = (x2 - x1)/d;
        double x1p = x1 * sine + y1 * cosine;
        double y1p = x1 * cosine - y1 * sine;
        double x2p = x2 * sine + y2 * cosine;
        //double y2p = x2 * cosine - y2 * sine;
        double xp = x * sine + y * cosine;
        double yp = x * cosine - y * sine;
        return x1p <= xp && xp <=x2p && Math.abs(yp - y1p) <= selectionRadius;
    }
}
