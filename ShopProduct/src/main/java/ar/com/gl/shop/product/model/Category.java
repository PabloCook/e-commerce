package ar.com.gl.shop.product.model;

public class Category {
	
	private Long id;
	private String name;
	private String description;
	private Boolean enabled; 
	
	public Category() {		
		this.enabled = true;
	}	
	
	public Category(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.enabled = true;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		return "Categoria ---> ID: "+ id + ""
			 + "\n               Nombre: " + name + ""
			 + "\n               Descripción: " + description + "\n";
	}

	
	

}
