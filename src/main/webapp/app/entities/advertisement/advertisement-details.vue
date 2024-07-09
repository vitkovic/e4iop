<template>
  <div class="row justify-content-center container-box">
    <div class="col-xs-12 col-sm-10 col-md-10 col-lg-8">
      <div v-if="advertisement">
        <b-breadcrumb v-if="advertisement.subsubcategory" class="breadcrumb-box p-2">
          <b-breadcrumb-item href="/b2b">
            <font-awesome-icon icon="home" /> {{ $t('riportalApp.advertisement.breadcrumb.home') }}
          </b-breadcrumb-item>
          <b-breadcrumb-item href="#">{{
            advertisement.subsubcategory.advertisementSubcategory.advertisementCategory.name
          }}</b-breadcrumb-item>
          <b-breadcrumb-item href="#">{{ advertisement.subsubcategory.advertisementSubcategory.name }}</b-breadcrumb-item>
          <b-breadcrumb-item href="#">{{ advertisement.subsubcategory.name }}</b-breadcrumb-item>
          <b-breadcrumb-item active>{{ advertisement.title }}</b-breadcrumb-item>
        </b-breadcrumb>

        <b-row class="mb-4">
          <div class="col-xs-12 col-md-6 col-lg-7 d-flex flex-column">
            <h2 class="jh-entity-heading mb-1 mb-md-5">{{ advertisement.title }}</h2>
            <div class="company-info-responsive mb-5"></div>
            <div>
              <dl class="d-flex flex-wrap mb-1">
                <dt class="mr-4">
                  <span>{{ $t('riportalApp.advertisement.type') }}:</span>
                </dt>
                <dd>
                  <div v-if="advertisement.type">
                    {{ advertisement.type.type }}
                  </div>
                </dd>
              </dl>
              <dl class="d-flex flex-wrap mb-1">
                <dt class="mr-4">
                  <span>{{ $t('riportalApp.advertisement.kind') }}:</span>
                </dt>
                <dd>
                  <div v-if="advertisement.kind">
                    {{ advertisement.kind.kind }}
                  </div>
                </dd>
              </dl>
            </div>
          </div>
          <div class="d-none d-md-block col-md-6 col-lg-5">
            <section
              v-if="advertisement.company && companyRatingsDTO"
              class="company-info"
              @click="prepareCollaborationsModal()"
              style="cursor: pointer;"
              v-b-modal.collaborationsModal
            >
              <b-card>
                <div class="d-flex align-items-center justify-content-between mb-2">
                  <div class="d-flex">
                    <div v-if="advertisement.company.logo" class="img-box mr-2">
                      <img
                        :src="advertisementService().retrieveImage(advertisement.company.logo.filename)"
                        alt="company logo"
                        class="img-logo"
                      />
                    </div>
                    <div v-else class="img-box mr-2 placeholder-logo">{{ getCompanyInitials(advertisement.company) }}</div>
                    <router-link
                      class="d-flex flex-wrap align-items-center"
                      :to="{ name: 'CompanyView', params: { companyId: advertisement.company.id } }"
                    >
                      <h2 class="company-title mb-0" style="align-self: center;">{{ advertisement.company.name }}</h2>
                    </router-link>
                  </div>
                  <div
                    v-if="companyRatingsDTO.totalRatings > 0 && companyRatingsDTO.averageRating"
                    class="company-avg-star d-flex align-items-center"
                  >
                    <font-awesome-icon icon="star" class="fa-xs mr-1"></font-awesome-icon>
                    <div>
                      <span class="">{{ companyRatingsDTO.averageRating }}</span>
                    </div>
                  </div>
                </div>
                <div class="d-flex flex-column">
                  <div>
                    <b-form-rating
                      id="rating-inline"
                      inline
                      value="4"
                      class="p-0 mr-1"
                      variant="primary"
                      size="sm"
                      stars="4"
                      disabled
                    ></b-form-rating>
                    <label for="rating-inline"
                      >{{ companyRatingsDTO.percentageRating4 }} % ({{ companyRatingsDTO.totalRatings4 }}
                      {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                    >
                  </div>
                  <div>
                    <b-form-rating
                      id="rating-inline"
                      inline
                      value="3"
                      class="p-0 mr-1"
                      variant="primary"
                      size="sm"
                      stars="4"
                      disabled
                    ></b-form-rating>
                    <label for="rating-inline"
                      >{{ companyRatingsDTO.percentageRating3 }} % ({{ companyRatingsDTO.totalRatings3 }}
                      {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                    >
                  </div>
                  <div>
                    <b-form-rating
                      id="rating-inline"
                      inline
                      value="2"
                      class="p-0 mr-1"
                      variant="primary"
                      size="sm"
                      stars="4"
                      disabled
                    ></b-form-rating>
                    <label for="rating-inline"
                      >{{ companyRatingsDTO.percentageRating2 }} % ({{ companyRatingsDTO.totalRatings2 }}
                      {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                    >
                  </div>
                  <div>
                    <b-form-rating
                      id="rating-inline"
                      inline
                      value="1"
                      class="p-0 mr-1"
                      variant="primary"
                      size="sm"
                      stars="4"
                      disabled
                    ></b-form-rating>
                    <label for="rating-inline"
                      >{{ companyRatingsDTO.percentageRating1 }} % ({{ companyRatingsDTO.totalRatings1 }}
                      {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                    >
                  </div>
                </div>
              </b-card>
            </section>
          </div>
        </b-row>

        <section class="description mb-4">
          <h5 v-text="$t('riportalApp.advertisement.description')">Opis</h5>
          <b-card style="white-space: preserve-breaks;">
            {{ advertisement.description }}
          </b-card>
        </section>
        <section class="conditions mb-4">
          <h5 v-text="$t('riportalApp.advertisement.conditions')">Uslovi</h5>
          <b-card style="white-space: preserve-breaks;">
            {{ advertisement.conditions }}
          </b-card>
        </section>
        <section class="contact-share d-flex flex-column flex-md-row align-items-center justify-content-between mb-4">
          <div class="buttons d-flex flex-column flex-md-row mb-3 mb-md-0">
            <b-button
              v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner)"
              v-on:click="prepareAdInquiry(advertisement)"
              class="btn btn-modal mb-2 mb-md-0 mb mr-0 mr-md-2"
              v-b-modal.adInquiry
            >
              <span v-text="$t('riportalApp.advertisement.interactionButtons.question')">Pošalji upit</span>
            </b-button>
            <b-button
              v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner)"
              v-on:click="prepareCreateMeetingModal(advertisement)"
              class="btn btn-modal mb-2 mb-md-0 mr-0 mr-md-2"
            >
              <span v-text="$t('riportalApp.advertisement.interactionButtons.appointment')"> Zakaži sastanak</span>
            </b-button>
            <b-button
              v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner)"
              v-on:click="prepareAdCollaboration(advertisement)"
              class="btn btn-modal mb-2 mb-md-0 mr-0 mr-md-2"
              v-b-modal.adCollaboration
            >
              <span v-text="$t('riportalApp.advertisement.interactionButtons.collaboration')"> Pokreni saradnju</span>
            </b-button>

            <router-link
              v-if="advertisement.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || isCompanyOwner)"
              :to="{ name: 'AdvertisementEdit', params: { advertisementId: advertisement.id } }"
              tag="button"
              class="btn btn-primary mb-2 mb-md-0"
            >
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
            </router-link>
          </div>
          <div class="share-icons d-flex justify-content-end">
            <div class="d-flex justify-content-between">
              <a href="#" @click="shareOnNewTwitter($event)">
                <img class="mb-1 icon-contact mr-2" src="/content/images/x-twitter.svg" alt="X icon" />
              </a>
              <a href="#" @click="shareOnLinkedIn($event)">
                <img class="mb-1 icon-contact mr-2" src="/content/images/linkedin.svg" alt="Linkedin icon" />
              </a>
              <a href="#" @click="shareOnFacebook($event)">
                <img class="mb-1 mr-2" src="/content/images/facebook-square.svg" alt="Facebook icon" />
              </a>
              <b-link href="mailto:info@example.com" target="_blank" type="button">
                <font-awesome-icon icon="envelope" class="icon-contact fa-lg mr-2"></font-awesome-icon>
              </b-link>
              <b-link @click="copyToClipboard" type="button">
                <font-awesome-icon icon="copy" class="icon-contact fa-lg"></font-awesome-icon>
              </b-link>
            </div>
          </div>
        </section>

        <section
          v-if="advertisement.company && companyRatingsDTO"
          class="company-info-responsive d-block d-md-none"
          @click="prepareCollaborationsModal()"
          style="cursor: pointer;"
          v-b-modal.collaborationsModal
        >
          <b-card>
            <div class="d-flex align-items-center justify-content-between mb-2">
              <div class="d-flex">
                <div v-if="advertisement.company.logo" class="img-box mr-2">
                  <img
                    :src="advertisementService().retrieveImage(advertisement.company.logo.filename)"
                    alt="company logo"
                    class="img-logo"
                  />
                </div>
                <div v-else class="img-box mr-2 placeholder-logo">{{ getCompanyInitials(advertisement.company) }}</div>
                <router-link
                  class="d-flex flex-wrap align-items-center"
                  :to="{ name: 'CompanyView', params: { companyId: advertisement.company.id } }"
                >
                  <h2 class="company-title mb-0" style="align-self: center;">{{ advertisement.company.name }}</h2>
                </router-link>
              </div>
              <div class="company-avg-star d-flex align-items-center">
                <font-awesome-icon icon="star" class="fa-xs mr-1"></font-awesome-icon>
                <div>
                  <span v-if="companyRatingsDTO.averageRating" class="">{{ companyRatingsDTO.averageRating }}</span>
                </div>
              </div>
            </div>
            <div class="d-flex flex-column">
              <div>
                <b-form-rating
                  id="rating-inline"
                  inline
                  value="4"
                  class="p-0 mr-1"
                  variant="primary"
                  size="sm"
                  stars="4"
                  readonly
                ></b-form-rating>
                <label for="rating-inline"
                  >{{ companyRatingsDTO.percentageRating4 }} % ({{ companyRatingsDTO.totalRatings4 }}
                  {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                >
              </div>
              <div>
                <b-form-rating
                  id="rating-inline"
                  inline
                  value="3"
                  class="p-0 mr-1"
                  variant="primary"
                  size="sm"
                  stars="4"
                  readonly
                ></b-form-rating>
                <label for="rating-inline"
                  >{{ companyRatingsDTO.percentageRating3 }} % ({{ companyRatingsDTO.totalRatings3 }}
                  {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                >
              </div>
              <div>
                <b-form-rating
                  id="rating-inline"
                  inline
                  value="2"
                  class="p-0 mr-1"
                  variant="primary"
                  size="sm"
                  stars="4"
                  readonly
                ></b-form-rating>
                <label for="rating-inline"
                  >{{ companyRatingsDTO.percentageRating2 }} % ({{ companyRatingsDTO.totalRatings2 }}
                  {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                >
              </div>
              <div>
                <b-form-rating
                  id="rating-inline"
                  inline
                  value="1"
                  class="p-0 mr-1"
                  variant="primary"
                  size="sm"
                  stars="4"
                  readonly
                ></b-form-rating>
                <label for="rating-inline"
                  >{{ companyRatingsDTO.percentageRating1 }} % ({{ companyRatingsDTO.totalRatings1 }}
                  {{ $t('riportalApp.advertisement.of') }} {{ companyRatingsDTO.totalRatings }})</label
                >
              </div>
            </div>
          </b-card>
        </section>
        <hr class="line" />

        <section v-if="advertisementImagesArray.length > 0" class="section-gallery mb-4">
          <div class="prev-box mr-2">
            <b-button variant="none" class="prevButton" @click="scrollPrev">
              <font-awesome-icon icon="caret-left" class="fa-lg"></font-awesome-icon>
            </b-button>
          </div>
          <div class="wrapper">
            <div class="carousel" ref="carousel" v-if="advertisementImagesArray">
              <img
                v-for="(document, index) in advertisementImagesArray"
                :key="index"
                @click="onPreviewImage(index)"
                :src="document"
                alt="img"
                @load="onImageLoad"
              />
            </div>
            <div
              v-if="showMask"
              ref="lightbox"
              class="lightbox"
              tabindex="0"
              @keydown.left="prev"
              @keydown.right="next"
              @keydown.esc="onClosePreviewImage"
              @click="onClosePreviewImage"
            >
              <div class="light-box-contain d-flex align-items-center justify-content-center">
                <button v-if="previewImage" class="close-btn" @click="onClosePreviewImage()">
                  <font-awesome-icon icon="times" class="fa-lg"></font-awesome-icon>
                </button>
                <button class="btn-lightbox-carousel btn-prev" @click.stop="prev()">
                  <font-awesome-icon icon="caret-left" class="fa-3x icon-lightbox-carousel icon-prev"></font-awesome-icon>
                </button>
                <button class="btn-lightbox-carousel btn-next" @click.stop="next()">
                  <font-awesome-icon icon="caret-right" class="fa-3x icon-lightbox-carousel icon-next"></font-awesome-icon>
                </button>
                <div v-if="previewImage" class="lightbox-img d-flex align-items-center justify-content-center" @click.stop>
                  <img :src="currentLightboxImage" alt="Image description" class="img-fluid" />
                </div>
              </div>
            </div>
          </div>
          <div class="next-box ml-2">
            <b-button variant="none" class="nextButton" @click="scrollNext">
              <font-awesome-icon icon="caret-right" class="fa-lg"></font-awesome-icon>
            </b-button>
          </div>
        </section>

        <section class="documents">
          <h3 v-text="$t('riportalApp.advertisement.documents')" class="mb-4">Dokumenti</h3>
          <div v-for="document in advertisement.documents">
            <p v-if="document.type.type == 'document'" class="mb-1">
              <a
                class="text-info font-weight-normal"
                :href="advertisementService().retrieveDocument(document.filename)"
                target="_blank"
                title="Preuzmite dokument"
                >{{ document.filename }}
              </a>
            </p>
          </div>
        </section>
        
        <hr v-if="advertisement.advertisementSupporters && advertisement.advertisementSupporters.some(supporter => supporter.status.nameEn === advertisementSupporterStatusOptions.ACCEPTED)" class="line" />

        <section v-if="advertisement.advertisementSupporters && advertisement.advertisementSupporters.some(supporter => supporter.status.nameEn === advertisementSupporterStatusOptions.ACCEPTED)" class="supporters">
          <h3 v-text="$t('riportalApp.advertisement.advertisementSupporters')" class="mb-4">Priključeni oglašivači</h3>
          <div v-for="supporter in advertisement.advertisementSupporters" :key="supporter.id">
            <div v-if="supporter.hasAccepted" class="d-flex align-items-center justify-content-between mb-2">
              <div class="d-flex">
                <div v-if="supporter.company.logo" class="img-box mr-2">
                  <img :src="advertisementService().retrieveImage(supporter.company.logo.filename)" alt="company logo" class="img-logo" />
                </div>
                <div v-else class="img-box mr-2 placeholder-logo">{{ getCompanyInitials(supporter.company) }}</div>
                <router-link
                  class="d-flex flex-wrap align-items-center"
                  :to="{ name: 'CompanyView', params: { companyId: supporter.company.id } }"
                >
                  <h2 class="company-title mb-0" style="align-self: center;">{{ supporter.company.name }}</h2>
                </router-link>
              </div>
            </div>
          </div>
        </section>

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

              <b-input v-model="meetingEvent.location" class="mb-3" placeholder="Unesite lokaciju..."></b-input>

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

              <hr />
              <label for="" v-text="'Pozovite učesnike koji nisu na B2B portalu'">Pozovite učesnike koji nisu na B2B portalu</label>
              <b-input-group>
                <b-form-input
                  type="email"
                  ref="email-address"
                  class="form-control mb-3"
                  name="email-address"
                  id="email-address"
                  placeholder="Unesite email adresu..."
                  v-model="nonB2BMeetingParticipantEmail"
                  @keyup.enter="addNonB2BMeetingParticipant()"
                ></b-form-input>
                <div>
                  <b-button
                    @click="addNonB2BMeetingParticipant()"
                    variant="primary"
                    v-text="'Dodaj'"
                    :disabled="!$v.nonB2BMeetingParticipantEmail.email || !$v.nonB2BMeetingParticipantEmail.required"
                    >Dodaj
                  </b-button>
                </div>
              </b-input-group>
              <small class="form-text text-danger" v-if="!isEmailValid" v-text="'Email adresa nije ispravna'"
                >Email adresa nije ispravna.
              </small>
              <div v-for="email in nonB2BParticipantsEmails" :key="email" class="d-flex align-items-center justify-content-between mb-3">
                <span>{{ email }}</span>
                <b-button @click="removeNonB2BMeetingParticipant(email)" variant="primary" class="close">x</b-button>
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

        <b-modal v-if="collaborations" ref="collaborationsModal" id="collaborationsModal">
          <span slot="modal-title"><span id="collaboration" v-text="$t('riportalApp.collaboration.home.title')">Saradnje</span></span>
          <div class="modal-body">
            <b-card v-for="collaboration in collaborations" :key="collaboration.id" class="card-box mb-3">
              <div class="d-flex align-items-center">
                <div class="d-flex mb-2">
                  <div
                    v-if="collaboration.companyOffer.id == advertisement.company.id && collaboration.companyRequest.logo"
                    class="img-box mr-2"
                  >
                    <img
                      :src="advertisementService().retrieveImage(collaboration.companyRequest.logo.filename)"
                      alt="company logo"
                      class="img-logo"
                    />
                  </div>
                  <div
                    v-else-if="collaboration.companyRequest.id == advertisement.company.id && collaboration.companyOffer.logo"
                    class="img-box mr-2"
                  >
                    <img
                      :src="advertisementService().retrieveImage(collaboration.companyOffer.logo.filename)"
                      alt="company logo"
                      class="img-logo"
                    />
                  </div>
                  <div v-else class="img-box mr-2 placeholder-logo">
                    {{
                      collaboration.companyOffer.id == advertisement.company.id
                        ? getCompanyInitials(collaboration.companyRequest)
                        : getCompanyInitials(collaboration.companyOffer)
                    }}
                  </div>
                  <h2 class="company-title mb-0" style="align-self: center;">
                    {{
                      collaboration.companyOffer.id == advertisement.company.id
                        ? collaboration.companyRequest.name
                        : collaboration.companyOffer.name
                    }}
                  </h2>
                </div>
              </div>
              <h4 class="mb-4">{{ collaboration.advertisement.title }}</h4>
              <div class="d-flex" style="flex-direction: column;">
                <div class="mb-2">
                  {{ collaboration.datetime ? $d(Date.parse(collaboration.datetime.toString()), 'short') : '' }}
                </div>
                <div class="align-items-center">
                  <b-form-rating
                    id="rating-inline"
                    inline
                    :value="
                      collaboration.companyOffer.id == advertisement.company.id
                        ? collaboration.ratingRequest.number
                        : collaboration.ratingOffer.number
                    "
                    class="mr-4"
                    variant="primary"
                    size="sm"
                    stars="4"
                    disabled
                  ></b-form-rating>
                  <label for="rating-inline">{{
                    collaboration.companyOffer.id == advertisement.company.id ? 'Tražilac' : 'Oglašivač'
                  }}</label>
                </div>
              </div>

              <hr />
              <div>
                <p>
                  {{
                    collaboration.companyOffer.id == advertisement.company.id ? collaboration.commentRequest : collaboration.commentOffer
                  }}
                </p>
              </div>
            </b-card>
          </div>
          <div slot="modal-footer">
            <button class="btn btn-danger" v-text="$t('entity.action.close')" v-on:click="closeCollaborationsModal()">
              Zatvori
            </button>
          </div>
        </b-modal>
      </div>
    </div>
  </div>
</template>

<style>
.jh-entity-details > dd {
  justify-self: left;
  margin-left: 5rem !important;
}

.container-box {
  color: #23384b;
}

.startech-link,
.startech-link:link,
.startech-link:visited,
.startech-link:hover,
.startech-link:active {
  color: blue;
  text-decoration: none;
}

/* --- DESCRIPTION --- */

.card-body {
  padding: 10px;
}

.breadcrumb {
  background-color: #cfcdcd;
  font-size: 14px;
}

.breadcrumb-item a {
  color: #23384b;
}

.breadcrumb-item span {
  color: #23384b;
}

/* --- DESCRIPTION END ---  */

/* --- COMPANY INFO --- */

.img-box {
  height: 3.125rem;
}

.img-logo {
  height: 100%;
  object-fit: cover;
}

.company-title {
  white-space: normal;
  font-size: 1.2em;
  font-weight: 400;
}

.company-avg-star {
  font-size: 0.8em;
}

/* --- COMPANY INFO END --- */

/* --- BUTTONS MODAL --- */
.btn-modal {
  background-color: #23384b;
  border-color: #23384b;
}

.btn-modal:hover {
  background-color: #1e2b37;
  border-color: #1e2b37;
}

.iconClass {
  width: 25px;
  height: 25px;
}

.icon-contact {
  color: #23384b;
}

/* --- BUTTONS MODAL END --- */

/* --- LINE --- */

.line {
  background-color: #23384b;
}

/* --- LINE END --- */

/* --- GALLERY --- */

.section-gallery {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.prev-box {
  width: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.next-box {
  width: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wrapper {
  max-width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wrapper .carousel {
  font-size: 0px;
  width: 100%;
  overflow: hidden;
  cursor: pointer;
  white-space: nowrap;
}

.carousel img {
  height: 240px;
  padding: 16px;
  object-fit: contain;
}
.carousel img:first-child {
  margin-left: 0px;
}

/* --- GALLERY END --- */

/* --- LIGHTBOX --- */

.lightbox {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.85);
  z-index: 1000;
  padding-right: 50px;
  padding-left: 50px;
  padding-top: 50px;
  padding-bottom: 50px;
}

.light-box-contain {
  width: 100%;
  height: 100%;
}

.lightbox-img {
  width: 100%;
  height: 100%;
}

.lightbox-img img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.close-btn {
  position: absolute;
  top: 20px;
  right: 30px;
  padding: 0;
  color: #fff;
  background: 0 0;
  border: 0;
  cursor: pointer;
}

.close-btn:focus {
  outline: none;
}

.btn-lightbox-carousel {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 0;
  color: #fff;
  background: 0 0;
  border: 0;
  cursor: pointer;
  z-index: 1001;
}

.btn-lightbox-carousel:focus {
  outline: none;
}

.btn-prev {
  left: 20px;
}

.btn-next {
  right: 20px;
}

/* --- LIGHTBOX END--- */

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
  width: 3.125rem;
  height: 3.125rem;
  margin-right: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ccc;
  border-radius: 50%;
  color: black;
  text-align: center; /* Center text inside the div */
}

.b-rating.disabled {
  color: rgb(40, 40, 56);
}
</style>

<script lang="ts" src="./advertisement-details.component.ts"></script>
