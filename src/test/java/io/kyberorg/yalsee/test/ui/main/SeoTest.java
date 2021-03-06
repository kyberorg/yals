package io.kyberorg.yalsee.test.ui.main;

import io.kyberorg.yalsee.test.TestUtils;
import io.kyberorg.yalsee.test.TestedEnv;
import io.kyberorg.yalsee.test.pageobjects.VaadinPageObject;
import io.kyberorg.yalsee.test.ui.SelenideTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.kyberorg.yalsee.test.pageobjects.MainViewPageObject.GOOGLE_ANALYTICS_CONTROL_SPAN;

/**
 * Tests SEO (Search Engine Optimization) related stuff.
 *
 * @since 3.0.2
 */
public class SeoTest extends SelenideTest {

    /**
     * Test Setup.
     */
    @BeforeEach
    public void beforeTest() {
        tuneDriverWithCapabilities();
        open("/");
        VaadinPageObject.waitForVaadin();
    }

    /**
     * Tests if Google Analytics script and control span,
     * which goes with it are present, hidden and have correct values, if Google Analytics enabled for tested env
     * or absent if Google Analytics disabled for tested env.
     */
    @Test
    public void correctGoogleAnalyticsScriptLoadedAndHidden() {
        TestedEnv testedEnv = TestUtils.getTestedEnv();
        if (testedEnv.isGoogleAnalyticsEnabled()) {
            GOOGLE_ANALYTICS_CONTROL_SPAN.should(exist);

            GOOGLE_ANALYTICS_CONTROL_SPAN.shouldNotBe(visible);
            GOOGLE_ANALYTICS_CONTROL_SPAN
                    .shouldHave(attribute("aria-hidden", "true"));

            GOOGLE_ANALYTICS_CONTROL_SPAN
                    .shouldHave(attribute("aria-valuetext", testedEnv.getGoogleAnalyticsFileName()));
        } else {
            GOOGLE_ANALYTICS_CONTROL_SPAN.shouldNot(exist);
        }
    }

    /**
     * Checks if we have meta needed meta.
     */
    @Test
    public void hasNeededMetaTags() {
        $("meta[name='title']").should(exist);
        $("meta[name='description']").should(exist);
        $("meta[name='og:type']").should(exist);
        $("meta[name='og:url']").should(exist);
        $("meta[name='og:title']").should(exist);
        $("meta[name='og:image']").should(exist);
        $("meta[name='twitter:card']").should(exist);
        $("meta[name='twitter:url']").should(exist);
        $("meta[name='twitter:title']").should(exist);
        $("meta[name='twitter:description']").should(exist);
        $("meta[name='twitter:image']").should(exist);

    }
}
