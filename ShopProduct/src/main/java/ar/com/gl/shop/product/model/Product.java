package ar.com.gl.shop.product.model;

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

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "stock_id", unique = true)
	private Stock stock;

	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

	public Product() {
		this.enabled = true;

	}

	public Product(String name, String description, Double price, Category category) {

		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.enabled = true;
	}

	@Override
	public String toString() {
		return "Producto ---> ID: " + id + "\n" + "              Nombre: " + name + "\n" + "              Descripci�n: "
				+ description + "\n" + "              Precio: $" + price + "\n" + "              Stock: "
				+ stock.getQuantity() + "\n" + "              Locacion: " + stock.getLocationCode() + "\n"
				+ "              Categoria: " + category.getName();
	}

}
