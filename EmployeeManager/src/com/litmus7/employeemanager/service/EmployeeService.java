package com.litmus7.employeemanager.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.employeemanager.constant.MessageConstants;
import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.EmployeeDaoException;
import com.litmus7.employeemanager.exception.EmployeeNotFoundException;
import com.litmus7.employeemanager.exception.EmployeeServiceException;

public class EmployeeService {
	
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private static final Logger logger = LogManager.getLogger("EmployeeService.class");
	
	public int createEmployee(Employee employee) throws EmployeeServiceException {
		logger.trace("Entering createEmployee() with employeeId: {}", employee.getId());
		
		int rowInserted;
		
		try {
			
			rowInserted = employeeDAO.saveEmployee(employee);
			
			if (rowInserted>0) {
				logger.info("Employee created successfully: {}", employee.getId());
			} else {
				logger.warn("Employee creation returned 0 rows for employeeId: {}", employee.getId());
				throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_CREATE_EMPLOYEE);
			}
			
		} catch (EmployeeDaoException e) {
			logger.error("Error creating employee: {}", employee.getId(), e);
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_CREATE_EMPLOYEE, e);
		}
	
		logger.trace("Exiting createEmployee() for employeeId: {}", employee.getId());
		return rowInserted;
	}
	
	public List<Employee> getAllEmployees() throws EmployeeServiceException, EmployeeNotFoundException {
		logger.trace("Entering getAllEmployees()");
		
		List<Employee> employees;
		
		try {
			employees = employeeDAO.getAllEmployees();
			
			if (employees.isEmpty()) {
				logger.warn("Employees retrieval returned 0 rows");
				throw new EmployeeNotFoundException(MessageConstants.ERROR_NO_EMPLOYEES_FOUND);
			}
			else {
				logger.info("{} employee(s) retrieved successfully", employees.size());
			}
			
		} catch (EmployeeDaoException e) {
			logger.error("Error fetching employees", e);
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_FETCH_ALL_EMPLOYEES, e);
		}
		
		logger.trace("Exiting getAllEmployees()");
		return employees;
	}
	
	public Employee getEmployeeById(int employeeId) throws EmployeeServiceException, EmployeeNotFoundException {
		logger.trace("Entering getEmployeeById() with employeeId: {}", employeeId);
		
		Employee employee;
		
		try {
			
			employee = employeeDAO.getEmployeeById(employeeId);
			
			if (employee == null) { 
				logger.warn("Employee retrieval returned 0 rows");
				throw new EmployeeNotFoundException(String.format(MessageConstants.ERROR_EMPLOYEE_NOT_FOUND_BY_ID, employeeId));
			}
			else {
				logger.info("Employee retrieved successfully: {}", employee.getId());
			}
			
		} catch (EmployeeDaoException e) {
			logger.error("Error fetching employee: {}",employeeId,e);
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_FETCH_EMPLOYEE,e);
		}
		
		logger.trace("Exiting getEmployeeById() for employeeId: {}", employeeId);
		return employee;
	}
	
	public int deleteEmployeebyId(int employeeId) throws EmployeeServiceException, EmployeeNotFoundException {
		logger.trace("Entering deleteEmployeebyId() with employeeId: {}", employeeId);
		
		int rowDeleted=0;
		
		try {
			
			rowDeleted = employeeDAO.deleteEmployeebyId(employeeId);
		
			if(rowDeleted>0) {
				logger.info("Employee deleted successfully: {}", employeeId);
			}
			else {
				logger.warn("Employee deletion failed: {}", employeeId);
				throw new EmployeeNotFoundException(String.format(MessageConstants.ERROR_EMPLOYEE_NOT_FOUND_BY_ID, employeeId));
			}
	
		} catch (EmployeeDaoException e) {
			logger.error("Error deleting employee: {}",employeeId);
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_DELETE_EMPLOYEE,e);   //)
		}
		
		logger.trace("Exiting deleteEmployeebyId() for employeeId: {}", employeeId);
		return rowDeleted;
	}
	
	public int updateEmployee(Employee employee) throws EmployeeServiceException, EmployeeNotFoundException {
		logger.trace("Entering updateEmployee() with employeeId: {}", employee.getId());
		
		int rowUpdated;
		
		try {
			
			rowUpdated = employeeDAO.updateEmployee(employee);
			
			if(rowUpdated>0) {
				logger.info("Employee details updated successfully for: {}", employee.getId());
			}
			else {
				logger.warn("Updation failed for employee: {}", employee.getId());
				throw new EmployeeNotFoundException(String.format(MessageConstants.ERROR_EMPLOYEE_NOT_FOUND_BY_ID, employee.getId()));
			}
			
		} catch(EmployeeDaoException e) {
			logger.error("Error updating employee: {}",employee.getId());
			throw new EmployeeServiceException(MessageConstants.ERROR_SERVICE_UPDATE_EMPLOYEE, e);
		}
		
		logger.trace("Exiting updateEmployee() for employeeId: {}", employee.getId());
		return rowUpdated;
	}
}
