package com.example.labspot_backend_api.lab_api.lab_registration_and_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Account_Verify_Bean;
import Java_DAO.lab_DAO.lab_registration_and_login_DAO.Lab_Account_Verify_DAO;

@Path("lab")
public class Lab_Account_Verify 
{
	Lab_Account_Verify_Bean lab_Account_Verify_Bean;
	Lab_Account_Verify_DAO lab_Account_Verify_DAO;

	String email_temp, otp_temp, result;
	
	@Path("account_verify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labAccountVerify(String json)
	{
		System.out.println("\nLAB ACCOUNT VERFICATION API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			email_temp = jsonObject.get("lab_email").getAsString();
			otp_temp = jsonObject.get("lab_otp").getAsString();
			
			lab_Account_Verify_Bean = new Lab_Account_Verify_Bean(email_temp, otp_temp);
			
		}
		
		lab_Account_Verify_DAO = new Lab_Account_Verify_DAO();
		
		result = lab_Account_Verify_DAO.labAccountVerify(lab_Account_Verify_Bean);
		
		System.out.println("\nReturn Message from labAccountVerify Method : - "+result);
		
		return "{'lab_return_message' : '"+result+"'}";
	}
}