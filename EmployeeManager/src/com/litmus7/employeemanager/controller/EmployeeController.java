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

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.exception.AppException;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.TextFileUtil;
import com.litmus7.employeemanager.util.ValidationUtil;

public class EmployeeController {

	public Response<List<Employee>, Boolean, String> getDataFromTextFile(String inputFilePath) {
		
		if (inputFilePath == null || inputFilePath.isEmpty()) 
			return new Response<>(null, false, "Please provide a valid path to the input file.");
		
		List<Employee> employees = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				List<String> fields = TextFileUtil.parseDelimitedLine(line);
				Employee emp = createEmployeeFromData(fields);
				employees.add(emp);
			}
			return new Response<>(employees, true, "Data loaded successfully.");
		} catch (FileNotFoundException e) {
				return new Response<>(null, false, "File not found: " + inputFilePath);
		} catch (IOException e) {
				return new Response<>(null, false,"Error reading the file: "+inputFilePath);
		} catch (Exception e) {
				return new Response<>(null, false,"Unexpected error while processing file.");
		}
	}

	public Response<Void, Boolean, String> writeDataToCSV(List<Employee> employees,String csvFilePath) {
		if (employees.isEmpty()) return new Response<>(null, false,"No employee information could be found.");
		if (csvFilePath == null || csvFilePath.isEmpty()) return new Response<>(null, false,"Please provide a valid path to the output file: "+ csvFilePath);
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
			return new Response<>(null, true, "Data written to CSV succesfully.");
		} catch (IOException e) {
			return new Response<>(null, false,"Failed to write to CSV");
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
		if (employee == null) return new Response<>(null,false,"Enter valid employee details");
		try {
			new EmployeeService().createEmployee(employee);
		} catch (AppException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(null,true,"Employee inserted successfully.");
	}
	
	public Response<List<Employee>, Boolean, String>  getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		try {
			employees = new EmployeeService().getAllEmployees();
		} catch (AppException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(employees,true,"Employee list retrieved successfully.");
	}
	
	public Response<Employee, Boolean, String> getEmployeeById(int employeeId) {
		if (employeeId == 0) return new Response<>(null,false,"Invalid employee id");
		Employee employee = null;
		try {
			employee = new EmployeeService().getEmployeeById(employeeId);
		} catch (AppException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(employee,true,"Employee retrieved successfully.");
	}
	
	public Response<Void, Boolean, String> deleteEmployeebyId(int employeeId) {
		if (employeeId == 0) return new Response<>(null,false,"Invalid employee id");
		try {
			new EmployeeService().deleteEmployeebyId(employeeId);
		} catch (AppException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(null,true,"Employee deleted successfully.");
	}

	public Response<Void, Boolean, String> updateEmployee(Employee employee) {
		if (employee == null) return new Response<>(null,false,"Enter valid employee details");
		try {
			new EmployeeService().updateEmployee(employee);
		} catch (AppException e) {
			return new Response<>(null,false,e.getMessage());
		}
		return new Response<>(null,true,"Employee updated successfully.");
	}
	
	//private helper methods
	private static Response<Employee, Boolean, String> validateEmployeeData(Employee e,List<String> dataFromUser, List<Employee> emp){
		if (!ValidationUtil.isInteger(dataFromUser.get(0))) {
			return new Response<>(null, false, "ID must be a valid integer.");
		}
		if (!ValidationUtil.isUnique(dataFromUser.get(0), emp)) {
			return new Response<>(null, false, "ID must be unique.");
		}
		if (!ValidationUtil.isPositive(dataFromUser.get(0))) {
			return new Response<>(null, false, "ID must be a positive integer.");
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(1))) {
			return new Response<>(null, false, "First name cannot be empty.");
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(2))) {
			return new Response<>(null, false, "Last name cannot be empty.");
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(3))) {
			return new Response<>(null, false, "Mobile number cannot be empty.");
		}
		if (!ValidationUtil.isValidNumber(dataFromUser.get(3))) {
			return new Response<>(null, false, "Mobile number must be a valid numerical format.");
		}
		if (ValidationUtil.isEmpty(dataFromUser.get(4))) {
			return new Response<>(null, false, "Email address cannot be empty.");
		}
		if (!ValidationUtil.isValidEmail(dataFromUser.get(4))) {
			return new Response<>(null, false, "Email address must follow a basic email format.");
		}
		if (!ValidationUtil.isValidDate(dataFromUser.get(5))) {
			return new Response<>(null, false, "Joining date must be a valid date in YYYY-MM-DD format.");
		}
		if (ValidationUtil.isFutureDate(dataFromUser.get(5))) {
			return new Response<>(null, false, "Joining date cannot be a future date.");
		}
		if (!ValidationUtil.isValidStatus(dataFromUser.get(6))) {
			return new Response<>(null, false, "Active status must be true or false");
		}
		return new Response<>(null, true, "Validation passed");
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
				return new Response<>(employee, true, "Employee added to CSV successfully.");
		}catch (IOException ee) {
				return new Response<>(null, false, "Failed to add employee to CSV");
		}
	}

}