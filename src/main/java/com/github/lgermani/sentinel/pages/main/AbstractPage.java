package com.github.lgermani.sentinel.pages.main;

import com.github.lgermani.sentinel.core.selenium.base.Element;
import com.github.lgermani.sentinel.core.selenium.base.ElementImpl;
import com.github.lgermani.sentinel.core.webdriver.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by lgermani on 02/01/2018.
 */
public class AbstractPage {

    protected WebDriver getDriver() {
        return WebDriverManager.getDriver();
    }

    protected String getPageCurrentUrl(){
        return getDriver().getCurrentUrl();
    }

    protected String getPageTittle(){
        return getDriver().getTitle();
    }

    protected Element findElementByXpath(String locator){
        By xpath = By.xpath(locator);
        Element element = new ElementImpl(getDriver().findElement(xpath));
        return element;
    }
}
