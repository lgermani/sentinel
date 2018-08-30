package core.webdriver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteWebDriverSetup {

    public static WebDriver setupRemote(String hubURL, String browser, String platform){
        WebDriver driver;
        MutableCapabilities capability;

        switch (browser) {
            case BrowserType.CHROME:
                capability = chromeCapabilities();
                break;
            case BrowserType.FIREFOX:
                capability = firefoxCapabilities();
                break;
            case BrowserType.EDGE:
                capability = edgeCapabilities();
                break;
            default:
                capability = chromeCapabilities();
                break;
        }
        capability.setCapability("platform", platform);
        try {
            driver = new RemoteWebDriver(new URL(hubURL), capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return driver;
    }

    private static MutableCapabilities chromeCapabilities(){
        ChromeOptions options = new ChromeOptions();
        return options;
    }

    private static MutableCapabilities firefoxCapabilities(){
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        return options;
    }

    private static MutableCapabilities edgeCapabilities(){
        EdgeOptions options = new EdgeOptions();
        return options;
    }
}
