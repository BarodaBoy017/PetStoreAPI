package io.swagger.cucumber;
/* 
 Created by Jaydip Patel
 */

import io.cucumber.junit.CucumberOptions;
import io.swagger.testbase.TestBase;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/java/resources/features",
        tags = "@TestAPI")
public class TestRunner extends TestBase {
}
