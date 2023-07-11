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

import osu.cse3241.Utils;


public class TrafficViolationsApp {

	private static String DATABASE = "TrafficViolations.db";
	
	private static Parser parserObj = new Parser();

	public static Connection initializeDB(String databaseFileName) {
		String url = "jdbc:sqlite:" + databaseFileName;
		Connection conn = null; // If you create this variable inside the Try block it will be out of scope
		try {
			conn = DriverManager.getConnection(url);
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				
				parserObj.setConn(conn);
				
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("The connection to the database was successful.");
			} else {
				System.out.println("Null Connection");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("There was a problem connecting to the database.");
		}
		return conn;
	}

	public static void sqlQuery(Connection conn, String sql, boolean has_params, String param) {
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			if (has_params) {
				stmt.setString(1, param);
			}
			
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String value = rsmd.getColumnName(i);
				System.out.print(value);
				if (i < columnCount)
					System.out.print(",  ");
			}
			System.out.print("\n");
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
					if (i < columnCount)
						System.out.print(",  ");
				}
				System.out.print("\n");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void sqlQuery2(Connection conn, String sql, boolean has_params, List<String> params) {
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			if (has_params) {
				int i = 1;
				for (String param : params) {
					stmt.setString(i, param);
					i++;
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String value = rsmd.getColumnName(i);
				System.out.print(value);
				if (i < columnCount)
					System.out.print(",  ");
			}
			System.out.print("\n");
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
					if (i < columnCount)
						System.out.print(",  ");
				}
				System.out.print("\n");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println("This is a new run");
		Connection conn = initializeDB(DATABASE);
		
		System.out.println("Enter :\n1 for All Violations\n2 for Violations against which fine has been paid\n3 for Violations against which fine has not been paid\n4 for Violations in between two dates.\n");
		BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
	 
		String inp = "";
        try {
			inp = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
     
        String sqlStatement;
        if (inp.equals("1")) {
        	sqlStatement = "SELECT * FROM Violations;";
        	sqlQuery(conn, sqlStatement, false, "");	
        }
        else if(inp.equals("2")) {
        	sqlStatement = "SELECT * FROM Violations where Paid = ?;";
        	sqlQuery(conn, sqlStatement, true, "Yes");
        }
        else if(inp.equals("3")) {
        	sqlStatement = "SELECT * FROM Violations where Paid = ?;";
        	sqlQuery(conn, sqlStatement, true, "No");
        }
        else if(inp.equals("4")) {
        	System.out.println("Enter from date in yyyy-mm-dd format:");
    		reader = new BufferedReader(
    	            new InputStreamReader(System.in));
    		inp = "";
            try {
    			inp = reader.readLine();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            String date1 = inp;
            
            System.out.println("Enter to date in yyyy-mm-dd format:");
    		reader = new BufferedReader(
    	            new InputStreamReader(System.in));
    		inp = "";
            try {
    			inp = reader.readLine();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            String date2 = inp;
            
            List<String> params = new ArrayList<String>();
            params.add(date1);
            params.add(date2);
        	sqlStatement = "SELECT * FROM Violations where ViolationDate between date(?) and date(?);";
        	sqlQuery2(conn, sqlStatement, true, params);
        }

		System.out.println("*********************************************************************");
		System.out.println("Part 5 - Add another query");

		System.out.println("*********************************************************************");
		System.out.println("Part 6 - Add other queries - Use PreparedStatements");
	}
}
