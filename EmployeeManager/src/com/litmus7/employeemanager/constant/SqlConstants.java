package com.litmus7.employeemanager.constant;

public class SqlConstants {
	
	//EMPLOYEE TABLE
	public static final String EMPLOYEE_TABLE = "employee";
	
	public static final String EMPLOYEE_COL_ID = "id";
	public static final String EMPLOYEE_COL_FIRST_NAME = "first_name";
	public static final String EMPLOYEE_COL_LAST_NAME = "last_name";
	public static final String EMPLOYEE_COL_MOBILE_NO = "mobile_no";
	public static final String EMPLOYEE_COL_EMAIL = "email";
	public static final String EMPLOYEE_COL_JOINING_DATE = "joining_date";
	public static final String EMPLOYEE_COL_ACTIVE_STATUS = "active_status";
	
	
	public static final String INSERT_EMPLOYEE = "insert into "+EMPLOYEE_TABLE+" values (?,?,?,?,?,?,?)";
	
	public static final String SELECT_All_EMPLOYEES = 
			"select " +
			EMPLOYEE_COL_ID+", " +
			EMPLOYEE_COL_FIRST_NAME+", "  +
			EMPLOYEE_COL_LAST_NAME+", "  +
			EMPLOYEE_COL_MOBILE_NO+", "  +
			EMPLOYEE_COL_EMAIL+", "  +
			EMPLOYEE_COL_JOINING_DATE+", "  +
			EMPLOYEE_COL_ACTIVE_STATUS+ " from " + EMPLOYEE_TABLE;
	
	public static final String SELECT_EMPLOYEE = 
			"select " +
			EMPLOYEE_COL_ID+", " +
			EMPLOYEE_COL_FIRST_NAME+", "  +
			EMPLOYEE_COL_LAST_NAME+", "  +
			EMPLOYEE_COL_MOBILE_NO+", "  +
			EMPLOYEE_COL_EMAIL+", "  +
			EMPLOYEE_COL_JOINING_DATE+", "  +
			EMPLOYEE_COL_ACTIVE_STATUS+ " from " + EMPLOYEE_TABLE + " where " + EMPLOYEE_COL_ID + " = ?";
	
	public static final String DELETE_EMPLOYEE = "delete from " + EMPLOYEE_TABLE + " where " + EMPLOYEE_COL_ID + " = ?";
	
	public static final String UPDATE_EMPLOYEE = 
	"update "+ EMPLOYEE_TABLE+" set "+ EMPLOYEE_COL_FIRST_NAME + " =?, "+ EMPLOYEE_COL_LAST_NAME + " =?, " 
	+ EMPLOYEE_COL_MOBILE_NO + " =?, " + EMPLOYEE_COL_EMAIL+ " =?, " +EMPLOYEE_COL_JOINING_DATE + " =?, "
	+ EMPLOYEE_COL_ACTIVE_STATUS + " =? where "+ EMPLOYEE_COL_ID +" =?";
}
