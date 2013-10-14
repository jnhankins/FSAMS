package fsams.gui;

import fsams.FSAMS;
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
    private final FSAMS fsams;
    
    public EditPanel(FSAMS fsams) {
        this.fsams = fsams;
        addMouseListener(this);
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        fsams.draw(g);
    }

    // MouseListener
    @Override
    public void mousePressed(MouseEvent me) {
        //actionManager.selectComponent(me.getX(), me.getY());
        if(fsams.getNextComponentType() != null) {
            fsams.addComponent(me.getX(), me.getY(), getWidth(), getHeight());
        }
        else {
            fsams.selectComponent(me.getX(), me.getY(), getWidth(), getHeight());
        }
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
