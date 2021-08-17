package runners;



import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		 features = "src/test/resources/functionalTests/UITest.feature",
	        glue = {"stepDefinitions"},
	        monochrome = true,
	        plugin= {"junit:Target/cucumber-reports/Cucumber.xml","html:Target/cucumber-reports/report.html"}
	        ,tags = "@FirstTest"

	)
public class UITestRunner {
	public static void main(String[] args) throws Throwable {
		
		io.cucumber.core.cli.Main.main(args);
	}

}
