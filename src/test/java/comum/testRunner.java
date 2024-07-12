package comum;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/feature",
				 glue= {"pageObject"},
				 monochrome=true,
				 plugin= {"pretty", "junit:target/JUnitReports/report.xml",
						  			"json:target/JSONReports/report.json",
						  			"html:terget/HtmlReports"},
				 tags="@regression"
				)

public class testRunner {
}
