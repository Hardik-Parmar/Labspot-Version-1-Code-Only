package com.example.labspot_backend_api.lab_api.lab_registration_and_login;

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

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Reset_Password_Bean;
import Java_DAO.lab_DAO.lab_registration_and_login_DAO.Lab_Reset_Password_DAO;
import database_connection.ConnectionProvider;
import send_mail.Lab_Change_Password_Alert_Mail;

@Path("lab")
public class Lab_Reset_Password 
{
	Lab_Reset_Password_Bean lab_Reset_Password_Bean;
	Lab_Reset_Password_DAO lab_Reset_Password_DAO;
	
	public String email_temp, new_password_temp, result, response;
	
	@Path("reset_password")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labPasswordReset(String json)
	{
		System.out.println("\nLAB RESET PASSWORD API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			email_temp = jsonObject.get("lab_email").getAsString();
			new_password_temp = jsonObject.get("lab_new_password").getAsString();
			
			lab_Reset_Password_Bean = new Lab_Reset_Password_Bean(email_temp, new_password_temp);
			
		}
		
		lab_Reset_Password_DAO = new Lab_Reset_Password_DAO();
		
		result = lab_Reset_Password_DAO.labResetPassword(lab_Reset_Password_Bean);
		
		System.out.println("\nReturn Message from labAccountVerify Method : - "+result);
		
		if(result.equals("Lab Password has been Reset"))
		{
			String name = getName(lab_Reset_Password_Bean.getEmail());
			
			response = Lab_Change_Password_Alert_Mail.labPasswordChangeAlertEmail(lab_Reset_Password_Bean.getEmail(), name);
			System.out.println("\n\nReturn Message from labPasswordChangeAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'lab_return_message' : '"+result+"'}";
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