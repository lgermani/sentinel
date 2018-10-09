package com.github.lgermani.sentinel.core.selenium.components;

import com.github.lgermani.sentinel.core.selenium.base.Element;
import com.github.lgermani.sentinel.core.selenium.base.ImplementedBy;

/**
 * Html form label.
 */
@ImplementedBy(LabelImpl.class)
public interface Label extends Element {
    /**
     * Gets the for attribute on the label.
     *
     * @return string containing getValue of the for attribute, null if empty.
     */
    String getFor();
}
