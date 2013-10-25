package fsams.gui;

import fsams.FSAMS;
import fsams.components.ComponentManager.ComponentType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author FSAMS Team
 */
public class ComponentsPanel extends JPanel implements ActionListener {
    private final FSAMS fsams;
    
    private int row=0;
    private int column=0;
    private JComboBox componentCB;
    private JButton wallB;
    private JButton doorB;
    private JButton elevatorB;
    private JButton sensorB;
    private JButton fireAlarm;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;
    private JButton cancelB;
    
    public ComponentsPanel(FSAMS fsams) {
        this.fsams = fsams;
        initComponents();
    }
    
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        componentCB = new JComboBox();
        JLabel buildingL = new JLabel();
        wallB = new JButton();
        doorB = new JButton();
        elevatorB = new JButton();
        JLabel jLabel2 = new JLabel();
        sensorB = new JButton();
        fireAlarm = new JButton();
        jButton6 = new JButton();
        JLabel jLabel3 = new JLabel();
        jButton7 = new JButton();
        jButton8 = new JButton();
        jButton9 = new JButton();
        JLabel cancelL = new JLabel();
        cancelB = new JButton();
        Box.Filler bottomFiller = new Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        
        componentCB.addActionListener(this);
        wallB.addActionListener(this);
        doorB.addActionListener(this);
        elevatorB.addActionListener(this);
        sensorB.addActionListener(this);
        fireAlarm.addActionListener(this);
        jButton6.addActionListener(this);
        cancelB.addActionListener(this);
        
        setLayout(new java.awt.GridBagLayout());
        
        componentCB.setModel(new DefaultComboBoxModel(new String[] { "Wall", "Sensor", "Door" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(componentCB, gridBagConstraints);

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

        elevatorB.setText("Elevator");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(elevatorB, gridBagConstraints);

        jLabel2.setText("Sensors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(jLabel2, gridBagConstraints);

        sensorB.setText("Sensor1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(sensorB, gridBagConstraints);

        fireAlarm.setText("Fire Alarm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireAlarm, gridBagConstraints);

        jButton6.setText("jButton6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton6, gridBagConstraints);
        
        
        jLabel3.setText("Sensors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(jLabel3, gridBagConstraints);

        jButton7.setText("Wall");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton7, gridBagConstraints);

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

        cancelL.setText("Cancel");
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
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if(src == componentCB) {
            String cbSelected = (String)componentCB.getSelectedItem();
            switch(cbSelected) {
                case "Wall": 
                    fsams.setNextComponentType(ComponentType.Wall);
                    break;
                case "Sensor": 
                    fsams.setNextComponentType(ComponentType.Sensor);
                    break;
                default: break;
            }
            
        } else if(src == wallB) {
            fsams.setNextComponentType(ComponentType.Wall);
        } else if(src == doorB) {
            
        } else if(src == elevatorB) {
            
        } else if(src == sensorB) {
            fsams.setNextComponentType(ComponentType.Sensor);
        } else if(src == fireAlarm) {
            
        } else if(src == jButton6) {
            
        } else if(src == cancelB) {
            fsams.setNextComponentType(null);
        }
    }
    
}
