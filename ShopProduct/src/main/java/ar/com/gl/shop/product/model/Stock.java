package ar.com.gl.shop.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "stock")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "quantity" , nullable = false)
	private Integer quantity;

	@Column(name = "locationCode" , nullable = false)
	private String locationCode;

	@Column(name = "enabled" , nullable = false)
	private Boolean enabled;

	public Stock() {
		enabled = true;
	}

	public Stock(Integer quantity, String locationCode) {
		this.quantity = quantity;
		this.locationCode = locationCode;
		this.enabled = true;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", quantity=" + quantity + ", locationCode=" + locationCode + "]";
	}

}
