package com.payconiq.hotelbookingautomation.tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    public RequestSpecification requestSpecification;
    public Properties properties;
    public ObjectMapper objectMapper;

    /**
     * Load properties from config.properties file
     * Initialize Restassured RequestSpecification object
     *
     * @throws IOException
     */
    @BeforeAll
    public void setUp() throws IOException {
        FileReader fileReader = new FileReader("src/test/resources/config.properties");
        properties = new Properties();
        properties.load(fileReader);

        objectMapper = new ObjectMapper();

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(properties.getProperty("baseUrl"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    }

}