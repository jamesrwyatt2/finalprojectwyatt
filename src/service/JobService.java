package service;

import org.apache.derby.client.am.SqlException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JobService extends MainService {

    public void insertJob(String title, String description, int pcRequirements, int networkRequirements, int cableRequirements, String EmployeeIds) throws SQLException {
        String INSERT_QUERY =
                "INSERT INTO jobs (title, description, pcRequirements, networkRequirements, cableRequirements, EmployeeIds) " +
                        "VALUES ('" + title + "', '" + description + "', " + pcRequirements + ", " + networkRequirements + ", " + cableRequirements + ", '" + EmployeeIds + "')";
        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        int affectedRows = statement.executeUpdate(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

    }
}
