<template>
  <div class="row justify-content-center container-box">
    <div class="col-xs-12 col-md-8">
      <div v-if="company">
        <b-row class="mb-4">
          <div class="col-xs-12 col-lg-9 d-flex flex-column">
            <div class="d-flex align-items-center mb-5">
              <div class="image-box mr-4">
                <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)" alt="company logo" class="img-logo" />
              </div>
              <h2 class="company-title" style="align-self: center;">
                {{ company.name }}
              </h2>
            </div>
            <div class="d-flex mb-3">
              <rating-badge
                class="mr-3"
                :rating="4"
                icon="handshake"
                v-b-tooltip.hover.v-info
                :title="$t('riportalApp.company.badges.collaborations')"
              ></rating-badge>
              <rating-badge
                class="mr-3"
                :rating="4"
                icon="star"
                v-b-tooltip.hover.v-info
                :title="$t('riportalApp.company.badges.rating')"
              ></rating-badge>
              <rating-badge
                v-if="company.isEinovator"
                class="mr-3"
                icon="lightbulb"
                v-b-tooltip.hover.v-info
                :title="$t('riportalApp.company.badges.registered')"
              ></rating-badge>
              <rating-badge
                icon="toggle-on"
                v-b-tooltip.hover.v-info
                :title="$t('riportalApp.company.badges.specialConditions')"
              ></rating-badge>
            </div>

            <div>
              <dl class="d-flex flex-wrap mb-1">
                <dt class="mr-4">
                  <span v-text="$t('riportalApp.company.kind')">Vrsta:</span>
                </dt>
                <dd>
                  <span>Vrsta kompanije</span>
                </dd>
              </dl>
              <dl class="d-flex flex-wrap mb-1">
                <dt class="mr-4">
                  <span v-text="$t('riportalApp.company.data')">Podaci:</span>
                </dt>
                <dd>
                  <span>Dodatni podaci</span>
                </dd>
              </dl>
              <dl class="d-flex flex-wrap mb-1">
                <dt class="mr-4">
                  <span v-text="$t('riportalApp.company.registrationDateList')">Datum registracije:</span>
                </dt>
                <dd>
                  <span>{{ company.createdAt }}</span>
                </dd>
              </dl>
              <dl class="d-flex flex-wrap mb-1">
                <dt class="mr-4">
                  <span v-text="$t('riportalApp.company.activities')">Spisak delatnosti:</span>
                </dt>
                <dd>
                  <span>Delatnost 1, delatnost 2</span>
                </dd>
              </dl>
            </div>
          </div>
          <div class="col-xs-12 col-lg-3 d-flex flex-xs-row flex-lg-column info-section">
            <div class="contact-button-section mb-3 w-100">
              <router-link
                v-if="company.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || isCompanyOwner())"
                :to="{ name: 'CompanyEdit', params: { companyId: company.id } }"
                tag="button"
                class="btn btn-primary"
              >
                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
              </router-link>
              <router-link
                v-if="company.id"
                :to="{ name: 'CompanyAdvertisementsView', params: { companyId: company.id } }"
                tag="button"
                class="btn btn-primary"
              >
                <span v-text="$t('riportalApp.company.interactionButtons.advertisements')"> Pogledaj oglase</span>
              </router-link>
              <b-button
                v-if="company.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner())"
                v-on:click="prepareAdInquiry()"
                variant="primary"
                class="btn btn-primary"
                v-b-modal.adInquiry
              >
                <span v-text="$t('riportalApp.company.interactionButtons.message')">Pošalji poruku</span>
              </b-button>
            </div>
            <div class="d-flex justify-content-end">
              <div class="d-none d-md-flex justify-content-between w-100">
                <a href="#">
                  <img class="mb-1 icon-contact mr-2" src="/content/images/x-twitter.svg" alt="X-twitter icon" />
                </a>
                <a href="#">
                  <img class="mb-1 icon-contact mr-2" src="/content/images/linkedin.svg" alt="Linkedin icon" />
                </a>
                <a href="#">
                  <img class="mb-1 mr-2" src="/content/images/facebook-square.svg" alt="Facebook icon" />
                </a>
                <b-link>
                  <font-awesome-icon icon="envelope" class="icon-contact fa-lg mr-2"></font-awesome-icon>
                </b-link>
                <b-link>
                  <font-awesome-icon icon="copy" class="icon-contact fa-lg"></font-awesome-icon>
                </b-link>
              </div>
              <b-dropdown id="dropdown-right" right variant="none" class="d-block d-md-none custom-dropdown">
                <template #button-content>
                  <font-awesome-icon icon="share-alt" class="fa-lg mr-2 icon-contact"></font-awesome-icon>
                </template>
                <b-dropdown-item>
                  <img class="mb-1 mr-2" src="/content/images/x-twitter.svg" alt="X icon" /><span>X</span>
                </b-dropdown-item>
                <b-dropdown-item>
                  <img class="mb-1 mr-2" src="/content/images/linkedin.svg" alt="Linkedin icon" /><span>Linkedin</span>
                </b-dropdown-item>
                <b-dropdown-item>
                  <img class="mb-1 mr-2" src="/content/images/facebook-square.svg" alt="Facebook icon" /><span>Facebook</span>
                </b-dropdown-item>
                <b-dropdown-item>
                  <font-awesome-icon icon="envelope" class="icon-dropdown fa-lg mr-2"></font-awesome-icon><span>Email</span>
                </b-dropdown-item>
                <b-dropdown-item>
                  <font-awesome-icon icon="copy" class="icon-dropdown fa-lg mr-2"></font-awesome-icon><span>Copy company</span>
                </b-dropdown-item>
              </b-dropdown>
            </div>
          </div>
        </b-row>
        <div class="col-xs-12 mb-4">
          <p>{{ company.about }}</p>
        </div>

        <hr />

        <!-- <section>
          <div class="image-slider">
            <div class="nav-button prev" @click="slideLeft">&lt; Prev</div>
            <b-row class="image-row" ref="imageRow">
              <b-col cols="12" md="2" v-for="(image, index) in imagesToShow" :key="image.id">
                <img :src="image.src" class="img-fluid ml-5" alt="Image" />
              </b-col>
            </b-row>
            <div class="nav-button next" @click="slideRight">Next &gt;</div>
          </div>
        </section> -->

        <section class="section-gallery mb-4">
          <div class="prev-box mr-2">
            <b-button variant="none" class="prevButton" @click="scrollPrev">
              <font-awesome-icon icon="caret-left" class="fa-lg levaIkonica"></font-awesome-icon>
            </b-button>
          </div>
          <div class="wrapper">
            <div class="carousel" ref="carousel">
              <img
                v-for="document in company.documents"
                v-if="document.type.type === 'image'"
                :src="companyService().retrieveImage(document.filename)"
                alt="img"
                @load="onImageLoad"
              />
            </div>
          </div>
          <div class="next-box ml-2">
            <b-button variant="none" class="nextButton" @click="scrollNext">
              <font-awesome-icon icon="caret-right" class="fa-lg"></font-awesome-icon>
            </b-button>
          </div>
        </section>

        <!-- <section>
          <h3 v-text="$t('riportalApp.company.gallery')">Galerija</h3>
          <div class="row images-container mt-2">
            <div
              class="col-xs-12 col-sm-6 col-md-4 col-lg-4 mb-4"
              v-for="document in company.documents"
              :key="document.id"
              v-if="document.type.type == 'image'"
            >
              <img class="img-thumbnail-moja img-fluid" :src="companyService().retrieveImage(document.filename)" width="200px" />
            </div>
          </div>
        </section> -->
        <section class="mb-4">
          <h3 v-text="$t('riportalApp.company.documents')" class="mb-4">Dokumenti</h3>
          <div v-for="document in company.documents">
            <p v-if="document.type.type == 'document'" class="mb-1">
              <a
                class="text-info font-weight-normal"
                :href="companyService().retrieveDocument(document.filename)"
                target="_blank"
                title="Preuzmite dokument"
                >{{ document.filename }}
              </a>
            </p>
          </div>
        </section>
        <br />
        <!-- <section>
          <h3 v-text="$t('riportalApp.company.collaborations')" class="mb-4">Saradnje</h3>
          <b-row class="align-items-center justify-content-center">
            <div class="d-none d-lg-flex align-items-center justify-content-center col-lg-1">
              <b-button variant="none" class="prevButtonColl" @click="scrollPrevColl">
                <font-awesome-icon icon="caret-left" class="fa-lg"></font-awesome-icon>
              </b-button>
            </div>
            <div class="col-xs-12 col-lg-10 wrapper-collaboration">
              <div class="carousel-collaboration" ref="carouselCollaboration">
                <b-card class="card-box">
                  <div class="d-flex align-items-center">
                    <div class="image-box-test mr-2">
                      <img
                        v-if="company.logo"
                        :src="companyService().retrieveImage(company.logo.filename)"
                        alt="company logo"
                        class="img-logo-test"
                      />
                    </div>
                    <h3 class="company-title" style="align-self: center;">
                      {{ company.name }}
                    </h3>
                  </div>
                  <h4>Analiza faktora zastoja masina</h4>
                  <div>
                    <b-form-rating id="rating-inline" inline value="4" class="mr-4" variant="primary" size="sm" stars="4"></b-form-rating>
                    <label for="rating-inline">Oglasivac</label>
                  </div>
                  <hr />
                  <div>
                    <p>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna
                      aliqua. !
                    </p>
                  </div>
                </b-card>
                <b-card class="card-box">
                  <div class="d-flex align-items-center">
                    <div class="image-box-test mr-2">
                      <img
                        v-if="company.logo"
                        :src="companyService().retrieveImage(company.logo.filename)"
                        alt="company logo"
                        class="img-logo-test"
                      />
                    </div>
                    <h3 class="company-title" style="align-self: center;">
                      B.2.B Company
                    </h3>
                  </div>
                  <h4>Ispitivanje kostrukcija</h4>
                  <div>
                    <b-form-rating id="rating-inline" inline value="2" class="mr-4" variant="primary" size="sm" stars="4"></b-form-rating>
                    <label for="rating-inline">Trazilac</label>
                  </div>
                  <hr />
                  <div>
                    <p>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna
                      aliqua. !
                    </p>
                  </div>
                </b-card>
                <b-card class="card-box">
                  <div class="d-flex align-items-center">
                    <div class="image-box-test mr-2">
                      <img
                        v-if="company.logo"
                        :src="companyService().retrieveImage(company.logo.filename)"
                        alt="company logo"
                        class="img-logo-test"
                      />
                    </div>
                    <h3 class="company-title" style="align-self: center;">
                      Mehanika D.O.O.
                    </h3>
                  </div>
                  <h4>Ispitivanje trzista</h4>
                  <div>
                    <b-form-rating id="rating-inline" inline value="3" class="mr-4" variant="primary" size="sm" stars="4"></b-form-rating>
                    <label for="rating-inline">Oglasivac</label>
                  </div>
                  <hr />
                  <div>
                    <p>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna
                      aliqua. !
                    </p>
                  </div>
                </b-card>
              </div>
            </div>
            <div class="d-none d-lg-flex align-items-center justify-content-center col-lg-1">
              <b-button variant="none" class="nextButtonColl" @click="scrollNextColl">
                <font-awesome-icon icon="caret-right" class="fa-lg"></font-awesome-icon>
              </b-button>
            </div>
          </b-row>
        </section> -->
        <section>
          <h3 v-text="$t('riportalApp.company.collaborationTestimonials')" class="mb-4">Saradnje</h3>
          <b-row class="align-items-center justify-content-center">
            <div class="d-none d-lg-flex align-items-center justify-content-center col-lg-1">
              <!-- @click="scrollPrevColl" -->
              <b-button variant="none" class="prevButtonColl">
                <font-awesome-icon icon="caret-left" class="fa-lg"></font-awesome-icon>
              </b-button>
            </div>
            <div class="col-xs-12 col-lg-10 wrapper-collaboration">
              <div class="carousel-collaboration" ref="carouselCollaboration">
                <b-card v-for="(compani, index) in companies" :key="index" class="card-box">
                  <div class="d-flex align-items-center">
                    <div class="image-box-test mr-2">
                      <img v-if="company.logo" :src="compani.logo" alt="company logo" class="img-logo-test" />
                    </div>
                    <h3 class="company-title" style="align-self: center;">
                      {{ compani.name }}
                    </h3>
                  </div>
                  <h4>{{ compani.description }}</h4>
                  <div>
                    <b-form-rating
                      id="rating-inline"
                      inline
                      :value="compani.rating"
                      class="mr-4"
                      variant="primary"
                      size="sm"
                      stars="4"
                      readonly
                    ></b-form-rating>
                    <label for="rating-inline">{{ compani.role }}</label>
                  </div>
                  <hr />
                  <div>
                    <p>{{ compani.details }}</p>
                  </div>
                </b-card>
              </div>
            </div>
            <div class="d-none d-lg-flex align-items-center justify-content-center col-lg-1">
              <!-- @click="scrollNextColl" -->
              <b-button variant="none" class="nextButtonColl">
                <font-awesome-icon icon="caret-right" class="fa-lg"></font-awesome-icon>
              </b-button>
            </div>
          </b-row>
        </section>
        <br />
        <!-- <section>
                    <h3 v-text="'RSNIS podaci'">RSNIS podaci</h3>
                </section>
                <br>
                <section>
                    <h3 v-text="'Podeli na društvenim mrežama'">Podeli na društvenim mrežama</h3>
                </section> -->
        <br />
        <b-modal v-if="inquiryDTO" ref="adInquiry" id="adInquiry">
          <span slot="modal-title"
            ><span id="riportalApp.advertisement.delete.question">
              {{ $t('riportalApp.company.modal.advertisementModalTitle') }}{{ company.name }}</span
            ></span
          >
          <div class="modal-body">
            <label name="inquiry-subject" v-text="$t('entity.form.messageTitle')">Naziv poruke:</label>
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
            <button type="button" class="btn btn-secondary" v-text="'Otkaži'" v-on:click="closeAdInquiry()">Otkaži</button>
            <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisement" v-on:click="sendInquiry()">Pošalji</button>
          </div>
        </b-modal>

        <!-- <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button> -->
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

