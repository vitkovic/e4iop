package e4i.web.rest.errors;

public class InfrastructureImagesLimitException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public InfrastructureImagesLimitException() {
        super(ErrorConstants.INFRASTRUCTURE_IMAGES_LIMIT, "Max 3 images per infrastructure are allowed!", "researchInfrastructure", "imageLimit");
    }
}
