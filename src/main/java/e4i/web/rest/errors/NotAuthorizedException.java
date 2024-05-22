package e4i.web.rest.errors;


public class NotAuthorizedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public NotAuthorizedException() {
        super(ErrorConstants.NOT_AUTHORIZED, "You are not authorized!", "general", "authorization");
    }
}
