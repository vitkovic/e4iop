<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="advertisement">
        <b-breadcrumb v-if="advertisement.subsubcategory">
          <b-breadcrumb-item href="/"> <font-awesome-icon icon="home" /> почетна </b-breadcrumb-item>
          <b-breadcrumb-item href="#">{{
            advertisement.subsubcategory.advertisementSubcategory.advertisementCategory.name
          }}</b-breadcrumb-item>
          <b-breadcrumb-item href="#">{{ advertisement.subsubcategory.advertisementSubcategory.name }}</b-breadcrumb-item>
          <b-breadcrumb-item href="#">{{ advertisement.subsubcategory.name }}</b-breadcrumb-item>
          <b-breadcrumb-item active>{{ advertisement.title }}</b-breadcrumb-item>
        </b-breadcrumb>
        <p></p>
        <br />
        <h2 class="jh-entity-heading"><span v-text="''">Advertisement</span> {{ advertisement.title }}</h2>
        <br />

        <dl class="row jh-entity-details" style="justify-items: left; margin: 0;">
          <dt>
            <span v-text="$t('riportalApp.advertisement.type')">Type</span>
          </dt>
          <dd>
            <div v-if="advertisement.type">
              {{ advertisement.type.type }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.kind')">Kind</span>
          </dt>
          <dd>
            <div v-if="advertisement.kind">
              {{ advertisement.kind.kind }}
            </div>
          </dd>
        </dl>
        <br />

        <section>
          <!-- <h4 v-text="'Galerija'">Galerija</h4> -->
          <br />
          <div class="row images-container mt-2">
            <div
              class="col-xs-12 col-sm-6 col-md-4 col-lg-2 mb-4"
              v-for="document in advertisement.documents"
              v-if="document.type.type == 'image'"
            >
              <img class="img-thumbnail img-fluid" :src="advertisementService().retrieveImage(document.filename)" />
            </div>
          </div>
        </section>
        <br />

        <section>
          <!-- <h4 v-text="'Opis'">Opis</h4> -->
          {{ advertisement.description }}
        </section>
        <hr />
        <br />
        <dl class="row jh-entity-details" style="justify-items: left; margin: 0;">
          <dt>
            <span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span>
          </dt>
          <dd>
            <span v-if="advertisement.activationDatetime">{{ $d(Date.parse(advertisement.activationDatetime), 'short') }}</span>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.expirationDatetime')">Expiration Datetime</span>
          </dt>
          <dd>
            <div v-if="advertisement.duration && advertisement.activationDatetime">
              <!-- {{advertisement.duration.duration}} -->
              {{ $d(Date.parse(getExpirationDate(advertisement)), 'short') }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.budget')">Budget</span>
          </dt>
          <dd>
            <span>{{ advertisement.budget }}</span>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.conditions')">Conditions</span>
          </dt>
          <dd>
            <span>{{ advertisement.conditions }}</span>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.status')">Status</span>
          </dt>
          <dd>
            <div v-if="advertisement.status">
              {{ advertisement.status.status }}
            </div>
          </dd>
        </dl>
        <br />

        <section>
          <h4 v-text="$t('riportalApp.advertisement.documents')">Dokumenti</h4>
          <div v-for="document in advertisement.documents">
            <li v-if="document.type.type == 'document'" style="list-style-type: none;">
              <a
                class="text-info"
                :href="advertisementService().retrieveDocument(document.filename)"
                target="_blank"
                title="Preuzmite dokument"
                >{{ document.filename }}
              </a>
            </li>
          </div>
        </section>
        <br />

        <dl class="row jh-entity-details" style="justify-items: left; margin: 0;">
          <h4 v-text="$t('riportalApp.advertisement.advertiser')">Oglašivač</h4>
          <br />
          <div class="d-flex" v-if="advertisement.company">
            <img
              v-if="advertisement.company.logo"
              :src="advertisementService().retrieveImage(advertisement.company.logo.filename)"
              alt="company logo"
              style="width: 70px; height: 70x; margin-right: 1rem;"
            />
            <router-link :to="{ name: 'CompanyView', params: { companyId: advertisement.company.id } }" class="d-flex">
              <h2 class="jh-entity-heading" style="align-self: center;">{{ advertisement.company.name }}</h2>
            </router-link>
          </div>
        </dl>
        <br />

        <!-- <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button> -->
        <b-button
          v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner())"
          v-on:click="prepareAdInquiry(advertisement)"
          variant="info"
          class="btn btn-primary"
          v-b-modal.adInquiry
        >
          <span class="d-none d-md-inline" v-text="$t('riportalApp.advertisement.interactionButtons.question')">Pošalji upit</span>
        </b-button>
        <b-button
          v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner())"
          v-on:click="prepareCreateMeetingModal(advertisement)"
          class="btn btn-primary"
        >
          <span v-text="$t('riportalApp.advertisement.interactionButtons.appointment')"> Zakaži sastanak</span>
        </b-button>
        <b-button
          v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner())"
          v-on:click="prepareAdCollaboration(advertisement)"
          class="btn btn-primary"
          v-b-modal.adCollaboration
        >
          <span v-text="$t('riportalApp.advertisement.interactionButtons.collaboration')"> Pokreni saradnju</span>
        </b-button>

        <router-link
          v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || isCompanyOwner())"
          :to="{ name: 'AdvertisementEdit', params: { advertisementId: advertisement.id } }"
          tag="button"
          class="btn btn-primary"
        >
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
        </router-link>
        <br />
        <br />
        <b-modal v-if="inquiryDTO" ref="adInquiry" id="adInquiry">
          <span slot="modal-title"
            ><span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.advertisement.modal.advertisementModalTitle')"
              >Upit za oglas</span
            ></span
          >
          <div class="modal-body">
            <p id="jhi-delete-advertisement-heading">
              <b>{{ $t('riportalApp.advertisement.modal.advertisementTitle') }}</b
              >{{ advertisement.title }}
            </p>
            <p v-if="advertisement.company" id="jhi-delete-advertisement-heading">
              <b>{{ $t('riportalApp.advertisement.modal.companyTitle') }}</b
              >{{ advertisement.company.name }}
            </p>
            <br />
            <label name="inquiry-subject" v-text="$t('entity.form.messageTitle')">Naslov poruke:</label>
            <b-input v-model.trim="inputSubject.value" @blur="clearValidity('inputSubject')"></b-input>
            <p v-if="!inputSubject.isValid" class="text-danger small" v-text="$t('entity.form.validation.title')">
              Naziv poruke ne može biti prazan.
            </p>
            <br />
            <label name="inquiry-content" v-text="$t('entity.form.messageContent')">Sadržaj poruke:</label>
            <b-textarea v-model.trim="textareaContent.value" @blur="clearValidity('textareaContent')"></b-textarea>
            <p v-if="!textareaContent.isValid" class="text-danger small" v-text="$t('entity.form.validation.content')">
              Sadržaj poruke ne može biti prazan.
            </p>
          </div>
          <div slot="modal-footer">
            <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeAdInquiry()">
              Otkaži
            </button>
            <button
              type="button"
              class="btn btn-primary"
              id="jhi-confirm-delete-advertisement"
              v-text="$t('entity.action.save')"
              v-on:click="sendInquiry()"
            >
              Pošalji
            </button>
          </div>
        </b-modal>

        <b-modal ref="adCollaboration" id="adCollaboration">
          <span slot="modal-title"
            ><span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.advertisement.modal.collaborationModalTitle')"
              >Potvrdite pokretanje saradnje</span
            ></span
          >
          <div class="modal-body">
            <p id="jhi-delete-advertisement-heading">
              <b>{{ $t('riportalApp.advertisement.modal.advertisementTitle') }}</b
              >{{ advertisement.title }}
            </p>
            <p v-if="advertisement.company" id="jhi-delete-advertisement-heading">
              <b>{{ $t('riportalApp.advertisement.modal.companyTitle') }}</b
              >{{ advertisement.company.name }}
            </p>
            <br />
          </div>
          <div slot="modal-footer">
            <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeAdCollaboration()">
              Otkaži
            </button>
            <button
              type="button"
              class="btn btn-success"
              id="jhi-confirm-delete-advertisement"
              v-text="$t('entity.action.send')"
              v-on:click="startAdCollaboration()"
            >
              Pokreni
            </button>
          </div>
        </b-modal>

        <b-modal ref="createMeetingModal" id="createMeetingModal" size="lg">
          <span slot="modal-title"
            ><span id="riportalApp.researchInfrastructure.calendar" v-text="'Novi sastanak za oglas - '"></span
            ><span>{{ advertisement.title }}</span></span
          >
          <div class="d-flex">
            <div class="modal-body" style="border-right: 1px solid #ccc; width: 45%; padding-right: 5%;">
              <div class="mb-3">
                <b-input v-model="meetingEvent.title" placeholder="Unesite naslov..."></b-input>
              </div>

              <div class="d-flex">
                <b-form-datepicker
                  style="width: 70%;"
                  v-model="meetingEvent.startDate"
                  :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                  placeholder="Izaberite datum početka..."
                  @input="updateMeetingEndDate()"
                ></b-form-datepicker>
                <b-form-timepicker style="width: 30%;" minutes-step="15" v-model="meetingEvent.startTime"></b-form-timepicker>
              </div>

              <div class="d-flex mb-3">
                <b-form-datepicker
                  style="width: 70%;"
                  v-model="meetingEvent.endDate"
                  :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                  :min="meetingEvent.startDate"
                ></b-form-datepicker>
                <b-form-timepicker style="width: 30%;" minutes-step="15" v-model="meetingEvent.endTime"></b-form-timepicker>
              </div>

              <b-form-textarea v-model="meetingEvent.description" class="mb-3" placeholder="Unesite opis..."></b-form-textarea>
            </div>

            <div class="modal-body" style="width: 45%; padding-left: 5%;">
              <label for="" v-text="'Organizator'">Organizator</label>
              <div v-if="portalUser" class="d-flex align-items-center">
                <div v-if="portalUser.company.logo" class="company-logo-container position-relative">
                  <img
                    :src="companyService().retrieveImage(portalUser.company.logo.filename)"
                    alt="company logo"
                    style="width: 100%; max-height: 100%;"
                    class="company-logo"
                  />
                </div>
                <div v-else class="placeholder-logo">{{ getCompanyInitials(portalUser.company) }}</div>
                <span>{{ portalUser.company.name }}</span>
              </div>
              <hr />

              <label for="" v-text="'Oglašivač'">Oglašivač</label>
              <div v-if="advertisement && advertisement.company" class="d-flex align-items-center">
                <div v-if="advertisement.company.logo" class="company-logo-container position-relative">
                  <img
                    :src="companyService().retrieveImage(advertisement.company.logo.filename)"
                    alt="company logo"
                    style="width: 100%; max-height: 100%;"
                    class="company-logo"
                  />
                </div>
                <div v-else class="placeholder-logo">{{ getCompanyInitials(advertisement.company) }}</div>
                <span>{{ advertisement.company.name }}</span>
              </div>
              <hr />

              <label for="" v-text="'Dodajte druge učesnike'">Dodajte druge učesnike</label>
              <input
                type="text"
                ref="company-name"
                class="form-control mb-3"
                name="company-name"
                id="company-name"
                placeholder="Potražite kompaniju..."
                @keyup="getCompaniesBySearchText()"
                @click="toggleSearchList($event, 'showCompaniesSearch')"
                @focusout="toggleSearchList($event, 'showCompaniesSearch')"
                @focusin="getCompaniesBySearchText()"
                v-model="companySearchText"
                autocomplete="off"
              />
              <div
                class="form-control"
                style="text-align: left; height: 150px; overflow-y: scroll; position: absolute; z-index: 9999;"
                v-if="companiesSearch.length && showCompaniesSearch"
              >
                <ul class="list-group" @click.stop>
                  <div v-for="company in companiesSearch" :key="company.id">
                    <a class="list-group-item" @mousedown="addToCompaniesMeetingParticipants(company)">
                      <span>{{ company.name }}</span>
                    </a>
                  </div>
                </ul>
              </div>

              <div
                v-for="company in companiesMeetingParticipants"
                :key="company.id"
                class="d-flex align-items-center justify-content-between mb-3"
              >
                <div class="d-flex align-items-center">
                  <div v-if="company.logo" class="company-logo-container position-relative">
                    <img
                      :src="companyService().retrieveImage(company.logo.filename)"
                      alt="company logo"
                      style="width: 100%; max-height: 100%;"
                      class="company-logo"
                    />
                  </div>
                  <div v-else class="placeholder-logo">{{ getCompanyInitials(company) }}</div>
                  <span>{{ company.name }}</span>
                </div>
                <b-button @click="removeFromCompaniesMeetingParticipants(company)" variant="primary" class="close">x</b-button>
              </div>
            </div>
          </div>
          <div slot="modal-footer">
            <button type="button" class="btn btn-success" v-text="'Zakaži sastanak'" v-on:click="createMeeting()">Zakaži sastanak</button>
            <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeCreateMeetingModal()">
              Cancel
            </button>
          </div>
        </b-modal>

        <dl class="row jh-entity-details" v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')" style="justify-items: left; margin: 0;">
          <h4 v-text="$t('riportalApp.advertisement.dataChanges')">Podaci o izmenama</h4>
          <br />
          <dt>
            <span v-text="$t('riportalApp.advertisement.createdAt')">Created At</span>
          </dt>
          <dd>
            <span v-if="advertisement.createdAt">{{ $d(Date.parse(advertisement.createdAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.createdBy')">Created By</span>
          </dt>
          <dd>
            <div v-if="advertisement.createdBy">
              <router-link :to="{ name: 'PortalUserView', params: { portalUserId: advertisement.createdBy.id } }">{{
                advertisement.createdBy.firstname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span v-if="advertisement.modifiedAt">{{ $d(Date.parse(advertisement.modifiedAt), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('riportalApp.advertisement.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <div v-if="advertisement.modifiedBy">
              <router-link :to="{ name: 'PortalUserView', params: { portalUserId: advertisement.modifiedBy.id } }">{{
                advertisement.modifiedBy.firstname
              }}</router-link>
            </div>
          </dd>
        </dl>
      </div>
    </div>
  </div>
</template>

<style>
.jh-entity-details > dd {
  justify-self: left;
  margin-left: 5rem !important;
}

.startech-link,
.startech-link:link,
.startech-link:visited,
.startech-link:hover,
.startech-link:active {
  color: blue;
  text-decoration: none;
}

.img-thumbnail {
  /* width: 100%; Ensure images fill their container */
  height: 100px; /* Maintain aspect ratio */
  cursor: pointer; /* Change cursor to pointer on hover */
  padding: 0;
  transition: all 0.4s;
  box-shadow: 0 0.6rem 1.2rem rgba(0, 0, 0, 0.075);
  /* object-fit: cover; Ensure images fill the entire card */
}

.img-thumbnail:hover {
  transform: translateY(-0.3rem);
  box-shadow: 0 0.8rem 1.6rem rgba(0, 0, 0, 0.06);
}

h1,
h2 {
  color: #004b90;
}

.company-logo-container {
  margin-right: 1rem;
  width: 40px;
  height: 40px;
  margin-right: 1rem; /* Space between the image and the name */
  border-radius: 50%; /* Make the image circular */
  object-fit: cover; /* Ensure the image covers the whole circle */
  border: 2px solid #ccc;
  align-content: center;
}

.company-logo {
  border-radius: 50%; /* Make the image circular */
}

.placeholder-logo {
  width: 40px;
  height: 40px;
  margin-right: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ccc;
  border-radius: 50%;
  color: black;
  text-align: center; /* Center text inside the div */
}
</style>

<script lang="ts" src="./advertisement-details.component.ts"></script>
