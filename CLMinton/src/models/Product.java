package models;

public class Product {
	private String ProductID;
	private String ProductName;
	private String ProductMerk;
	private int ProductPrice;
	private int ProductStock;
	public Product(String productID, String productName, String productMerk, int productPrice, int productStock) {
		super();
		ProductID = productID;
		ProductName = productName;
		ProductMerk = productMerk;
		ProductPrice = productPrice;
		ProductStock = productStock;
	}
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getProductMerk() {
		return ProductMerk;
	}
	public void setProductMerk(String productMerk) {
		ProductMerk = productMerk;
	}
	public int getProductPrice() {
		return ProductPrice;
	}
	public void setProductPrice(int productPrice) {
		ProductPrice = productPrice;
	}
	public int getProductStock() {
		return ProductStock;
	}
	public void setProductStock(int productStock) {
		ProductStock = productStock;
	}
}
