CREATE DATABASE ProductManagement;

USE ProductManagement;

DROP TABLE IF EXISTS 'Products';
DROP TABLE IF EXISTS 'OrderDetails';
DROP TABLE IF EXISTS 'Orders';
DROP TABLE IF EXISTS 'Customer';
DROP TABLE IF EXISTS 'PaymentInformation';
DROP TABLE IF EXISTS 'Employees';
DROP TABLE IF EXISTS 'Offices';

CREATE TABLE 'Products' (
	'productCode' VARCHAR(15) NOT NULL,
	'productName' VARCHAR(70) NOT NULL,
	'productBrand' VARCHAR(30) NOT NULL,
	'productDescription' TEXT NOT NULL,
	'productPrice' INT(10) NOT NULL,
	'availability' SMALLINT(5) NOT NULL,
	PRIMARY KEY ('productCode')
  );

CREATE TABLE 'OrderDetails' (
	'orderID' INT(10) NOT NULL,
	'productCode' VARCHAR(15) NOT NULL,
	'quantityOrdered' SMALLINT(5) NOT NULL,
	'totalPrice' INT(30) NOT NULL,
	PRIMARY KEY ('orderID', 'productCode'),
	CONSTRAINT 'orderdetails_1' FOREIGN KEY ('orderID') REFERENCES 'Orders' ('orderID'),
	CONSTRAINT 'orderdetails_2' FOREIGN KEY ('productCode') REFERENCES 'Products' ('productCode')
  );

-- Table to be Archived
CREATE TABLE 'Orders' (
	'orderID' INT(10) NOT NULL,
	'orderDate' DATE NOT NULL,
	'status' VARCHAR(15) NOT NULL,
	'shippedDate' DATE DEFAULT NULL,
	'customerID' INT(10) NOT NULL,
	PRIMARY KEY ('orderID'),
	KEY 'customerID' ('customerID'),
	CONSTRAINT 'customerorder' FOREIGN KEY ('customerID') REFERENCES 'Customer' ('customerID')
  );

CREATE TABLE 'Customer' (
	'customerID' INT(10) NOT NULL,
	'firstName' VARCHAR(50) NOT NULL,
	'lastName' VARCHAR(50) NOT NULL,
	'phoneNumber' VARCHAR(50) NOT NULL,
	'address' VARCHAR(50) NOT NULL,
	'city' VARCHAR(50) NOT NULL,
	'state' VARCHAR(50) DEFAULT NULL,
	'postalCode' VARCHAR(15) DEFAULT NULL,
	'salesEmployee' INT(10) NOT NULL,
	PRIMARY KEY ('customerID'),
	KEY 'salesEmployee' ('salesEmployee'),
	CONSTRAINT 'customerToEmployee' FOREIGN KEY ('salesEmployee') REFERENCES 'Employees' ('employeeID')
  );

CREATE TABLE 'Employees' (
	'employeeID' INT(10) NOT NULL,
	'employeeFirstName' VARCHAR(50) NOT NULL,
	'employeeLastName' VARCHAR(50) NOT NULL,
	'email' VARCHAR(100) NOT NULL,
	'jobTitle' VARCHAR(50) NOT NULL,
	'officeID' VARCHAR(10) NOT NULL,
	PRIMARY KEY ('employeeID'),
	KEY 'officeID' ('officeID'),
	CONSTRAINT 'employeeOffice' FOREIGN KEY ('officeID') REFERENCES 'Offices' ('officeID')
  );

CREATE TABLE 'Offices' (
	'officeID' VARCHAR(10) NOT NULL,
	'officeAddress' VARCHAR(50) NOT NULL,
	'officeCity' VARCHAR(50) NOT NULL,
	'officeState' VARCHAR(15) DEFAULT NULL,
	'officePhone' VARCHAR(50) NOT NULL,
	PRIMARY KEY ('officeID')
  );

CREATE TABLE 'PaymentInformation' (
	'customerID' INT(10) NOT NULL,
	'bankName' VARCHAR(50) NOT NULL,
	'cardNumber' VARCHAR(50) NOT NULL,
	'cardType' VARCHAR(15) NOT NULL,
	'cvv' VARCHAR(15) NOT NULL,
	PRIMARY KEY ('customerID')
  );