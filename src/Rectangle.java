/*
* File: Rectangle.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program is meant to accompany MenuGUI.java and is dedicated
* to constructing Rectangle objects and the Rectangle menu. Rectangle overrides createMenu() 
* method from Shape. Rectangle(int rectangleLength, rectangleWidth) constructor 
* uses the arguments as length/width and uses that to determine area and diagonal. 
* calculateRectangleArea contains the formula to calculate area and 
* calculateRectangleDiagonal contains the formula to calculate diagonal.
* createMenu() overrides the Shape method and creates the menu panel specific to
* Rectangle option. Validator is an inner class dedicated to listening to keys
* pressed in the input textfield and notifies the user if the input is valid
* or not. BtnListener is an inner class dedicated to listen to the calculate
* button and outputs the results if the input is valid in addition to creating
* a frame for the visual. DrawSquare uses Graphics to draw the visual for the
* rectangle.
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

// Rectangle class, child of Shape
public class Rectangle extends Shape {
    
    // Variable initialization
    private int rectangleLength, rectangleWidth = 0;
    private double area, rectangleDiagonal = 0.0;
    private final JTextField input1Txt = new JTextField("");
    private final JTextField input2Txt = new JTextField("");
    private final JTextArea output1Txt = new JTextArea("");
    private final JTextArea output2Txt = new JTextArea("");
    private final JLabel inputMsg = new JLabel("<HTML><U>Input Positive "
                                               +"Integer(s) below :</U></HTML>");
    
    // No-argument constructor
    public Rectangle () {
        rectangleLength = 0;
        rectangleWidth = 0;
        area = 0.0;
        rectangleDiagonal = 0.0;
    } // end of method
    
    // Constructor
    public Rectangle (int rectangleLength, int rectangleWidth) {
        this.rectangleLength = rectangleLength;
        this.rectangleWidth = rectangleWidth;
        area = calculateRectangleArea(rectangleLength, rectangleWidth);
        rectangleDiagonal = calculateRectangleDiagonal(rectangleLength, 
                                                       rectangleWidth);
    } // end of method
    
    // Method to calculate rectangle area
    private double calculateRectangleArea (int rectangleLength, 
                                           int rectangleWidth) {
        area = rectangleLength * rectangleWidth;
        return area;
    } // end of method
    
    // Method to calculate rectangle diagonal
    private double calculateRectangleDiagonal (int rectangleLength, 
                                               int rectangleWidth) {
        rectangleDiagonal = Math.sqrt(Math.pow(rectangleLength, 2.0) 
                                      + Math.pow(rectangleWidth, 2.0));
        return rectangleDiagonal;
    } // end of method
    
    // Method to create menu card, overrides createMenu() from Shape
    @Override
    public JPanel createMenu() {
        // Initializing Components
        final JLabel input1Lbl = new JLabel("Length :");
        final JLabel input2Lbl = new JLabel("Width :");
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
                Rectangle rectangle = new Rectangle(Integer.parseInt(input1Txt.getText().trim()),
                                                    Integer.parseInt(input2Txt.getText().trim()));
                rectangleLength = rectangle.rectangleLength;
                rectangleWidth = rectangle.rectangleWidth;
                output1Txt.setText(Double.toString(rectangle.area));
                output2Txt.setText(Double.toString(rectangle.rectangleDiagonal));
                
                // Create new frame and draw rectangle in it
                JFrame visual = new JFrame();
                DrawRectangle createVisual = new DrawRectangle();
                visual.setMinimumSize(new Dimension(60,60));
                visual.add(createVisual);
                visual.setSize((rectangle.rectangleLength+50),(rectangle.rectangleWidth+60));
                visual.setTitle("Your Rectangle");
                visual.setVisible(true);
                visual.setLocationRelativeTo(null);       
            } // end of if
            
            // Create Dialog if input is invalid or empty
            else {
                JOptionPane invalidMsg = new JOptionPane();
                JOptionPane.showMessageDialog(invalidMsg, "Please enter a "
                                              + "positive integer(s).");
                output1Txt.setText("");
                output2Txt.setText("");
            } // end of else
        } // end of actionPerformed
    } // end of listener
    
    // Inner Class to draw with Graphics
    private class DrawRectangle extends JComponent {
        // Constructor
        DrawRectangle() {
            
        } // end of constructor
        
        // Method for drawing rectangle
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(10, 10, rectangleLength, rectangleWidth);
        } // end of method
    } // end of inner class
} // end of class