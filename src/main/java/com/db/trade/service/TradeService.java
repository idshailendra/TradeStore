package com.db.trade.service;

import com.db.trade.request.TradeDto;

public interface TradeService {
	
	void execute(TradeDto tradeRequest);
	
}
