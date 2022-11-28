package com.payconiq.hotelbookingautomation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class GetBookingByIdTest extends BaseTest {
	long bookingid;

	String firstname;

	String lastname;

	long totalprice;

	String checkin;

	String checkout;

	boolean depositpaid;

	String additionalneeds;

	@BeforeAll
	void init() {
		BookingServiceUtil.createBooking();
		File createBookingResponseBody = new File(
				this.getClass().getClassLoader().getResource("test-data/create-booking-response.json").getFile());
		JsonPath createdJson = new JsonPath(createBookingResponseBody);
		bookingid = createdJson.getLong("bookingid");
		firstname = createdJson.getString("booking.firstname");
		lastname = createdJson.getString("booking.lastname");
		totalprice = createdJson.getLong("booking.totalprice");
		depositpaid = createdJson.getBoolean("booking.depositpaid");
		checkin = createdJson.getString("booking.bookingDates.checkin");
		checkout = createdJson.getString("booking.bookingDates.checkout");
		additionalneeds = createdJson.getString("booking.additionalneeds");
	}

	@Test
	@DisplayName("Get Booking By Id")
	@Tag("Regression")
	public void testGetBookingByIdSuccess() {

		Response response = RestAssured.given(requestSpecification).when().get("/booking/" + bookingid).then().log()
				.all().extract().response();

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

		assertNotNull(response.getBody());
		assertEquals(firstname, response.jsonPath().getString("firstname"));
		assertEquals(lastname, response.jsonPath().getString("lastname"));
		assertEquals(totalprice, response.jsonPath().getLong("totalprice"));
		assertEquals(depositpaid, response.jsonPath().getBoolean("depositpaid"));
		assertEquals(checkin, response.jsonPath().getString("bookingDates.checkin"));
		assertEquals(checkout, response.jsonPath().getString("bookingDates.checkout"));
		assertEquals(additionalneeds, response.jsonPath().getString("additionalneeds"));

		log.info("Response Code: {}", response.getStatusCode());
		log.info("Response Body: {}", response.getBody().asString());

		assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Response code is not as expected");

	}

}
