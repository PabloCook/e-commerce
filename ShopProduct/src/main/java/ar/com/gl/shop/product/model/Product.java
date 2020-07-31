package ar.com.gl.shop.product.model;

public class Product {
	
	private Long id;
	private String name;
	private String description;
	private Double price;
	private Stock stock;
	private Boolean enabled;
	private Category category;
	
public Product() {
		this.enabled = true;
	}
	
	public Product(Long id, String name, String description, Double price, Category category) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.enabled = true;
		this.stock = new Stock(null, 0,"Establecer");
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return  "Producto ---> ID: "+ id +"\n"
			  + "              Nombre: " + name + "\n"
			  + "              Descripción: " + description + "\n"
			  + "              Precio: $" + price + "\n"
			  + "              Stock: " + stock.getQuantity() + "\n"
			  + "              Locacion: " + stock.getLocationCode() + "\n"
			  + "              Categoria: " + category.getName();
	}
	
	

}
