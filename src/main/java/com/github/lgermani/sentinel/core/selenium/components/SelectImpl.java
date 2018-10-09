package com.github.lgermani.sentinel.core.selenium.components;

import com.github.lgermani.sentinel.core.selenium.base.ElementImpl;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Wrapper around a WebElement for the Select class in Selenium.
 */
public class SelectImpl extends ElementImpl implements Select {
    private final org.openqa.selenium.support.ui.Select innerSelect;

    /**
     * Wraps a WebElement with select functionality.
     *
     * @param element to wrap up
     */
    public SelectImpl(WebElement element) {
        super(element);
        this.innerSelect = new org.openqa.selenium.support.ui.Select(element);
    }

    /**
     * Wraps Selenium's method.
     *
     * @return boolean if this is a multiselect.
     * @see org.openqa.selenium.support.ui.Select#isMultiple()
     */
    public boolean isMultiple() {
        return innerSelect.isMultiple();
    }


    /**
     * Wraps Selenium's method.
     *
     * @return WebElement of the first selected option.
     * @see org.openqa.selenium.support.ui.Select#getFirstSelectedOption()
     */
    public WebElement getFirstSelectedOption() {
        return innerSelect.getFirstSelectedOption();
    }

    /**
     * Wraps Selenium's method.
     *
     * @return List of WebElements selected in the select
     * @see org.openqa.selenium.support.ui.Select#getAllSelectedOptions()
     */
    public List<WebElement> getAllSelectedOptions() {
        return innerSelect.getAllSelectedOptions();
    }

    /**
     * Wraps Selenium's method.
     *
     * @return list of all options in the select.
     * @see org.openqa.selenium.support.ui.Select#getOptions()
     */
    public List<WebElement> getOptions() {
        return innerSelect.getOptions();
    }

    private String escapeQuotes(String toEscape) {
        // Convert strings with both quotes and ticks into: foo'"bar ->
        // concat("foo'", '"', "bar")
        if (toEscape.indexOf("\"") > -1 && toEscape.indexOf("'") > -1) {
            boolean quoteIsLast = false;
            if (toEscape.lastIndexOf("\"") == toEscape.length() - 1) {
                quoteIsLast = true;
            }
            String[] substrings = toEscape.split("\"");

            StringBuilder quoted = new StringBuilder("concat(");
            for (int i = 0; i < substrings.length; i++) {
                quoted.append("\"").append(substrings[i]).append("\"");
                quoted.append(((i == substrings.length - 1) ? (quoteIsLast ? ", '\"')"
                        : ")")
                        : ", '\"', "));
            }
            return quoted.toString();
        }

        // Escape string with just a quote into being single quoted:
        // f"oo -> 'f"oo'
        if (toEscape.indexOf("\"") > -1) {
            return String.format("'%s'", toEscape);
        }

        // Otherwise return the quoted string
        return String.format("\"%s\"", toEscape);
    }

    private State setSelected(WebElement option) {
        if (!option.isDisplayed()) {
            return State.NOT_VISIBLE;
        }
        if (!option.isEnabled()) {
            return State.DISABLED;
        }
        if (!option.isSelected()) {
            option.click();
        }
        return State.SELECTED;
    }

    private String getLongestSubstringWithoutSpace(String s) {
        String result = "";
        StringTokenizer st = new StringTokenizer(s, " ");
        while (st.hasMoreTokens()) {
            String t = st.nextToken();
            if (t.length() > result.length()) {
                result = t;
            }
        }
        return result;
    }

    private enum State {

        NOT_FOUND, NOT_VISIBLE, DISABLED, SELECTED;

        private State recognizeNewState(State newState) {
            if (this.ordinal() < newState.ordinal()) {
                return newState;
            } else {
                return this;
            }

        }

        private void checkState(String searchCriteria) {
            switch (this) {
            case NOT_VISIBLE:
                throw new ElementNotVisibleException(
                        "You may only interact with visible elements" + searchCriteria);
            case DISABLED:
                throw new InvalidElementStateException(
                        "You may only interact with enabled elements with " + searchCriteria);
            case NOT_FOUND:
                throw new NoSuchElementException(
                        "Cannot locate option with " + searchCriteria);
            case SELECTED:
                //DO_NOTHING;
            }
        }

    }
}
