package com.litmus7.employeemanager.exception;

public class EmployeeDaoException extends Exception {
	private final String errorCode;
	
	public EmployeeDaoException (String message, Throwable cause, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
	

}
