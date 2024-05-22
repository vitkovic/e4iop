<template>
	<div class="ceoHeader">
		<div class="navZero">
			<div class="logoiZalet">
					<b-link to="/b2b">
						<img src="/content/images/b2b1.png" alt="" class="divZaLogo">
					</b-link>
					<span class="zalet">Залет иновација</span>
			</div>

			<div class="navItems">


				<b-nav-item to="/" exact style="white-space: nowrap;" class="navbar-menu-item highlight-option">
					<span>
						<span class="headerItemi">ОИИ</span>
					</span>
				</b-nav-item>
							
				<b-nav-item-dropdown id="languagesnavBarDropdown" right active-class="active" class="pointer">
					<span slot="button-content" class="navbar-dropdown-menu">
						<span class="headerItemi" v-text="'Огласи'">CMS</span>
					</span>
					<b-dropdown-item to="/b2b/advertisement/new" active-class="active">
						<span v-text="'Огласи потребу'">Огласи потребу</span>
					</b-dropdown-item>
					<b-dropdown-item to="/b2b/advertisement-browse" active-class="active">
						<span v-text="'Пронађи потребу'">Пронађи потребу</span>
					</b-dropdown-item>
				</b-nav-item-dropdown>
				<b-nav-item to="/b2b/about" exact style="white-space: nowrap;" class="navbar-menu-item">
					<span>
						<span class="headerItemi">О нама</span>
					</span>
				</b-nav-item>

				<b-nav-item to="/b2b/questions" exact style="white-space: nowrap;" class="navbar-menu-item">
					<span>
						<span class="headerItemi">Најчешћа питања</span>
					</span>
				</b-nav-item>

				<b-nav-item-dropdown right id="cms-admin-menu" v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
					:class="{ 'router-link-active': subIsActive('/admin') }" active-class="active" class="pointer">
					<span slot="button-content" class="navbar-dropdown-menu headerItemi">
						<font-awesome-icon icon="cogs" />
						<span v-text="'CMS'">CMS</span>
					</span>
					<b-dropdown-item to="#" active-class="active">
						<span v-text="'Upravljanje administratorima'">Upravljanje administratorima</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" active-class="active">
						<span v-text="'Upravljanje korisnicima'">Upravljanje korisnicima</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" active-class="active">
						<span v-text="'Upravljanje oglasima'">Upravljanje oglasima</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" active-class="active">
						<span v-text="'Upravljanje stranicama'">Upravljanje stranicama</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" active-class="active">
						<span v-text="'Šifarnici'">Šifarnici</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" active-class="active">
						<span v-text="'Vesti'">Vesti</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" active-class="active">
						<span v-text="'Izveštaji'">Izveštaji</span>
					</b-dropdown-item>

				</b-nav-item-dropdown>

				<b-nav-item-dropdown right id="entity-menu" v-if="hasAnyAuthority('ROLE_ADMIN') &&
					authenticated" active-class="active" class="pointer">
					<span slot="button-content" class="navbar-dropdown-menu headerItemi">
						<font-awesome-icon icon="th-list" />
						<span v-text="$t('global.menu.entities.main')">Entities</span>
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
					<b-dropdown-item to="/b2b/collaboration-status">
						<font-awesome-icon icon="asterisk" />
						<span v-text="$t('global.menu.entities.collaborationStatus')">CollaborationStatuys</span>
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
				<b-nav-item-dropdown right id="admin-menu" v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
					:class="{ 'router-link-active': subIsActive('/admin') }" active-class="active" class="pointer">
					<span slot="button-content" class="navbar-dropdown-menu headerItemi">
						<font-awesome-icon icon="cogs" />
						<span v-text="$t('global.menu.admin.main')">Administration</span>
					</span>
					<b-dropdown-item to="/admin/user-management" active-class="active">
						<font-awesome-icon icon="user" />
						<span v-text="$t('global.menu.admin.userManagement')">User management</span>
					</b-dropdown-item>
					<b-dropdown-item to="/admin/jhi-metrics" active-class="active">
						<font-awesome-icon icon="tachometer-alt" />
						<span v-text="$t('global.menu.admin.metrics')">Metrics</span>
					</b-dropdown-item>
					<b-dropdown-item to="/admin/jhi-health" active-class="active">
						<font-awesome-icon icon="heart" />
						<span v-text="$t('global.menu.admin.health')">Health</span>
					</b-dropdown-item>
					<b-dropdown-item to="/admin/jhi-configuration" active-class="active">
						<font-awesome-icon icon="list" />
						<span v-text="$t('global.menu.admin.configuration')">Configuration</span>
					</b-dropdown-item>
					<b-dropdown-item to="/admin/audits" active-class="active">
						<font-awesome-icon icon="bell" />
						<span v-text="$t('global.menu.admin.audits')">Audits</span>
					</b-dropdown-item>
					<b-dropdown-item to="/admin/logs" active-class="active">
						<font-awesome-icon icon="tasks" />
						<span v-text="$t('global.menu.admin.logs')">Logs</span>
					</b-dropdown-item>
					<b-dropdown-item v-if="swaggerEnabled" to="/admin/docs" active-class="active">
						<font-awesome-icon icon="book" />
						<span v-text="$t('global.menu.admin.apidocs')">API</span>
					</b-dropdown-item>
				</b-nav-item-dropdown>
				<b-nav-item-dropdown id="languagesnavBarDropdown" right
					v-if="languages && Object.keys(languages).length > 1">
					<span slot="button-content">
						<font-awesome-icon icon="flag" class="ikonica" />
						<span v-text="$t('global.menu.language')" class="headerItemi">Language</span>
					</span>
					<b-dropdown-item v-for="(value, key) in languages" :key="`lang-${key}`"
						v-on:click="changeLanguage(key);" :class="{ active: isActiveLanguage(key) }">
						{{ value.name }}
					</b-dropdown-item>
				</b-nav-item-dropdown>
				<b-nav-item-dropdown right href="javascript:void(0);" id="account-menu"
					:class="{ 'router-link-active': subIsActive('/account') }" active-class="active" class="pointer">
					<span slot="button-content" class="navbar-dropdown-menu mojProfil">
						<font-awesome-icon icon="user" class="ikonica" />
						<span v-text="$t('global.menu.account.main')" class="headerItemi mojProf">
							Account
						</span>
					</span>
					<b-dropdown-item v-if="authenticated && portalUser && portalUser.company" :to="{name: 'CompanyView', params: {companyId: portalUser.company.id}}" tag="b-dropdown-item"  active-class="active">	
						<span v-text="'Moji podaci'">Moji podaci</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" tag="b-dropdown-item" v-if="authenticated" active-class="active">
						<span v-text="'RSNIS podaci'">RSNIS podaci</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" tag="b-dropdown-item" v-if="authenticated" active-class="active">
						<span v-text="'Unos dodatnih korisnika'">Unos dodatnih korisnika</span>
					</b-dropdown-item>
					<b-dropdown-item v-if="authenticated && portalUser && portalUser.company" :to="{name: 'CompanyAdvertisementsManage', params: {companyId: portalUser.company.id}}" tag="b-dropdown-item" active-class="active">
						<span v-text="'Oglasi'">Oglasi</span>
					</b-dropdown-item>
					<b-dropdown-item v-if="authenticated && portalUser && portalUser.company" :to="{name: 'CompanyCollaborations', params: {companyId: portalUser.company.id}}" tag="b-dropdown-item"  active-class="active">	
						<span v-text="'Saradnje'">Saradnje</span>
					</b-dropdown-item>
					<b-dropdown-item v-if="authenticated && portalUser && portalUser.company" :to="{name: 'CompanyThreads', params: {companyId: portalUser.company.id}}" tag="b-dropdown-item"  active-class="active">	
						<span v-text="'Poruke'">Poruke</span>
					</b-dropdown-item>
					<b-dropdown-item to="#" tag="b-dropdown-item" v-if="authenticated" active-class="active">
						<span v-text="'Kalendar'">Kalendar</span>
					</b-dropdown-item>

					<b-dropdown-item to="/account/settings" tag="b-dropdown-item" v-if="authenticated"
						active-class="active">
						<font-awesome-icon icon="wrench" />
						<span v-text="$t('global.menu.account.settings')">Settings</span>
					</b-dropdown-item>
					<b-dropdown-item to="/account/password" tag="b-dropdown-item" v-if="authenticated"
						active-class="active">
						<font-awesome-icon icon="lock" />
						<span v-text="$t('global.menu.account.password')">Password</span>
					</b-dropdown-item>
					<b-dropdown-item v-if="authenticated" v-on:click="logout()" id="logout" active-class="active">
						<font-awesome-icon icon="sign-out-alt" />
						<span v-text="$t('global.menu.account.logout')">Sign out</span>
					</b-dropdown-item>
					<b-dropdown-item v-if="!authenticated" v-on:click="openLogin()" id="login" active-class="active">
						<font-awesome-icon icon="sign-in-alt" />
						<span v-text="$t('global.menu.account.login')">Sign in</span>
					</b-dropdown-item>
					<b-dropdown-item to="/register" tag="b-dropdown-item" id="register" v-if="!authenticated"
						active-class="active">
						<font-awesome-icon icon="user-plus" />
						<span v-text="$t('global.menu.account.register')">Register</span>
					</b-dropdown-item>
				</b-nav-item-dropdown>

			</div>
		</div>
		<b-navbar toggleable="md" type="dark" class="all">


			<b-navbar-toggle right class="jh-navbar-toggler d-lg-none" href="javascript:void(0);" data-toggle="collapse"
				target="header-tabs" aria-expanded="false" aria-label="Toggle navigation">
				<font-awesome-icon icon="bars" />
			</b-navbar-toggle>

			<b-collapse is-nav id="header-tabs" style="flex-direction: row; width: 100%;">
				<b-navbar-nav class="prviNav">
					<!-- <b-nav-item to="/" exact>
                    <span>
                        <font-awesome-icon icon="home" />
                        <span v-text="$t('global.menu.home')">Home</span>
                    </span>
                </b-nav-item> -->


					<div class="selInp">



						<b-input-group class="input-grupa">
							<b-input-group-prepend is-text>
								<font-awesome-icon icon="search" scale="1.3"></font-awesome-icon>
							</b-input-group-prepend>
							<b-form-input class="inputPolje" type="text" placeholder="Претражи огласе...">

							</b-form-input>
							<div class="">
								<b-button class="divZaDugme primary btn btn-dark">Претражи</b-button>
							</div>
						</b-input-group>

						<div class="select">
							<b-input-group class="">
								<b-input-group-prepend is-text>
									<font-awesome-icon icon="search" scale="1.3"></font-awesome-icon>
								</b-input-group-prepend>
								<!-- <label class="form-control-label" v-text="$t('b2BportalApp.portalUser.company')" for="portal-user-company">Company</label> -->
								<select class="form-control" id="portal-user-company" name="company">

									<option value="" disabled selected hidden>Категорије</option>
									<option value="" disabled>Kategorija 1</option>
									<option value="" disabled>Kategorija 2</option>
									<!-- <option v-bind:value="null"></option> -->
									<!-- <option v-bind:value="portalUser.company && companyOption.id === portalUser.company.id ? portalUser.company : companyOption" v-for="companyOption in companies" :key="companyOption.id">{{companyOption.id}}</option> -->
								</select>
							</b-input-group>
						</div>


					</div>

				</b-navbar-nav>
			</b-collapse>
		</b-navbar>


		<b-navbar>

		</b-navbar>
	</div>
