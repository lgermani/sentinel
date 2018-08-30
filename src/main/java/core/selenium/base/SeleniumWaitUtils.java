package core.selenium.base;

import core.webdriver.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

/**
 * Created by lgermani on 02/01/2018.
 */
public class SeleniumWaitUtils {

    public static Wait<WebDriver> customWaitForCondition(long miliseconds, long pauseLength) {
        return new FluentWait<>(WebDriverManager.getDriver(), new org.openqa.selenium.support.ui.SystemClock(), Sleeper.SYSTEM_SLEEPER)
                .withTimeout(miliseconds, TimeUnit.MILLISECONDS)
                .pollingEvery(pauseLength, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
    }
}
