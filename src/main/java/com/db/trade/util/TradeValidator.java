package com.db.trade.util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.trade.constant.ExceptionConstants;
import com.db.trade.exception.BusinessException;
import com.db.trade.model.Trade;
import com.db.trade.repository.TradeDao;
import com.db.trade.request.TradeDto;

@Component
public class TradeValidator {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeValidator.class);
	
	@Autowired
	private TradeDao tradeDao;
	
	public void doTradePreValidation(TradeDto tradeRequest) {
		Map<ExceptionConstants, String> validationErrors = new HashMap<>(0);

		this.validateMaturityDate(tradeRequest.getMaturityDate(), validationErrors, tradeRequest.getTradeId());
		this.validateTradeVersion(tradeRequest.getVersion(), validationErrors, tradeRequest.getTradeId());
		if (!validationErrors.isEmpty()) {
			logger.info("Trade Pre Validation Failed for trade {}", tradeRequest.getTradeId());
			throw new BusinessException(ExceptionConstants.ERROR_1004, new RuntimeException("Trade Validations Failed"));
		}
	}

	private void validateMaturityDate(LocalDate maturityDate, Map<ExceptionConstants, String> validationErrors, String tradeId) {
		if (maturityDate.isBefore(LocalDate.now())) {
			logger.info("Trade maturity date is less than today for trade {}", tradeId);
			validationErrors.put(ExceptionConstants.ERROR_1003, ExceptionConstants.ERROR_1003.getErrorMessage());
		}

	}

	private void validateTradeVersion(Integer requestedTradeVersion,
			Map<ExceptionConstants, String> validationErrors, String tradeId) {

		Trade existingtradeDetail = tradeDao
				.findTradeDetailsByOrderAndLimit(tradeId);
		if(existingtradeDetail!=null &&  existingtradeDetail.getTradeVersion()!=null) {
		if (requestedTradeVersion < existingtradeDetail.getTradeVersion()) {
			logger.info("Trade version is less than current for trade {}", tradeId);
			validationErrors.put(ExceptionConstants.ERROR_1002, ExceptionConstants.ERROR_1002.getErrorMessage());
		}
		}
	}


}
