package Runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/Feature/api" },
        glue = {"stepdefinition"},
        monochrome = true,
        dryRun = false,
        plugin={
                "json:build/cucumber-report/cucumber.json",
                "rerun:build/cucumber-reports/rerun.txt"
        })


public class Runner {
}
