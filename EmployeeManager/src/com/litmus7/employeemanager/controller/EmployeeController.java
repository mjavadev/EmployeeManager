package com.litmus7.employeemanager.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.constant.MessageConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.exception.EmployeeNotFoundException;
import com.litmus7.employeemanager.exception.EmployeeServiceException;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.TextFileUtil;
import com.litmus7.employeemanager.util.ValidationUtil;

public class EmployeeController {

	private EmployeeService employeeService = new EmployeeService();
	
	public Response<List<Employee>, Boolean, String> getDataFromTextFile(String inputFilePath) {
		
		if (inputFilePath == null || inputFilePath.isEmpty()) 
			return new Response<>(null, false, MessageConstants.MISSING_INPUT_TEXT_FILE);
		
		List<Employee> employees = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				List<String> fields = TextFileUtil.parseDelimitedLine(line);
				Employee emp = createEmployeeFromData(fields);
				employees.add(emp);
			}
			return new Response<>(employees, true, MessageConstants.DATA_LOADED_SUCCESSFULLY);
		} catch (FileNotFoundException e) {
				return new Response<>(null, false, MessageConstants.FILE_NOT_FOUND + inputFilePath);
		} catch (IOException e) {
				return new Response<>(null, false, MessageConstants.ERROR_READING_FILE + inputFilePath);
		} catch (Exception e) {
				return new Response<>(null, false, MessageConstants.UNEXPECTED_TEXT_FILE_PROCESSING_ERROR);
		}
	}

	public Response<Void, Boolean, String> writeDataToCSV(List<Employee> employees,String csvFilePath) {
		if (employees.isEmpty()) return new Response<>(null, false, MessageConstants.NO_EMPLOYEE_INFORMATION_FOUND);
		if (csvFilePath == null || csvFilePath.isEmpty()) return new Response<>(null, false,MessageConstants.MISSING_OUTPUT_CSV_FILE + csvFilePath);
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(csvFilePath)))) {
			writer.println("ID,FirstName,LastName,MobileNumber,EmailAddress,JoiningDate(YYYY-MM-DD),ActiveStatus(true/false)");
			for (Employee e : employees) {
				writer.println(
					e.getId() + "," +
					TextFileUtil.escapeCSV(e.getFirstName()) + "," + 
					TextFileUtil.escapeCSV(e.getLastName()) + "," + 
					e.getMobileNumber() + "," + 
					TextFileUtil.escapeCSV(e.getEmail()) + "," + 
					e.getDate() + "," + e.getActiveStatus()
					);
			}
			return new Response<>(null, true, MessageConstants.DATA_WRITTEN_TO_CSV_SUCCESSFULLY);
		} catch (IOException e) {
			return new Response<>(null, false, MessageConstants.FAILED_TO_WRITE_TO_CSV);
		}
	}
	
	public Response<Employee, Boolean, String> appendDataToCSV(List<String> dataFromUser,List<Employee> emp,String csvFilePath){
		Response<Employee, Boolean, String> validationResponse  = validateEmployeeData(null,dataFromUser, emp);
		if (!validationResponse .getApplicationStatus()) {
			return validationResponse ;
		}
		
		Employee employee = createEmployeeFromData(dataFromUser);
	
		return appendEmployeeToCSV(employee, csvFilePath);
	}
	
	public Response<Void, Boolean, String> createEmployee(Employee employee) {
		if (employee == null) return new Response<>(null,false, MessageConstants.EMPLOYEE_DETAILS_REQUIRED);
		try {
			employeeService.createEmployee(employee);
		} catch (EmployeeServiceException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(null,true, MessageConstants.EMPLOYEE_INSERTED_SUCCESFULLY);
	}
	
	public Response<List<Employee>, Boolean, String>  getAllEmployees() {
		List<Employee> employees; //= new ArrayList<>();
		try {
			employees = employeeService.getAllEmployees();
		} catch (EmployeeServiceException | EmployeeNotFoundException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(employees,true,MessageConstants.EMPLOYEE_LIST_RETRIEVED_SUCCESSFULLY);
	}
	
	public Response<Employee, Boolean, String> getEmployeeById(int employeeId) {
		if (employeeId <= 0) return new Response<>(null,false, MessageConstants.INVALID_EMPLOYEE_ID);
		Employee employee = null;
		try {
			employee = employeeService.getEmployeeById(employeeId);
		} catch (EmployeeServiceException | EmployeeNotFoundException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(employee,true,MessageConstants.EMPLOYEE_RETRIEVED_SUCCESSFULLY);
	}
	
	public Response<Void, Boolean, String> deleteEmployeebyId(int employeeId) {
		if (employeeId <= 0) return new Response<>(null,false, MessageConstants.INVALID_EMPLOYEE_ID);
		try {
			employeeService.deleteEmployeebyId(employeeId);
		} catch (EmployeeServiceException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(null,true,MessageConstants.EMPLOYEE_DELETED_SUCCESSFULLY);
	}

	public Response<Void, Boolean, String> updateEmployee(Employee employee) {
		if (employee == null) return new Response<>(null,false,MessageConstants.EMPLOYEE_DETAILS_REQUIRED);
		try {
			employeeService.updateEmployee(employee);
		} catch (EmployeeServiceException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(null,true, MessageConstants.EMPLOYEE_UPDATED_SUCCESSFULLY);
	}
	
	//private helper methods
	private static Response<Employee, Boolean, String> validateEmployeeData(Employee e,List<String> dataFromUser, List<Employee> emp){
		if (!ValidationUtil.isInteger(dataFromUser.get(0))) {
			return new Response<>(null, false, MessageConstants.INVALID_ID_FORMAT);
		}
		if (!ValidationUtil.isUnique(dataFromUser.get(0), emp)) {
			return new Response<>(null, false, MessageConstants.ID_MUST_BE_UNIQUE);
		}
		if (!ValidationUtil.isPositive(dataFromUser.get(0))) {
			return new Response<>(null, false, MessageConstants.ID_MUST_BE_POSITIVE_INTEGER);
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(1))) {
			return new Response<>(null, false,MessageConstants.FIRST_NAME_CANNOT_BE_EMPTY);
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(2))) {
			return new Response<>(null, false, MessageConstants.LAST_NAME_CANNOT_BE_EMPTY);
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(3))) {
			return new Response<>(null, false, MessageConstants.MOBILE_NUMBER_CANNOT_BE_EMPTY);
		}
		if (!ValidationUtil.isValidNumber(dataFromUser.get(3))) {
			return new Response<>(null, false, MessageConstants.INVALID_MOBILE_NUMBER_FORMAT);
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(4))) {
			return new Response<>(null, false, MessageConstants.EMAIL_ADDRESS_CANNOT_BE_EMPTY);
		}
		if (!ValidationUtil.isValidEmail(dataFromUser.get(4))) {
			return new Response<>(null, false, MessageConstants.INVALID_EMAIL_ADDRESS_FORMAT);
		}
		if (!ValidationUtil.isValidDate(dataFromUser.get(5))) {
			return new Response<>(null, false, MessageConstants.INVALID_JOINING_DATE_FORMAT);
		}
		if (ValidationUtil.isFutureDate(dataFromUser.get(5))) {
			return new Response<>(null, false, MessageConstants.JOINING_DATE_CANNOT_BE_FUTURE);
		}
		if (!ValidationUtil.isValidStatus(dataFromUser.get(6))) {
			return new Response<>(null, false, MessageConstants.INVALID_ACTIVE_STATUS);
		}
		return new Response<>(null, true, MessageConstants.VALIDATION_PASSED);
	}
	
	private static Employee createEmployeeFromData(List<String> dataFromUser) {
		return new Employee(
				Integer.parseInt(dataFromUser.get(0)),
				dataFromUser.get(1),
				dataFromUser.get(2),
				dataFromUser.get(3),
				dataFromUser.get(4),
				LocalDate.parse(dataFromUser.get(5)),
				Boolean.parseBoolean(dataFromUser.get(6))
				);
	}
	
	private static Response<Employee, Boolean, String> appendEmployeeToCSV(Employee employee,String csvFilePath){
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(csvFilePath,true)))) {
			//	String.join(",", String.valueOf(e.getId()), escapeCSV(e.getFirstName()), escapeCSV(e.getLastName()), escapeCSV(e.getMobileNo()), String.vaescapeCSV(e.getmail()),e.getDate(),String.valueOf(e.getActiveStatus()) );
				writer.println(employee.getId() + "," +
						TextFileUtil.escapeCSV(employee.getFirstName()) + "," +
						TextFileUtil.escapeCSV(employee.getLastName()) + "," +
						employee.getMobileNumber() + "," +
						TextFileUtil.escapeCSV(employee.getEmail()) + "," +
						employee.getDate() + "," +
						employee.getActiveStatus());
				return new Response<>(employee, true, MessageConstants.EMPLOYEE_ADDED_TO_CSV_SUCCESSFULLY);
		}catch (IOException ee) {
				return new Response<>(null, false, MessageConstants.FAILED_TO_ADD_EMPLOYEE_TO_CSV);
		}
	}

}