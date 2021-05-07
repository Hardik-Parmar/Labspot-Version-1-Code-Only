package com.example.labspot_backend_api.user_api.user_registration_and_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Forgot_Password_OTP_Verify_Bean;
import Java_DAO.user_DAO.user_registration_and_login_DAO.User_Forgot_Password_OTP_Verify_DAO;

@Path("user")
public class User_Forgot_Password_OTP_Verify 
{
	User_Forgot_Password_OTP_Verify_Bean user_Forgot_Password_OTP_Verify_Bean;
	User_Forgot_Password_OTP_Verify_DAO user_Forgot_Password_OTP_Verify_DAO;
	
	String email_temp, otp_temp, result;
	
	@Path("forgot_password_otp_verify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userForgotPasswordOTPVerify(String json)
	{
		System.out.println("\nUSER FORGOT PASSWORD OTP VERFICATION API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			email_temp = jsonObject.get("user_email").getAsString();
			otp_temp = jsonObject.get("user_forgot_password_otp").getAsString();
			
			user_Forgot_Password_OTP_Verify_Bean = new User_Forgot_Password_OTP_Verify_Bean(email_temp, otp_temp);
			
		}
		
		user_Forgot_Password_OTP_Verify_DAO = new User_Forgot_Password_OTP_Verify_DAO();
		
		result = user_Forgot_Password_OTP_Verify_DAO.userForgotOTPVerify(user_Forgot_Password_OTP_Verify_Bean);
		
		System.out.println("\nReturn Message from userForgotOTPVerify Method : - "+result);
		
		return "{'user_return_message' : '"+result+"'}";
	}
}