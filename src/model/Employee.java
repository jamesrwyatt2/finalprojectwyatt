package model;

/**
 * Employee Class for the Employee Application
 * This class is used to create Employee objects for list model
 * @Author James Wyatt
 */
public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private int pcHours;
    private int networkHours;
    private int cableHours;
    private int pcYears;
    private int networkYears;
    private int cableYears;

    // An all argument constructor
    public Employee(int employeeId, String firstName, String lastName, int pcHours, int networkHours, int cableHours, int pcYears, int networkYears, int cableYears) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pcHours = pcHours;
        this.networkHours = networkHours;
        this.cableHours = cableHours;
        this.pcYears = pcYears;
        this.networkYears = networkYears;
        this.cableYears = cableYears;
    }
    // A name only constructor
    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    // An id and name constructor
    public Employee(int employeeId, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPcHours() {
        return pcHours;
    }

    public void setPcHours(int pcHours) {
        this.pcHours = pcHours;
    }

    public int getNetworkHours() {
        return networkHours;
    }

    public void setNetworkHours(int networkHours) {
        this.networkHours = networkHours;
    }

    public int getCableHours() {
        return cableHours;
    }

    public void setCableHours(int cableHours) {
        this.cableHours = cableHours;
    }

    public int getPcYears() {
        return pcYears;
    }

    public void setPcYears(int pcYears) {
        this.pcYears = pcYears;
    }

    public int getNetworkYears() {
        return networkYears;
    }

    public void setNetworkYears(int networkYears) {
        this.networkYears = networkYears;
    }

    public int getCableYears() {
        return cableYears;
    }

    public void setCableYears(int cableYears) {
        this.cableYears = cableYears;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pcHours=" + pcHours +
                ", networkHours=" + networkHours +
                ", cableHours=" + cableHours +
                ", pcYears=" + pcYears +
                ", networkYears=" + networkYears +
                ", cableYears=" + cableYears +
                '}';
    }
}
