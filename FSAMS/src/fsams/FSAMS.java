package fsams;

import fsams.grid.Component;
import fsams.grid.Component.Sensor;
import fsams.grid.Grid;
import fsams.gui.ComponentsPanel;
import fsams.gui.EditPanel;
import javax.swing.JFrame;
import javax.swing.JSplitPane;


public final class FSAMS extends JFrame {
    // GUI Components
    private EditPanel editP;
    private ComponentsPanel compP;
    
    // World State
    private Grid grid;
    private Grid sim_grid;
    
    // Editor State
    Component.Type nextComponentType = null;
    
    public FSAMS() {
        editP = new EditPanel(this);
        compP = new ComponentsPanel(this);
        
        grid = new Grid();
        editP.setGrid(grid);
        
        initMainWindow();
        
        grid.addComponent(new Sensor(), 0, 1);
        grid.addWall(1,1,1,3);
        grid.addWall(2,2,4,2);
    }
    private void initMainWindow() {
        JSplitPane splitP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitP.add(compP, JSplitPane.LEFT);
        splitP.add(editP, JSplitPane.RIGHT);
        add(splitP);
        setSize(800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void setNextComponentType(Component.Type type) {
        editP.setNextComponentType(type);
    }
    
    public static void main(String[] args) {
        new FSAMS();
    }
}
