package core.selenium.base;

import ch.lambdaj.function.convert.Converter;
import com.google.common.collect.Lists;
import core.SeleniumParams;
import core.selenium.components.DropdownDeselector;
import core.selenium.components.DropdownSelector;
import core.selenium.selectors.Selectors;
import core.webdriver.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ch.lambdaj.Lambda.convert;


/**
 * An implementation of the Element interface. Delegates its work to an underlying WebElement instance for
 * custom functionality.
 */
public class ElementImpl implements Element {

    final static Logger logger = LogManager.getLogger(ElementImpl.class);

    private final WebElement webElement;

    private final Clock webdriverClock;
    private final Sleeper sleeper;
    //private final long waitForTimeoutInMilliseconds;

    /**
     * Creates a Element for a given WebElement.
     *
     * @param webElement webElement to wrap up
     */
    public ElementImpl(final WebElement webElement) {
        this.webElement = webElement;
        this.webdriverClock = new SystemClock();
        this.sleeper = Sleeper.SYSTEM_SLEEPER;
    }

    @Override
    public void click() {
        waitUntilClickable();
        webElement.click();
    }

    @Override
    public void doubleClick() {
        waitUntilClickable();
        Actions action = new Actions(WebDriverManager.getDriver());
        action.doubleClick(getWrappedElement()).build().perform();
    }

    private void logClick() {
        logIfVerbose("click");
    }

    //toString nao funciona retornando locator :(
    private void logIfVerbose(String action) {
        if (useVerboseLogging()) {
            logger.debug(String.format("Selenium Action: '%s' -> %s ", action, this.toString()));
        }
    }

    private boolean useVerboseLogging() {
        return SeleniumParams.getInstance().useVerboseLogging();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        webElement.sendKeys(keysToSend);
    }

    @Override
    public Point getLocation() {
        return webElement.getLocation();
    }

    @Override
    public void submit() {
        webElement.submit();
    }

    @Override
    public String getAttribute(String name) {
        return webElement.getAttribute(name);
    }

    @Override
    public String getCssValue(String propertyName) {
        return webElement.getCssValue(propertyName);
    }

    @Override
    public Dimension getSize() {
        return webElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public List<WebElement> findElements(By by) {
        return webElement.findElements(by);
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public String getTagName() {
        return webElement.getTagName();
    }

    @Override
    public boolean isSelected() {
        return webElement.isSelected();
    }

    @Override
    public WebElement findElement(By by) {
        return webElement.findElement(by);
    }

    @Override
    public boolean isEnabled() {
        return (webElement != null) && (webElement.isEnabled());
    }

    @Override
    public void shouldBeEnabled() {
        if (!isEnabled()) {
            String errorMessage = String.format(
                    "Field '%s' should be enabled", toString());
            failWithMessage(errorMessage);
        }
    }

    @Override
    public void shouldNotBeEnabled() {
        if (isEnabled()) {
            String errorMessage = String.format(
                    "Field '%s' should not be enabled", toString());
            failWithMessage(errorMessage);
        }
    }

    @Override
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    @Override
    public void clear() {
        webElement.clear();
    }

    @Override
    public WebElement getWrappedElement() {
        return webElement;
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) webElement).getCoordinates();
    }

    @Override
    public boolean elementWired() {
        return (webElement != null);
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(target);
    }

