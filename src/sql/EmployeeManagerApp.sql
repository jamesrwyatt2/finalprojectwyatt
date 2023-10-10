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
                    pcRequirements INT NOT NULL,
                    networkRequirements INT NOT NULL,
                    cableRequirements INT NOT NULL,
                    employeeIDs varchar (100) NOT NULL,
                    PRIMARY KEY (jobID)
);

INSERT INTO employees (firstName, lastName, pcHours, networkHours, cableHours, pcYears, networkYears, cableYears)
    VALUES
    ('James','Wyatt', 0, 0, 0, 1, 1, 5),
    ('Don','Deitel', 25, 25, 25, 1, 1, 1),
    ('Sammy','Fields', 1445, 800, 200, 1, 1, 1),
    ('Dan','Quirk', 1350, 800, 3400, 1, 2, 1),
    ('Michael','Morgano', 1200, 2300, 1500, 6, 5, 8);

INSERT INTO CERTS (certName, employeeID)
    VALUES
    ('PC', 3),
    ('PC', 4),
    ('Cable', 4),
    ('PC', 5),
    ('Network', 5),
    ('Cable', 5)
;

INSERT INTO TRAINERS (employeeID)
    VALUES
    (4),
    (5)
;


SELECT * FROM employees;

SELECT employee.firstName, Employee.LastName FROM Certs cert, Employees employee WHERE cert.CERTNAME = 'PC' AND cert.EMPLOYEEID = employee.EMPLOYEEID;