package ar.com.gl.shop.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Category;
import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.Repository;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.services.CategoryService;
import ar.com.gl.shop.product.services.ProductService;
import ar.com.gl.shop.product.servicesimpl.CategoryServiceImpl;
import ar.com.gl.shop.product.servicesimpl.ProductServiceImpl;



public class AppTest{
	
	static Repository repository;
	static CategoryService categoryService;
	static ProductService productService;
	static Category category;
	static Product product;
	static Stock stock;
	static ItemNotFound itemNotFound;
	private static final InputStream DEFAULT_STDIN = System.in;
	
	@AfterEach
	void resetSystem() {
		System.setIn(DEFAULT_STDIN);
	}
	
	@BeforeEach
	void beforeEachTest() {
		repository = new RepositoryImpl();
		categoryService = new CategoryServiceImpl();
		productService = new ProductServiceImpl();
		category = new Category();
		product = new Product();
		stock = new Stock();
		itemNotFound = new ItemNotFound("Error");
	}
	
	@Test
	@DisplayName("Custom exception")
	void testCustomException() {
		
		try {
			throw itemNotFound;
		} catch (ItemNotFound e) {
			assertEquals("Error", e.getMessage());
		}
	}
	
	
	@Nested
	@DisplayName("Testeo de modelos")
	class ModelsTest{
		
		
		@Nested
		@DisplayName("Metodos clase repository")
		class RepositoryTest{
			@Test
			@DisplayName("agregar Categoria a lista de categorias")
			void testCreateNewCategory() {
				
				repository.getListaCategorias().add(category);
				
				assertNotNull(repository.getListaCategorias().get(0));
			}
			
			@Test
			@DisplayName("agregar Producto a lista de productos")
			void testCreateNewProduct() {
				
				repository.getListaProductos().add(product);
				
				assertNotNull(repository.getListaProductos().get(0));
			}
			
			@Test
			@DisplayName("agregar Stock a lista de stocks")
			void testCreateNewStock() {
				
				repository.getListaStock().add(stock);
				
				assertNotNull(repository.getListaStock().get(0));
			}
			
			
		}
		
		@Nested
		@DisplayName("Metodos clase Category")
		class CategoryTest{

			
			@Test
			@DisplayName("setId y getId")
			void categorySetAndGetId() {
				
				Long id = 1l;
				
				category.setId(id);
				
				assertEquals(id, category.getId());
			}
			
			@Test
			@DisplayName("setName y getName")
			void categorySetAndGetName() {
				
				String name = "Nombre Generico";
				
				category.setName(name);
				
				assertEquals(name, category.getName());
			}
			
			@Test
			@DisplayName("setDescription y getDescription")
			void categorySetAndGetDescription() {
				
				String description = "Descripcion generica";
				
				category.setDescription(description);
				
				assertEquals(description, category.getDescription());
			}
			
			@Test
			@DisplayName("setEnabled y getEnabled")
			void categorySetAndGetEnabled() {
				
				Boolean enabled = true;
				
				category.setEnabled(enabled);
				
				assertEquals(enabled, category.getEnabled());
			}
			
			@Test
			@DisplayName("Constructor")
			void categoryConstructor() {
				
				Long id = 1l;
				String name = "Nombre categoria";
				String description = "Una descripcion";
				Category theCategory = new Category(id, name, description);
				
				assertEquals(id + name + description, theCategory.getId() + theCategory.getName() + theCategory.getDescription());
				
			}
			
			
			
		}

		@Nested
		@DisplayName("Metodos clase Product")
		class ProductTest{

			
			@Test
			@DisplayName("set y get Id")
			void productSetAndGetId() {
				
				Long id = 1l;
				
				product.setId(id);
				
				assertEquals(id, product.getId());
				
			}
			
			@Test
			@DisplayName("setName y getName")
			void productSetAndGetName() {
				
				String name = "Nombre Generico";
				
				product.setName(name);
				
				assertEquals(name, product.getName());
			}
			
			@Test
			@DisplayName("setDescription y getDescription")
			void productSetAndGetDescription() {
				
				String description = "Descripcion generica";
				
				product.setDescription(description);
				
				assertEquals(description, product.getDescription());
			}
			
