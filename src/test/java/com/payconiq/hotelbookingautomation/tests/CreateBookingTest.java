package com.payconiq.hotelbookingautomation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.PrintWriter;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateBookingTest extends BaseTest {

	File createBookingRequestBody;

	File createBookingResponseBody;

	String bookingId;

	@BeforeAll
	void readRequestBody() {
		createBookingRequestBody = new File(
				this.getClass().getClassLoader().getResource("test-data/create-booking-request.json").getFile());
		createBookingResponseBody = new File(
				this.getClass().getClassLoader().getResource("test-data/create-booking-response.json").getFile());

	}

	@Test
	@DisplayName("Create Booking")
	@Tag("Regression")
	public void testCreateBookingSuccess() {

		Response response = RestAssured.given(requestSpecification).when().body(createBookingRequestBody)
				.post("/booking").then().log().all().extract().response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		PrintWriter file = null;
		try {
			file = new PrintWriter(createBookingResponseBody);
			file.write(response.prettyPrint());
			file.flush();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}

	@Test
	@DisplayName("Create Booking - Invalid Request Body")
    @Tag("Regression")
	public void testCreateBookingWithInvalidRequestBody() {

		Response response = RestAssured.given(requestSpecification).when().body("{}").post("/booking").then().log()
				.all().extract().response();

		assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response code is not as expected");
		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

	}
}
