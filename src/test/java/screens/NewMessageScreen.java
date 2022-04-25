package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;
import models.Email;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class NewMessageScreen extends BaseScreen {

    private AppiumDriver driver;

    private final By TO_FIELD = MobileBy.xpath("//android.view.ViewGroup[@resource-id =\"com.google.android.gm:id/peoplekit_autocomplete_chip_group\"]/android.widget.EditText");
    private final By SUBJECT_FIELD = MobileBy.id("subject");
    private final By COMPOSE_AREA = MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\").text(\"Compose email\")");
    private final By SEND_BUTTON = MobileBy.id("send");

    public NewMessageScreen(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Create and send new Email")
    public PrimaryScreen createAndSendEmail(Email email) {
        enterAddressee(email.getAddressee());
        enterSubject(email.getSubject());
        enterMessage(email.getMessage());
        clickSendButton();
        return new PrimaryScreen(driver);
    }

    @Step("Enter email of addressee: {addressee}")
    public void enterAddressee(String addressee) {
        findWithWait(TO_FIELD).sendKeys(addressee);
        driver.getKeyboard().sendKeys(Keys.ENTER);
    }

    @Step("Enter subject: {subject}")
    public void enterSubject(String subject) {
        findWithWait(SUBJECT_FIELD).sendKeys(subject);
    }

    @Step("Enter message: {message}")
    public void enterMessage(String message) {
        findWithWait(COMPOSE_AREA).sendKeys(message);
    }

    @Step("Click Send button")
    public void clickSendButton() {
        findWithWait(SEND_BUTTON).click();
    }

    @Step("Get time of sent message")
    public String getTimeMessageForTimezone(String timezone) {
        return LocalDateTime.now(ZoneId.of(timezone)).format(DateTimeFormatter.ofPattern("h:mm a"));
    }
}
