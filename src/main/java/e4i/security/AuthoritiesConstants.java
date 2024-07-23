package e4i.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String ARCHIVED = "ROLE_ARCHIVED";
    
    public static final String COMPANY_ADMIN = "ROLE_COMPANY_ADMIN";
    
    public static final String COMPANY_USER = "ROLE_COMPANY_USER";
    
    public static final String CMS_ADMIN = "ROLE_CMS_ADMIN";


    private AuthoritiesConstants() {
    }
}
