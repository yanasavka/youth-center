package com.example.youthcenter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.net.URL;

public class CrossBrowserTest {
    private WebDriver driver;
    public static final String URL = "https://oauth-yana.savka.knm.2020-906bc:1c7f29e3-295b-43af-9db4-bd5964308168@ondemand.eu-central-1.saucelabs.com:443/wd/hub";
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (browser.equalsIgnoreCase("chrome")) {
            capabilities.setCapability("browserName", "chrome");
        }
//        if (browser.equalsIgnoreCase("firefox")) {
//            capabilities.setCapability("browserName", "firefox");
//        }
//        if (browser.equalsIgnoreCase("edge")) {
//            capabilities.setCapability("browserName", "MicrosoftEdge");
//        }
//        if (browser.equalsIgnoreCase("safari")) {
//            capabilities.setCapability("browserName", "safari");
//        }
        capabilities.setCapability("platformName", "Windows 10");
        capabilities.setCapability("browserVersion", "latest");
        driver = new RemoteWebDriver(new URL(URL), capabilities);
    }
    @Test
    public void testHomePage() {
        driver.get("https://localhost:8080");
        String title = driver.getTitle();
        System.out.println("Title is: " + title);
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

