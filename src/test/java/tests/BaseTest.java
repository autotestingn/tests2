package tests;

import enums.DriverType;
import factory.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.MarkClickListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected EventFiringWebDriver driver;

    @BeforeClass
    public static void setupWebDriverManager() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void beforeTest() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = new EventFiringWebDriver(webDriverFactory.getWebDriver(DriverType.CHROME));
        driver.register(new MarkClickListener());
        setUpDriverSession();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void setUpDriverSession() {
        driver.manage().window().maximize();
    }
}
