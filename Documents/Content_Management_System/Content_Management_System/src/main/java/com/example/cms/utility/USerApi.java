package com.example.cms.utility;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
public class USerApi {
	
	Info info()
	{
		return new Info().description("Content Management System").version("0.1").contact(contact());
	}
	
	Contact contact()
	{
		return new Contact().email("abc@gmail").name("Developer Lokesh").url("lokesh.com");
	}
	
	OpenAPI openApi()
	{
		return new OpenAPI().info(info());
	}
}
