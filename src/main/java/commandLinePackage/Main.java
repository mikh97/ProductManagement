package commandLinePackage;

import java.sql.*;
import java.sql.Date;
import java.util.*;


public class Main {
	
	private static final Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		boolean appRunning = true;    	
		while (appRunning) {
			System.out.println("Welcome to the Product Management Application!");
			System.out.println("Enter '1' if you are a customer.");
			System.out.println("Enter '2' if you are an administrator");
			System.out.println("Enter anything else to exit the application.");
			System.out.println();
			String userInput = scanner.nextLine();
			switch (userInput) {
			
				case "1": {
					enterCustomerLoginPage();
					break;
				}
				
				case "2": {
					enterAdminLoginPage();
					break;
				}
				
			}
		}
	}
		
		
	private static void enterCustomerLoginPage() {
		boolean pageRunning = true;
		while (pageRunning) {		
			System.out.println("---Customer Login Page---");
			System.out.println("Enter '1' to login.");
			System.out.println("Enter '2' to register");
			System.out.println("Enter anything else to return to the previous page.");
			System.out.println();
			String userInput = scanner.nextLine();
			
			switch (userInput) {
			
				case "1": {
					System.out.println("Enter username");
					String username = scanner.nextLine();
					System.out.println("Enter password");
					String password = scanner.nextLine();
					Customer customer = null;
					try {
						customer = DatabaseUtility.loginWithCredentials(username, password);
						if (customer != null) {
							System.out.println("Successfully logged in. Welcome " + customer.getFirstName() + " " + customer.getLastName() + "!");
							enterCustomerInterfacePage(customer);
						} else {
							System.out.println("Could not log in.");
						}
					} catch (SQLException e) {
						System.out.println(e);
						System.out.println("Could not log in.");
					}
					break;
				}
				
				case "2": {
					System.out.println("Enter first name");
					String firstName = scanner.nextLine();
					System.out.println("Enter last name");
					String lastName = scanner.nextLine();
					System.out.println("Enter username");
					String username = scanner.nextLine();
					System.out.println("Enter password");
					String password = scanner.nextLine();
					System.out.println("Enter phone number");
					String phoneNumber = scanner.nextLine();
					System.out.println("Enter address");
					String address = scanner.nextLine();
					System.out.println("city");
					String city = scanner.nextLine();
					System.out.println("Enter state");
					String state = scanner.nextLine();
					System.out.println("Enter postal code");
					String postalCode = scanner.nextLine();
					
					try {
						DatabaseUtility.registerCustomer(username, password, firstName, lastName, phoneNumber, address, city, state, postalCode);
						Customer customer = DatabaseUtility.loginWithCredentials(username, password);
						if (customer != null) {
							System.out.println("Successfully registered and logged in. Welcome " + customer.getFirstName() + " " + customer.getLastName() + "!");
							enterCustomerInterfacePage(customer);
						} else {
							System.out.println("Could not register.");
						}
					} catch (SQLException e) {
						System.out.println(e);
						System.out.println("Could not register.");
					}
					break;
				}
				
				
				default:  {
					System.out.println("Returning to landing page...");
					pageRunning = false;
					break;
				}
			}	
		}
	}
	
	private static void enterCustomerInterfacePage(Customer customer) {
		boolean pageRunning = true;
		while (pageRunning) {		
			System.out.println();
			System.out.println("---Customer Interface---");
			System.out.println("Enter '1' to view products.");
			System.out.println("Enter '2' to create an order.");
			System.out.println("Enter '3' to view current orders.");
			System.out.println("Enter '4' to delete account.");
			System.out.println("Enter '5' to add a card to the account.");
			System.out.println("Enter '6' to view the card in your account.");
			System.out.println("Enter '7' to cancel an order.");
			System.out.println("Enter anything else to log out.");
			String userInput = scanner.nextLine();
			
			switch (userInput) {
			
				case "1": {
					System.out.println("Products");
					List<Product> availableProducts = new ArrayList<Product>();;
					try {
						availableProducts = DatabaseUtility.getProducts();
					} catch (SQLException e) {
						System.out.println(e);
					}
					
					if (availableProducts.size() == 0) {
						System.out.println("No products available.");
					} else {
						for (Product product : availableProducts) {
							System.out.println(product.toString());
						}
					}
					break;
				}
				
				case "2": {
					System.out.println("Enter productCode of product to order");
					String productCode = scanner.nextLine();
					System.out.println("Enter quantity to order");
					String quantity = scanner.nextLine();
					int intQuantity = 0;
					try {
						intQuantity = Integer.parseInt(quantity);
					} catch (NumberFormatException e) {
						System.out.println("Order failed: please enter an integer value");
						break;
					}
				
					try {
						DatabaseUtility.createOrder(customer.getCustomerID(), productCode, intQuantity);
						System.out.println("Order placed!");
					} catch (SQLException e) {
						System.out.println(e);
					}
					
					break;
				}
				
				case "3": {
					System.out.println("All orders and their details:");
					List<String> orderStatusList = new ArrayList<>();
					try {
						orderStatusList = DatabaseUtility.getOrderStatusList(customer.getCustomerID());
					} catch (SQLException e) {
						System.out.println(e);
					}
					if (orderStatusList.size() == 0) {
						System.out.println("No orders are currently in the system.");
					} else {
						for (String order : orderStatusList) {
							System.out.println(order);
						}
					}
					break;
				}
				
				case "4": {
					System.out.println("Deleting account...");
					try {
						DatabaseUtility.deleteAccount(customer.getCustomerID());
						pageRunning = false;
					} catch (SQLException e) {
						System.out.println(e);
					}
					break;
				}
				
				
				case "5": {
                    System.out.println("Enter your customer ID:");
                    String customerID = scanner.nextLine();
                    System.out.println("Enter your bank name:");
                    String bankName = scanner.nextLine();
                    System.out.println("Enter your card number:");
                    String cardNumber = scanner.nextLine();
                    System.out.println("Enter your card type:");
                    String cardType = scanner.nextLine();
                    System.out.println("Enter your cvv:");
                    String cvv = scanner.nextLine();
                    int intCustomerID = 0;
                    try {
                        intCustomerID = Integer.parseInt(customerID);
                    } catch (NumberFormatException e) {
                        System.out.println("Payment added failed: please enter an integer value");
                        break;
                    }
                    
                    try {
                        DatabaseUtility.addCard(intCustomerID, bankName, cardNumber, cardType, cvv);
                        System.out.println("Successfully added your card");
                    } catch (SQLException e) {
                        System.out.println(e);
                        System.out.println("Card cannot be added");
                    }
                    break;
                }
				
				case "6": {
					List<String> cardInfo = new ArrayList<>();
					System.out.println("Your Payment Information:");
					try {
						cardInfo = DatabaseUtility.viewCard(customer.getCustomerID());
					} catch (SQLException e) {
						System.out.println(e);
					}				
					if (cardInfo.size() == 0) {
						System.out.println("No cards are currently in the system.");
					} else {
						for (String card : cardInfo) {
							System.out.println(card);
						}
					}
					break;
				}
				
				case "7": {
                    System.out.println("Enter the Order ID of the order you wish to delete:");
                    String orderIDInput = scanner.nextLine();
                    int intOrderID = -1;
                    try {
                        intOrderID = Integer.parseInt(orderIDInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Order deletion failed: please enter an integer value");
                        break;
                    }
                    System.out.println("Deleting order if exists in your account...");
                    try {
                        DatabaseUtility.deleteOrder(intOrderID, customer.getCustomerID());
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                    break;
                }
				
				
				default:  {
					System.out.println("Logging out...");
					pageRunning = false;
					break;
				}
			}	
		}
	}
	
	private static void enterAdminLoginPage() {
		boolean pageRunning = true;
		while (pageRunning) {		
			System.out.println("---Admin Login Page---");
			System.out.println("Enter the admin password to login. (Admin Password: 'password123')");
			System.out.println("Enter 0 to return to the previous page.");
			System.out.println();
			String userInput = scanner.nextLine();
			
			switch (userInput) {
			
				case "password123": {
					System.out.println("Correct Password, logged in");
					enterAdminInterfacePage();
					break;
				}
				
				case "0": {
					System.out.println("Returning to landing page...");
					pageRunning = false;
					break;
				}
				
				default:  {
					System.out.println("Wrong password, please try again.");
					break;
				}
			}	
		}
	}
	
	private static void enterAdminInterfacePage() {
		boolean pageRunning = true;
		while (pageRunning) {		
			System.out.println("\n---Admin Interface---");
			System.out.println("Enter '1' to view products.");
			System.out.println("Enter '2' to add a product.");
			System.out.println("Enter '3' to delete a product.");
			System.out.println("Enter '4' to change the availability of a product."); // 
			System.out.println("Enter '5' to see all names in the system."); // set operation (union)
			System.out.println("Enter '6' to see the current order breakdown"); // aggregation
			System.out.println("Enter '7' to see all orders and their details"); // outer join
			System.out.println("Enter '8' to set the status of an order"); 
			System.out.println("Enter '9' to see all brands with atleast X amount of products in the system"); // group by and having
			System.out.println("Enter '10' to see all employees currently not assisting a customer"); // correlated subquery
			System.out.println("enter '11' to archive all orders by cutoff date");
			System.out.println("Enter anything else to log out.");
			System.out.println();
			String userInput = scanner.nextLine();
			
			switch (userInput) {
			
				case "1": {
					System.out.println("Products");
					try {
						List<Product> availableProducts = DatabaseUtility.getProducts();
						for (Product product : availableProducts) {
							System.out.println(product.toString());
						}
					} catch (SQLException e) {
						System.out.println(e);
					}
					break;
				}
				
				case "2": {
					System.out.println("Enter product code");
					String productCode = scanner.nextLine();
					System.out.println("Enter product name");
					String productName = scanner.nextLine();
					System.out.println("Enter product brand");
					String productBrand = scanner.nextLine();
					System.out.println("Enter product description");
					String productDescription = scanner.nextLine();
					System.out.println("Enter product price (int)");
					int productPrice = scanner.nextInt();
					System.out.println("Enter product availability (int)");
					int availability = scanner.nextInt();
					try {
						DatabaseUtility.addProduct(productCode, productName, productBrand, productDescription, productPrice, availability);
						System.out.println("Successfully added product");
					} catch (SQLException e) {
						System.out.println(e);
						System.out.println("Product not added");
					}
					break;
				}
				
				case "3": {
					System.out.println("Enter product code of product to delete");
					String productCode = scanner.nextLine();
					boolean deleted = false;
					try {
						deleted = DatabaseUtility.deleteProduct(productCode);
					} catch (SQLException e) {
						System.out.println(e);
						System.out.println("Could not delete product");
					}
					if (deleted) {
						System.out.println("Successfully delete product");
					} else {
						System.out.println("Could not delete product, product not found");
					}
					break;
				}
				
				case "4": {
					System.out.println("Enter the product code");
					String productCode = scanner.nextLine();
					System.out.println("Enter the new availability amount");
					String availability = scanner.nextLine();
					
					int newAvailability = -1;
					try {
						newAvailability = Integer.parseInt(availability);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input!");
						break;
					}
					Product product = null;
					try {
						product = DatabaseUtility.getProduct(productCode);
					} catch (SQLException e) {
						System.out.println(e);
						System.out.println("Could not get product");
						break;
					}
					
					if (product == null) {
						System.out.println("Product code not found");
						break;
					}
					
					try {
						DatabaseUtility.setAvailability(productCode, newAvailability);
						System.out.println("Product availabilty updated!");
					} catch (SQLException e) {
						System.out.println(e);
						System.out.println("Unable to update");
					}
					break;
				}
				
				case "5": {
					List<String> namesInSystemList = new ArrayList<>();
					try {
						namesInSystemList = DatabaseUtility.getNamesInSystem();
					} catch (SQLException e) {
						System.out.println(e);
					}
					
					if (namesInSystemList.size() == 0) {
						System.out.println("No persons found in the system");
					} else {
						System.out.println("Names in system: (names of all employees and customers)");
						System.out.print("|\t");
						for (String name: namesInSystemList) {
							System.out.print(name + "\t|\t");
						}
						System.out.println();
					}
					break;
					
				}
				
				case "6": {
					System.out.println("Order status breakdown:");
					List<String> orderStatusList = new ArrayList<>();
					try {
						orderStatusList = DatabaseUtility.getStatusBreakdownList();
					} catch (SQLException e) {
						System.out.println(e);
					}
					if (orderStatusList.size() == 0) {
						System.out.println("No orders available yet.");
					} else {
						for (String orderStatus : orderStatusList) {
							System.out.println("Order Status | "+ orderStatus);
						}
					}
					break;
					
				}
				
				case "7": {
					System.out.println("All orders and their details:");
					List<String> orderDetailsList = new ArrayList<>();
					try {
						orderDetailsList = DatabaseUtility.getOrderDetailsList();
					} catch (SQLException e) {
						System.out.println(e);
					}
					if (orderDetailsList.size() == 0) {
						System.out.println("No orders available yet.");
					} else {
						for (String order : orderDetailsList) {
							System.out.println(order);
						}
					}
					break;
				}
				
				case "8": {
					
					System.out.println("Enter the order ID");
					String orderIDInput = scanner.nextLine();
					
					int orderID = -1;
					try {
						orderID = Integer.parseInt(orderIDInput);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input!");
						break;
					}
					
					System.out.println("Enter the new status");
					String status = scanner.nextLine();
					
					
					try {
						DatabaseUtility.setOrderStatus(orderID, status);
					} catch (SQLException e){
						System.out.println(e);
					}
					
					System.out.println("Query complete");
					break;
				}
				
				
				case "9": {
					List<String> productBrandByCountList = new ArrayList<>();
					
					System.out.println("Enter the minimum product count per brand");
					String minCountInput = scanner.nextLine();
					int minCount = -1;
					try {
						minCount = Integer.parseInt(minCountInput);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input!");
						break;
					}
					
					
					if (minCount >= 0) {
						try {
							productBrandByCountList = DatabaseUtility.getBrandByCountList(minCount);
						} catch (SQLException e) {
							System.out.println(e);
						}
					}
					
					if (productBrandByCountList.size() == 0) {
						System.out.println("No brands meet this criteria.");
					} else {
						for (String brandAndCount : productBrandByCountList) {
							System.out.println(brandAndCount);
						}
					}
					break;
					
				}
				
				case "10": {
					System.out.println("All employees not currently assisting a customer:");
					List<String> inactiveEmployeeList = new ArrayList<>();
					try {
						inactiveEmployeeList = DatabaseUtility.inactiveEmployeeList();
					} catch (SQLException e) {
						System.out.println(e);
					}
					if (inactiveEmployeeList.size() == 0) {
						System.out.println("No employees meet this criteria.");
					} else {
						for (String employeeName : inactiveEmployeeList) {
							System.out.println(employeeName);
						}
					}
					break;
				}
				
				case "11": {
					System.out.println("Please cutoff date to archive orders from (inclusive)");
					System.out.println("Year: (2000 - 2021)");
					String yearInput = scanner.nextLine();	
					
					int year = -1;
					try {
						year = Integer.parseInt(yearInput);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input!");
						break;
					}
					System.out.println("Month: (1 - 12)");
					String monthInput = scanner.nextLine();
					
					int month = -1;
					try {
						month = Integer.parseInt(monthInput);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input!");
						break;
					}
				
						
					
					System.out.println("Day: (1 - 31)");
					String dayInput = scanner.nextLine();
					
					int day = -1;
					
					try {
						day = Integer.parseInt(dayInput);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input!");
						break;
					}
					
					Date date = new Date(year - 1900, month - 1, day);

					boolean archived = false;
					System.out.println(date);
					try {
						archived = DatabaseUtility.archiveOrders(date);
					} catch (SQLException e) {
						System.out.println(e);
						System.out.println("Could not archive orders");
					}
					if (archived) {
						System.out.println("Successfully archived orders");
					} else {
						System.out.println("Could not archive orders, invalid date");
					}
					break;
				}
				
				
				default:  {
					System.out.println("Logging out...");
					pageRunning = false;
					break;
				}
				
				
			}	
		}
	}
	
}


