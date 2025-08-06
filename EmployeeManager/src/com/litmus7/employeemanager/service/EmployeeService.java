package com.litmus7.employeemanager.service;

import java.util.List;

import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.AppException;

public class EmployeeService {
	
	public int createEmployee(Employee employee) throws AppException {
		int rowInserted = new EmployeeDAO().createEmployee(employee);
		if (rowInserted==0) throw new AppException("Failed to insert employee.");
		return rowInserted;
	}
	
	public List<Employee> getAllEmployees() throws AppException {
		List<Employee> employees = new EmployeeDAO().getAllEmployees();
		if (employees.isEmpty()) throw new AppException("No Employees found.");
		return employees;
	}
	
	public Employee getEmployeeById(int employeeId) throws AppException {
		Employee employee = new EmployeeDAO().getEmployeeById(employeeId);
		if (employee == null) throw new AppException("Employee not found.");
		return employee;
	}
	
	public int deleteEmployeebyId(int employeeId) throws AppException {
		int rowDeleted = new EmployeeDAO().deleteEmployeebyId(employeeId);
		if (rowDeleted==0) throw new AppException("Employee not found.");
		return rowDeleted;
	}
	
	public int updateEmployee(Employee employee) throws AppException {
		int rowUpdated = new EmployeeDAO().updateEmployee(employee);
		if (rowUpdated==0) throw new AppException("Employee not found for update.");
		return rowUpdated;
	}
}
