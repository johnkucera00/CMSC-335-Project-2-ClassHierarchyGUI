/*
* File: Cone.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program is meant to accompany MenuGUI.java and is dedicated
* to constructing Cone objects and the Cone menu. Cone overrides createMenu() 
* method from Shape. Cone(int coneRadius, int coneHeight) constructor 
* uses the arguments as radius/height and uses that to determine 
* volume and slant height. calculateConeVolume contains the formula to
* calculate volume and calculateSlantHeight contains the formula to calculate
* slant height. createMenu() overrides the Shape method and creates the menu
* panel specific to Cone option. Validator is an inner class dedicated
* to listening to keys pressed in the input textfield and notifies the user if the
* input is valid or not. BtnListener is an inner class dedicated to
* listen to the calculate button and outputs the results if the input
* is valid in addition to creating a frame for the visual. 
*
* The source of cone.jpg is cited here and in lines 201-204 under the file load.
*
* References:
*   Cangiano, A. (2007). Cone. Retrieved November 15, 2020, 
*       from https://mathblog.com/reference/geometry/cone/
*/

// import necessary Java classes
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Cone class, child of Shape
public class Cone extends Shape {
    
    // Variable initialization
    private int coneRadius, coneHeight = 0;
    private double volume, slantHeight = 0.0;
    private final JTextField input1Txt = new JTextField("");
    private final JTextField input2Txt = new JTextField("");
    private final JTextArea output1Txt = new JTextArea("");
    private final JTextArea output2Txt = new JTextArea("");
    private final JLabel inputMsg = new JLabel("<HTML><U>Input Positive "
                                               +"Integer(s) below :</U></HTML>");

    // No-argument constructor
    public Cone () {
        coneRadius = 0;
        coneHeight = 0;
        volume = 0.0;
        slantHeight = 0.0;
    } // end of method
    
    // Constructor
    public Cone (int coneRadius, int coneHeight) {
        this.coneRadius = coneRadius;
        this.coneHeight = coneHeight;
        volume = calculateConeVolume(coneRadius, coneHeight);
        slantHeight = calculateSlantHeight(coneRadius, coneHeight);
    } // end of method
    
    // Method to calculate cone volume
    private double calculateConeVolume (int coneRadius, int coneHeight) {
        volume = Math.PI * Math.pow(coneRadius, 2.0) * coneHeight / 3.0;
        return volume;
    } // end of method
    
    // Method to calculate slant height
    private double calculateSlantHeight (int coneRadius, int coneHeight) {
        slantHeight = Math.sqrt(Math.pow(coneRadius, 2.0) + Math.pow(coneHeight, 2.0));
        return slantHeight;
    } // end of method
    
    // Method to create menu card, inherited from Shape
    @Override
    public JPanel createMenu() {
        // Initializing Components
        final JLabel input1Lbl = new JLabel("Radius :");
        final JLabel input2Lbl = new JLabel("Height :");
        final JLabel output1Lbl = new JLabel("Volume :");
        final JLabel output2Lbl = new JLabel("Slant Height :");
        final JButton calcBtn = new JButton("Calculate Properties");
        calcBtn.addActionListener(new BtnListener());
        
        // Creating Constraints for GridBag
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.anchor = GridBagConstraints.EAST;
        
        // ioPanel for Input/Output Menu items
        final JPanel ioPanel = new JPanel();
        ioPanel.setLayout(new GridBagLayout());
        
        // Label: Message about input
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        ioPanel.add(inputMsg, gbc);
                
        // Label: Input 1
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        ioPanel.add(input1Lbl, gbc);
        
        // Textfield: Input 1
        gbc.gridx = 1;
        input1Txt.addKeyListener(new Validator());
        input1Txt.setColumns(10);
        input1Txt.isFocusable();
        ioPanel.add(input1Txt, gbc);
        
        // Label: Input 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        ioPanel.add(input2Lbl, gbc);
            
        // Textfield: Input 2
        gbc.gridx = 1;
        gbc.gridy = 2;
        input2Txt.addKeyListener(new Validator());
        input2Txt.setColumns(10);
        input2Txt.isFocusable();
        ioPanel.add(input2Txt, gbc);
            
        // Label: Output 1
        gbc.gridx = 2;
        gbc.gridy = 1;
        ioPanel.add(output1Lbl, gbc);
        
        // Label: Output 2
        gbc.gridy = 2;
        ioPanel.add(output2Lbl, gbc);
        
        // TextArea: Output 1
        gbc.gridx = 3;
        gbc.gridy = 1;
        output1Txt.setColumns(20);
        output1Txt.setEditable(false);
        ioPanel.add(output1Txt, gbc);
        
        // TextArea: Output 2
        gbc.gridy = 2;
        output2Txt.setColumns(20);
        output2Txt.setEditable(false);
        ioPanel.add(output2Txt, gbc);
        
        // Button: Calculate Properties
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 3;
        gbc.gridy = 0;
        ioPanel.add(calcBtn, gbc);
        return ioPanel;
    } // End of method
    
    // Validator for input
    private class Validator extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (Pattern.matches("[0-9]+", input1Txt.getText().trim())
                && Pattern.matches("[0-9]+", input2Txt.getText().trim())
                && !Pattern.matches("[0]+", input1Txt.getText().trim())
                && !Pattern.matches("[0]+", input2Txt.getText().trim())) {
                inputMsg.setText("<HTML><U>Input is Valid</U></HTML>");
            } // end of if
            else if (input1Txt.getText().equals("") || input2Txt.getText().equals("")) {
                inputMsg.setText("<HTML><U>Input Positive Integer(s) below :</U></HTML>");
            } // end of else if
            else {
                inputMsg.setText("<HTML><U>Input is Invalid</U></HTML>");
            } // end of else
        } // end of method
    } // end of inner class
    
    // CountBtnListener, ActionListener for the Count button
    private class BtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Only allow calculation if input is valid
            if (inputMsg.getText().equals("<HTML><U>Input is Valid</U></HTML>")) {
                // Use input to calculate properties, output it
                Cone cone = new Cone(Integer.parseInt(input1Txt.getText().trim()),
                                     Integer.parseInt(input2Txt.getText().trim()));
                output1Txt.setText(Double.toString(cone.volume));
                output2Txt.setText(Double.toString(cone.slantHeight));
                
                // Create new frame and display cone image in it.
                // cone.jpg is cited in APA format in lines 201 to 204.
                JFrame visual = new JFrame();
                ImageIcon shapeImg = new ImageIcon(getClass().getResource("cone.jpg"));
                
                    /*
                    * Cangiano, A. (2007). Cone. Retrieved November 15, 2020, 
                    *   from https://mathblog.com/reference/geometry/cone/
                    */
                    
                JLabel shapeLbl = new JLabel(shapeImg);
                visual.add(shapeLbl);
                visual.setTitle("Your Cone");
                visual.setVisible(true);
                visual.setLocationRelativeTo(null);
                visual.pack();
            } // end of if
            
            // Create Dialog if input is invalid or empty
            else {
                final JOptionPane invalidMsg = new JOptionPane();
                JOptionPane.showMessageDialog(invalidMsg, "Please enter a "
                                              + "positive integer(s).");
                output1Txt.setText("");
                output2Txt.setText("");
            } // end of else
        } // end of actionPerformed
    } // end of listener
} // end of class
