package models;

public class Cart {
	private String cartId, cartName, cartBrand;
	private Integer cartPrice, cartQty, cartTotal;
	public Cart(String cartId, String cartName, String cartBrand, Integer cartPrice, Integer cartQty,
			Integer cartTotal) {
		super();
		this.cartId = cartId;
		this.cartName = cartName;
		this.cartBrand = cartBrand;
		this.cartPrice = cartPrice;
		this.cartQty = cartQty;
		this.cartTotal = cartTotal;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getCartName() {
		return cartName;
	}
	public void setCartName(String cartName) {
		this.cartName = cartName;
	}
	public String getCartBrand() {
		return cartBrand;
	}
	public void setCartBrand(String cartBrand) {
		this.cartBrand = cartBrand;
	}
	public Integer getCartPrice() {
		return cartPrice;
	}
	public void setCartPrice(Integer cartPrice) {
		this.cartPrice = cartPrice;
	}
	public Integer getCartQty() {
		return cartQty;
	}
	public void setCartQty(Integer cartQty) {
		this.cartQty = cartQty;
	}
	public Integer getCartTotal() {
		return cartTotal;
	}
	public void setCartTotal(Integer cartTotal) {
		this.cartTotal = cartTotal;
	}
}
