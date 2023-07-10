package stepdefinitions.ui.US01;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AdminPage;
import pages.Login;
import pages.MainPage;
import utilities.ConfigReader;
import utilities.Driver;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TC01_PossitiveLogin {
AdminPage adminPage = new AdminPage();
MainPage mainPage = new MainPage();
Login login = new Login();
    @Given("admin anasayfaya gider")
    public void admin_anasayfaya_gider() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }
    @When("admin login linkine tiklar")
    public void admin_login_linkine_tiklar() {
        mainPage.loginLink.click();
    }
    @Then("admin login penceresinde oldugunu dogrular")
    public void admin_login_penceresinde_oldugunu_dogrular() {
       Driver.waitForVisibility(login.loginVerify,15);
        assertTrue(login.loginVerify.isDisplayed());
    }
    @When("admin username alanina admin adini girer")
    public void admin_username_alanina_admin_adini_girer() {
       login.userName.sendKeys(ConfigReader.getProperty("username"));
    }
    @When("admin password alanina sifresini girer")
    public void admin_password_alanina_sifresini_girer() {
       login.password.sendKeys(ConfigReader.getProperty("password"));
    }
    @When("admin login butonuna tiklar")
    public void admin_login_butonuna_tiklar() {
        login.loginButton.click();
    }
    @Then("admin login oldugunu dogrular dogrular")
    public void admin_login_oldugunu_dogrular_dogrular() {
       Driver.waitForVisibility(adminPage.loginVerify,15);
        String actualUserName = adminPage.loginVerify.getText();
       assertEquals(ConfigReader.getProperty("username"),actualUserName);
    }
    @Then("admin admin yonetim sayfasinda oldugunu dogrular")
    public void admin_admin_yonetim_sayfasinda_oldugunu_dogrular() {
       assertTrue(adminPage.adminPageVerify.isDisplayed());
    }
}