</template>

<script lang="ts" src="./b2b-jhi-navbar.component.ts">
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
	span span {
		display: none;
	}
}

.navbar-title {
	display: inline-block;
	vertical-align: bottom;
	font-size: 1em;
	color: rgb(146, 146, 146);

}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
	padding: 5px 15px;
}

.divZaDugme {
	width: auto;
	height: 2.2em;
	border-bottom-left-radius: 0px !important;
	border-top-left-radius: 0px !important;
	display: flex;
	align-items: center;
}

.prviNav {
	align-items: center;
	display: flex;
	justify-content: center;
}

.form-control {
	height: 2.2em !important;
}

.all {
	background: rgb(11, 62, 132);
	background: #1f3448 !important;
	border-bottom: 8px solid rgba(102, 124, 150, 255);
	box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.3);
	padding-top: 0.5em !important;
	height: 4.5em;
	box-shadow: 0px -5px 10px rgba(0, 0, 0, 0.3);
	border-bottom-left-radius: 5px;
	border-top-left-radius: 30px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 30px;
}

#searchbox {
	display: flex;
}

.inputPolje {
	width: 40em !important;
	flex: initial !important;
	height: 2em;
}

.selInp {
	display: flex;
	flex-direction: row;
	justify-content: center;
	width: 100%;
}

