package com.litmus7.employeemanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.litmus7.employeemanager.constant.MessageConstants;
import com.litmus7.employeemanager.constant.SqlConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.EmployeeDaoException;
import com.litmus7.employeemanager.util.DbConnectionUtil;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
	
	public int saveEmployee(Employee employee) throws EmployeeDaoException {
		String employeeInsertQuery = SqlConstants.INSERT_EMPLOYEE;
		try(Connection connection = DbConnectionUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(employeeInsertQuery))  {		
			preparedStatement.setInt(1, employee.getId());
			preparedStatement.setString(2, employee.getFirstName());
			preparedStatement.setString(3, employee.getLastName());
			preparedStatement.setString(4, employee.getMobileNumber());
			preparedStatement.setString(5, employee.getEmail());
			preparedStatement.setDate(6, Date.valueOf(employee.getDate()));
			preparedStatement.setBoolean(7, employee.getActiveStatus());
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new EmployeeDaoException(MessageConstants.ERROR_DB_SAVE_EMPLOYEE,e);
		}
	} 
	
	public List<Employee> getAllEmployees() throws EmployeeDaoException {
		String employeesSelectQuery = SqlConstants.SELECT_All_EMPLOYEES;
		try(Connection connection = DbConnectionUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(employeesSelectQuery);
			ResultSet resultSet = preparedStatement.executeQuery()) {
			return getEmployeesFromResultSet(resultSet);
		} catch(SQLException e) {
			throw new EmployeeDaoException(MessageConstants.ERROR_DB_FETCH_ALL_EMPLOYEES,e);
		}
	}
	
	public Employee getEmployeeById(int employeeId) throws EmployeeDaoException {
		String employeeSelectQuery = SqlConstants.SELECT_EMPLOYEE;
		try(Connection connection = DbConnectionUtil.getConnection(); 
			PreparedStatement preparedStatement = connection.prepareStatement(employeeSelectQuery)) {
			preparedStatement.setInt(1, employeeId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return getEmployeeFromResultSet(resultSet);
			}
		} catch(SQLException e) {
			throw new EmployeeDaoException(MessageConstants.ERROR_DB_FETCH_EMPLOYEE,e);
		}
	}
	
	public int deleteEmployeebyId(int employeeId) throws EmployeeDaoException   {
		String employeeDeleteQuery = SqlConstants.DELETE_EMPLOYEE;
		try(Connection connection = DbConnectionUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(employeeDeleteQuery)) {
			preparedStatement.setInt(1, employeeId);
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new EmployeeDaoException(MessageConstants.ERROR_DB_DELETE_EMPLOYEE,e);
		}
	}
	
	public int updateEmployee(Employee employee) throws EmployeeDaoException {
		String employeeUpdateQuery = SqlConstants.UPDATE_EMPLOYEE;
		try(Connection connection = DbConnectionUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(employeeUpdateQuery)) {
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getMobileNumber());
			preparedStatement.setString(4, employee.getEmail());
			preparedStatement.setDate(5, Date.valueOf(employee.getDate()));
			preparedStatement.setBoolean(6, employee.getActiveStatus());
			preparedStatement.setInt(7, employee.getId());
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new EmployeeDaoException(MessageConstants.ERROR_DB_UPDATE_EMPLOYEE,e);
		}
	}
	
	//helper methods
	private List<Employee> getEmployeesFromResultSet(ResultSet resultSet) throws SQLException{
		List<Employee> employees = new ArrayList<>();
		while (resultSet.next()) {
			Employee employee = saveEmployeeFromResultSet(resultSet);
			employees.add(employee);
		}
		return employees;
	}
	
	private Employee getEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
		if(!resultSet.next()) return null;
		return saveEmployeeFromResultSet(resultSet);
	}
	
	private Employee saveEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt(SqlConstants.EMPLOYEE_COL_ID);
		String firstName = resultSet.getString(SqlConstants.EMPLOYEE_COL_FIRST_NAME);
		String lastName = resultSet.getString(SqlConstants.EMPLOYEE_COL_LAST_NAME);
		String mobileNo = resultSet.getString(SqlConstants.EMPLOYEE_COL_MOBILE_NO);
		String email = resultSet.getString(SqlConstants.EMPLOYEE_COL_EMAIL);
		LocalDate joiningDate = resultSet.getDate(SqlConstants.EMPLOYEE_COL_JOINING_DATE).toLocalDate();
		boolean activeStatus = resultSet.getBoolean(SqlConstants.EMPLOYEE_COL_ACTIVE_STATUS);
		return new Employee(id, firstName, lastName, mobileNo, email, joiningDate, activeStatus);
	}
}
