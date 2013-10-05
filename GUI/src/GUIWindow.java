import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.Dimension;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Scotty007
 */
public class GUIWindow extends JFrame
{
    static public void main(String arg[])
    {
        JFrame main = new JFrame("FSAMS");

        main.setPreferredSize(new Dimension(800, 600));
        main.pack();
        main.setVisible(true);
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
