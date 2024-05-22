package e4i.web.rest.errors;

public class NioMustBeUniqueException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public NioMustBeUniqueException() {
        super(ErrorConstants.NIO_MUST_BE_UNIQUE_TYPE, "For this NIO already exists RIPO!", "ripoManagement", "nioxists");
    }
}
