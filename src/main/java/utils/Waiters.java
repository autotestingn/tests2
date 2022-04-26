package utils;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Waiters<T> {
    protected EventFiringWebDriver driver;
    private WebDriverWait wait;
    private static final long TIMEOUTS_IN_SECONDS = Integer.parseInt(System.getProperty("webdriver.timeoutsInSeconds.implicitlyWait"));

    public Waiters(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public T waitForCondition(ExpectedCondition condition) {
        wait = new WebDriverWait(driver, TIMEOUTS_IN_SECONDS);
        wait.until(condition);
        return (T)this;
    }
}
