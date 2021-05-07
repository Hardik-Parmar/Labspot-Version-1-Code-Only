package com.example.labspot_backend_api.delivery_api.delivery_registration_and_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Account_Verify_Bean;
import Java_DAO.delivery_DAO.delivery_registration_and_login_DAO.Delivery_Account_Verify_DAO;

@Path("delivery")
public class Delivery_Account_Verify 
{
	Delivery_Account_Verify_Bean delivery_Account_Verify_Bean;
	Delivery_Account_Verify_DAO delivery_Account_Verify_DAO;

	String email_temp, otp_temp, result;
	
	@Path("account_verify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryAccountVerify(String json)
	{
		System.out.println("\nDELIVERY ACCOUNT VERFICATION API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			email_temp = jsonObject.get("delivery_email").getAsString();
			otp_temp = jsonObject.get("delivery_otp").getAsString();
			
			delivery_Account_Verify_Bean = new Delivery_Account_Verify_Bean(email_temp, otp_temp);
			
		}
		
		delivery_Account_Verify_DAO = new Delivery_Account_Verify_DAO();
		
		result = delivery_Account_Verify_DAO.deliveryAccountVerify(delivery_Account_Verify_Bean);
		
		System.out.println("\nReturn Message from deliveryAccountVerify Method : - "+result);
		
		return "{'delivery_return_message' : '"+result+"'}";
	}
}