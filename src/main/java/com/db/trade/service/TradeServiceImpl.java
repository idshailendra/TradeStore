package com.db.trade.service;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.trade.constant.ApplicationConstant;
import com.db.trade.model.Trade;
import com.db.trade.repository.TradeDao;
import com.db.trade.request.TradeDto;
import com.db.trade.util.TradeValidator;

@Service("tradeServiceImpl")
public class TradeServiceImpl implements TradeService{
	
	
	@Autowired
	private TradeDao tradeDao;

	@Autowired
	private TradeValidator validateTrade;

	@Override
	public void execute(TradeDto tradeRequest) {
		Trade trade = new Trade();
		validateTrade.doTradePreValidation(tradeRequest);
		BeanUtils.copyProperties(tradeRequest, trade);
		trade.setCreatedDate(LocalDate.now());
		trade.setTradeExpired(ApplicationConstant.TRADE_DETAILS_EXPIRED_N);
		trade = tradeDao.save(trade);
	}

}
