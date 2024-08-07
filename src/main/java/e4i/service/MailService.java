package e4i.service;

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementSupporter;
import e4i.domain.Collaboration;
import e4i.domain.Company;
import e4i.domain.Meeting;
import e4i.domain.Message;
import e4i.domain.PortalUser;
import e4i.domain.Thread;
import e4i.domain.User;
import e4i.repository.AdvertisementRepository;
import e4i.repository.CompanyRepository;
import e4i.repository.MessageRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.ThreadRepository;
import e4i.web.rest.dto.AttachmentDTO;
import e4i.web.rest.dto.NotificationMailDTO;
import io.github.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;
    
//    @Autowired
    private final PortalUserService portalUserService;
    
    private final UserService userService;
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    ThreadService threadService;
    
    @Autowired
    MessageService messageService;
    
    @Autowired
    AdvertisementRepository advertisementRepository;
    
    @Autowired
    PortalUserRepository portalUserRepository;
    
    @Autowired
    ThreadRepository threadRepository;
    
    @Autowired
    MessageRepository messageRepository; 
    
    @Autowired
    CompanyRepository companyRepository;
    

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine, @Lazy PortalUserService portalUserService, UserService userService) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
		this.portalUserService = portalUserService;
		this.userService = userService;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }
    
    @Async
    public void sendNotificationMail(NotificationMailDTO mailDTO) {
        log.debug("Sending message notification to PortalUsers from company");
        
        Boolean hasAttachments = false;
        if (mailDTO.getAttachments() != null) {
        	hasAttachments = true;
        }
        
        for (String email : mailDTO.getEmails()) {
            try {     		
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper emailMessage = new MimeMessageHelper(mimeMessage, hasAttachments, StandardCharsets.UTF_8.name());
                emailMessage.setTo(email);
                emailMessage.setFrom(jHipsterProperties.getMail().getFrom());
                emailMessage.setSubject(mailDTO.getSubject());
                emailMessage.setText(mailDTO.getContent(), true);
               
                if (hasAttachments) {
                    for (AttachmentDTO attachment : mailDTO.getAttachments()) {
                        emailMessage.addAttachment(attachment.getFileName(), attachment.getFile());
                    }
                }
                
                javaMailSender.send(mimeMessage);
                log.debug("Sent email to User '{}'", email);
            }  catch (MailException | MessagingException e) {
                log.warn("Email could not be sent to user '{}'", email, e);
            }	
        }
    }
    
    @Transactional
    public NotificationMailDTO createNotificationMailDTOForNewMessage(Message message) {
    	// Ova metoda stalno daje "lazyInitializationException" i no-session 
    	// ako se koristi lazu fetching child entiteta.
    	// Druge metode iz ove klase koje su na istom principu nemaju taj problem.
    	// Kao fix su napravljene servisne metode za svaki child entitet koji je potreban.
    	
    	
    	// Lazy fetching varijanta koja daje "lazyInitializationException"
//    	Thread thread = message.getThread();
//    	PortalUser portalUserSender = message.getPortalUserSender();
//    	Company portalUserSenderCompany = portalUserSender.getCompany();
//    	Company companySender = thread.getCompanySender();
//    	Company companyReceiver = thread.getCompanyReceiver();
    	
    	
    	// Dobijanje child entiteta preko servisnih metoda
    	Thread thread = messageService.findThreadByMessage(message);
    	PortalUser portalUserSender = messageService.findPortalUserSenderByMessage(message);
    	Company portalUserSenderCompany = portalUserService.findCompanyByPortalUser(portalUserSender);
    	Company companySender = threadService.findCompanySenderByThread(thread);
    	Company companyReceiver = threadService.findCompanyReceiverByThread(thread);
    	    	
        Company company = companySender;
        if (portalUserSenderCompany.getId() == companySender.getId()) {
        	company = companyReceiver;
        }
        
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(company, true);
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> userService.getEmailByPortalUser(portalUser))
                .collect(Collectors.toList());

        String mailSubject = "B2B portal - Obaveštenje o novoj poruci";
        String mailContent = this.prepareContentForNewMessageNotification(message, thread, company, portalUserSender);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
    }
    
    @Transactional
    public NotificationMailDTO createNotificationMailDTOForCollaborationRequest(Collaboration collaboration) {
    	Company company = collaboration.getCompanyOffer();
    	
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(company, true);
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o novom zahtevu za saradnjom";
        String mailContent = this.prepareContentForCollaborationRequestNotification(collaboration);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
    }
    
    @Transactional
    public NotificationMailDTO createNotificationMailDTOForCollaborationConfirm(Collaboration collaboration) {
    	Company company = collaboration.getCompanyRequest();
    	
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(company, true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o prihvaćenom zahtevu za saradnjom";
        String mailContent = this.prepareContentForCollaborationConfirmNotification(collaboration);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
    }
    
    @Transactional
    public NotificationMailDTO createNotificationMailDTOForCollaborationCancel(Collaboration collaboration) {
    	Company company = collaboration.getCompanyRequest();
    	
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(company, true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o odbijenoom zahtevu za saradnjom";
        String mailContent = this.prepareContentForCollaborationCancelNotification(collaboration);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
    }
    
    @Transactional
    public NotificationMailDTO createNotificationMailDTOForCollaborationRatingCompanyOffer(Collaboration collaboration) {
    	Company company = collaboration.getCompanyOffer();
    	
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(company, true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o novoj oceni za saradnju";
        String mailContent = this.prepareNotificationContentForCollaborationRatingCompanyOffer(collaboration);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
    }
    
    @Transactional
    public NotificationMailDTO createNotificationMailDTOForCollaborationRatingCompanyRequest(Collaboration collaboration) {
    	Company company = collaboration.getCompanyRequest();
    	
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(company, true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o novoj oceni za saradnju";
        String mailContent = this.prepareNotificationContentForCollaborationRatingCompanyRequest(collaboration);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
    }
    
    @Transactional
	public NotificationMailDTO createNotificationMailDTOForMeetingInvitation(
			Meeting meeting, 
			Company companyOrganizer,
			Company companyParticipant
			) {
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(companyParticipant, true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o pozivu za sastanak";
        String mailContent = this.prepareNotificationContentForMeetingInvitation(meeting, companyOrganizer, companyParticipant);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
	}
    
    @Transactional
	public NotificationMailDTO createNotificationMailDTOForMeetingAcceptance(
			Meeting meeting, 
			Company companyOrganizer,
			Company companyParticipant
			) {
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(companyOrganizer, true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o prihvatanju poziva za sastanak";
        String mailContent = this.prepareNotificationContentForMeetingAcceptance(meeting, companyOrganizer, companyParticipant);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
	}
	
    @Transactional
	public NotificationMailDTO createNotificationMailDTOForMeetingRejection(
			Meeting meeting, 
			Company companyOrganizer,
			Company companyParticipant, 
			String comment
			) {
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(companyOrganizer, true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o odbijanju poziva za sastanak";
        String mailContent = this.prepareNotificationContentForMeetingRejection(meeting, companyOrganizer, companyParticipant, comment);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
	}
    
    @Transactional
	public NotificationMailDTO createNotificationMailDTOForMeetingInvitationNonB2B(
			Meeting meeting,
			Company companyOrganizer, 
			List<String> emails,
			ByteArrayResource attachment) {
        String mailSubject = "B2B portal - Obaveštenje o pozivu za sastanak";
        String mailContent = this.prepareNotificationContentForMeetingInvitationNonB2B(meeting, companyOrganizer);

        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setFile(attachment);
        attachmentDTO.setFileName("b2b_sastanak.ics");
        
        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        mailDTO.setAttachments(new ArrayList<>());
        mailDTO.addAttachment(attachmentDTO);
        
        return mailDTO;
	}
	
    @Transactional
	public NotificationMailDTO createNotificationMailDTOForSupporterInvitation(
			AdvertisementSupporter advertisementSupporter) {
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(advertisementSupporter.getCompany(), true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o pozivu za zajedničko oglašavanje";
        String mailContent = this.prepareNotificationContentForSupporterInvitation(advertisementSupporter);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
	}
    
    @Transactional
	public NotificationMailDTO createNotificationMailDTOForSupporterAcceptance(
			AdvertisementSupporter advertisementSupporter) {
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(advertisementSupporter.getAdvertisement().getCompany(), true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o prihvatanju poziva za zajedničko oglašavanje";
        String mailContent = this.prepareNotificationContentForSupporterAcceptance(advertisementSupporter);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
	}
    
    @Transactional
	public NotificationMailDTO createNotificationMailDTOForSupporterRejection(
			AdvertisementSupporter advertisementSupporter) {
        List<PortalUser> companyPortalUsers = portalUserRepository.findAllByCompanyAndDoNotify(advertisementSupporter.getAdvertisement().getCompany(), true); 
        List<String> emails = companyPortalUsers.stream()
                .map(portalUser -> portalUser.getUser().getEmail())
                .collect(Collectors.toList());
	
        String mailSubject = "B2B portal - Obaveštenje o odbijanju poziva za zajedničko oglašavanje";
        String mailContent = this.prepareNotificationContentForSupporterRejection(advertisementSupporter);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
	}
	
    @Transactional
	public NotificationMailDTO createB2BInvitationMail(Company company, String email) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
	
        String mailSubject = "B2B portal - Poziv za zajedničko upravljanje nalogom kompanije " + company.getName();
        String mailContent = this.prepareNotificationContentForB2BInvitation(company);

        NotificationMailDTO mailDTO = new NotificationMailDTO();
        mailDTO.setEmails(emails);
        mailDTO.setSubject(mailSubject);
        mailDTO.setContent(mailContent);
        
        return mailDTO;
		
	}

	public String prepareContentForNewMessageNotification(Message message, Thread thread, Company company, PortalUser portalUser) {
    	
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTime = message.getDatetime().atZone(ZoneId.systemDefault());
        
        String advertisementString = "";
        Optional<Advertisement> advertisementOptional = advertisementRepository.findOneByThreads(thread);        
        if (advertisementOptional.isPresent()) {
        	Advertisement advertisement = advertisementOptional.get();
        	advertisementString = "<p><b>Oglas: </b><span>" + advertisement.getTitle() + "</span></p>";
        }
    	
//        String senderString = message.getPortalUserSender().getCompany().getName()
//        		+ " - " + message.getPortalUserSender().getUser().getFirstName()
//        		+ " " + message.getPortalUserSender().getUser().getLastName();

        String senderString = portalUser.getCompany().getName()
        		+ " - " + portalUser.getUser().getFirstName()
        		+ " " + portalUser.getUser().getLastName();
        
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyMessagesLink = homeURL + "/b2b/company/" + company.getId() + "/threads";
        
        String content = "<div>"
        		+ "<p>Imate novu poruku na B2B portalu za kompaniju " + company.getName() + ".</p>"
        		+ "<br>"
        		+ "<p><b>Vreme: </b><span>" + dateTimeFormatter.format(zonedDateTime) + "</span></p>"
        		+ "<p><b>Pоšiljalac: </b><span>" + senderString + "</span></p>"
        		+ advertisementString
        		+ "<p><b>Upit: </b><span>" + thread.getSubject() + "</span></p>"
        		+ "<hr>"
        		+ "<p style='white-space: pre-line;'>" + message.getContent() + "</p>"
        		+ "<hr>"
        		+ "<p>Na poruku možete odgovoriti sa "
        		+ "<a href='" + companyMessagesLink + "'>profila Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
             
    	return content;
    }
    
    public String prepareContentForCollaborationRequestNotification(Collaboration collaboration) {
    	
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTime = collaboration.getDatetime().atZone(ZoneId.systemDefault());
        
        String advertisementString = "";
        advertisementString = "<p><b>Oglas: </b><span>" + collaboration.getAdvertisement().getTitle() + "</span></p>";
    	    
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyMessagesLink = homeURL + "/b2b/company/" + collaboration.getCompanyOffer().getId() + "/threads";
        
        String content = "<div>"
        		+ "<p>Imate novi zahtev za saradnjom na B2B portalu za kompaniju " + collaboration.getCompanyOffer().getName() + ".</p>"
        		+ "<br>"
        		+ "<p><b>Vreme: </b><span>" + dateTimeFormatter.format(zonedDateTime) + "</span></p>"
        		+ "<p><b>Tražilac: </b><span>" + collaboration.getCompanyRequest().getName() + "</span></p>"
        		+ advertisementString
        		+ "<hr>"
        		+ "<p>Saradnju možete potvrditi sa "
        		+ "<a href='" + companyMessagesLink + "'>profila Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
             
    	return content;
    }
    
    public String prepareContentForCollaborationConfirmNotification(Collaboration collaboration) {
    	
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTime = collaboration.getDatetime().atZone(ZoneId.systemDefault());
        
        String advertisementString = "";
        advertisementString = "<p><b>Oglas: </b><span>" + collaboration.getAdvertisement().getTitle() + "</span></p>";
        
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyMessagesLink = homeURL + "/b2b/company/" + collaboration.getCompanyRequest().getId() + "/collaborations";
        
        String content = "<div>"
        		+ "<p>Prihvaćen je zahtev za saradnju na B2B portalu za kompaniju " + collaboration.getCompanyRequest().getName() + ".</p>"
        		+ "<br>"
        		+ "<p><b>Vreme: </b><span>" + dateTimeFormatter.format(zonedDateTime) + "</span></p>"
        		+ "<p><b>Oglašivač: </b><span>" + collaboration.getCompanyOffer().getName() + "</span></p>"
        		+ advertisementString
        		+ "<hr>"
        		+ "<p>Sve ostvarene saradnje možete pogledati sa "
        		+ "<a href='" + companyMessagesLink + "'>profila Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
             
    	return content;
    }
    
    public String prepareContentForCollaborationCancelNotification(Collaboration collaboration) {
    	
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTime = collaboration.getDatetime().atZone(ZoneId.systemDefault());
        
        String advertisementString = "";
        advertisementString = "<p><b>Oglas: </b><span>" + collaboration.getAdvertisement().getTitle() + "</span></p>";
        
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyMessagesLink = homeURL + "/b2b/company/" + collaboration.getCompanyRequest().getId() + "/collaborations";
        
        String content = "<div>"
        		+ "<p>Odbijen je zahtev za saradnju na B2B portalu za kompaniju " + collaboration.getCompanyRequest().getName() + ".</p>"
        		+ "<br>"
        		+ "<p><b>Vreme: </b><span>" + dateTimeFormatter.format(zonedDateTime) + "</span></p>"
        		+ "<p><b>Oglašivač: </b><span>" + collaboration.getCompanyOffer().getName() + "</span></p>"
        		+ advertisementString
        		+ "<hr>"
        		+ "<p>Sve ostvarene saradnje možete pogledati sa "
        		+ "<a href='" + companyMessagesLink + "'>profila Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
             
    	return content;
    }

    public String prepareNotificationContentForCollaborationRatingCompanyOffer(Collaboration collaboration) {        
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyMessagesLink = homeURL + "/b2b/company/" + collaboration.getCompanyOffer().getId() + "/collaborations";

        String rating = "<p><b>Ocena: </b><span>"
				+ collaboration.getRatingRequest().getNumber() 
				+ "/4 - " +  collaboration.getRatingRequest().getDescription() 
				+ "</span></p>";
        
        String ratingComment = "";
        if (collaboration.getCommentRequest() != "") {
        	ratingComment ="<p><b>Komentar: </b>" + collaboration.getCommentRequest() + "</span></p>";
        }
        
        String content = "<div>"
        		+ "<p>Imate novu ocenu na B2B portalu za ostvarenu saradnju kao oglašivač."
        		+ "<br>"
        		+ "<p><b>Oglas: </b><span>" + collaboration.getAdvertisement().getTitle() + "</span></p>"
        		+ "<p><b>Oglašivač: </b><span>" + collaboration.getCompanyOffer().getName() + "</span></p>"
        		+ "<p><b>Tražilac: </b><span>" + collaboration.getCompanyRequest().getName() + "</span></p>"
        		+ "<hr>"
        		+ rating
        		+ ratingComment
        		+ "<hr>"
        		+ "<p>Sve ostvarene saradnje možete pogledati sa "
        		+ "<a href='" + companyMessagesLink + "'>profila Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
    	return content;
    }
    
    public String prepareNotificationContentForCollaborationRatingCompanyRequest(Collaboration collaboration) {        
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyMessagesLink = homeURL + "/b2b/company/" + collaboration.getCompanyRequest().getId() + "/collaborations";
        
        String rating = "<p><b>Ocena: </b><span>"
				+ collaboration.getRatingOffer().getNumber() 
				+ "/4 - " +  collaboration.getRatingOffer().getDescription() 
				+ "</span></p>";
        
        String ratingComment = "";
        if (collaboration.getCommentRequest() != "") {
        	ratingComment ="<p><b>Komentar: </b>" + collaboration.getCommentOffer() + "</span></p>";
        }
        String content = "<div>"
        		+ "<p>Imate novu ocenu na B2B portalu za ostvarenu saradnju kao tražilac."
        		+ "<br>"
        		+ "<p><b>Oglas: </b><span>" + collaboration.getAdvertisement().getTitle() + "</span></p>"
        		+ "<p><b>Oglašivač: </b><span>" + collaboration.getCompanyOffer().getName() + "</span></p>"
        		+ "<p><b>Tražilac: </b><span>" + collaboration.getCompanyRequest().getName() + "</span></p>"
        		+ "<hr>"
        		+ rating
        		+ ratingComment
        		+ "<hr>"
        		+ "<p>Sve ostvarene saradnje možete pogledati sa "
        		+ "<a href='" + companyMessagesLink + "'>profila Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
    	return content;
    }

    private String prepareNotificationContentForMeetingInvitation(
    		Meeting meeting, 
    		Company companyOrganizer, 
    		Company companyParticipant
    		) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTimeStart = meeting.getDatetimeStart().atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeEnd = meeting.getDatetimeEnd().atZone(ZoneId.systemDefault());
    	
    	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    	
    	String activationLink = String
    			.format(homeURL + "/b2b/meeting-accept?meetingId=%d&companyId=%d", meeting.getId(), companyParticipant.getId());
        String companyCalendarLink = homeURL + "/b2b/company/" + companyParticipant.getId() + "/calendar";
        
    	String advertisementText = (meeting.getAdvertisement() != null) ? 
    			"<p><b>Oglas: </b><span>" + meeting.getAdvertisement().getTitle() : "";
        
        String meetingText = 
        		"<p><b>Sastanak: </b><span>" + meeting.getTitle()
        		+ "<p><b>Organizator: </b><span>" + companyOrganizer.getName()
        		+ advertisementText
        		+ "<p><b>Vreme početka: </b><span>" + dateTimeFormatter.format(zonedDateTimeStart)
        		+ "<p><b>Vreme završetka: </b><span>" + dateTimeFormatter.format(zonedDateTimeEnd);
        
        String content = "<div>"
        		+ "<p>Kompanija sa B2B portala Vam je uputila poziv za sastanak."
        		+ "<br>"
        		+ "<br>"
        		+ meetingText
        		+ "<hr>"
        		+ "<br>"
        		+ "<p>Poziv na sastanak možete potvrditi klikom na "
        		+ "<a href='" + activationLink + "'>ovaj link<a>.</p>"
        		+ "<br>"
        		+ "<p>Ukoliko link nije aktivan, poziv na sastanak možete potvrditi i iz kalendara na "
        		+ "<a href='" + companyCalendarLink + "'> B2B profilu Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
        
    	return content;
	}
    
	private String prepareNotificationContentForMeetingAcceptance(
			Meeting meeting, 
			Company companyOrganizer,
			Company companyParticipant
			) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTimeStart = meeting.getDatetimeStart().atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeEnd = meeting.getDatetimeEnd().atZone(ZoneId.systemDefault());
    	
    	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyCalendarLink = homeURL + "/b2b/company/" + companyOrganizer.getId() + "/calendar";
        
    	String advertisementText = (meeting.getAdvertisement() != null) ? 
    			"<p><b>Oglas: </b><span>" + meeting.getAdvertisement().getTitle() : "";
        
        String meetingText = 
        		"<p><b>Sastanak: </b><span>" + meeting.getTitle()
        		+ "<p><b>Organizator: </b><span>" + companyOrganizer.getName()
        		+ advertisementText
        		+ "<p><b>Vreme početka: </b><span>" + dateTimeFormatter.format(zonedDateTimeStart)
        		+ "<p><b>Vreme završetka: </b><span>" + dateTimeFormatter.format(zonedDateTimeEnd);
        
        String content = "<div>"
        		+ "<p>Kompanija " + companyParticipant.getName() + " je prihvatila poziv za sastanak."
        		+ "<br>"
        		+ "<br>"
        		+ meetingText
        		+ "<hr>"
        		+ "<br>"
        		+ "<p>Detalje svih zakazanih sastanaka možete pogledati u kalendaru na "
        		+ "<a href='" + companyCalendarLink + "'> B2B profilu Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
        
    	return content;
	}
	
    
	private String prepareNotificationContentForMeetingRejection(
			Meeting meeting, 
			Company companyOrganizer,
			Company companyParticipant, 
			String comment
			) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTimeStart = meeting.getDatetimeStart().atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeEnd = meeting.getDatetimeEnd().atZone(ZoneId.systemDefault());
    	
    	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String companyCalendarLink = homeURL + "/b2b/company/" + companyOrganizer.getId() + "/calendar";
        
    	String advertisementText = (meeting.getAdvertisement() != null) ? 
    			"<p><b>Oglas: </b><span>" + meeting.getAdvertisement().getTitle() : "";
        
        String meetingText = 
        		"<p><b>Sastanak: </b><span>" + meeting.getTitle()
        		+ "<p><b>Organizator: </b><span>" + companyOrganizer.getName()
        		+ advertisementText
        		+ "<p><b>Vreme početka: </b><span>" + dateTimeFormatter.format(zonedDateTimeStart)
        		+ "<p><b>Vreme završetka: </b><span>" + dateTimeFormatter.format(zonedDateTimeEnd);
        
        String content = "<div>"
        		+ "<p>Kompanija " + companyParticipant.getName() + " je odbila poziv za sastanak."
        		+ "<br>"
        		+ "<br>"
        		+ meetingText
        		+ "<hr>"
        		+ "<p style=\"white-space: pre-line;\">" + comment.replace("\n", "<br>").replaceAll("^_+|_+$", "") + "</p>"
        		+ "<hr>"
        		+ "<br>"
        		+ "<p>Detalje svih zakazanih sastanaka možete pogledati u kalendaru na "
        		+ "<a href='" + companyCalendarLink + "'> B2B profilu Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
        
    	return content;
	}
	
	private String prepareNotificationContentForMeetingInvitationNonB2B(Meeting meeting, Company companyOrganizer) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        ZonedDateTime zonedDateTimeStart = meeting.getDatetimeStart().atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeEnd = meeting.getDatetimeEnd().atZone(ZoneId.systemDefault());
    	
    	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    	

//        String companyCalendarLink = homeURL + "/b2b/company/" + companyParticipant.getId() + "/calendar";
        
    	String advertisementText = "";
    	if (meeting.getAdvertisement() != null) {
        	String advertisementLink = homeURL + "/b2b/advertisement/" + meeting.getAdvertisement().getId() + "/view";
        	advertisementText = "<p>Sastanak je vezan za oglas <b>" + meeting.getAdvertisement().getTitle() + "</b>." 
        			+ "<br>Oglas možete pogledati na " 
        			+ "<a href='" + advertisementLink + "'>ovom linku<a></p>";
    	}
    	
    	
    	String locationText = (meeting.getLocation() != null && !meeting.getLocation().isBlank()) ? 
    			"<p><b>Lokacija: </b><span>".concat( meeting.getLocation() + "</p>") : "";
    	
    	String descriptionText = (meeting.getDescription() != null && !meeting.getDescription().isBlank()) ? 
    			"<p><b>Opis: </b><span>".concat(meeting.getDescription() + "</p>") : "";
        
        String meetingText = 
        		"<p><b>Sastanak: </b><span>" + meeting.getTitle()
        		+ "<p><b>Organizator: </b><span>" + companyOrganizer.getName()
        		+ "<p><b>Vreme početka: </b><span>" + dateTimeFormatter.format(zonedDateTimeStart)
        		+ "<p><b>Vreme završetka: </b><span>" + dateTimeFormatter.format(zonedDateTimeEnd)
        		+ locationText
        		+ descriptionText;
        
        String content = "<div>"
        		+ "<p>Kompanija sa <a href='" + homeURL +"/b2b" + "'>B2B portala<a> Vam je uputila poziv za sastanak.</p>"
        		+ "<br>"
        		+ meetingText
        		+ "<hr>"
        		+ "<br>"
        		+ advertisementText
        		+ "<br>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
        
    	return content;
	}
	

	private String prepareNotificationContentForSupporterInvitation(AdvertisementSupporter advertisementSupporter) {
    	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    	
    	String acceptLink = String
    			.format(homeURL + "/b2b/advertisement-supporter-accept?advertisementId=%d&companyId=%d", 
    					advertisementSupporter.getAdvertisement().getId(), 
    					advertisementSupporter.getCompany().getId());
    	
    	String companyThreadsLink = homeURL + "/b2b/company/" + advertisementSupporter.getAdvertisement().getCompany().getId() + "/threads";
    	
        String advertisementLink = homeURL + "/b2b/advertisement/" + advertisementSupporter.getAdvertisement().getId() + "/view";
    	String advertisementText = "<p><b>Oglas: </b><a href='" + advertisementLink + "'>" + advertisementSupporter.getAdvertisement().getTitle() + "<a><p>";
    	
    	String advertiserLink = homeURL + "/b2b/company/" + advertisementSupporter.getAdvertisement().getCompany().getId() + "/view";
    	String advertiserText = "<p><b>Oglašivač: </b><a href='" + advertiserLink + "'>" + advertisementSupporter.getAdvertisement().getCompany().getName() + "<a><p>";
    	
        
        
        String content = "<div>"
        		+ "<p>Kompanija sa B2B portala Vam je uputila poziv da postanete pridruženi oglašivač."
        		+ "<br>"
        		+ "<br>"
        		+ advertisementText
        		+ advertiserText
        		+ "<hr>"
        		+ "<br>"
        		+ "<p>Poziv možete potvrditi klikom na "
        		+ "<a href='" + acceptLink + "'>ovaj link<a>.</p>"
        		+ "<br>"
        		+ "<p>Ukoliko link nije aktivan, na poziv za pridruženo oglašavanje možete odgovoriti i sa stranice poruka na "
        		+ "<a href='" + companyThreadsLink + "'> B2B profilu Vaše kompanije<a>.</p>"
        		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
        
    	return content;
	}
	
	private String prepareNotificationContentForSupporterAcceptance(AdvertisementSupporter advertisementSupporter) { 	
  	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
  	

    String advertisementLink = homeURL + "/b2b/advertisement/" + advertisementSupporter.getAdvertisement().getId() + "/view";
  	String advertisementText = "<p><b>Oglas: </b><a href='" + advertisementLink + "'>" + advertisementSupporter.getAdvertisement().getTitle() + "<a><p>";
  	
  	String supporterLink = homeURL + "/b2b/company/" + advertisementSupporter.getCompany().getId() + "/view";
  	String supporterText = "<p><b>Pridruženi oglašivač: </b><a href='" + supporterLink + "'>" + advertisementSupporter.getCompany().getName() + "<a><p>";
  	
      
      
      String content = "<div>"
      		+ "<p>Kompanija sa B2B portala je prihvatila poziv za pridruženo oglašavanje."
      		+ "<br>"
      		+ "<br>"
      		+ advertisementText
      		+ supporterText
      		+ "<hr>"
      		+ "<br>"
      		+ "<br>"
      		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
      
  	return content;
	}
	

	private String prepareNotificationContentForSupporterRejection(AdvertisementSupporter advertisementSupporter) {
	  	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
	  	

	    String advertisementLink = homeURL + "/b2b/advertisement/" + advertisementSupporter.getAdvertisement().getId() + "/view";
	  	String advertisementText = "<p><b>Oglas: </b><a href='" + advertisementLink + "'>" + advertisementSupporter.getAdvertisement().getTitle() + "<a><p>";
	  	
	  	String supporterLink = homeURL + "/b2b/company/" + advertisementSupporter.getCompany().getId() + "/view";
	  	String supporterText = "<a href='" + supporterLink + "'>" + advertisementSupporter.getCompany().getName() + "<a>";
	  	
	      
	      
	      String content = "<div>"
	      		+ "<p>Kompanija " + supporterText + " sa B2B portala je odbila poziv za pridruženo oglašavanje."
	      		+ "<br>"
	      		+ "<br>"
	      		+ advertisementText
	      		+ "<hr>"
	      		+ "<br>"
	      		+ "<br>"
	      		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
	      
	  	return content;
	}
	

	private String prepareNotificationContentForB2BInvitation(Company company) {
	  	String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
	  	
	  	String portalLink = homeURL + "/b2b";
	  	String portalText = "<a href='" + portalLink + "'>" + "ovaj link" + "<a>";
	  	
	
	  	String companyLink = homeURL + "/b2b/company/" + company.getId() + "/view";
	  	String companyText = "<a href='" + companyLink + "'>" + company.getName() + "<a>";
	  	
	      
	      
	      String content = "<div>"
	      		+ "<p>Kompanija " + companyText + " sa B2B portala Vam je uputila poziv za zajedničko korišćenje i uredjivanje naloga."
	      		+ "<br>"
	      		+ "<br>"
	      		+ "<p>Registraciju na B2B portalu možete izvršiti klikom na " + portalText + "."
	      		+ "<hr>"
	      		+ "<br>"
	      		+ "<br>"
	      		+ "<p>Ovo je automatski poslata poruka, ne odgovarati na ovaj mail.</p>";
	      
	  	return content;
	}


}
