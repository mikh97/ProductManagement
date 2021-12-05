DROP DATABASE IF EXISTS ProductManagement;

CREATE DATABASE ProductManagement;

USE ProductManagement;

DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS OrderDetails;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS OrdersArchive;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS PaymentInformation;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Offices;

CREATE TABLE Products (
	productCode VARCHAR(15) NOT NULL,
	productName VARCHAR(70) NOT NULL,
	productBrand VARCHAR(30) NOT NULL,
	productDescription TEXT NOT NULL,
	productPrice INT(10) NOT NULL,
	availability SMALLINT(5) NOT NULL,
	PRIMARY KEY (productCode)
  );

INSERT INTO Products VALUES ('A12', 'Nike Blazer', 'Nike', 'Comfy, cool!', 100, 50);
INSERT INTO Products VALUES ('B11', 'Nike Zoom', 'Nike', 'Fun, fast!', 90, 40);
INSERT INTO Products VALUES ('A23', 'Nike Air Force', 'Nike', 'Insane, cool!', 80, 60);
INSERT INTO Products VALUES ('E12', 'Nike React', 'Nike', 'Comfy, fast!', 120, 80);
INSERT INTO Products VALUES ('B22', 'Adidas Swift', 'Adidas', 'Insane, comfy!', 120, 10);
INSERT INTO Products VALUES ('A32', 'Adidas NMD', 'Adidas', 'Fast, cool!', 70, 80);

CREATE TABLE Offices (
	officeID VARCHAR(10) NOT NULL,
	officeAddress VARCHAR(50) NOT NULL,
	officeCity VARCHAR(50) NOT NULL,
	officeState VARCHAR(15) DEFAULT NULL,
	officePhone VARCHAR(50) NOT NULL,
	PRIMARY KEY (officeID)
  );

INSERT INTO Offices (officeID, officeAddress, officeCity, officeState, officePhone) VALUES ('A32CD', '50 Office Avenue', 'San Jose', 'CA', '(858)583-1020');
INSERT INTO Offices (officeID, officeAddress, officeCity, officeState, officePhone) VALUES ('B72DA', '30 Office Street', 'San Francisco', 'CA', '(455)313-4220');
INSERT INTO Offices (officeID, officeAddress, officeCity, officeState, officePhone) VALUES ('C89DD', '11 Office Road', 'Los Angeles', 'CA', '(453)145-5435');
INSERT INTO Offices (officeID, officeAddress, officeCity, officeState, officePhone) VALUES ('W26DA', '324 Office Avenue', 'San Diego', 'CA', '(234)134-1344');


CREATE TABLE Employees (
	employeeID INT(10) AUTO_INCREMENT,
	employeeFirstName VARCHAR(50) NOT NULL,
	employeeLastName VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	jobTitle VARCHAR(50) NOT NULL,
	officeID VARCHAR(10) NOT NULL,
	PRIMARY KEY (employeeID),
	CONSTRAINT employeeOffice FOREIGN KEY (officeID) REFERENCES Offices (officeID)
  );

INSERT INTO Employees (employeeID, employeeFirstName, employeeLastName, email, jobTitle, officeID) VALUES (4142, 'John', 'Adams', 'john.adams@gmail.com', 'Senior Sales Employee', 'A32CD');
INSERT INTO Employees (employeeID, employeeFirstName, employeeLastName, email, jobTitle, officeID) VALUES (6431, 'Bob', 'Sandman', 'bob.sandman@gmail.com', 'Junior Sales Associate', 'B72DA');
INSERT INTO Employees (employeeID, employeeFirstName, employeeLastName, email, jobTitle, officeID) VALUES (3254, 'Jane', 'Doe', 'janedoe@gmail.com', 'Junior Sales Marketing', 'C89DD');
INSERT INTO Employees (employeeID, employeeFirstName, employeeLastName, email, jobTitle, officeID) VALUES (2626, 'Holly', 'Wass', 'holywas@gmail.com', 'Senior Sales Agent', 'W26DA');
INSERT INTO Employees (employeeID, employeeFirstName, employeeLastName, email, jobTitle, officeID) VALUES (5005, 'Madison', 'Real', 'medisona@gmail.com', 'Senior Sales Associate', 'C89DD');
INSERT INTO Employees (employeeID, employeeFirstName, employeeLastName, email, jobTitle, officeID) VALUES (7411, 'Sally', 'Mills', 'sally.mills@gmail.com', 'Sales Associate', 'B72DA');

