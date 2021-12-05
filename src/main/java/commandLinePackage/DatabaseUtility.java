package commandLinePackage;

import java.util.*;
import java.sql.*;
import java.sql.Date;

public class DatabaseUtility {
	
	private final static String connectionPassword = "mikkhail"; // your user password
	private final static String connectionUrl = "jdbc:mysql://localhost:3306/ProductManagement";
	
	
	/**
	 *  Private constructor: utility class not instantiable
	 */
	private DatabaseUtility() {}
	
	/**
	 *  Checks if username exists, then if password matches password in row. Returns row as a new Customer object.
	 */
	public static Customer loginWithCredentials(String username, String password) throws SQLException {
		 Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
         
		 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE username = ?");
         preparedStatement.setString(1, username);
         ResultSet resultSet = preparedStatement.executeQuery();
         
         if (!resultSet.isBeforeFirst()) {
        	 System.out.println("User not found in the Database!");
         } 
         
         else {
        	resultSet.next();
      		String retrievedPassword = resultSet.getString("password");
      		if (retrievedPassword.equals(password)) {
      			System.out.println("Credentials validated.");
      			Customer customer = new Customer(resultSet.getInt("customerID"), resultSet.getString("username"), 
      					resultSet.getString("password"), resultSet.getString("firstName"), resultSet.getString("lastName"),
      					resultSet.getString("phoneNumber"), resultSet.getString("address"), resultSet.getString("city"), 
      					resultSet.getString("state"), resultSet.getString("postalCode"), resultSet.getInt("salesEmployee"));
      			connection.close();
      			return customer;
      		} else {
      			System.out.println("Password is incorrect!");
      		}
         }
         connection.close();
         return null;
	}
	
