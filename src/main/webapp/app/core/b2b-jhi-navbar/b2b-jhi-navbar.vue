<template>
  <div class="ceoHeader">
    <div class="mainHeader">
      <b-navbar toggleable="lg" class="mainNav">
        <b-navbar-brand>
          <div class="divLogo d-flex">
            <router-link to="/b2b">
              <div class="logoiZalet">
                <img src="/content/images/b2b1.png" alt="B2B logo" class="divZaLogo" />
                <span class="zalet" v-text="$t('global.menu.innovationBoost')">Залет иновација</span>
              </div>
            </router-link>
          </div>
        </b-navbar-brand>

        <b-navbar-toggle
          right
          class="jh-navbar-toggler d-lg-none"
          href="javascript:void(0);"
          data-toggle="collapse"
          target="nav-collapse"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <font-awesome-icon icon="bars" class="burger"
        /></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav class="mainCollapse">
          <b-navbar-nav class="ml-auto align-items-center">
            <b-nav-item to="/">
              <span>
                <img src="/content/images/OII_sm_sm_nt.png" alt="" class="riportalLogo" />
              </span>
            </b-nav-item>

            <b-nav-item-dropdown right active-class="active">
              <span slot="button-content" class="navbar-dropdown-menu">
                <span class="headerItemi" v-text="$t('global.menu.advertisements.main')">Oglasi</span>
              </span>
              <b-dropdown-item :to="{ name: 'AdvertisementGet' }">
                <span v-text="$t('global.menu.advertisements.advertisesearch')">Svi oglasi</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'AdvertisementCreate', query: { type: advertisementTypeOptions.OFFER } }">
                <span v-text="$t('global.menu.advertisements.advertiseOffer')">Oglasi ponudu</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'AdvertisementCreate', query: { type: advertisementTypeOptions.DEMAND } }">
                <span v-text="$t('global.menu.advertisements.advertiseDemand')">Oglasi potražnju</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'AdvertisementSearch', query:{type:'3451'} }">
                <span v-text="$t('global.menu.advertisements.findAllOffers')">Pogledaj sve ponude</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'AdvertisementSearch',query:{type:'3452'} }">
                <span v-text="$t('global.menu.advertisements.findAllDemands')">Pogledaj sve potražnje</span>
              </b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item to="/b2b/about" exact style="white-space: nowrap;" class="navbar-menu-item">
              <span>
                <span class="headerItemi" v-text="$t('global.menu.about')">О нама</span>
              </span>
            </b-nav-item>

            <b-nav-item to="/b2b/questions" exact style="white-space: nowrap;" class="navbar-menu-item">
              <span>
                <span class="headerItemi" v-text="$t('global.menu.faq')">Најчешћа питања</span>
              </span>
            </b-nav-item>

            <b-nav-item to="/b2b/news" exact style="white-space: nowrap;" class="navbar-menu-item">
              <span>
                <span class="headerItemi" v-text="$t('global.menu.news')">Најчешћа питања</span>
              </span>
            </b-nav-item>

            <b-nav-item-dropdown
              right
              id="cms-admin-menu"
              v-if="(isAdmin || isCMSAdmin || isCMSSuperAdmin) && authenticated"
              :class="{ 'router-link-active': subIsActive('/admin') }"
              active-class="active"
            >
              <span slot="button-content" class="boja">
                <font-awesome-icon icon="cogs" />
                <span v-text="'CMS'">CMS</span>
              </span>
              <b-dropdown-item v-if="(isAdmin || isCMSSuperAdmin) && authenticated" :to="{ name: 'CMSAdministrators' }" active-class="active">
                <span v-text="$t('global.menu.cms.admins')">Upravljanje administratorima</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'CMSUserManagement' }" active-class="active">
                <span v-text="$t('global.menu.cms.users')">Upravljanje korisnicima</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'CMSAdvertisements' }" active-class="active">
                <span v-text="$t('global.menu.cms.advertisements')">Upravljanje oglasima</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'CMSPages' }" active-class="active">
                <span v-text="$t('global.menu.cms.pages')">Upravljanje stranicama</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'CMSCodebook' }" active-class="active">
                <span v-text="$t('global.menu.cms.codebooks')">Šifarnici</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'CMSNews' }" active-class="active">
                <span v-text="$t('global.menu.cms.news')">Vesti</span>
              </b-dropdown-item>
              <b-dropdown-item :to="{ name: 'CMSReports' }" active-class="active">
                <span v-text="$t('global.menu.cms.reports')">Izveštaji</span>
              </b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item-dropdown right id="entity-menu" v-if="isAdmin && authenticated" active-class="active">
              <span slot="button-content" class="boja">
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
              <b-dropdown-item to="/portal-user">
                <font-awesome-icon icon="asterisk" />
                <span v-text="$t('global.menu.entities.portalUser')">PortalUser</span>
              </b-dropdown-item>
              <b-dropdown-item to="/portal-user-status">
                <font-awesome-icon icon="asterisk" />
                <span v-text="$t('global.menu.entities.portalUserStatus')">PortalUserStatus</span>
              </b-dropdown-item>
              <b-dropdown-item to="/portal-user-role">
                <font-awesome-icon icon="asterisk" />
                <span v-text="$t('global.menu.entities.portalUserRole')">PortalUserRole</span>
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
                <span v-text="$t('global.menu.entities.advertisementSubsubcategory')">AdvertisementSubsubcategory</span>
              </b-dropdown-item>
              <b-dropdown-item to="/b2b/collaboration">
                <font-awesome-icon icon="asterisk" />
                <span v-text="$t('global.menu.entities.collaboration')">Collaboration</span>
              </b-dropdown-item>
              <b-dropdown-item to="/b2b/collaboration-rating">
                <font-awesome-icon icon="asterisk" />
                <span v-text="$t('global.menu.entities.collaborationRating')">CollaborationRating</span>
              </b-dropdown-item>
              <b-dropdown-item to="/b2b/collaboration-status">
                <font-awesome-icon icon="asterisk" />
                <span v-text="$t('global.menu.entities.collaborationStatus')">CollaborationStatus</span>
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
            </b-nav-item-dropdown>

            <b-nav-item-dropdown
              right
              id="admin-menu"
              v-if="isAdmin && authenticated"
              :class="{ 'router-link-active': subIsActive('/admin') }"
              active-class="active"
            >
              <span slot="button-content" class="boja">
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

            <b-nav-item-dropdown right v-if="languages && Object.keys(languages).length > 1">
              <span slot="button-content">
                <font-awesome-icon icon="flag" class="ikonica" />
                <span v-text="$t('global.menu.language')" class="headerItemi jezik">Language</span>
              </span>
              <b-dropdown-item
                v-for="(value, key) in languages"
                :key="`lang-${key}`"
                v-on:click="changeLanguage(key)"
                :class="{ active: isActiveLanguage(key) }"
              >
                {{ value.name }}
              </b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item-dropdown
              right
              href="javascript:void(0);"
              id="account-menu"
              :class="{ 'router-link-active': subIsActive('/account') }"
              active-class="active"
              class="boja profileMenu"
            >
              <span slot="button-content" class="mojProfil">
                <font-awesome-icon icon="user" class="ikonica" />
                <span v-text="$t('global.menu.account.main')" class="headerItemi mojProf">
                  Account
                </span>
              </span>
              <b-dropdown-item
                :to="{ name: 'CompanyView', params: { companyId: portalUser.company.id } }"
                tag="b-dropdown-item"
                v-if="authenticated && portalUser && portalUser.company"
                active-class="active"
              >
                <span v-text="$t('global.menu.account.accountData')">Moji podaci</span>
              </b-dropdown-item>
              <!-- <b-dropdown-item to="#" tag="b-dropdown-item" v-if="authenticated" active-class="active">
                  <span v-text="$t('global.menu.account.rsnisData')">RSNIS podaci</span>
                </b-dropdown-item> -->
              <b-dropdown-item v-if="isCompanyAdmin && authenticated && portalUser && portalUser.company"
                :to="{ name: 'CompanyPortalUsers', params: { companyId: portalUser.company.id } }"
                tag="b-dropdown-item"
                active-class="active"
              >
                <span v-text="$t('global.menu.account.additionalusers')">Unos dodatnih korisnika</span>
              </b-dropdown-item>
              <b-dropdown-item
                v-if="authenticated && portalUser && portalUser.company"
                :to="{ name: 'CompanyAdvertisementsManage', params: { companyId: portalUser.company.id } }"
                tag="b-dropdown-item"
                active-class="active"
              >
                <span v-text="$t('global.menu.account.advertisements')">Oglasi</span>
              </b-dropdown-item>
              <b-dropdown-item
                v-if="authenticated && portalUser && portalUser.company"
                :to="{ name: 'CompanyCollaborations', params: { companyId: portalUser.company.id } }"
                tag="b-dropdown-item"
                active-class="active"
              >
                <span v-text="$t('global.menu.account.collaborations')">Saradnje</span>
              </b-dropdown-item>
              <b-dropdown-item
                v-if="authenticated && portalUser && portalUser.company"
                :to="{ name: 'CompanyThreads', params: { companyId: portalUser.company.id } }"
                tag="b-dropdown-item"
                active-class="active"
              >
                <span v-text="$t('global.menu.account.messages')">Poruke</span>
              </b-dropdown-item>
              <b-dropdown-item
                v-if="authenticated && portalUser && portalUser.company"
                :to="{ name: 'CompanyCalendar', params: { companyId: portalUser.company.id } }"
                tag="b-dropdown-item"
                active-class="active"
              >
                <span v-text="$t('global.menu.account.calendar')">Kalendar</span>
              </b-dropdown-item>

              <b-dropdown-item to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
                <font-awesome-icon icon="wrench" />
                <span v-text="$t('global.menu.account.settings')">Settings</span>
              </b-dropdown-item>
              <b-dropdown-item to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
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
              <b-dropdown-item to="/register" tag="b-dropdown-item" id="register" v-if="!authenticated" active-class="active">
                <font-awesome-icon icon="user-plus" />
                <span v-text="$t('global.menu.account.register')">Register</span>
              </b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>

    <b-navbar-nav class="prviNav all d-flex justify-content-center align-items-center">
      <div class="container">
        <div class="row">
          <div class="col-sm-8 col-md-8 mb-2 mb-sm-0">
            <!-- Širina 70% na većim ekranima -->
            <div class="selInp">
              <b-input-group >
                <b-input-group-prepend is-text >
                  <font-awesome-icon icon="search" scale="1.3"></font-awesome-icon>
                </b-input-group-prepend>
                <b-form-input
                  class="inputPoljeC"
                  v-model="txtsearchNav"
                  ref="searchadv"
                  id="searchadv"
                  name="searchadv"
                  type="text"
                  @keyup.enter="searchAdvSub()"
                  @keyup = "autoAdvSub()"
                  @click = "autoAdvSub()"
                  :placeholder="$t('global.menu.searchAd')"
                ></b-form-input>
                <div class="input-group-append">
                  <b-button class="primary btn btn-dark" @click="searchAdvSub()">{{ $t('entity.action.searchBar') }}</b-button>
                </div>
              </b-input-group>
            </div>
          </div>
          <!--<div class="col-sm-4 col-md-4" style="max-width:100%">
          
                <select class="form-control"  v-model="mainSearchCategory" id="kat" name="kat" @click="" >
                    <option  v-for="element in advCategList" :key="element.id" :value="element.id" >{{element.name}}</option>
                </select>
               
              </div>-->
           <div class="col-sm-4 col-md-4" style="max-width:100%">
          
                <select class="form-control"  v-model="mainSearchSubCategory" id="katsub" name="katsub">
                    <option  v-for="element in advSubCategList" :key="element.id" :value="element.id" >{{  getElementName(element) }}</option>
                </select>
                <!-- @change="autoAdvSub()" -->
               
              </div>
         <!-- <div class="col-sm-2 col-md-4" style="max-width: 100%;">
            <multiselect
              v-model="valuetype"
              :options="options"
              :multiple="true"
              :close-on-select="false"
              :clear-on-select="false"
              :preserve-search="true"
              placeholder="Одаберите елемент претраживања"
              label="name"
              track-by="name"
              :preselect-first="true"
            >
              <template #selection="{ values, search, isOpen }">
                <span class="multiselect__single" v-if="values.length" v-show="!isOpen">{{ values.length }} options selected</span>
              </template>
            </multiselect>
          </div>-->
        </div>
      </div>
    </b-navbar-nav>

    <div class="container">
      <div class="row">
        &nbsp;
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./b2b-jhi-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
}

