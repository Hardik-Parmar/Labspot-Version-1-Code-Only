package com.example.labspot_backend_api.delivery_api.delivery_after_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.delivery_Beans.delivery_after_login_Beans.Delivery_Change_Password_Bean;
import Java_DAO.delivery_DAO.delivery_after_login_DAO.Delivery_Change_Password_DAO;
import send_mail.Delivery_Change_Password_Alert_Mail;

@Path("delivery")
public class Delivery_Change_Password
{
	Delivery_Change_Password_Bean delivery_Change_Password_Bean;
	Delivery_Change_Password_DAO delivery_Change_Password_DAO;
	
	public String email_temp, name_temp;
	public String old_password_temp, new_password_temp;
	
	public String result,response;
	
	@Path("change_password")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String userChangePassword(String json)
	{
		System.out.println("\nDELIVERY CHANGE PASSWORD API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("delivery_email").getAsString();
			name_temp = jsonObject.get("delivery_name").getAsString();
			old_password_temp = jsonObject.get("delivery_old_password").getAsString();
			new_password_temp = jsonObject.get("delivery_new_password").getAsString();
			
			delivery_Change_Password_Bean = new Delivery_Change_Password_Bean(email_temp, 
					old_password_temp, new_password_temp, name_temp);
		}
		
		delivery_Change_Password_DAO = new Delivery_Change_Password_DAO();
		
		result = delivery_Change_Password_DAO.delivery_Change_Password(delivery_Change_Password_Bean);
		
		System.out.println("\nReturn Message from delivery_Change_Password Method : - "+result);

		if(result.equals("Delivery Password has been Changed"))
		{
			response = Delivery_Change_Password_Alert_Mail.deliveryPasswordChangeAlertEmail(delivery_Change_Password_Bean.getEmail(), delivery_Change_Password_Bean.getName());
			System.out.println("\n\nReturn Message from deliveryPasswordChangeAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'delivery_return_message' : '"+result+"'}";
	}
}