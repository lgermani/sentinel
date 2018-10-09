package com.github.lgermani.sentinel.core.selenium.components;

import com.github.lgermani.sentinel.core.selenium.base.Element;
import com.github.lgermani.sentinel.core.selenium.base.ImplementedBy;
import org.openqa.selenium.WebElement;

/**
 * Table functionality.
 */
@ImplementedBy(TableImpl.class)
public interface Table extends Element {

	/**
     * Gets the number of rows in the table
     * @return int equal to the number of rows in the table
     */
    int getRowCount();

    /**
     * Gets the number of columns in the table
     * @return int equal to the number of rows in the table
     */
    int getColumnCount();

    /**
     * Gets the WebElement of the cell at the specified LOGICAL index
     * @param rowIdx The logical index of the row. Starts at 1.
     * @param colIdx The logical index of the column. Starts at 1.
     * @return the WebElement of the cell at the specified index
     */
    WebElement getCellAtIndex(int rowIdx, int colIdx);

    /**
     * Gets the WebElement of the cell at the specified ABSOLUTE index
     * @param rowIdx The zero based index of the row
     * @param colIdx The zero based index of the column
     * @return the WebElement of the cell at the specified index
     */
    WebElement getCellAtAbsoluteIndex(int rowIdx, int colIdx);
}
