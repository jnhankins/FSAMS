/*
 */
package fsams.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author FSAMS Team
 */
public class EditPanel extends JPanel {
    public EditPanel(){
        
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
