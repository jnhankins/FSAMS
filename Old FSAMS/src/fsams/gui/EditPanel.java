package fsams.gui;

import fsams.FSAMS;
import fsams.components.FSAMSComponent1D;
import fsams.components.FSAMSComponent2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author FSAMS Team
 */
public class EditPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final FSAMS fsams;
    private boolean justSelected = false;
    private int clickedMouseX, clickedMouseY;
    private View view;
    
    public EditPanel(FSAMS fsams) {
        this.fsams = fsams;
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        fsams.draw(g, getWidth(), getHeight());
    }

    // MouseListener
    @Override
    public void mousePressed(MouseEvent me) {
        //actionManager.selectComponent(me.getX(), me.getY());
        if(fsams.getNextComponentType() != null) {
            fsams.addComponent(me.getX(), me.getY(), getWidth(), getHeight());
            fsams.selectComponent(me.getX(), me.getY(), getWidth(), getHeight());
            justSelected = true;
            clickedMouseX = me.getX();
            clickedMouseY = me.getY();
        }
        else {
            if(fsams.selectComponent(me.getX(), me.getY(), getWidth(), getHeight())){
                justSelected = true;
                clickedMouseX = me.getX();
                clickedMouseY = me.getY();
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent me) {
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        justSelected = false;
        //System.out.println("release");
        //System.out.println(me.getX());
    }
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        
        if(justSelected == true){
            int width = getWidth();
            int height = getHeight();
            view = fsams.getView();
            double dX = view.toWorldCoordinateX(e.getX(), width, height) - view.toWorldCoordinateX(clickedMouseX, width, height);
            double dY = view.toWorldCoordinateY(e.getY(), width, height) - view.toWorldCoordinateY(clickedMouseY, width, height);
            FSAMSComponent1D comp = fsams.getSelectedComponent();
            comp.setX1(comp.getX1() + dX);
            comp.setY1(comp.getY1() + dY);
            if(comp instanceof FSAMSComponent2D) {
                FSAMSComponent2D comp2d = (FSAMSComponent2D)comp;
                comp2d.setX2(comp2d.getX2() + dX);
                comp2d.setY2(comp2d.getY2() + dY);
            }
            clickedMouseX = e.getX();
            clickedMouseY = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
