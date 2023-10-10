package ui;

import service.CertService;
import service.EmployeeService;
import service.ResultSetTableModel;
import service.TrainerService;

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
    private JTable certTable;
    private JComboBox certSelector;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JList trainerList;
    private JButton submitButton;

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
                    // Update employee table
                    setEmployeeTable();
                    certService.certQualifyChecker(createEmployeeId);
                    trainerService.trainerQualifyChecker(createEmployeeId);


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
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void updateAllTables() {
        setEmployeeTable();
        setCertTable("PC");
        setTrainerList();
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

    public void setTrainerList(){
        try {
            trainerList.setModel(trainerService.getAllTrainer());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Setting Trainer Table", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setCertSelector() {
        certSelector.addItem("PC");
        certSelector.addItem("Network");
        certSelector.addItem("Cable");
    }
}
