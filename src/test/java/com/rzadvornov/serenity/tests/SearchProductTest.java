package org.softindustry.com.tests;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/search/search_product.feature",
        glue = "org.softindustry.com.steps")
public class SearchProductTest {

}
