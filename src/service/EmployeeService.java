package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeService extends MainService {

    public void createEmployee( String firstName, String lastName,
                                int pcHours, int networkHours, int cableHours,
                                int pcYears, int networkYears, int cableYears) throws SQLException {
        String INSERT_QUERY =
                "INSERT INTO employees (firstName, lastName, pcHours, networkHours, cableHours, pcYears, networkYears, cableYears) " +
                        "VALUES ('" + firstName + "', '" + lastName +
                        "', " + pcHours + ", " + networkHours + ", " + cableHours +
                        ", " + pcYears + ", " + networkYears + ", " + cableYears + ")";

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        statement.execute(INSERT_QUERY);
    }
}
