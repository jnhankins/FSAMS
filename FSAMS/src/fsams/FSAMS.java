package fsams;

import fsams.components.ComponentManager;
import fsams.components.ComponentManager.ComponentType;
import fsams.components.FSAMSComponent1D;
import fsams.components.Sensor;
import fsams.components.Wall;
import fsams.gui.ComponentsPanel;
import fsams.gui.EditPanel;
import fsams.gui.View;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 *
 * @author bherrera22
 */
public final class FSAMS  extends JFrame {
    // Model/Data
    private ComponentManager components;
    // GUI/View
    private EditPanel editP;
    private ComponentsPanel compP;
    private View view;
    // State
    private FSAMSComponent1D selectedComponent;
    private ComponentType nextComponentType;
    
    public FSAMS() {
        super("FSAMS");
        
        // Model/Data
        components = new ComponentManager();
        // GUI/View
        editP = new EditPanel(this);
        compP = new ComponentsPanel(this);
        view = new View();
        // State
        nextComponentType = null;
        
        // Initialize components
        components.addComponent(new Wall(-5,5,5,5));
        components.addComponent(new Wall(5,5,5,-5));
        components.addComponent(new Wall(5,-5,-5,-5));
        components.addComponent(new Wall(-5,-5,-5,5));
        components.addComponent(new Wall(0,10,-10,0));
        components.addComponent(new Sensor(0,0));
        
        // Construct the main window
        initMainWindow();
    }
    
    private void initMainWindow() {
        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split2.add(editP, JSplitPane.RIGHT);
        split2.add(compP, JSplitPane.LEFT);
        split1.add(split2, JSplitPane.LEFT);
        add(split1, BorderLayout.CENTER);
        setSize(800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void selectComponent(int mouseX, int mouseY, int width, int height) {
        double worldX = view.toWorldCoordinateX(mouseX, width, height);
        double worldY = view.toWorldCoordinateY(mouseY, width, height);
        FSAMSComponent1D comp = components.getComponent(worldX, worldY);
        selectComponent(comp);
    }
    
    private void selectComponent(FSAMSComponent1D newSelectedComponent) {
        if(selectedComponent!=null)
            selectedComponent.setSelected(false);
        selectedComponent = newSelectedComponent;
        if(selectedComponent!=null)
            selectedComponent.setSelected(true);
        repaint();
    }
    
    public void deleteSelectedComponent() {
        if(selectedComponent!=null) {
            components.removeComponent(selectedComponent);
            selectedComponent = null;
            repaint();
        }
    }
    
    public void draw(Graphics g) {
        components.drawComponents(g, view);
    }
    
    public void setNextComponentType(ComponentType type) {
        nextComponentType = type;
    }
    
    public ComponentType getNextComponentType() {
        return nextComponentType;
    }
    
    public void addComponent(int mouseX, int mouseY, int width, int height) {
        if(nextComponentType==null)
            return;
        
        double worldX = view.toWorldCoordinateX(mouseX, width, height);
        double worldY = view.toWorldCoordinateY(mouseY, width, height);

        switch(nextComponentType) {
            case Wall: 
                components.addComponent(new Wall(worldX-0.5, worldY, worldX+0.5, worldY));
                break;
            case Sensor: 
                components.addComponent(new Sensor(worldX, worldY));
                break;
            default: break;
        }

        repaint();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FSAMS fsams = new FSAMS();
    }
}