    /**
     * Is this web webElement present and visible on the screen
     * This method will not throw an exception if the webElement is not on the screen at all.
     * If the webElement is not visible, the method will wait a bit to see if it appears later on.
     */
    @Override
    public boolean isVisible() {
        try {
            return (webElement != null) && (webElement.isDisplayed());
        } catch (ElementNotVisibleException e) {
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (StaleElementReferenceException se) {
            return false;
        }
    }

    /**
     * Convenience method to chain method calls more fluently.
     */
    @Override
    public Element and() {
        return this;
    }

    /**
     * Convenience method to chain method calls more fluently.
     */
    @Override
    public Element then() {
        return this;
    }

    private void failWithMessage(String errorMessage) {
        throw new AssertionError(errorMessage);
    }

    @Override
    public Element waitUntilEnabled() {
        try {
            waitForCondition().until(elementIsEnabled(this));
        } catch (TimeoutException timeout) {
            failWithMessage("Expected enabled, webElement was not enabled");
        }
        return this;
    }

    @Override
    public Element waitUntilVisible() {
        checkPresenceOfWebElement();
        return this;
    }

    private void checkPresenceOfWebElement() {
        try {
            waitForCondition().until(ElementExpectations.elementIsDisplayed(this));
        } catch (Throwable error) {
            if (webElement != null) {
                failWithMessage("Element is present but not visible.");
            } else {
                failWithMessage("Element is not present.");
            }
        }
    }


    @Override
    public Wait<WebDriver> waitForCondition() {
        return new FluentWait<>(WebDriverManager.getDriver(), webdriverClock, sleeper)
                .withTimeout(SeleniumParams.getInstance().getDefaultWaitForTimeouts(), TimeUnit.MILLISECONDS)
                .pollingEvery(SeleniumParams.getInstance().getDefaultWaitPauseLength(), TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
    }

    @Override
    public Wait<WebDriver> customWaitForCondition(long miliseconds, long pauselength) {
        return new FluentWait<>(WebDriverManager.getDriver(), webdriverClock, sleeper)
                .withTimeout(miliseconds, TimeUnit.MILLISECONDS)
                .pollingEvery(pauselength, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
    }

    @Override
    public Element waitUntilNotVisible() {
        try {
             waitForCondition().until(ElementExpectations.elementIsNotDisplayed(this));
        } catch (TimeoutException timeout) {
            failWithMessage("Expected hidden webElement was displayed");
        }
        return this;
    }

    @Override
    public boolean isCurrentlyVisible() {
        return isVisible();
    }


    public static ExpectedCondition<Boolean> elementIsEnabled(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                //WebElement resolvedElement = webElement.getElement();
                return ((element != null) && (!element.isEnabled()));
            }

            @Override
            public String toString() {
                return element.toString() + " to be enabled";
            }

        }.forElement(element);
    }

    @Override
    public void shouldContainElements(String xpathOrCssSelector) {
        if (!containsElements(xpathOrCssSelector)) {
            String errorMessage = String.format(
                    "Could not find contained elements %s in %s", xpathOrCssSelector, this.toString());
            failWithMessage(errorMessage);
        }
    }

    @Override
    public boolean containsElements(String xpathOrCssSelector) {
        return !thenFindAll(xpathOrCssSelector).isEmpty();
    }

    @Override
    public List<Element> thenFindAll(String xpathOrCssSelector) {
        List<WebElement> nestedElements;
        if (Selectors.isXPath(xpathOrCssSelector)) {
            nestedElements = findElements(By.xpath(xpathOrCssSelector));
        } else {
            nestedElements = findElements(By.cssSelector(xpathOrCssSelector));
        }

        return elementsFrom(nestedElements);
    }

    private List<Element> elementsFrom(List<WebElement> nestedElements) {
        List<Element> results = Lists.newArrayList();
        for (WebElement element : nestedElements) {
            results.add(wrapWebElement(element));
        }
        return results;
    }

    public static <T extends Element> T wrapWebElement(final WebElement element) {
        return (T) new ElementImpl(element);
    }

    @Override
    public boolean isCurrentlyEnabled() {
        try {
            return webElement.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        } catch (StaleElementReferenceException se) {
            return false;
        }
    }

    @Override
    public void shouldBeVisible() {
        if (!isVisible()) {
            failWithMessage("Element should be visible");
        }
    }

    @Override
    public void shouldBeCurrentlyVisible() {
        if (!isCurrentlyVisible()) {
            failWithMessage("Element should be visible");
        }
    }

    @Override
    public void shouldNotBeVisible() {
        if (isCurrentlyVisible()) {
            failWithMessage("Element should not be visible");
        }
    }

    @Override
    public boolean hasFocus() {
        return webElement.equals(WebDriverManager.getDriver().switchTo().activeElement());
    }

    @Override
    public boolean containsText(final String value) {
        return ((webElement != null) && (webElement.getText().contains(value)));
    }

    @Override
    public boolean containsOnlyText(final String value) {
        return ((webElement != null) && (webElement.getText().equals(value)));
    }

    @Override
    public boolean containsSelectOption(final String value) {
        return getSelectOptions().contains(value);
    }

    @Override
    public List<String> getSelectOptions() {
        List<WebElement> results = Collections.emptyList();
        if (webElement != null) {
            results = findElements(By.tagName("option"));
        }
        return convert(results, new ExtractText());
    }

    static class ExtractText implements Converter<WebElement, String> {
        public String convert(WebElement from) {
            return from.getText();
        }
    }

    @Override
    public void shouldContainText(String textValue) {
        if (!containsText(textValue)) {
            String errorMessage = String.format(
                    "The text '%s' was not found in the web webElement. Element text '%s'.", textValue, webElement.getText());
            failWithMessage(errorMessage);
        }
    }

    @Override
    public void shouldContainOnlyText(String textValue) {
        if (!containsOnlyText(textValue)) {
            String errorMessage = String.format(
                    "The text '%s' does not match the elements text '%s'.", textValue, webElement.getText());
            failWithMessage(errorMessage);
        }
    }

    @Override
    public void shouldContainSelectedOption(String textValue) {
        if (!containsSelectOption(textValue)) {
            String errorMessage = String.format(
                    "The list webElement '%s' was not found in the web webElement", textValue);
            failWithMessage(errorMessage);
        }
    }

    @Override
    public void shouldNotContainText(String textValue) {
        if (containsText(textValue)) {
            String errorMessage = String.format(
                    "The text '%s' was found in the web webElement when it should not have. Element text '%s'.", textValue, webElement.getText());
            failWithMessage(errorMessage);
        }
    }

    @Override
    public Element type(final String value) {
        waitUntilElementAvailable();
        clear();
        webElement.sendKeys(value);
        return this;
    }

    @Override
    public void waitUntilElementAvailable() {
        waitUntilPresent();
        waitUntilVisible();
    }

    @Override
    public boolean isPresent() {
        try {
            WebElement element = getWrappedElement();

            if (element == null) {
                return false;
            }
            element.isDisplayed();
            return true;
        } catch (ElementNotVisibleException e) {
            return true;
        }catch(NoSuchElementException | StaleElementReferenceException e){
            return false;
        }
    }

    @Override
    public Element waitUntilPresent() {
        try {
            waitForCondition().until(ElementExpectations.elementIsPresent(this));
        } catch (TimeoutException timeout) {
            failWithMessage("Element not present within timeout limit.");
        }
        return this;
    }

    @Override
    public Element waitUntilNotPresent() {
        try {
            waitForCondition().until(ElementExpectations.elementIsNotPresent(this));
        } catch (TimeoutException timeout) {
            failWithMessage("Element is present, expected not present within timeout limit.");
        }
        return this;
    }

    @Override
    public Element typeAndEnter(final String value) {
        waitUntilElementAvailable();
        clear();
        webElement.sendKeys(value, Keys.ENTER);
        return this;
    }

    @Override
    public Element typeAndTab(final String value) {
        waitUntilElementAvailable();
        clear();
        webElement.sendKeys(value);
        webElement.sendKeys(Keys.TAB);

        try {
            webdriverClock.wait(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    private DropdownSelector select() {
        return new DropdownSelector(this);
    }

    private DropdownDeselector deselect() {
        return new DropdownDeselector(this);
    }

    @Override
    public Element deselectAll() {
        return deselect().all();
    }

    @Override
    public Element deselectByIndex(int indexValue) {
        return deselect().byIndex(indexValue);
    }

    @Override
    public Element deselectByVisibleText(String label) {
        return deselect().byVisibleText(label);
    }

    @Override
    public Element deselectByValue(String value) {
        return deselect().byValue(value);
    }

    @Override
    public Element selectByVisibleText(final String label) {
        return select().byVisibleText(label);
    }

    @Override
    public String getSelectedVisibleTextValue() {
        return select().visibleTextValue();
    }

    @Override
    public String getTextValue() {
        waitUntilPresent();

        if (!isVisible()) {
            return "";
        }

        if (!StringUtils.isEmpty(webElement.getText())) {
            return webElement.getText();
        }else if (!StringUtils.isEmpty(webElement.getAttribute("getValue"))) {
            return webElement.getAttribute("getValue");
        }
        return "";
    }

    @Override
    public Element selectByValue(String value) {
        return select().byValue(value);
    }

    @Override
    public String getSelectedValue() {
        return select().value();
    }

    @Override
    public Element selectByIndex(int indexValue) {
        return select().byIndex(indexValue);
    }


    @Override
    public void shouldBePresent() {
        if (!isPresent()) {
            failWithMessage("Field should be present");
        }
    }

    @Override
    public void shouldNotBePresent() {
        if (isPresent()) {
            failWithMessage("Field should not be present");
        }
    }

    @Override
    public String getValue() {
        checkPresenceOfWebElement();
        return webElement.getAttribute("getTld");
    }

    @Override
    public Element waitUntilClickable() {
        try {
            waitForCondition().until(ElementExpectations.elementIsClickable(this));
        } catch (TimeoutException timeout) {
            failWithMessage("Expected clickable element was not clickable");
        }
        return this;
    }

    @Override
    public Element waitUntilDisabled() {
        try {
                waitForCondition().until(ElementExpectations.elementIsNotEnabled(this));
        } catch (TimeoutException timeout) {
            failWithMessage("Expected disabled element was not disabled");
        }
        return this;
    }

    @Override
    public Element slowType(String value){
        //will type backwards due to sendkeys nature to type always at the begginig
        for (int i = 1; i <= value.length(); i++) {
            this.sendKeys(Character.toString(value.charAt(value.length() - i)));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public Element moveMouseOver(){
        Actions builder = new Actions(WebDriverManager.getDriver());
        builder.moveToElement(this.getWrappedElement()).perform();
        return this;
    }

    @Override
    public Element javaScriptClick() {
        JavascriptExecutor executor = (JavascriptExecutor) WebDriverManager.getDriver();
        executor.executeScript("arguments[0].click();", this.getWrappedElement());
        return this;
    }

    @Override
    public Element scrollToView(){
        JavascriptExecutor je = (JavascriptExecutor) WebDriverManager.getDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", this.getWrappedElement());
        return this;
    }

}
