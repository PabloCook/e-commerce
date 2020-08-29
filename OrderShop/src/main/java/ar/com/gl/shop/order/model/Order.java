package ar.com.gl.shop.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "order")
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long productId;
	private Long customerId;
	private Integer quantity;
	private Double totalPrice;
}
