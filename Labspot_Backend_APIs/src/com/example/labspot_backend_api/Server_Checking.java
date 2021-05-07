package com.example.labspot_backend_api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("checking_server")
public class Server_Checking 
{
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String hello()
	{
		System.out.println("This is Demo Cheking of API that server is running perfectly or not");
		System.out.println("If above sentence is print on console and on browser then server is running perfectly");
		return "This is Demo Cheking of API that server is running perfectly or not\nIf above sentence is print on console and on browser then server is running perfectly";
	}
	
}