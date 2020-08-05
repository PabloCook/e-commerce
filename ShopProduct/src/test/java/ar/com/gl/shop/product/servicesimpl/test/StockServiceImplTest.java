package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import ar.com.gl.shop.product.model.Product;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.servicesimpl.StockServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class StockServiceImplTest {

	private static StockServiceImpl stockService;
	private static Stock stock;
	private static Product product;
	
	@BeforeAll
	static void setUp() throws Exception {
		stockService = new StockServiceImpl();
		stock = new Stock(400, "SJ");
		stockService.create(stock);
	}
	
	@Test
	void testCase_0()
	{
		assertEquals(400,stock.getQuantity());
		assertEquals("SJ",stock.getLocationCode());
	}

	@Test
	void testCase_1() {
		stockService.findById(2L, true).setId(5L);
		assertEquals(5L,stockService.findById(5L, true).getId());
	}

	@Test
	void testCase_2() {
		stockService.softDelete(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, true));
		assertNotNull(stockService.findById(5L, false));
	}
	@Test
	void testCase_3() {
		stockService.softDelete(stockService.findById(5L, false));
		assertNotNull(stockService.findById(5L, true));
	}
	
	@Test
	void testCase_4() {
		stockService.findById(5L, true).setQuantity(78);
		assertEquals(78,stockService.findById(5L, true).getQuantity());
	}
	
	@Test
	void testCase_5() {
		stockService.findById(5L, true).setLocationCode("MDZ");
		assertEquals("MDZ",stockService.findById(5L, true).getLocationCode());
	}
	
	@Test
	void testCase_6() {
		assertEquals("Stock [id=5, quantity=78, locationCode=MDZ]",stockService.findById(5L, true).toString());
	}
	
	@Test
	void testCase_7() {
		stockService.delete(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, true));
		assertNull(stockService.findById(5L, false));
	}
	
}
