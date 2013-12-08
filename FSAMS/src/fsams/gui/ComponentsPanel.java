package fsams.gui;

import fsams.FSAMS;
import fsams.grid.ComponentType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
/**
 *
 * @author FSAMS Team
 */
public class ComponentsPanel extends JPanel implements ActionListener {
    private final FSAMS fsams;
   
    private JToggleButton doorB;
    private JToggleButton exitB;
    private JToggleButton fireSensorB;
    private JToggleButton fireSuppressorB;
    private JToggleButton fireB;
    private JToggleButton humanAgentB;
    private JToggleButton equipmentB;
    private JToggleButton alarmB;
    private JButton cancelB;
    private JToggleButton wallB;
    private JToggleButton cancelHidden;
    
    public ComponentsPanel(FSAMS fsams) {
        this.fsams = fsams;
        initComponents();
    }
    
    private void initComponents() {

        JLabel buildingL = new JLabel();
        wallB = new JToggleButton();
        //wallB = new JButton();
        doorB = new JToggleButton();
        exitB = new JToggleButton();
        JLabel jLabel2 = new JLabel();
        fireSensorB = new JToggleButton();
        fireSuppressorB = new JToggleButton();
        fireB = new JToggleButton();
        JLabel jLabel3 = new JLabel();
        humanAgentB = new JToggleButton();
        alarmB = new JToggleButton();
        JLabel cancelL = new JLabel();
        cancelB = new JButton();
        cancelHidden = new JToggleButton();
        equipmentB = new JToggleButton();
        Box.Filler bottomFiller = new Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        
        wallB.setActionCommand("wall");
        doorB.setActionCommand("door");
        exitB.setActionCommand("exit");
        fireSensorB.setActionCommand("fireSensor");
        fireSuppressorB.setActionCommand("fireSuppressor");
        fireB.setActionCommand("fire");
        humanAgentB.setActionCommand("humanAgent");
        alarmB.setActionCommand("alarm");
        cancelB.setActionCommand("cancel");
        equipmentB.setActionCommand("equipment");
        
        wallB.addActionListener(this);
        doorB.addActionListener(this);
        exitB.addActionListener(this);
        fireSensorB.addActionListener(this);
        fireSuppressorB.addActionListener(this);
        fireB.addActionListener(this);
        humanAgentB.addActionListener(this);
        alarmB.addActionListener(this);
        cancelB.addActionListener(this);
        equipmentB.addActionListener(this);
        
        ButtonGroup toggleGroup = new ButtonGroup();
        toggleGroup.add(wallB);
        toggleGroup.add(fireB);
        toggleGroup.add(humanAgentB);
        toggleGroup.add(fireSensorB);
        toggleGroup.add(fireSuppressorB);
        toggleGroup.add(doorB);
        toggleGroup.add(exitB);
        toggleGroup.add(alarmB);
        toggleGroup.add(cancelB);
        toggleGroup.add(cancelHidden);
        toggleGroup.add(equipmentB);
              
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;
        int row=0;

        buildingL.setText("Building");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(buildingL, gridBagConstraints);

        wallB.setText("<html><u>W</u>all</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(wallB, gridBagConstraints);

        doorB.setText("<html><u>D</u>oor</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(doorB, gridBagConstraints);

        exitB.setText("<html>E<u>x</u>it</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(exitB, gridBagConstraints);

        jLabel2.setText("<html><u>S</u>ensors</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(jLabel2, gridBagConstraints);

        fireSensorB.setText("<html>Fire <u>S</u>ensor</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireSensorB, gridBagConstraints);

        fireSuppressorB.setText("<html>S<u>p</u>rinkler</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireSuppressorB, gridBagConstraints);

        fireB.setText("<html><u>F</u>ire</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireB, gridBagConstraints);
        
        
        jLabel3.setText("Objects");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(jLabel3, gridBagConstraints);

        humanAgentB.setText("<html><u>P</u>erson</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(humanAgentB, gridBagConstraints);

        equipmentB.setText("<html><u>E</u>quipment</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(equipmentB, gridBagConstraints);

        alarmB.setText("<html><u>A</u>larm</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(alarmB, gridBagConstraints);

        cancelL.setText("\n");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = ++row;
        add(cancelL, gridBagConstraints);

        cancelB.setText("<html><u>C</u>ancel</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(cancelB, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        add(bottomFiller, gridBagConstraints);
        
        wallB.setToolTipText("Adds a wall on the grid edge. Shortcut is ' w ' to select a wall"); 
        doorB.setToolTipText("Adds a door. Shortcut is ' d ' to select a door");
        exitB.setToolTipText("Adds an exit. Humans proceed towards it in the event of an alarm. Shortcut is ' e ' to select an exit"); 
        fireSensorB.setToolTipText("Adds a fire sensor. Shortcut is ' s ' to select a fire sensor");
        fireSuppressorB.setToolTipText("Adds a sprinkeler. Shortcut is ' k ' to select a sprinkeler");
        fireB.setToolTipText("Fire. It spreads! Shortcut is ' f ' to select fire");
        humanAgentB.setToolTipText("Person. Adds people who proceed to the exit. Shortcut is ' h '");
        equipmentB.setToolTipText("Equipment. Turn off to protect from spinklers. Shortcut is ' e '");
        alarmB.setToolTipText("");
        cancelB.setToolTipText("Cancels the simulation, reverting the simulation to it's previous state before it began running. Shortcut is ' c ' to cancel the simuluation.");

            wallB.doClick();

    }

    AbstractAction fireA = new AbstractAction() {                
         public void actionPerformed(ActionEvent ae) {
             fireB.doClick();
         }
     };
   AbstractAction doorA = new AbstractAction() {                
         public void actionPerformed(ActionEvent ae) {
             doorB.doClick();
         }
     };   
   AbstractAction exitA = new AbstractAction() {
         public void actionPerformed(ActionEvent ae) {
             exitB.doClick();
         }
     };   

   AbstractAction wallA = new AbstractAction() {
         public void actionPerformed(ActionEvent ae) {
             wallB.doClick();
         }
     };
   AbstractAction humanA = new AbstractAction() {
         public void actionPerformed(ActionEvent ae) {
             humanAgentB.doClick();
         }
     };
   AbstractAction sensorA = new AbstractAction() {
         public void actionPerformed(ActionEvent ae) {
             fireSensorB.doClick();
         }
     };  
   AbstractAction sprinklerA = new AbstractAction() {
         public void actionPerformed(ActionEvent ae) {
             fireSuppressorB.doClick();
         }
    }; 
   AbstractAction equipmentA = new AbstractAction() {
        public void actionPerformed(ActionEvent ae) {
            equipmentB.doClick();
        }
   };
   AbstractAction alarmA = new AbstractAction() {
        public void actionPerformed(ActionEvent ae) {
            alarmB.doClick();
        }
    };    
    AbstractAction cancelA = new AbstractAction() {
        public void actionPerformed(ActionEvent ae) {
            cancelB.doClick();
            cancelHidden.doClick();
        }
    };  
          
    public void actionPerformed(ActionEvent ae) {
        if(fsams.isSimulationRunning())
            return;
        
        Object src = ae.getSource();

        fireB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "fire");
        fireB.getActionMap().put("fire", fireA);
        doorB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "door");
        doorB.getActionMap().put("door", doorA);
        exitB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "exit");
        exitB.getActionMap().put("exit", exitA);
        wallB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "wall");
        wallB.getActionMap().put("wall", wallA);
        humanAgentB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "person");
        humanAgentB.getActionMap().put("person", humanA);
        fireSensorB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "sensor");
        fireSensorB.getActionMap().put("sensor", sensorA);
        fireSuppressorB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), "sprinkler");
        fireSuppressorB.getActionMap().put("sprinkler", sprinklerA);
        equipmentB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "equipment");
        equipmentB.getActionMap().put("equipment", equipmentA);
        alarmB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "alarm");
        alarmB.getActionMap().put("alarm", alarmA);
        cancelB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "cancel");
        cancelB.getActionMap().put("cancel", cancelA);
       
        switch(ae.getActionCommand()){
            case "wall": 
                fsams.setNextComponentType(ComponentType.Wall);
                break;
            case "door": 
                fsams.setNextComponentType(ComponentType.Door); 
                break;
            case "exit": 
                fsams.setNextComponentType(ComponentType.Exit);
                break;
            case "fireSensor": 
                fsams.setNextComponentType(ComponentType.FireSensor);
                break;
            case "fireSuppressor": 
                fsams.setNextComponentType(ComponentType.Sprinkler);
                break;
            case "fire": 
                fsams.setNextComponentType(ComponentType.Fire);
                break;
            case "humanAgent": 
                fsams.setNextComponentType(ComponentType.HumanAgent);
                break;
            case "equipment": 
                fsams.setNextComponentType(ComponentType.Equipment);
                break;
            case "alarm":  
                fsams.setNextComponentType(ComponentType.FireAlarm);
                break;
            case "cancel": 
                fsams.setNextComponentType(null);
                cancelHidden.setSelected(true);
                break;
        }
        
//        switch(src){
//            case "aa":
//                 fireB.doClick();
//        }
        
        
    }
    
}
