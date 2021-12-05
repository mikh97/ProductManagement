package commandLinePackage;

public class Customer {
	
	private int customerID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String postalCode;
	private int salesEmployee;
	
	public Customer(int customerID, String username, String password, String firstName, String lastName,
			String phoneNumber, String address, String city, String state, String postalCode, int salesEmployee) {
		super();
		this.customerID = customerID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.salesEmployee = salesEmployee;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public int getSalesEmployee() {
		return salesEmployee;
	}
}

