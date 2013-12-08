package fsams;

import fsams.grid.ComponentType;
import fsams.grid.Grid;
import fsams.gui.ComponentsPanel;
import fsams.gui.ControlAreaPanel;
import fsams.gui.EditPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import java.awt.Color;
import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public final class FSAMS extends JFrame implements ActionListener {
    // GUI Components
    private JToolBar toolBar;
    private EditPanel editP;
    private ComponentsPanel compP;
    private ControlAreaPanel ctrlP;

    // World State
    public Grid grid;
    private Grid sim_grid;
    private Simulation simulation;
    
    // Project File
    String workingDirectory = null;
    String projectFileName = null;
    
    public FSAMS() {
        grid = new Grid();
        sim_grid = null;
        
        toolBar =  new JToolBar();
        editP = new EditPanel(this);
        editP.setGrid(grid);
        compP = new ComponentsPanel(this);
        
        simulation = new Simulation(editP);
        
        ctrlP = new ControlAreaPanel(this,simulation);
        
        initMainWindow();
        simulation.start();
    }
    private void initMainWindow() {
        JButton saveB = new JButton("Save");
        saveB.setActionCommand("save");
        saveB.addActionListener(this);
        toolBar.add(saveB);
        
        JButton openB = new JButton("Open");
        openB.setActionCommand("open");
        openB.addActionListener(this);
        toolBar.add(openB);
        
        JToggleButton startB = new JToggleButton("Start");
        startB.setActionCommand("start");
        startB.addActionListener(this);
        startB.setBackground(Color.green);
        toolBar.add(startB);
        
        JToggleButton stopB = new JToggleButton("Stop");
        stopB.setActionCommand("stop");
        stopB.addActionListener(this);
        stopB.setBackground(Color.red);
        toolBar.add(stopB);
        add(toolBar, BorderLayout.NORTH);
        
        JButton zoomIn = new JButton("Zoom In");
        zoomIn.setActionCommand("zoomIn");
        zoomIn.addActionListener(this);
        toolBar.add(zoomIn);
        add(toolBar, BorderLayout.NORTH);
        
        JButton zoomOut = new JButton("Zoom Out");
        zoomOut.setActionCommand("zoomOut");
        zoomOut.addActionListener(this);
        toolBar.add(zoomOut);
        add(toolBar, BorderLayout.NORTH);
        
        ButtonGroup startStop = new ButtonGroup();
        startStop.add(startB);
        startStop.add(stopB);
        
        JSplitPane controlP = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        controlP.add(compP, JSplitPane.TOP);
        controlP.add(ctrlP, JSplitPane.BOTTOM);
        add(controlP, BorderLayout.CENTER);
        
        JSplitPane splitP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitP.add(controlP, JSplitPane.LEFT);
        splitP.add(editP, JSplitPane.RIGHT);
        add(splitP, BorderLayout.CENTER);
        
        setSize(800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void setNextComponentType(ComponentType type) {
        editP.setNextComponentType(type);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        switch(ae.getActionCommand()){
            case "save":
                if(projectFileName==null) {
                    FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.SAVE);
                    if(workingDirectory!=null)
                        fd.setDirectory(workingDirectory);
                    fd.setFile("*.fsams");
                    fd.setVisible(true);
                    projectFileName = fd.getFile();
                    workingDirectory = fd.getDirectory();
                }
                if(projectFileName==null) break;
                try {
                    ProjectIO.saveProject(grid, projectFileName);
                } catch (ParserConfigurationException | TransformerException ex) {
                    JOptionPane.showMessageDialog(this, "An error was encoutned while saving the project file.");
                }
                break;
            case "open":
                FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
                if(workingDirectory!=null)
                    fd.setDirectory(workingDirectory);
                fd.setFile("*.fsams");
                fd.setVisible(true);
                projectFileName = fd.getFile();
                workingDirectory = fd.getDirectory();
                if(projectFileName==null) break;
                try {
                    ProjectIO.openProject(grid, projectFileName);
                } catch (ParserConfigurationException | SAXException | IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error was encoutned while opening the project file.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
                repaint();
                break;
            case "start":
                sim_grid = new Grid(grid);
                editP.setGrid(sim_grid);
                simulation.startSim(sim_grid);
                ctrlP.startSim();
                break;
            case "stop":
                sim_grid = null;
                editP.setGrid(grid);
                simulation.stopSim();
                ctrlP.stopSim();
                break;
            case "zoomIn":
                editP.zoomIn();
                repaint();
                break; 
            case "zoomOut":
                editP.zoomOut();
                repaint();
                break;            
            default:
                System.out.println("warning unknown action: "+ae.getActionCommand());
        }
    }
    
    public static void main(String[] args) {
        new FSAMS();
    }
    
    public void repaint() {
        editP.repaint();
    }
    
    public void zoomIn() {
        editP.zoomIn();
    }
    public void zoomOut() {
        editP.zoomOut();
    }
    
    public boolean isSimulationRunning() {
        return simulation.isSimRunning();
    }
}
