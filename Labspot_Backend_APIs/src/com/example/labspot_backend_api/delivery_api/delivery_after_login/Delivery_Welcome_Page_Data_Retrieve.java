package com.example.labspot_backend_api.delivery_api.delivery_after_login;

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

@Path("delivery")
public class Delivery_Welcome_Page_Data_Retrieve 
{
	String email_temp, return_json_string;
	
	String delivery_name, delivery_address, delivery_city, delivery_phone, delivery_dob, delivery_email, delivery_password, delivery_image_name, delivery_image_url, delivery_available, delivery_busy;
	
	@Path("fetch_data")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryFetchData(String json)
	{
		System.out.println("\nDELIVERY FETCH DATA API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("delivery_email").getAsString();
		}
		
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking OTP of the user and if it matches with the OTP then we will change the status value from "inactive" to "active"
			
			String query = "SELECT * from delivery_register WHERE delivery_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email_temp);
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// Return All the Data
				
				delivery_name = resultSet.getString("delivery_name");
				delivery_address = resultSet.getString("delivery_address");
				delivery_city = resultSet.getString("delivery_city");
				delivery_phone = resultSet.getString("delivery_phone");
				delivery_dob = resultSet.getString("delivery_dob");
				delivery_email = resultSet.getString("delivery_email");
				delivery_password = resultSet.getString("delivery_password");
				delivery_image_name = resultSet.getString("delivery_image_name");
				delivery_image_url = resultSet.getString("delivery_image_url");
				delivery_available = resultSet.getString("delivery_available");
				delivery_busy = resultSet.getString("delivery_busy");
				
				Delivery_Support_Fetch_Data delivery_Support_Fetch_Data = new Delivery_Support_Fetch_Data(delivery_name, delivery_address, delivery_city, delivery_phone, delivery_dob, delivery_email, delivery_password, delivery_image_name, delivery_image_url, delivery_available, delivery_busy);
				
				Gson json_temp = new Gson();
				
				return_json_string = json_temp.toJson(delivery_Support_Fetch_Data);
				
				System.out.println("\nReturn Message from deliveryFetchData Method : - SUCCESS");
				
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