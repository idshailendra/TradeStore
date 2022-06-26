package com.db.trade.exception;

import com.db.trade.constant.ExceptionConstants;

public class SystemException extends TradeException{
	
	private static final long serialVersionUID = 1L;
	
	
	public SystemException() {
		super();
	}

	public SystemException(ExceptionConstants errorCode, Exception exception) {
		super(errorCode, exception);
	}
}
