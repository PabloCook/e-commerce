package ar.com.gl.shop.product.model;

public class Stock {
	
	Product product;
	Integer quantity;
	String locationCode;
	
	public Stock() {
		
	}
	
	public Stock(Product product, Integer quantity, String locationCode) {
		this.product = product;
		this.quantity = quantity;
		this.locationCode = locationCode;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	@Override
	public String toString() {
		return "Stock [product=" + product + ", quantity=" + quantity + ", locationCode=" + locationCode + "]";
	}
	
	

}
