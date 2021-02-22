/*
* File: Square.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program is meant to accompany MenuGUI.java and is dedicated
* to constructing Square objects and the Square menu. Square overrides createMenu() 
* method from Shape. Square(int squareLength) constructor uses the argument 
* as length and uses that to determine area and diagonal. 
* calculateSquareArea contains the formula to calculate area and 
* calculateSquareDiagonal contains the formula to calculate diagonal.
* createMenu() overrides the Shape method and creates the menu panel specific to
* Square option. Validator is an inner class dedicated to listening to keys
* pressed in the input textfield and notifies the user if the input is valid
* or not. BtnListener is an inner class dedicated to listen to the calculate
* button and outputs the results if the input is valid in addition to creating
* a frame for the visual. DrawSquare uses Graphics to draw the visual for the
* square.
*/

// import necessary Java classes
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Square class, child of Shape
public class Square extends Shape {
    
    // Variable initialization
    private int squareLength = 0;
    private double area, squareDiagonal = 0.0;
    private final JTextField input1Txt = new JTextField("");
    private final JTextArea output1Txt = new JTextArea("");
    private final JTextArea output2Txt = new JTextArea("");
    private final JLabel inputMsg = new JLabel("<HTML><U>Input Positive "
                                               +"Integer(s) below :</U></HTML>");
    
    // No-argument constructor
    public Square () {
        squareLength = 0;
        area = 0.0;
        squareDiagonal = 0.0;
    } // end of method
    
    // Constructor
    public Square (int squareLength) {
        this.squareLength = squareLength;
        area = calculateSquareArea(squareLength);
        squareDiagonal = calculateSquareDiagonal(squareLength);
    } // end of method
    
    // Method to calculate square area
    private double calculateSquareArea (int squareLength) {
        area = Math.pow(squareLength, 2.0);
        return area;
    } // end of method
    
    // Method to calculate square Diagonal
    private double calculateSquareDiagonal (int squareLength) {
        squareDiagonal = squareLength * Math.sqrt(2.0);
        return squareDiagonal;
    } // end of method
    
    // Method to create menu card, inherited from Shape
    @Override
    public JPanel createMenu() {
        // Initializing Components
        final JLabel input1Lbl = new JLabel("Length/Width :");
        final JLabel output1Lbl = new JLabel("Area :");
        final JLabel output2Lbl = new JLabel("Diagonal :");
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
                && !Pattern.matches("[0]+", input1Txt.getText().trim())) {
                inputMsg.setText("<HTML><U>Input is Valid</U></HTML>");
            } // end of if
            else if (input1Txt.getText().equals("")) {
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
                Square square = new Square(Integer.parseInt(input1Txt.getText().trim()));
                squareLength = square.squareLength;
                output1Txt.setText(Double.toString(square.area));
                output2Txt.setText(Double.toString(square.squareDiagonal));
                
                // Create new frame and draw square in it
                JFrame visual = new JFrame();
                DrawSquare createVisual = new DrawSquare();
                visual.setMinimumSize(new Dimension(60,60));
                visual.add(createVisual);
                visual.setSize((square.squareLength+50),(square.squareLength+60));
                visual.setTitle("Your Square");
                visual.setVisible(true);
                visual.setLocationRelativeTo(null);       
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
    
    // Inner Class to draw with Graphics
    private class DrawSquare extends JComponent {
        // Constructor
        DrawSquare() {
            
        } // end of constructor
        
        // Method for drawing square
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(10, 10, squareLength, squareLength);
        } // end of method
    } // end of inner class
} // end of class
