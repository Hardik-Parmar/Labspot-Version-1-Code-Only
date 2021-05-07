package com.example.labspot_backend_api.lab_api.lab_registration_and_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Forgot_Password_OTP_Verify_Bean;
import Java_DAO.lab_DAO.lab_registration_and_login_DAO.Lab_Forgot_Password_OTP_Verify_DAO;

@Path("lab")
public class Lab_Forgot_Password_OTP_Verify 
{
	Lab_Forgot_Password_OTP_Verify_Bean lab_Forgot_Password_OTP_Verify_Bean;
	Lab_Forgot_Password_OTP_Verify_DAO lab_Forgot_Password_OTP_Verify_DAO;
	
	String email_temp, otp_temp, result;
	
	@Path("forgot_password_otp_verify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labForgotPasswordOTPVerify(String json)
	{
		System.out.println("\nLAB FORGOT PASSWORD OTP VERFICATION API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			email_temp = jsonObject.get("lab_email").getAsString();
			otp_temp = jsonObject.get("lab_forgot_password_otp").getAsString();
			
			lab_Forgot_Password_OTP_Verify_Bean = new Lab_Forgot_Password_OTP_Verify_Bean(email_temp, otp_temp);
			
		}
		
		lab_Forgot_Password_OTP_Verify_DAO = new Lab_Forgot_Password_OTP_Verify_DAO();
		
		result = lab_Forgot_Password_OTP_Verify_DAO.labForgotOTPVerify(lab_Forgot_Password_OTP_Verify_Bean);
		
		System.out.println("\nReturn Message from labForgotOTPVerify Method : - "+result);
		
		return "{'lab_return_message' : '"+result+"'}";
	}
}