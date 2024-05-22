package e4i.web.rest.errors;

public class RegistrationNumberAlreadyExistsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public RegistrationNumberAlreadyExistsException() {
        super(ErrorConstants.REGISTRATION_NUMBER_ALREADY_EXISTS_TYPE, "Registration number already used!", "organizationManagement", "organizationexists");
    }
}
