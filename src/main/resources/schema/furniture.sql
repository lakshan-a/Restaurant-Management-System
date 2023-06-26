drop database restaurantManagement;
CREATE DATABASE restaurantManagement;
USE restaurantManagement;

CREATE TABLE Employee(
                         empId VARCHAR(10),
                         empName VARCHAR(30) NOT NULL ,
                         address TEXT NOT NULL ,
                         dob DATE NOT NULL ,
                         contactNo VARCHAR(15) UNIQUE NOT NULL ,
                         email VARCHAR(50) ,
                         nic VARCHAR(15) UNIQUE NOT NULL ,
                         CONSTRAINT PRIMARY KEY (empId)
);

CREATE TABLE Salary(
                       salaryId VARCHAR(20),
                       empId VARCHAR(20),
                       salaryPaymentMethod VARCHAR(30) NOT NULL ,
                       salaryPayment DECIMAL (8,2) NOT NULL ,
                       salaryOt DECIMAL (7,2),
                       CONSTRAINT PRIMARY KEY (SalaryId),
                       CONSTRAINT FOREIGN KEY (EmpId) REFERENCES Employee(EmpId)
                           ON UPDATE CASCADE
                           ON DELETE CASCADE

);


CREATE TABLE User(
                     Password VARCHAR(10),
                     UserName VARCHAR(10),
                     email    VARCHAR(50) UNIQUE NOT NULL,
                     CONSTRAINT PRIMARY KEY (Password)

);

CREATE TABLE EmployeeAttendance(
                                   addendanceId VARCHAR(10),
                                   empId VARCHAR(10),
                                   attendance VARCHAR(30) NOT NULL,
                                   workingHourse INT(30) NOT NULL,
                                   date VARCHAR(30) NOT NULL,
                                   CONSTRAINT PRIMARY KEY (addendanceId),
                                   CONSTRAINT FOREIGN KEY(empId) REFERENCES Employee(empId)
                                       ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Customer(
                         custId VARCHAR(10),
                         custName VARCHAR(30) NOT NULL ,
                         contactNo VARCHAR(15) NOT NULL ,
                         address TEXT NOT NULL ,
                         email VARCHAR(50) ,
                         CONSTRAINT PRIMARY KEY (custId)
);

CREATE TABLE Orders(
                       orderId VARCHAR(10),
                       custId VARCHAR(10),
                       payment DECIMAL(8,2) NOT NULL ,
                       time TIME NOT NULL ,
                       date DATE NOT NULL ,
                       deliveryStatus VARCHAR(10) DEFAULT 'Pending' ,
                       CONSTRAINT PRIMARY KEY (orderId),
                       CONSTRAINT FOREIGN KEY (custId) REFERENCES Customer(custId)
                           ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Delivery(
                         deliveryId VARCHAR(10),
                         empId VARCHAR(10) ,
                         orderId VARCHAR(10) UNIQUE NOT NULL ,
                         location TEXT,
                         deliveryDate VARCHAR(20) DEFAULT 'pending',
                         dueDate VARCHAR(20) DEFAULT 'not given',
                         deliveryStatus VARCHAR(10) DEFAULT 'pending',
                         CONSTRAINT PRIMARY KEY (deliveryId),
                         CONSTRAINT FOREIGN KEY (empId) REFERENCES Employee(empId)
                             ON UPDATE CASCADE ON DELETE CASCADE ,
                         CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(orderId)
                             ON UPDATE CASCADE ON DELETE CASCADE

);




CREATE TABLE Item(
                     itemId VARCHAR(6) NOT NULL,
                     itemName VARCHAR(30) NOT NULL,
                     unitPrize Int(30),
                     qtyOnHand INT,
                     CONSTRAINT PRIMARY KEY (itemId)

);

CREATE TABLE food(
                     Food_num Varchar(30) PRIMARY KEY,
                     Description Varchar(30)NOT NULL,
                     Price DECIMAL(10,2) NOT NULL,
                     qtyOnHand INT,
                     itemId VARCHAR (30),
                     CONSTRAINT FOREIGN KEY(itemId) REFERENCES item(itemId)
);


CREATE TABLE OrderDetail(
                            orderId VARCHAR(10),
                            Food_num VARCHAR(10),
                            qty INT NOT NULL ,
                            CONSTRAINT PRIMARY KEY (orderId,Food_num),
                            CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(orderId)
                                ON UPDATE CASCADE ON DELETE CASCADE,
                            CONSTRAINT FOREIGN KEY (Food_num) REFERENCES food(Food_num)
                                ON UPDATE CASCADE ON DELETE CASCADE
);