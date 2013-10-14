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
    
    private JComboBox componentCB;
    private JButton wallB;
    private JButton jButton2;
    private JButton jButton3;
    private JButton sensorB;
    private JButton jButton5;
    private JButton jButton6;
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
        jButton2 = new JButton();
        jButton3 = new JButton();
        JLabel jLabel2 = new JLabel();
        sensorB = new JButton();
        jButton5 = new JButton();
        jButton6 = new JButton();
        JLabel cancelL = new JLabel();
        cancelB = new JButton();
        Box.Filler bottomFiller = new Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        
        componentCB.addActionListener(this);
        wallB.addActionListener(this);
        jButton2.addActionListener(this);
        jButton3.addActionListener(this);
        sensorB.addActionListener(this);
        jButton5.addActionListener(this);
        jButton6.addActionListener(this);
        cancelB.addActionListener(this);
        
        setLayout(new java.awt.GridBagLayout());
        
        componentCB.setModel(new DefaultComboBoxModel(new String[] { "Wall", "Sensor" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(componentCB, gridBagConstraints);

        buildingL.setText("Building");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(buildingL, gridBagConstraints);

        wallB.setText("Wall");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(wallB, gridBagConstraints);

        jButton2.setText("jButton2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton2, gridBagConstraints);

        jButton3.setText("jButton3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton3, gridBagConstraints);

        jLabel2.setText("Sensors");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 0);
        add(jLabel2, gridBagConstraints);

        sensorB.setText("Sensor1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(sensorB, gridBagConstraints);

        jButton5.setText("jButton5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton5, gridBagConstraints);

        jButton6.setText("jButton6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jButton6, gridBagConstraints);

        cancelL.setText("Cancel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        add(cancelL, gridBagConstraints);

        cancelB.setText("cancel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(cancelB, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
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
        } else if(src == jButton2) {
            
        } else if(src == jButton3) {
            
        } else if(src == sensorB) {
            fsams.setNextComponentType(ComponentType.Sensor);
        } else if(src == jButton5) {
            
        } else if(src == jButton6) {
            
        } else if(src == cancelB) {
            fsams.setNextComponentType(null);
        }
    }
    
}
