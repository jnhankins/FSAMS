/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.grid;

/**
 *
 * @author FSAMS Team
 */
public class Component {
    public int location_x, location_y;
    
    public static enum Type {
        Wall,
        FireSensor, 
        Fire,
        HumanAgent,
        Exit,
        Suppressor
    }
    
    public static class FireSensor extends Component {}
    public static class Fire extends Component {}
    public static class HumanAgent extends Component {
        public int xDest, yDest;//IS THIS EVER USED?
    }
    public static class Exit extends Component{}
    public static class Suppressor extends Component{}
}
