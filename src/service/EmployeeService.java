package service;

import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService extends MainService {

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

}
