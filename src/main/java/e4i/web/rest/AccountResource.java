package e4i.web.rest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import e4i.domain.PortalUser;
import e4i.domain.PortalUserOrganization;
import e4i.domain.ResearchInstitution;
import e4i.domain.User;
import e4i.repository.PortalUserOrganizationRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.UserRepository;
import e4i.security.SecurityUtils;
import e4i.service.MailService;
import e4i.service.UserService;
import e4i.service.dto.PasswordChangeDTO;
import e4i.service.dto.UserDTO;
import e4i.web.rest.errors.*;
import e4i.web.rest.vm.KeyAndPasswordVM;
import e4i.web.rest.vm.ManagedUserVM;
import e4i.web.rest.vm.PortalUserRegisterDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;
    
    private final PortalUserRepository portalUserRepository;
    private final PortalUserOrganizationRepository portalUserOrganizationRepository;

	public AccountResource(UserRepository userRepository, UserService userService, MailService mailService,
			PortalUserRepository portalUserRepository,
			PortalUserOrganizationRepository portalUserOrganizationRepository) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.mailService = mailService;
		this.portalUserRepository = portalUserRepository;
		this.portalUserOrganizationRepository = portalUserOrganizationRepository;
	}

	/**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    //public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
    public void registerAccount(@Valid @RequestBody PortalUserRegisterDTO managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        
        System.out.println("OVDE SAM");
        System.out.println(managedUserVM.getResearcher());
        //ubacuje dodatne podatke za PortalUserORganization
        PortalUserOrganization puoResult = null;
        if("Employee".equals(managedUserVM.getPortalUserStatus().getValueEn()) || "Research institution employee".equals(managedUserVM.getPortalUserStatus().getValueEn())) {
        	if(managedUserVM.getPortalUserOrganization() != null) {
        		if(managedUserVM.getPortalUserOrganization().getId() != null) {
            		puoResult = managedUserVM.getPortalUserOrganization();
            	}
            }else {
            	System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
            	Optional<PortalUserOrganization> pu = portalUserOrganizationRepository.findByRegistrationNumber(managedUserVM.getOrganizationMaticniBroj());
                pu.ifPresent(puo -> {  // ako postoji organizacija sa tim maticnim brojem
                	throw new RegistrationNumberAlreadyExistsException();        	
                });
                
                Optional<PortalUserOrganization> pu1 = portalUserOrganizationRepository.findByPib(managedUserVM.getOrganizationPib());
                pu1.ifPresent(puo -> {  // ako postoji organizacija sa tim maticnim brojem      	
                	throw new PibAlreadyExistsException();        	
                });
            	
            	PortalUserOrganization puo = new PortalUserOrganization(managedUserVM);
        		puoResult = portalUserOrganizationRepository.save(puo);
        	}	
        }
        
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
  // Ubacuje dodatne podatke za PortalUser (telefon i pozicija) i povezuje ga sa userom
        PortalUser portalUser = new PortalUser(managedUserVM, user);
        portalUser.setUserOrganization(puoResult);
        portalUser.setResearcher(managedUserVM.getResearcher());
        
        // Temporary quick fix to bypass eId field in PortalUser which is annotated as "not null".
        portalUser.setEidId(9999L);
        
        PortalUser result = portalUserRepository.save(portalUser);
        mailService.sendActivationEmail(user);
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        Optional<User> user = userService.requestPasswordReset(mail);
        if (user.isPresent()) {
            mailService.sendPasswordResetMail(user.get());
        } else {
            // Pretend the request has been successful to prevent checking which emails really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail");
        }
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
