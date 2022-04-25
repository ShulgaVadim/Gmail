package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionStateBuilder;

public class Connection {

    AndroidDriver driver;

    public Connection(AndroidDriver driver) {
        this.driver = driver;
    }

    public void disableInternetConnection() {
        driver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().withDataDisabled().build());
    }

    public void enableInternetConnection() {
        driver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().withDataEnabled().build());
    }
}
