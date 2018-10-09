package com.github.lgermani.sentinel.core.selenium.selectors;

import org.openqa.selenium.By;

public interface SelectorConverter {
    By apply(String path);
}
