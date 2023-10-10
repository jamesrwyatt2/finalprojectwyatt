package ui;

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

    private static final String DB_URL = "jdbc:derby://localhost:1527/employees";
    private static final String DEFAULT_QUERY = "SELECT * FROM employees";
    private ResultSetTableModel tableModel;

    public EmployeeManager() {

        try {
            // create TableModel for results of DEFAULT_QUERY
            tableModel = new ResultSetTableModel(DB_URL, DEFAULT_QUERY);

            employeeTable.setModel(tableModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            tableModel.disconnectFromDatabase();
            System.exit(1);
        }


        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
