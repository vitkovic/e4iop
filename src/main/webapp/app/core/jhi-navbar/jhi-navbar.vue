<template>

			    <b-navbar toggleable="sm" type="light">
			        <b-navbar-brand class="logo" b-link to="/" style="margin-bottom:-10px;" >
			            
			           <b-img :src="'/content/images/OII_sm_sm_2.png'"></b-img>
			            
			        </b-navbar-brand>      
			        <b-navbar-toggle 
			        right 
			        class="jh-navbar-toggler d-lg-none" 
			        href="javascript:void(0);"  
			        data-toggle="collapse" 
			        target="header-tabs" 
			        aria-expanded="false" 
			        aria-label="Toggle navigation">
			            <font-awesome-icon icon="bars" />
			        </b-navbar-toggle>
			           
			        <b-collapse is-nav id="header-tabs">
			            <b-navbar-nav class="md-auto">
			                <b-nav-item to="/" exact>
			                    <span>
			                        <font-awesome-icon icon="home" />
			                        <!--<span v-text="$t('global.menu.home')">Home</span>-->
			                    </span>
			                </b-nav-item>
			                <b-nav-item-dropdown
			                    right
			                    id="entity-menu"                    
			                    active-class="active" class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.pregled.main')">pregled</span>
			                    </span>
			                    <b-dropdown-item to="/research-infrastructure-pregled">
			                        <span v-text="$t('global.menu.entities.researchInfrastructure')">ResearchInfrastructure</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-service-pregled">
			                        <span v-text="$t('global.menu.entities.riService')">RiService</span>
			                    </b-dropdown-item>
			                     <b-dropdown-item to="/portal-user-organization-pregled">
			                        <span v-text="$t('global.menu.entities.portalUserOrganization')">PortalUserOrganization</span>
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
			<!--
			    v-if="authenticated && (hasAnyRole(['PA', 'RPU']) || hasAnyAuthority('ROLE_ADMIN'))"
			-->
			
			                <b-nav-item-dropdown
			                    right
			                    id="entity-menu"                    
			                    v-if="authenticated"
			                    active-class="active" class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.usluge.main')"></span>
			                    </span>
			                    <b-dropdown-item to="/request-for-service">                        
			                        <span v-text="$t('global.menu.entities.requestForService')">RequestForService</span>
			                    </b-dropdown-item>
			                    <!--
			                    <b-dropdown-item to="/request-for-service">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.offerAccepting')"></span>
			                    </b-dropdown-item>
			                    -->
			                    <b-dropdown-item to="/request-rating">
			                        <span v-text="$t('global.menu.entities.serviceRating')"></span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-procurement-proposal">
			                        <span v-text="$t('global.menu.entities.riProcurementProposal')">RiProcurementProposal</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/service-proposal">
			                        <span v-text="$t('global.menu.entities.serviceProposal')">ServiceProposal</span>
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
			                <b-nav-item-dropdown
			                    right
			                    id="entity-menu"
			                    v-if="authenticated && (hasAnyRole(['RPRI', 'RPRIPO', 'PA']) || hasAnyAuthority('ROLE_ADMIN'))"
			                    active-class="active" class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.infrastructure.main')"></span>
			                    </span>
			                     <b-dropdown-item to="/research-infrastructure">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.researchInfrastructure')">ResearchInfrastructure</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-service">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riService')">RiService</span>
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
			                <b-nav-item-dropdown
			                    right
			                    id="entity-menu"
			                    v-if="authenticated && (hasAnyRole(['PA', 'RPSI']) || hasAnyAuthority('ROLE_ADMIN'))"
			                    active-class="active" class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.report.main')"></span>
			                    </span>
			                    <b-dropdown-item to="/reports/organizations">                        
			                        <span v-text="$t('global.menu.entities.organizationsStatistic')">ResearchInfrastructure</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/reports/infrastructure-statistic">                        
			                        <span v-text="$t('global.menu.entities.infrastructureStatistic')"></span>
			                    </b-dropdown-item> 
			                    <b-dropdown-item to="/reports/service-statistic">                        
			                        <span v-text="$t('global.menu.entities.serviceStatistic')"></span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/reports/service-statistic-nio">        
			                        <span v-text="$t('global.menu.entities.serviceStatisticNio')"></span>
			                    </b-dropdown-item>                                       
			                </b-nav-item-dropdown>
			                <b-nav-item-dropdown
			                    right
			                    id="entity-menu"
			                    v-if="authenticated && (hasAnyRole(['PA','RPRIPO']) || hasAnyAuthority('ROLE_ADMIN'))"
			                    active-class="active" class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.portalAdministration.main')"></span>
			                    </span>
			                    <b-dropdown-item to="/portal-user">                        
			                        <span v-text="$t('global.menu.portalAdministration.portalUsers')"></span>
			                    </b-dropdown-item>
			                    <!--
			                    <b-dropdown-item to="/portal-user-responsible-persons">                        
			                        <span v-text="$t('global.menu.portalAdministration.responsiblePersons')"></span>
			                    </b-dropdown-item>
			                    -->
			                    <b-dropdown-item to="/ri-service"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">                        
			                        <span v-text="$t('global.menu.portalAdministration.databaseBackup')"></span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-service"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.portalAdministration.repoBackup')"></span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/portal-user-status"
								v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
								>
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.portalUserStatus')"></span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/portal-user-role"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.portalUserRole')">PortalUserRole</span>
			                    </b-dropdown-item>
			                   
			                    <b-dropdown-item to="/scientific-domain"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.scientificDomain')">ScientificDomain</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/scientific-subdomain"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.scientificSubdomain')">ScientificSubdomain</span>
			                    </b-dropdown-item>
			                    
			                    <b-dropdown-item to="/ri-status"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riStatus')">RiStatus</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-category"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riCategory')">RiCategory</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-subcategory"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riSubcategory')">RiSubcategory</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-access-type"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riAccessType')">RiAccessType</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-access-mode"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riAccessMode')">RiAccessMode</span>
			                    </b-dropdown-item>
			                    
			                    <b-dropdown-item to="/service-type"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.serviceType')">ServiceType</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/service-subtype"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.serviceSubtype')">ServiceSubtype</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/service-accreditation"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.serviceAccreditation')">ServiceAccreditation</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/service-availability"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.serviceAvailability')">ServiceAvailability</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-service-status"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riServiceStatus')">RiServiceStatus</span>
			                    </b-dropdown-item>
			                    
			                    <b-dropdown-item to="/researcher"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.researcher')">Researcher</span>
			                    </b-dropdown-item>
			                    
			                    <b-dropdown-item to="/ri-calendar"
			                    v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riCalendar')">RiCalendar</span>
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
			                <b-nav-item-dropdown
			                    right
			                    id="entity-menu"
			                    v-if="authenticated && (hasAnyRole(['PA', 'RPRIPO']) || hasAnyAuthority('ROLE_ADMIN'))"
			                    active-class="active" class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.organizationAdministration.main')"></span>
			                    </span>
			                    <b-dropdown-item to="/portal-user-organization">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.portalUserOrganization')">PortalUserOrganization</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/ri-research-organization">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.riResearchOrganization')">RiResearchOrganization</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/puo-legal-status"
								v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
								>
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.puoLegalStatus')">PuoLegalStatus</span>
			                    </b-dropdown-item>
			                      <b-dropdown-item to="/ripo-activity-status"
								  v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
								  >
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.ripoActivityStatus')">RipoActivityStatus</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/research-institution"
								v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
								>
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.researchInstitution')">ResearchInstitution</span>
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
			
			
			
			
			
			
			
			
			
			
			
			<!--
			                <b-nav-item-dropdown
			                    right
			                    id="entity-menu"
			                    v-if="authenticated && (hasAnyRole(['PA', 'RPU']) || hasAnyAuthority('ROLE_ADMIN'))"
			                    active-class="active" class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.entities.main')">Entities</span>
			                    </span>
			                    <b-dropdown-item to="/portal-user">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.portalUser')">PortalUser</span>
			                    </b-dropdown-item>
			                    
			                    
			                    <b-dropdown-item to="/request-respond">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.requestRespond')">RequestRespond</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/request-answer">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.requestAnswer')">RequestAnswer</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/service-offer-respond">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.serviceOfferRespond')">ServiceOfferRespond</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/answer-to-offer">
			                        <font-awesome-icon icon="asterisk" />
			                        <span v-text="$t('global.menu.entities.answerToOffer')">AnswerToOffer</span>
			                    </b-dropdown-item>
			                    
			
			                </b-nav-item-dropdown>
			-->



			<b-nav-item-dropdown right id="entity-menu_2" v-if="hasAnyAuthority('ROLE_ADMIN') && 
				authenticated" active-class="active" class="pointer">
					<span slot="button-content" class="navbar-dropdown-menu">
						<font-awesome-icon icon="th-list" />
						<span v-text="$t('global.menu.entities.mainb2b')">B2B portal</span>
					</span>
					<b-dropdown-item to="/b2b/advertisement">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisement')">Advertisement</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/company">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.company')">Company</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/message">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.message')">Message</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/badge">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.badge')">Badge</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-supporter">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisementSupporter')">AdvertisementSupporter</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-status">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisementStatus')">AdvertisementStatus</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-duration">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisementDuration')">AdvertisementDuration</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-type">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisementType')">AdvertisementType</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-kind">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisementKind')">AdvertisementKind</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-category">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisementCategory')">AdvertisementCategory</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-subcategory">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.advertisementSubcategory')">AdvertisementSubcategory</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-subsubcategory">
						<font-awesome-icon icon="asterisk" />
						<span
							v-text="$t('global.menu.entities.advertisementSubsubcategory')">AdvertisementSubsubcategory</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/collaboration">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.collaboration')">Collaboration</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/collaboration-rating">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.collaborationRating')">CollaborationRating</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/thread">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.thread')">Thread</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/meeting">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.meeting')">Meeting</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/meeting-participant">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.meetingParticipant')">MeetingParticipant</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/meeting-type">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.meetingType')">MeetingType</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/cms-page">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.cmsPage')">CmsPage</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/cms-page-type">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.cmsPageType')">CmsPageType</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/cms-testimonials">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.cmsTestimonials')">CmsTestimonials</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/cms-question">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.cmsQuestion')">CmsQuestion</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/cms-slider">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.cmsSlider')">CmsSlider</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/cms-carousel">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.cmsCarousel')">CmsCarousel</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/cms-advertisement-category">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.cmsAdvertisementCategory')">CmsAdvertisementCategory</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/document">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.document')">Document</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/document-type">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.documentType')">DocumentType</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/document-kind">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.documentKind')">DocumentKind</span>
					</b-dropdown-item>
					<!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
				</b-nav-item-dropdown>












			                <b-nav-item-dropdown
			                    right
			                    id="admin-menu"
			                    v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
			                    :class="{'router-link-active': subIsActive('/admin')}"
			                    active-class="active"
			                    class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">                        
			                        <span v-text="$t('global.menu.admin.main')">Administration</span>
			                    </span>
			                    <b-dropdown-item to="/admin/user-management" active-class="active">
			                        <font-awesome-icon icon="user" />
			                        <span v-text="$t('global.menu.admin.userManagement')">User management</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item  to="/admin/jhi-metrics" active-class="active">
			                        <font-awesome-icon icon="tachometer-alt" />
			                        <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/admin/jhi-health" active-class="active">
			                        <font-awesome-icon icon="heart" />
			                        <span v-text="$t('global.menu.admin.health')">Health</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item  to="/admin/jhi-configuration" active-class="active">
			                        <font-awesome-icon icon="list" />
			                        <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item  to="/admin/audits" active-class="active">
			                        <font-awesome-icon icon="bell" />
			                        <span v-text="$t('global.menu.admin.audits')">Audits</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item  to="/admin/logs" active-class="active">
			                        <font-awesome-icon icon="tasks" />
			                        <span v-text="$t('global.menu.admin.logs')">Logs</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item v-if="swaggerEnabled"  to="/admin/docs" active-class="active">
			                        <font-awesome-icon icon="book" />
			                        <span v-text="$t('global.menu.admin.apidocs')">API</span>
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
			                <b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
			                    <span slot="button-content">
			                        <font-awesome-icon icon="flag" />
			                        <span v-text="$t('global.menu.language')">Language</span>
			                    </span>
			                    <b-dropdown-item v-for="(value, key) in languages" :key="`lang-${key}`" v-on:click="changeLanguage(key);"
			                        :class="{ active: isActiveLanguage(key)}">
			                        {{value.name}}
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
							<b-nav-item to="/b2b" exact style="white-space: nowrap;" class="navbar-menu-item">
								<span>
									<span class="headerItemi">B2B</span>
								</span>
							</b-nav-item>
			                <b-nav-item-dropdown
			                    right
			                    href="javascript:void(0);"
			                    id="account-menu"
			                    :class="{'router-link-active': subIsActive('/account')}"
			                    active-class="active"
			                    class="pointer">
			                    <span slot="button-content" class="navbar-dropdown-menu">
			                        <font-awesome-icon 
			                        icon="user"
			                        :style="{color: authenticated ? '#00356B' : 'rgba(0,0,0,0.5)'}"
			                        />
			                           <span v-if="authenticated" class="initialsSpan">{{ getUserInitials() }}</span>
			                        <span v-text="$t('global.menu.account.main')">
			                            Account
			                        </span>
			               
			                    </span>
			                    <b-dropdown-item to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
			                        <font-awesome-icon icon="wrench" />
			                        <span v-text="$t('global.menu.account.settings')">Settings</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
			                        <font-awesome-icon icon="lock" />
			                        <span v-text="$t('global.menu.account.password')">Password</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item v-if="authenticated"  v-on:click="logout()" id="logout" active-class="active">
			                        <font-awesome-icon icon="sign-out-alt" />
			                        <span v-text="$t('global.menu.account.logout')">Sign out</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item v-if="!authenticated"  v-on:click="openLogin()" id="login" active-class="active">
			                        <font-awesome-icon icon="sign-in-alt" />
			                        <span v-text="$t('global.menu.account.login')">Sign in</span>
			                    </b-dropdown-item>
			                    <b-dropdown-item to="/register" tag="b-dropdown-item" id="register" v-if="!authenticated" active-class="active">
			                        <font-awesome-icon icon="user-plus" />
			                        <span v-text="$t('global.menu.account.register')">Register</span>
			                    </b-dropdown-item>
			                </b-nav-item-dropdown>
			            </b-navbar-nav>
			        </b-collapse>
			    </b-navbar>
	
	
</template>

<script lang="ts" src="./jhi-navbar.component.ts">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
}


@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span{
    display:none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 5px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  
}

.logo-img {
  height: 100%;
  background: url("../../../content/images/logo-jhipster.png") no-repeat center
    center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}

.initialsSpan {
	 border: 2px solid gray;
	 border-radius: 50%;
	 font-size: 0.9rem;
	 padding: 2px;
	 color: rgb(76, 76, 76);
	 border-color: rgb(76, 76, 76);
	 box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.1)
  	
}


</style>
