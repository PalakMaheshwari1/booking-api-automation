package com.payconiq.hotelbookingautomation.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ConfigHelper {

	public static RequestSpecification getBasicRequestSpecification() {

        FileReader fileReader;
        Properties properties = new Properties();
		try {
			fileReader = new FileReader("src/test/resources/config.properties");
			properties.load(fileReader);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(properties.getProperty("baseUrl"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        return requestSpecification;
	}
	
}
