/*
* File: MenuGUI.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program constructs a GUI that allows a user to
* construct child objects of the Shape class, such as Circle, Rectangle,
* Sphere, and Cone. After selecting which shape they want to construct, the
* user is prompted to enter dimensions for that shape. The input is used to
* calculate mathematical information about the shapes, such as area and volume.
* Additional unique information, such as the circumference of a circle, is
* also returned depending on the selected shape. Finally, a visual of the shape
* is displayed in a new JFrame, containing a drawing from java.awt.Graphics 
* (if 2d shape) or an image loaded from a file (if 3d shape).
*
* Methods: MenuGUI() is the constructor that creates shape objects/menus:
* Circle, Square, Triangle, Rectangle, Sphere, Cube, Cone, Cylinder, Torus.
* In each of those classes, they create their own menus and this method turns those
* menus into cards. The Cards are put into the CardLayout of the main Frame
* and inner class SelectComboListener allows the user to switch between each
* shape with the combo box.
*
* MenuGUI.java should be used alongside the following source files: Circle.java,
* Cone.java, Cube.java, Cylinder.java, Rectangle.java, Shape.java, Sphere.java,
* Square.java, Torus.java, Triangle.java. In addition to the following image
* files: cone.jpg, cube.jpg, cylinder.jpg, horntorus.jpg, ringtorus.jpg,
* sphere.jpg, spindletorus.jpg.
*/

// import of necessary java classes
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

// MenuGUI Class
public class MenuGUI extends JFrame {
    
    // Variable Initialization/Declaration
    private final JPanel cards;
    private CardLayout cl;
    
    // Combo Box Listener
    private class SelectComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            cl = (CardLayout)(cards.getLayout());
            cl.show(cards, (String)e.getItem());
        } // end of itemevent
    } // end of listener class
    
    // Constructor
    public MenuGUI() {
        
        // Component Initialization, Combo box with listener
        final JLabel selectLbl = new JLabel("Select Shape:");
        final String[] selectItems = {"Circle", "Square", "Triangle",
        "Rectangle", "Sphere", "Cube", "Cone", "Cylinder", "Torus"};
        final JComboBox selectCombo = new JComboBox(selectItems);
        selectCombo.addItemListener(new SelectComboListener());
        
        // Selection Panel with Combo Box
        JPanel selectPanel = new JPanel();
        selectPanel.add(selectLbl);
        selectPanel.add(selectCombo);
        
        // Create shape menus/objects
        Circle circle = new Circle();
        Square square = new Square();
        Triangle triangle = new Triangle();
        Rectangle rectangle = new Rectangle();
        Sphere sphere = new Sphere();
        Cube cube = new Cube();
        Cone cone = new Cone();
        Cylinder cylinder = new Cylinder();
        Torus torus = new Torus();
        
        // CardLayout w/ cards creation
        cl = new CardLayout();
        cards = new JPanel();
        cards.setLayout(cl);
        cards.add(circle.createMenu(), "Circle");
        cards.add(square.createMenu(), "Square");
        cards.add(triangle.createMenu(), "Triangle");
        cards.add(rectangle.createMenu(), "Rectangle");
        cards.add(sphere.createMenu(), "Sphere");
        cards.add(cube.createMenu(), "Cube");
        cards.add(cone.createMenu(), "Cone");
        cards.add(cylinder.createMenu(), "Cylinder");
        cards.add(torus.createMenu(), "Torus");
        
        // Adding shape selection and menu to frame
        add(selectPanel, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);

        // Frame characteristics
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Shape Calculator");
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    } // end of constructor
    
    // Main method
    public static void main (String[] args) {
        // GUI Creation
        MenuGUI newGUI = new MenuGUI();
    } // end of main method
} // end of class
