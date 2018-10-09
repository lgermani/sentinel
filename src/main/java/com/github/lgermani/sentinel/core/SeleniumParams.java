package com.github.lgermani.sentinel.core;

import com.github.lgermani.sentinel.core.enumerator.BrowserSessionsEnum;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;

/**
 * Created by lgermani on 02/01/2018.
 */
public class SeleniumParams {

    private long defaultWaitForTimeouts;
    private long defaultWaitPauseLength;
    private long defaultWaitPageLoadLength;
    private boolean useVerboseLogging;
    private BrowserSessionsEnum browserSessions;
    private boolean developmentEnvironment;

    //Grid info
    private boolean executeOnGrid;
    private String hubURL;

    //Desired Capabilities
    private String browser;
    private String platform;

    //Singleton implementation
    public SeleniumParams() {
        if (StringUtils.isNotBlank(System.getenv("default_wait_for_timeouts"))) {
            defaultWaitForTimeouts = Long.parseLong(System.getenv("default_wait_for_timeouts"));
        } else {
            defaultWaitForTimeouts = 2000;
        }

        if (StringUtils.isNotBlank(System.getenv("default_wait_pause_length"))) {
            defaultWaitPauseLength = Long.parseLong(System.getenv("default_wait_pause_length"));
        } else {
            defaultWaitPauseLength = 200;
        }

        if (StringUtils.isNotBlank(System.getenv("default_wait_page_load_time"))) {
            defaultWaitPageLoadLength = Long.parseLong(System.getenv("default_wait_page_load_time"));
        } else {
            defaultWaitPageLoadLength = 30000;
        }

        String useVerboseString = System.getenv("use_verbose_logging");
        if (StringUtils.isNotBlank(useVerboseString)) {
            useVerboseLogging = Boolean.parseBoolean(useVerboseString);
        } else {
            useVerboseLogging = false;
        }

        String bsValue = System.getenv("browser_sessions");
        if (StringUtils.isNotBlank(bsValue)) {
            if (StringUtils.equals(bsValue, BrowserSessionsEnum.SCENARIO.value())) {
                browserSessions = BrowserSessionsEnum.SCENARIO;
            } else {
                browserSessions = BrowserSessionsEnum.SPEC;
            }
        } else {
            browserSessions = BrowserSessionsEnum.SPEC;
        }

        String developEnvironmentParam = System.getenv("is_development_environment");
        if (StringUtils.isNotBlank(useVerboseString)) {
            developmentEnvironment = Boolean.parseBoolean(developEnvironmentParam);
        } else {
            developmentEnvironment = false;
        }

        hubURL = System.getenv("hub_url");

        String executeOnGridParam = System.getenv("execute_on_grid");
        if (StringUtils.isNotBlank(executeOnGridParam)) {
            executeOnGrid = Boolean.parseBoolean(executeOnGridParam);
        } else {
            executeOnGrid = false;
        }

        this.browser = StringUtils.isNotEmpty(System.getenv("BROWSER")) ? System.getenv("BROWSER") : BrowserType.CHROME;
        this.platform = StringUtils.isNotEmpty(System.getenv("PLATFORM")) ? System.getenv("PLATFORM") : Platform.WINDOWS.name();

    }

    private static SeleniumParams instance;

    public static SeleniumParams getInstance() {
        if (instance == null) {
            instance = new SeleniumParams();
        }
        return instance;
    }

    public long getDefaultWaitForTimeouts() {
        return this.defaultWaitForTimeouts;
    }

    public long getDefaultWaitPauseLength() {
        return defaultWaitPauseLength;
    }

    public long getDefaultWaitPageLoadLength() {
        return defaultWaitPageLoadLength;
    }

    public boolean useVerboseLogging() {
        return useVerboseLogging;
    }

    public BrowserSessionsEnum getBrowserSessions() {
        return browserSessions;
    }

    public boolean isDevelopmentEnvironment() {
        return developmentEnvironment;
    }

    public String getHubURL() { return hubURL; }

    public boolean isExecuteOnGrid() { return executeOnGrid; }

    public String getBrowser() {
        return browser;
    }

    public String getPlatform() {
        return platform;
    }
}
