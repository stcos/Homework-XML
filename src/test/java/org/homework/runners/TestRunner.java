package org.homework.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features", // Path to the feature files
        glue = {"org.homework.steps", "org.homework.hooks"},  // Path to steps and hooks
        plugin = {
                "pretty",  // Pretty print
                "html:target/cucumber-reports/cucumber-reports.html",  // HTML report
                "json:target/cucumber-reports/Cucumber.json" // JSON report
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
