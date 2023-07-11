package osu.cse3241.insert;

import java.util.HashMap;

public class InsuranceTypes {
    public static final HashMap<String, String> insuranceTypesMap = new HashMap<String, String>();

	public InsuranceTypes() {
    	insuranceTypesMap.put("UNINSURED", "Uninsured and underinsured motorist coverage");
        insuranceTypesMap.put("LIABILITY", "Liability coverage");
        insuranceTypesMap.put("COLLISION", "Collision coverage");
        insuranceTypesMap.put("MEDICAL", "Medical payments coverage");
        insuranceTypesMap.put("PERSONAL", "Personal injury protection");
        insuranceTypesMap.put("COMPREHENSIVE", "Comprehensive coverage");	
    }
	
    public static HashMap<String, String> getInsurancetypesmap() {
		return insuranceTypesMap;
	}    
}