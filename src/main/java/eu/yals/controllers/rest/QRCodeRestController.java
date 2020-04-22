package eu.yals.controllers.rest;

import com.google.zxing.WriterException;
import eu.yals.Endpoint;
import eu.yals.json.EmptyJson;
import eu.yals.json.ErrorJson;
import eu.yals.json.QRCodeResponseJson;
import eu.yals.json.internal.Json;
import eu.yals.result.GetResult;
import eu.yals.services.LinkService;
import eu.yals.services.QRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static eu.yals.constants.HttpCode.*;

/**
 * Generates QR from short link.
 *
 * @since 2.6
 */
@Slf4j
@RestController
public class QRCodeRestController {
    private static final String TAG = "[" + QRCodeRestController.class.getSimpleName() + "]";

    private final QRCodeService qrCodeService;
    private final LinkService linkService;

    private HttpServletResponse response;

    /**
     * Constructor for Spring autowiring.
     *
     * @param qrService    service which handles QR codes related actions
     * @param linksService service which handles links
     */
    public QRCodeRestController(final QRCodeService qrService, final LinkService linksService) {
        this.qrCodeService = qrService;
        this.linkService = linksService;
    }

    /**
     * Endpoint for getting QR codes based on ident
     *
     * @param ident part of URL, which identifies  short link
     * @param resp  HTTP response
     * @return json with reply
     */
    @RequestMapping(method = RequestMethod.GET, value = Endpoint.Api.QR_CODE_API + "/{ident}")
    @ResponseBody
    public Json getQRCode(final @PathVariable("ident") String ident, final HttpServletResponse resp) {
        this.response = resp;

        Json testResult = testIdentExist(ident);
        if (testResult instanceof ErrorJson) {
            return testResult;
        }

        Optional<String> qrCode = getQRCode(ident, QRCodeService.DEFAULT_SIZE);
        Json result;
        if (qrCode.isPresent()) {
            resp.setStatus(STATUS_200);
            result = QRCodeResponseJson.withQRCode(qrCode.get());
        } else {
            resp.setStatus(STATUS_500);
            result = ErrorJson.createWithMessage("Failed to generate QR code. Internal error");
        }
        return result;

    }

    /**
     * Endpoint for getting QR codes based on ident and custom size. Creates squared QR codes.
     *
     * @param ident part of URL, which identifies  short link
     * @param size  number of pixels
     * @param resp  HTTP response
     * @return json with reply
     */
    @RequestMapping(method = RequestMethod.GET, value = Endpoint.Api.QR_CODE_API + "/{ident}/{size}")
    @ResponseBody
    public Json getQRCodeWithCustomSize(final @PathVariable("ident") String ident,
                                        final @PathVariable("size") int size,
                                        final HttpServletResponse resp) {
        this.response = resp;

        Json sizeTestResult = testSize(size);
        if (sizeTestResult instanceof ErrorJson) {
            return sizeTestResult;
        }

        Json identTestResult = testIdentExist(ident);
        if (identTestResult instanceof ErrorJson) {
            return identTestResult;
        }

        Optional<String> qrCode = getQRCode(ident, size);
        Json result;
        if (qrCode.isPresent()) {
            resp.setStatus(STATUS_200);
            result = QRCodeResponseJson.withQRCode(qrCode.get());
        } else {
            resp.setStatus(STATUS_500);
            result = ErrorJson.createWithMessage("Failed to generate QR code. Internal error");
        }
        return result;
    }

    private Json testIdentExist(final String ident) {
        GetResult identFromDatabase = linkService.getLink(ident);

        if (identFromDatabase instanceof GetResult.NotFound) {
            log.debug("{} 0 idents found by request: {}", TAG, ident);
            response.setStatus(STATUS_404);
            return ErrorJson.createWithMessage("No links found by this request. " +
                    "Ident should be stored before requesting QR code");
        } else if (identFromDatabase instanceof GetResult.Fail) {
            log.debug("{} Failed to query DB for ident. Error: {}",
                    TAG, ((GetResult.Fail) identFromDatabase).getErrorMessage());
            response.setStatus(STATUS_500);
            return ErrorJson.createWithMessage("Server Error: Something wrong at our side");
        } else if (identFromDatabase instanceof GetResult.DatabaseDown) {
            log.debug("DB is DOWN");
            response.setStatus(STATUS_503);
            return ErrorJson.createWithMessage("Server is unreachable. Please repeat request later");
        } else {
            return EmptyJson.create();
        }
    }

    private Json testSize(final int sizeToTest) {
        if (sizeToTest <= 0) {
            log.error("{} invalid size {}. Replying 400", TAG, sizeToTest);
            response.setStatus(STATUS_400);
            return ErrorJson.createWithMessage("Size must be positive number");
        } else {
            return EmptyJson.create();
        }
    }

    private Optional<String> getQRCode(final String ident,final int size) {
        try {
            return Optional.of(qrCodeService.getQRCodeFromIdent(ident, size));
        } catch (WriterException | IOException e) {
            log.error("{} Failed to generate QR code", TAG, e);
            return Optional.empty();
        }
    }
}
