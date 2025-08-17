package com.litmus7.employeemanager.exception;

public class EmployeeNotFoundException extends Exception {
	private final String errorCode;
	
	public EmployeeNotFoundException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
}
