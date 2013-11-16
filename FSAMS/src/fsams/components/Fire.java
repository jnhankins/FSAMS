package fsams.components;

import fsams.gui.View;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author FSAMS Team
 */
public class Fire extends FSAMSComponent1D {
    protected class Square implements Comparable<Square> {
        public static final double size = 0.5;
        public int x, y;
        public Square(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object o) {
            if(o == null)
                return false;
            if(!(o instanceof Square))
                return false;
            Square s = (Square)o;
            return x==s.x && y==s.y;
        }
        @Override
        public int compareTo(Square s) {
            if(s == null)
                return -1;
            if(x==s.x && y==s.y)
                return 0;
            if(x<s.x || y<s.y)
                return -1;
            return 1;
        }
    }
    protected Set<Square> innert = new TreeSet<>();
    protected Set<Square> occupied = new TreeSet<>();
    protected Set<Square> contiguous = new TreeSet<>();
    
    public Fire() {
        super();
    }
    public Fire(double x1, double y1) {
        super(x1, y1);
    }
    public Fire(Fire fire) {
        super(fire);
    }
    
    @Override
    public String getType() {
        return "Fire";
    }

    @Override
    public void draw(Graphics g, View v) {
        double width = g.getClipBounds().width;
        double height = g.getClipBounds().height;
        
        final double radius = 0.5;
        double screenX1 = v.toScreenCoordinateX(getX1()-radius/2.0, width, height);
        double screenY1 = v.toScreenCoordinateY(getY1()+radius/2.0, width, height);
        double screenW = radius*v.getScale()* Math.min(width, height)/2.0;
        
        if(isSelected) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.red);
        }
        
        g.drawOval( (int)screenX1,
                    (int)screenY1,
                    (int)screenW,
                    (int)screenW);
        
        g.setColor(Color.red);
        for(Square s: occupied) {
            g.drawRect((int)(s.x*Square.size),(int)(s.y*Square.size),(int)((s.x+1)*Square.size),(int)((s.y+1)*Square.size));
        }
        
    }

    @Override
    public FSAMSComponent1D copy() {
        return new Fire(this);
    }
    
    protected boolean intersectingLineSegment(double line1x1, double line1y1, double line1x2, double line1y2,
                                            double line2x1, double line2y1, double line2x2, double line2y2) {
        if(line1x1 == line1x2) {
            if(line2x1 == line2x2) {
                if(line1x1 == line2x1)
                    return true;
                else
                    return false;
            }
            // line1 verticle
            double m2 = (line2y2 - line2y1)/(line2x2 - line2x1); 
            double b2 = m2*line2x1-line2y1;
            double ix = line1x1;
            double iy = m2*ix+b2;
            if(!(line1y1<iy && iy<line1y2) && !(line1y2<iy && iy<line1y2))
                return false;
            if((line2x1<ix && line2x2<ix) || (line2x1>ix && line2x2>ix))
                return false;
            return true;
        } if(line2x1 == line2x2) {
            // line2 verticle
            double m1 = (line1y2 - line1y1)/(line1x2 - line1x1); 
            double b1 = m1*line1x1-line1y1;
            double ix = line1x1;
            double iy = m1*ix+b1;
            if(!(line2y1<iy && iy<line2y2) && !(line2y2<iy && iy<line2y2))
                return false;
            if((line1x1<ix && line1x2<ix) || (line1x1>ix && line1x2>ix))
                return false;
            return true;
        }
        double m1 = (line1y2 - line1y1)/(line1x2 - line1x1); 
        double b1 = m1*line1x1-line1y1;
        double ix = line1x1;
        double iy = m1*ix+b1;
        if(!(line2y1<iy && iy<line2y2) && !(line2y2<iy && iy<line2y2))
            return false;
        if((line1x1<ix && line1x2<ix) || (line1x1>ix && line1x2>ix))
            return false;
        return true;
    }
    
    protected void checkSquare(ComponentManager components, Square s) {
        if(!(occupied.contains(s) || innert.contains(s) || contiguous.contains(s))) {
            boolean intersects = false;
            for(FSAMSComponent1D comp: components.getComponents()) {
                if(comp instanceof Wall) { // Things fire cannot burn
                    FSAMSComponent2D comp2d = (FSAMSComponent2D)comp;
                    double x1 = comp2d.getX1();
                    double y1 = comp2d.getY1();
                    double x2 = comp2d.getX2();
                    double y2 = comp2d.getY2();
                    double c1x = s.x*Square.size;
                    double c1y = s.y*Square.size;
                    double c2x = (s.x+1)*Square.size;
                    double c2y = s.y*Square.size;
                    double c3x = (s.x+1)*Square.size;
                    double c3y = (s.y+1)*Square.size;
                    double c4x = s.x*Square.size;
                    double c4y = (s.y+1)*Square.size;
                    if(intersectingLineSegment(x1,y1,x2,y2,c1x,c1y,c2x,c2y)) {
                        intersects = true;
                        break;
                    }
                    if(intersectingLineSegment(x1,y1,x2,y2,c2x,c2y,c3x,c3y)) {
                        intersects = true;
                        break;
                    }
                    if(intersectingLineSegment(x1,y1,x2,y2,c3x,c3y,c4x,c4y)) {
                        intersects = true;
                        break;
                    }
                    if(intersectingLineSegment(x1,y1,x2,y2,c4x,c4y,c1x,c1y)) {
                        intersects = true;
                        break;
                    }
                }
            }
            
            if(intersects) innert.add(s);
            else contiguous.add(s);
        }
    }

    @Override
    public void update(ComponentManager components, double dTime) {
        contiguous.add(new Square((int)Math.floor(getX1()/Square.size),(int)Math.floor(getY1()/Square.size)));
        
        for(Square cont: contiguous) {
            contiguous.remove(cont);
            occupied.add(cont);
            // light the square on fire
            checkSquare(components, new Square(cont.x+1,cont.y  ));
            checkSquare(components, new Square(cont.x+1,cont.y+1));
            checkSquare(components, new Square(cont.x  ,cont.y+1));
            checkSquare(components, new Square(cont.x-1,cont.y+1));
            checkSquare(components, new Square(cont.x-1,cont.y  ));
            checkSquare(components, new Square(cont.x-1,cont.y-1));
            checkSquare(components, new Square(cont.x  ,cont.y-1));
            checkSquare(components, new Square(cont.x+1,cont.y-1));
        }
    }
}
