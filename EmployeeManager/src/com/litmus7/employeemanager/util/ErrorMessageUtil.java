package com.litmus7.employeemanager.util;

import java.util.HashMap;
import java.util.Map;

import com.litmus7.employeemanager.constant.MessageConstants;

public class ErrorMessageUtil {

	private static final Map<String, String> ERROR_MESSAGES = new HashMap<>();
	
	static {
		
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_DB_SAVE_EMPLOYEE, MessageConstants.ERROR_UI_EMP_DB_001);
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_DB_FETCH_EMPLOYEE, MessageConstants.ERROR_UI_EMP_DB_002);
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_DB_FETCH_ALL_EMPLOYEES, MessageConstants.ERROR_UI_EMP_DB_003);
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_DB_DELETE_EMPLOYEE, MessageConstants.ERROR_UI_EMP_DB_004);
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_DB_UPDATE_EMPLOYEE, MessageConstants.ERROR_UI_EMP_DB_005);
		
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_SERVICE_CREATE_EMPLOYEE_NO_ROWS, MessageConstants.ERROR_UI_EMP_SRV_001);
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_NO_EMPLOYEES_FOUND, MessageConstants.ERROR_UI_EMP_SRV_002);
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_EMPLOYEE_NOT_FOUND_BY_ID, MessageConstants.ERROR_UI_EMP_SRV_003);
		ERROR_MESSAGES.put(MessageConstants.ERROR_CODE_NO_ROWS_AFFECTED, MessageConstants.ERROR_UI_EMP_SRV_004);
		
	}
	
	public static String getMessage(String errorCode) {
		return ERROR_MESSAGES.getOrDefault(errorCode, MessageConstants.ERROR_MESSAGE);
	}
	
}
