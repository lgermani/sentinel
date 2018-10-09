package com.github.lgermani.sentinel.core.selenium.base;

import com.github.lgermani.sentinel.core.selenium.components.Select;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Created by lgermani on 02/01/2018.
 */
public class ElementExpectations {


    public static ExpectedCondition<Boolean> elementIsNotDisplayed(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return !element.isCurrentlyVisible();
            }

            @Override
            public String toString() {
                return element.toString() + " to be not displayed";
            }
        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsDisplayed(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return element.isCurrentlyVisible();
            }

            @Override
            public String toString() {
                return element.toString() + " to be displayed";
            }
        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsPresent(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return element.isPresent();
            }

            @Override
            public String toString() {
                return element.toString() + " to be present";
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsNotPresent(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return !element.isPresent();
            }

            @Override
            public String toString() {
                return element.toString() + " to be not present";
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsClickable(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                WebElement resolvedElement = element.getWrappedElement();
                return ((resolvedElement != null) && (resolvedElement.isDisplayed()) && resolvedElement.isEnabled());
            }

            @Override
            public String toString() {
                return element.toString() + " to be clickable";
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsNotEnabled(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                WebElement resolvedElement = element.getWrappedElement();
                return ((resolvedElement != null) && (!resolvedElement.isEnabled()));
            }

            @Override
            public String toString() {
                return element.toString() + " to not be enabled";
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsEnabled(final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                WebElement resolvedElement = element.getWrappedElement();
                return ((resolvedElement != null) && (resolvedElement.isEnabled()));
            }

            @Override
            public String toString() {
                return element.toString() + " to be enabled";
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementTextValueEqualsTo(final String expectedValue, final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                WebElement resolvedElement = element.getWrappedElement();
                return ((resolvedElement != null) && (StringUtils.equals(resolvedElement.getText(), expectedValue)));
            }

            @Override
            public String toString() {
                return String.format("%s to have text getValue equals to %s", element.toString(), expectedValue);
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> elementTextValueNotEqualsTo(final String expectedValue, final Element element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                WebElement resolvedElement = element.getWrappedElement();
                return ((resolvedElement != null) && (!StringUtils.equals(resolvedElement.getText(), expectedValue)));
            }

            @Override
            public String toString() {
                return String.format("%s to have text getValue equals to %s", element.toString(), expectedValue);
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> selectedValueEqualsTo(final String expectedValue, final Select element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return ((element != null) && (StringUtils.equals(element.getSelectedValue(), expectedValue)));
            }

            @Override
            public String toString() {
                return String.format("%s to have text getValue equals to %s", element.toString(), expectedValue);
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> selectedValueNotEqualsTo(final String expectedValue, final Select element) {
        return new ExpectedCondition<Boolean>() {
            private Element element;
            public ExpectedCondition<Boolean> forElement(Element element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                Element refreshedElement = new ElementImpl(StaleElementUtils.refreshElement(element));
                return ((refreshedElement != null) && (!StringUtils.equals(refreshedElement.getSelectedValue(), expectedValue)));
            }

            @Override
            public String toString() {
                return String.format("%s to have text getValue equals to %s", element.toString(), expectedValue);
            }

        }.forElement(element);
    }

    public static ExpectedCondition<Boolean> attributeToBe(final WebElement element, final String attribute, final String value) {
        return new ExpectedCondition<Boolean>() {
            private String currentValue = null;

            public Boolean apply(WebDriver driver) {
                this.currentValue = StaleElementUtils.refreshElement(element).getAttribute(attribute);
                if (this.currentValue == null || this.currentValue.isEmpty()) {
                    this.currentValue = element.getCssValue(attribute);
                }

                return value.equals(this.currentValue);
            }

            public String toString() {
                return String.format(attribute + " to be '%s'. Current " + attribute + ": '%s'", value, this.currentValue);
            }
        };
    }


}
