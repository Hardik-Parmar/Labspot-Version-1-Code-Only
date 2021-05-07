package com.example.labspot_backend_api.test_detail_transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.Place_Test_Request_to_Lab_Bean;
import Java_DAO.test_detail_transaction_DAO.Place_Test_Request_to_Lab_DAO;
import send_mail.Place_Test_Request_to_Lab_Generate_Mail;

@Path("test_detail_transaction")
public class Place_Test_Request_to_Lab
{
	Place_Test_Request_to_Lab_Bean place_Test_Request_to_Lab_Bean;
	Place_Test_Request_to_Lab_DAO place_Test_Request_to_Lab_DAO;
	
	public String user_name_temp, user_address_temp, user_phone_temp, user_email_temp;
	public String lab_name_temp, lab_address_temp, lab_phone_temp, lab_email_temp;
	public String test_name_temp, test_description_temp, test_price_temp;
	public String date_temp;
	public String test_transaction_city_temp;
	
	public String result, response;
	
	@Path("place_test_request")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String placeTestRequestToLab(String json)
	{
		System.out.println("\nPLACE TEST REQUEST TO LAB API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			user_name_temp = jsonObject.get("user_name").getAsString();
			user_address_temp = jsonObject.get("user_address").getAsString();
			user_phone_temp = jsonObject.get("user_phone").getAsString();
			user_email_temp = jsonObject.get("user_email").getAsString();
			test_transaction_city_temp = jsonObject.get("transaction_city").getAsString();
			
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_address_temp = jsonObject.get("lab_address").getAsString();
			lab_phone_temp = jsonObject.get("lab_phone").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
			
			test_name_temp = jsonObject.get("test_name").getAsString();
			test_description_temp = jsonObject.get("test_description").getAsString();
			test_price_temp = jsonObject.get("test_price").getAsString();
			
			date_temp = jsonObject.get("date_of_test_order").getAsString();
			
			place_Test_Request_to_Lab_Bean = new Place_Test_Request_to_Lab_Bean(
					user_name_temp, user_address_temp, test_transaction_city_temp, 
					user_phone_temp, user_email_temp, lab_name_temp, lab_address_temp, 
					lab_phone_temp, lab_email_temp, test_name_temp, 
					test_description_temp, test_price_temp, date_temp);
		}
		
		place_Test_Request_to_Lab_DAO = new Place_Test_Request_to_Lab_DAO();
		
		result = place_Test_Request_to_Lab_DAO.place_Test_Request_To_Lab(place_Test_Request_to_Lab_Bean);
		
		System.out.println("\nReturn Message from place_Test_Request_To_Lab Method : - "+result);
		
		if(result.equals("Test Request Submitted Successfully"))
		{
			response = Place_Test_Request_to_Lab_Generate_Mail.plactTestRequestToLabGenerateEmail(place_Test_Request_to_Lab_Bean);
			
			System.out.println("\n\nReturn Message from plactTestRequestToLabGenerateEmail Method : - "+response);
			
			result = result +" and "+ response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}