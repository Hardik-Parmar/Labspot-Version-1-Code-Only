package com.example.labspot_backend_api.user_api.user_after_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.user_Beans.user_after_login_Beans.User_Change_Address_Bean;
import Java_DAO.user_DAO.user_after_login_DAO.User_Change_Address_DAO;
import send_mail.User_Change_Address_Alert_Mail;

@Path("user")
public class User_Change_Address
{
	User_Change_Address_Bean user_Change_Address_Bean;
	User_Change_Address_DAO user_Change_Address_DAO;
	
	public String email_temp, address_temp, name_temp;
	
	public String result,response;
	
	@Path("change_address")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userChangeAddress(String json)
	{
		System.out.println("\nUSER CHANGE ADDRESS API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("user_email").getAsString();
			address_temp = jsonObject.get("user_new_address").getAsString();
			name_temp = jsonObject.get("user_name").getAsString();
			
			user_Change_Address_Bean = new User_Change_Address_Bean(email_temp, address_temp, name_temp);
		}
		
		user_Change_Address_DAO = new User_Change_Address_DAO();
		
		result = user_Change_Address_DAO.user_Change_Address(user_Change_Address_Bean);
		
		System.out.println("\nReturn Message from user_Change_Address Method : - "+result);

		if(result.equals("User Address has been Changed"))
		{
			response = User_Change_Address_Alert_Mail.userAddressChangeAlertEmail(user_Change_Address_Bean.getEmail(), user_Change_Address_Bean.getName());
			System.out.println("\n\nReturn Message from userAddressChangeAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'user_return_message' : '"+result+"'}";
	}
}