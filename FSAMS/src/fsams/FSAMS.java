/*
 * TODO documentation
 */
package fsams;

import fsams.components.ComponentManager;
import fsams.components.Wall;
import fsams.gui.MainWindow;
import fsams.gui.View;

/**
 *
 * @author bherrera22
 */
public class FSAMS {
    static public View view = new View();
    static public ComponentManager components = new ComponentManager();
    static public MainWindow mainWindow = new MainWindow();
    
    FSAMS() {
        mainWindow.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        
        components.addComponent(new Wall(-5,5,5,5));
        components.addComponent(new Wall(5,5,5,-5));
        components.addComponent(new Wall(5,-5,-5,-5));
        components.addComponent(new Wall(-5,-5,-5,5));
        components.addComponent(new Wall(0,10,-10,0));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FSAMS fsams = new FSAMS();
    }
}
