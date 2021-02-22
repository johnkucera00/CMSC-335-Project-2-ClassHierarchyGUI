/*
* File: Triangle.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program is meant to accompany MenuGUI.java and is dedicated
* to constructing Triangle objects and the Triangle menu. Triangle overrides createMenu() 
* method from Shape. Triangle(int side1Length, int side2Length, int side3Length) constructor 
* uses the arguments as side lengtsh and uses that to determine area and triangle type. 
* calculateTriangleArea contains the formula to calculate area and 
* determineTriangleType contains the formula to find the triangle's type.
* createMenu() overrides the Shape method and creates the menu panel specific to
* Triangle option. Validator is an inner class dedicated to listening to keys
* pressed in the input textfield and notifies the user if the input is valid
* or not. BtnListener is an inner class dedicated to listen to the calculate
* button and outputs the results if the input is valid in addition to creating
* a frame for the visual. DrawTriangle uses Graphics to draw the visual for the
* triangle.
*/

// import necessary Java classes
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
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

// Triangle class, child of Shape
public class Triangle extends Shape {
    
    // Variable initialization
    private int side1Length, side2Length, side3Length, longestSide = 0;
    private double area = 0.0;
    private String triangleType = null;
    private final JTextField input1Txt = new JTextField("");
    private final JTextField input2Txt = new JTextField("");
    private final JTextField input3Txt = new JTextField("");
    private final JTextArea output1Txt = new JTextArea("");
    private final JTextArea output2Txt = new JTextArea("");
    private final JLabel inputMsg = new JLabel("<HTML><U>Input Positive "
                                               +"Integer(s) below :</U></HTML>");
    
    // No-argument constructor
    public Triangle () {
        side1Length = 0;
        side2Length = 0;
        side3Length = 0;
        area = 0.0;
        triangleType = null;
    } // end of method
    
    // Constructor
    public Triangle (int side1Length, int side2Length, int side3Length) {
        this.side1Length = side1Length;
        this.side2Length = side2Length;
        this.side3Length = side3Length;
        area = calculateTriangleArea(side1Length, side2Length, side3Length);
        triangleType = determineTriangleType(side1Length, side2Length, side3Length);
    } // end of method
    
    // Method to calculate triangle area with Heron's formula
    private double calculateTriangleArea (int side1Length, int side2Length, 
                                           int side3Length) {
        double semiPerimeter = (side1Length + side2Length + side3Length) / 2.0;
        area = Math.sqrt(semiPerimeter * (semiPerimeter - side1Length)
                         * (semiPerimeter - side2Length) * (semiPerimeter - 
                         side3Length));
        return area;
    } // end of method
    
    // Method to determine triangle type
    private String determineTriangleType (int side1Length, int side2Length, 
                                           int side3Length) {
        // Equilateral, 3 equal sides
        if (side1Length == side2Length && side2Length == side3Length) {
            triangleType = "Equilateral";
        } // end of if
        
        // Isosceles, 2 equal sides
        else if (side1Length == side2Length || side2Length == side3Length
                 || side1Length == side3Length) {
            triangleType = "Isosceles";
        } // end of else if
        
        // Scalene, no equal sides
        else {
            triangleType = "Scalene";
        } // end of else
        
        return triangleType;
    } // end of method
    
