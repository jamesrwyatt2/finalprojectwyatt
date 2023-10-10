import ui.EmployeeManagerUI;

import javax.swing.*;
import java.sql.*;

public class EmployeeManagerApplication {

    // Main method to run the application
    public static void main(String[] args) {
        createDb();
        EmployeeManagerUI employeeManager = new EmployeeManagerUI();
        JPanel root = employeeManager.getRootPanel();
        JFrame frame = new JFrame("Employee Certification and Job Management System");
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900,500);
    }

    // Create the database if it doesn't exist
    // project should have derbyclient.jar in the libraries
    public static void createDb() {
        final String DB_URL = "jdbc:derby://localhost:1527/employees;create=true";

        try {
            DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}
