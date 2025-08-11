package com.litmus7.employeemanager.constant;

public class MessageConstants {
	
	//success messages
	public static final String EMPLOYEE_CREATED_SUCCESSFULLY = "";
	
	//error massages
	//Employee - DAO Layer Failure Messages
	public static final String ERROR_INSERTING_EMPLOYEE = "An error occurred while inserting the employee.";
	public static final String ERROR_RETRIEVING_EMPLOYEE_LIST = "An error occurred while retrieving employee list.";
	public static final String ERROR_RETRIEVING_EMPLOYEE = "An error occurred while retrieving the employee.";
	public static final String ERROR_DELETING_EMPLOYEE = "An error occurred while deleting the employee.";
	public static final String ERROR_UPDATING_EMPLOYEE = "An error occurred while updating the employee.";
	public static final String EMPLOYEE_ALREADY_EXISTS = "Employee already exists.";
	
	//Employee - Service Layer Failure Messages
	public static final String FAILED_TO_INSERT_EMPLOYEE = "Failed to insert employee.";
	public static final String NO_EMPLOYEES_FOUND = "No Employees found.";
	public static final String EMPLOYEE_NOT_FOUND = "Employee not found.";
	public static final String EMPLOYEE_NOT_FOUND_FOR_UPDATE = "Employee not found for update.";

	//Employee - Controller Layer

	// Success
	public static final String DATA_LOADED_SUCCESSFULLY = "Data loaded successfully.";
	public static final String DATA_WRITTEN_TO_CSV_SUCCESSFULLY = "Data written to CSV succesfully.";
	public static final String EMPLOYEE_ADDED_TO_CSV_SUCCESSFULLY = "Employee added to CSV successfully.";
	public static final String EMPLOYEE_INSERTED_SUCCESFULLY = "Employee inserted successfully.";
	public static final String EMPLOYEE_LIST_RETRIEVED_SUCCESSFULLY = "Employee list retrieved successfully.";
	public static final String EMPLOYEE_RETRIEVED_SUCCESSFULLY = "Employee retrieved successfully.";
	public static final String EMPLOYEE_DELETED_SUCCESSFULLY = "Employee deleted successfully.";
	public static final String EMPLOYEE_UPDATED_SUCCESSFULLY = "Employee updated successfully.";
	// Errors
	public static final String MISSING_INPUT_TEXT_FILE = "No input text file provided.";
	public static final String FILE_NOT_FOUND = "File not found: ";
	public static final String ERROR_READING_FILE = "Error reading the file: ";
	public static final String UNEXPECTED_TEXT_FILE_PROCESSING_ERROR = "Unexpected error while processing text file.";
	public static final String NO_EMPLOYEE_INFORMATION_FOUND = "No employee information could be found.";
	public static final String MISSING_OUTPUT_CSV_FILE = "No output csv file found";
	public static final String FAILED_TO_WRITE_TO_CSV = "Failed to write to CSV";
	public static final String FAILED_TO_ADD_EMPLOYEE_TO_CSV = "Failed to add employee to CSV";
	public static final String EMPLOYEE_DETAILS_REQUIRED = "Please provide employee details";
	public static final String INVALID_EMPLOYEE_ID = "Invalid employee id";

	// Validation Messages

	// Generic
	public static final String VALIDATION_PASSED = "Validation passed.";

	// ID
	public static final String INVALID_ID_FORMAT = "ID must be a valid integer.";
	public static final String ID_MUST_BE_UNIQUE = "ID must be unique.";
	public static final String ID_MUST_BE_POSITIVE_INTEGER = "ID must be a positive integer.";

	// Employee Name Fields
	public static final String FIRST_NAME_CANNOT_BE_EMPTY = "First name cannot be empty.";
	public static final String LAST_NAME_CANNOT_BE_EMPTY = "Last name cannot be empty.";

	// Mobile Number
	public static final String MOBILE_NUMBER_CANNOT_BE_EMPTY = "Mobile number cannot be empty.";
	public static final String INVALID_MOBILE_NUMBER_FORMAT = "Mobile number must be a valid numerical format.";

	// Email Address
	public static final String EMAIL_ADDRESS_CANNOT_BE_EMPTY = "Email address cannot be empty.";
	public static final String INVALID_EMAIL_ADDRESS_FORMAT = "Email address must follow a basic email format.";

	// Joining Date
	public static final String INVALID_JOINING_DATE_FORMAT = "Joining date must be a valid date in YYYY-MM-DD format.";
	public static final String JOINING_DATE_CANNOT_BE_FUTURE = "Joining date cannot be a future date.";

	// Active Status
	public static final String INVALID_ACTIVE_STATUS = "Active status must be true or false.";

	
	
	
	//DbConnectionUtil
	public static final String ERROR_DB_PROPERTIES_NOT_FOUND_IN_CLASSPATH = "Could not find db.properties in classpath";
	public static final String ERROR_DB_CONFIG_LOAD_FAILED = "Failed to load database configuration";
	
	

	
}
