package e4i.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import e4i.config.AskToExpireSessionEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing global OIDC logout.
 */
@RestController
public class LogoutResource {
   
	@Autowired
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	
	@Autowired
    private ApplicationEventPublisher publisher;
	
	private final ClientRegistration registration;
    
   

    public LogoutResource(ClientRegistrationRepository registrations) {
        this.registration = registrations.findByRegistrationId("oidc");
    }

    /**
     * {@code POST  /api/logout} : logout the current user.
     *
     * @param request the {@link HttpServletRequest}.
     * @param idToken the ID token.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and a body with a global logout URL and ID token.
     */
    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {
        String logoutUrl = this.registration.getProviderDetails()
            .getConfigurationMetadata().get("end_session_endpoint").toString();

        if (idToken == null) request.getSession().invalidate();
        
        Map<String, String> logoutDetails = new HashMap<>();
        logoutDetails.put("logoutUrl", logoutUrl);
        logoutDetails.put("idToken", idToken.getTokenValue());
    
        boolean expieredAtLeastOneSession = false;
        
        if(sessionRegistry.getAllPrincipals().size() != 0) {
         //   System.out.println("ACTIVE USER: " + sessionRegistry.getAllPrincipals().size());
            int lengthPrincipals = sessionRegistry.getAllPrincipals().size();
            
            for (Object principal : sessionRegistry.getAllPrincipals()) {
             if (principal instanceof DefaultOidcUser) {
            	 
            	 
            	 System.out.println("User " + ((DefaultOidcUser)principal).getFamilyName());
            	 
            	 List<SessionInformation>  lsessions = sessionRegistry.getAllSessions(principal, false);
            	 
            //	 System.out.println(lsessions.size());
            	 
            	
            	
            	 for (Object sessiono : lsessions) {
            		 
            		 if (sessiono instanceof SessionInformation) { 
            //			 System.out.println((SessionInformation) sessiono);
            			 ((SessionInformation) sessiono).expireNow();
            			 sessionRegistry.removeSessionInformation(((SessionInformation) sessiono).getSessionId());
            			 publisher.publishEvent(AskToExpireSessionEvent.of(((SessionInformation) sessiono).getSessionId()));
                         expieredAtLeastOneSession = true;
            		 }
            	 }
            	
             } 
            }
            
            //model.put("activeuser",  sessionRegistry.getAllPrincipals().size());
        }
        else
        	System.out.println("EMPTY" );
        
      
        
        request.getSession().invalidate();
        
        
        
        return ResponseEntity.ok().body(logoutDetails);
    }
}
