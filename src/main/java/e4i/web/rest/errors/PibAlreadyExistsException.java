package e4i.web.rest.errors;

public class PibAlreadyExistsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public PibAlreadyExistsException() {
        super(ErrorConstants.PIB_ALREADY_EXISTS_TYPE, "Pib already used!", "organizationManagement", "organizationexists");
    }
}
