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
public class PartialUpdateBookingTest extends BaseTest {

	File partialUpdateBookingRequestBody;

	File partialUpdateBookingResponseBody;

	long bookingid;

	@BeforeAll
	void readRequestBody() {
		BookingServiceUtil.createBooking();
		File createBookingResponseBody = new File(this.getClass().getClassLoader()
				.getResource("test-data/create-booking-response.json").getFile());
		JsonPath createdJson = new JsonPath(createBookingResponseBody);
		bookingid = createdJson.getLong("bookingid");
		partialUpdateBookingRequestBody = new File(this.getClass().getClassLoader()
				.getResource("test-data/partial-update-booking-request.json").getFile());
		partialUpdateBookingResponseBody = new File(this.getClass().getClassLoader()
				.getResource("test-data/partial-update-booking-response.json").getFile());
	}

	@Test
	@DisplayName("Partial Update Booking")
	@Tag("Regression")
	public void testUpdatePartialBookingSuccess() {
		Response response = RestAssured.given(requestSpecification).auth().preemptive()
				.basic(properties.getProperty("username"), properties.getProperty("password"))
				.when().body(partialUpdateBookingRequestBody)
				.patch("/booking/" + bookingid).then().log().all()
				.extract().response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		JsonPath expectedJson = new JsonPath(partialUpdateBookingResponseBody);

		assertEquals(expectedJson.getString("firstname"), response.jsonPath().getString("firstname"),
				"Response Body is not as expected");
		assertEquals(expectedJson.getString("lastname"), response.jsonPath().getString("lastname"),
				"Response Body is not as expected");

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}
	
	@Test
	@DisplayName("Partial Update Booking - Bad Booking Id")
	@Tag("Regression")
	public void testUpdatePartialBookingWithBadBookingId() {
		Response response = RestAssured.given(requestSpecification).auth().preemptive()
				.basic(properties.getProperty("username"), properties.getProperty("password"))
				.when().body(partialUpdateBookingRequestBody)
				.patch("/booking/" + "someRandomBookingId").then().log().all()
				.extract().response();
		
		assertEquals(HttpStatus.SC_METHOD_NOT_ALLOWED, response.getStatusCode(), "Response code is not as expected");

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}
	
	@Test
	@DisplayName("Partial Update Booking - Unauthorized")
	@Tag("Regression")
	public void testUpdatePartialBookingWhenNotAuthorized() {
		Response response = RestAssured.given(requestSpecification)
				.when().body(partialUpdateBookingRequestBody)
				.patch("/booking/" + "someRandomBookingId").then().log().all()
				.extract().response();
		
		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Response code is not as expected");

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}


}
