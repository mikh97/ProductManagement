package commandLinePackage;

public class Product {
	private String productCode;
	private String productName;
	private String productBrand;
	private String productDescription;
	private int productPrice;
	private int availability;
	
	
	public Product(String productCode, String productName, String productBrand, String productDescription,
			int productPrice, int availability) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.availability = availability;
	}
	
	@Override
	public String toString() {
		return "[ productCode: " + productCode + ", productName: " + productName + ", productBrand: " + productBrand
				+ ", productDescription: " + productDescription + ", productPrice: " + productPrice + ", stock: "
				+ availability + " ]";
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public int getAvailability() {
		return availability;
	}
	public void setAvailability(int availability) {
		this.availability = availability;
	}
	
}

