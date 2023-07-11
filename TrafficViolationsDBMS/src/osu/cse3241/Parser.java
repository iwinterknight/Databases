package osu.cse3241;

import java.sql.PreparedStatement;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import osu.cse3241.insert.InsuranceTypes;

import osu.cse3241.Querylets;

public class Parser {

	private Connection conn;
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public boolean checkStringExists(String item, String TableName, String AttributeName) {
		String sqlStatement = "SELECT * FROM ? where ? = ?;";
		List<String> params = new ArrayList<String>();
        params.add(TableName);
        params.add(AttributeName);
        params.add(item);
    	int columnCount = Querylets.sqlQueryAllStrings(conn, sqlStatement, true, params);
    	if (columnCount > 0) {
    		return true;
    	}
    	return false;
	}
	
	public boolean checkStringFormat(String item, String sep, int numSeparators, List<Integer> spltLengths) {
		int count = (int)item.chars().filter(ch -> ch == sep.charAt(0)).count();
		if (count != numSeparators) {
			return false;
		}
		
		String[] result = item.split(sep);
		if (result.length != spltLengths.size()) {
			return false;
		}
		
		int idx = 0;
		for (String res : result) {
			int spltLen = spltLengths.get(idx++);
			if (res.length() != spltLen) {
				return false;
			}
		}
		
		return true;
	}
}
