package com.github.lgermani.sentinel.core.selenium.components;

import com.github.lgermani.sentinel.core.selenium.base.Element;
import com.github.lgermani.sentinel.core.selenium.base.ImplementedBy;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Interface for a select element.
 */
@ImplementedBy(SelectImpl.class)
public interface Select extends Element {

    /**
     * Wraps Selenium's method.
     *
     * @return boolean if this is a multiselect.
     * @see org.openqa.selenium.support.ui.Select#isMultiple()
     */
    boolean isMultiple();

    /**
     * Wraps Selenium's method.
     *
     * @return WebElement of the first selected option.
     * @see org.openqa.selenium.support.ui.Select#getFirstSelectedOption()
     */
    WebElement getFirstSelectedOption();

    /**
     * Wraps Selenium's method.
     *
     * @return List of WebElements selected in the select
     * @see org.openqa.selenium.support.ui.Select#getAllSelectedOptions()
     */
    List<WebElement> getAllSelectedOptions();

    /**
     * Wraps Selenium's method.
     *
     * @return list of all options in the select.
     * @see org.openqa.selenium.support.ui.Select#getOptions()
     */
    List<WebElement> getOptions();


}
