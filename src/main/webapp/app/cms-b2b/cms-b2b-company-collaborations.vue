<template>
  <div>
    <b-alert
      :show="dismissCountDown"
      dismissible
      :variant="alertType"
      @dismissed="dismissCountDown = 0"
      @dismiss-count-down="countDownChanged"
    >
      {{ alertMessage }}
    </b-alert>
    <div v-if="company" class="d-flex mb-3">
      <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)" alt="company logo" class="company-logo mr-3" />
      <h2 id="page-heading" class="jh-entity-heading company-title cms-title" style="align-self: center;">
        {{ company.name }}
      </h2>
    </div>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && collaborations && collaborations.length === 0">
      <span v-text="$t('riportalApp.collaboration.home.notFound')">No collaborations found</span>
    </div>

    <!-- <div class="ml-3 mb-3" style="display: flex;"> -->
    <b-row>
      <div class="col-xs-12 col-lg-2 p-0 ml-4">
        <h3 v-text="$t('riportalApp.collaboration.home.title')" class="mb-4">Saradnje</h3>
      </div>

      <div class="company-filter col-lg-6 ml-4">
        <b-row>
          <b-form-group
            class="pr-2 border-left pl-2 col-6 col-sm-3"
            label-size="lg"
            :label="$t('riportalApp.collaboration.filters.role')"
            v-slot="{ ariaDescribedby }"
          >
            <b-form-checkbox v-model="companyOfferFlag">
              <span v-text="$t('riportalApp.collaboration.filters.companyOffer')">Oglašivač</span>
            </b-form-checkbox>
            <b-form-checkbox v-model="companyRequestFlag">
              <span v-text="$t('riportalApp.collaboration.filters.companyRequest')">Tražilac</span>
            </b-form-checkbox>
          </b-form-group>

          <b-form-group
            class="border-left pl-2 col-6 col-sm-4"
            label-size="lg"
            :label="$t('riportalApp.collaboration.filters.status')"
            v-slot="{ ariaDescribedby }"
          >
            <b-form-checkbox-group
              id="checkbox-group-2"
              v-model="selectedStatusIds"
              :aria-describedby="ariaDescribedby"
              name="flavour-2"
              stacked
            >
              <b-form-checkbox v-for="option in collaborationsStatusChoices" :key="option.id" :value="option.id">
                <span v-if="$store.getters.currentLanguage === 'sr'">{{ option.status }}</span>
                <span v-else-if="$store.getters.currentLanguage === 'src'">{{ option.statusSrc }}</span>
                <span v-else-if="$store.getters.currentLanguage === 'en'">{{ option.statusEn }}</span>
              </b-form-checkbox>
            </b-form-checkbox-group>
          </b-form-group>

          <b-form-group
            class="pr-2 border-left pl-2 col-6 col-sm-3"
            label-size="lg"
            :label="$t('riportalApp.collaboration.filters.rating')"
            v-slot="{ ariaDescribedby }"
          >
            <b-form-checkbox v-model="rartingGivenFlag">
              <span v-text="$t('riportalApp.collaboration.filters.ratingGiven')">Data</span>
            </b-form-checkbox>
            <b-form-checkbox v-model="ratingReceviedFlag">
              <span v-text="$t('riportalApp.collaboration.filters.ratingReceived')">Dobijena</span>
            </b-form-checkbox>
          </b-form-group>

        </b-row>
      </div>
    </b-row>
    <!-- </div> -->

    <div class="custom-table-responsive" v-if="collaborations && collaborations.length > 0">
      <table class="table table-striped">
        <thead>
          <tr>
            <th v-on:click="changeOrder('datetime')">
              <span v-text="$t('riportalApp.collaboration.datetime')">Datetime</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('status.id')">
              <span v-text="$t('riportalApp.advertisement.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.status'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('companyOffer.id')">
              <span v-text="$t('riportalApp.collaboration.companyOffer')">Company Offer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyOffer.id'"></jhi-sort-indicator>
            </th>
            <th class="advertisement-column" v-on:click="changeOrder('companyRequest.id')">
              <span v-text="$t('riportalApp.collaboration.companyRequest')">Company Request</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyRequest.id'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('advertisement.title')">
              <span v-text="$t('riportalApp.collaboration.advertisement')">Advertisement</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'advertisement.title'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('ratingOffer.id')">
              <span v-text="$t('riportalApp.collaboration.ratingOffer')">Rating Offer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingOffer.number'"></jhi-sort-indicator>
            </th>
            <th v-on:click="changeOrder('ratingRequest.id')">
              <span v-text="$t('riportalApp.collaboration.ratingRequest')">Rating Request</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingRequest.number'"></jhi-sort-indicator>
            </th>
            <!-- <th><span v-text="$t('riportalApp.collaboration.commentOffer')">Comment Offer</span></th> -->
            <!-- <th><span v-text="$t('riportalApp.collaboration.commentRequest')">Comment Request</span></th> -->
          </tr>
        </thead>
        <tbody>
          <tr v-for="collaboration in collaborations" :key="collaboration.id">
            <td>{{ collaboration.datetime ? $d(Date.parse(collaboration.datetime.toString()), { dateStyle: 'short' }) : '' }}</td>
            <td>
              <div v-if="collaboration.status">
                <span v-if="$store.getters.currentLanguage === 'sr'">{{ collaboration.status.status }}</span>
                <span v-else-if="$store.getters.currentLanguage === 'src'">{{ collaboration.status.statusSrc }}</span>
                <span v-else-if="$store.getters.currentLanguage === 'en'">{{ collaboration.status.statusEn }}</span>
              </div>
            </td>
            <td>
              <div v-if="collaboration.companyOffer">
                <router-link
                  :to="{ name: 'CompanyView', params: { companyId: collaboration.companyOffer.id } }"
                  class="text-body"
                  >{{ collaboration.companyOffer.name }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="collaboration.companyRequest">
                <router-link
                  :to="{ name: 'CompanyView', params: { companyId: collaboration.companyRequest.id } }"
                  class="text-body"
                  >{{ collaboration.companyRequest.name }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="collaboration.advertisement">
                <router-link
                  :to="{ name: 'AdvertisementView', params: { advertisementId: collaboration.advertisement.id } }"
                  class="text-body"
                  >{{ collaboration.advertisement.title }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="collaboration.ratingOffer">
                <div class="d-flex mb-2">
                  <b-form-rating disabled :value="collaboration.ratingOffer.number" stars="4" inline size="sm"> </b-form-rating>
                  <div class="ml-2">
                    <span v-if="collaboration.commentOffer.length > 30"> {{ collaboration.commentOffer.slice(0, 30) + '...' }}</span>
                    <span v-else>{{ collaboration.commentOffer }}</span>
                  </div>
                </div>
                <div class="btn-group">
                  <b-button
                      v-on:click="showCommentModal(collaboration, ratingSideOptions.OFFER)"
                      variant="info"
                      class="btn btn-sm"
                      v-b-modal.testimonial
                    >
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span v-text="$t('entity.action.view')">Oceni</span>
                  </b-button>
                  <!-- <b-button
                      v-on:click="prepareEditRating(collaboration, ratingSideOptions.OFFER)"
                      variant="primary"
                      class="btn btn-sm"
                      v-b-modal.ratingEntity
                    >
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span v-text="$t('entity.action.edit')">Oceni</span>
                  </b-button> -->
                  <b-button
                      v-on:click="preareDeleteRatingModal(collaboration, ratingSideOptions.OFFER)"
                      variant="danger"
                      class="btn btn-sm"
                      v-b-modal.deleteRatingModal
                    >
                    <font-awesome-icon icon="times"></font-awesome-icon>
                    <span v-text="$t('entity.action.delete')">Oceni</span>
                  </b-button>
                </div>
              </div>
              <div v-else>
                <!-- <b-button
                    v-on:click="prepareRating(collaboration, ratingSideOptions.OFFER)"
                    variant="secondary"
                    class="btn btn-sm"
                    v-b-modal.ratingEntity
                  >
                    <font-awesome-icon icon="star"></font-awesome-icon>
                    <span v-text="$t('riportalApp.collaboration.actions.rate')">Oceni</span>
                  </b-button> -->
              </div>
            </td>
            <td>
              <div v-if="collaboration.ratingRequest">
                <div class="d-flex mb-2">
                  <b-form-rating disabled :value="collaboration.ratingRequest.number" stars="4" inline size="sm"> </b-form-rating>
                  <div class="ml-2">
                    <span v-if="collaboration.commentRequest.length > 30"> {{ collaboration.commentRequest.slice(0, 30) + '...' }}</span>
                    <span v-else>{{ collaboration.commentRequest }}</span>
                  </div>
                </div>
                <div class="btn-group">
                  <b-button
                      v-on:click="showCommentModal(collaboration, ratingSideOptions.REQUEST)"
                      variant="info"
                      class="btn btn-sm"
                      v-b-modal.testimonial
                    >
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span v-text="$t('entity.action.view')">Oceni</span>
                  </b-button>
                  <!-- <b-button
                      v-on:click="prepareEditRating(collaboration, ratingSideOptions.REQUEST)"
                      variant="primary"
                      class="btn btn-sm"
                      v-b-modal.ratingEntity
                    >
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span v-text="$t('entity.action.edit')">Oceni</span>
                  </b-button> -->
                  <b-button
                      v-on:click="preareDeleteRatingModal(collaboration, ratingSideOptions.REQUEST)"
                      variant="danger"
                      class="btn btn-sm"
                      v-b-modal.deleteRatingModal
                    >
                    <font-awesome-icon icon="times"></font-awesome-icon>
                    <span v-text="$t('entity.action.delete')">Oceni</span>
                  </b-button>
                </div>
              </div>
              <div v-else>
                <!-- <b-button
                    v-on:click="prepareRating(collaboration, ratingSideOptions.REQUEST)"
                    variant="secondary"
                    class="btn btn-sm"
                    v-b-modal.ratingEntity
                  >
                    <font-awesome-icon icon="star"></font-awesome-icon>
                    <span v-text="$t('riportalApp.collaboration.actions.rate')">Oceni</span>
                  </b-button> -->
              </div>
            </td>
            <!-- <td>{{collaboration.commentOffer}}</td> -->
            <!-- <td>{{collaboration.commentRequest}}</td> -->
          </tr>
        </tbody>
      </table>
    </div>

    <b-modal v-if="collaborationToRate" ref="ratingEntity" id="ratingEntity">
      <span slot="modal-title"><span v-text="$t('riportalApp.collaboration.modal.ratingEntityModal.title')">Ocenite saradnju</span></span>
      <div class="modal-body">
        <p>
          <b>{{ $t('riportalApp.collaboration.advertisement') }}: </b>{{ collaborationToRate.advertisement.title }}
        </p>
        <p v-if="ratingSide && ratingSide === ratingSideOptions.OFFER">
          <b>{{ $t('riportalApp.collaboration.companyOffer') }}: </b>{{ collaborationToRate.companyOffer.name }}
        </p>
        <p v-if="ratingSide && ratingSide === ratingSideOptions.REQUEST">
          <b>{{ $t('riportalApp.collaboration.companyRequest') }}: </b>{{ collaborationToRate.companyRequest.name }}
        </p>
        <div>
          <b-dropdown :text="$t('riportalApp.collaboration.modal.ratingEntityModal.ratingButton')" class="mb-3">
            <b-dropdown-item v-for="rating in collaborationRatings" :key="rating.id" @click="selectRating(rating)">
              <b-form-rating disabled :value="rating.number" stars="4" inline size="sm"> </b-form-rating>
              <span v-if="$store.getters.currentLanguage === 'sr'" class="ml-2">{{ rating.description }}</span>
              <span v-else-if="$store.getters.currentLanguage === 'src'" class="ml-2">{{ rating.descriptionSrc }}</span>
              <span v-else-if="$store.getters.currentLanguage === 'en'" class="ml-2">{{ rating.descriptionEn }}</span>
            </b-dropdown-item>
          </b-dropdown>
          <div v-if="selectedRating" class="mb-3">
            <b-form-rating disabled :value="selectedRating.number" stars="4" inline size="sm"> </b-form-rating>
            <span class="ml-2">{{ selectedRating.description }}</span>
          </div>
          <div v-else class="mb-3">
            <span style="color: red;" v-text="$t('riportalApp.collaboration.modal.ratingEntityModal.ratingValidation')"
              >Ocena nije izabrana</span
            >
          </div>
        </div>
        <b-form-textarea
          id="textarea"
          v-model="ratingComment"
          :placeholder="$t('riportalApp.collaboration.modal.ratingEntityModal.placeholderComment')"
        >
        </b-form-textarea>
      </div>
      <div slot="modal-footer">
        <button
          type="button"
          class="btn btn-success"
          v-text="$t('riportalApp.collaboration.actions.rate')"
          v-on:click="rateCollaboration()"
        >
          Oceni
        </button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-collaboration"
          v-text="$t('entity.action.cancel')"
          v-on:click="closeRatingDialog()"
        >
          Otkaži
        </button>
      </div>
    </b-modal>

    <b-modal ref="deleteRatingModal" id="deleteRatingModal">
      <span slot="modal-title"
        ><span id="riportalApp.advertisement.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisement-heading" v-text="$t('riportalApp.collaboration.modal.deleteRatingModal.question')">
          Are you sure you want to delete this Advertisement from Database?
        </p>
      </div>
      <div slot="modal-footer">
        <button
          type="button"
          class="btn btn-success"
          id="jhi-confirm-delete-advertisement"
          v-text="$t('entity.action.confirm')"
          v-on:click="deleteRating()"
        >
          Delete
        </button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeDeleteRatingModal()">Cancel</button>
      </div>
    </b-modal>

    <b-modal id="testimonial" ok-only :ok-title="$t('entity.action.close')" @hide="resetModalData" ref="testimonial">
        <div class="modal-body">
          <div class="d-flex flex-column mb-2">
            <b class="p-0 mb-1">Ocena:</b>
            <b-form-rating
              id="rating-inline"
              inline
              :value="modalRating"
              variant="primary"
              size="sm"
              stars="4"
              disabled
              style="width: fit-content;"
            ></b-form-rating>
          </div>
          <div>
            <b class="p-0 mb-1">Komentar:</b>
            <p>{{ modalComment }}</p>
          </div>
        </div>
      </b-modal>

    <div v-show="collaborations && collaborations.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cms-b2b-company-collaborations.component.ts"></script>

<style scoped>
.b-rating.disabled {
  color: rgb(40, 40, 56);
}

.cms-title{
  color: #004b90;
}

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
