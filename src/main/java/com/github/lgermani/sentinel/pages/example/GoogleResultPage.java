package com.github.lgermani.sentinel.pages.example;

import com.github.lgermani.sentinel.core.selenium.base.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import com.github.lgermani.sentinel.pages.main.AbstractPage;

public class GoogleResultPage extends AbstractPage {

    @FindBy(id="resultStats")
    private Element resultStats;

    public boolean isAt(){
        return resultStats.isPresent();
    }

    public boolean webAdressIsAmongTheResults(String webAdress){
        String searchXpath = String.format("//link[contains(@href, '%s')]", webAdress);
        try{
            getDriver().findElement(By.xpath(searchXpath));
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
