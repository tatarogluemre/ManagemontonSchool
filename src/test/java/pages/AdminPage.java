package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AdminPage {
    public AdminPage(){

        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//span[@class='text-white']")
    public WebElement loginVerify;

    @FindBy(xpath = "//h3[@class='fw-bold p-3 card-header']")
    public WebElement adminPageVerify;
}
