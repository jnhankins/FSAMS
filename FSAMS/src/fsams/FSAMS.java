package fsams;

import fsams.grid.Component;
import fsams.grid.Grid;
import fsams.gui.ComponentsPanel;
import fsams.gui.EditPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;


public final class FSAMS extends JFrame implements ActionListener {
    // GUI Components
    private JToolBar toolBar;
    private EditPanel editP;
    private ComponentsPanel compP;
    
    // World State
    private Grid grid;
    private Grid sim_grid;
    private Simulation simulation;
    
    public FSAMS() {
        toolBar =  new JToolBar();
        editP = new EditPanel(this);
        compP = new ComponentsPanel(this);
        
        grid = new Grid();
        sim_grid = null;
        simulation = new Simulation(editP);
        editP.setGrid(grid);
        
        initMainWindow();
        simulation.start();
    }
    private void initMainWindow() {
        JButton startB = new JButton("Start");
        startB.setActionCommand("start");
        startB.addActionListener(this);
        toolBar.add(startB);
        JButton stopB = new JButton("Stop");
        stopB.setActionCommand("stop");
        stopB.addActionListener(this);
        toolBar.add(stopB);
        add(toolBar, BorderLayout.NORTH);
        
        JSplitPane splitP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitP.add(compP, JSplitPane.LEFT);
        splitP.add(editP, JSplitPane.RIGHT);
        add(splitP, BorderLayout.CENTER);
        
        setSize(800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void setNextComponentType(Component.Type type) {
        editP.setNextComponentType(type);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "start":
                sim_grid = new Grid(grid);
                editP.setGrid(sim_grid);
                simulation.startSim(sim_grid);
                break;
            case "stop":
                sim_grid = null;
                editP.setGrid(grid);
                simulation.stopSim();
                break;
            default:
                System.out.println("warning unknown action: "+ae.getActionCommand());
        }
    }
    
    public static void main(String[] args) {
        new FSAMS();
    }
}
