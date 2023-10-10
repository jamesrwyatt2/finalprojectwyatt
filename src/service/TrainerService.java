package service;

import model.Employee;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerService extends MainService{

    private final EmployeeService employeeService = new EmployeeService();

    public void trainerQualifyChecker(int employeeId) throws SQLException {
        List<Employee> employees;

        String SELECT_QUERY =
                "SELECT employeeId, firstName, lastName, pcHours, networkHours, cableHours, pcYears, networkYears, cableYears " +
                        "FROM employees " +
                        "WHERE employeeId = " + employeeId;

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
        employees = employeeService.mapResultSetToEmployeeList(resultSet);

        for(Employee employee : employees) {
            createTrainerIfQualified(employee);
        }

    }

    // This method is used to get all authors from the database and return them in a DefaultListModel
    public DefaultListModel getAllTrainer() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT employee.firstName, employee.lastName FROM Employees employee, Trainers trainer WHERE employee.employeeId = trainer.employeeId");
        // Loop through the result set and add each author to the list
        while (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            // Create a new author object and add it to the list
            Employee employee = new Employee(firstName, lastName);
            employees.add(employee);
        }

        DefaultListModel<String> trainerListModel = new DefaultListModel<>();
        // Loop through the list of authors and add them to the list model as single String
        for (Employee employee : employees) {
            trainerListModel.addElement(employee.getLastName() + ", " + employee.getFirstName());
        }

        return trainerListModel;
    }


    // Private Helper Methods //
    private void createTrainerIfQualified(Employee employee) throws SQLException {
        if(employee.getPcHours() >= 1000 && employee.getPcYears() >= 2) {
            createTrainer(employee.getEmployeeId(), "PC");
        }
        if(employee.getNetworkHours() >= 1000 && employee.getNetworkYears() >= 2) {
            createTrainer(employee.getEmployeeId(), "Network");
        }
        if(employee.getCableHours() >= 1000 && employee.getCableYears() >= 2) {
            createTrainer(employee.getEmployeeId(), "Cable");
        }
    }

    private void createTrainer(int employeeId, String certName) throws SQLException {

        String FIND_QUERY =
                "SELECT * " +
                        "FROM TRAINERS " +
                        "WHERE employeeId = " + employeeId;

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(FIND_QUERY);
        if(resultSet.next()) {
            return;
        } else {

            String INSERT_QUERY =
                    "INSERT INTO TRAINERS (employeeId) " +
                            "VALUES (" + employeeId + ")";

            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
            statement.executeUpdate(INSERT_QUERY);
        }

    }

}