CREATE TABLE Customer (
	customerID INT(10) NOT NULL AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL UNIQUE,
	password VARCHAR(30) NOT NULL,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	phoneNumber VARCHAR(50) NOT NULL,
	address VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL,
	state VARCHAR(50) DEFAULT NULL,
	postalCode VARCHAR(15) DEFAULT NULL,
	salesEmployee INT(10) DEFAULT NULL,
	PRIMARY KEY (customerID),
	CONSTRAINT customerToEmployee FOREIGN KEY (salesEmployee) REFERENCES Employees (employeeID)
  );

INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('antonio', 'antonio123', 'Antonio', 'Mars', '(939)583-3921', '120 Green Road', 'San Jose', 'CA', '95123', 4142);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('anne', 'anne99', 'Anne', 'Parker', '(943)523-4323', '150 Purple Road', 'Los Angeles', 'CA', '95543', 6431);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('miguel', 'admin', 'Miguel', 'Morales', '(456)123-897', '111 2nd St', 'San Jose', 'CA', '95112', 3254);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('paul01', 'paulina', 'Paul', 'Smith', '(410)555-6576', '17 Hicklemoore Road', 'Pawtucket', 'Rhode Island', '02860', 2626);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('santanotreal', 'mrberseker', 'Miles', 'Reed', '(364)432-785', '78 Berry St', 'Santa Cruz', 'CA', '95841', 5005);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('pedrogar', 'portuguesduck', 'Lego', 'Las', '(780)453-786', '2331 Nugget Ave', 'Monterey', 'CA', '72027', 4142);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('jimny4x4', 'gundam123', 'Jackie', 'Lynn', '(042)045-343', '363 11th St', 'San Ramon', 'CA', '78652', 6431);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('mk1728', 'vtasucks', 'Trann', 'Pho', '(500)453-789', '505 Arctic Monkey St', 'Las Vegas', 'CA', '02752', 3254);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('llamaMaster', 'incorrect', 'Hehe', 'Michael', '(204)057-087', '234 Hillsborough Ave', 'Beverly Hills', 'CA', '20752', 2626);
INSERT INTO Customer (username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee) VALUES ('porcupee', 'idkbspass', 'Jacklyn', 'Hernandez', '(786)780-078', '112 3rd St', 'San Jose', 'CA', '95124', 5005);


CREATE TABLE PaymentInformation (
	customerID INT(10) NOT NULL,
	bankName VARCHAR(50) NOT NULL,
	cardNumber VARCHAR(50) NOT NULL,
	cardType VARCHAR(15) NOT NULL,
	cvv VARCHAR(15) NOT NULL,
	PRIMARY KEY (customerID),
	CONSTRAINT customerpayment FOREIGN KEY (customerID) REFERENCES Customer (customerID) ON DELETE CASCADE
  );

INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (1, 'Chase', '1122 2243 2211 6653', 'Credit', '121');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (2, 'WesterUnion', '5432 5432 1253 5431', 'Debit', '124');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (2, 'BankOfAmerica', '2344 1234 6543 8123', 'Credit', '264');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (3, 'BankofTheWest', '1542 2659 3659 7894', 'Debit', '958');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (4, 'CommonWealth', '2655 8484 3659 7848', 'Credit', '261');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (5, 'CaliforniaBank', '1648 8484 5954 2645', 'Debit', '348');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (6, 'UnionBank', '0348 0218 2090 9554', 'Credit', '484');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (7, 'PeopleBank', '3201 5451 6595 7812', 'Debit', '998');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (8, 'WellsFargo', '9115 9548 8483 8784', 'Debit', '326');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (9, 'CityBank', '1233 8774 1923 0200', 'Credit', '008');
INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (10, 'JPMorgan', '1515 2310 7894 0515', 'Debit', '003');

CREATE TABLE Orders (
	orderID INT(10) AUTO_INCREMENT,
	orderDate DATE NOT NULL DEFAULT (CURRENT_DATE),
	status VARCHAR(15) NOT NULL DEFAULT 'Not Shipped',
	shippedDate DATE DEFAULT NULL,
	customerID INT(10) NOT NULL,
	PRIMARY KEY (orderID),
	CONSTRAINT customerorder FOREIGN KEY (customerID) REFERENCES Customer (customerID) ON DELETE CASCADE
  );

