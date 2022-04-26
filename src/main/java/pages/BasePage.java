package pages;

import actions.CustomAction;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import utils.Waiters;

public abstract class BasePage<T> {
    protected EventFiringWebDriver driver;
    protected Waiters waiter;
    protected Actions action;
    protected CustomAction customAction;
    private String pathName;
    private static final String HOSTNAME = System.getProperty("base.url");

    public BasePage(EventFiringWebDriver driver, String pathName) {
        this.driver = driver;
        this.pathName = pathName;
        this.waiter = new Waiters(driver);
        this.action = new Actions(driver);
        this.customAction = new CustomAction(driver);
    }

    public T open() {
        driver.get(HOSTNAME + (T)this.pathName);
        return (T)this;
    }

    public T pageTitleShouldBe(String pageTitle) {
        assert(driver.getTitle().equals(pageTitle));
        return (T)this;
    }
}
