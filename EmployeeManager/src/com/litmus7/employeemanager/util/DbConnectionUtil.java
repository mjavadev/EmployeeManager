package com.litmus7.employeemanager.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.litmus7.employeemanager.constant.MessageConstants;

public class DbConnectionUtil {
	
	private static String url;
	private static String username;
	private static String password;
	private static String driver;
	
	static {
	
		try {
			Properties properties = new Properties();
			
			InputStream inputStream = DbConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties");
			if(inputStream == null) throw new RuntimeException(MessageConstants.ERROR_DB_PROPERTIES_NOT_FOUND_IN_CLASSPATH);
			
			properties.load(inputStream);
			
			url = properties.getProperty("db.url");
			username = properties.getProperty("db.username");
			password = properties.getProperty("db.password");
			driver = properties.getProperty("db.driver");
			
			Class.forName(driver);
			
		} catch (Exception e) {
			throw new RuntimeException(MessageConstants.ERROR_DB_CONFIG_LOAD_FAILED, e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url,username,password);
	}
	
}
