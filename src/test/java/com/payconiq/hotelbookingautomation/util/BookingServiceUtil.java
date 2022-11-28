package com.payconiq.hotelbookingautomation.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.PrintWriter;

import org.apache.http.HttpStatus;

import com.payconiq.hotelbookingautomation.helpers.ConfigHelper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookingServiceUtil {

	public static void createBooking(){
		File createBookingRequestBody = new File(
				BookingServiceUtil.class.getClassLoader().getResource("test-data/create-booking-request.json").getFile());
		File createBookingResponseBody =  new File(
				BookingServiceUtil.class.getClassLoader().getResource("test-data/create-booking-response.json").getFile());

		RequestSpecification requestSpecification = ConfigHelper.getBasicRequestSpecification();
		
		Response response = RestAssured.given(requestSpecification).when().body(createBookingRequestBody)
				.post("/booking").then().log().all().extract().response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		PrintWriter file = null;
		try {
			file = new PrintWriter(createBookingResponseBody);
			file.write( response.prettyPrint());
			file.flush();
			file.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
