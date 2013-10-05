/*
 * TODO documentation
 */
package fsams;

import fsams.components.Wall;
import fsams.gui.MainWindow;

/**
 *
 * @author bherrera22
 */
public class FSAMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow xFactor = new MainWindow();
        xFactor.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        Wall w = new Wall(100, 100, 0, 0, 2);
        System.out.println(w.isSelected(0, 0));
        System.out.println(w.isSelected(10, 0));
        System.out.println(w.isSelected(10, 10));
        System.out.println(w.isSelected(5, -2));
        System.out.println(w.isSelected(5, 3));
    }
}
