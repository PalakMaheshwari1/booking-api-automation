package com.payconiq.hotelbookingautomation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.payconiq.hotelbookingautomation.util.BookingServiceUtil;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag("Regression")
public class DeleteBookingTest  extends BaseTest{

	Long bookingid;
	
	@BeforeAll
	void init() {
		BookingServiceUtil.createBooking();
		File createBookingResponseBody = new File(this.getClass().getClassLoader()
				.getResource("test-data/create-booking-response.json").getFile());
		JsonPath createdJson = new JsonPath(createBookingResponseBody);
		bookingid = createdJson.getLong("bookingid");
	}
	
	@Test
	@Tag("Regression")
	@DisplayName("Delete Booking")
	public void testDeleteBookingSuccess() {
		Response response = RestAssured.given(requestSpecification)
				.auth().preemptive().basic(properties.getProperty("username"), properties.getProperty("password"))
				.when()
				.delete("/booking/" + bookingid).then().log().all()
				.extract().response();

		assertEquals(HttpStatus.SC_CREATED, response.getStatusCode(), "Response code is not as expected");

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}
	
	@Test
	@DisplayName("Delete Booking - Invalid Credentials")
	        @Tag("Regression")
	public void testDeleteBookingWithInvalidCredentials() {
		Response response = RestAssured.given(requestSpecification)
				.auth().preemptive().basic(properties.getProperty("username"), "somebadpassword")
				.when()
				.delete("/booking/" + bookingid).then().log().all()
				.extract().response();

		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Response code is not as expected");

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}
	
	@Test
	@DisplayName("Delete Booking - Invalid BookingId")
	@Tag("Regression")
	public void testDeleteBookingWithInvalidBookingId() {
		Response response = RestAssured.given(requestSpecification)
				.auth().preemptive().basic(properties.getProperty("username"), properties.getProperty("password"))
				.when()
				.delete("/booking/" + "randomId").then().log().all()
				.extract().response();

		assertEquals(HttpStatus.SC_METHOD_NOT_ALLOWED, response.getStatusCode(), "Response code is not as expected");

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}
	
	@Test
	@DisplayName("Delete Booking - Without Authorization")
	@Tag("Regression")
	public void testDeleteBookingWithoutAuth() {
		Response response = RestAssured.given(requestSpecification)
				.when()
				.delete("/booking/" + "bookingid").then().log().all()
				.extract().response();

		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Response code is not as expected");

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}
	



}
