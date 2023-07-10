package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class RemoteDriver {
    private static WebDriver remoteDriver;

    public static WebDriver getRemoteDriver(String url, String browser) throws MalformedURLException {
        if (remoteDriver == null) {
            switch (browser) {
                case "chrome":
                    remoteDriver = new RemoteWebDriver(new URL(url), new ChromeOptions());
                    break;
                case "edge":
                    remoteDriver = new RemoteWebDriver(new URL(url), new EdgeOptions());
                    break;
                case "firefox":
                    remoteDriver = new RemoteWebDriver(new URL(url), new FirefoxOptions());
                    break;
            }
            remoteDriver.manage().window().maximize();
            remoteDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return remoteDriver;
    }

    public static void closeRemoteDriver(){
        remoteDriver.close();
    }
}
