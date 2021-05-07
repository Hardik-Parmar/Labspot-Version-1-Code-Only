package com.example.labspot_backend_api.lab_api.lab_registration_and_login;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.lab_Beans.lab_registration_and_login_Beans.Lab_Registration_Bean;
import Java_DAO.lab_DAO.lab_registration_and_login_DAO.Lab_Registration_DAO;
import send_mail.Lab_Account_Verify_Mail;

@Path("lab")
public class Lab_Registration 
{
	Lab_Registration_Bean lab_Registration_Bean;
	Lab_Registration_DAO lab_Registration_DAO;
	
	public String name_temp, address_temp, city_temp, phone_temp, category_temp;
	public String established_year_temp, email_temp, password_temp, image_name_temp;
	public String image_url_temp;
	
	public String lab_otp, result, response;
	
	@Path("register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labRegister(String json)
	{
		System.out.println("\nLAB REGISTRATION API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			name_temp = jsonObject.get("lab_name").getAsString();
			address_temp = jsonObject.get("lab_address").getAsString();
			city_temp = jsonObject.get("lab_city").getAsString();
			phone_temp = jsonObject.get("lab_phone").getAsString();
			category_temp = jsonObject.get("lab_category").getAsString();
			established_year_temp = jsonObject.get("lab_established_year").getAsString();
			email_temp = jsonObject.get("lab_email").getAsString();
			password_temp = jsonObject.get("lab_password").getAsString();
			image_name_temp = jsonObject.get("lab_logo_name").getAsString();
			image_url_temp = jsonObject.get("lab_logo_url").getAsString();
			
			lab_Registration_Bean = new Lab_Registration_Bean(name_temp, 
					address_temp, city_temp, phone_temp, category_temp, 
					established_year_temp,email_temp, password_temp, image_name_temp, 
					image_url_temp);
		}
		
		lab_Registration_DAO = new Lab_Registration_DAO();
		
		Random random = new Random();
		int number = random. nextInt(999999);
		lab_otp = String.format("%06d", number);
		
		result = lab_Registration_DAO.lab_Register(lab_Registration_Bean, lab_otp);
		
		System.out.println("\nReturn Message from lab_Register Method : - "+result);
		
		if(result.equals("Lab Register Success"))
		{
			response = Lab_Account_Verify_Mail.labAccountVerifyEmail(lab_Registration_Bean.getEmail(), lab_otp, lab_Registration_Bean.getName());
			System.out.println("\n\nReturn Message from labAccountVerifyEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'lab_return_message' : '"+result+"'}";
	}
}