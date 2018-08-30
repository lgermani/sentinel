package core.webdriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;

public class LocalWebDriverSetup {

    public static WebDriver setupLocalDriver(String browser){
        WebDriver newDriver;
        switch (browser) {
            case BrowserType.CHROME:
                newDriver = LocalWebDriverSetup.chromeSetup();
                break;
            case BrowserType.FIREFOX:
                newDriver = LocalWebDriverSetup.firefoxSetup();
                break;
            case BrowserType.EDGE:
                newDriver = LocalWebDriverSetup.edgeSetup();
                break;
            default:
                newDriver = LocalWebDriverSetup.chromeSetup();
                break;
        }
        return newDriver;
    }

    private static WebDriver chromeSetup() {
        WebDriver newDriver;
        ChromeDriverManager.getInstance().setup();

        ChromeOptions options = new ChromeOptions();
        //options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        newDriver = new ChromeDriver(options);

        return newDriver;
    }

    private static WebDriver firefoxSetup() {
        WebDriver newDriver;
        FirefoxDriverManager.getInstance().setup();
        FirefoxOptions options = new FirefoxOptions();
        //Options to run firefox headless or with trace log level
        options.addArguments("--headless");
        //options.setLogLevel(FirefoxDriverLogLevel.TRACE);
        newDriver = new FirefoxDriver(options);
        return newDriver;
    }

    private static WebDriver edgeSetup() {
        WebDriver newDriver;
        InternetExplorerDriverManager.getInstance().setup();
        newDriver = new EdgeDriver();
        return newDriver;
    }
}
