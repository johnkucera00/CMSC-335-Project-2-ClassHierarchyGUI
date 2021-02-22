/*
* File: Sphere.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program is meant to accompany MenuGUI.java and is dedicated
* to constructing Sphere objects and the Sphere menu. Sphere overrides createMenu() 
* method from Shape. Sphere(int sphereRadius, int capHeight) constructor 
* uses the arguments as radius/spherical cap height and uses that to determine 
* volume and cap volume. calculateSphereVolume contains the formula to
* calculate volume and calculateCapVolume contains the formula to calculate volume of
* a spherical cap. createMenu() overrides the Shape method and creates the menu
* panel specific to Sphere option. Validator is an inner class dedicated
* to listening to keys pressed in the input textfield and notifies the user if the
* input is valid or not. BtnListener is an inner class dedicated to
* listen to the calculate button and outputs the results if the input
* is valid in addition to creating a frame for the visual. 
*
* The source of sphere.jpg is cited here and in lines 204-207 under the file load.
*
* References:
*   Cangiano, A. (2007). Sphere. Retrieved November 15, 2020, 
*       from https://mathblog.com/reference/geometry/sphere/
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

// Sphere class, child of Shape
public class Sphere extends Shape {
    
    // Variable initialization
    private int sphereRadius, capHeight = 0;
    private double volume, capVolume = 0.0;
    private final JTextField input1Txt = new JTextField("");
    private final JTextField input2Txt = new JTextField("");
    private final JTextArea output1Txt = new JTextArea("");
    private final JTextArea output2Txt = new JTextArea("");
    private final JLabel inputMsg = new JLabel("<HTML><U>Input Positive "
                                               +"Integer(s) below :</U></HTML>");
    
    // No-argument constructor
    public Sphere () {
        sphereRadius = 0;
        capHeight = 0;
        volume = 0.0;
        capVolume = 0.0;
    } // end of method
    
    // Constructor
    public Sphere (int sphereRadius, int capHeight) {
        this.sphereRadius = sphereRadius;
        this.capHeight = capHeight;
        volume = calculateSphereVolume(sphereRadius);
        capVolume = calculateCapVolume(sphereRadius, capHeight);
    } // end of method
    
    // Method to calculate sphere volume
    private double calculateSphereVolume (int sphereRadius) {
        volume = (4.0 / 3.0) * Math.PI * Math.pow(sphereRadius, 3.0);
        return volume;
    } // end of method
    
    // Method to calculate spherical cap volume
    private double calculateCapVolume (int sphereRadius, int capHeight) {
        capVolume = Math.PI * Math.pow(capHeight, 2.0) 
                   * (sphereRadius - (1.0 / 3.0) * capHeight);
        return capVolume;
    } // end of method
    
    // Method to create menu card, inherited from Shape
    @Override
    public JPanel createMenu() {
        // Initializing Components
        final JLabel input1Lbl = new JLabel("Radius :");
        final JLabel input2Lbl = new JLabel("Spherical Cap Height :");
        final JLabel output1Lbl = new JLabel("Volume :");
        final JLabel output2Lbl = new JLabel("Spherical Cap Volume :");
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
                if (Integer.parseInt(input1Txt.getText().trim()) >
                    Integer.parseInt(input2Txt.getText().trim())) {
                    // Use input to calculate properties, output it
                    Sphere sphere = new Sphere(Integer.parseInt(input1Txt.getText().trim()),
                                               Integer.parseInt(input2Txt.getText().trim()));
                    output1Txt.setText(Double.toString(sphere.volume));
                    output2Txt.setText(Double.toString(sphere.capVolume));

                    // Create new frame and display sphere image in it.
                    // sphere.jpg is cited in APA format in lines 204 to 207.
                    JFrame visual = new JFrame();
                    ImageIcon shapeImg = new ImageIcon(getClass().getResource("sphere.jpg"));

                        /*
                        * Cangiano, A. (2007). Sphere. Retrieved November 15, 2020, 
                        *   from https://mathblog.com/reference/geometry/sphere/
                        */

                    JLabel shapeLbl = new JLabel(shapeImg);
                    visual.add(shapeLbl);
                    visual.setTitle("Your Sphere");
                    visual.setVisible(true);
                    visual.setLocationRelativeTo(null);
                    visual.pack();
                } // end of if

                // Spherical Cap Height cannot be greater than Radius
                else {
                    final JOptionPane invalidMsg = new JOptionPane();
                    JOptionPane.showMessageDialog(invalidMsg, "The Spherical Cap "
                                                  + "cannot have a Height greater than "
                                                  + "or equal to the Sphere's Radius. "
                                                  + "Please try again.");
                    output1Txt.setText("");
                    output2Txt.setText("");
                } // end of inner else
            } // end of outer if
            
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
