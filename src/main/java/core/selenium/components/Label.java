package core.selenium.components;

import core.selenium.base.Element;
import core.selenium.base.ImplementedBy;

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
