package pages.example;

import core.selenium.base.Element;
import core.selenium.components.TextInput;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

public class GoogleHomePage {

    @FindBy(id="lst-ib")
    private TextInput searchInput;

    @FindBy(name="btnK")
    private Element searchBtn;

    public boolean isAt(){
        return searchInput.isPresent() && searchBtn.isPresent();
    }

    public GoogleHomePage searchFor(String searchValue){
        searchInput.sendKeys(searchValue);
        searchInput.sendKeys(Keys.ENTER);
        return this;
    }

}
