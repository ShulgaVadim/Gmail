package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PrimaryScreen extends BaseScreen {

    private AppiumDriver driver;
    private String email = "new UiSelector().resourceId(\"com.google.android.gm:id/viewified_conversation_item_view\").textContains(\"%s\")";

    private final By COMPOSE_BUTTON = MobileBy.id("compose_button");
    private final By SENDER = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.gm:id/senders\")");
    private final By SUBJECT = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.gm:id/subject\")");
    private final By DATE = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.gm:id/date\")");
    private final By TEXT_MESSAGE = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.gm:id/snippet\")");
    private final By DELETE_BUTTON = MobileBy.id("delete");
    private final By EMAIL_FROM_ME = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.gm:id/senders\").text(\"me\")");
    private final By EMAIL_IN_QUEUE = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.gm:id/senders\").textContains(\"Queued\")");

    public PrimaryScreen(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    @Step("Tap 'Compose' button")
    public NewMessageScreen tapComposeButton() {
        findWithWait(COMPOSE_BUTTON).click();
        return new NewMessageScreen(driver);
    }

    @Step("Checking that email {subject} has been deleted")
    public boolean validateTheMessageHasBeenDeleted(String subject) {
        return driver.findElements(MobileBy.AndroidUIAutomator(String.format(email, subject))).isEmpty();
    }

    @Step("Checking that email {subject} has been received")
    public int checkTheEmailHasBeenReceived(String subject) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject))).findElements((EMAIL_FROM_ME)))).size();
    }

    @Step("Checking that email {subject} is in the queue")
    public int checkEmailQuantityInTheQueue(String subject) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject))).findElements((EMAIL_IN_QUEUE)))).size();
    }

    @Step("Delete email {subject}")
    public PrimaryScreen deleteEmail(String subject) {
        if (!driver.findElements(MobileBy.AndroidUIAutomator(String.format(email, subject))).isEmpty()) {
            MobileElement emailElement = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject)));
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withDuration(Duration.ofSeconds(2)).withElement(ElementOption.element(emailElement));
            TouchAction action = new TouchAction(driver);
            action.longPress(longPressOptions).release().perform();
            tapDeleteButton();
        }
        return this;
    }

    @Step("Get subject of the sent email")
    public String getSubjectFromEmail(String subject) {
        return driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject))).findElement(SUBJECT).getText();
    }

    @Step("Get sender matches of the sent email")
    public String getSenderFromEmail(String subject) {
        return driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject))).findElement(SENDER).getText();
    }

    @Step("Get the message of the sent email")
    public String getMessageFromEmail(String subject) {
        return driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject))).findElement(TEXT_MESSAGE).getText();
    }

    @Step("Get the time of the sent email")
    public String getTimeFromEmail(String subject) {
        return driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject))).findElement(DATE).getText();
    }

    @Step("Tap 'Delete' button")
    public PrimaryScreen tapDeleteButton() {
        findWithWait(DELETE_BUTTON).click();
        return this;
    }
}
