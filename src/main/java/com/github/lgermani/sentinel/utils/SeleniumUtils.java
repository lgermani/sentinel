package com.github.lgermani.sentinel.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

/**
 * Created by lgermani on 02/01/2018.
 */
public class SeleniumUtils {

    public static WebElement waitElementBySelector(WebDriver driver, By selector) throws NoSuchElementException {

        Integer secondsToWait = 5;
        Integer retrys = 3;
        WebElement element = null;
        element  = waitElementBySelector(driver, selector, secondsToWait);
        //markElement(element);
        return element;
    }

    protected static WebElement waitElementBySelector(WebDriver driver, By selector, Integer timeToWaitInSeconds){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeToWaitInSeconds))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(selector));
    }
}
