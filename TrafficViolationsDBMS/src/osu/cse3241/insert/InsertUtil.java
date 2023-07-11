package osu.cse3241.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import osu.cse3241.Parser;
import osu.cse3241.Querylets;
import osu.cse3241.insert.InsuranceTypes;


public class InsertUtil {
	
	private Connection conn;
	private Parser parserObj;

	boolean validFormats;
	boolean uniqueEntries;
	boolean entryExists;
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public void setParserObj(Parser parserObj) {
		this.parserObj = parserObj;
	}
	
	
	
	public void enterInsuranceInformation(String insuranceID, String expirationDate, String phoneNumber, String insuranceType) {
		validFormats = true;
		uniqueEntries = true;
		
		uniqueEntries = !this.parserObj.checkStringExists(insuranceID, "Insurance", "InsuranceID");
		
		List<Integer> spltLengths = new ArrayList<>();
		spltLengths.add(4);
		spltLengths.add(2);
		spltLengths.add(2);
		validFormats = this.parserObj.checkStringFormat(expirationDate, "-", 2, spltLengths);
		
		if (validFormats && uniqueEntries) {
			List<String> params = new ArrayList<String>();
	        params.add(insuranceID);
	        params.add(expirationDate);
	        params.add(phoneNumber);
	        params.add(insuranceType);
			String sqlStatement = "INSERT INTO Insurance (InsuranceID, ExpirationDate, PhoneNumber, InsuranceType) "
					+ "VALUES (?, ?, ?, ?);";
	    	Querylets.sqlQueryAllStrings(conn, sqlStatement, entryExists, params);	
		}
	}
	
	public boolean checkVehicleExists(String licenseNumber) {
		entryExists = this.parserObj.checkStringExists(licenseNumber, "Vehicles", "LicenseNumber");
		if (entryExists) {
			return true;
		}
		return false;
	}
	
	public List<Object> getVehicleInformation(String licenseNumber) {
		String sqlStatement = "SELECT * FROM Vehicles WHERE LicenseNumber = ?;";
		List<Object> returnList = new ArrayList<>(); 
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, licenseNumber);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rs.getString(i);
					returnList.add(columnValue);
				}
			}
			
			return returnList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public List<Object> getVehicleLicensesInformation(String licenseNumber) {
		String sqlStatement = "SELECT * FROM VehicleLicenses WHERE LicenseNumber = ?;";
		List<Object> returnList = new ArrayList<>(); 
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, licenseNumber);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rs.getString(i);
					returnList.add(columnValue);
				}
			}
			
			return returnList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void enterVehicleInformation(String licenseNumber, String registrationCity, String registrationState, int registrationZipCode) {
		validFormats = true;
		uniqueEntries = true;
		
		uniqueEntries = !this.parserObj.checkStringExists(licenseNumber, "Vehicles", "LicenseNumber");
	
		String sqlStatement = "INSERT INTO Vehicles (LicenseNumber, RegistrationCity, RegistrationState, RegistrationZipCode) "
				+ "VALUES (?, ?, ?, ?);";
		
		if (validFormats && uniqueEntries) {
			try {
				PreparedStatement stmt = conn.prepareStatement(sqlStatement);
				stmt.setString(1, licenseNumber);
				stmt.setString(2, registrationCity);
				stmt.setString(3, registrationState);
				stmt.setInt(4, registrationZipCode);
				
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void enterVehicleLicensesInformation(String vehicleID, String licenseNumber) {
		validFormats = true;
		uniqueEntries = true;
		
		uniqueEntries = !this.parserObj.checkStringExists(vehicleID, "VehicleLicenses", "VehicleID");
		uniqueEntries = !this.parserObj.checkStringExists(licenseNumber, "VehicleLicenses", "LicenseNumber");
	
		String sqlStatement = "INSERT INTO VehicleLicenses (VehicleID, LicenseNumber) VALUES (?, ?);";
		
		if (validFormats && uniqueEntries) {
			try {
				PreparedStatement stmt = conn.prepareStatement(sqlStatement);
				stmt.setString(1, vehicleID);
				stmt.setString(2, licenseNumber);
				
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	public void enterPersonInformation(String SSN, String FirstName, String LastName, int HouseNo, String StreetName, String City, 
			String State, int Zipcode, String InsuranceID, String VehicleID) {
		validFormats = true;
		uniqueEntries = true;
		entryExists = false;
		
		uniqueEntries = !this.parserObj.checkStringExists(SSN, "People", "SSN");
		entryExists = this.parserObj.checkStringExists(InsuranceID, "Insurance", "InsuranceID");
		entryExists = this.parserObj.checkStringExists(VehicleID, "People", "VehicleID");
	
		List<Integer> spltLengths = new ArrayList<>();
		spltLengths.add(3);
		spltLengths.add(2);
		spltLengths.add(4);
		validFormats = this.parserObj.checkStringFormat(SSN, "-", 2, spltLengths);
		
		String sqlStatement = "INSERT INTO People (SSN, FirstName, Lastname, HouseNo, StreetName, City, State, Zipcode, InsuranceID, VehicleID)"
				+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		if (validFormats && uniqueEntries && entryExists) {
			try {
				PreparedStatement stmt = conn.prepareStatement(sqlStatement);
				stmt.setString(1, SSN);
				stmt.setString(2, FirstName);
				stmt.setString(3, LastName);
				stmt.setInt(4, HouseNo);
				stmt.setString(5, StreetName);
				stmt.setString(6, City);
				stmt.setString(7, State);
				stmt.setInt(8, Zipcode);
				stmt.setString(9, InsuranceID);
				stmt.setString(10, VehicleID);
				
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}
