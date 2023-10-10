DROP TABLE employees;
DROP TABLE certs;
DROP TABLE trainers;
DROP TABLE jobs;

CREATE TABLE employees (
                        employeeID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
                        firstName varchar (20) NOT NULL,
                        lastName varchar (30) NOT NULL,
                        pcHours INT NOT NULL,
                        networkHours INT NOT NULL,
                        cableHours INT NOT NULL,
                        pcYears INT NOT NULL,
                        networkYears INT NOT NULL,
                        cableYears INT NOT NULL,
                        PRIMARY KEY (employeeID)
);

CREATE TABLE trainers (
                        trainerID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
                        employeeID INT NOT NULL,
                        FOREIGN KEY (employeeID) REFERENCES employees (employeeID)
);

CREATE TABLE certs (
                    certID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
                    certName varchar (100) NOT NULL,
                    employeeID INT NOT NULL,
                    FOREIGN KEY (employeeID) REFERENCES employees (employeeID)
);

CREATE TABLE jobs (
                    jobID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
                    title varchar (100) NOT NULL,
                    description varchar (100) NOT NULL,
                    requirements varchar (100) NOT NULL,
                    PRIMARY KEY (jobID)
);

INSERT INTO employees (firstName, lastName, pcHours, networkHours, cableHours, pcYears, networkYears, cableYears)
    VALUES
    ('James','Wyatt', 0, 0, 0, 0, 0, 0),
    ('Don','Deitel', 0, 0, 0, 0, 0, 0),
    ('Sammy','Fields', 0, 0, 0, 0, 0, 0),
    ('Dan','Quirk', 0, 0, 0, 0, 0, 0),
    ('Michael','Morgano', 0, 0, 0, 0, 0, 0);

SELECT * FROM employees;

SELECT employee.firstName, Employee.LastName FROM Certs cert, Employees employee WHERE cert.CERTNAME = 'PC' AND cert.EMPLOYEEID = employee.EMPLOYEEID;