package com.db.trade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.db.trade.constant.ApplicationConstant;
import com.db.trade.job.TradeJobScheduler;
import com.db.trade.model.Trade;
import com.db.trade.repository.TradeDao;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeJobSchedulerTest extends TradeApplicationTests{

	@InjectMocks
	private TradeJobScheduler tradeJobScheduler;
	
	@Mock
	private TradeDao tradeDao;
	
	@SuppressWarnings("deprecation")
	@BeforeAll
	public  void setup() {
		MockitoAnnotations.initMocks(tradeJobScheduler); 
	}
	
	@Test
	public void testExpireSchedulesWhenAvailable() {
		Exception expected = null;
		List<Trade> tradeDetails = new ArrayList<>();
		tradeDetails.add(new Trade("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.now(), "N"));
		tradeDetails.add(new Trade("T2", 1, "CP-2", "B2", LocalDate.now(), LocalDate.now(), "N"));
		
		Mockito.when(tradeDao
				.findByExpiredAndMaturityDateLessThan(ApplicationConstant.TRADE_DETAILS_EXPIRED_N, LocalDate.now())).thenReturn(tradeDetails);
		
		Mockito.when(tradeDao.saveAll(tradeDetails)).thenReturn(tradeDetails);
		
		try {
			tradeJobScheduler.expireMaturedTradesSchedules();
		}catch(Exception exp) {
			expected = exp;
		}
		Assert.assertNull(expected);
	}
	
	@Test
	public void testExpireSchedulesWhenNotAvailable() {
		Exception expected = null;
		List<Trade> tradeDetails = null;
		
		Mockito.when(tradeDao
				.findByExpiredAndMaturityDateLessThan(ApplicationConstant.TRADE_DETAILS_EXPIRED_N, LocalDate.now())).thenReturn(tradeDetails);
		
		
		try {
			tradeJobScheduler.expireMaturedTradesSchedules();
		}catch(Exception exp) {
			expected = exp;
		}
		Assert.assertNull(expected);
	}
	
}
