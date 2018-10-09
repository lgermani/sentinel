package com.github.lgermani.sentinel.core.webdriver;

import com.thoughtworks.gauge.screenshot.ICustomScreenshotGrabber;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CustomScreenGrabber implements ICustomScreenshotGrabber {
    // Return a screenshot byte array
    public byte[] takeScreenshot() {
        WebDriver driver = DriverFactory.getDriverInstance();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}