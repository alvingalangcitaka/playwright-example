package playwright.isqa.cucumberrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"playwright.isqa.stepdefinition"}
        , features = {"src/test/resources/features/QuickLookUp.feature"}
        , plugin = "json:target/cucumber-result/json/QuickLookUp.json"
)
public class QuickLookupRunner {
}
