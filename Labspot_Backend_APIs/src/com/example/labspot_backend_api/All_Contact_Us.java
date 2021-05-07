package com.example.labspot_backend_api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.All_Contact_Us_Bean;
import Java_DAO.All_Contact_Us_DAO;
import send_mail.Contact_Us_Alert_Mail;

@Path("labspot")
public class All_Contact_Us
{
	All_Contact_Us_Bean all_Contact_Us_Bean;
	All_Contact_Us_DAO all_Contact_Us_DAO;
	
	public String name_temp, email_temp, feedback_temp, user_type_temp;
	
	public String result,response;
	
	@Path("contact_us")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String contactUs(String json)
	{
		System.out.println("\nCONTACT US API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			name_temp = jsonObject.get("name").getAsString();
			email_temp = jsonObject.get("email").getAsString();
			feedback_temp = jsonObject.get("feedback").getAsString();
			user_type_temp = jsonObject.get("user_type").getAsString();
			
			all_Contact_Us_Bean = new All_Contact_Us_Bean(name_temp, email_temp, feedback_temp, user_type_temp);
		}
		
		all_Contact_Us_DAO = new All_Contact_Us_DAO();
		
		result = all_Contact_Us_DAO.all_Contact_Us(all_Contact_Us_Bean);
		
		System.out.println("\nReturn Message from all_Contact_Us Method : - "+result);

		if(result.equals("Feedback Submitted Successfully"))
		{
			response = Contact_Us_Alert_Mail.contactUsAlertEmail(all_Contact_Us_Bean);
			System.out.println("\n\nReturn Message from contactUsAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'return_message' : '"+result+"'}";
	}
}