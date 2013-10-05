/*
 * 
 */
package fsams.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;


/**
 *
 * @author FSAMS Team
 */
public class MainWindow extends JFrame {
    private EditPanel editP = new EditPanel();
    
    public MainWindow()
    {
        super("FSAMS");
        setSize(800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        add(editP, BorderLayout.CENTER);
        setVisible(true);
    }
}