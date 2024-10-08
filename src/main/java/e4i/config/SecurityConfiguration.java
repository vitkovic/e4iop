package e4i.config;

import e4i.security.*;

import io.github.jhipster.config.JHipsterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import e4i.security.oauth2.AudienceValidator;
import e4i.security.SecurityUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import java.util.*;

import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import e4i.security.oauth2.JwtGrantedAuthorityConverter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}")
    private String issuerUri;
    
   

    private final JHipsterProperties jHipsterProperties;
    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(CorsFilter corsFilter, JHipsterProperties jHipsterProperties, SecurityProblemSupport problemSupport) {
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
    
    @Bean
    public ConcurrentSessionFilter concurrentSessionFilter() {
        return new ConcurrentSessionFilter(sessionRegistry(), new SimpleRedirectSessionInformationExpiredStrategy("/"));
    }
    
    
    @Bean
    public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {

        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy =
                new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);
        concurrentSessionControlAuthenticationStrategy.setMaximumSessions(5);
        return concurrentSessionControlAuthenticationStrategy;
    }

    @Bean
    public SessionFixationProtectionStrategy sessionFixationProtectionStrategy() {
        return new SessionFixationProtectionStrategy();
    }
    @Bean
    public RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy() {
        List<SessionAuthenticationStrategy> sessionAuthenticationStrategyList = new ArrayList<>();
        sessionAuthenticationStrategyList.add(concurrentSessionControlAuthenticationStrategy());
        sessionAuthenticationStrategyList.add(sessionFixationProtectionStrategy());
        sessionAuthenticationStrategyList.add(registerSessionAuthenticationStrategy());

        return new CompositeSessionAuthenticationStrategy(sessionAuthenticationStrategyList);
    }
    
    
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
	        .logout()
	        .invalidateHttpSession(true)
	        .deleteCookies("JSESSIONID")
	    .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            .addFilterBefore(corsFilter, CsrfFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' https://fonts.googleapis.com 'unsafe-inline'; img-src 'self' data:; font-src 'self' https://fonts.gstatic.com data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()
        .and()
            .authorizeRequests()
            .antMatchers("/b2b/meeting-accept**").authenticated()
            .antMatchers("/b2b/advertisement/*/view").permitAll()
            .antMatchers("/b2b/companies/*/view").permitAll()
            .antMatchers("/api/advertisements/*").permitAll()
            .antMatchers("/api/companies/*").permitAll()
            .antMatchers("/api/retrieve/image/*").permitAll()
            .antMatchers("/api/retrieve/document/*").permitAll()
            .antMatchers("/api/auth-info").permitAll()
            .antMatchers("/api/advertisement-categories/**").permitAll()
            .antMatchers("/api/advertisement-categories/*").permitAll()
            .antMatchers("/api/advertisements/view/*").permitAll()
            .antMatchers("/api/advertisements/view/**").permitAll()
            .antMatchers("/api/collaborations/company-ratings/*").permitAll()
            .antMatchers("/api/collaborations/company-rated/*").permitAll()
            .antMatchers("/api/advertisement-supporters/retrieve/*").permitAll()
            .antMatchers("/api/retrieve/image*").permitAll()
            .antMatchers("/api/advertisement-categories/*").permitAll()
            .antMatchers("/api/advertisement-categories").permitAll()
            .antMatchers("/api/advertisements/search*").permitAll()
            .antMatchers("/api/advertisements/search**").permitAll()
            .antMatchers("/api//advertisement-subcategories*").permitAll()
            .antMatchers("/api/advertisements/get*").permitAll()
            .antMatchers("/api/advertisements/get**").permitAll()
            .antMatchers("/api/companies/search*").permitAll()
            .antMatchers("/api/cms-questions/search*").permitAll()
            .antMatchers("/api/cms-questions*").permitAll()
            .antMatchers("/api/cms-questions/*").permitAll()
            .antMatchers("/api/cms-news/search*").permitAll()
            .antMatchers("/api/cms-pages/specific/*").permitAll()
            .antMatchers("/api/logout*").permitAll()
            .antMatchers("/api/advertisements/searchnopag/search*").permitAll()
            .antMatchers("/api/portal-user/get*").permitAll()
            .antMatchers("/api/portal-users/get*").permitAll()
            .antMatchers("/api/portal-user-organizations/get*").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .oauth2Login()
        .and()
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(authenticationConverter())
                .and()
            .and()
                .oauth2Client()
            .and()
    	    	.sessionManagement().maximumSessions(5).sessionRegistry(sessionRegistry()).expiredUrl("/api/logout")
    	    	.and().sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy());
          
          

        // @formatter:on
    }

    Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtGrantedAuthorityConverter());
        return jwtAuthenticationConverter;
    }
    /**
     * Map authorities from "groups" or "roles" claim in ID Token.
     *
     * @return a {@link GrantedAuthoritiesMapper} that maps groups from
     * the IdP to Spring Security Authorities.
     */
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                // Check for OidcUserAuthority because Spring Security 5.2 returns
                // each scope as a GrantedAuthority, which we don't care about.
                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
                    mappedAuthorities.addAll(SecurityUtils.extractAuthorityFromClaims(oidcUserAuthority.getUserInfo().getClaims()));
                }
            });
            return mappedAuthorities;
        };
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuerUri);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(jHipsterProperties.getSecurity().getOauth2().getAudience());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
