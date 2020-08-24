package ar.com.gl.shop.product.servicesimpl.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ar.com.gl.shop.product.model.Stock;
import ar.com.gl.shop.product.repository.StockRepository;
import ar.com.gl.shop.product.service.impl.StockServiceImpl;


@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

	@InjectMocks
	StockServiceImpl stockService;
	
	@Mock
	StockRepository stockRepositoryMock;
	
	Stock stock1,stock2,stock3;
	Optional<Stock> oStock1,oStock3,oStockNull;
	List<Stock> stockList = new ArrayList<Stock>();
	List<Stock> stockListEnabled = new ArrayList<Stock>();
	
	@BeforeEach
	public void setUp() {
		stock1 = new Stock(100,"Quilmes");
		stock2 = new Stock(200,"Croacia");
		stock3 = new Stock(300,"La Plata");
		stock1.setId(1L);
		stock2.setId(2L);
		stock3.setId(3L);
		stock3.setEnabled(false);
		oStock1 = Optional.of(stock1);
		oStock3 = Optional.of(stock3);
		oStockNull = Optional.empty();
		stockList.add(stock1);
		stockList.add(stock2);
		stockList.add(stock3);
		stockListEnabled.add(stock1);
		stockListEnabled.add(stock2);
	}
	
	@Test
	@DisplayName("Create_Successful")
	void testCase_0() {
		when(stockRepositoryMock.save(stock1)).thenReturn(stock1);
		assertTrue(stockService.create(stock1).equals(stock1));
	}

	@Test
	@DisplayName("Get_By_Id")
	void testCase_1(){
		when(stockRepositoryMock.findById(stock1.getId())).thenReturn(oStock1);
		assertTrue(stockService.getById(stock1.getId(),true).equals(stock1));
	}
	
	@Test
	@DisplayName("update_successful")
	void testCase_2(){
		stock1.setLocationCode("MOD");
		stock1.setQuantity(9999);
		when(stockRepositoryMock.save(stock1)).thenReturn(stock1);
		assertTrue(stockService.update(stock1).equals(stock1));
	}
	
	@Test
	@DisplayName("softDelete_successful")
	void testCase_3(){
		when(stockRepositoryMock.findById(stock1.getId())).thenReturn(oStock1);		
		when(stockRepositoryMock.save(stock1)).thenReturn(stock1);
		assertFalse(stockService.softDelete(stock1.getId()).getEnabled());
	}
	
	@Test
	@DisplayName("restore_successful")
	void testCase_3_5(){
		when(stockRepositoryMock.findById(stock3.getId())).thenReturn(oStock3);		
		when(stockRepositoryMock.save(stock3)).thenReturn(stock3);
		assertTrue(stockService.softDelete(stock3.getId()).getEnabled());
	}

	@Test
	@DisplayName("delete_successful")
	void testCase_4(){
		when(stockRepositoryMock.findById(stock1.getId())).thenReturn(oStock1);
		stockService.delete(stock1.getId());
		
		verify(stockRepositoryMock).delete(stock1);
		
		}	
		
	
}
