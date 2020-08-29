package ar.com.gl.shop.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "orderShop")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long productId;
	
	@Column(nullable = false)
	private Long customerId;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(nullable = false)
	private Double totalPrice;
	
	@Column(nullable = false)
	private Boolean disable;
}
