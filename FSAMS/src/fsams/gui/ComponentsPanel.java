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
    private JToggleButton jButton8;
    private JToggleButton jButton9;
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
        jButton8 = new JToggleButton();
        jButton9 = new JToggleButton();
        JLabel cancelL = new JLabel();
        cancelB = new JButton();
        cancelHidden = new JToggleButton();
        Box.Filler bottomFiller = new Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        
        wallB.addActionListener(this);
        doorB.addActionListener(this);
        exitB.addActionListener(this);
        fireSensorB.addActionListener(this);
        fireSuppressorB.addActionListener(this);
        fireB.addActionListener(this);
        humanAgentB.addActionListener(this);
        jButton8.addActionListener(this);
        jButton9.addActionListener(this);
        cancelB.addActionListener(this);
        
        ButtonGroup toggleGroup = new ButtonGroup();
        toggleGroup.add(wallB);
        toggleGroup.add(fireB);
        toggleGroup.add(humanAgentB);
        toggleGroup.add(fireSensorB);
        toggleGroup.add(fireSuppressorB);
        toggleGroup.add(doorB);
        toggleGroup.add(exitB);
        toggleGroup.add(jButton8);
        toggleGroup.add(jButton9);
        toggleGroup.add(cancelB);
        toggleGroup.add(cancelHidden);
              
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

        wallB.setText("Wall");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(wallB, gridBagConstraints);

       doorB.setText("Door");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(doorB, gridBagConstraints);

        exitB.setText("Exit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(exitB, gridBagConstraints);

        jLabel2.setText("Sensors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(jLabel2, gridBagConstraints);

        fireSensorB.setText("Fire Sensor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireSensorB, gridBagConstraints);

        fireSuppressorB.setText("Sprinkler");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireSuppressorB, gridBagConstraints);

        fireB.setText("Fire");
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

        humanAgentB.setText("Human Agent");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(humanAgentB, gridBagConstraints);

        jButton8.setText("Door");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton8, gridBagConstraints);

        jButton9.setText("Elevator");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton9, gridBagConstraints);

        cancelL.setText("\n");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = ++row;
        add(cancelL, gridBagConstraints);

        cancelB.setText("cancel");
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
        humanAgentB.setToolTipText("Humans. Adds people who proceed to the exit. Shortcut is ' h '");
        jButton8.setToolTipText("");
        jButton9.setToolTipText("");
        cancelB.setToolTipText("Cancels the simulation, reverting the simulation to it's previous state before it began running. Shortcut is ' c ' to cancel the simuluation.");


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
          AbstractAction doorASecond = new AbstractAction() {
                public void actionPerformed(ActionEvent ae) {
                    jButton8.doClick();
                }
            };
          AbstractAction elevatorA = new AbstractAction() {
                public void actionPerformed(ActionEvent ae) {
                    jButton9.doClick();
                }
            };    
          AbstractAction cancelA = new AbstractAction() {
                public void actionPerformed(ActionEvent ae) {
                    cancelB.doClick();
                    cancelHidden.doClick();
                }
            };  
          
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        
       fireB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "fire");
       fireB.getActionMap().put("fire", fireA);
       doorB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "door");
       doorB.getActionMap().put("door", doorA);
       exitB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "exit");
       exitB.getActionMap().put("exit", exitA);
       wallB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "wall");
       wallB.getActionMap().put("wall", wallA);
       humanAgentB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "human");
       humanAgentB.getActionMap().put("human", humanA);
       fireSensorB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "sensor");
       fireSensorB.getActionMap().put("sensor", sensorA);
       fireSuppressorB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), "alarm");
       fireSuppressorB.getActionMap().put("sprinkler", sprinklerA);
       jButton8.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0), "door");
       jButton8.getActionMap().put("door", doorASecond);
       jButton9.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "elevator");
       jButton9.getActionMap().put("elevator", elevatorA);
       cancelB.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "cancel");
       cancelB.getActionMap().put("cancel", cancelA);

        
        if(src == wallB) {
            fsams.setNextComponentType(ComponentType.Wall);
        } else if(src == doorB) {
            
        } else if(src == exitB) {
            fsams.setNextComponentType(ComponentType.Exit);
        } else if(src == fireSensorB) {
            fsams.setNextComponentType(ComponentType.FireSensor);
        } else if(src == fireSuppressorB) {
            fsams.setNextComponentType(ComponentType.Suppressor);
        } else if(src == fireB) {
            fsams.setNextComponentType(ComponentType.Fire);
        } else if(src == humanAgentB) {
            fsams.setNextComponentType(ComponentType.HumanAgent);
        } else if(src == jButton8) {
            
        } else if(src == jButton9) {
            
        } else if(src == cancelB) {
            fsams.setNextComponentType(null);
            cancelHidden.setSelected(true);
            
            
        }
        
//        switch(src){
//            case "aa":
//                 fireB.doClick();
//        }
        
        
    }
    
}
