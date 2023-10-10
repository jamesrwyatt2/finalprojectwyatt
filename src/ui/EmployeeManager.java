package ui;

import service.EmployeeService;
import service.ResultSetTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EmployeeManager {
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JTable employeeTable;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField pcHr;
    private JTextField networkHr;
    private JTextField cableHr;
    private JTextField pcYr;
    private JTextField networkYr;
    private JTextField cableYr;
    private JButton createButton;

    private final EmployeeService employeeService = new EmployeeService();

    private static final String DB_URL = "jdbc:derby://localhost:1527/employees";
    private static final String DEFAULT_QUERY = "SELECT * FROM employees";
    private ResultSetTableModel tableModel;

    public EmployeeManager() {
        // On load actions
        //Setting Employee Table
        setEmployeeTable();

        // Create button action listener
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Excute Insert Statement
                try {
                    employeeService.createEmployee(firstName.getText(), lastName.getText(),
                            Integer.parseInt(pcHr.getText()),  Integer.parseInt(networkHr.getText()),  Integer.parseInt(cableHr.getText()),
                            Integer.parseInt(pcYr.getText()),  Integer.parseInt(networkYr.getText()),  Integer.parseInt(cableYr.getText())
                    );
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Error Creating Employee", JOptionPane.ERROR_MESSAGE);
                }
                // Update employee table
                setEmployeeTable();
                // Clear text fields
                firstName.setText("");
                lastName.setText("");
                pcHr.setText("");
                networkHr.setText("");
                cableHr.setText("");
                pcYr.setText("");
                networkYr.setText("");
                cableYr.setText("");
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    // On load this method will set the table model for the employee table
    public void setEmployeeTable(){
        try {
            // create TableModel for results of DEFAULT_QUERY
            tableModel = new ResultSetTableModel(DB_URL, DEFAULT_QUERY);

            employeeTable.setModel(tableModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            tableModel.disconnectFromDatabase();
            System.exit(1);
        }
    }
}
