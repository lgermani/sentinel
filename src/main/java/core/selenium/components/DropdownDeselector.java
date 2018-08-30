package core.selenium.components;

import core.selenium.base.Element;
import org.openqa.selenium.support.ui.Select;

public class DropdownDeselector {

    private final Element element;

    public DropdownDeselector(Element element) {
        this.element = element;
    }

    public Element all() {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        select.deselectAll();
        return element;
    }

    public Element byVisibleText(String label) {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        select.deselectByVisibleText(label);
        return element;
    }

    public Element byValue(String value) {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        select.deselectByValue(value);
        return element;
    }

    public Element byIndex(int indexValue) {
        element.waitUntilElementAvailable();
        Select select = new Select(element.getWrappedElement());
        select.deselectByIndex(indexValue);
        return element;
    }
}