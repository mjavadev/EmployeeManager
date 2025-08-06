package com.litmus7.employeemanager.util;
import com.litmus7.employeemanager.dto.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


public class ValidationUtil {
		
	public static boolean isInteger(String data) {
		try {
			Integer.parseInt(data);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isUnique(String data, List<Employee> e) {
		for (Employee i : e) {
			if (i.getId() == Integer.parseInt(data)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isPositive(String data) {
		if (Integer.parseInt(data)>0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String data) {
		if (data == null || data.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidNumber(String data) {
		if(data.matches("\\d{10}")) {
			return true;
		}
		return false;
	}

	public static boolean isValidEmail(String data) {
		if(data.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidDate(String date) {
		try {
			LocalDate.parse(date);
			return true;
		}
		catch (DateTimeParseException e) {
			return false;
		}
	}
	
	public static boolean isFutureDate(String date) {
		if(!isValidDate(date)) return false;
		LocalDate d = LocalDate.parse(date);
		return d.isAfter(LocalDate.now());
	}
	
	public static boolean isValidStatus(String status) {
		if (status.equalsIgnoreCase("true")) {
			return true;
		}
		if (status.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}
	
}