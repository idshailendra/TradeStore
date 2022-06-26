package com.db.trade.exception;

import com.db.trade.constant.ExceptionConstants;

public class BusinessException extends TradeException{
	
	
	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
	}

	public BusinessException(ExceptionConstants errorCode, Exception exception) {
		super(errorCode, exception);
	}
	
	
}
