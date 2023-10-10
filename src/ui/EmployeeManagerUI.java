package ui;

import model.Employee;
import service.CertService;
import service.EmployeeService;
import service.ResultSetTableModel;
import service.TrainerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

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
    private JTable certTable;
    private JComboBox certSelector;
    private JTextField pcHoursUpdate;
    private JTextField networkHoursUpdate;
    private JTextField cableHoursUpdate;
    private JList trainerList;
    private JButton submitButton;
    private JList employeeListForTraining;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton submitButton1;

    private final EmployeeService employeeService = new EmployeeService();

    private final TrainerService trainerService = new TrainerService();

    private final CertService certService = new CertService();

    private static final String DB_URL = "jdbc:derby://localhost:1527/employees";
    private static final String DEFAULT_QUERY = "SELECT * FROM employees";
    private ResultSetTableModel tableModel;

    public EmployeeManagerUI() {
        // On load actions
        //Setting Employee Table
        setEmployeeTable();
        setCertTable("PC");
        setTrainerList();
        setAllEmployeeList();
        setCertSelector();


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
                    // Run cert and trainer processes
                    certService.certQualifyChecker(createEmployeeId);
                    trainerService.trainerQualifyChecker(createEmployeeId);
                    // Update employee table
                    updateAllTablesAndList();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Error Creating Employee", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // This method will update the cert table with the cert selected
        certSelector.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCert = (String) certSelector.getSelectedItem();
                setCertTable(selectedCert);
            }
        });

        // This method will update the training hours for given employee
        submitButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                trainerList.getSelectedValue();
                List<String> employeesSelected = (List<String>) employeeListForTraining.getSelectedValuesList().stream().toList();

                try {
                    // Validate that a trainer and at least one employee is selected
                    if(trainerList.getSelectedValue() == null || employeesSelected.size() == 0) {
                        throw new Exception("Please select a trainer and at least one employee");
                    }
                    // Loop through each selected employee and update hours
                    for (String employee : employeesSelected) {
                        // Get employeeId
                        String[] employeeId = employee.split(", ");
                        // Get employee
                        Employee employeeUpdate = employeeService.getEmployeeById(Integer.parseInt(employeeId[0]));
                        // Check and update hours
                        if(Integer.parseInt(pcHoursUpdate.getText()) > 0) {
                            employeeUpdate.setPcHours(employeeUpdate.getPcHours() + Integer.parseInt(pcHoursUpdate.getText()));
                        }
                        if (Integer.parseInt(networkHoursUpdate.getText()) > 0) {
                            employeeUpdate.setNetworkHours(employeeUpdate.getNetworkHours() + Integer.parseInt(networkHoursUpdate.getText()));
                        }
                        if (Integer.parseInt(cableHoursUpdate.getText()) > 0) {
                            employeeUpdate.setCableHours(employeeUpdate.getCableHours() + Integer.parseInt(cableHoursUpdate.getText()));
                        }
                        // Make Update call
                        employeeService.updateEmployee(employeeUpdate);
                        // Run cert and trainer processes for updated employee
                        certService.certQualifyChecker(Integer.parseInt(employeeId[0]));
                        trainerService.trainerQualifyChecker(Integer.parseInt(employeeId[0]));
                    }
                    // Clear text fields
                    pcHoursUpdate.setText("0");
                    networkHoursUpdate.setText("0");
                    cableHoursUpdate.setText("0");

                    // Update employee tables
                    updateAllTablesAndList();

                } catch (SQLException exception) { // SQL exception handling
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error Updating Employees", JOptionPane.ERROR_MESSAGE);
                } catch (Exception exception) { // All other exception handling
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error Updating Employees", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
    // This method will return the root panel for the UI
    public JPanel getRootPanel() {
        return rootPanel;
    }
    // This method will update all tables and lists after an insert
    private void updateAllTablesAndList() {
        setEmployeeTable();
        setCertTable("PC");
        setTrainerList();
        setAllEmployeeList();
    }

    // On load this method will set the table model for the employee table
    public void setEmployeeTable(){
        try {
            // create TableModel for results of DEFAULT_QUERY
            tableModel = new ResultSetTableModel(DB_URL, DEFAULT_QUERY);
            employeeTable.setModel(tableModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Setting Employee Table", JOptionPane.ERROR_MESSAGE);
            tableModel.disconnectFromDatabase();
        }
    }
    // On load this method will set the table model for the cert table
    public void setCertTable(String certName){
        try { // Find all employees with selected cert
            String CERT_QUERY = "SELECT employee.firstName, Employee.LastName FROM Certs cert, Employees employee WHERE cert.certName = '" + certName + "'" +
                    "AND cert.employeeId = employee.employeeId";

            // create TableModel for results of DEFAULT_QUERY
            tableModel = new ResultSetTableModel(DB_URL, CERT_QUERY);

            certTable.setModel(tableModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Setting Cert Table", JOptionPane.ERROR_MESSAGE);
            tableModel.disconnectFromDatabase();
        }
    }
    // On load this method will set the list model for the trainer list
    public void setTrainerList(){
        try {
            trainerList.setModel(trainerService.getAllTrainer());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Setting Trainer Table", JOptionPane.ERROR_MESSAGE);
        }
    }
    // On load this method will set the list model for the employee list
    private void setAllEmployeeList() {
        try {
            employeeListForTraining.setModel(employeeService.getAllEmployeesList());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Setting Employee List", JOptionPane.ERROR_MESSAGE);
        }
    }
    // On load this method will set the cert list selector options
    private void setCertSelector() {
        certSelector.addItem("PC");
        certSelector.addItem("Network");
        certSelector.addItem("Cable");
    }
}
