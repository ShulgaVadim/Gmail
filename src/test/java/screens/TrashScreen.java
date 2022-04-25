package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TrashScreen extends BaseScreen {

    private AppiumDriver driver;
    private String email = "new UiSelector().className(\"android.view.ViewGroup\").textContains(\"%s\")";
    private String timeOfEmail = "new UiSelector().resourceId(\"com.google.android.gm:id/date\").text(\"%s\")";

    public TrashScreen(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    @Step("Checking that email {subject} is in Trash Folder")
    public int checkTheEmailIsInTrash(String subject, String time) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(MobileBy.AndroidUIAutomator(String.format(email, subject))).findElements(MobileBy.AndroidUIAutomator(String.format(timeOfEmail, time))))).size();
    }
}
