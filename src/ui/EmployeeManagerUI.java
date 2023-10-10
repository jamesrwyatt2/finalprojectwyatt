package ui;

import service.CertService;
import service.EmployeeService;
import service.ResultSetTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EmployeeManagerUI {
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

    private final CertService certService = new CertService();

    private static final String DB_URL = "jdbc:derby://localhost:1527/employees";
    private static final String DEFAULT_QUERY = "SELECT * FROM employees";
    private ResultSetTableModel tableModel;

    public EmployeeManagerUI() {
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
                // Execute Insert Statement
                try {
                    int createEmployeeId = employeeService.createEmployee(firstName.getText(), lastName.getText(),
                            Integer.parseInt(pcHr.getText()),  Integer.parseInt(networkHr.getText()),  Integer.parseInt(cableHr.getText()),
                            Integer.parseInt(pcYr.getText()),  Integer.parseInt(networkYr.getText()),  Integer.parseInt(cableYr.getText())
                    );
                    // Clear text fields
                    firstName.setText("");
                    lastName.setText("");
                    pcHr.setText("");
                    networkHr.setText("");
                    cableHr.setText("");
                    pcYr.setText("");
                    networkYr.setText("");
                    cableYr.setText("");
                    // Update employee table
                    setEmployeeTable();
                    certService.certQualifyChecker(createEmployeeId);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Error Creating Employee", JOptionPane.ERROR_MESSAGE);
                }




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
