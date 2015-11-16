package cz.muni.fi.pa165.travelagency.exceptions;

/**
 *
 * @author omular
 */
public class TravelAgencyServiceException extends RuntimeException {

    public TravelAgencyServiceException() {
    }

    public TravelAgencyServiceException(String message) {
        super(message);
    }

    public TravelAgencyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelAgencyServiceException(Throwable cause) {
        super(cause);
    }

    public TravelAgencyServiceException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
