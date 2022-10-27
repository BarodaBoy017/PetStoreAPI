package io.swagger.testbase;
/* 
 Created by Jaydip Patel
 */

import io.restassured.RestAssured;
import io.swagger.constants.Path;
import io.swagger.utils.PropertyReader;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.Properties;

public class TestBase {
    public static PropertyReader propertyReader ;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");

          propertyReader = new PropertyReader();
        }

    }

