package com.example.labspot_backend_api.user_api.user_after_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.user_Beans.user_after_login_Beans.User_Change_Password_Bean;
import Java_DAO.user_DAO.user_after_login_DAO.User_Change_Password_DAO;
import send_mail.User_Change_Password_Alert_Mail;

@Path("user")
public class User_Change_Password
{
	User_Change_Password_Bean user_Change_Password_Bean;
	User_Change_Password_DAO user_Change_Password_DAO;
	
	public String email_temp, name_temp;
	public String old_password_temp, new_password_temp;
	
	public String result,response;
	
	@Path("change_password")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userChangePassword(String json)
	{
		System.out.println("\nUSER CHANGE PASSWORD API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("user_email").getAsString();
			name_temp = jsonObject.get("user_name").getAsString();
			old_password_temp = jsonObject.get("user_old_password").getAsString();
			new_password_temp = jsonObject.get("user_new_password").getAsString();
			
			user_Change_Password_Bean = new User_Change_Password_Bean(email_temp, 
					old_password_temp, new_password_temp, name_temp);
		}
		
		user_Change_Password_DAO = new User_Change_Password_DAO();
		
		result = user_Change_Password_DAO.user_Change_Password(user_Change_Password_Bean);
		
		System.out.println("\nReturn Message from user_Change_Password Method : - "+result);

		if(result.equals("User Password has been Changed"))
		{
			response = User_Change_Password_Alert_Mail.userPasswordChangeAlertEmail(user_Change_Password_Bean.getEmail(), user_Change_Password_Bean.getName());
			System.out.println("\n\nReturn Message from userPasswordChangeAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'user_return_message' : '"+result+"'}";
	}
}