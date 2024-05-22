package e4i.web.rest.errors;

public class ResearchOrganizationMustExistException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ResearchOrganizationMustExistException() {
        super(ErrorConstants.RI_ORGANIZATION_MUST_EXIST_TYPE, "You have to create Research Organization first!", "infrastructureManagement", "rimustexist");    }
}
