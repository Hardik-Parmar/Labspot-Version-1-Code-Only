package com.example.labspot_backend_api.lab_api.lab_after_login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.lab_Beans.lab_after_login_Beans.Lab_Change_Address_Bean;
import Java_DAO.lab_DAO.lab_after_login_DAO.Lab_Change_Address_DAO;
import send_mail.Lab_Change_Address_Alert_Mail;

@Path("lab")
public class Lab_Change_Address
{
	Lab_Change_Address_Bean lab_Change_Address_Bean;
	Lab_Change_Address_DAO lab_Change_Address_DAO;
	
	public String email_temp, address_temp, name_temp;
	
	public String result,response;
	
	@Path("change_address")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String labChangeAddress(String json)
	{
		System.out.println("\nLAB CHANGE ADDRESS API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			email_temp = jsonObject.get("lab_email").getAsString();
			address_temp = jsonObject.get("lab_new_address").getAsString();
			name_temp = jsonObject.get("lab_name").getAsString();
			
			lab_Change_Address_Bean = new Lab_Change_Address_Bean(email_temp, address_temp, name_temp);
		}
		
		lab_Change_Address_DAO = new Lab_Change_Address_DAO();
		
		result = lab_Change_Address_DAO.lab_Change_Address(lab_Change_Address_Bean);
		
		System.out.println("\nReturn Message from lab_Change_Address Method : - "+result);

		if(result.equals("Lab Address has been Changed"))
		{
			response = Lab_Change_Address_Alert_Mail.labAddressChangeAlertEmail(lab_Change_Address_Bean.getEmail(), lab_Change_Address_Bean.getName());
			System.out.println("\n\nReturn Message from labAddressChangeAlertEmail Method : - "+response);
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'lab_return_message' : '"+result+"'}";
	}
}