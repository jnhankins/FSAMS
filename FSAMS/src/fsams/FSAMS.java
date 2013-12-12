package fsams;

import fsams.grid.ComponentType;
import fsams.grid.Grid;
import fsams.grid.Tile;
import fsams.gui.ComponentsPanel;
import fsams.gui.ControlAreaPanel;
import fsams.gui.EditPanel;
import fsams.gui.TimerPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import java.awt.Color;
import java.awt.FileDialog;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * Starts the program
 * @author FSAMS Team
 */
public final class FSAMS extends JFrame implements ActionListener {
    // GUI Components
    private final JToolBar toolBar;
    private final EditPanel editP;
    private final ComponentsPanel compP;
    private final ControlAreaPanel ctrlP;
    private final TimerPanel timerP;

    // World State
    private final Grid grid;
    private Grid sim_grid;
    private final Simulation simulation;
    
    // Project File
    String workingDirectory = null;
    String projectFileName = null;
    
    // File Menu
    JMenuBar menuBar;
    JMenuItem doorLockMI;
    JMenuItem doorUnlockMI;
    JMenuItem sprinklerEnableMI;
    JMenuItem sprinklerDisableMI;
    JMenuItem alarmEnableMI;
    JMenuItem alarmDisableMI;
    JMenuItem equipmentEnableMI;
    JMenuItem equipmentDisableMI;
    JMenuItem masterResetMI;
    JMenuItem callEmergencyMI;
    
