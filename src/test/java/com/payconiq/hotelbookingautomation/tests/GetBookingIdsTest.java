package com.payconiq.hotelbookingautomation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.payconiq.hotelbookingautomation.models.BookingListResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetBookingIdsTest extends BaseTest {
	@Test
	@DisplayName("Get Bookings -All")
	@Tag("Regression")
	public void getAllBookingIdsTestWithoutFilterSuccess() {

		Response response = RestAssured.given(requestSpecification).when().get("/booking").then().log().all().extract()
				.response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		try {
			List<BookingListResponse> allBookings = Arrays
					.asList(objectMapper.readValue(response.getBody().asString(), BookingListResponse[].class));
			assertNotNull(allBookings);
			assertNotEquals(0, allBookings.size());
		} catch (JsonProcessingException e) {
			fail("Response body does not match with expected structure");
		}

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

	}

	@Test
	@DisplayName("Get Bookings - With FirstName and LastName filter")
	@Tag("Regression")
	public void getAllBookingIdsTestWithFirstNameAndLastNameSuccess() {

		Response response = RestAssured.given(requestSpecification).queryParam("lastname", "Brown")
				.queryParam("firstname", "Jim").when().get("/booking").then().log().all().extract().response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		try {
			List<BookingListResponse> allBookings = Arrays
					.asList(objectMapper.readValue(response.getBody().asString(), BookingListResponse[].class));
			assertNotNull(allBookings);
			assertNotEquals(0, allBookings.size());
		} catch (JsonProcessingException e) {
			fail("Response body does not match with expected structure");
		}

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

	}

	@Test
	@DisplayName("Get Bookings - With Non Existent FirstName and LastName filter values")
	@Tag("Regression")
	public void getAllBookingIdsTestWithInvalidFirstNameAndLastName() {
		Response response = RestAssured.given(requestSpecification)
				.queryParam("lastname", properties.getProperty("bookings.nonExistentLastname"))
				.queryParam("firstname", properties.getProperty("bookings.nonExistentFirstname")).when().get("/booking")
				.then().log().all().extract().response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		try {
			List<BookingListResponse> allBookings = Arrays
					.asList(objectMapper.readValue(response.getBody().asString(), BookingListResponse[].class));
			assertNotNull(allBookings);
			assertEquals(0, allBookings.size());
		} catch (JsonProcessingException e) {
			fail("Response body does not match with expected structure");
		}

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

	}

	@Test
	@DisplayName("Get Bookings with Checkin and Checkout date Filter")
	@Tag("Regression")
	public void getAllBookingIdsTestWithCheckInCheckOutSuccess() {

		Response response = RestAssured.given(requestSpecification).queryParam("checkin",  properties.getProperty("bookings.validCheckin"))
				.queryParam("checkout", properties.getProperty("bookings.validCheckout")).when().get("/booking").then().log().all().extract().response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		try {
			List<BookingListResponse> allBookings = Arrays
					.asList(objectMapper.readValue(response.getBody().asString(), BookingListResponse[].class));
			assertNotNull(allBookings);
			assertNotEquals(0, allBookings.size());
		} catch (JsonProcessingException e) {
			fail("Response body does not match with expected structure");
		}

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

	}

}
