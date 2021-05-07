package com.example.labspot_backend_api.delivery_api.delivery_registration_and_login;

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

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Reset_Password_Bean;
import Java_DAO.delivery_DAO.delivery_registration_and_login_DAO.Delivery_Reset_Password_DAO;
import database_connection.ConnectionProvider;
import send_mail.Delivery_Change_Password_Alert_Mail;

@Path("delivery")
public class Deliery_Reset_Password 
{
	Delivery_Reset_Password_Bean delivery_Reset_Password_Bean;
	Delivery_Reset_Password_DAO delivery_Reset_Password_DAO;
	
	public String email_temp, new_password_temp, result, response;
	
	@Path("reset_password")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryPasswordReset(String json)
	{
		System.out.println("\nDELIVERY RESET PASSWORD API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			email_temp = jsonObject.get("delivery_email").getAsString();
			new_password_temp = jsonObject.get("delivery_new_password").getAsString();
			
			delivery_Reset_Password_Bean = new Delivery_Reset_Password_Bean(email_temp, new_password_temp);
			
		}
		
		delivery_Reset_Password_DAO = new Delivery_Reset_Password_DAO();
		
		result = delivery_Reset_Password_DAO.deliveryResetPassword(delivery_Reset_Password_Bean);
		
		System.out.println("\nReturn Message from deliveryAccountVerify Method : - "+result);
		
		if(result.equals("Delivery Password has been Reset"))
		{
			String name = getName(delivery_Reset_Password_Bean.getEmail());
			
			response = Delivery_Change_Password_Alert_Mail.deliveryPasswordChangeAlertEmail(delivery_Reset_Password_Bean.getEmail(), name);
			System.out.println("\n\nReturn Message from deliveryPasswordChangeAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'delivery_return_message' : '"+result+"'}";
	}
	
	// Support method which will retrieve delivery_name of the input email which will be used in sendMail
	public String getName(String email)
	{
		try
		{
			String name = null;
				
			Connection connection = ConnectionProvider.getConnection();
				
			String query = "SELECT * from delivery_register WHERE delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
				
			ResultSet resultSet = ps.executeQuery();
				
			if(resultSet.next())
			{
				name = resultSet.getString("delivery_name");
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