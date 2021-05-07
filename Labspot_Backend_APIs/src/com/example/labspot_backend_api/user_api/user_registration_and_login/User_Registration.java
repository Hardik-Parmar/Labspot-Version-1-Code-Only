package com.example.labspot_backend_api.user_api.user_registration_and_login;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.user_Beans.user_registration_and_login_Beans.User_Registration_Bean;
import Java_DAO.user_DAO.user_registration_and_login_DAO.User_Registration_DAO;
import send_mail.User_Account_Verify_Mail;

@Path("user")
public class User_Registration 
{
	User_Registration_Bean user_Registration_Bean;
	User_Registration_DAO user_Registration_DAO;
	
	public String name_temp, address_temp, city_temp, phone_temp, DOB_temp, email_temp;
	public String password_temp, image_name_temp, image_url_temp;
	
	public String user_otp, result,response;
	
	@Path("register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userRegister(String json)
	{
		System.out.println("\nUSER REGISTRATION API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			name_temp = jsonObject.get("user_name").getAsString();
			address_temp = jsonObject.get("user_address").getAsString();
			city_temp = jsonObject.get("user_city").getAsString();
			phone_temp = jsonObject.get("user_phone").getAsString();
			DOB_temp = jsonObject.get("user_DOB").getAsString();
			email_temp = jsonObject.get("user_email").getAsString();
			password_temp = jsonObject.get("user_password").getAsString();
			image_name_temp = jsonObject.get("user_image_name").getAsString();
			image_url_temp = jsonObject.get("user_image_url").getAsString();
			
			user_Registration_Bean = new User_Registration_Bean(name_temp, 
					address_temp, city_temp, phone_temp, DOB_temp, email_temp, 
					password_temp, image_name_temp, image_url_temp);
		}
		
		user_Registration_DAO = new User_Registration_DAO();
		
		Random random = new Random();
		int number = random. nextInt(999999);
		user_otp = String.format("%06d", number);
		
		result = user_Registration_DAO.user_Register(user_Registration_Bean, user_otp);
		
		System.out.println("\nReturn Message from user_Register Method : - "+result);

		if(result.equals("User Register Success"))
		{
			response = User_Account_Verify_Mail.userAccountVerifyEmail(user_Registration_Bean.getEmail(), user_otp, user_Registration_Bean.getName());
			System.out.println("\n\nReturn Message from userAccountVerifyEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'user_return_message' : '"+result+"'}";
	}
}