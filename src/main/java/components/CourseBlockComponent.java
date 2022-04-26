package components;

import entities.CourseBlockEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import utils.Finder;
import utils.IFindDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CourseBlockComponent extends BaseComponent<CourseBlockComponent>{

    private List<CourseBlockEntity> courseBlocksEntities = new ArrayList<>();

    public CourseBlockComponent(EventFiringWebDriver driver) {
        super(driver);
    }

    String xpathForName = ".//div[contains(@class, 'lessons__new-item-title')]";

    String xpathForStartDate = ".//div[@class = 'lessons__new-item-start'] | .//div[@class = 'lessons__new-item-courses']//following-sibling::div[@class = 'lessons__new-item-time']";

    private String getCourseBlockName(WebElement courseBlock) {
        webElementShouldBeVisible(courseBlock);
        return courseBlock.findElement(By.xpath(xpathForName)).getText().trim();
    }

    private String getCourseBlockStartDate(WebElement courseBlock) {
        webElementShouldBeVisible(courseBlock);
        return courseBlock.findElement(By.xpath(xpathForStartDate)).getText().trim();
    }

    private String getCourseBlockLink(WebElement courseBlock) {
        webElementShouldBeVisible(courseBlock);
        return courseBlock.getAttribute("href").trim();
    }

    private boolean isDateWithYear(String date) {
        Pattern pattern = Pattern.compile(".*?((\\d{4})(?:\\s*года)).*");
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

    private String parseYear(String date) {
        Pattern pattern = Pattern.compile("(\\d{4})");
        Matcher matcher = pattern.matcher(date);
        return matcher.find() ? matcher.group(1) : null;
    }

    private LocalDate parseStringToDate(String date) {
        String newDate = null;
        String year;

        Pattern pattern = Pattern.compile("(\\d{1,2}\\s*[а-я]+)");
        Matcher matcher = pattern.matcher(date);

        if (matcher.find())
        {
            newDate = matcher.group(1);
        }

        year = isDateWithYear(date) ? parseYear(date) : Integer.toString(LocalDate.now().getYear());
        newDate += " " + year;

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter
                        .ofPattern("dd MMMM uuuu")
                        .withLocale(new Locale("ru"));
        return LocalDate.parse(newDate, dateTimeFormatter);
    }


    private WebElement searchCourseByName(String courseName) {
        List<WebElement> courseNames = driver.findElements((By.xpath(xpathForName)));
        IFindDate<WebElement> finder = new Finder<>(courseNames, (WebElement course) -> course.getText().contains(courseName));
        return finder.searchFirstElement().findElement(By.xpath("./../.."));
    }

    private List<WebElement> searchCoursesWithDate() {
        List<WebElement> courseStartDates = driver.findElements((By.xpath(xpathForStartDate)));
        IFindDate<WebElement>  finder = new Finder<>(courseStartDates, (WebElement courseDate) -> courseDate.getText().matches("([^В].*?\\d{1,2}\\s*[а-я]+)"));
        return finder.searchAllElements().stream().map(course -> course.findElement(By.xpath("./../.."))).collect(Collectors.toList());
    }

    private List<WebElement> searchCoursesWithoutDate() {
        List<WebElement> courseStartDates = driver.findElements((By.xpath(xpathForStartDate)));
        IFindDate<WebElement>  finder = new Finder<>(courseStartDates, (WebElement courseDate) -> courseDate.getText().matches(".*?((О дате старта будет объявлено позже)|([В]\\s*[а-я]+)).*"));
        return finder.searchAllElements().stream().map(course -> course.findElement(By.xpath("./../.."))).collect(Collectors.toList());
    }

    private void parseСourseBlocks(List<WebElement> courseBlockWebElems) {
        courseBlocksEntities.clear();

        for (WebElement courseBlockWebElem : courseBlockWebElems) {
            String name = getCourseBlockName(courseBlockWebElem);
            String startDateText = getCourseBlockStartDate(courseBlockWebElem);
            LocalDate startDate = parseStringToDate(startDateText);
            CourseBlockEntity courseBlockEntity = new CourseBlockEntity(name, startDate);
            courseBlocksEntities.add(courseBlockEntity);
        }
    }

    private WebElement searchCourseByMaxDate() {
        List<WebElement> w = searchCoursesWithDate();
        parseСourseBlocks(w);
        CourseBlockEntity courseBlock = courseBlocksEntities.stream().reduce((CourseBlockEntity firstDate, CourseBlockEntity secondDate)-> firstDate.getStartDate().isBefore(secondDate.getStartDate()) ? firstDate : secondDate).get();
        System.out.println(courseBlock.getName());
        if (courseBlock != null) {
            return searchCourseByName(courseBlock.getName());
        } else {
            return null;
        }
    }

    private WebElement searchCourseByMinDate() {
        List<WebElement> w = searchCoursesWithDate();
        parseСourseBlocks(w);
        CourseBlockEntity courseBlock = courseBlocksEntities.stream().reduce((CourseBlockEntity firstDate, CourseBlockEntity secondDate)-> firstDate.getStartDate().isAfter(secondDate.getStartDate()) ? firstDate : secondDate).get();
        System.out.println(courseBlock.getName());
        if (courseBlock != null) {
            return searchCourseByName(courseBlock.getName());
        } else {
            return null;
        }
    }

    public void clickCourseWithName(String courseName) {
        if (courseName != null) {
            customAction.click(driver, searchCourseByName(courseName)).build().perform();
        }
    }

    public void clickCourseWithMaxStartDate() {
        WebElement courseWithMaxDate = searchCourseByMaxDate();
        customAction.setAttributeForOpenLinkInNewTab(driver, getCourseBlockLink(courseWithMaxDate)).click(driver, courseWithMaxDate).build().perform();
        String oldTab = driver.getWindowHandle();
        driver.switchTo().window(oldTab);
    }

    public void clickCourseWithMinStartDate() {
        action.moveToElement(searchCourseByMinDate()).keyDown(Keys.CONTROL).sendKeys(Keys.TAB).click().build().perform();
    }
}
