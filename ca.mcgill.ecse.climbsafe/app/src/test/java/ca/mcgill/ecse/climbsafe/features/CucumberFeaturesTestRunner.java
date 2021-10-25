package ca.mcgill.ecse.climbsafe.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/resources/RegisterGuide.feature")
public class CucumberFeaturesTestRunner {
}