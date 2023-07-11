package osu.cse3241.insert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class PeopleVehicles {

	protected String getSaltString(int len) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	public void insertPeople(InsertUtil insertUtil) {
    	System.out.println("Enter First Name\n");
    	String inpFirstName = "";
    	BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpFirstName = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter Last Name\n");
    	String inpLastName = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpLastName = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter SSN\n");
    	String inpSSN = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpSSN = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter House No.\n");
    	String inpHouseNo = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpHouseNo = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter Street Name\n");
    	String inpStreetName = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpStreetName = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter City of Residence\n");
    	String inpCity = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpCity = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter State of Residence\n");
    	String inpState = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpState = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter ZipCode\n");
    	int inpZipCode = 0;
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpZipCode = Integer.parseInt(reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Now let's gather insurance details for the user.\n");
        System.out.println("Enter Insurance ID\n");
    	String inpInsuranceID = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpInsuranceID = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter Insurance Expiration Date (format : yyyy-mm-dd)\n");
    	String inpInsuranceExpDate = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpInsuranceExpDate = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Enter Phone Number\n");
    	String inpPhoneNumber = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpPhoneNumber = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}            
        
        System.out.println("Enter Insurance Type\n1 UNINSURED\n2 LIABILITY\n3 COLLISION\n4 MEDICAL\n5 PERSONAL\n6 COMPREHENSIVE\n");
    	String inpInsuranceType = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpInsuranceType = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("Finally, let's gather vehicle details.\n");
        System.out.println("Enter License Number ID\n");
        String inpLicenseNumber = "";
        reader = new BufferedReader(
	            new InputStreamReader(System.in));
        try {
        	inpLicenseNumber = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        boolean vehicleExists = insertUtil.checkVehicleExists(inpLicenseNumber);
        String VehicleId;
        if (vehicleExists) {
        	System.out.println("Vehicle exists in Database.\n");
        	List<Object> vehicleInformation = insertUtil.getVehicleInformation(inpLicenseNumber);
        	String vehicleLicenseNumber = (String)vehicleInformation.get(0);
        	String vehicleRegistrationCity = (String)vehicleInformation.get(1);
        	String vehicleRegistrationState = (String)vehicleInformation.get(2);
        	int vehicleRegistrationZipCode = Integer.parseInt((String)vehicleInformation.get(3));
        	
        	List<Object> vehicleLicensesInformation = insertUtil.getVehicleLicensesInformation(vehicleLicenseNumber);
        	VehicleId = (String)vehicleLicensesInformation.get(0);
		}
        else {
        	VehicleId = getSaltString(16);
            System.out.println("Enter Vehicle Registration City\n");
            String vehicleRegistrationCity = "";
            reader = new BufferedReader(
    	            new InputStreamReader(System.in));
            try {
            	vehicleRegistrationCity = reader.readLine();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            System.out.println("Enter Vehicle Registration State\n");
            String vehicleRegistrationState = "";
            reader = new BufferedReader(
    	            new InputStreamReader(System.in));
            try {
            	vehicleRegistrationState = reader.readLine();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            System.out.println("Enter Vehicle Registration ZipCode\n");
            int vehicleRegistrationZipCode = 0;
            reader = new BufferedReader(
    	            new InputStreamReader(System.in));
            try {
            	vehicleRegistrationZipCode = Integer.parseInt(reader.readLine());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            insertUtil.enterVehicleInformation(inpLicenseNumber, vehicleRegistrationCity, vehicleRegistrationState, vehicleRegistrationZipCode);
            insertUtil.enterVehicleLicensesInformation(VehicleId, inpLicenseNumber);
        }
        
        insertUtil.enterInsuranceInformation(inpInsuranceID, inpInsuranceExpDate, inpPhoneNumber, inpInsuranceType);
        insertUtil.enterPersonInformation(inpSSN, inpFirstName, inpLastName, inpZipCode, inpStreetName, inpCity, inpState, inpZipCode, inpInsuranceID, VehicleId);
    	
    }
}
