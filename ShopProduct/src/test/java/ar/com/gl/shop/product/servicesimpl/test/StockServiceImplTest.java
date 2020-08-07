package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repositoryimpl.RepositoryImpl;
import ar.com.gl.shop.product.servicesimpl.StockServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

	
	private static Stock stock;
	
	@Mock
	RepositoryImpl repositoryMock;
	

	@InjectMocks
	StockServiceImpl stockService;
	
	@BeforeAll
	static void setUp() throws Exception {
		stock = new Stock(400, "SJ");
	//	stockService.create(stock);
	}
	
	@Test
	@DisplayName("create()")
	void testCase_0()
	{
		Mockito.when(repositoryMock.findStockById(2L)).thenReturn(stock);
		Stock stockFind = stockService.findById(2L, true);
		assertEquals(stock, stockFind); //
	//	assertEquals(400, stockFind.getQuantity());
	//	assertEquals("SJ", stockFind.getLocationCode());
	}

	@Test
	@DisplayName("setId()")
	void testCase_1() {
		stock.setId(5L);
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);
		assertEquals(5L,stockService.findById(5L, true).getId());
	}
	
	@Test
	@DisplayName("setQuantity()")
	void testCase_2() {
		stock.setQuantity(78);
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);
		assertEquals(78,stockService.findById(5L, true).getQuantity());
	}
	
	@Test
	@DisplayName("setLocationCode()")
	void testCase_3() {
		stock.setLocationCode("MDZ");
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);
		assertEquals("MDZ",stockService.findById(5L, true).getLocationCode());
	}

	@Test
	@DisplayName("softDelete() - false")
	void testCase_4() {
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);//
		stockService.softDelete(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, true));
		assertNotNull(stockService.findById(5L, false)); 
		
		//stock.setEnabled(false);
		//Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);//
		assertFalse(stockService.findById(5L, false).getEnabled());
	}
	
	
	@Test
	@DisplayName("findById() - null")
	void testCase_5() {
		Mockito.when(repositoryMock.findStockById(6L)).thenReturn(null);//
		assertNull(stockService.findById(6L, true));
		assertNull(stockService.findById(6L, false));
	}
	
	
	@Test
	@DisplayName("softDelete() - true")
	void testCase_6() {
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);//
		stockService.softDelete(stockService.findById(5L, false));
		assertNotNull(stockService.findById(5L, true));
		assertTrue(stockService.findById(5L, false).getEnabled());
	}
	
	
	@Test
	@DisplayName("toString()")
	void testCase_7() {
		stock.setLocationCode("SJ");
		stock.setQuantity(10);
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);//
		assertEquals("Stock [id=5, quantity=10, locationCode=SJ]",stockService.findById(5L, true).toString());
	}
	
	@Test
	@DisplayName("update()")
	void testCase_8() {
		stock.setLocationCode("mza");
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);//
		Stock stockNew = stockService.update(stock);
		assertEquals(stock, stockNew);
		
	}
	
	@Test
	@DisplayName("delete()")
	void testCase_9() {
		stockService.delete(stockService.findById(5L, true));
		Mockito.when(repositoryMock.findStockById(5L)).thenReturn(null);//
		assertNull(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, false));
	}
	
	
	
}