.image-box {
  height: 7.8rem;
}

.img-logo {
  height: 100%;
  object-fit: cover;
}

.image-box-test {
  height: 4.6rem;
}

.img-logo-test {
  height: 100%;
  object-fit: cover;
}

.company-title {
  font-weight: 400;
  line-height: 1;
}
.contact-button-section {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.badge-section {
  margin-top: 2rem;
  margin-bottom: 2rem;
}

.badge-section > h3 {
  margin-bottom: 1rem;
}

.badge-description {
  display: none;
  padding: 0.5rem;
  border-radius: 0.5rem;
  background-color: rgb(128, 128, 128);
  color: white;
  position: absolute;
  bottom: 100%;
  left: 100%;
  z-index: 10;
  width: 300%;
}

.badge-wrapper {
  position: relative;
  display: inline-block;
}

.badge-wrapper:hover .badge-description {
  display: block;
}

.startech-link,
.startech-link:link,
.startech-link:visited,
.startech-link:hover,
.startech-link:active {
  color: blue;
  text-decoration: none;
}

.img-thumbnail-moja {
  width: 100%; /* Ensure images fill their container */
  height: 200px; /* Maintain aspect ratio */
  cursor: pointer; /* Change cursor to pointer on hover */
  padding: 0;
  transition: all 0.4s;
  box-shadow: 0 0.6rem 1.2rem rgba(0, 0, 0, 0.075);
  object-fit: cover; /* Ensure images fill the entire card */
}

.img-thumbnail-moja:hover {
  transform: translateY(-0.3rem);
  box-shadow: 0 0.8rem 1.6rem rgba(0, 0, 0, 0.06);
}

h1,
h2 {
  color: #004b90;
}

.iconClass {
  width: 25px;
  height: 25px;
}

.badge-container {
  display: inline-block;
  margin-top: 10px;
  margin-bottom: 10px;
}

.badge-circle {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 2px solid #000;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.badge-content {
  text-align: center;
}

.badge-content span {
  display: block;
  font-size: 16px;
}

.badge-content .fa-star {
  font-size: 20px;
  margin-top: 1px;
}

.dropdown-item {
  color: #23384b;
}

.icon-contact {
  color: #23384b;
}

.icon-dropdown {
  padding-left: 1px;
}

@media (max-width: 980px) {
  .contact-button-section {
    flex-direction: row;
    gap: 10px;
  }
}

/* ------------------- */

.section-gallery {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.wrapper {
  max-width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wrapper-collaboration {
  max-width: 100%;
  /* display: flex;
  align-items: center;
  justify-content: center; */
  overflow: hidden;
}

.wrapper-collaboration .carousel-collaboration {
  font-size: 0px;
  /* width: 100%; */
  /* overflow: hidden; */
  white-space: nowrap;
  padding: 10px;
}

.carousel-collaboration .card-box {
  width: calc(100% / 2);
  margin: 5px;
  display: inline-block;
  vertical-align: top; /* Align cards at the top */
  font-size: initial; /* Reset font size for card content */
  white-space: normal;
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
  object-fit: cover;
  /* margin-left: 14px; */
  /* width: calc(100% / 5); */
}

.carousel img:first-child {
  margin-left: 0px;
}

.carousel-collaboration .card-box:first-child {
  margin-left: 5px;
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

.custom-dropdown-icon {
  fill: #23384b;
}

.custom-dropdown .dropdown-toggle::after {
  display: none;
}

@media (max-width: 991.98px) {
  .carousel-collaboration {
    white-space: normal; /* Allow cards to wrap in column layout on smaller screens */
    overflow-x: hidden; /* Hide horizontal scrolling on smaller screens */
    /* display: block;  */
    transition: none; /* Disable transition effect */
  }

  .carousel-collaboration .card-box {
    display: block; /* Display cards in a block layout */
    width: 100%;
  }
}
</style>

<script lang="ts" src="./company-details.component.ts"></script>
