/*
* File: Torus.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program is meant to accompany MenuGUI.java and is dedicated
* to constructing Torus objects and the Torus menu. Torus overrides createMenu() 
* method from Shape. Torus(int minorRadius, int majorRadius) constructor 
* uses the arguments as minor radius/major radius and uses that to determine 
* volume and torus type. calculateTorusVolume contains the formula to
* calculate volume and determineTorusType contains the conditional to determine
* the type of torus. createMenu() overrides the Shape method and creates the menu
* panel specific to Torus option. Validator is an inner class dedicated
* to listening to keys pressed in the input textfield and notifies the user if the
* input is valid or not. BtnListener is an inner class dedicated to
* listen to the calculate button and outputs the results if the input
* is valid in addition to creating a frame for the visual. 
*
* The sources of ringtorus.jpg, horntorus.jpg, and spindletorus.jpg are
* cited here and (respectively) in lines 246-250, 257-260, 267-272 under each file load.
*
* References:
*   Lamb, E. (2015, November 28). A Few of My Favorite Spaces: 
*       The Torus. Retrieved November 15, 2020, from 
*       https://blogs.scientificamerican.com/roots-of-unity/a-few-of-my-favorite-spaces-the-torus/
*
*   Sharma, R. (2020, November 14). Torus. Retrieved 
*       November 15, 2020, from https://alchetron.com/Torus
*
*   Murdzek, R. (2007). The Geometry Of The Torus Universe. 
*       International Journal of Modern Physics D, 16(04), 681-686.
*       doi:10.1142/s0218271807009826. Retrieved November 15, 2020,
*       from https://www.worldscientific.com/doi/abs/10.1142/S0218271807009826?journalCode=ijmpd
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


// Torus class, child of Shape
public class Torus extends Shape {
    
    // Variable initialization
    private int minorRadius, majorRadius = 0;
    private double volume = 0.0;
    private String torusType = null;
    private final JTextField input1Txt = new JTextField("");
    private final JTextField input2Txt = new JTextField("");
    private final JTextArea output1Txt = new JTextArea("");
    private final JTextArea output2Txt = new JTextArea("");
    private final JLabel inputMsg = new JLabel("<HTML><U>Input Positive "
                                               +"Integer(s) below :</U></HTML>");
    
    // No-argument constructor
    public Torus () {
        minorRadius = 0;
        majorRadius = 0;
        volume = 0.0;
        torusType = null;
    } // end of method
    
    // Constructor
    public Torus (int minorRadius, int majorRadius) {
        this.minorRadius = minorRadius;
        this.majorRadius = majorRadius;
        torusType = determineTorusType(minorRadius, majorRadius);
        volume = calculateTorusVolume(minorRadius, majorRadius, torusType);
    } // end of method
    
    // Method to determine Torus type
    private String determineTorusType (int minorRadius, int majorRadius) {
        // Ring torus, r < R
        if (minorRadius < majorRadius) {
            torusType = "Ring";
        } // end of if
        
        // Horn torus, r = R
        else if (minorRadius == majorRadius) {
            torusType = "Horn";
        } // end of else if
        
        // Spindle torus, r > R
        else {
            torusType = "Spindle";
        } // end of else
        return torusType;
    } // end of method
    
    // Method to calculate Torus volume
    private double calculateTorusVolume (int minorRadius, int majorRadius,
                                         String torusType) {
        // Calculate volume for Ring and Horn torus
        if ("Ring".equals(torusType) || "Horn".equals(torusType)) {
            volume = (Math.PI * Math.pow(minorRadius, 2.0)) 
                    * (2.0 * Math.PI * majorRadius);
        } // end of if
        
        // Volume cannot be calculated for Spindle Torus
        else {
            volume = 0;
        } // end of else
        return volume;
    } // end of method
    
    // Method to create menu card, inherited from Shape
    @Override
    public JPanel createMenu() {
        // Initializing Components
        final JLabel input1Lbl = new JLabel("Minor Radius :");
        final JLabel input2Lbl = new JLabel("Major Radius :");
        final JLabel output1Lbl = new JLabel("Volume :");
        final JLabel output2Lbl = new JLabel("Torus Type :");
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
                Torus torus = new Torus(Integer.parseInt(input1Txt.getText().trim()),
                                        Integer.parseInt(input2Txt.getText().trim()));
                if ("Ring".equals(torus.torusType) || "Horn".equals(torus.torusType)) {
                    output1Txt.setText(Double.toString(torus.volume));
                } // end of if
                else {
                    output1Txt.setText("Cannot be calculated for Spindle");
                } // end of else
                output2Txt.setText(torus.torusType);
                
                // Create new frame and display torus image in it.
                // ringtorus.jpg, horntorus.jpg, spindletorus.jpg
                // are cited in APA format under lines where they are loaded.
                JFrame visual = new JFrame();
                ImageIcon shapeImg;
                // Load Ring Torus image, citation lines 246-250
                if ("Ring".equals(torus.torusType)) {
                    shapeImg = new ImageIcon(getClass().getResource("ringtorus.jpg"));
                } // end of if
                
                    /*
                    * Lamb, E. (2015, November 28). A Few of My Favorite Spaces: 
                    *   The Torus. Retrieved November 15, 2020, from 
                    *   https://blogs.scientificamerican.com/roots-of-unity/a-few-of-my-favorite-spaces-the-torus/
                    */
                
                // Load Horn Torus image, citation lines 257-260
                else if ("Horn".equals(torus.torusType)) {
                    shapeImg = new ImageIcon(getClass().getResource("horntorus.jpg"));
                } // end of else if
                
                    /*
                    * Sharma, R. (2020, November 14). Torus. Retrieved 
                    *   November 15, 2020, from https://alchetron.com/Torus
                    */
                
                // Load Spindle Torus image, citation lines 267-272
                else {
                    shapeImg = new ImageIcon(getClass().getResource("spindletorus.jpg"));
                } // end of else
                
                    /*
                    * Murdzek, R. (2007). The Geometry Of The Torus Universe. 
                    *   International Journal of Modern Physics D, 16(04), 681-686.
                    *   doi:10.1142/s0218271807009826. Retrieved November 15, 2020,
                    *   from https://www.worldscientific.com/doi/abs/10.1142/S0218271807009826?journalCode=ijmpd
                    */
                    
                JLabel shapeLbl = new JLabel(shapeImg);
                visual.add(shapeLbl);
                visual.setTitle("Your Torus");
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