@media screen and (min-width: 1000px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span {
    /* display: none; */
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: bottom;
  font-size: 1em;
  color: rgb(146, 146, 146);
}

.mainNav {
  padding: 0 0 10px 0;
}

.navbar-light .navbar-toggler {
  border: 2px solid #1f3448;
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

.selInp {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.headerItemi {
  font-size: 0.9em;
  color: #1f3448;
}

.jezik {
  font-size: 0.8em;
}

.headerItemi:hover {
  text-decoration: underline;
}

.nav-link {
  padding-right: 2em;
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
  /* margin-left: 5em; */
  display: flex;
  align-items: center;
  /* margin-top: 1em; */
}

.riportalLogo {
  height: 40px;
}

.mojaNeka {
  color: red;
}

.zalet {
  width: 10em;
  height: 4em;
  font-size: small;
  display: flex;
  align-items: center;
  margin-top: 25px;
  margin-left: 3px;
  font-weight: bolder;
  color: #1f3448;
}

.logoiZalet {
  display: flex;
  align-items: center;
  margin-left: 25px;
}

.navItems {
  display: flex;
}

.navItems li {
  display: flex;
  justify-content: center;
  align-items: center;
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

.input-group-text {
  height: 2.2em;
}

.ceoHeader {
  width: 92%;
  margin-left: auto;
  margin-right: auto;
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

.burger {
  color: #1f3448;
}

.input-group {
  flex-wrap: nowrap;
}

.klasa {
  border: none;
}

.boja {
  color: #1f3448;
  font-size: 0.9em;
}

/* .pointer span {
	color: #1f3448 !important;
	font-size: 0.9em;
} */

.dropdown-menu-left {
  position: absolute;
  margin-top: 23em;
  background-color: white;
  z-index: 5;
  margin-left: -3em;
  padding: 1em;
  border-radius: 10px;
}

.nasaKlasa {
  position: relative;
}



.input-group-append .btn {
  height: auto;
  /* Postavite visinu na auto kako bi se prilagodila sadržaju */
  padding: 5px 10px;
  /* Prilagodite padding po potrebi */
  font-size: 14px;
  /* Prilagodite veličinu fonta po potrebi */
}

@media screen and (max-width: 786px) {
  .divZaLogo {
    height: 2em;
    width: 4em;
    margin-left: none;
  }

  .all {
    height: 7em;
  }

  #portal-user-company {
    width: 32em !important;
  }

    .profileMenu {
    padding-left: 8px;
    padding-right: 8px;
  }
}

select {
  display: flex;
  max-width: 100%;
}
</style>
