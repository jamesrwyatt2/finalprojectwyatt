package service;

import model.Employee;

import java.sql.*;
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
