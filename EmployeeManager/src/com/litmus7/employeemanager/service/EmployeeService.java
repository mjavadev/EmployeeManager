package com.litmus7.employeemanager.service;

import java.util.List;

import com.litmus7.employeemanager.constant.MessageConstants;
import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.EmployeeDaoException;
import com.litmus7.employeemanager.exception.EmployeeNotFoundException;
import com.litmus7.employeemanager.exception.EmployeeServiceException;

public class EmployeeService {
	
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	
	public int createEmployee(Employee employee) throws EmployeeServiceException {
		int rowInserted;
		try {
			rowInserted = employeeDAO.saveEmployee(employee);
		} catch (EmployeeDaoException e) {
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_CREATE_EMPLOYEE, e);
		}
		if (rowInserted==0) throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_CREATE_EMPLOYEE);
		return rowInserted;
	}
	
	public List<Employee> getAllEmployees() throws EmployeeServiceException, EmployeeNotFoundException {
		List<Employee> employees;
		try {
			employees = employeeDAO.getAllEmployees();
		} catch (EmployeeDaoException e) {
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_FETCH_ALL_EMPLOYEES, e);
		}
		if (employees.isEmpty()) throw new EmployeeNotFoundException(MessageConstants.ERROR_NO_EMPLOYEES_FOUND);
		return employees;
	}
	
	public Employee getEmployeeById(int employeeId) throws EmployeeServiceException, EmployeeNotFoundException {
		Employee employee;
		try {
			employee = employeeDAO.getEmployeeById(employeeId);
		} catch (EmployeeDaoException e) {
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_FETCH_EMPLOYEE,e);
		}
		if (employee == null) throw new EmployeeNotFoundException(String.format(MessageConstants.ERROR_EMPLOYEE_NOT_FOUND_BY_ID, employeeId));
		return employee;
	}
	
	public int deleteEmployeebyId(int employeeId) throws EmployeeServiceException {
		int rowDeleted=0;
		try {
			rowDeleted = employeeDAO.deleteEmployeebyId(employeeId);
		} catch (EmployeeDaoException e) {
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_DELETE_EMPLOYEE,e);   //)
		}
		if (rowDeleted==0) throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_DELETE_EMPLOYEE);
		return rowDeleted;
	}
	
	public int updateEmployee(Employee employee) throws EmployeeServiceException {
		int rowUpdated;
		try {
			rowUpdated = employeeDAO.updateEmployee(employee);
		} catch(EmployeeDaoException e) {
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_UPDATE_EMPLOYEE, e);
		}
		if (rowUpdated==0) throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_UPDATE_EMPLOYEE);
		return rowUpdated;
	}
}
