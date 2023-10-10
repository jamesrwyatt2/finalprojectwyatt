import ui.EmployeeManager;

import javax.swing.*;
import java.sql.*;

public class EmployeeManagerApplication {

    public static void main(String[] args) {
        createDb();
        EmployeeManager employeeManager = new EmployeeManager();
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
            Connection connection = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}
