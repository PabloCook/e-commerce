package ar.com.gl.shop.product.model;

import java.util.concurrent.atomic.AtomicLong;

public class Stock {
	
	Long id;
	Integer quantity;
	String locationCode;
	private static final AtomicLong count = new AtomicLong(0);
	public Stock() {
		
	}
	
	public Stock(Integer quantity, String locationCode) {
		this.id = count.incrementAndGet();
		this.quantity = quantity;
		this.locationCode = locationCode;
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
		return "Stock [id=" + id + ", quantity=" + quantity + ", locationCode=" + locationCode + "]";
	}
	
	

}
