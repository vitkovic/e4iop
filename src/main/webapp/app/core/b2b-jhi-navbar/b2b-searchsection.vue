<template>
  <div>
 
   <div v-if="advertisements && advertisements.length > 0" style="overflow-y: scroll; height:500px;">
  
   <div class="custom-table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!-- <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.advertisement.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.advertisement.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th> -->

                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.advertisement.title')">Title</span> </th>
                    <!-- <th v-on:click="changeOrder('description')"><span v-text="$t('riportalApp.advertisement.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('conditions')"><span v-text="$t('riportalApp.advertisement.conditions')">Conditions</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'conditions'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.advertisement.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th> -->
                    <!-- <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.advertisement.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('status.id')"><span v-text="$t('riportalApp.advertisement.status')">Status</span> </th>
                    <th v-on:click="changeOrder('type.id')"><span v-text="$t('riportalApp.advertisement.type')">Type</span> </th>
                    <th v-on:click="changeOrder('kind.id')"><span v-text="$t('riportalApp.advertisement.kind')">Kind</span> </th>
                    <th v-on:click="changeOrder('subsubcategory.name')"><span v-text="$t('riportalApp.advertisement.subsubcategory')">Subsubcategory</span> </th>  
                    <th v-on:click="changeOrder('budget')"><span v-text="$t('riportalApp.advertisement.budget')">Budget</span> </th>
                    <th v-on:click="changeOrder('company.id')"><span v-text="$t('riportalApp.advertisement.company')">Company</span> </th>
                    <th v-on:click="changeOrder('activationDatetime')"><span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span> 
                    </th>
                    <th v-on:click="changeOrder('expirationDatetime')"><span v-text="$t('riportalApp.advertisement.expirationDatetime')">Expiration Date</span></th>
                    <th v-on:click="changeOrder('deletionDatetime')"><span v-text="$t('riportalApp.advertisement.deletionDatetime')">Deletion Date</span> </th>
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
                          <span v-if="$store.getters.currentLanguage === 'sr'">{{ advertisement.status.status }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'src'">{{ advertisement.status.statusSrc }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'en'">{{ advertisement.status.statusEn }}</span>
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.type">
                          <span v-if="$store.getters.currentLanguage === 'sr'">{{ advertisement.type.type }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'src'">{{ advertisement.type.typeSrc }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'en'">{{ advertisement.type.typeEn }}</span>
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.kinds">
                          {{ advertisementKindsString(advertisement) }}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.subsubcategory">
                          {{ advertisementCategorizationBranch(advertisement) }}
                        </div>
                    </td>
                    <td>
                      <div v-if="advertisement.budget">
                        {{advertisement.budget.toLocaleString('sr-SR', { style: 'currency', currency: 'RSD' })}}
                      </div>
                    </td>
                    <td>
                        <div v-if="advertisement.company">
                            <router-link :to="{name: 'CompanyView', params: {companyId: advertisement.company.id}}" class="text-body">{{advertisement.company.name}}</router-link>
                        </div>
                    </td>             
                    <td>{{ advertisement.activationDatetime ? $d(Date.parse(advertisement.activationDatetime.toString()), { dateStyle: 'short' }) : ''}}</td>
                    <td>{{ advertisement.expirationDatetime ? $d(Date.parse(advertisement.expirationDatetime.toString()), { dateStyle: 'short' }) : '' }}</td>
                    <td>{{ advertisement.deletionDatetime ? $d(Date.parse(advertisement.deletionDatetime.toString()), { dateStyle: 'short' }) : '' }}</td>
                    <td class="text-right">
                       
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisement.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span v-text="$t('entity.action.view')">View</span>
                            </router-link>
                    <!--        <router-link v-if="advertisement.status.status === 'Активан'" :to="{name: 'AdvertisementEdit', params: {advertisementId: advertisement.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-if="advertisement.status.status === 'Активан'" v-on:click="prepareDeactivate(advertisement)"
                                   variant="dark"
                                   class="btn btn-sm"
                                   v-b-modal.deactivateEntity>
                                <!-- <font-awesome-icon icon="times"></font-awesome-icon> 
                                <span class="d-none d-md-inline">Deaktiviraj</span>
                            </b-button>
                            <b-button v-if="['Неактиван', 'Архивиран'].includes(advertisement.status.status)" v-on:click="prepareActivate(advertisement)"
                                   variant="success"
                                   class="btn btn-sm"
                                   v-b-modal.activateEntity>
                                   <!-- <font-awesome-icon icon="check"></font-awesome-icon> 
                                <span class="d-none d-md-inline">Aktiviraj</span>
                            </b-button>
                            <b-button v-if="advertisement.status.status === 'Неактиван'" v-on:click="prepareSoftDelete(advertisement)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.softDeleteEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                            <b-button v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')" v-on:click="prepareRemove(advertisement)"
                                   variant="outline-danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="'Obriši iz baze'">Obriši iz baze</span>
                            </b-button>-->
                        </div>
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

.custom-table-responsive {
  overflow-x: auto;
}

@media (max-width: 1280px) {
  .custom-table-responsive table {
    min-width: 1280px;
  }
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
