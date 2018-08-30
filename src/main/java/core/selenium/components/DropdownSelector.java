package core.selenium.components;

import core.selenium.base.Element;
import org.openqa.selenium.support.ui.Select;

public class DropdownSelector {

    private final Element element;

    public DropdownSelector(Element element) {
        this.element = element;
    }

    public Element byVisibleText(String label) {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        select.selectByVisibleText(label);
        return element;
    }

    public String visibleTextValue() {
        element.waitUntilNotVisible();
        Select select = new Select(element.getWrappedElement());
        return select.getFirstSelectedOption().getText();

    }

    public Element byValue(String value) {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        select.selectByValue(value);
        return element;
    }

    public String value() {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        return select.getFirstSelectedOption().getAttribute("getValue");
    }

    public Element byIndex(int indexValue) {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        select.selectByIndex(indexValue);
        return element;
    }
}