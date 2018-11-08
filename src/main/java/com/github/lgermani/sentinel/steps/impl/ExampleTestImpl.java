package com.github.lgermani.sentinel.steps.impl;

import com.thoughtworks.gauge.Step;
import com.github.lgermani.sentinel.core.selenium.factory.api.ElementFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import com.github.lgermani.sentinel.pages.example.GoogleHomePage;
import com.github.lgermani.sentinel.pages.example.GoogleResultPage;
import com.github.lgermani.sentinel.steps.AbstractStep;

import static org.assertj.core.api.Assertions.*;

public class ExampleTestImpl extends AbstractStep {

    final static Logger logger = LogManager.getLogger(ExampleTestImpl.class);

    @Step("Given I'm acessing google homepage")
    public void givenAcessingGoogleHomePage() {
        Assert.assertTrue("Should be at Google HomePage", googleHomePage().isAt());
    }

    @Step("When I search for <searchInput>")
    public void whenSearchFor(String searchInput) {
        googleHomePage().searchFor(searchInput);
    }

    @Step("Then should led me to result page")
    public void ThenShouldRedirectToResultPage() {
        Assert.assertTrue("Should be at Google Results Page", googleResultPage().isAt());
    }

    @Step("Then <webAdress> should be among the results")
    public void adressShouldBeAmongTheResults(String webAdress) {
        assertThat(googleResultPage().webAdressIsAmongTheResults(webAdress)).isTrue();
    }


    private GoogleHomePage googleHomePage() {
        return ElementFactory.initElements(getDriver(), GoogleHomePage.class);
    }

    private GoogleResultPage googleResultPage() {
        return ElementFactory.initElements(getDriver(), GoogleResultPage.class);
    }


    @Step("Integration Step")
    public void integrationStep() {
        System.out.println(" ---------> Integration Step <---------");
    }
}
