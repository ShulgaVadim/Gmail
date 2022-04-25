import appiumdriver.AppiumDriverSingleton;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Description;
import models.Email;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import screens.PrimaryScreen;
import screens.NewMessageScreen;
import screens.TrashScreen;
import utils.Connection;
import utils.MyTestWatcher;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MyTestWatcher.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GmailTest {
    AndroidDriver driver;
    PrimaryScreen primaryScreen;
    NewMessageScreen newMessageScreen;
    TrashScreen trashScreen;
    Connection connection;
    Email email = new Email("Greetings", "finaltaskappium@gmail.com", "Gamarjoba!");

    @BeforeEach
    public void setUp() throws Exception {
        driver = AppiumDriverSingleton.getInstance();
        primaryScreen = new PrimaryScreen(driver);
        newMessageScreen = new NewMessageScreen(driver);
        trashScreen = new TrashScreen(driver);
        connection = new Connection(driver);
    }

    @Description("1.Validate the ability to send and delete email")
    @Test
    public void sendEmailTest() {
        primaryScreen.tapComposeButton();
        newMessageScreen.createAndSendEmail(email);
        Assertions.assertAll(
                () -> assertEquals(1, primaryScreen.checkTheEmailHasBeenReceived(email.getSubject()), "The message has not been received"),
                () -> assertEquals(email.getSubject(), primaryScreen.getSubjectFromEmail(email.getSubject()), "Email subject doesn't match the one created."),
                () -> assertEquals("me", primaryScreen.getSenderFromEmail(email.getSubject()), "Email sender doesn't match your models.Email"),
                () -> assertEquals(email.getMessage(), primaryScreen.getMessageFromEmail(email.getSubject()), "Email text doesn't match the one created."),
                () -> assertEquals(newMessageScreen.getTimeMessageForTimezone("Europe/Madrid"), primaryScreen.getTimeFromEmail(email.getSubject()), "Email date is incorrect")
        );
        primaryScreen.deleteEmail(email.getSubject());
        Assert.assertTrue("The Email has not been deleted", primaryScreen.validateTheMessageHasBeenDeleted(email.getSubject()));
        primaryScreen.goToTrash();
        Assert.assertEquals("The Email is not in the Trash Folder", 1, trashScreen.checkTheEmailIsInTrash(email.getSubject(), primaryScreen.getTimeFromEmail(email.getSubject())));
    }

    @Description("2.Verify the application’s behavior when WIFI is disabled")
    @Test
    public void sendEmailWithoutConnectionTest() {
        connection.disableInternetConnection();
        primaryScreen.tapComposeButton();
        newMessageScreen.createAndSendEmail(email);
        Assert.assertEquals("Email is not in queue", 1, primaryScreen.checkEmailQuantityInTheQueue(email.getSubject()));
        connection.enableInternetConnection();
        Assert.assertEquals("Email is still in queue", 1, primaryScreen.checkTheEmailHasBeenReceived(email.getSubject()));
    }

    @AfterEach
    public void deleteEmail() {
        driver.launchApp();
        primaryScreen.deleteEmail(email.getSubject());
    }

    @AfterAll
    public void driverTearDown() throws IOException {
        if (driver != null) {
            AppiumDriverSingleton.quit();
        }
    }
}

