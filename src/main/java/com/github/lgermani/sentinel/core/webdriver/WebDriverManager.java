package com.github.lgermani.sentinel.core.webdriver;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.BeforeSpec;
import com.github.lgermani.sentinel.core.EnviromentParams;
import com.github.lgermani.sentinel.core.SeleniumParams;
import com.github.lgermani.sentinel.core.enumerator.BrowserSessionsEnum;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by lgermani on 02/01/2018.
 */
public class WebDriverManager {

    @BeforeSpec
    public void specSetup(){
        if(loadBrowser()){
            if(isSpecRenew()){
                initializeDriver();
            }
        }
    }

    @AfterSpec
    public void specTearDown(){
        if(loadBrowser()){
            if(isSpecRenew()){
                closeDriver();
            }
        }
    }

    @BeforeScenario
    public void scenarioSetup(){
        if(loadBrowser()) {
            if (isScenarioRenew()) {
                initializeDriver();
            }
        }
    }

    @AfterScenario
    public void scenarioTearDown(){
        if(loadBrowser()) {
            if (isScenarioRenew()) {
                closeDriver();
            }
        }
    }

    public static WebDriver getDriver(){
        return DriverFactory.getDriverInstance();
    }

    protected void initializeDriver(){
        WebDriver driver = DriverFactory.startDriver();
        driver.manage().timeouts().implicitlyWait(getImplicitlyWait(), TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(getPageLoadTime(), TimeUnit.MILLISECONDS);

        //TODO verificar isso
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);

        if(isDevelopmentEnvironment()){
            driver.manage().window().setPosition(new Point(2000, 1));
        }else{
            driver.manage().window().setPosition(new Point(0, 0));
        }
        driver.manage().window().maximize();

        driver.get(getApplicationBaseURL());
    }

    protected void closeDriver(){
        DriverFactory.stopDriver();
    }

    public static void setImplicitlyTimeout(long miliseconds){
        getDriver().manage().timeouts().implicitlyWait(miliseconds, TimeUnit.MILLISECONDS);
    }

    public static void resetTimeouts() {
        double seconds = SeleniumParams.getInstance().getDefaultWaitForTimeouts() / 1000.0;
        long longSeconds = Long.parseLong(String.format("%.0f", seconds));
        getDriver().manage().timeouts().implicitlyWait(longSeconds, TimeUnit.SECONDS);
    }

    public static String getApplicationBaseURL(){
        return EnviromentParams.getInstance().getEnviromentBaseURL();
    }

    public static boolean isSpecRenew(){
        return SeleniumParams.getInstance().getBrowserSessions().equals(BrowserSessionsEnum.SPEC);
    }

    public static boolean isScenarioRenew(){
        return SeleniumParams.getInstance().getBrowserSessions().equals(BrowserSessionsEnum.SCENARIO);
    }

    public static long getImplicitlyWait(){
        return SeleniumParams.getInstance().getDefaultWaitForTimeouts();
    }

    public static long getPageLoadTime(){
        return SeleniumParams.getInstance().getDefaultWaitPageLoadLength();
    }

    public static boolean isDevelopmentEnvironment(){
        return SeleniumParams.getInstance().isDevelopmentEnvironment();
    }

    public static boolean loadBrowser(){
        return SeleniumParams.getInstance().loadBrowser();
    }

}
