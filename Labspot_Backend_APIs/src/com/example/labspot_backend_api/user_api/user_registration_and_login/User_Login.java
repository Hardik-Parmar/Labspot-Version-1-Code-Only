package com.example.labspot_backend_api.user_api.user_registration_and_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Login_Bean;
import Java_DAO.user_DAO.user_registration_and_login_DAO.User_Login_DAO;

@Path("user")
public class User_Login 
{
	User_Login_Bean user_Login_Bean;
	User_Login_DAO user_Login_DAO;
	
	String email_temp, password_temp, result;
	
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userLogin(String json)
	{
		System.out.println("\nUSER LOGIN API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("user_email_login").getAsString();
			password_temp = jsonObject.get("user_password_login").getAsString();
			
			user_Login_Bean = new User_Login_Bean(email_temp, password_temp);
			
		}
		
		user_Login_DAO = new User_Login_DAO();
		
		result = user_Login_DAO.user_login(user_Login_Bean);
		
		System.out.println("\nReturn Message from user_login Method : - "+result);
		
		return "{'user_return_message' : '"+result+"'}";
	}
}