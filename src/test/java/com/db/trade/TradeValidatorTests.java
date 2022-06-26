package com.db.trade;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.db.trade.constant.ApplicationConstant;
import com.db.trade.constant.ExceptionConstants;
import com.db.trade.exception.BusinessException;
import com.db.trade.model.Trade;
import com.db.trade.repository.TradeDao;
import com.db.trade.request.TradeDto;
import com.db.trade.util.TradeValidator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	public class TradeValidatorTests extends TradeApplicationTests{

		@InjectMocks
		private TradeValidator tradeValidator;
		
		@Mock
		private TradeDao tradeDao;
		
		@BeforeAll
		public  void setup() {
			MockitoAnnotations.initMocks(tradeValidator); 
		}
		
		@Test
		public void testDoTradePreValidationInvalidMaturityDate() {
			Trade  trade = null;
			TradeDto tradeRequest = new TradeDto("T1", 1, "CP-1", "B1", LocalDate.now().minusDays(2L));
			
			try {
				Mockito.when(tradeDao.findTradeDetailsByOrderAndLimit(tradeRequest.getTradeId())).thenReturn(trade);
				tradeValidator.doTradePreValidation(tradeRequest);
			}catch(BusinessException be ) {
				Assert.assertNotNull(be);
				Assert.assertEquals(ExceptionConstants.ERROR_1004, be.getErrorCode());
			}
		}
		
		@Test
		public void testDoTradePreValidationFutureMaturityDate() {
			Trade  trade = null;
			TradeDto tradeRequest = new TradeDto("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1L));
			Exception ex = null;
			try {
				Mockito.when(tradeDao.findTradeDetailsByOrderAndLimit(tradeRequest.getTradeId())).thenReturn(trade);
				tradeValidator.doTradePreValidation(tradeRequest);
			}catch(Exception tve ) {
				ex = tve;
			}
			Assert.assertNull(ex);
		}
		
		@Test
		public void testDoTradePreValidationTodayMaturityDate() {
			Trade  trade = null;
			TradeDto tradeRequest = new TradeDto("T1", 1, "CP-1", "B1", LocalDate.now());
			Exception ex = null;
			try {
				Mockito.when(tradeDao.findTradeDetailsByOrderAndLimit(tradeRequest.getTradeId())).thenReturn(trade);
				tradeValidator.doTradePreValidation(tradeRequest);
			}catch(Exception tve ) {
				ex = tve;
			}
			Assert.assertNull(ex);
		}
		
		@Test
		public void testvalidateTradeVersionWithSameVersion() {
			Trade  trade = new Trade("T1", 1l,"CP-1", "B1",LocalDate.now().plusDays(1L),ApplicationConstant.TRADE_DETAILS_EXPIRED_N);
			TradeDto tradeRequest = new TradeDto("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1L));
			Exception ex = null;
			try {
				Mockito.when(tradeDao.findTradeDetailsByOrderAndLimit(tradeRequest.getTradeId())).thenReturn(trade);
				tradeValidator.doTradePreValidation(tradeRequest);
			}catch(Exception tve ) {
				ex = tve;
			}
			Assert.assertNull(ex);
		}
		
		
		@Test
		public void testvalidateTradeVersionWithFewerVersion() {
			Trade  trade = new Trade("T1", 2l,"CP-1", "B1",LocalDate.now().plusDays(1L),ApplicationConstant.TRADE_DETAILS_EXPIRED_N);
			TradeDto tradeRequest = new TradeDto("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1L));
			Exception ex = null;
			try {
				Mockito.when(tradeDao.findTradeDetailsByOrderAndLimit(tradeRequest.getTradeId())).thenReturn(trade);
				tradeValidator.doTradePreValidation(tradeRequest);
			}catch(BusinessException be ) {
				Assert.assertNotNull(be);
				Assert.assertEquals(ExceptionConstants.ERROR_1004, be.getErrorCode());
			}
			
		}
}
