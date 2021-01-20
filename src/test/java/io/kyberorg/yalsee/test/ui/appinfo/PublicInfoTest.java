package io.kyberorg.yalsee.test.ui.appinfo;

import io.kyberorg.yalsee.test.ui.SelenideTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static io.kyberorg.yalsee.test.pageobjects.AppInfoPageObject.PublicInfoArea.*;
import static io.kyberorg.yalsee.test.pageobjects.VaadinPageObject.waitForVaadin;

/**
 * Checking elements of public info area with information about version.
 *
 * @since 2.7
 */
@SpringBootTest
public class PublicInfoTest extends SelenideTest {

    @BeforeEach
    public void beforeTest() {
        tuneDriverWithCapabilities();
        open("/appInfo");
        waitForVaadin();
    }

    @Test
    public void publicAreaIsVisible() {
        PUBLIC_INFO_AREA.should(exist);
        PUBLIC_INFO_AREA.shouldBe(visible);
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