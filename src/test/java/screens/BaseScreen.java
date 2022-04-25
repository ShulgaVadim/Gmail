package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class BaseScreen {

    private AppiumDriver driver;
    WebDriverWait wait;
    private final By MENU_BUTTON = MobileBy.AccessibilityId("Open navigation drawer");

    public BaseScreen(AppiumDriver driver) {
        this.driver = driver;
    }

    private ExpectedCondition<MobileElement> elementIsDisplayed(By by) {
        return AppiumDriver -> {
            List<MobileElement> list;
            list = AppiumDriver.findElements(by);
            if (list.size() > 0 && list.get(0).isDisplayed()) {
                return list.get(0);
            } else return null;
        };
    }

    public MobileElement findWithWait(By by) {
        wait = new WebDriverWait(driver, 10);
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        return wait.until(elementIsDisplayed(by));
    }

    @Step("Click Menu Button")
    public void clickMenuButton() {
        driver.findElement(MENU_BUTTON).click();
    }

    @Step("Go to Trash Folder")
    public void goToTrash() {
        clickMenuButton();
        driver.findElement(MobileBy.AndroidUIAutomator(("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Trash\").instance(0))"))).click();
    }
}