			@Test
			@DisplayName("setEnabled y getEnabled")
			void productSetAndGetEnabled() {
				
				Boolean enabled = true;
				
				product.setEnabled(enabled);
				
				assertEquals(enabled, product.getEnabled());
			}
			
			@Test
			@DisplayName("setPrice y getPrice")
			void productSetAndGetPrice() {
				
				Double price = 20.0;
				
				product.setPrice(price);
				
				assertEquals(price, product.getPrice());
			}
			
			@Test
			@DisplayName("setStock y getStock")
			void productSetAndGetStock() {
				
				product.setStock(stock);
				
				assertEquals(stock, product.getStock());
			}
			
			@Test
			@DisplayName("setCategory y getCategory")
			void productSetAndGetCategory() {
				
				product.setCategory(category);
				
				assertEquals(category, product.getCategory());
			}
			
			@Test
			@DisplayName("Constructor")
			void productCosntructor() {
				
				Long id = 1l;
				String name = "Nombre Generico";
				String description = "Descripcion generica";
				Boolean enabled = true;
				Double price = 20.0;
				
				Product  theProduct = new Product(id, name, description, price, category);
				
				assertEquals(id + name + description + enabled + price + category, 
								theProduct.getId() + theProduct.getName() + theProduct.getDescription() + 
								theProduct.getEnabled() + theProduct.getPrice() + theProduct.getCategory());
			}
			
			
			
		}
		
		@Nested
		@DisplayName("Metodos clase Stock")
		class StockTest{

			
			@Test
			@DisplayName("setProduct y getProduct")
			void stockSetAndGetProduct() {
				
					
				stock.setProduct(product);
				
				assertEquals(product, stock.getProduct());
			}
			
			@Test
			@DisplayName("setName y getName")
			void categorySetAndGetLocation() {
				
				String location = "Locacion Generica";
				
				stock.setLocationCode(location);
				
				assertEquals(location, stock.getLocationCode());
			}
			
			@Test
			@DisplayName("setQuantity y getQuantity")
			void categorySetAndGetQuantity() {
				
				Integer quantity = 50;
				
				stock.setQuantity(quantity);
				
				assertEquals(quantity, stock.getQuantity());
			}
			
			
			
			@Test
			@DisplayName("Constructor")
			void stockConstructor() {
				
				Integer quantity = 50;
				String location = "Locacion Generica";
				
				Stock theStock = new Stock(product, quantity, location);
				
				assertEquals(product.getName() + quantity + location, theStock.getProduct().getName() + theStock.getQuantity() + theStock.getLocationCode());
				
			}
			
			
			
		}
		
		
		
	}

	@Nested
	@DisplayName("Testeo de servicio")
	class ServicesTest{
		
		@Nested
		@DisplayName("Product Service")
		class ProductServiceTest{
			
			
			/*@Test			
			@DisplayName("Update by id Nombre")
			void updateByIdNombre() {
				
				Long id = 1l;
				
				category = new Category(id, "Comida", "para llevar a la mesa");
				
				product = new Product(id, "Fideos", "Para comer", 50.0, category);	
				
				productService.getTheProducts().add(product);
				
				String nuevoNombre = "Fideos codito";
								
				String numero = "1\n" + nuevoNombre;		

				try {
					
					setStreams(numero);					
						
					productService.updateById(id, categoryService);
					
				}finally {
					
					System.setIn(System.in);
					
				}
				
				
				
				assertEquals(nuevoNombre, productService.findOneByiD(id, true).getName());
				
				
			}


			
			@Test
			@DisplayName("Update by id Descripcion")
			void updateByIdDescripcion() {
				
				Long id = 1l;
				
				category = new Category(id, "Comida", "para llevar a la mesa");
				
				product = new Product(id, "Fideos", "Para comer", 50.0, category);	
				
				productService.getTheProducts().add(product);
				
				String nuevaDescripcion = "Para guardar";
								
				String numero = "2\n" + nuevaDescripcion;		

				try {
					setStreams(numero);
					productService.updateById(id, categoryService);
					
				} finally {
					
				}
				
				assertEquals(nuevaDescripcion, productService.findOneByiD(id, true).getDescription());
				
				
			}*/
			
			
			
		}
		
		
	}
	
	private void setStreams(String input) {
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		
	}
	

	
		
}