.headerItemi {
	font-size: 0.9em;

	color: #1f3448;
}

.headerItemi:hover {
	text-decoration: underline;
}

/* .nav-link { */
	/* border-right: 1px solid white; */
	/* padding-right: 2em; */
/* } */

#languagesnavBarDropdown {
	border-right: 1px solid rgb(255, 255, 255);
	/* Zamijenite #000 sa željenom bojom */
}

.navZero {
	display: flex;
	height: 4em;
	background-color: white;
	width: 100%;
	justify-content: space-between;
}

.divZaLogo {
	height: 2.2em;
	width: 7em;

	background-size: contain;
	background-repeat: no-repeat;
	background-position: center;
	margin-left: 5em;
	display: flex;
	align-items: center;
	margin-top: 1em;
}

.zalet {
	width: 10em;
	height: 4em;
	font-size: small;
	display: flex;
	align-items: flex-end;
	margin-left: 3px;
	font-weight: bolder;
	color: #1f3448;
}

.logoiZalet {
	display: flex;
	width: 25em;
}

.navItems {
	display: flex;
}

.navItems li {
	display: flex;
	justify-content: center;
	align-items: center;
}

.input-grupa {
	padding-top: 11px;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: row;
	width: 45vw;
}

#portal-user-company {
	width: 8em !important;
	flex: initial !important;

}

#header-tabs {
	display: flex;
	justify-content: center;
	align-items: center;
}

.select {
	margin-top: 10px;

}

.input-group-text {
	height: 2.2em;
}

.ceoHeader {
	width: 92%;
	margin-left: auto;
	margin-right: auto;

}

.highlight-option {
	border: 2px solid rgb(229, 55, 55);
	border-radius: 10px;
	margin: 10px;
}

.highlight-option:hover {
	background-color: rgb(233, 233, 233);
}

#account-menu {
	border: 2px solid rgb(45, 75, 106);
	border-radius: 10px;
	margin: 10px;
}

#account-menu:hover {
	background-color: rgb(233, 233, 233);
}

.mojProf:hover {
	text-decoration: none;
}

.ikonica {
	color: #1f3448;

}

@media screen and (max-width: 768px) {
	.divZaLogo {
		height: 2em;
		width: 4em;
		margin-left: 1.5em;
	}
}
</style>
