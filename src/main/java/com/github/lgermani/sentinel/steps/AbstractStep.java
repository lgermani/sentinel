package com.github.lgermani.sentinel.steps;

import com.github.lgermani.sentinel.core.EnviromentParams;
import com.github.lgermani.sentinel.core.webdriver.WebDriverManager;
import org.openqa.selenium.WebDriver;

/**
 * Created by lgermani on 02/01/2018.
 */
public abstract class AbstractStep {

    public AbstractStep() {
    }

    protected void goToHomePage() {
        getDriver().get(getApplicationBaseURL());
    }

    protected WebDriver getDriver() {
        return WebDriverManager.getDriver();
    }

    protected String getCurrentPageTitle() {
        return getDriver().getTitle();
    }

    protected String getApplicationBaseURL() {
        return EnviromentParams.getInstance().getEnviromentBaseURL();
    }
}

