package factory;

import enums.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverFactory {
    public WebDriver getWebDriver(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            case OPERA:
                return new OperaDriver();
            default:
                throw new RuntimeException("Incorrect BrowserName");
        }
    }
}