INSERT INTO Orders (customerID) VALUES (1);
INSERT INTO Orders (customerID) VALUES (2);
INSERT INTO Orders (customerID) VALUES (2);
INSERT INTO Orders (customerID) VALUES (3);
INSERT INTO Orders (customerID) VALUES (4);
INSERT INTO Orders (customerID) VALUES (5);
INSERT INTO Orders (customerID) VALUES (6);
INSERT INTO Orders (customerID) VALUES (7);
INSERT INTO Orders (customerID) VALUES (7);
INSERT INTO Orders (customerID) VALUES (7);
INSERT INTO Orders (customerID) VALUES (8);
INSERT INTO Orders (customerID) VALUES (9);
INSERT INTO Orders (customerID, status, shippedDate) VALUES (10,'Shipped', '2021-12-04');
INSERT INTO Orders (customerID, status, shippedDate) VALUES (8, 'Shipped', '2021-12-04');
INSERT INTO Orders (customerID, status, shippedDate) VALUES (9, 'Shipped', '2021-12-04');

CREATE TABLE OrderDetails (
	orderID INT(10) NOT NULL,
	productCode VARCHAR(15) NOT NULL,
	quantityOrdered SMALLINT(5) NOT NULL,
	totalPrice INT(30) NOT NULL,
	PRIMARY KEY (orderID, productCode),
	CONSTRAINT orderdetails_1 FOREIGN KEY (orderID) REFERENCES Orders (orderID) ON DELETE CASCADE,
	CONSTRAINT orderdetails_2 FOREIGN KEY (productCode) REFERENCES Products (productCode)
  );

INSERT INTO OrderDetails VALUES (1, 'A12', 3, 300);
INSERT INTO OrderDetails VALUES (2, 'B11', 2, 180);
INSERT INTO OrderDetails VALUES (3, 'A32', 4, 280);
INSERT INTO OrderDetails VALUES (4, 'A32', 1, 70);
INSERT INTO OrderDetails VALUES (5, 'E12', 5, 600);
INSERT INTO OrderDetails VALUES (6, 'B22', 2, 240);
INSERT INTO OrderDetails VALUES (7, 'A12', 10, 1000);
INSERT INTO OrderDetails VALUES (8, 'B11', 1, 90);
INSERT INTO OrderDetails VALUES (9, 'B11', 5, 450);
INSERT INTO OrderDetails VALUES (10, 'B22', 3, 360);
INSERT INTO OrderDetails VALUES (11, 'E12', 1, 120);
INSERT INTO OrderDetails VALUES (12, 'A32', 20, 1400);
INSERT INTO OrderDetails VALUES (13, 'E12', 10, 1200);
INSERT INTO OrderDetails VALUES (14, 'A23', 6, 480);
INSERT INTO OrderDetails VALUES (15, 'A23', 30, 2400);


CREATE TABLE OrdersArchive (
	orderID INT(10) NOT NULL,
	orderDate DATE NOT NULL,
	status VARCHAR(15) NOT NULL,
	shippedDate DATE,
	customerID INT(10) NOT NULL
  );


DROP TRIGGER IF EXISTS PriceUpdateTrigger;
DROP TRIGGER IF EXISTS AvailabilityUpdateTrigger;
DROP TRIGGER IF EXISTS AvailabilityInsertTrigger;


DELIMITER $$
CREATE TRIGGER PriceUpdateTrigger
BEFORE UPDATE ON OrderDetails
FOR EACH ROW
BEGIN
	IF NEW.quantityOrdered <> OLD.quantityOrdered THEN
		SET NEW.totalPrice = OLD.totalPrice +
		(NEW.quantityOrdered - OLD.quantityOrdered) * (SELECT productPrice FROM Products WHERE productCode = NEW.productCode);
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER AvailabilityUpdateTrigger
AFTER UPDATE ON OrderDetails
FOR EACH ROW
BEGIN
	IF NEW.quantityOrdered <> OLD.quantityOrdered THEN
		UPDATE Products SET availability = (availability - NEW.quantityOrdered + OLD.quantityOrdered)
		WHERE productCode = NEW.productCode;
	END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER AvailabilityInsertTrigger
AFTER INSERT ON OrderDetails
FOR EACH ROW
BEGIN
	UPDATE Products SET availability = (availability - NEW.quantityOrdered)
	WHERE productCode = NEW.productCode;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS OrdersArchiveProcedure;


-- I deleted from OrderDetails aswell because i was getting key error when attempting to delete from orders
DELIMITER $$
CREATE PROCEDURE OrdersArchiveProcedure(IN cutOff DATE)
BEGIN
	INSERT INTO OrdersArchive SELECT * FROM Orders WHERE orderDate <= cutOff;
	DELETE FROM Orders WHERE orderDate <= cutOff;
END$$
DELIMITER ;