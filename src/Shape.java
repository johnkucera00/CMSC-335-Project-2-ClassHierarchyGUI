/*
* File: Shape.java
* Author: John Kucera
* Date: November 12, 2020
* Purpose: This Java program is meant to accompany MenuGUI.java and is dedicated
* to being the parent superclass of all shapes listed in the combo box.
* Shape holds a no-argument constructor and method createMenu(), which returns
* a JPanel. createMenu() is meant to be inherited and overridden by child classes
* so their own unique menus can be created.
* Shape is a child of java.lang.Object class.
*/

// import necessary Java classes
import javax.swing.JPanel;

// Shape class, parent class
public class Shape {
    
    // No-argument constructor
    public Shape() {

    } // end of method
    
    // Method to create menu, which all shapes have
    public JPanel createMenu() {
        JPanel menuPanel = new JPanel();
        return menuPanel;
    } // End of method
} // end of class
