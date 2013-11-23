/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.grid;

/**
 *
 * @author auv_lab3
 */
public class Component {
    public static enum Type {
        Wall,
        Sensor, 
        Fire,
        HumanAgent
    }
    
    public static class Sensor extends Component {}
    public static class Fire extends Component {}
    
}
