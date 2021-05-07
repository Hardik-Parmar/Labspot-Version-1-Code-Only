package com.example.labspot_backend_api.user_api.user_after_login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import database_connection.ConnectionProvider;

@Path("user")
public class User_Welcome_Page_Data_Retrieve 
{
	String email_temp, return_json_string;
	
	String user_name, user_address, user_city, user_phone, user_dob, user_email, user_password, user_image_name, user_image_url;
	
	@Path("fetch_data")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userFetchData(String json)
	{
		System.out.println("\nUSER FETCH DATA API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("user_email").getAsString();
		}
		
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking OTP of the user and if it matches with the OTP then we will change the status value from "inactive" to "active"
			
			String query = "SELECT * from user_register WHERE user_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email_temp);
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// Return All the Data
				
				user_name = resultSet.getString("user_name");
				user_address = resultSet.getString("user_address");
				user_city = resultSet.getString("user_city");
				user_phone = resultSet.getString("user_phone");
				user_dob = resultSet.getString("user_dob");
				user_email = resultSet.getString("user_email");
				user_password = resultSet.getString("user_password");
				user_image_name = resultSet.getString("user_image_name");
				user_image_url = resultSet.getString("user_image_url");
				
				User_Support_Fetch_Data user_Support_Fetch_Data = new User_Support_Fetch_Data(user_name, user_address, user_city, user_phone, user_dob, user_email, user_password, user_image_name, user_image_url);
				
				Gson json_temp = new Gson();
				
				return_json_string = json_temp.toJson(user_Support_Fetch_Data);
				
				System.out.println("\nReturn Message from userFetchData Method : - SUCCESS");
				
				return return_json_string;
			}
			else
			{
				return return_json_string;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return return_json_string;
	}
}