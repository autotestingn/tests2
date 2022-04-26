package pages;

import org.openqa.selenium.support.events.EventFiringWebDriver;

public class OtusPage extends BasePage<OtusPage> {
    private static final String PATH_NAME = "/";

    public OtusPage(EventFiringWebDriver driver) {
        super(driver, PATH_NAME);
    }
}
