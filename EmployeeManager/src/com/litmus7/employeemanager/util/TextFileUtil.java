package com.litmus7.employeemanager.util;

import java.util.ArrayList;
import java.util.List;


public class TextFileUtil {

	public static List<String> parseDelimitedLine(String emp) {
		int start = 0;
		int end =0;
		List<String> fields = new ArrayList<>();
		
		for (int i=0; i<emp.length(); i++) {
			if (emp.charAt(i) == '$') {
				String field = emp.substring(start, end);
				fields.add(field);
				start = end +1;
				end++;
			}
			else {
				end++;
			}
		}
		String field = emp.substring(start, end);
		fields.add(field);
		
		return fields;
	}
	
	public static String escapeCSV(String data) {
		if (data.contains(",")) {
			return "\"" + data + "\"";
		}
		return data;
	}
	
}