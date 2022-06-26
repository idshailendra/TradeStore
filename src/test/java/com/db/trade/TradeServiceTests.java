package com.db.trade;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.db.trade.model.Trade;
import com.db.trade.repository.TradeDao;
import com.db.trade.request.TradeDto;
import com.db.trade.service.TradeServiceImpl;
import com.db.trade.util.TradeValidator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeServiceTests extends TradeApplicationTests {
	
	@InjectMocks
	private TradeServiceImpl tradeServiceImpl;
	
	@Mock
	private TradeDao tradeDao;

	@Mock
	private TradeValidator tradeValidator;
	
	
	@BeforeAll
	public  void setup() {
		MockitoAnnotations.initMocks(tradeServiceImpl); 
	}
	
	private Exception actualException=null;
	

	
	@Test
	public void testDoTrade() {
		Trade  trade = null;
		TradeDto tradeDto = new TradeDto("T1", 1, "CP-1", "B1", LocalDate.now());
		
		Mockito.doNothing().when(tradeValidator).doTradePreValidation(tradeDto);
		
		Mockito.when(tradeDao.save(trade)).thenReturn(trade);
		try {
		tradeServiceImpl.execute(tradeDto);;
		}catch(Exception e) {
			actualException=e;
		}
		
		Assert.assertEquals(null, actualException);
	}
	
	
}
