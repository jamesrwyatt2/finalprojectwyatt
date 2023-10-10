package service;

import model.Cert;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertService extends MainService{

    private final EmployeeService employeeService = new EmployeeService();

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
