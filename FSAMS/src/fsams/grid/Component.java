/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.grid;

import fsams.pathfinding.Path;
import fsams.pathfinding.PathFinder;

/**
 *
 * @author FSAMS Team
 */
public class Component {
    
    public Path path;
    public PathFinder finder;
    
    
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
    public static class HumanAgent extends Component{}
    public static class Exit extends Component{}
    public static class Suppressor extends Component{}
}
