package com.example.labspot_backend_api.lab_api.lab_registration_and_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Login_Bean;
import Java_DAO.lab_DAO.lab_registration_and_login_DAO.Lab_Login_DAO;

@Path("lab")
public class Lab_Login 
{
	Lab_Login_Bean lab_Login_Bean;
	Lab_Login_DAO lab_Login_DAO;
	
	String email_temp, password_temp, result;
	
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labLogin(String json)
	{
		System.out.println("\nLAB LOGIN API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("lab_email_login").getAsString();
			password_temp = jsonObject.get("lab_password_login").getAsString();
			
			lab_Login_Bean = new Lab_Login_Bean(email_temp, password_temp);
			
		}
		
		lab_Login_DAO = new Lab_Login_DAO();
		
		result = lab_Login_DAO.lab_login(lab_Login_Bean);
		
		System.out.println("\nReturn Message from lab_login Method : - "+result);
		
		return "{'lab_return_message' : '"+result+"'}";
	}
}