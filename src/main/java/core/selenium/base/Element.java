package core.selenium.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

/**
 * wraps a web element interface with extra functionality. Anything added here will be added to all descendants.
 */
@ImplementedBy(ElementImpl.class)
public interface Element extends WebElement, WrapsElement, Locatable {
    /**
     * Returns true when the inner element is ready to be used.
     *
     * @return boolean true for an initialized WebElement, or false if we were somehow passed a null WebElement.
     */
    boolean elementWired();

    /**
     *
     * @return boolean true if element is visible
     */
    boolean isVisible();

    /**
     * Convenience method to chain method calls more fluently.
     */
    public <T extends Element> T and();

    /**
     * Convenience method to chain method calls more fluently.
     */
    public <T extends Element> T then();

    public void shouldBeEnabled();
    public void shouldNotBeEnabled();

    public Element waitUntilEnabled();

    Element waitUntilVisible();

    public Wait<WebDriver> waitForCondition();

    public boolean isCurrentlyVisible();

    Wait<WebDriver> customWaitForCondition(long miliseconds, long pauseLength);

    public Element waitUntilNotVisible();

    public void shouldContainElements(String xpathOrCssSelector);

    public boolean containsElements(String xpathOrCssSelector);

    public List<Element> thenFindAll(String xpathOrCssSelector);

    public boolean isCurrentlyEnabled();

    public void shouldBeVisible();

    public void shouldBeCurrentlyVisible();

    public void shouldNotBeVisible();

    public boolean hasFocus();

    public boolean containsText(String value);

    public boolean containsOnlyText(String value);

    public boolean containsSelectOption(String value);

    public List<String> getSelectOptions();

    public void shouldContainText(String textValue);

    public void shouldContainOnlyText(String textValue);

    public void shouldContainSelectedOption(String textValue);

    public void shouldNotContainText(String textValue);

    public Element type(String value);

    public void waitUntilElementAvailable();

    public boolean isPresent();

    public Element waitUntilPresent();

    Element waitUntilNotPresent();

    public Element typeAndEnter(String value);

    public Element typeAndTab(String value);

    public Element deselectAll();

    public Element deselectByIndex(int indexValue);

    public Element deselectByVisibleText(String label);

    public Element deselectByValue(String value);

    public Element selectByVisibleText(String label);

    public String getSelectedVisibleTextValue();

    public String getTextValue();

    public Element selectByValue(String value);

    public String getSelectedValue();

    Element selectByIndex(int indexValue);

    public void shouldBePresent();

    public void shouldNotBePresent();

    public String getValue();

    public Element waitUntilClickable();

    public Element waitUntilDisabled();

    public void doubleClick();

    public Element slowType(String value);

    Element moveMouseOver();

    Element javaScriptClick();

    Element scrollToView();
}
