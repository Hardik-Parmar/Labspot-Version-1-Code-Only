package com.example.labspot_backend_api.user_api.user_registration_and_login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Reset_Password_Bean;
import Java_DAO.user_DAO.user_registration_and_login_DAO.User_Reset_Password_DAO;
import database_connection.ConnectionProvider;
import send_mail.User_Change_Password_Alert_Mail;

@Path("user")
public class User_Reset_Password 
{
	User_Reset_Password_Bean user_Reset_Password_Bean;
	User_Reset_Password_DAO user_Reset_Password_DAO;
	
	public String email_temp, new_password_temp, result, response;
	
	@Path("reset_password")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userPasswordReset(String json)
	{
		System.out.println("\nUSER RESET PASSWORD API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			email_temp = jsonObject.get("user_email").getAsString();
			new_password_temp = jsonObject.get("user_new_password").getAsString();
			
			user_Reset_Password_Bean = new User_Reset_Password_Bean(email_temp, new_password_temp);
			
		}
		
		user_Reset_Password_DAO = new User_Reset_Password_DAO();
		
		result = user_Reset_Password_DAO.userResetPassword(user_Reset_Password_Bean);
		
		System.out.println("\nReturn Message from userAccountVerify Method : - "+result);
		
		if(result.equals("User Password has been Reset"))
		{
			String name = getName(user_Reset_Password_Bean.getEmail());
			
			response = User_Change_Password_Alert_Mail.userPasswordChangeAlertEmail(user_Reset_Password_Bean.getEmail(), name);
			System.out.println("\n\nReturn Message from userPasswordChangeAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'user_return_message' : '"+result+"'}";
	}
	
	// Support method which will retrieve user_name of the input email which will be used in sendMail
	public String getName(String email)
	{
		try
		{
			String name = null;
				
			Connection connection = ConnectionProvider.getConnection();
				
			String query = "SELECT * from user_register WHERE user_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
				
			ResultSet resultSet = ps.executeQuery();
				
			if(resultSet.next())
			{
				name = resultSet.getString("user_name");
			}	
			return name;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}