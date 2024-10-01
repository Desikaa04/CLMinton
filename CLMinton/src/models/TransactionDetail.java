package models;

public class TransactionDetail {
	private String id;
	private String product;
	private Integer price;
	private Integer quantity;
	private Integer totalP;
	public TransactionDetail(String id, String product, Integer price, Integer quantity, Integer totalP) {
		super();
		this.id = id;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.totalP = totalP;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getTotalP() {
		return totalP;
	}
	public void setTotalP(Integer totalP) {
		this.totalP = totalP;
	}
}
