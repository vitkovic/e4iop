package e4i.web.rest.errors;


public class PuoMustBeUniqueException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public PuoMustBeUniqueException() {
        super(ErrorConstants.PUO_MUST_BE_UNIQUE_TYPE, "For this PUO already exists RIPO!", "ripoManagement", "puoexists");
    }
}
