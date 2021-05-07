package com.example.labspot_backend_api.delivery_api.delivery_registration_and_login;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.delivery_Beans.delivery_registartion_and_login_Beans.Delivery_Registration_Bean;
import Java_DAO.delivery_DAO.delivery_registration_and_login_DAO.Delivery_Registration_DAO;
import send_mail.Delivery_Account_Verify_Mail;

@Path("delivery")
public class Delivery_Registration 
{
	Delivery_Registration_Bean delivery_Registration_Bean;
	Delivery_Registration_DAO delivery_Registration_DAO;
	
	public String name_temp, address_temp, city_temp, phone_temp, DOB_temp, email_temp;
	public String password_temp, image_name_temp, image_url_temp;
	
	public String delivery_otp, result, response;
	
	@Path("register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryRegister(String json)
	{
		System.out.println("\nDELIVERY REGISTRATION API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			name_temp = jsonObject.get("delivery_name").getAsString();
			address_temp = jsonObject.get("delivery_address").getAsString();
			city_temp = jsonObject.get("delivery_city").getAsString();
			phone_temp = jsonObject.get("delivery_phone").getAsString();
			DOB_temp = jsonObject.get("delivery_DOB").getAsString();
			email_temp = jsonObject.get("delivery_email").getAsString();
			password_temp = jsonObject.get("delivery_password").getAsString();
			image_name_temp = jsonObject.get("delivery_image_name").getAsString();
			image_url_temp = jsonObject.get("delivery_image_url").getAsString();
			
			delivery_Registration_Bean = new Delivery_Registration_Bean(name_temp, 
					address_temp, city_temp, phone_temp, DOB_temp, email_temp, 
					password_temp, image_name_temp, image_url_temp);
		}
		
		delivery_Registration_DAO = new Delivery_Registration_DAO();
		
		Random random = new Random();
		int number = random. nextInt(999999);
		delivery_otp = String.format("%06d", number);
		
		result = delivery_Registration_DAO.delivery_Register(delivery_Registration_Bean, delivery_otp);
		
		System.out.println("\nReturn Message from delivery_Register Method : - "+result);
		
		if(result.equals("Delivery Register Success"))
		{
			response = Delivery_Account_Verify_Mail.deliveryAccountVerifyEmail(delivery_Registration_Bean.getEmail(), delivery_otp, delivery_Registration_Bean.getName());
			System.out.println("\n\nReturn Message from deliveryAccountVerifyEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'delivery_return_message' : '"+result+"'}";
	}
}