package fsams.gui;

/**
 *
 * @author Jeremiah
 */


public class View {
    private double centerX;  // The coordinates the View is centered on.
    private double centerY;
    private double scale;     // The scaling coefficient that controls the saling (aka zoom).   (0,inf)
    
    // Constructors
    public View(){
        centerX = 0;
        centerY = 0;
        scale = 3/100.0;
    }
    public View(double center_x, double center_y, double scale){
        if(Double.isInfinite(center_x) || Double.isNaN(center_x))
            throw new RuntimeException("center_x must be in range (-inf,inf): "+center_x);
        if(Double.isInfinite(center_y) || Double.isNaN(center_y))
            throw new RuntimeException("center_y must be in range (-inf,inf): "+center_y);
        if(scale<=0 || Double.isInfinite(scale) || Double.isNaN(scale))
            throw new RuntimeException("scale must be in range (0,inf): "+scale);
        this.centerX = center_x;
        this.centerY = center_y;
        this.scale = scale;
    }
    public View(View view) {
        centerX = view.centerX;
        centerY = view.centerY;
        scale = view.scale;
    }

    // Setters
    public void set(double center_x, double center_y, double scale) {
        if(Double.isInfinite(center_x) || Double.isNaN(center_x))
            throw new RuntimeException("center_x must be in range (-inf,inf): "+center_x);
        if(Double.isInfinite(center_y) || Double.isNaN(center_y))
            throw new RuntimeException("center_y must be in range (-inf,inf): "+center_y);
        if(scale<=0 || Double.isInfinite(scale) || Double.isNaN(scale))
            throw new RuntimeException("scale must be in range (0,inf): "+scale);
        this.centerX = center_x;
        this.centerY = center_y;
        this.scale = scale;
    }
    public void setCenter(double center_x, double center_y) {
        if(Double.isInfinite(center_x) || Double.isNaN(center_x))
            throw new RuntimeException("center_x must be in range (-inf,inf): "+center_x);
        if(Double.isInfinite(center_y) || Double.isNaN(center_y))
            throw new RuntimeException("center_y must be in range (-inf,inf): "+center_y);
        this.centerX = center_x;
        this.centerY = center_y;
    }
    public void setScale(double scale) {
        if(scale<=0 || Double.isInfinite(scale) || Double.isNaN(scale))
            throw new RuntimeException("scale must be in range (0,inf): "+scale);
        this.scale = scale;
    }
    // Getters
    public double getCenterX() {
        return centerX;
    }
    public double getCenterY() {
        return centerY;
    }
    public double getScale() {
        return scale;
    }
    //coordinate transformations
    public double toWorldCoordinateX(double x, double width, double height) {
        x = x - width/2.0;
        x = x * 2.0 / Math.min(width, height);
        x = x / scale + centerX;
        return x;
    }
    public double toWorldCoordinateY(double y, double width, double height) {
        y = -(y - height/2.0);
        y = y * 2.0 / Math.min(width, height);
        y = y / scale + centerY;
        return y;
    }
    public double toScreenCoordinateX(double x, double width, double height) {
        x = (x - centerX)*scale;
        x = x * Math.min(width, height) / 2.0;
        x = x + width/2.0;
        return x;
    }
    public double toScreenCoordinateY(double y, double width, double height) {
        y = (y - centerY)*scale;
        y = y * Math.min(width, height) / 2.0;
        y = -y + height/2.0;
        return y;
    }
    // toString
    @Override
    public String toString() {
        return "View("+centerX+","+centerY + ","+scale+")";
    }
}