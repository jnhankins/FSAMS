package fsams.gui;

import fsams.FSAMS;
import fsams.grid.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author FSAMS Team
 */
public class ComponentsPanel extends JPanel implements ActionListener {
    private final FSAMS fsams;
    
    private JButton wallB;
    private JButton doorB;
    private JButton exitB;
    private JButton sensorB;
    private JButton fireAlarmB;
    private JButton fireB;
    private JButton humanAgentB;
    private JButton jButton8;
    private JButton jButton9;
    private JButton cancelB;
    
    public ComponentsPanel(FSAMS fsams) {
        this.fsams = fsams;
        initComponents();
    }
    
    private void initComponents() {

        JLabel buildingL = new JLabel();
        wallB = new JButton();
        doorB = new JButton();
        exitB = new JButton();
        JLabel jLabel2 = new JLabel();
        sensorB = new JButton();
        fireAlarmB = new JButton();
        fireB = new JButton();
        JLabel jLabel3 = new JLabel();
        humanAgentB = new JButton();
        jButton8 = new JButton();
        jButton9 = new JButton();
        JLabel cancelL = new JLabel();
        cancelB = new JButton();
        Box.Filler bottomFiller = new Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        
        wallB.addActionListener(this);
        doorB.addActionListener(this);
        exitB.addActionListener(this);
        sensorB.addActionListener(this);
        fireAlarmB.addActionListener(this);
        fireB.addActionListener(this);
        humanAgentB.addActionListener(this);
        jButton8.addActionListener(this);
        jButton9.addActionListener(this);
        cancelB.addActionListener(this);
        
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

        sensorB.setText("Sensor1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ++row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(sensorB, gridBagConstraints);

        fireAlarmB.setText("Fire Alarm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireAlarmB, gridBagConstraints);

        fireB.setText("Fire");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = row;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(fireB, gridBagConstraints);
        
        
        jLabel3.setText("Sensors");
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
        
        if(src == wallB) {
            fsams.setNextComponentType(Component.Type.Wall);
        } else if(src == doorB) {
            
        } else if(src == exitB) {
            fsams.setNextComponentType(Component.Type.Exit);
        } else if(src == sensorB) {
            fsams.setNextComponentType(Component.Type.Sensor);
        } else if(src == fireAlarmB) {
            
        } else if(src == fireB) {
            fsams.setNextComponentType(Component.Type.Fire);
        } else if(src == humanAgentB) {
            fsams.setNextComponentType(Component.Type.HumanAgent);
        } else if(src == jButton8) {
            
        } else if(src == jButton9) {
            
        } else if(src == cancelB) {
            fsams.setNextComponentType(null);
        }
    }
    
}
