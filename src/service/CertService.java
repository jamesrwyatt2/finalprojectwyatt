package service;

import model.Cert;
import model.Employee;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CertService Class for the Employee Application
 * This class contains the main method and sql statements for the application
 * @Author James Wyatt
 */
public class CertService extends MainService{
    // Utilize the EmployeeService to get employee data
    private final EmployeeService employeeService = new EmployeeService();

    /**
     * This method is used to get all certs from the database and return them in a List
     * @param employeeId
     * @return A List of cert employees
     * @throws SQLException
     */
    public List<Cert> getCertsByEmployeeId(int employeeId) throws SQLException {
        String SELECT_QUERY =
                "SELECT * " +
                        "FROM certs " +
                        "WHERE employeeId = " + employeeId;

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
        return mapResultSetToCertList(resultSet);
    }

    // This method runs the cert processes
    public void certQualifyChecker(int employeeId) throws SQLException{
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
            createCertIfQualified(employee);
        }
    }
    // Return a list of certs in a DefaultListModel for JList
    public DefaultListModel getCertList(String certName) throws SQLException{
        List<Employee> employees = new ArrayList<>();

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT employee.employeeId, employee.firstName, employee.lastName FROM Employees employee, Certs cert WHERE employee.employeeId = cert.employeeId AND cert.certName = '" + certName + "'");
        // Loop through the result set and add each author to the list
        while (resultSet.next()) {
            int employeeId = Integer.parseInt(resultSet.getString("employeeId"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            // Create a new author object and add it to the list
            Employee employee = new Employee(employeeId, firstName, lastName);
            employees.add(employee);
        }

        DefaultListModel<String> certListModel = new DefaultListModel<>();
        // Loop through the list of authors and add them to the list model as single String
        for (Employee employee : employees) {
            certListModel.addElement(employee.getEmployeeId() + ", " + employee.getLastName() + ", " + employee.getFirstName());
        }

        return certListModel;
 }

    // Private helper methods //

    // Map the result set to a list of certs
    private List<Cert> mapResultSetToCertList(ResultSet resultSet) throws SQLException {
        List<Cert> certs = new ArrayList<>();
        while(resultSet.next()) {
            int certId = Integer.parseInt(resultSet.getString("certId"));
            int employeeId = Integer.parseInt(resultSet.getString("employeeId"));
            String certName = resultSet.getString("certName");
            Cert cert = new Cert(certId, employeeId, certName);
            certs.add(cert);
        }
        return certs;
    }

    // If they can be certified, create the cert
    private void createCertIfQualified(Employee employee) throws SQLException {
        if(employee.getPcHours() >= 1000) {
            createCert(employee.getEmployeeId(), "PC");
        }
        if(employee.getNetworkHours() >= 1000) {
            createCert(employee.getEmployeeId(), "Network");
        }
        if(employee.getCableHours() >= 1000) {
            createCert(employee.getEmployeeId(), "Cable");
        }
    }

    // Check if the cert already exists, if not create it
    private void createCert(int employeeId, String certName) throws SQLException{

        getCertsByEmployeeId(employeeId).forEach(cert -> {
            if(cert.getCertName().equals(certName)) {
                System.out.println("Cert already exists");
                return;
            }
        });

        String INSERT_QUERY =
                "INSERT INTO certs (employeeId, certName) " +
                        "VALUES (" + employeeId + ", '" + certName + "')";

        Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement();
        statement.executeUpdate(INSERT_QUERY);

    }
}
