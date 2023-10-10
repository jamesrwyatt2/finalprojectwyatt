package service;

import model.Employee;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeService Class for the Employee Application
 * This class contain employee sql statements and mapping for the application
 * @Author James Wyatt
 */

public class EmployeeService extends MainService {

    // This method gets employee by id
    public Employee getEmployeeById(int employeeId) throws SQLException {
        String FIND_EMPLOYEE_BY_ID_QUERY =
                "SELECT * " +
                        "FROM employees " +
                        "WHERE employeeId = " + employeeId;
        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(FIND_EMPLOYEE_BY_ID_QUERY);
        if(resultSet.next()) {
            employeeId = Integer.parseInt(resultSet.getString("employeeId"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int pcHours = Integer.parseInt(resultSet.getString("pcHours"));
            int networkHours = Integer.parseInt(resultSet.getString("networkHours"));
            int cableHours = Integer.parseInt(resultSet.getString("cableHours"));
            int pcYears = Integer.parseInt(resultSet.getString("pcYears"));
            int networkYears = Integer.parseInt(resultSet.getString("networkYears"));
            int cableYears = Integer.parseInt(resultSet.getString("cableYears"));
            Employee employee = new Employee(employeeId, firstName, lastName, pcHours, networkHours, cableHours, pcYears, networkYears, cableYears);
            return employee;
        } else {
            return null;
        }
    }

    // This method is to create employee
    public int createEmployee( String firstName, String lastName,
                                int pcHours, int networkHours, int cableHours,
                                int pcYears, int networkYears, int cableYears) throws SQLException {
        Long employeeId;
        String INSERT_QUERY =
                "INSERT INTO employees (firstName, lastName, pcHours, networkHours, cableHours, pcYears, networkYears, cableYears) " +
                        "VALUES ('" + firstName + "', '" + lastName +
                        "', " + pcHours + ", " + networkHours + ", " + cableHours +
                        ", " + pcYears + ", " + networkYears + ", " + cableYears + ")";

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        int affectedRows = statement.executeUpdate(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        System.out.println("Affected Rows: " + affectedRows);
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        employeeId = generatedKeys.getLong(1);
        return employeeId.intValue();
    }

    // Mapper for employee
    public List<Employee> mapResultSetToEmployeeList(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while(resultSet.next()) {
            int employeeId = Integer.parseInt(resultSet.getString("employeeId"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int pcHours = Integer.parseInt(resultSet.getString("pcHours"));
            int networkHours = Integer.parseInt(resultSet.getString("networkHours"));
            int cableHours = Integer.parseInt(resultSet.getString("cableHours"));
            int pcYears = Integer.parseInt(resultSet.getString("pcYears"));
            int networkYears = Integer.parseInt(resultSet.getString("networkYears"));
            int cableYears = Integer.parseInt(resultSet.getString("cableYears"));
            Employee employee = new Employee(employeeId, firstName, lastName, pcHours, networkHours, cableHours, pcYears, networkYears, cableYears);
            employees.add(employee);
        }
        return employees;
    }

    // Returns all employees in a DefaultListModel for JList
    public DefaultListModel getAllEmployeesList() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT employee.employeeId, employee.firstName, employee.lastName FROM Employees employee");
        // Loop through the result set and add each author to the list
        while (resultSet.next()) {
            int employeeId = resultSet.getInt("employeeId");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            // Create a new author object and add it to the list
            Employee employee = new Employee(employeeId, firstName, lastName);
            employees.add(employee);
        }

        DefaultListModel<String> trainerListModel = new DefaultListModel<>();
        // Loop through the list of authors and add them to the list model as single String
        for (Employee employee : employees) {
            trainerListModel.addElement(employee.getEmployeeId() + ", " + employee.getLastName() + ", " + employee.getFirstName());
        }

        return trainerListModel;
    }

}
