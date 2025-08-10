package com.litmus7.employeemanager.service;

import java.util.List;

import com.litmus7.employeemanager.constant.MessageConstants;
import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.AppException;

public class EmployeeService {
	
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	
	public int createEmployee(Employee employee) throws AppException {
		int rowInserted = employeeDAO.saveEmployee(employee);
		if (rowInserted==0) throw new AppException(MessageConstants.FAILED_TO_INSERT_EMPLOYEE);
		return rowInserted;
	}
	
	public List<Employee> getAllEmployees() throws AppException {
		List<Employee> employees = employeeDAO.getAllEmployees();
		if (employees.isEmpty()) throw new AppException(MessageConstants.NO_EMPLOYEES_FOUND);
		return employees;
	}
	
	public Employee getEmployeeById(int employeeId) throws AppException {
		Employee employee = employeeDAO.getEmployeeById(employeeId);
		if (employee == null) throw new AppException(MessageConstants.EMPLOYEE_NOT_FOUND);
		return employee;
	}
	
	public int deleteEmployeebyId(int employeeId) throws AppException {
		int rowDeleted = employeeDAO.deleteEmployeebyId(employeeId);
		if (rowDeleted==0) throw new AppException(MessageConstants.EMPLOYEE_NOT_FOUND);
		return rowDeleted;
	}
	
	public int updateEmployee(Employee employee) throws AppException {
		int rowUpdated = employeeDAO.updateEmployee(employee);
		if (rowUpdated==0) throw new AppException(MessageConstants.EMPLOYEE_NOT_FOUND_FOR_UPDATE);
		return rowUpdated;
	}
}
