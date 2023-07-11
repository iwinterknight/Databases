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
import osu.cse3241.insert.*;

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
	
	public static void main(String[] args) {
		System.out.println("This is a new run");
		Connection conn = initializeDB(DATABASE);
	
		InsertUtil insertUtil = new InsertUtil();
		insertUtil.setParserObj(parserObj);
		insertUtil.setConn(conn);
		
		PeopleVehicles peopleVehicles = new PeopleVehicles();
		
		System.out.println("Enter :\n1 to enter Vehicle Record\n2 to enter Person's Record\n");
		BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
	 
		String selection = "";
        try {
        	selection = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
     
        String sqlStatement;
        if (selection.equals("1")) {
        	System.out.println("Let's first verify whether the Vehicle already exists in the Database.\nEnter Vehicle's License Number\n");
        	String inpLicenseNumber = "";
            reader = new BufferedReader(
    	            new InputStreamReader(System.in));
            try {
            	inpLicenseNumber = reader.readLine();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            boolean vehicleExists = insertUtil.checkVehicleExists(inpLicenseNumber);
            if (vehicleExists) {
            	System.out.println("Vehicle already exists. Retrieving vehicle information...\n");
            	List<Object> vehicleInformation = insertUtil.getVehicleInformation(inpLicenseNumber);
            	String vehicleLicenseNumber = (String)vehicleInformation.get(0);
            	String vehicleRegistrationCity = (String)vehicleInformation.get(1);
            	String vehicleRegistrationState = (String)vehicleInformation.get(2);
            	int vehicleRegistrationZipCode = (int)vehicleInformation.get(3);            	
			}
        } else if (selection.equals("2")) {
        	peopleVehicles.insertPeople(insertUtil);
        }
    }
}
