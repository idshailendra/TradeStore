package com.db.trade.exception;

import com.db.trade.constant.ExceptionConstants;

public class TradeException extends RuntimeException{
	
private static final long serialVersionUID = 1L;
	
	protected String errorId;
	protected String errorMessage;
	protected String httpStatus;
	protected ExceptionConstants errorCode;
	protected Exception exception;
	
	public TradeException() {
		super();
	}

	public TradeException(ExceptionConstants errorCode, Exception exception) {
		super();
		this.errorCode = errorCode;
		this.errorId = errorCode.getErrorId();
		this.httpStatus = errorCode.getHttpStatus();
		this.errorMessage = errorCode.getErrorMessage();
		this.exception = exception;
	}
	
	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ExceptionConstants getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ExceptionConstants errorCode) {
		this.errorCode = errorCode;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}


}
