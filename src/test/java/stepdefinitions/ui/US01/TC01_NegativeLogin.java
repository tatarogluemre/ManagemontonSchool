package stepdefinitions.ui.US01;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import pages.AdminPage;
import pages.Login;
import utilities.Driver;

import static org.junit.Assert.assertTrue;

public class TC01_NegativeLogin {
    AdminPage adminPage =new AdminPage();
    Login login = new Login();
    @When("admin username alanini bos gecer")
    public void admin_username_alanini_bos_gecer() {
       login.userName.sendKeys("", Keys.TAB);
    }
    @Then("admin username alanininda required fields uyarisini gorur")
    public void admin_username_alanininda_required_fields_uyarisini_gorur() {
        Driver.waitForVisibility(login.userNameBlankVerify,15);
        assertTrue(login.userNameBlankVerify.isDisplayed());
    }
    @When("admin password alanini bos gecer")
    public void admin_password_alanini_bos_gecer() {
        login.password.sendKeys("", Keys.TAB);
    }
    @Then("admin password alanininda required fields uyarisini gorur")
    public void admin_password_alanininda_required_fields_uyarisini_gorur() {
        Driver.waitForVisibility(login.passwordBlankVerify,15);
        assertTrue(login.passwordBlankVerify.isDisplayed());
    }
    @Then("admin login olamadigini dogrular dogrular")
    public void admin_login_olamadigini_dogrular_dogrular() {
       assertTrue(login.loginVerify.isDisplayed());
    }


}
