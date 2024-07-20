<template>
  <div>
    <h2 id="page-heading">
      <router-link
        :to="{ name: 'AdvertisementCreate' }"
        tag="button"
        id="jh-create-entity"
        class="btn btn-primary float-right jh-create-entity create-advertisement"
      >
        <font-awesome-icon icon="plus"></font-awesome-icon>
        <span v-text="$t('riportalApp.advertisement.home.createLabel')">
          Create a new Advertisement
        </span>
      </router-link>
    </h2>
    <b-alert
      :show="dismissCountDown"
      dismissible
      :variant="alertType"
      @dismissed="dismissCountDown = 0"
      @dismiss-count-down="countDownChanged"
    >
      {{ alertMessage }}
    </b-alert>
    <div v-if="company" class="d-flex mb-3 mt-5">
      <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)" alt="company logo" class="company-logo mr-3" />
      <h2 id="page-heading" class="jh-entity-heading company-title" style="align-self: center;">
        {{ company.name }}
      </h2>
    </div>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && advertisements && advertisements.length === 0">
      <span v-text="$t('riportalApp.advertisement.home.notFound')">No advertisements found</span>
    </div>
    <div class="ml-3 mb-3 d-flex flex-column flex-sm-row">
      <h3 v-text="$t('riportalApp.advertisement.home.myAdvertisements')" class="mr-3 mb-3 mb-sm-0">Oglasi</h3>
      <div>
        <b-button
          :variant="filterAllButtonVariant"
          v-text="$t('riportalApp.advertisement.filterButtons.all') + ` (${countAllAdvertisements})`"
          v-on:click="showAllAdvertisements()"
          >Cancel</b-button
        >
        <b-button
          :variant="filterActiveButtonVariant"
          v-text="$t('riportalApp.advertisement.filterButtons.active') + ` (${countActiveAdvertisements})`"
          v-on:click="showActiveAdvertisements()"
          >Cancel</b-button
        >
        <b-button
          :variant="filterInactiveButtonVariant"
          v-text="$t('riportalApp.advertisement.filterButtons.inactive') + ` (${countInactiveAdvertisements})`"
          v-on:click="showInactiveAdvertisements()"
          >Cancel</b-button
        >
        <b-button
          v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')"
          :variant="filterSoftDeleteButtonVariant"
          v-text="$t('riportalApp.advertisement.filterButtons.archived')"
          v-on:click="showSoftDeleteAdvertisements()"
          >Cancel</b-button
        >
      </div>
    </div>
    <div class="custom-table-responsive" v-if="advertisements && advertisements.length > 0">
      <table class="table table-striped">
        <thead>
          <tr>
            <!-- <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.advertisement.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th> -->
            <!-- <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.advertisement.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th> -->
            <th v-on:click="changeOrder('title')">
              <span v-text="$t('riportalApp.advertisement.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <!-- <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.advertisement.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th> -->
            <!-- <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.advertisement.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th> -->
            <th v-on:click="changeOrder('status.status')">
              <span v-text="$t('riportalApp.advertisement.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.id'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('type.type')">
              <span v-text="$t('riportalApp.advertisement.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('kind.kind')">
              <span v-text="$t('riportalApp.advertisement.kind')">Kind</span>
              <!-- <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.id'"></jhi-sort-indicator> -->
            </th>
            <th v-on:click="changeOrder('subsubcategory.name')">
              <span v-text="$t('riportalApp.advertisement.categorization')">Subsubcategory</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subsubcategory.id'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('budget')">
              <span v-text="$t('riportalApp.advertisement.budget')">Budget</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'budget'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('activationDatetime')">
              <span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationDatetime'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('expirationDatetime')">
              <span v-text="$t('riportalApp.advertisement.expirationDatetime')">Expiration Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expirationDatetime'"></jhi-sort-indicator>
            </th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="advertisement in advertisements" :key="advertisement.id">
            <!-- <td>{{advertisement.createdAt ? $d(Date.parse(advertisement.createdAt), 'short') : ''}}</td> -->
            <!-- <td>{{advertisement.modifiedAt ? $d(Date.parse(advertisement.modifiedAt), 'short') : ''}}</td> -->
            <td class="title-column">
              <router-link :to="{ name: 'AdvertisementView', params: { advertisementId: advertisement.id } }" class="text-body">
                {{ advertisement.title }}      
              </router-link>
            </td>
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
            <td>{{ advertisement.budget.toLocaleString('sr-SR', { style: 'currency', currency: 'RSD' }) }}</td>
            <td>
              {{
                advertisement.activationDatetime ? $d(Date.parse(advertisement.activationDatetime.toString()), { dateStyle: 'short' }) : ''
              }}
            </td>
            <td>
              {{
                advertisement.expirationDatetime ? $d(Date.parse(advertisement.expirationDatetime.toString()), { dateStyle: 'short' }) : ''
              }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  v-if="advertisement.status.statusEn === advertisementStatusOptions.ACTIVE"
                  :to="{ name: 'AdvertisementEdit', params: { advertisementId: advertisement.id } }"
                  tag="button"
                  class="btn btn-primary btn-sm edit"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span v-text="$t('entity.action.edit')">Edit</span>
                </router-link>
                <b-button
                  v-if="advertisement.status.statusEn === advertisementStatusOptions.ACTIVE"
                  v-on:click="prepareDeactivate(advertisement)"
                  variant="dark"
                  class="btn btn-sm"
                  v-b-modal.deactivateEntity
                >
                  <span v-text="$t('entity.action.deactivate')">Deaktiviraj</span>
                </b-button>
                <b-button
                  v-if="advertisement.status.statusEn === advertisementStatusOptions.INACTIVE || advertisement.status.statusEn === advertisementStatusOptions.ARCHIVED"
                  v-on:click="prepareActivate(advertisement)"
                  variant="success"
                  class="btn btn-sm"
                  v-b-modal.activateEntity
                >
                  <span v-text="$t('entity.action.activate')">Aktiviraj</span>
                </b-button>
                <b-button
                  v-if="advertisement.status.statusEn === advertisementStatusOptions.INACTIVE"
                  v-on:click="prepareSoftDelete(advertisement)"
                  variant="danger"
                  class="btn btn-sm"
                  v-b-modal.softDeleteEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
                <b-button
                  v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')"
                  v-on:click="prepareRemove(advertisement)"
                  variant="outline-danger"
                  class="btn btn-sm"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span v-text="$t('entity.action.deleteDatabase')">Obriši iz baze</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="deactivateEntity" id="deactivateEntity">
      <span slot="modal-title"
        ><span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.advertisement.modal.deactivateModal.title')"
          >Potvrdite deaktiviranje oglasa</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisement-heading" v-text="$t('riportalApp.advertisement.modal.deactivateModal.question')">
          Da li ste sigurni da zelite da deaktivirate oglas?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDeactivateDialog()">
          Cancel
        </button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-advertisement"
          v-on:click="deactivateAdvertisement()"
          v-text="$t('entity.action.deactivate')"
        >
          Deaktiviraj
        </button>
      </div>
    </b-modal>
    <b-modal ref="activateEntity" id="activateEntity">
      <span slot="modal-title"
        ><span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.advertisement.modal.activateModal.title')"
          >Potvrdite aktiviranje oglasa</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisement-heading" v-text="$t('riportalApp.advertisement.modal.activateModal.question')">
          Da li ste sigurni da zelite da aktivirate oglas?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeActivateDialog()">
          Cancel
        </button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-advertisement"
          v-on:click="activateAdvertisement()"
          v-text="$t('entity.action.activate')"
        >
          Aktiviraj
        </button>
      </div>
    </b-modal>
    <b-modal ref="softDeleteEntity" id="softDeleteEntity">
      <span slot="modal-title"
        ><span id="riportalApp.advertisement.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisement-heading" v-text="$t('riportalApp.advertisement.delete.question', { id: removeId })">
          Are you sure you want to delete this Advertisement?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeSoftDeleteDialog()">
          Cancel
        </button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-advertisement"
          v-text="$t('entity.action.delete')"
          v-on:click="softDeleteAdvertisement()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="riportalApp.advertisement.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisement-heading" v-text="$t('riportalApp.advertisement.modal.deleteDatabaseModal.question')">
          Are you sure you want to delete this Advertisement from Database?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-advertisement"
          v-text="$t('entity.action.deleteDatabase')"
          v-on:click="removeAdvertisement()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="advertisements && advertisements.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./company-advertisements-manage.component.ts"></script>

<style scoped>
.company-logo {
  height: 75px;
}

.custom-table-responsive {
  overflow-x: auto;
}

@media (max-width: 1280px) {
  .custom-table-responsive table {
    min-width: 1280px;
  }
}

@media screen and (max-width: 578px) {
  .company-title {
    font-size: 2rem;
  }
}
</style>
