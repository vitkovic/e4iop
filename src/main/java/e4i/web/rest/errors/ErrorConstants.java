package e4i.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI REGISTRATION_NUMBER_ALREADY_EXISTS_TYPE = URI.create(PROBLEM_BASE_URL + "/registration_number-already-exists");
    public static final URI PIB_ALREADY_EXISTS_TYPE = URI.create(PROBLEM_BASE_URL + "/pib-already-exists");
    public static final URI NIO_MUST_BE_UNIQUE_TYPE = URI.create(PROBLEM_BASE_URL + "/ripo-already-exists-for-nio");
    public static final URI PUO_MUST_BE_UNIQUE_TYPE = URI.create(PROBLEM_BASE_URL + "/ripo-already-exists-for-puo");
    public static final URI NOT_AUTHORIZED = URI.create(PROBLEM_BASE_URL + "/not-authorized");
    public static final URI RI_ORGANIZATION_MUST_EXIST_TYPE = URI.create(PROBLEM_BASE_URL + "/ri-must-exist");
    public static final URI INFRASTRUCTURE_IMAGES_LIMIT = URI.create(PROBLEM_BASE_URL + "/infrastructure-image-limit");
    

    private ErrorConstants() {
    }
}
