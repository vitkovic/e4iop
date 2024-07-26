<template>
  <div class="row justify-content-center container-box">
    <div class="col-12 col-md-10 col-xl-8">
      <div v-if="company">
        <b-row class="mb-4">
          <div class="col-12 col-lg-9 d-flex flex-column">
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
                v-if="companyRatingsDTO && companyRatingsDTO.totalRatings > 0"
                class="mr-3"
                :rating="companyRatingsDTO.totalRatings"
                icon="handshake"
                v-b-tooltip.hover.v-info
                :title="$t('riportalApp.company.badges.collaborations')"
              ></rating-badge>
              <rating-badge
                v-if="companyRatingsDTO && companyRatingsDTO.totalRatings > 0"
                class="mr-3"
                :rating="companyRatingsDTO.averageRating"
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
              <!-- <rating-badge
                icon="toggle-on"
                v-b-tooltip.hover.v-info
                :title="$t('riportalApp.company.badges.specialConditions')"
              ></rating-badge> -->
            </div>

            <div>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.businessName')">Vrsta:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.businessName }}</span>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.foundationDate')">Podaci:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.foundationDate ? $d(Date.parse(company.foundationDate.toString()), { dateStyle: 'short' }) : '' }}</span>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.registrationDateList')">Datum registracije:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.createdAt ? $d(Date.parse(company.createdAt.toString()), { dateStyle: 'short' }) : '' }}</span>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.address')">Vrsta:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.address }}</span>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.primaryActivity')">Spisak delatnosti:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.primaryActivity }}</span>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.pib')">Vrsta:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.pib }}</span>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.registrationNumber')">Vrsta:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.registrationNumber }}</span>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.website')">Vrsta:</span>
                </dt>
                <dd class="col-sm-7">
                  <b-link v-if="company.website" :to="`//${company.website}`" target="_blank" class="text-body">{{
                    company.website.replace(/^https?:\/\//, '')
                  }}</b-link>
                </dd>
              </dl>
              <dl class="row d-flex flex-wrap mb-1">
                <dt class="col-sm-4 col-lg-3 mr-4">
                  <span v-text="$t('riportalApp.company.email')">Vrsta:</span>
                </dt>
                <dd class="col-sm-7">
                  <span>{{ company.email }}</span>
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
                <a href="#" @click="shareOnNewTwitter($event)">
                  <img class="mb-1 icon-contact mr-2" src="/content/images/x-twitter.svg" alt="X-twitter icon" />
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
              <b-dropdown id="dropdown-right" right variant="none" class="d-block d-md-none custom-dropdown">
                <template #button-content>
                  <font-awesome-icon icon="share-alt" class="fa-lg mr-2 icon-contact"></font-awesome-icon>
                </template>
                <b-dropdown-item @click="shareOnNewTwitter($event)">
                  <a href="#" @click="shareOnNewTwitter($event)">
                    <img class="mb-1 icon-contact mr-2" src="/content/images/x-twitter.svg" alt="X-twitter icon" /><span>X</span>
                  </a>
                </b-dropdown-item>
                <b-dropdown-item @click="shareOnLinkedIn($event)">
                  <a href="#" @click="shareOnLinkedIn($event)">
                    <img class="mb-1 icon-contact mr-2" src="/content/images/linkedin.svg" alt="Linkedin icon" /><span>Linkedin</span>
                  </a>
                </b-dropdown-item>
                <b-dropdown-item @click="shareOnFacebook($event)">
                  <a href="#" @click="shareOnFacebook($event)">
                    <img class="mb-1 mr-2" src="/content/images/facebook-square.svg" alt="Facebook icon" /><span>Facebook</span>
                  </a>
                </b-dropdown-item>
                <b-dropdown-item>
                  <b-link href="mailto:info@example.com" target="_blank" type="button">
                    <font-awesome-icon icon="envelope" class="icon-contact fa-lg mr-2"></font-awesome-icon><span>Email</span>
                  </b-link>
                </b-dropdown-item>
                <b-dropdown-item>
                  <b-link @click="copyToClipboard" type="button">
                    <font-awesome-icon icon="copy" class="icon-contact fa-lg mr-2"></font-awesome-icon><span>Copy company</span>
                  </b-link>
                </b-dropdown-item>
              </b-dropdown>
            </div>
          </div>
        </b-row>
        <div class="col-xs-12 mb-4">
          <div v-html="company.about" class="conditionsText border rounded p-2"></div>
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

        <!-- <section class="section-gallery mb-4">
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
        </section> -->

        <section v-if="companyImagesArray.length > 0" class="section-gallery mb-4">
          <div class="prev-box mr-2">
            <b-button variant="none" class="prevButton" @click="scrollPrev">
              <font-awesome-icon icon="caret-left" class="fa-lg"></font-awesome-icon>
            </b-button>
          </div>
          <div class="wrapper">
            <div class="carousel" ref="carousel" v-if="companyImagesArray">
              <img
                v-for="(document, index) in companyImagesArray"
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

        <!-- <section>
          <b-row class="gallery-lightbox-container">
            <div class="gallery d-flex flex-wrap align-items-center justify-content-center">
              <div class="gallery-img" v-for="(image, index) in companyImagesArray" :key="index">
                <img @click="onPreviewImage(index)" :src="image" alt="image name" />
              </div>
            </div>
            <div v-if="showMask" ref="lightbox" class="lightbox"  tabindex="0" @keydown.left="prev" @keydown.right="next">
              <div class="light-box-contain d-flex align-items-center justify-content-center">
                <button v-if="previewImage" class="close-btn" @click="onClosePreviewImage()">
                  <font-awesome-icon icon="times" class="fa-lg"></font-awesome-icon>
                </button>
                <button class="btn-lightbox-carousel btn-prev" @click="prev()" >
                  <font-awesome-icon icon="caret-left" class="fa-3x icon-lightbox-carousel icon-prev"></font-awesome-icon>
                </button>
                <button class="btn-lightbox-carousel btn-next" @click="next()" >
                  <font-awesome-icon icon="caret-right" class="fa-3x icon-lightbox-carousel icon-next"></font-awesome-icon>
                </button>
                <div v-if="previewImage" class="lightbox-img d-flex align-items-center justify-content-center" >
                  <img :src="currentLightboxImage" alt="Image description" class="img-fluid" />
                </div>
              </div>
            </div>
          </b-row>
        </section> -->

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
        <section v-if="company.documents && company.documents.length > 0" class="mb-4">
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

        <section v-if="totalItems > 0" class="w-100">
          <h3 v-text="$t('riportalApp.company.collaborationTestimonials')" class="mb-4">Saradnje</h3>
          <div class="d-flex align-items-center justify-content-center w-100">
            <b-row class="d-flex align-items-center justify-content-center testimonial-box mb-3">
              <div class="d-none d-lg-flex align-items-center justify-content-start col-lg-1">
                <b-button variant="none" class="prevButtonColl p-0" @click="previousPage()">
                  <font-awesome-icon icon="caret-left" class="fa-lg"></font-awesome-icon>
                </b-button>
              </div>
              <div class="col-xs-12 col-lg-10 w-100 wrapper-collaboration">
                <div
                  class="carousel-collaboration w-100 d-flex flex-column flex-md-row justify-content-between"
                  ref="carouselCollaboration"
                >
                  <b-card v-for="collaboration in collaborations" :key="collaboration.id" class="card-box">
                    <div class="d-flex align-items-center">
                      <div class="d-flex mb-2" style="height: 65px;">
                        <div
                          v-if="collaboration.companyOffer.id == company.id && collaboration.companyRequest.logo"
                          class="img-logo-test mr-2"
                        >
                          <img
                            :src="companyService().retrieveImage(collaboration.companyRequest.logo.filename)"
                            alt="company logo"
                            class="img-logo"
                          />
                        </div>
                        <div
                          v-else-if="collaboration.companyRequest.id == company.id && collaboration.companyOffer.logo"
                          class="img-logo-test mr-2"
                        >
                          <img
                            :src="companyService().retrieveImage(collaboration.companyOffer.logo.filename)"
                            alt="company logo"
                            class="img-logo"
                          />
                        </div>
                        <div v-else class="img-box mr-2 placeholder-logo">
                          {{
                            collaboration.companyOffer.id == company.id
                              ? getCompanyInitials(collaboration.companyRequest)
                              : getCompanyInitials(collaboration.companyOffer)
                          }}
                        </div>
                        <h3 class="company-title mb-0" style="align-self: center;">
                          {{
                            collaboration.companyOffer.id == company.id
                              ? collaboration.companyRequest.name
                              : collaboration.companyOffer.name
                          }}
                        </h3>
                      </div>
                    </div>
                    <h4 class="mb-1" style="align-self: center; height: 60px;">{{ collaboration.advertisement.title }}</h4>
                    <div class="d-flex" style="flex-direction: column;">
                      <div class="mb-2">
                        {{ collaboration.datetime ? $d(Date.parse(collaboration.datetime.toString()), 'short') : '' }}
                      </div>
                      <div class="align-items-center">
                        <b-form-rating
                          id="rating-inline"
                          inline
                          :value="
                            collaboration.companyOffer.id == company.id
                              ? collaboration.ratingRequest.number
                              : collaboration.ratingOffer.number
                          "
                          class="mr-4"
                          variant="primary"
                          size="sm"
                          stars="4"
                          disabled
                        ></b-form-rating>
                        <label for="rating-inline">{{ collaboration.companyOffer.id == company.id ? 'Tražilac' : 'Oglašivač' }}</label>
                      </div>
                    </div>
                    <hr />
                    <div style="height: 75px;">
                      {{
                        collaboration.companyOffer.id == company.id
                          ? shortenedComment(collaboration.commentRequest)
                          : shortenedComment(collaboration.commentOffer)
                      }}
                    </div>
                    <div class="mt-1 d-flex justify-content-end" style="height: 32px;">
                      <b-button size="sm" v-if="getCommentText(collaboration).length > 90" @click="showModal(collaboration)">
                        {{ $t('entity.action.readMore') }}
                      </b-button>
                    </div>
                  </b-card>
                </div>
              </div>
              <div class="d-none d-lg-flex align-items-center justify-content-end col-lg-1">
                <b-button variant="none" class="nextButtonColl p-0" @click="nextPage()">
                  <font-awesome-icon icon="caret-right" class="fa-lg"></font-awesome-icon>
                </b-button>
              </div>
            </b-row>
          </div>
          <div class="responsivePaginationButtons d-block d-lg-none d-flex align-items-center justify-content-around">
            <div>
              <b-button variant="none" class="prevButtonColl" @click="previousPage()">
                <font-awesome-icon icon="caret-left" class="fa-lg"></font-awesome-icon>
              </b-button>
            </div>
            <div>
              <b-button variant="none" class="nextButtonColl" @click="nextPage()">
                <font-awesome-icon icon="caret-right" class="fa-lg"></font-awesome-icon>
              </b-button>
            </div>
          </div>
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
            <button
              type="button"
              class="btn btn-success"
              id="jhi-confirm-delete-advertisement"
              v-text="$t('entity.action.send')"
              v-on:click="sendInquiry()"
            >
              Pošalji
            </button>
            <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeAdInquiry()">
              Otkaži
            </button>
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
/* LIGHTBOX & GALLERY  */

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

/* LIGHTBOX & GALLERY END  */

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
  /* height: 100%; */
  height: 3.125rem;
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

.dropdown-item a {
  color: #23384b;
  text-decoration: none;
}

.dropdown-item:hover {
  background-color: #fff;
  border: solid 1px #23384b;
}

.dropdown-menu {
  padding: 0px;
}

.icon-contact {
  color: #23384b;
}

.icon-dropdown {
  padding-left: 1px;
}

.card-box {
  width: calc(100% / 2);
  margin: 5px;
  display: inline-block;
  vertical-align: top;
  font-size: initial;
  white-space: normal;
}

.testimonial-box {
  width: 100%;
}

@media (max-width: 992px) {
  .contact-button-section {
    flex-direction: row;
    gap: 5px;
  }
}

@media (max-width: 768px) {
  .carousel-collaboration .card-box {
    display: block;
    width: 100%;
  }
}

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

/* .wrapper-collaboration {
  max-width: 100%;
  overflow: hidden;
}

.wrapper-collaboration .carousel-collaboration {
  font-size: 0px;
  white-space: nowrap;
  padding: 10px;
} */

/* .carousel-collaboration .card-box {
  width: calc(100% / 2);
  margin: 5px;
  display: inline-block;
  vertical-align: top;
  font-size: initial; 
  white-space: normal;
} */

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
  /* margin-left: 14px; */
  /* width: calc(100% / 5); */
}

.carousel img:first-child {
  margin-left: 0px;
}

/* .carousel-collaboration .card-box:first-child {
  margin-left: 5px;
} */

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

/* @media (max-width: 991.98px) {
  .carousel-collaboration {
    white-space: normal; 
    overflow-x: hidden;

    transition: none; 
  }

  .carousel-collaboration .card-box {
    display: block; 
    width: 100%;
  }
} */

.placeholder-logo {
  /* width: 40px; */
  /* height: 40px; */
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

.conditionsText p {
  margin-bottom: 8px;
}
</style>

<script lang="ts" src="./company-details.component.ts"></script>
