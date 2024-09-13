<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-9">
        <h3 class="mb-5" v-text="$t('riportalApp.cmsB2B.pages.reports')"></h3>

        <div>
          <b-tabs content-class="mt-3">
            <b-tab class="tab-header" :title="$t('riportalApp.advertisement.home.title')" active>
              <div class="d-flex">
                <div class="form-group">
                  <input id="advCountId" type="text" class="form-control" name="advCountName" v-model="advCount" />
                </div>
              </div>

              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.category')" for="kat">Category</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="mainSearchCategory"
                  id="kat"
                  name="kat"
                  @change="retrieveAdvertisementsByCategory()"
                >
                  <option v-for="element in advCategList" :key="element.id" :value="element.id">{{ element.name }}</option>
                </select>
              </div>

              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.type')" for="typ">Type</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="advType"
                  id="typ"
                  name="typ"
                  @change="retrieveAdvertisementsByType()"
                >
                  <option v-for="element in advTypeList" :key="element.id" :value="element.id">{{ element.type }}</option>
                </select>
              </div>

              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.kind')" for="kin">Kind</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="advKind"
                  id="kin"
                  name="kin"
                  @change="retrieveAdvertisementsByKind()"
                >
                  <option v-for="element in advKindList" :key="element.id" :value="element.id">{{ element.kind }}</option>
                </select>
              </div>

              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.company')" for="comp">Company</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="advCompany"
                  id="comp"
                  name="comp"
                  @change="retrieveAdvertisementsByCompany()"
                >
                  <option v-for="element in advCompanyList" :key="element.id" :value="element.id">{{ element.name }}</option>
                </select>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.activationDatetimeFrom')" for="activationDatetimeFrom"
                  >Activation Datetime From</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeFrom"
                    v-model="activationDatetimeFrom"
                    type="datetime-local"
                    class="form-control"
                    name="activationDatetimeFrom"
                    @change="updateInstantFieldFrom($event)"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.activationDatetimeТо')" for="activationDatetimeTo"
                  >Activation Datetime To</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeTo"
                    type="datetime-local"
                    v-model="activationDatetimeTo"
                    class="form-control"
                    name="activationDatetimeTo"
                    @change="updateInstantFieldTo($event)"
                  />
                </div>
              </div>

              <div>
                <b-button v-text="$t('riportalApp.reports.exportcsv')" v-on:click="exportCSVFile('advertisements', 'advertisements')"
                  >Cancel</b-button
                >
              </div>
              <div class="table-responsive" v-if="advertisements && advertisements.length > 0">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th v-on:click="changeOrder('title')">
                        <span v-text="$t('riportalApp.advertisement.title')">Title</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('status.id')">
                        <span v-text="$t('riportalApp.advertisement.status')">Status</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.id'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('type.id')">
                        <span v-text="$t('riportalApp.advertisement.type')">Type</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('kind.id')">
                        <span v-text="$t('riportalApp.advertisement.kind')">Kind</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.id'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('subsubcategory.name')">
                        <span v-text="$t('riportalApp.advertisement.subsubcategory')">Subsubcategory</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'subsubcategory.name'"
                        ></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('budget')">
                        <span v-text="$t('riportalApp.advertisement.budget')">Budget</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'budget'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('company.id')">
                        <span v-text="$t('riportalApp.advertisement.company')">Company</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.id'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('activationDatetime')">
                        <span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'activationDatetime'"
                        ></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrder('expirationDatetime')">
                        <span v-text="$t('riportalApp.advertisement.expirationDatetime')">Expiration Date</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'expirationDatetime'"
                        ></jhi-sort-indicator>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="advertisement in advertisements" :key="advertisement.id">
                      <td>{{ advertisement.title }}</td>
                      <td>
                        <div v-if="advertisement.status">
                          {{ advertisement.status.status }}
                        </div>
                      </td>
                      <td>
                        <div v-if="advertisement.type">
                          {{ advertisement.type.type }}
                        </div>
                      </td>
                      <td>
                        <div v-if="advertisement.kind">
                          {{ advertisement.kind.kind }}
                        </div>
                      </td>
                      <td>
                        <div v-if="advertisement.subsubcategory">
                          {{ advertisement.subsubcategory.name }}
                        </div>
                      </td>
                      <td>
                        <div v-if="advertisement.budget">
                          {{ advertisement.budget.toLocaleString('us-US', { style: 'currency', currency: 'RSD' }) }}
                        </div>
                      </td>
                      <td>
                        <div v-if="advertisement.company">
                          <router-link :to="{ name: 'CompanyView', params: { companyId: advertisement.company.id } }" class="text-body">{{
                            advertisement.company.name
                          }}</router-link>
                        </div>
                      </td>
                      <td>
                        {{
                          advertisement.activationDatetime
                            ? $d(Date.parse(advertisement.activationDatetime.toString()), { dateStyle: 'short' })
                            : ''
                        }}
                      </td>
                      <td>
                        {{
                          advertisement.expirationDatetime
                            ? $d(Date.parse(advertisement.expirationDatetime.toString()), { dateStyle: 'short' })
                            : ''
                        }}
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </b-tab>
            <b-tab class="tab-header" :title="$t('riportalApp.collaboration.home.title')"
              ><p>Collaborations</p>

              <div class="d-flex">
                <div class="form-group">
                  <input id="collabCountId" type="text" class="form-control" name="collabCountId" v-model="collabCount" />
                </div>
              </div>

              <div>&nbsp;</div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.creationCollabDatetimeFrom')" for="activationDatetimeFrom"
                  >Activation Datetime From</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeFrom"
                    v-model="activationDatetimeFrom"
                    type="datetime-local"
                    class="form-control"
                    name="activationDatetimeFrom"
                    @change="updateInstantFieldFromCollab($event)"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.creationCollabDatetimeТо')" for="activationDatetimeTo"
                  >Activation Datetime To</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeTo"
                    type="datetime-local"
                    v-model="activationDatetimeTo"
                    class="form-control"
                    name="activationDatetimeTo"
                    @change="updateInstantFieldToCollab($event)"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.collaboration.status')" for="kat">Coolaborations</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="collabStatus"
                  id="collab"
                  name="collab"
                  @change="retrieveFilteredCollaborations()"
                >
                  <option v-for="element in collabStatusList" :key="element.id" :value="element.id">
                    <span v-if="$store.getters.currentLanguage === 'sr'">{{ element.status }}</span>
                    <span v-else-if="$store.getters.currentLanguage === 'src'">{{ element.statusSrc }}</span>
                    <span v-else-if="$store.getters.currentLanguage === 'en'">{{ element.statusEn }}</span>
                  </option>
                </select>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.type')" for="typ">Type</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="advType"
                  id="typ"
                  name="typ"
                  @change="retrieveFilteredCollaborations()"
                >
                  <option v-for="element in advTypeList" :key="element.id" :value="element.id">
                    <span v-if="$store.getters.currentLanguage === 'sr'">{{ element.type }}</span>
                    <span v-else-if="$store.getters.currentLanguage === 'src'">{{ element.typeSrc }}</span>
                    <span v-else-if="$store.getters.currentLanguage === 'en'">{{ element.typeEn }}</span>
                  </option>
                </select>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.kind')" for="kin">Kind</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="advKind"
                  id="kin"
                  name="kin"
                  @change="retrieveFilteredCollaborations()"
                >
                  <option v-for="element in advKindList" :key="element.id" :value="element.id">
                    <span v-if="$store.getters.currentLanguage === 'sr'">{{ element.kind }}</span>
                    <span v-else-if="$store.getters.currentLanguage === 'src'">{{ element.kindSrc }}</span>
                    <span v-else-if="$store.getters.currentLanguage === 'en'">{{ element.kindEn }}</span>
                  </option>
                </select>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.advertisement.categorization')" for="kat">Category</label>
                <select
                  class="form-control"
                  style="width: 100%; max-width: 100%;"
                  v-model="advSubsubcategory"
                  id="kat"
                  name="kat"
                  @change="retrieveFilteredCollaborations()"
                >
                  <option v-for="element in advSubsubcategoryList" :key="element.id" :value="element">{{
                    advertisementCategorizationBranch(element)
                  }}</option>
                </select>
              </div>
              <div>&nbsp;</div>

              <b-button v-text="$t('riportalApp.reports.exportcsv')" v-on:click="exportCSVFile('collaborations', 'collaborations')"
                >Export</b-button
              >

              <div class="table-responsive" v-if="collaborations && collaborations.length > 0">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th v-on:click="changeOrderCollab('id')">
                        <span v-text="$t('global.field.id')">ID</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderCollab('datetime')">
                        <span v-text="$t('riportalApp.collaboration.datetime')">Datetime</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator>
                      </th>
                      <!-- <th v-on:click="changeOrderCollab('commentOffer')"><span v-text="$t('riportalApp.collaboration.commentOffer')">Comment Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'commentOffer'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('commentRequest')"><span v-text="$t('riportalApp.collaboration.commentRequest')">Comment Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'commentRequest'"></jhi-sort-indicator></th> -->
                      <th v-on:click="changeOrderCollab('companyOffer.id')">
                        <span v-text="$t('riportalApp.collaboration.companyOffer')">Company Offer</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'companyOffer.id'"
                        ></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderCollab('companyRequest.id')">
                        <span v-text="$t('riportalApp.collaboration.companyRequest')">Company Request</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'companyRequest.id'"
                        ></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderCollab('advertisement.id')">
                        <span v-text="$t('riportalApp.collaboration.advertisement')">Advertisement</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'advertisement.id'"
                        ></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderCollab('advertisement.type.type')">
                        <span v-text="$t('riportalApp.collaboration.advertisementType')">Advertisement</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'advertisement.type.type'"
                        ></jhi-sort-indicator>
                      </th>
                      <th>
                        <span v-text="$t('riportalApp.collaboration.advertisementKind')">Advertisement</span>
                      </th>
                      <th v-on:click="changeOrderCollab('advertisement.subsubcategory.name')">
                        <span v-text="$t('riportalApp.advertisement.categorization')">Advertisement</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'advertisement.subsubcategory.name'"
                        ></jhi-sort-indicator>
                      </th>
                      <!-- <th v-on:click="changeOrderCollab('ratingOffer.id')"><span v-text="$t('riportalApp.collaboration.ratingOffer')">Rating Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingOffer.id'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('ratingRequest.id')"><span v-text="$t('riportalApp.collaboration.ratingRequest')">Rating Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingRequest.id'"></jhi-sort-indicator></th> -->
                      <th v-on:click="changeOrderCollab('status.id')">
                        <span v-text="'Status'">Status</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.id'"></jhi-sort-indicator>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="collaboration in collaborations" :key="collaboration.id">
                      <td>
                        <router-link :to="{ name: 'CollaborationView', params: { collaborationId: collaboration.id } }">{{
                          collaboration.id
                        }}</router-link>
                      </td>
                      <td>{{ collaboration.datetime ? $d(Date.parse(collaboration.datetime), 'short') : '' }}</td>
                      <!-- <td>{{collaboration.commentOffer}}</td>
				                    <td>{{collaboration.commentRequest}}</td> -->
                      <td>
                        <div v-if="collaboration.companyOffer">
                          <router-link :to="{ name: 'CompanyView', params: { companyId: collaboration.companyOffer.id } }">{{
                            collaboration.companyOffer.name
                          }}</router-link>
                        </div>
                      </td>
                      <td>
                        <div v-if="collaboration.companyRequest">
                          <router-link :to="{ name: 'CompanyView', params: { companyId: collaboration.companyRequest.id } }">{{
                            collaboration.companyRequest.name
                          }}</router-link>
                        </div>
                      </td>
                      <td>
                        <div v-if="collaboration.advertisement">
                          <router-link :to="{ name: 'AdvertisementView', params: { advertisementId: collaboration.advertisement.id } }">{{
                            collaboration.advertisement.title
                          }}</router-link>
                        </div>
                      </td>
                      <td>
                        <div v-if="collaboration.advertisement.type">
                          <span v-if="$store.getters.currentLanguage === 'sr'">{{ collaboration.advertisement.type.type }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'src'">{{ collaboration.advertisement.type.typeSrc }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'en'">{{ collaboration.advertisement.type.typeEn }}</span>
                        </div>
                      </td>
                      <td>
                        <div v-if="collaboration.advertisement.kinds">
                          {{ advertisementKindsString(collaboration.advertisement) }}
                        </div>
                      </td>
                      <td>
                        <div v-if="collaboration.advertisement.subsubcategory">
                          {{ advertisementCategorizationBranch(collaboration.advertisement) }}
                        </div>
                      </td>
                      <!-- <td>
				                        <div v-if="collaboration.ratingOffer">
				                            <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaboration.ratingOffer.id}}">{{collaboration.ratingOffer.number}}</router-link>
				                        </div>
				                    </td>
				                    <td>
				                        <div v-if="collaboration.ratingRequest">
				                            <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaboration.ratingRequest.id}}">{{collaboration.ratingRequest.number}}</router-link>
				                        </div>
				                    </td> -->
                      <td>
                        <div v-if="collaboration.status">
                          <span v-if="$store.getters.currentLanguage === 'sr'">{{ collaboration.status.status }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'src'">{{ collaboration.status.statusSrc }}</span>
                          <span v-else-if="$store.getters.currentLanguage === 'en'">{{ collaboration.status.statusEn }}</span>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </b-tab>
            <b-tab :title="$t('userManagement.home.title')">
              <div class="d-flex">
                <div class="form-group">
                  <input id="collabCountId" type="text" class="form-control" name="collabCountId" v-model="usersCount" />
                </div>
              </div>

              <div>&nbsp;</div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.creationCollabDatetimeFrom')" for="activationDatetimeFrom"
                  >Activation Datetime From</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeFrom"
                    v-model="activationDatetimeFrom"
                    type="datetime-local"
                    class="form-control"
                    name="activationDatetimeFrom"
                    @change="updateInstantFieldFromUsers($event)"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.creationCollabDatetimeТо')" for="activationDatetimeTo"
                  >Activation Datetime To</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeTo"
                    type="datetime-local"
                    v-model="activationDatetimeTo"
                    class="form-control"
                    name="activationDatetimeTo"
                    @change="updateInstantFieldToUsers($event)"
                  />
                </div>
              </div>

              <b-button v-text="$t('riportalApp.reports.exportcsv')" v-on:click="exportCSVFile('portalUsers', 'users')">Export</b-button>

              <div class="table-responsive" v-if="portalUsers && portalUsers.length > 0">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th v-on:click="changeOrderUsers('id')">
                        <span v-text="$t('global.field.id')">ID</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderUsers('userFirstName')">
                        <span v-text="$t('riportalApp.portalUser.firstName')">First Name</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderUsers('user.lastName')">
                        <span v-text="$t('riportalApp.portalUser.familyName')">Family Name</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'familyName'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderUsers('phone')">
                        <span v-text="$t('riportalApp.portalUser.phone')">Phone</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderUsers('position')">
                        <span v-text="$t('riportalApp.portalUser.position')">Position</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'position'"></jhi-sort-indicator>
                      </th>
                      <!--
					                    <th v-on:click="changeOrder('researcher.id')"><span v-text="$t('riportalApp.portalUser.researcher')">Researcher</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'researcher.id'"></jhi-sort-indicator></th>
					                    -->
                      <th v-on:click="changeOrderUsers('userOrganization.legalNameSr')">
                        <span v-text="$t('riportalApp.portalUser.userOrganization')">User Organization</span>
                        <jhi-sort-indicator
                          :current-order="propOrder"
                          :reverse="reverse"
                          :field-name="'userOrganization.id'"
                        ></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderUsers('createdAt')">
                        <span v-text="$t('riportalApp.portalUser.createdat')">Position</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
                      </th>
                      <th v-on:click="changeOrderUsers('company.id')">
                        <span v-text="$t('riportalApp.advertisement.company')">Company</span>
                        <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.id'"></jhi-sort-indicator>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="portalUser in portalUsers" :key="portalUser.id">
                      <td>
                        <router-link class="text-body" :to="{ name: 'PortalUserView', params: { portalUserId: portalUser.id } }">{{
                          portalUser.id
                        }}</router-link>
                      </td>
                      <td>{{ portalUser.user.firstName }}</td>
                      <td>{{ portalUser.user.lastName }}</td>
                      <td>{{ portalUser.phone }}</td>
                      <td>{{ portalUser.position }}</td>
                      <!--
					                    <td>
					                        <div v-if="portalUser.researcher">
					                            <router-link :to="{name: 'ResearcherView', params: {researcherId: portalUser.researcher.id}}">{{portalUser.researcher.id}}</router-link>
					                        </div>
					                    </td>
					                    -->
                      <td>
                        <div v-if="portalUser.userOrganization">
                          <router-link
                            class="text-body"
                            :to="{
                              name: 'PortalUserOrganizationView',
                              params: { portalUserOrganizationId: portalUser.userOrganization.id },
                            }"
                          >
                            <span v-if="currentLanguage == 'sr'">{{ portalUser.userOrganization.legalNameSr }}</span>
                            <span v-if="currentLanguage == 'en'">{{ portalUser.userOrganization.legalNameEn }}</span>
                            <span v-if="currentLanguage == 'src'">{{ portalUser.userOrganization.legalNameSrCyr }}</span>
                          </router-link>
                        </div>
                      </td>
                      <td>{{ portalUser.createdAt }}</td>
                      <td>
                        <div v-if="portalUser.company">
                          <router-link class="text-body" :to="{ name: 'CompanyView', params: { companyId: portalUser.company.id } }">{{
                            portalUser.company.name
                          }}</router-link>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </b-tab>

            <b-tab :title="$t('riportalApp.collaborationRating.home.reportsTitle')" @click="retrieveFilteredRatings()">
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.creationCollabDatetimeFrom')" for="activationDatetimeFrom"
                  >Activation Datetime From</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeFrom"
                    v-model="activationDatetimeFrom"
                    type="datetime-local"
                    class="form-control"
                    name="activationDatetimeFrom"
                    @change="updateInstantFieldFromRatings($event)"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.reports.creationCollabDatetimeТо')" for="activationDatetimeTo"
                  >Activation Datetime To</label
                >
                <div class="d-flex">
                  <input
                    id="activationDatetimeTo"
                    type="datetime-local"
                    v-model="activationDatetimeTo"
                    class="form-control"
                    name="activationDatetimeTo"
                    @change="updateInstantFieldToRatings($event)"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" for="advertisement-type">
                  <span v-text="$t('riportalApp.advertisement.type')">Type</span>
                </label>
                <multiselect
                  v-if="advTypeList"
                  v-model="ratingFilters.types"
                  :options="advTypeList"
                  :multiple="true"
                  :close-on-select="false"
                  :clear-on-select="false"
                  :custom-label="advertisementTypeTranslation"
                  placeholder=""
                  :selectLabel="$t('multiselect.selectLabel')"
                  :selectedLabel="$t('multiselect.selectedLabel')"
                  :deselectLabel="$t('multiselect.deselectLabel')"
                  label="type"
                  track-by="type"
                  @input="retrieveFilteredRatings()"
                >
                </multiselect>
              </div>

              <div class="form-group">
                <label class="form-control-label" for="advertisement-kind">
                  <span v-text="$t('riportalApp.advertisement.kind')">Kind</span>
                </label>
                <multiselect
                  v-if="advKindList"
                  v-model="ratingFilters.kinds"
                  :options="advKindList"
                  :multiple="true"
                  :close-on-select="false"
                  :clear-on-select="false"
                  :custom-label="advertisementKindTranslation"
                  placeholder=""
                  :selectLabel="$t('multiselect.selectLabel')"
                  :selectedLabel="$t('multiselect.selectedLabel')"
                  :deselectLabel="$t('multiselect.deselectLabel')"
                  label="kind"
                  track-by="kind"
                  @input="retrieveFilteredRatings()"
                >
                </multiselect>
              </div>

              <div class="form-group">
                <label class="form-control-label" for="advertisement-subsubcategory">
                  <span v-text="$t('riportalApp.advertisement.categorization')">Subsubcategory</span>
                </label>
                <multiselect
                  v-if="advSubsubcategoryList"
                  v-model="ratingFilters.subsubcategories"
                  :options="advSubsubcategoryList"
                  :multiple="true"
                  :close-on-select="false"
                  :clear-on-select="false"
                  :custom-label="advertisementCategorizationBranch"
                  placeholder=""
                  :selectLabel="$t('multiselect.selectLabel')"
                  :selectedLabel="$t('multiselect.selectedLabel')"
                  :deselectLabel="$t('multiselect.deselectLabel')"
                  label="name"
                  track-by="name"
                  @input="retrieveFilteredRatings()"
                >
                </multiselect>
              </div>

              <div>&nbsp;</div>
              <b-button v-text="$t('riportalApp.reports.exportcsv')" v-on:click="exportCSVFile('companyRatings', 'ratings')"
                >Export
              </b-button>

              <div class="table-responsive" v-if="companyRatings && companyRatings.length > 0">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.company')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.totalRatings')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.averageRating')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.totalRatings1')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.percentageRating1')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.totalRatings2')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.percentageRating2')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.totalRatings3')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.percentageRating3')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.totalRatings4')"></th>
                      <th v-text="$t('riportalApp.collaborationRating.ratings.percentageRating4')"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="rating in companyRatings" :key="rating.companyId">
                      <td>
                        <div v-if="rating.companyName">
                          <router-link
                            v-if="rating.companyId"
                            :to="{ name: 'CompanyView', params: { companyId: rating.companyId } }"
                            class="text-body"
                            >{{ rating.companyName }}</router-link
                          >
                          <span v-else class="text-body">{{ rating.companyName }}</span>
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.totalRatings !== null">
                          {{ rating.totalRatings }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.averageRating !== null">
                          {{ rating.averageRating }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.totalRatings1 !== null">
                          {{ rating.totalRatings1 }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.percentageRating1 !== null">
                          {{ rating.percentageRating1 }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.totalRatings2 !== null">
                          {{ rating.totalRatings2 }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.percentageRating2 !== null">
                          {{ rating.percentageRating2 }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.totalRatings3 !== null">
                          {{ rating.totalRatings3 }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.percentageRating3 !== null">
                          {{ rating.percentageRating3 }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.totalRatings4 !== null">
                          {{ rating.totalRatings4 }}
                        </div>
                      </td>
                      <td>
                        <div v-if="rating.percentageRating4 !== null">
                          {{ rating.percentageRating4 }}
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </b-tab>
          </b-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cms-b2b-reports.component.ts"></script>

<style scoped>
::v-deep .nav-item .nav-link {
  color: #2c3e50;
}

@media screen and (max-width: 560px) {
  ::v-deep .nav-item .nav-link {
    padding: 8px 16px;
  }
}
</style>
