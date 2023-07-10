package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.MainPage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class ReusableMethods {
    private static Connection connection;
    private static Statement statement;
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    //========Switching Window=====//
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    //========Hover Over=====//
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    //==========Return a list of string given a list of Web Element====////
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //========Returns the Text of the element given an element locator==//
    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //   HARD WAIT WITH THREAD.SLEEP
//   waitFor(5);  => waits for 5 second => Thread.sleep(5000)
    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //===============Explicit Wait==============//
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    public static void waitForPageToLoad(long timeout) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
        }
    }

    //======Fluent Wait====//
    public static WebElement fluentWait(final WebElement webElement, int timeout) {
        //FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver()).withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(3))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(1))////Check for the element every 1 second
                .ignoring(NoSuchMethodException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }

    /**
     * Performs double click action on an element
     *
     * @param element
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * @param element
     * @param check
     */
    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * Selects a random value from a dropdown list and returns the selected Web Element
     *
     * @param select
     * @return
     */
    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    public static void selectRandomFromDropdown(WebElement dropdown) {
        Select select = new Select(dropdown);
        List<WebElement> options = select.getOptions();
        int randomIndex = 1 + new Random().nextInt(options.size());
        try {
            // select.selectByValue(String.valueOf(randomIndex));
            select.selectByIndex(randomIndex);
        } catch (Exception e) {
            selectRandomFromDropdown(dropdown);
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     * fails if the element matching the provided locator is not found or not displayed
     *
     * @param by
     */
    public static void verifyElementDisplayed(By by) {
        try {
            assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + by);
        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     * fails if the element matching the provided locator is not found or not displayed
     *
     * @param by
     */
    public static void verifyElementNotDisplayed(By by) {
        try {
            assertFalse("Element should not be visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided WebElement is NOT displayed on page
     * fails if the element matching the WebElement is not found or not displayed
     *
     * @paramWebElement
     */
    public static void verifyElementNotDisplayed(WebElement element) {
        try {
            assertFalse("Element should not be visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies whether the element is displayed on page
     * fails if the element is not found or not displayed
     *
     * @param element
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + element);
        }
    }

    public static void login(String userName, String passWord) {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        mainPage.login.click();
        loginPage.username.sendKeys(userName);
        loginPage.password.sendKeys(passWord);
        JSMethods.clickElementByJS(loginPage.loginButton);

    }


    public static String generateSSN(int len1, int len2, String c, int length) {
        StringBuilder ssn = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ssn.append((int) (Math.random() * 10));
            if (i == len1-1 || i == len2-1) {
                ssn.append(c);
            }
        }
        return ssn.toString();
    }

    public static String generatePhoneNumber(int length, int len1, int len2, String s) {
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            phoneNumber.append((int) (Math.random() * 10));
            if (i == len1-1 || i == len2) {
                phoneNumber.append(s);
            }
        }
        return phoneNumber.toString();
    }

    public static void viceDeanlogin(String userName, String passWord) {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        mainPage.loginLink.click();
        loginPage.username.sendKeys(userName);
        loginPage.password.sendKeys(passWord);
        JSMethods.clickElementByJS(loginPage.loginButton);

    }


    public static void isStudentCreated(List<WebElement> pageTable, String fullName) {
        ReusableMethods.waitFor(1);
        List<WebElement> nameInfos = pageTable;
        String studentNumber = "", studentName = "";
        System.out.println("\n********** Table **********");       // get the data of the table
        for(WebElement w : nameInfos){
            System.out.println(w.getText());
        }
        System.out.println("***************************\n");
        boolean flag = false;
        for (WebElement w : nameInfos) {
            if (w.getText().contains(fullName)) {
                studentNumber = w.getText().split(" ")[0];
                studentName = w.getText().split(" ")[1].concat(" ").concat(w.getText().split(" ")[2]);
                System.out.println("studentName = " + studentName);
                flag = true;
                Assert.assertEquals(fullName, studentName);
            }else {
                Assert.assertNotEquals(fullName, studentName);
            }
        }
        if (flag){
            System.out.println("Student Created! Number = " + studentNumber + ", Name = " + studentName);
        } else {
            System.out.println("FullName = " + fullName + " is not created!");
        }
    }

    public static  WebElement selectGender(List<WebElement> gender){
        int rnd = (int) (Math.random() * 2);
        return gender.get(rnd);
    }

    public static StringBuilder createEmailWithLengthIs81(int length) {
        StringBuilder sb = new StringBuilder();
        sb.append("@a.a");
        String s = "a";
        s = s.repeat(length-4);
        sb.insert(0, s);
        System.out.println("Length Of Email = " + sb.length());
        return sb;
}

    public static void takeScreenShotOfPage() throws IOException {
//        1. Take screenshot
        File image = ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.FILE);

//       2. Save screenshot
//        getting the current time as string to use in teh screenshot name, previous screenshots will be kept
        String currentTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

//        Path of screenshot save folder               folder / folder    /file name
        String path = System.getProperty("user.dir")+"/test-output/Screenshots/"+currentTime+"image.png";
        FileUtils.copyFile(image,new File(path));
    }
    public static void clickWithJS(WebElement element)
    {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    public static void scrollToElement(WebElement element)
    {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://164.92.252.42:5432/school_management","select_user","43w5ijfso");
        return connection;
    }
    public static Statement getStatement() throws SQLException {
        statement = connection.createStatement();
        return statement;
    }

    public static void closeConnectionAndStatement() throws SQLException {
        connection.close();
        statement.close();
    }

}