	public static int getRandomEmployeeID() throws SQLException {
		
		Set<Integer> employeeIDs = new HashSet<>();
		
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT employeeID FROM Employees");
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (!resultSet.isBeforeFirst()) {
        	return -1;
        } else {
        	while (resultSet.next()) {
        		int employeeID = resultSet.getInt("employeeID");
        		employeeIDs.add(employeeID);
        	}
        }
        connection.close();
        List<Integer> employeeIDList = new ArrayList<>(employeeIDs);
        return employeeIDList.get(0);
	}
	
	
	public static void registerCustomer(String username, String password, String firstName, String lastName,
		String phoneNumber, String address, String city, String state, String postalCode) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer "
        		+ "(customerID, username, password, firstName, lastName, phoneNumber, address, city, state, postalCode, salesEmployee)"
        		+ "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, firstName);
        preparedStatement.setString(4, lastName);
        preparedStatement.setString(5, phoneNumber);
        preparedStatement.setString(6, address);
        preparedStatement.setString(7, city);
        preparedStatement.setString(8, state);
        preparedStatement.setString(9, postalCode);
        preparedStatement.setInt(10, getRandomEmployeeID());
        preparedStatement.executeUpdate();
        connection.close();
	}
	
	public static boolean createOrder(int customerID, String productCode, int quantity) throws SQLException {
		
		// get product
		Product selectedProduct = getProduct(productCode);
		
		// if productCode not in products, return false
		if (selectedProduct == null) {
			System.out.println("Product was not found");
			return false;
		}
		
		// if not enough availability, return false
		if (selectedProduct.getAvailability() < quantity || quantity < 1) {
			System.out.println("Order quantity exceeds availabilty.");
			return false;
		}
		
		if (quantity < 1) {
			System.out.println("Order quantity must be greater than 0.");
			return false;
		}
		
		int orderPrice = selectedProduct.getProductPrice() * quantity;
		
		// prepare insert to Orders using customerID
		
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Orders (customerID) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, customerID);
        preparedStatement.executeUpdate();
       
        // get orderID of newly generated order
        ResultSet keyResultSet = preparedStatement.getGeneratedKeys();
        int orderID = -1;
        if (keyResultSet.next()) {
        	orderID = (int) keyResultSet.getLong(1);
        }
	
		// prepare insert to OrderDetails with new orderID, 
        preparedStatement = connection.prepareStatement("INSERT INTO OrderDetails (orderID, productCode, quantityOrdered, totalPrice) VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, orderID);
        preparedStatement.setString(2, productCode);
        preparedStatement.setInt(3, quantity);
        preparedStatement.setInt(4, orderPrice);
        preparedStatement.executeUpdate();
        
        connection.close();
        return true;
	}
	
	
	public static List<Product> getProducts() throws SQLException {
		
		List<Product> productList = new ArrayList<>();
		
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Products");
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (!resultSet.isBeforeFirst()) {
        System.out.println("No products available.");
        } else {
        	while (resultSet.next()) {
        		String productCode = resultSet.getString("productCode");
        		String productName = resultSet.getString("productName");
        		String productBrand = resultSet.getString("productBrand");
        		String productDescription = resultSet.getString("productDescription");
        		int productPrice = resultSet.getInt("productPrice");
        		int availability = resultSet.getInt("availability");
        		Product product = new Product(productCode, productName, productBrand, productDescription, productPrice, availability);
        		productList.add(product);
        	}
        }
        connection.close();
        return productList;
	}
	
	public static Product getProduct(String selectedProductCode) throws SQLException {
		
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Products WHERE productCode = ?");
        preparedStatement.setString(1, selectedProductCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (!resultSet.isBeforeFirst()) {
        	connection.close();
        	return null;
        } else {
        	resultSet.next();
        	String productCode = resultSet.getString("productCode");
    		String productName = resultSet.getString("productName");
    		String productBrand = resultSet.getString("productBrand");
    		String productDescription = resultSet.getString("productDescription");
    		int productPrice = resultSet.getInt("productPrice");
    		int availability = resultSet.getInt("availability");
    		Product product = new Product(productCode, productName, productBrand, productDescription, productPrice, availability);
    		connection.close();
    		return product;
        }
	}
	
	public static void addProduct(String productCode, String productName, String productBrand, String productDescription, int productPrice, int availability) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Products VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, productCode);
        preparedStatement.setString(2, productName);
        preparedStatement.setString(3, productBrand);
        preparedStatement.setString(4, productDescription);
        preparedStatement.setInt(5, productPrice);
        preparedStatement.setInt(6, availability);
        preparedStatement.executeUpdate();
        connection.close();
	}
	
	
	public static boolean deleteProduct(String productCode) throws SQLException {
		
		if (getProduct(productCode) == null) {
			return false;
		}
		
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Products WHERE productCode= ?");
        preparedStatement.setString(1, productCode);
        preparedStatement.executeUpdate();
        connection.close();
        return true;
	}
	
	
	public static void setAvailability(String productCode, int newAvailability) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Products SET availability = ? WHERE productCode = ?");
		preparedStatement.setInt(1, newAvailability);
		preparedStatement.setString(2, productCode);
		preparedStatement.executeUpdate();
		connection.close();
	}
	
	public static List<String> getNamesInSystem() throws SQLException {
		List<String> namesInSystemList = new ArrayList<>();
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT firstName, lastName FROM Customer UNION SELECT employeeFirstName as firstName, employeeLastName as lastName FROM Employees;");
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
    		String firstName = resultSet.getString("firstName");
    		String lastName = resultSet.getString("lastName");
    		namesInSystemList.add(firstName + " " + lastName);
		}
		
		connection.close();
		return namesInSystemList;
	}

	public static List<String> getStatusBreakdownList() throws SQLException {
		List<String> statusBreakdownList = new ArrayList<>();
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(status) as count, status FROM orders GROUP BY status");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
    		String status = resultSet.getString("status");
    		int count = resultSet.getInt("count");
    		statusBreakdownList.add(status + ": " + count);
		}
		
		connection.close();
		return statusBreakdownList;
	}

	public static List<String> getOrderDetailsList() throws SQLException {
		List<String> orderDetailsList = new ArrayList<>();
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders right outer join OrderDetails using (orderID)");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
    		int orderID = resultSet.getInt("orderID");
    		String productCode = resultSet.getString("productCode");
    		int quantityOrdered = resultSet.getInt("quantityOrdered");
    		int totalPrice = resultSet.getInt("totalPrice");
    		Date orderDate = resultSet.getDate("orderDate");
    		String status = resultSet.getString("status");
    		Date shippedDate = resultSet.getDate("shippedDate");
    		int customerID = resultSet.getInt("customerID");
    		
    		
    		String orderDetails = "Order ID: " + orderID + "\tProduct Code: " + productCode + "\tQuantity Ordered: " + quantityOrdered +
    				"\tTotal Price: $" + totalPrice + "\tOrder Date: " + orderDate + "\tStatus: " + status + "\tShipped Date: " + shippedDate +
    				"\tCustomer ID: " + customerID;
    		orderDetailsList.add(orderDetails);
		}
		
		connection.close();
		return orderDetailsList;
	}
	
	public static List<String> getOrderStatusList(int customerID) throws SQLException {
		List<String> orderStatusList = new ArrayList<>();
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders right outer join OrderDetails using (orderID) WHERE customerID = ?");
		preparedStatement.setInt(1, customerID);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
    		int orderID = resultSet.getInt("orderID");
    		int totalPrice = resultSet.getInt("totalPrice");
    		Date orderDate = resultSet.getDate("orderDate");
    		String status = resultSet.getString("status");
    		Date shippedDate = resultSet.getDate("shippedDate");
    		
    		
    		String orderDetails = "Order ID: " + orderID + "\tTotal Price: $" + totalPrice + "\tOrder Date: " + orderDate + "\tStatus: " 
    		+ status + "\tShipped Date: " + shippedDate;   
    		orderStatusList.add(orderDetails);
		}
		
		connection.close();
		return orderStatusList;
	}

	public static void setOrderStatus(int orderID, String status) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Orders SET status = ? WHERE orderID = ?");
		preparedStatement.setString(1, status);
		preparedStatement.setInt(2, orderID);
		preparedStatement.executeUpdate();
		connection.close();
		
	}

	public static List<String> getBrandByCountList(int minimumProductCount) throws SQLException {
		List<String> brandByCountList = new ArrayList<>();
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement(" SELECT count(productCode) as count, productBrand FROM products GROUP BY productBrand HAVING count(productCode) >= ?;");
		preparedStatement.setInt(1, minimumProductCount);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
    		String brand = resultSet.getString("productBrand");
    		int count = resultSet.getInt("count");
    		brandByCountList.add(brand + ": " + count);
		}
		
		connection.close();
		return brandByCountList;
	}

	public static List<String> inactiveEmployeeList() throws SQLException {
		List<String> inactiveEmployeeList = new ArrayList<>();
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT employeeID, employeeFirstName, employeeLastName FROM Employees WHERE employeeID NOT IN (SELECT salesEmployee FROM Customer);");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
    		int employeeID = resultSet.getInt("employeeID");
    		String firstName = resultSet.getString("employeeFirstName");
    		String lastName = resultSet.getString("employeeLastName");
    		
    		
    		String employeeDetails = "Employee ID: " + employeeID + "\tFirst Name: " + firstName + "\tLast Name: " + lastName; 
    		inactiveEmployeeList.add(employeeDetails);
		}
		
		connection.close();
		return inactiveEmployeeList;
	}

	public static boolean archiveOrders(Date orderDate) throws SQLException {

		java.util.Date current = new java.util.Date();
		if (orderDate.after(new java.sql.Date(current.getTime())))
			return false;

		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
		PreparedStatement preparedStatement = connection.prepareStatement("CALL OrdersArchiveProcedure(?)");
		preparedStatement.setDate(1, orderDate);
		preparedStatement.executeUpdate();
		connection.close();
		return true;
	}
	
	public static void deleteAccount(int customerID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE customerID = ?");
        preparedStatement.setInt(1, customerID);
        preparedStatement.executeUpdate();
        connection.close();
	}
	
	public static boolean addCard(int customerID, String bankName, String cardNumber, String cardType, String cvv) throws SQLException {
        
        Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PaymentInformation (customerID, bankName, cardNumber, cardType, cvv) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setString(2, bankName);
        preparedStatement.setString(3, cardNumber);
        preparedStatement.setString(4, cardType);
        preparedStatement.setString(5, cvv);
        preparedStatement.executeUpdate();
        connection.close();
        return true;
    }
	
	public static void deleteOrder(int orderID, int customerID) throws SQLException{

        Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Orders WHERE orderID = ? AND customerID = ?");
        preparedStatement.setInt(1, orderID);
        preparedStatement.setInt(2, customerID);
        preparedStatement.executeUpdate();
        connection.close();
    }
	
	public static List<String> viewCard(int customerID) throws SQLException {
		
		List<String> cardInfo = new ArrayList<>();
		Connection connection = DriverManager.getConnection(connectionUrl,"root", connectionPassword);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM paymentInformation WHERE customerID= ?");
        preparedStatement.setInt(1, customerID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
			String bankName = resultSet.getString("bankName");
	    	String cardNumber = resultSet.getString("cardNumber");
	    	String cardType =resultSet.getString("cardType");
	    	String cvv = resultSet.getString("cvv");
	    	String cardDetails = "Bank Name: " + bankName + "\tCard Number: " + cardNumber + "\tCard Type: " + cardType + "\tCVV: " + cvv;
	    	cardInfo.add(cardDetails);
        }
    	connection.close();
		return cardInfo;
		
	}
	
}

