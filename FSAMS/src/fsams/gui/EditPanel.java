/*
 */
package fsams.gui;

import fsams.FSAMS;
import fsams.components.FSAMSComponent1D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author FSAMS Team
 */
public class EditPanel extends JPanel implements MouseListener {
    
    public EditPanel(){
        addMouseListener(this);
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        FSAMS.components.paint(g);
    }

    // MouseListener
    @Override
    public void mousePressed(MouseEvent me) {
        FSAMSComponent1D comp = FSAMS.components.getComponent(me.getX(), me.getY(), getWidth(), getHeight());
        FSAMS.components.selectComponent(comp);
    }
    @Override
    public void mouseClicked(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}
}
