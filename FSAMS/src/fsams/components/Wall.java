/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.components;

import java.awt.Graphics;

/**
 *
 * @author FSAMS Team
 */
public class Wall extends FSAMSComponent2D{
    public Wall() {
        super();
    }
    public Wall(int x1, int y1, int x2, int y2, int radius) {
        super(x1, y1, x2, y2, radius);
    }
    public Wall(Wall wall) {
        super(wall);

    }
    @Override
    public String getType() {
        return "Wall";
    }

    @Override
    public void paint(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
