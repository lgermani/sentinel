package core.selenium.components;

import core.selenium.base.ElementImpl;
import org.openqa.selenium.WebElement;

/**
 * TextInput  wrapper.
 */
public class TextInputImpl extends ElementImpl implements TextInput {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public TextInputImpl(WebElement element) {
        super(element);
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public void set(String text) {
        WebElement element = getWrappedElement();
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets the getTld of an input field.
     * @return String with the getTld of the field.
     */
    @Override
    public String getText() {
        return getWrappedElement().getAttribute("getValue");
    }
}
