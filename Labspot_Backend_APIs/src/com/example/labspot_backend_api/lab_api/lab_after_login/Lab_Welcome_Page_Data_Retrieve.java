package com.example.labspot_backend_api.lab_api.lab_after_login;

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

@Path("lab")
public class Lab_Welcome_Page_Data_Retrieve 
{
	String email_temp, return_json_string;
	
	String lab_name, lab_address, lab_city, lab_phone, lab_category, lab_established_year, lab_email, lab_password, lab_logo_name, lab_logo_url;
	
	@Path("fetch_data")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labFetchData(String json)
	{
		System.out.println("\nLAB FETCH DATA API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("lab_email").getAsString();
		}
		
		try
		{
			Connection connection = ConnectionProvider.getConnection();
			
			// logic: - Checking OTP of the user and if it matches with the OTP then we will change the status value from "inactive" to "active"
			
			String query = "SELECT * from lab_register WHERE lab_email = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email_temp);
			
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next())
			{
				// Return All the Data
				
				lab_name = resultSet.getString("lab_name");
				lab_address = resultSet.getString("lab_address");
				lab_city = resultSet.getString("lab_city");
				lab_phone = resultSet.getString("lab_phone");
				lab_category = resultSet.getString("lab_category");
				lab_established_year = resultSet.getString("lab_established_year");
				lab_email = resultSet.getString("lab_email");
				lab_password = resultSet.getString("lab_password");
				lab_logo_name = resultSet.getString("lab_logo_name");
				lab_logo_url = resultSet.getString("lab_logo_url");
				
				Lab_Support_Fetch_Data lab_Support_Fetch_Data = new Lab_Support_Fetch_Data(lab_name, lab_address, lab_city, lab_phone, lab_category, lab_established_year, lab_email, lab_password, lab_logo_name, lab_logo_url);
				
				Gson json_temp = new Gson();
				
				return_json_string = json_temp.toJson(lab_Support_Fetch_Data);
				
				System.out.println("\nReturn Message from labFetchData Method : - SUCCESS");
				
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