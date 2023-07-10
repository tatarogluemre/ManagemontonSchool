package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class Login {

    public Login(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//div[@class='mb-4 fw-semibold shadow-sm bg-body-tertiary card-title h5']")
    public WebElement loginVerify;

    @FindBy(id = "username")
    public WebElement userName;

    @FindBy(id = "password")
    public WebElement password;

    @FindBy(xpath = "//button[@class='fw-semibold btn btn-primary']")
    public WebElement loginButton;

    @FindBy(xpath = "(//div[@class='invalid-feedback'])[1]")
    public WebElement userNameBlankVerify;
    @FindBy(xpath = "(//div[@class='invalid-feedback'])[2]")
    public WebElement passwordBlankVerify;

}
