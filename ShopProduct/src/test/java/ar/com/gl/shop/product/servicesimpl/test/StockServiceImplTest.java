package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.servicesimpl.StockServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class StockServiceImplTest {

	private static StockServiceImpl stockService;
	private static Stock stock;
	
	@BeforeAll
	static void setUp() throws Exception {
		stockService = new StockServiceImpl();
		stock = new Stock(400, "SJ");
		stockService.create(stock);
	}
	
	@Test
	@DisplayName("create()")
	void testCase_0()
	{
		assertEquals(400,stock.getQuantity());
		assertEquals("SJ",stock.getLocationCode());
	}

	@Test
	@DisplayName("setId()")
	void testCase_1() {
		stockService.findById(2L, true).setId(5L);
		assertEquals(5L,stockService.findById(5L, true).getId());
	}
	
	@Test
	@DisplayName("setQuantity()")
	void testCase_2() {
		stockService.findById(5L, true).setQuantity(78);
		assertEquals(78,stockService.findById(5L, true).getQuantity());
	}
	
	@Test
	@DisplayName("setLocationCode()")
	void testCase_3() {
		stockService.findById(5L, true).setLocationCode("MDZ");
		assertEquals("MDZ",stockService.findById(5L, true).getLocationCode());
	}

	@Test
	@DisplayName("softDelete() - false")
	void testCase_4() {
		stockService.softDelete(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, true));
		assertNotNull(stockService.findById(5L, false));
	}
	
	
	@Test
	@DisplayName("findById() - null")
	void testCase_5() {
		assertNull(stockService.findById(6L, true));
	}
	
	
	@Test
	@DisplayName("softDelete() - true")
	void testCase_6() {
		stockService.softDelete(stockService.findById(5L, false));
		assertNotNull(stockService.findById(5L, true));
	}
	
	
	@Test
	@DisplayName("toString()")
	void testCase_7() {
		assertEquals("Stock [id=5, quantity=78, locationCode=MDZ]",stockService.findById(5L, true).toString());
	}
	
	@Test
	@DisplayName("update()")
	void testCase_8() {
		Stock stockOld = stockService.findById(5L, true);
		System.err.println(stockService.findById(5L, true));
		stockOld.setLocationCode("mza");
		Stock stockNew = stockService.update(stockOld);
		assertEquals(stockOld, stockNew);
		
	}
	
	@Test
	@DisplayName("delete()")
	void testCase_9() {
		stockService.delete(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, false));
	}
	
	
	
}
