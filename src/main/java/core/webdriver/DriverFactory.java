package core.webdriver;

import core.SeleniumParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Documentation for bonigarcia/webdrivermanager: https://github.com/bonigarcia/webdrivermanager
 */
public class DriverFactory {

    final static Logger logger = LogManager.getLogger(DriverFactory.class);

    // Holds the WebDriver instance
    private static HashMap<Long, WebDriver> map = new HashMap<Long, WebDriver>();

    protected static WebDriver getDriverInstance() {
        WebDriver driver = map.get(Thread.currentThread().getId());
        if (driver == null) {
            throw new NullPointerException(String.format("Falha ao retornar o driver para a thread %d.", Thread.currentThread().getId()));
        }
        return driver;
    }

    // Get a new WebDriver Instance.
    // There are various implementations for this depending on browser. The required browser can be set as an environment variable.
    // Refer http://getgauge.io/documentation/user/current/managing_environments/README.html
    protected static WebDriver startDriver() {
        WebDriver newDriver;
        SeleniumParams params = SeleniumParams.getInstance();

        if(params.isExecuteOnGrid()){
            newDriver = RemoteWebDriverSetup.setupRemote(params.getHubURL(), params.getBrowser(), params.getPlatform());
        }else{
            newDriver = LocalWebDriverSetup.setupLocalDriver(params.getBrowser());
        }

        map.put(Thread.currentThread().getId(), newDriver);
        return newDriver;
    }

    public static void stopDriver() {
        WebDriver driver = getDriverInstance();
        if (driver != null) {
            try {
                driver.close();
                try {
                    driver.quit();
                } catch (Throwable t) {
                }
            } catch (Throwable t) {
            }
            logger.info(String.format("Finalizado driver para a thread %d.", Thread.currentThread().getId()));
            map.remove(Thread.currentThread().getId());
        }
    }
}
