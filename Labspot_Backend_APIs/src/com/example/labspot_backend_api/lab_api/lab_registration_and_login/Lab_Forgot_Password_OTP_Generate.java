package com.example.labspot_backend_api.lab_api.lab_registration_and_login;

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
import send_mail.Lab_Forgot_OTP_Generate_Mail;

@Path("lab")
public class Lab_Forgot_Password_OTP_Generate 
{

	public String email_temp, lab_forgot_otp, result, response;
	
	@Path("forgot_password_generate_otp")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labForgotOTPGeneration(String json)
	{
		System.out.println("\nLAB FORGOT PASSWORD OTP GENERATE API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("lab_email").getAsString();
					
		}
		
		Random random = new Random();
		int number = random. nextInt(999999);
		lab_forgot_otp = String.format("%06d", number);
		
		result = generateOtp(email_temp, lab_forgot_otp);
			
		System.out.println("\nReturn Message from generateOtp Method is : - "+result);
		
		if(result.equals("Lab Forgot OTP Generated"))
		{
			String name = getName(email_temp);
			
			response = Lab_Forgot_OTP_Generate_Mail.labForgotOTPGenerateEmail(email_temp, lab_forgot_otp, name);
			
			System.out.println("\n\nReturn Message from deliveryAccountVerifyEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'lab_return_message' : '"+result+"'}";
		
	}
	
	
	// Support method which will store new OTP in database
	public String generateOtp(String email, String otp)
	{
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			String query = "SELECT * FROM lab_register WHERE lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setString(1, email);
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				String lab_status = resultSet.getString("lab_status");
				
				if(lab_status.equals("active"))
				{
					String query1 = "UPDATE lab_register set lab_forgot_otp = ?, lab_forgot_otp_status = ? WHERE lab_email = ?";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					
					ps1.setString(1, otp);
					ps1.setString(2, "False");
					ps1.setString(3, email);
					
					int temp = ps1.executeUpdate();
					
					if(temp == 1)
					{
						return "Lab Forgot OTP Generated";
					}
					else
					{
						return "Lab Forgot OTP Not Generated";
					}
				}
				else
				{
					return "Please Verify Your Lab Account First.";
				}
			}
			else
			{
				return "You Does Not Exist as Lab please register yourself first";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Something Went Wrong in Lab_Forgot_OTP_Generate";
	}
	
	
	// Support method which will retrieve lab_name of the input email which will be used in sendMail
	public String getName(String email)
	{
		try
		{
			String name = null;
				
			Connection connection = ConnectionProvider.getConnection();
				
			String query = "SELECT * from lab_register WHERE lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
				
			ResultSet resultSet = ps.executeQuery();
				
			if(resultSet.next())
			{
				name = resultSet.getString("lab_name");
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