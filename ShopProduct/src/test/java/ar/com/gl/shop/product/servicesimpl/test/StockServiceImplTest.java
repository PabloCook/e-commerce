package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.gl.shop.product.exceptions.ItemNotFound;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.impl.StockRepositoryImpl;
import ar.com.gl.shop.product.service.impl.StockServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

	
	private static Stock stock;
	
	@Mock
	StockRepositoryImpl repositoryMock;
	

	@InjectMocks
	StockServiceImpl stockService;
	
	@BeforeAll
	static void setUp() throws Exception {
		
		stock = new Stock(400, "SJ");
	//	stockService.create(stock);
	}
	
	@Test
	@DisplayName("create()")
	void testCase_0() throws ItemNotFound
	{
		when(repositoryMock.getById(2L)).thenReturn(stock);
		Stock stockFind = stockService.getById(2L, true);
		assertEquals(stock, stockFind); //
	//	assertEquals(400, stockFind.getQuantity());
	//	assertEquals("SJ", stockFind.getLocationCode());
	}

	@Test
	@DisplayName("setId()")
	void testCase_1() throws ItemNotFound {
		stock.setId(5L);
		when(repositoryMock.getById(5L)).thenReturn(stock);
		assertEquals(5L,stockService.getById(5L, true).getId());
	}
	
	@Test
	@DisplayName("setQuantity()")
	void testCase_2() throws ItemNotFound {
		stock.setQuantity(78);
		when(repositoryMock.getById(5L)).thenReturn(stock);
		assertEquals(78,stockService.getById(5L, true).getQuantity());
	}
	
	@Test
	@DisplayName("setLocationCode()")
	void testCase_3() throws ItemNotFound {
		stock.setLocationCode("MDZ");
		when(repositoryMock.getById(5L)).thenReturn(stock);
		assertEquals("MDZ",stockService.getById(5L, true).getLocationCode());
	}

	@Test
	@DisplayName("softDelete() - false")
	void testCase_4() throws ItemNotFound {
		stock.setEnabled(true);
		when(repositoryMock.getById(5L)).thenReturn(stock);//
		stockService.softDelete(5L);
		assertNull(stockService.getById(5L, true));
		assertNotNull(stockService.getById(5L, false)); 
		
		//stock.setEnabled(false);
		//Mockito.when(repositoryMock.findStockById(5L)).thenReturn(stock);//
		assertFalse(stockService.getById(5L, false).getEnabled());
	}
	
	
	@Test
	@DisplayName("findById() - null")
	void testCase_5() throws ItemNotFound {
		when(repositoryMock.getById(6L)).thenReturn(null);
		assertNull(stockService.getById(6L, true));
		assertNull(stockService.getById(6L, false));
	}
	
	
	@Test
	@DisplayName("softDelete() - true")
	void testCase_6() throws ItemNotFound {
		when(repositoryMock.getById(5L)).thenReturn(stock);//
		stockService.softDelete(5L);
		assertNotNull(stockService.getById(5L, true));
		assertTrue(stockService.getById(5L, false).getEnabled());
	}
	
	
	@Test
	@DisplayName("toString()")
	void testCase_7() throws ItemNotFound {
		stock.setLocationCode("SJ");
		stock.setQuantity(10);
		when(repositoryMock.getById(5L)).thenReturn(stock);//
		assertEquals("Stock [id=5, quantity=10, locationCode=SJ]",stockService.getById(5L, true).toString());
	}
	
	@Test
	@DisplayName("update()")
	void testCase_8() throws ItemNotFound {
		stock.setLocationCode("mza");
		when(repositoryMock.update(stock)).thenReturn(stock);
		Stock stockNew = stockService.update(stock);
		
		assertEquals(stock, stockNew);
		
	}
	
	@Test
	@DisplayName("delete()")
	void testCase_9() throws ItemNotFound {
		stockService.delete(5L);
		when(repositoryMock.getById(5L)).thenReturn(null);//
		assertNull(stockService.getById(5L, true));
		assertNull(stockService.getById(5L, false));
	}
	
	
	
}
