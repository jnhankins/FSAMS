package fsams.gui;

import fsams.components.*;
import java.awt.Color;
import java.awt.Graphics;

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
    
    
    public void drawWorld(ComponentManager componentManager, Graphics g, double width, double height) {
        for (FSAMSComponent1D c : componentManager.getComponents()) {
            if (c instanceof Wall) {
                FSAMSComponent2D c2D = (FSAMSComponent2D)c;
                width = g.getClipBounds().width;
                height = g.getClipBounds().height;
                double screenX1 = toScreenCoordinateX(c2D.getX1(), width, height);
                double screenY1 = toScreenCoordinateY(c2D.getY1(), width, height);
                double screenX2 = toScreenCoordinateX(c2D.getX2(), width, height);
                double screenY2 = toScreenCoordinateY(c2D.getY2(), width, height);

                if(c2D.isSelected()) {
                    g.setColor(Color.yellow);
                } else {
                    g.setColor(Color.orange);
                }

                g.drawLine((int)screenX1, (int)screenY1, (int)screenX2, (int)screenY2);
            }
            else if (c instanceof Sensor) {
                width = g.getClipBounds().width;
                height = g.getClipBounds().height;
                
                final double radius = 0.5;
                double screenX1 = toScreenCoordinateX(c.getX1()-radius/2.0, width, height);
                double screenY1 = toScreenCoordinateY(c.getY1()+radius/2.0, width, height);
                double screenW = radius*getScale()* Math.min(width, height)/2.0;
        
                if(c.isSelected()) {
                    g.setColor(Color.yellow);
                } else {
                    g.setColor(Color.pink);
                }

                g.drawOval( (int)screenX1,
                            (int)screenY1,
                            (int)screenW,
                            (int)screenW);
            }
            else if (c instanceof HumanAgent) {
                HumanAgent cHA = (HumanAgent)c;
                width = g.getClipBounds().width;
                height = g.getClipBounds().height;
        
                final double radius = 1;
                double screenX1 = toScreenCoordinateX(c.getX1()-radius/2.0, width, height);
                double screenY1 = toScreenCoordinateY(c.getY1()+radius/2.0, width, height);
                double screenW = radius*getScale()* Math.min(width, height)/2.0;

                if(c.isSelected()) {
                    g.setColor(Color.orange);
                } else {
                    g.setColor(Color.magenta);
                }

                g.drawOval( (int)screenX1,
                            (int)screenY1,
                            (int)screenW,
                            (int)screenW);
                g.setColor(Color.yellow);

                g.drawLine((int)toScreenCoordinateX(c.getX1(), width, height),
                        (int)toScreenCoordinateY(c.getY1(), width, height),
                        (int)toScreenCoordinateX(c.getX1()+cHA.getVX(), width, height),
                        (int)toScreenCoordinateY(c.getY1()+cHA.getVY(), width, height));
            }
            else if(c instanceof Fire){
                Fire cF = (Fire)c;
                
                width = g.getClipBounds().width;
                height = g.getClipBounds().height;

                final double radius = 0.5;
                double screenX1 = toScreenCoordinateX(cF.getX1()-radius/2.0, width, height);
                double screenY1 = toScreenCoordinateY(cF.getY1()+radius/2.0, width, height);
                double screenW = radius*getScale()* Math.min(width, height)/2.0;

                if(cF.isSelected()) {
                    g.setColor(Color.yellow);
                } else {
                    g.setColor(Color.red);
                }

                g.drawOval( (int)screenX1,
                            (int)screenY1,
                            (int)screenW,
                            (int)screenW);

                g.setColor(Color.red);
                for(Fire.Square s: cF.occupied) {
                    g.drawRect((int)(s.x*Fire.Square.size),(int)(s.y*Fire.Square.size),(int)((s.x+1)*Fire.Square.size),(int)((s.y+1)*Fire.Square.size));
                }
            }
        }
    }
}