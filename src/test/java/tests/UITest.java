package tests;

import components.CourseBlockComponent;
import org.testng.annotations.Test;
import pages.OtusPage;

public class UITest extends BaseTest {
    @Test
    public void chooseCourseByName() {
        OtusPage otusPage = new OtusPage(driver);
        otusPage.open();
        CourseBlockComponent courseBlockComponent = new CourseBlockComponent(driver);
        courseBlockComponent.clickCourseWithName("Linux");
    }

    @Test
    public void chooseCourseByMaxDate() {
        OtusPage otusPage = new OtusPage(driver);
        otusPage.open();
        CourseBlockComponent courseBlockComponent = new CourseBlockComponent(driver);
        courseBlockComponent.clickCourseWithMaxStartDate();
    }

    @Test
    public void chooseCourseByMinDate() {
        OtusPage otusPage = new OtusPage(driver);
        otusPage.open();
        CourseBlockComponent courseBlockComponent = new CourseBlockComponent(driver);
        courseBlockComponent.clickCourseWithMinStartDate();
    }
}
