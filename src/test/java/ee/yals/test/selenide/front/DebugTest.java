package ee.yals.test.selenide.front;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

public class DebugTest {
    public static BrowserWebDriverContainer chrome =
            new BrowserWebDriverContainer()
                    .withDesiredCapabilities(DesiredCapabilities.chrome())
                    .withRecordingMode(RECORD_ALL, new File("./target/"));

    @BeforeClass
    public static void setUp() {
        chrome.start();
        RemoteWebDriver driver = chrome.getWebDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @Test
    public void test() throws InterruptedException {
        Selenide.open("http://dev.yals.eu");
        $("#longUrl").setValue("https://vr.fi");
        $("#shortenIt").click();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        chrome.stop();
    }
}
