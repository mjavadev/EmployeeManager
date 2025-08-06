package com.litmus7.employeemanager.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.exception.AppException;
import com.litmus7.employeemanager.controller.EmployeeController;

public class EmployeeManagerApp {
	
	public static void main(String[] args) {
		
		boolean flag = true;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String fileName = "employees.txt";
		String csvFilePath = "employees.csv";
		
		List<Employee> employeeList = new ArrayList<>();
		Response<List<Employee>, Boolean, String> textReceivedResponse = new Response<>(employeeList, null, null);
		while (flag) {
			System.out.println("1.Get data from text file and write data to csv\n"
					+ "2.Add new employee data to CSV\n"
					+ "3.Create Employee\n"
					+ "4.Get all Employees\n"
					+ "5.Get all Employees by ID\n"
					+ "6.Delete Employee by ID\n"
					+ "7.Update Employee\n"
					+ "8.Exit");
			int choice = -1;
			System.out.print("Enter choice: ");
			try {
				choice = Integer.parseInt(reader.readLine());
				
				switch(choice) {
				case 1:
					textReceivedResponse =  new EmployeeController().getDataFromTextFile(fileName);
					if (textReceivedResponse.getApplicationStatus()) {
						new EmployeeManagerApp().printEmployeeDetails(textReceivedResponse.getData());
						System.out.println(textReceivedResponse.getMessage());
					}
					else 
						System.err.println(textReceivedResponse.getMessage());
				
					Response<Void, Boolean, String> dataWriteToCsvResponse = new EmployeeController().writeDataToCSV(textReceivedResponse.getData(), csvFilePath);
					if (dataWriteToCsvResponse.getApplicationStatus()) 
						System.out.println(dataWriteToCsvResponse.getMessage());
					else 
						System.err.println(dataWriteToCsvResponse.getMessage());
					break;
				case 2:		
					List<String> dataFromUser = new EmployeeManagerApp().getSingleInputFromUser(reader);

					Response<Employee , Boolean, String> newEmployeeAddResponse = new EmployeeController().appendDataToCSV(dataFromUser,textReceivedResponse.getData(),csvFilePath);
					if (newEmployeeAddResponse.getApplicationStatus()) {
						textReceivedResponse.getData().add(newEmployeeAddResponse.getData());
						System.out.println(newEmployeeAddResponse.getMessage()+"\n");
					}
					else {
						System.err.println(newEmployeeAddResponse.getMessage()+"\n");
					}
					break;
				case 3:
					System.out.println("Enter employee details:");
					List<String> employees1 = new EmployeeManagerApp().getSingleInputFromUser(reader);
					Employee employee = new EmployeeManagerApp().createEmployeeFromData(employees1);
					Response<Void, Boolean, String> employeeCreatedResponse = new EmployeeController().createEmployee(employee);
					if (employeeCreatedResponse.getApplicationStatus()) 
						System.out.println(employeeCreatedResponse.getMessage());
					else 
						System.err.println(employeeCreatedResponse.getMessage());
					break;
				case 4:
					Response<List<Employee>, Boolean, String> employeesFetchedResponse = new EmployeeController().getAllEmployees();
					if (employeesFetchedResponse.getApplicationStatus()) {
						new EmployeeManagerApp().printEmployeeDetails(employeesFetchedResponse.getData());
						System.out.println(employeesFetchedResponse.getMessage());
					}
					else 
						System.err.println(employeesFetchedResponse.getMessage());
					break;
				case 5:
					System.out.print("Enter employee id: ");
					int employeeId = Integer.parseInt(reader.readLine());
					Response<Employee, Boolean, String> employeeFetchedResponse =  new EmployeeController().getEmployeeById(employeeId);
					if (employeeFetchedResponse.getApplicationStatus()) {
						new EmployeeManagerApp().printEmployeeObject(employeeFetchedResponse.getData());
						System.out.println(employeeFetchedResponse.getMessage());
					}
					else 
						System.err.println(employeeFetchedResponse.getMessage());
					break;
				case 6:
					System.out.print("Enter employee id: ");
					int employeeId1 = Integer.parseInt(reader.readLine());
					Response<Void, Boolean, String> employeeDeletedResponse = new EmployeeController().deleteEmployeebyId(employeeId1);
					if (employeeDeletedResponse.getApplicationStatus()) 
						System.out.println(employeeDeletedResponse.getMessage());
					else 
						System.err.println(employeeDeletedResponse.getMessage());
					break;
				case 7:
					System.out.println("Enter employee details:");
					List<String> employees11 = new EmployeeManagerApp().getSingleInputFromUser(reader);
					Employee employee1 = new EmployeeManagerApp().createEmployeeFromData(employees11);
					Response<Void, Boolean, String> employeeUpdatedResponse = new EmployeeController().updateEmployee(employee1);
					if (employeeUpdatedResponse.getApplicationStatus()) 
						System.out.println(employeeUpdatedResponse.getMessage());
					else 
						System.err.println(employeeUpdatedResponse.getMessage());
					break;
				case 8:
					flag = false;
					break;
				default:
					System.out.println("Invalid choice\n");
				}
				
			} catch (NumberFormatException e) {
				AppException custom = new AppException("No option selected. Please choose a valid option.",e);
				System.err.println(custom.getMessage()+"\n");
			} catch (IOException e) {
				AppException custom = new AppException("Unable to access the file. Please try again.",e);
				System.err.println(custom.getMessage()+"\n");
			} 
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			AppException custom = new AppException("An unexpected error occurred while closing the file.",e);
			System.err.println(custom.getMessage()+"\n");
		}
	}

	//private helper methods
	
	private void printEmployeeDetails(List<Employee> employees) {
		System.out.println("Employee Details: \n");
		for (Employee i : employees) {
			printEmployeeObject(i);
		}
		
	}
	
	private void printEmployeeObject(Employee emp) {
		int id = emp.getId();
		String firstName =emp.getFirstName();
		String lastName = emp.getLastName();
		String mobileNo = emp.getMobileNumber();
		String email = emp.getEmail();
		LocalDate date = emp.getDate();
		boolean activeStatus = emp.getActiveStatus();
		
		System.out.println("id: " + id);
		System.out.println("firstName: " + firstName);
		System.out.println("lastName: " + lastName);
		System.out.println("mobileNo: " + mobileNo);
		System.out.println("email: " + email);
		System.out.println("date: " + date);
		System.out.println("activeStatus: " + activeStatus);
		System.out.println();
	}
	
	private List<String> getSingleInputFromUser(BufferedReader reader) throws IOException {
		
		System.out.print("Enter employee id: ");
		String id = reader.readLine();
		
		System.out.print("Enter first name: ");
		String firstName = reader.readLine();
		
		System.out.print("Enter last name: ");
		String lastName = reader.readLine();
		
		System.out.print("Enter mobile number: ");
		String mobileNo = reader.readLine();
		
		System.out.print("Enter email: ");
		String email = reader.readLine();
		
		System.out.print("Enter joining date: ");
		String date = reader.readLine();
		
		System.out.print("Enter active status(true/false): ");
		String activeStatus = reader.readLine();

		List<String> dataFromUser = new ArrayList<>(List.of(id,firstName,lastName,mobileNo,email,date,activeStatus));
		return dataFromUser;
	}
	
	private Employee createEmployeeFromData(List<String> dataFromUser) {
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
	
}