    // Method to create menu card, overrides createMenu() from Shape
    @Override
    public JPanel createMenu() {
        // Initializing Components
        final JLabel input1Lbl = new JLabel("Side 1 Length :");
        final JLabel input2Lbl = new JLabel("Side 2 Length :");
        final JLabel input3Lbl = new JLabel("Side 3 Length :");
        final JLabel output1Lbl = new JLabel("Area :");
        final JLabel output2Lbl = new JLabel("Triangle Type :");
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
        
        // Label: Input 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        ioPanel.add(input3Lbl, gbc);
            
        // Textfield: Input 3
        gbc.gridx = 1;
        gbc.gridy = 3;
        input3Txt.addKeyListener(new Validator());
        input3Txt.setColumns(10);
        input3Txt.isFocusable();
        ioPanel.add(input3Txt, gbc);
            
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
                && Pattern.matches("[0-9]+", input3Txt.getText().trim())
                && !Pattern.matches("[0]+", input1Txt.getText().trim())
                && !Pattern.matches("[0]+", input2Txt.getText().trim())
                && !Pattern.matches("[0]+", input3Txt.getText().trim())) {
                inputMsg.setText("<HTML><U>Input is Valid</U></HTML>");
            } // end of if
            else if (input1Txt.getText().equals("") || input2Txt.getText().equals("")
                     || input3Txt.getText().equals("")) {
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
                Triangle triangle = new Triangle(Integer.parseInt(input1Txt.getText().trim()),
                                                 Integer.parseInt(input2Txt.getText().trim()),
                                                 Integer.parseInt(input3Txt.getText().trim()));
                side1Length = triangle.side1Length;
                side2Length = triangle.side2Length;
                side3Length = triangle.side3Length;
                
                // Continue if triangle can exist with input dimensions
                if (!Double.isNaN(triangle.area) && triangle.area > 0.0) {
                    output1Txt.setText(Double.toString(triangle.area));
                    output2Txt.setText(triangle.triangleType);
                
                    // Finding Longest Side to adjust frame size/triangle center point
                    if ((side1Length > side2Length) && (side1Length > side3Length)) {
                        longestSide = side1Length;
                    } // end of inner if
                    else if (side2Length > side3Length) {
                        longestSide = side2Length;
                    } // end of inner else if
                    else {
                        longestSide = side3Length;
                    } // end of inner else

                    // Create new frame and draw triangle in it
                    JFrame visual = new JFrame();
                    DrawTriangle createVisual = new DrawTriangle();
                    visual.add(createVisual);
                    visual.setSize(longestSide+100, longestSide+100);
                    visual.setTitle("Your Triangle");
                    visual.setVisible(true);
                    visual.setLocationRelativeTo(null);      
                } // end of inner if
                
                // Display message if triangle cannot exist with input dimensions
                else {
                    final JOptionPane invalidMsg = new JOptionPane();
                    JOptionPane.showMessageDialog(invalidMsg, "This Triangle cannot "
                                                  + "exist with the dimensions "
                                                  + "provided. Please try again.");
                    output1Txt.setText("");
                    output2Txt.setText("");
                } // end of inner else
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
    
    //Inner Class to draw with Graphics
    private class DrawTriangle extends JComponent {
        // Constructor
        DrawTriangle() {
            
        } // end of constructor

        // Method for drawing triangle
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Law of cosines to determine angle
            double theta = Math.acos(-(Math.pow(side1Length, 2.0) - Math.pow(side2Length, 2.0) 
                       - Math.pow(side3Length, 2.0)) / (2.0 * side2Length * side3Length));
        
            // Array for points, determine points with law of cosines
            final Point[] points = new Point[3];
            points[0] = new Point(0, 0);
            points[1] = new Point(side2Length, 0);
            points[2] = new Point((int)(side3Length * Math.cos(theta)), 
                             (int)(side3Length * Math.sin(theta)));
        
            // Determine center point
            Point center = new Point((points[0].x + points[1].x + points[2].x) / 3, 
                                     (points[0].y + points[1].y + points[2].y) / 3);
            // Translate array elements to coordinates
            for (Point i : points) 
                i.translate(-center.x, -center.y);
                final Point coord = new Point(longestSide/2,longestSide/2);
                g.translate(coord.x, coord.y);
                for (int i = 0; i < points.length; i++) {
                    g.drawLine(points[i].x, points[i].y, 
                               points[(i+1) % points.length].x, 
                               points[(i+1) % points.length].y);
                } // end of inner for
        } // end of method
    } // end of inner class
} // end of class