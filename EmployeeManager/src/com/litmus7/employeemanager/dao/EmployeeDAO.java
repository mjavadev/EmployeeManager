package com.litmus7.employeemanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.AppException;
import com.litmus7.employeemanager.util.DbConnectionUtil;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

	public int createEmployee(Employee employee) throws AppException {
		try(Connection connection = DbConnectionUtil.getConnection()) {
			String employeeInsertQuery = "insert into employee values (?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(employeeInsertQuery);
			preparedStatement.setInt(1, employee.getId());
			preparedStatement.setString(2, employee.getFirstName());
			preparedStatement.setString(3, employee.getLastName());
			preparedStatement.setString(4, employee.getMobileNumber());
			preparedStatement.setString(5, employee.getEmail());
			preparedStatement.setDate(6, Date.valueOf(employee.getDate()));
			preparedStatement.setBoolean(7, employee.getActiveStatus());
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new AppException("An error occurred while inserting the employee.",e);
		}
	} 
	
	public List<Employee> getAllEmployees() throws AppException {
		try(Connection connection = DbConnectionUtil.getConnection()) {
			String employeesSelectQuery = "select id, first_name, last_name, mobile_no, email, joining_date, active_status from employee";
			PreparedStatement preparedStatement = connection.prepareStatement(employeesSelectQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			return getEmployeesFromResultSet(resultSet);
		} catch(SQLException e) {
			throw new AppException("An error occurred while retrieving employee list.",e);
		}
	}
	
	public Employee getEmployeeById(int employeeId) throws AppException {
		try(Connection connection = DbConnectionUtil.getConnection()) {
			String employeeSelectQuery = "select id, first_name, last_name, mobile_no, email, joining_date, active_status from employee where id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(employeeSelectQuery);
			preparedStatement.setInt(1, employeeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			return getEmployeeFromResultSet(resultSet);
		} catch(SQLException e) {
			e.printStackTrace();
			throw new AppException("An error occurred while retrieving the employee.",e);
		}
	}
	
	public int deleteEmployeebyId(int employeeId) throws AppException {
		try(Connection connection = DbConnectionUtil.getConnection()) {
			String employeeDeleteQuery = "delete from employee where id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(employeeDeleteQuery);
			preparedStatement.setInt(1, employeeId);
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new AppException("An error occurred while deleting the employee.",e);
		}
	}
	
	public int updateEmployee(Employee employee) throws AppException {
		try(Connection connection = DbConnectionUtil.getConnection()) {
			String employeeUpdateQuery = "update employee set first_name=?,last_name=?,mobile_no=?,"
							+ "email=?, joining_date=?, active_status=? where id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(employeeUpdateQuery);
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getMobileNumber());
			preparedStatement.setString(4, employee.getEmail());
			preparedStatement.setDate(5, Date.valueOf(employee.getDate()));
			preparedStatement.setBoolean(6, employee.getActiveStatus());
			preparedStatement.setInt(7, employee.getId());
			return preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new AppException("An error occurred while updating the employee.",e);
		}
	}
	
	//helper methods
	private List<Employee> getEmployeesFromResultSet(ResultSet resultSet) throws SQLException{
		List<Employee> employees = new ArrayList<>();
		while (resultSet.next()) {
			Employee employee = createEmployeeFromResultSet(resultSet);
			employees.add(employee);
		}
		return employees;
	}
	
	private Employee getEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
		if(!resultSet.next()) return null;
		return createEmployeeFromResultSet(resultSet);
	}
	
	private Employee createEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		String mobileNo = resultSet.getString("mobile_no");
		String email = resultSet.getString("email");
		LocalDate joiningDate = resultSet.getDate("joining_date").toLocalDate();
		boolean activeStatus = resultSet.getBoolean("active_status");
		return new Employee(id, firstName, lastName, mobileNo, email, joiningDate, activeStatus);
	}
}
