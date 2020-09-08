package ar.com.gl.shop.product.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name" , nullable = false)
	private String name;

	@Column(name = "description" , nullable = false)
	private String description;

	@Column(name = "price" , nullable = false)
	private Double price;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "stock_id", unique = true , nullable = false)
	private Stock stock;

	@Column(name = "enabled" , nullable = false)
	private Boolean enabled;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id" , nullable = false)
	private Category category;
	
	@Column
	private LocalDate date;

	public Product() {
		this.enabled = true;

	}

	public Product(String name, String description, Double price, Category category) {

		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.enabled = true;
		this.date = LocalDate.now();
	}

}
