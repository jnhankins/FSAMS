package fsams;

import fsams.components.*;
import fsams.components.ComponentManager.ComponentType;
import fsams.gui.ComponentsPanel;
import fsams.gui.EditPanel;
import fsams.gui.PropertiesPanel;
import fsams.gui.View;
import fsams.logic.Simulation;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 *
 * @author bherrera22
 */
public final class FSAMS extends JFrame implements ActionListener {
    // Model/Data
    private ComponentManager components;
    // GUI/View
    private JToolBar toolBar;
    private EditPanel editP;
    private ComponentsPanel compP;
    private View view;
    private PropertiesPanel propertiesP;
    // State
    private FSAMSComponent1D selectedComponent;
    private ComponentType nextComponentType;
    private Simulation simulation;
    
    public FSAMS() {
        super("FSAMS");
        
        // Model/Data
        components = new ComponentManager();
        // GUI/View
        toolBar = new JToolBar();
        editP = new EditPanel(this);
        compP = new ComponentsPanel(this);
        propertiesP = new PropertiesPanel();
        view = new View();
        // State
        nextComponentType = null;
        simulation = new Simulation(editP);
        // Initialize components
        components.addComponent(new Wall(-5,5,5,5));
        components.addComponent(new Wall(5,5,5,-5));
        components.addComponent(new Wall(5,-5,-5,-5));
        components.addComponent(new Wall(-5,-5,-5,5));
        components.addComponent(new Wall(0,10,-10,0));
        components.addComponent(new Sensor(0,0));
        components.addComponent(new HumanAgent(-10, 3, 1, 0));
        
        
        // Construct the main window
        initMainWindow();
        simulation.start();
    }
    
    private void initMainWindow() {
        
        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split2.add(editP, JSplitPane.RIGHT);
        split2.add(compP, JSplitPane.LEFT);
        split1.add(split2, JSplitPane.LEFT);
        split1.add(propertiesP, JSplitPane.RIGHT);
        split1.setDividerLocation(5000);
        //propertiesP.setSize(new Dimension(200, 800));
        //split1.setResizeWeight(0.2);
        add(split1, BorderLayout.CENTER);
        JRadioButton componentB = new JRadioButton("Building Components");
        toolBar.add(componentB);
        JRadioButton features = new JRadioButton("Security Features");
        toolBar.add(features);
        JToggleButton start = new JToggleButton("start");
        toolBar.add(start);
        JButton stop = new JButton("Stop");
        toolBar.add(stop);
        stop.addActionListener(this);
        start.addActionListener(this);
        start.setActionCommand("start");
        stop.setActionCommand("stop");
        add(toolBar, BorderLayout.NORTH);
        setSize(800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public boolean selectComponent(int mouseX, int mouseY, int width, int height) {
        if(!simulation.isSimRunning()){
            double worldX = view.toWorldCoordinateX(mouseX, width, height);
            double worldY = view.toWorldCoordinateY(mouseY, width, height);
            FSAMSComponent1D comp = components.getComponent(worldX, worldY);
            selectComponent(comp);
            return comp != null;
        }
        return false;
    }
    
    public FSAMSComponent1D getSelectedComponent(){
        return selectedComponent;
    }
    
    public View getView() {
        return view;
    }
    
    private void selectComponent(FSAMSComponent1D newSelectedComponent) {
        if(!simulation.isSimRunning()){
            if(selectedComponent!=null)
                selectedComponent.setSelected(false);
            selectedComponent = newSelectedComponent;
            if(selectedComponent!=null)
                selectedComponent.setSelected(true);
            repaint();
        }
    }
    
    public void deleteSelectedComponent() {
        if(!simulation.isSimRunning()){
            if(selectedComponent!=null) {
                components.removeComponent(selectedComponent);
                selectedComponent = null;
                repaint();
            }
        }
    }
    
    public void draw(Graphics g) {
        if(simulation.isSimRunning()){
            //simulation.getComponents().drawComponents(g, view);
            view.drawWorld(components, g, WIDTH, HEIGHT);
        }
        else{
            components.drawComponents(g, view);
            view.drawWorld(components, g, WIDTH, HEIGHT);
        }
    }
    
    public void setNextComponentType(ComponentType type) {
        nextComponentType = type;
    }
    
    public ComponentType getNextComponentType() {
        return nextComponentType;
    }
    
    public void addComponent(int mouseX, int mouseY, int width, int height) {
        if(!simulation.isSimRunning()){
            
            if(nextComponentType==null) {
                System.out.println("oops");
                return;
            }

            double worldX = view.toWorldCoordinateX(mouseX, width, height);
            double worldY = view.toWorldCoordinateY(mouseY, width, height);

            System.out.println("switch"+nextComponentType.name());
            switch(nextComponentType) {
                case Wall: 
                    components.addComponent(new Wall(worldX-0.5, worldY, worldX+0.5, worldY));
                    break;
                case Sensor: 
                    components.addComponent(new Sensor(worldX, worldY));
                    break;
                case Fire:
                    components.addComponent(new Fire(worldX, worldY));
                    break;
                case HumanAgent:
                    System.out.println("humanAgent");
                    components.addComponent(new HumanAgent(worldX, worldY, 0, 0));
                    break;
                default: break;
            }
            
            setNextComponentType(null);
            
            repaint();
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FSAMS fsams = new FSAMS();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "start":
                simulation.startSim(components);
                break;
            case "stop":
                simulation.stopSim();
                break;
            default:
                System.out.println("warning unknown action: "+ae.getActionCommand());
        }
    }
    
    public ComponentManager getComponentManager() {
        return components;
    }
}
