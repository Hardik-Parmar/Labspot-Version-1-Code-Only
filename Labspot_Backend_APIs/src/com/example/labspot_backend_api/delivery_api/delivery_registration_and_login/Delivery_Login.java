package com.example.labspot_backend_api.delivery_api.delivery_registration_and_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Login_Bean;
import Java_DAO.delivery_DAO.delivery_registration_and_login_DAO.Delivery_Login_DAO;

@Path("delivery")
public class Delivery_Login 
{
	Delivery_Login_Bean delivery_Login_Bean;
	Delivery_Login_DAO delivery_Login_DAO;
	
	String email_temp, password_temp, result;
	
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryLogin(String json)
	{
		System.out.println("\nDELIVERY LOGIN API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("delivery_email_login").getAsString();
			password_temp = jsonObject.get("delivery_password_login").getAsString();
			
			delivery_Login_Bean = new Delivery_Login_Bean(email_temp, password_temp);
			
		}
		
		delivery_Login_DAO = new Delivery_Login_DAO();
		
		result = delivery_Login_DAO.delivery_login(delivery_Login_Bean);
		
		System.out.println("\nReturn Message from delivery_login Method : - "+result);
		
		return "{'delivery_return_message' : '"+result+"'}";
	}
}