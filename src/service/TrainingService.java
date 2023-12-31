package service;

import model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Training Service class for the Employee Application
 * This class contains sql statements for the application
 * @Author James Wyatt
 */
public class TrainingService extends MainService {

    // This method is used to update an employee with training
    public void updateEmployeeWithTraining(Employee employee) throws SQLException {
        String UPDATE_QUERY =
                "UPDATE employees " +
                        "SET firstName = '" + employee.getFirstName() + "', lastName = '" + employee.getLastName() +
                        "', pcHours = " + employee.getPcHours() + ", networkHours = " + employee.getNetworkHours() + ", cableHours = " + employee.getCableHours() +
                        ", pcYears = " + employee.getPcYears() + ", networkYears = " + employee.getNetworkYears() + ", cableYears = " + employee.getCableYears() +
                        " WHERE employeeId = " + employee.getEmployeeId();

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        statement.executeUpdate(UPDATE_QUERY);
    }
}
