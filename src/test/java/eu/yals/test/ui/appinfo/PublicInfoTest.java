package eu.yals.test.ui.appinfo;

import com.codeborne.selenide.Condition;
import eu.yals.test.ui.SelenideTest;
import eu.yals.test.ui.pageobjects.uus.AppInfoPageObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static eu.yals.test.ui.pageobjects.uus.AppInfoPageObject.PublicInfoArea.COMMIT_LINK;
import static eu.yals.test.ui.pageobjects.uus.AppInfoPageObject.PublicInfoArea.VERSION;

/**
 * Checking elements of public info area with information about version
 *
 * @since 2.7
 */
@SpringBootTest
public class PublicInfoTest extends SelenideTest {

    @Before
    public void beforeTest() {
        open("/appInfo");
        updateTestNameHook();
    }

    @Test
    public void publicAreaHasAllRequiredElements() {

        VERSION.shouldBe(visible);
        VERSION.shouldHave(text("version"));
        VERSION.shouldHave(text("commit"));

        COMMIT_LINK.shouldBe(visible);
        COMMIT_LINK.shouldNotBe(empty);
        COMMIT_LINK.shouldHave(attribute("href"));

    }

}