    /**
     * Creates a new FSAMS
     */
    public FSAMS() {
        grid = new Grid();
        sim_grid = null;
        
        toolBar =  new JToolBar();
        editP = new EditPanel(this);
        simulation = new Simulation(editP);
        editP.setGrid(grid);
        compP = new ComponentsPanel(this);
        
        ctrlP = new ControlAreaPanel(simulation);
        timerP = new TimerPanel(simulation);
        simulation.setTimerP(timerP);
                
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
        
        JSplitPane timeP = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        timeP.add(controlP, JSplitPane.TOP);
        timeP.add(timerP, JSplitPane.BOTTOM);
        add(timeP, BorderLayout.CENTER);
        
        JSplitPane splitP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitP.add(timeP, JSplitPane.LEFT);
        splitP.add(editP, JSplitPane.RIGHT);
        add(splitP, BorderLayout.CENTER);
        
        menuBar = new JMenuBar();
        JMenu fileM = new JMenu("File");
        JMenu editM = new JMenu("Edit");
        JMenu viewM = new JMenu("View");
        JMenu simulationM = new JMenu("Simulation");
        menuBar.add(fileM);
        menuBar.add(editM);
        menuBar.add(viewM);
        menuBar.add(simulationM);
        
        JMenuItem newMI = new JMenuItem("New");
        newMI.setActionCommand("new");
        newMI.addActionListener(this);
        
        JMenuItem saveMI = new JMenuItem("Save");
        saveMI.setActionCommand("save");
        saveMI.addActionListener(this);
        
        JMenuItem openMI = new JMenuItem("Open");
        openMI.setActionCommand("open");
        openMI.addActionListener(this);
        
        JMenuItem exitFSAMSMI = new JMenuItem("Exit");
        exitFSAMSMI.setActionCommand("exit");
        exitFSAMSMI.addActionListener(this);
        
        fileM.add(newMI);
        fileM.add(saveMI);
        fileM.add(openMI);
        fileM.addSeparator();
        fileM.add(exitFSAMSMI);
        
        JMenuItem wallMI = new JMenuItem("Wall");
        JMenuItem doorMI = new JMenuItem("Door");
        JMenuItem exitMI = new JMenuItem("Exit");
        JMenuItem sensorMI = new JMenuItem("Sensor");
        JMenuItem sprinklerMI = new JMenuItem("Sprinkler");
        JMenuItem fireMI = new JMenuItem("Fire");
        JMenuItem humanAgentMI = new JMenuItem("Person");
        JMenuItem alarmMI = new JMenuItem("Alarm");
        JMenuItem equipmentMI = new JMenuItem("Equipment");
        JMenuItem cancelMI = new JMenuItem("Cancel");
        
        wallMI.setActionCommand("wall");
        doorMI.setActionCommand("door");
        exitMI.setActionCommand("exit");
        sensorMI.setActionCommand("fireSensor");
        sprinklerMI.setActionCommand("fireSuppressor");
        fireMI.setActionCommand("fire");
        humanAgentMI.setActionCommand("humanAgent");
        alarmMI.setActionCommand("alarm");
        equipmentMI.setActionCommand("equipment");
        cancelMI.setActionCommand("cancel");
        
        wallMI.addActionListener(compP);
        doorMI.addActionListener(compP);
        exitMI.addActionListener(compP);
        sensorMI.addActionListener(compP);
        sprinklerMI.addActionListener(compP);
        fireMI.addActionListener(compP);
        humanAgentMI.addActionListener(compP);
        alarmMI.addActionListener(compP);
        equipmentMI.addActionListener(compP);
        cancelMI.addActionListener(compP);
        
        editM.add(wallMI);
        editM.add(doorMI);
        editM.add(exitMI);
        editM.add(sensorMI);
        editM.add(sprinklerMI);
        editM.add(fireMI);
        editM.add(humanAgentMI);
        editM.add(alarmMI);
        editM.add(equipmentMI);
        editM.add(cancelMI);
        
        JMenuItem zoomInMI = new JMenuItem("Zoom In");
        JMenuItem zoomOutMI = new JMenuItem("Zoom Out");
        zoomInMI.setActionCommand("zoomIn");
        zoomOutMI.setActionCommand("zoomOut");
        zoomInMI.addActionListener(this);
        zoomOutMI.addActionListener(this);
        viewM.add(zoomInMI);
        viewM.add(zoomOutMI);
        
        JMenuItem startMI = new JMenuItem("Start");
        JMenuItem stopMI = new JMenuItem("Stop");
        startMI.setActionCommand("start");
        stopMI.setActionCommand("stop");
        startMI.addActionListener(this);
        stopMI.addActionListener(this);
        simulationM.add(startMI);
        simulationM.add(stopMI);
        simulationM.addSeparator();
        
        doorLockMI = new JMenuItem("Lock Doors");
        doorUnlockMI = new JMenuItem("Unlock Doors");
        sprinklerEnableMI = new JMenuItem("Activate Sprinklers");
        sprinklerDisableMI = new JMenuItem("Deactivate Sprinklers");
        alarmEnableMI = new JMenuItem("Activate Alarms");
        alarmDisableMI = new JMenuItem("Decativate Alarms");
        equipmentEnableMI = new JMenuItem("Power Equipment");
        equipmentDisableMI = new JMenuItem("Shutdown Equipment");
        masterResetMI = new JMenuItem("Master Reset");
        callEmergencyMI = new JMenuItem("Call Emergency Services");
        
        doorLockMI.setActionCommand("doorLock");
        doorUnlockMI.setActionCommand("doorUnlock");
        sprinklerEnableMI.setActionCommand("sprinklerEnable");
        sprinklerDisableMI.setActionCommand("sprinklerDisable");
        alarmEnableMI.setActionCommand("alarmEnable");
        alarmDisableMI.setActionCommand("alarmDisable");
        equipmentEnableMI.setActionCommand("equipmentEnable");
        equipmentDisableMI.setActionCommand("equipmentDisable");
        masterResetMI.setActionCommand("masterReset");
        callEmergencyMI.setActionCommand("callEmergency");
        
        doorLockMI.addActionListener(ctrlP);
        doorUnlockMI.addActionListener(ctrlP);
        sprinklerEnableMI.addActionListener(ctrlP);
        sprinklerDisableMI.addActionListener(ctrlP);
        alarmEnableMI.addActionListener(ctrlP);
        alarmDisableMI.addActionListener(ctrlP);
        equipmentEnableMI.addActionListener(ctrlP);
        equipmentDisableMI.addActionListener(ctrlP);
        masterResetMI.addActionListener(ctrlP);
        callEmergencyMI.addActionListener(ctrlP);
        
        doorLockMI.setEnabled(false);
        doorUnlockMI.setEnabled(false);
        sprinklerEnableMI.setEnabled(false);
        sprinklerDisableMI.setEnabled(false);
        alarmEnableMI.setEnabled(false);
        alarmDisableMI.setEnabled(false);
        equipmentEnableMI.setEnabled(false);
        equipmentDisableMI.setEnabled(false);
        masterResetMI.setEnabled(false);
        callEmergencyMI.setEnabled(false);
        
        simulationM.add(doorLockMI);
        simulationM.add(doorUnlockMI);
        simulationM.add(sprinklerEnableMI);
        simulationM.add(sprinklerDisableMI);
        simulationM.add(alarmEnableMI);
        simulationM.add(alarmDisableMI);
        simulationM.add(equipmentEnableMI);
        simulationM.add(equipmentDisableMI);
        simulationM.add(masterResetMI);
        simulationM.add(callEmergencyMI);
        
        setJMenuBar(menuBar);
        
        setSize(800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /**
     * Sets the next component to the specified type
     * @param type The type to set the next component
     */
    public void setNextComponentType(ComponentType type) {
        editP.setNextComponentType(type);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "exit":
                System.exit(0);
            case "new":
                for(int x=0; x<Grid.grid_width; x++) {
                    for(int y=0; y<Grid.grid_height; y++) {
                        Tile t = grid.getTiles()[x][y];
                        t.setWallD(false);
                        t.setWallD(false);
                        t.setWallL(false);
                        t.setWallU(false);
                        t.setWallR(false);
                        t.setDoorD(false);
                        t.setDoorL(false);
                        t.setDoorU(false);
                        t.setDoorR(false);
                        t.setFireSensor(false);
                        t.setFireAlarm(false);
                        t.setSprinkler(false);
                        t.setHumanAgent(false);
                        t.setEquipment(false);
                        t.setFire(false);
                        t.setExit(false);
                        t.setIntruder(false);
                        t.setCamera(false);
                    }
                }
                repaint();
                break;
            case "save":
                {
                    FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.SAVE);
                    if(workingDirectory!=null)
                        fd.setDirectory(workingDirectory);
                    fd.setFile("*.fsams");
                    fd.setVisible(true);
                    projectFileName = fd.getFile();
                    workingDirectory = fd.getDirectory();
                    if(projectFileName==null) break;
                    try {
                        ProjectIO.saveProject(grid, workingDirectory + projectFileName);
                    } catch (ParserConfigurationException | TransformerException ex) {
                        JOptionPane.showMessageDialog(this, "An error was encoutned while saving the project file.");
                    }
                }
                break;
            case "open":
                {
                    FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
                    if(workingDirectory!=null)
                        fd.setDirectory(workingDirectory);
                    fd.setFile("*.fsams");
                    fd.setVisible(true);
                    projectFileName = fd.getFile();
                    workingDirectory = fd.getDirectory();
                    if(projectFileName==null) break;
                    try {
                        ProjectIO.openProject(grid, workingDirectory + projectFileName);
                    } catch (ParserConfigurationException | SAXException | IOException ex) {
                        JOptionPane.showMessageDialog(this, "An error was encoutned while opening the project file.");
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    }
                    repaint();
                }
                break;
            case "start":
                doorLockMI.setEnabled(true);
                doorUnlockMI.setEnabled(true);
                sprinklerEnableMI.setEnabled(true);
                sprinklerDisableMI.setEnabled(true);
                alarmEnableMI.setEnabled(true);
                alarmDisableMI.setEnabled(true);
                equipmentEnableMI.setEnabled(true);
                equipmentDisableMI.setEnabled(true);
                masterResetMI.setEnabled(true);
                callEmergencyMI.setEnabled(true);
                
                sim_grid = new Grid(grid);
                editP.setGrid(sim_grid);
                simulation.startSim(sim_grid);
                ctrlP.startSim();
                
                break;
            case "stop":
                doorLockMI.setEnabled(false);
                doorUnlockMI.setEnabled(false);
                sprinklerEnableMI.setEnabled(false);
                sprinklerDisableMI.setEnabled(false);
                alarmEnableMI.setEnabled(false);
                alarmDisableMI.setEnabled(false);
                equipmentEnableMI.setEnabled(false);
                equipmentDisableMI.setEnabled(false);
                masterResetMI.setEnabled(false);
                callEmergencyMI.setEnabled(false);
                
                timerP.stop();
                
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
    
    /**
     * Starts the program
     * @param args Command line arguments. FSAMS does not take command line arguments.
     */
    public static void main(String[] args) {
        new FSAMS();
    }
    
    @Override
    public void repaint() {
        editP.repaint();
    }
    
    /**
     * Increases the viewing scale of the grid.
     */
    public void zoomIn() {
        editP.zoomIn();
    }

    /**
     * Decreases the viewing scale of the grid.
     */
    public void zoomOut() {
        editP.zoomOut();
    }
    
    /**
     * Checks to see if simulation is currently running
     * @return true if simulation is running.
     */
    public boolean isSimulationRunning() {
        return simulation.isSimRunning();
    }
}
