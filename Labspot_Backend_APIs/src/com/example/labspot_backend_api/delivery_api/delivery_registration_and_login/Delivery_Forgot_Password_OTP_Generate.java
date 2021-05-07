package com.example.labspot_backend_api.delivery_api.delivery_registration_and_login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import database_connection.ConnectionProvider;
import send_mail.Delivery_Forgot_OTP_Generate_Mail;

@Path("delivery")
public class Delivery_Forgot_Password_OTP_Generate 
{

	public String email_temp, delivery_forgot_otp, result, response;
	
	@Path("forgot_password_generate_otp")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryForgotOTPGeneration(String json)
	{
		System.out.println("\nDELIVERY FORGOT PASSWORD OTP GENERATE API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("delivery_email").getAsString();
					
		}
		
		Random random = new Random();
		int number = random. nextInt(999999);
		delivery_forgot_otp = String.format("%06d", number);
		
		result = generateOtp(email_temp, delivery_forgot_otp);
			
		System.out.println("\nReturn Message from generateOtp Method is : - "+result);
		
		if(result.equals("Delivery Forgot OTP Generated"))
		{
			String name = getName(email_temp);
			
			response = Delivery_Forgot_OTP_Generate_Mail.deliveryForgotOTPGenerateEmail(email_temp, delivery_forgot_otp, name);
			
			System.out.println("\n\nReturn Message from deliveryAccountVerifyEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'delivery_return_message' : '"+result+"'}";
		
	}
	
	
	// Support method which will store new OTP in database
	public String generateOtp(String email, String otp)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT * FROM delivery_register WHERE delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setString(1, email);
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				String lab_status = resultSet.getString("delivery_status");
				
				if(lab_status.equals("active"))
				{
					String query1 = "UPDATE delivery_register set delivery_forgot_otp = ?, delivery_forgot_otp_status = ? WHERE delivery_email = ?";
					
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, otp);
					ps1.setString(2, "False");
					ps1.setString(3, email);
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Delivery Forgot OTP Generated";
					}
					else
					{
						return "Delivery Forgot OTP Not Generated";
					}
				}
				else
				{
					return "Please Verify Your Delivery Account First.";
				}
			}
			else
			{
				return "You Does Not Exist as Delivery Staff please register yourself first";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Something Went Wrong in Delivery_Forgot_OTP_Generate";
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