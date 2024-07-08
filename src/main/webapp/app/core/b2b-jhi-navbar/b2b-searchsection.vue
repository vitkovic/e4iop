<template>
  <div>
  <div v-if="cmsnews && cmsnews.length > 0" style="overflow-y: scroll; height:200px;">
  
   <div class="table-responsive" >
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th @click="changeOrder('id')">
                            <span v-text="$t('global.field.id')">ID</span>
                        </th>
                        <th @click="changeOrder('date')">
                            <span v-text="$t('riportalApp.cmsNews.date')">Date</span>
                        </th>
                        <th @click="changeOrder('title')">
                            <span v-text="$t('riportalApp.cmsNews.title')">Title</span>
                        </th>
                        <th @click="changeOrder('sequenceNumber')">
                            <span v-text="$t('riportalApp.cmsNews.sequenceNumber')">Sequence Number</span>
                        </th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="news in cmsnews" :key="news.id">
                        <td>
                            {{ news.id }}
                        </td>
                        <td>{{ news.date ? $d(Date.parse(news.date), 'short') : '' }}</td>
                        <td>{{ news.title }}</td>
                        <td>{{ news.sequenceNumber }}</td>
                        <td class="text-right">
                          
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
   </div>
   <div v-if="advertisements && advertisements.length > 0" style="overflow-y: scroll; height:200px;">
  
   <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!-- <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.advertisement.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.advertisement.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th> -->

                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.advertisement.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('description')"><span v-text="$t('riportalApp.advertisement.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('conditions')"><span v-text="$t('riportalApp.advertisement.conditions')">Conditions</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'conditions'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.advertisement.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.advertisement.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('status.id')"><span v-text="$t('riportalApp.advertisement.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('type.id')"><span v-text="$t('riportalApp.advertisement.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('kind.id')"><span v-text="$t('riportalApp.advertisement.kind')">Kind</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('subsubcategory.name')"><span v-text="$t('riportalApp.advertisement.subsubcategory')">Subsubcategory</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subsubcategory.name'"></jhi-sort-indicator></th>  
                    <th v-on:click="changeOrder('budget')"><span v-text="$t('riportalApp.advertisement.budget')">Budget</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'budget'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('company.id')"><span v-text="$t('riportalApp.advertisement.company')">Company</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('activationDatetime')"><span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span> 
                    <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationDatetime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('expirationDatetime')"><span v-text="$t('riportalApp.advertisement.expirationDatetime')">Expiration Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expirationDatetime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('deletionDatetime')"><span v-text="$t('riportalApp.advertisement.deletionDatetime')">Deletion Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deletionDatetime'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisement in advertisements"
                    :key="advertisement.id">
                    <!-- <td>
                        <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisement.id}}">{{advertisement.id}}</router-link>
                    </td> -->
                    <!-- <td>{{advertisement.createdAt ? $d(Date.parse(advertisement.createdAt), 'short') : ''}}</td> -->
                    <!-- <td>{{advertisement.modifiedAt ? $d(Date.parse(advertisement.modifiedAt), 'short') : ''}}</td> -->
                    <td>{{advertisement.title}}</td>
                    <!-- <td>{{advertisement.description}}</td> -->

                    <!-- <td>{{advertisement.conditions}}</td> -->
                    <!-- <td>
                        <div v-if="advertisement.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: advertisement.createdBy.id}}">{{advertisement.createdBy.firstname}}</router-link>
                        </div>
                    </td> -->
                    <!-- <td>
                        <div v-if="advertisement.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: advertisement.modifiedBy.id}}">{{advertisement.modifiedBy.firstname}}</router-link>
                        </div>
                    </td> -->

                    <td>
                        <div v-if="advertisement.status">
                            {{advertisement.status.status}}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.type">
                            {{advertisement.type.type}}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.kind">
                            {{advertisement.kind.kind}}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.subsubcategory">
                            {{advertisement.subsubcategory.name}}
                        </div>
                    </td>
                    <td>{{advertisement.budget}}</td>
                    <td>
                        <div v-if="advertisement.company">
                            <router-link :to="{name: 'CompanyView', params: {companyId: advertisement.company.id}}">{{advertisement.company.name}}</router-link>
                        </div>
                    </td>             
                    <td>{{ advertisement.activationDatetime ? $d(Date.parse(advertisement.activationDatetime.toString()), { dateStyle: 'short' }) : ''}}</td>
                    <td>{{ advertisement.expirationDatetime ? $d(Date.parse(advertisement.expirationDatetime.toString()), { dateStyle: 'short' }) : '' }}</td>
                    <td>{{ advertisement.deletionDatetime ? $d(Date.parse(advertisement.deletionDatetime.toString()), { dateStyle: 'short' }) : '' }}</td>
                    <td class="text-right">
                       
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
  
   </div>
  
   <div v-if="cmsquestions && cmsquestions.length > 0" style="overflow-y: scroll; height:200px;">
   
    <div class="table-responsive" v-if="cmsquestions && cmsquestions.length > 0">
    
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.cmsQuestion.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.cmsQuestion.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('question')"><span v-text="$t('riportalApp.cmsQuestion.question')">Question</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'question'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('answer')"><span v-text="$t('riportalApp.cmsQuestion.answer')">Answer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'answer'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.cmsQuestion.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.cmsQuestion.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th> -->
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsQuestion in cmsquestions"
                    :key="cmsQuestion.id">
                    <td>
                        <router-link :to="{name: 'CmsQuestionView', params: {cmsQuestionId: cmsQuestion.id}}">{{cmsQuestion.id}}</router-link>
                    </td>
                    <td>{{cmsQuestion.createdAt ? $d(Date.parse(cmsQuestion.createdAt.toString()), 'short') : ''}}</td>
                    <!-- <td>{{cmsQuestion.modifiedAt ? $d(Date.parse(cmsQuestion.modifiedAt), 'short') : ''}}</td> -->
                    <td>{{cmsQuestion.question}}</td>
                    <td>{{cmsQuestion.answer}}</td>
                    <!-- <td>
                        <div v-if="cmsQuestion.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsQuestion.createdBy.id}}">{{cmsQuestion.createdBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsQuestion.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsQuestion.modifiedBy.id}}">{{cmsQuestion.modifiedBy.id}}</router-link>
                        </div>
                    </td> -->
                    <td class="text-right">
                     
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
  </div>
  <div v-if="companies && companies.length > 0" style="overflow-y: scroll; height:200px;">
    <div class="table-responsive" v-if="companies && companies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!-- <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('name')"><span v-text="$t('riportalApp.company.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rsnisId')"><span v-text="$t('riportalApp.company.rsnisId')">Rsnis Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rsnisId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('aprId')"><span v-text="$t('riportalApp.company.aprId')">Apr Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aprId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fields')"><span v-text="$t('riportalApp.company.fields')">Fields</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fields'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('about')"><span v-text="$t('riportalApp.company.about')">About</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'about'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.company.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.company.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.company.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.company.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('logo.id')"><span v-text="$t('riportalApp.company.logo')">Logo</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'logo.id'"></jhi-sort-indicator></th> -->
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="company in companies"
                    :key="company.id">
                    <!-- <td>
                        <router-link :to="{name: 'CompanyView', params: {companyId: company.id}}">{{company.id}}</router-link>
                    </td> -->
                    <td>{{company.name}}</td>
                    <td>{{company.rsnisId}}</td>
                    <td>{{company.aprId}}</td>
                    <!-- <td>{{company.about}}</td> -->
                    <td>{{company.fields}}</td>
                    <td>{{company.createdAt ? $d(Date.parse(company.createdAt), 'short') : ''}}</td>
                    <td>{{company.modifiedAt ? $d(Date.parse(company.modifiedAt), 'short') : ''}}</td>
                    <td>
                        <div v-if="company.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: company.createdBy.id}}">{{company.createdBy.firstname}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="company.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: company.modifiedBy.id}}">{{company.modifiedBy.firstname}}</router-link>
                        </div>
                    </td>
                    <!-- <td>
                        <div v-if="company.logo">
                            <router-link :to="{name: 'DocumentView', params: {documentId: company.logo.id}}">{{company.logo.id}}</router-link>
                        </div>
                    </td> -->
                    <td class="text-right">
                        
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
      </div>
  </div>
</template>

<script lang="ts" src="./b2b-searchsection.component.ts"></script>


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
  border-right: 1px solid white;
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
  margin-left: 5em;
  display: flex;
  align-items: center;
  margin-top: 1em;
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
  align-items: flex-end;
  margin-left: 3px;
  font-weight: bolder;
  color: #1f3448;
}

.logoiZalet {
  display: flex;
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
    margin-left: 1.5em;
  }

  .all {
    height: 7em;
  }

 

  #portal-user-company {
    width: 32em !important;
  }
 
}

 select {display: flex; max-width:100%}

</style>
