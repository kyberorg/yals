package io.kyberorg.yalsee.test;

import io.kyberorg.yalsee.constants.App;

/**
 * Test Application constants, pretty same as {@link App}, but only stuff used in testing scope.
 *
 * @since 2.5
 */
public final class TestApp {
    private TestApp() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Test App Properties (those that start with -D...).
     */
    public static class Properties {
        /**
         * String with URL we are testing.
         */
        public static final String TEST_URL = "test.url";

        /**
         * String with delete token (should be provided separately to remain secret).
         */
        public static final String TEST_DELETE_TOKEN = "test.deleteToken";

        /**
         * Grid or Local.
         */
        public static final String TEST_RUN_MODE = "test.runMode";

        /**
         * String with unique value, that identifies current test run. Used for naming videos.
         */
        public static final String BUILD_NAME = "test.buildName";

        /**
         * String path to folder, where {@link Selenide} stores test reports.
         */
        public static final String REPORT_DIR = "test.reportdir";

        /**
         * String with hostname of Selenium Grid.
         */
        public static final String GRID_HOSTNAME = "grid.hostname";

        /**
         * {@link Selenide} related constants.
         */
        public static class Selenide {
            /**
             * String with browser name. Example: chrome, firefox etc.
             */
            public static final String BROWSER = "selenide.browser";

            /**
             * Selenium Grid location.
             */
            public static final String REMOTE = "selenide.remote";

            /**
             * Global page load timeout.
             */
            public static final String TIMEOUT = "selenide.timeout";
        }
    }

    /**
     * Default values.
     */
    public static class Defaults {
        /**
         * String with default build name (myHost-210203-1826).
         */
        public static final String BUILD_NAME = TestUtils.hostName() + "-" + TestUtils.timeStamp();

        /**
         * {@link Selenide}-related defaults.
         */
        public static class Selenide {
            /**
             * Report dir.
             */
            public static final String REPORT_DIR = "target/reports";

            /**
             * Timeout in milliseconds.
             */
            public static final String TIMEOUT = "4000";

            /**
             * Chrome browser.
             */
            public static final String BROWSER = "chrome";
        }
    }

    /**
     * Who runs tests.
     */
    public enum RunMode {
        GRID,
        LOCAL
    }

}
