package pages;

import core.selenium.base.Element;
import core.selenium.base.ElementImpl;
import core.webdriver.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by lgermani on 02/01/2018.
 */
public abstract class AbstractPage {

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
