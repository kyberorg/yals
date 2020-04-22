package eu.yals.constants;

/**
 * List of used HTTP response code. Used to prevent magic number issue.
 *
 * @since 2.7
 */
public final class HttpCode {

    /**
     * 200 - ok.
     */
    public static final int STATUS_200 = 200;

    /**
     * 201 - created.
     */
    public static final int STATUS_201 = 201;

    /**
     * 301 - permanent redirect.
     */
    public static final int STATUS_301 = 301;

    /**
     * 400 - bad request.
     */
    public static final int STATUS_400 = 400;

    /**
     * 404 - not found.
     */
    public static final int STATUS_404 = 404;

    /**
     * 406 - Not acceptable.
     */
    public static final int STATUS_406 = 406;

    /**
     * 409 - conflict.
     */
    public static final int STATUS_409 = 409;

    /**
     * 421 - unprocessable entry.
     */
    public static final int STATUS_421 = 421;

    /**
     * 500 - server error.
     */
    public static final int STATUS_500 = 500;

    /**
     * 503 - application is down.
     */
    public static final int STATUS_503 = 503;
}
