package ar.com.gl.shop.product.model;

import java.util.concurrent.atomic.AtomicLong;

public class Stock {
	
	private Long id;
	private Integer quantity;
	private String locationCode;
	private Boolean enabled;
	
	private static final AtomicLong count = new AtomicLong(0);
	
	public Stock() {
		enabled = true;
	}
	
	public Stock(Integer quantity, String locationCode) {
		this.id = count.incrementAndGet();
		this.quantity = quantity;
		this.locationCode = locationCode;
		enabled = true;
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
