<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="company">
                <br>
                <div class="d-flex mb-3">
                    <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)"
                        alt="company logo" 
                        style="height: 100px; margin-right: 1rem">
                    <h2 class="jh-entity-heading"
                        style="align-self: center; text-wrap: nowrap;"> 
                        {{company.name}}
                    </h2>
                    <div class="d-flex w-100" style="justify-content: right;">
                        <router-link v-if="company.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || isCompanyOwner())" :to="{name: 'CompanyEdit', params: {companyId: company.id}}" tag="button" class="btn btn-primary ml-2" style="align-self: center;">
                            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                        </router-link>
                        <router-link v-if="company.id" :to="{name: 'CompanyAdvertisementsView', params: {companyId: company.id}}" tag="button" class="btn btn-primary ml-2" style="align-self: center;">
                            <span v-text="$t('riportalApp.company.interactionButtons.advertisements')"> Pogledaj oglase</span>
                        </router-link>
                        <b-button v-if="company.id && authenticated && (hasAnyAuthority('ROLE_ADMIN') || !isCompanyOwner())"
                            v-on:click="prepareAdInquiry()"
                            variant="dark"
                            class="btn btn-primary ml-2"
                            v-b-modal.adInquiry
                            style="align-self: center;">
                    <span class="d-none d-md-inline" v-text="$t('riportalApp.company.interactionButtons.message')">Pošalji poruku</span>
                </b-button>
                    </div>
                </div>
                <br>

                <br>
                <p>{{ company.about }}</p>
                <hr>
                <dl class="row jh-entity-details"
                    style="justify-items: left; margin: 0;">                    
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.view.type')">Type</span>
                    </dt>
                    <dd>
                        <span>Tip kompanije (proveriti na sta se misli)</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.view.typeRSNIS')">Type RSNIS</span>
                    </dt>
                    <dd>
                        <span>Tip kompanije po APR/RSNIS</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.view.registrationDate')">Registration Date</span>
                    </dt>
                    <dd>
                        Odraditi prikaz datuma u formatu dd.MM.yyyy
                        <span v-if="company.createdAt">{{ company.createdAt.split('T')[0].replaceAll('-', '.') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.view.address')">Address</span>
                    </dt>
                    <dd>
                        <span>Adresa kompanije po APR-u</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.view.category')">Category</span>
                    </dt>
                    <dd>
                        <span>Kategorija kompanije (proveriti na sta se misli)</span>
                    </dd> -->

                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.createdAt')">Created At</span>
                    </dt>
                    <dd>
                        <span v-if="company.createdAt">{{$d(Date.parse(company.createdAt), 'long') }}</span>
                    </dd> -->
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.modifiedAt')">Modified At</span>
                    </dt>
                    <dd>
                        <span v-if="company.modifiedAt">{{$d(Date.parse(company.modifiedAt), 'long') }}</span>
                    </dd> -->
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.rsnisId')">Rsnis Id</span>
                    </dt>
                    <dd>
                        <span>{{company.rsnisId}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.aprId')">Apr Id</span>
                    </dt>
                    <dd>
                        <span>{{company.aprId}}</span>
                    </dd> -->
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{company.name}}</span>
                    </dd> -->
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.about')">About</span>
                    </dt>
                    <dd>
                        <span>{{company.about}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.fields')">Fields</span>
                    </dt>
                    <dd>
                        <span>{{company.fields}}</span>
                    </dd> -->
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.badge')">Badge</span>
                    </dt>
                    <dd>
                        <span v-for="(badge, i) in company.badges" :key="badge.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'BadgeView', params: {badgeId: badge.id}}">{{badge.id}}</router-link>
                        </span>
                    </dd> -->
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.document')">Document</span>
                    </dt>
                    <dd>
                        <span v-for="(document, i) in company.documents" :key="document.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'DocumentView', params: {documentId: document.id}}">{{document.id}}</router-link>
                        </span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.createdBy')">Created By</span>
                    </dt> -->
                    <!-- <dd>
                        <div v-if="company.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: company.createdBy.id}}">{{company.createdBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.company.modifiedBy')">Modified By</span>
                    </dt>
                    <dd>
                        <div v-if="company.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: company.modifiedBy.id}}">{{company.modifiedBy.id}}</router-link>
                        </div>
                    </dd> -->
                    <!-- <dt>
                        <span v-text="$t('riportalApp.company.logo')">Logo</span>
                    </dt>
                    <dd>
                        <div v-if="company.logo">
                            <router-link :to="{name: 'DocumentView', params: {documentId: company.logo.id}}">{{company.logo.id}}</router-link>
                        </div>
                    </dd> -->
                </dl>

                <!-- <section class="badge-section">
                    <h3 v-text="$t('riportalApp.company.view.badges')">Badges</h3>
                    <div class="d-flex">
                        <div class="badge-wrapper mr-4">
                            <img src="/content/images/badge-placeholder-1.png"
                            alt="badge image" 
                            style="width:128px; height: 128px;">
                            <div class="badge-description">
                                <h4>Badge title</h4>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
                            </div>
                        </div>
                        <div class="badge-wrapper mr-4">
                            <img src="/content/images/badge-placeholder-2.png"
                            alt="badge image" 
                            style="width:128px; height: 128px">
                            <div class="badge-description">
                                <h4>Badge title</h4>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
                            </div>
                        </div>
                        <div class="badge-wrapper mr-4">
                            <img src="/content/images/badge-placeholder-3.png"
                            alt="badge image" 
                            style="width:128px; height: 128px">
                            <div class="badge-description">
                                <h4>Badge title</h4>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
                            </div>
                        </div>
                    </div>
                </section> -->

                    
                    
                <section>
                    <h3 v-text="$t('riportalApp.company.gallery')">Galerija</h3>
                    <div class="row images-container mt-2">
                    <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 mb-4" v-for="document in company.documents" v-if="document.type.type == 'image'">
                        <img class="img-thumbnail img-fluid"  :src="companyService().retrieveImage(document.filename)" width="200px"/>
                    </div>
                    </div>

                </section>
                <section>
                    <h3 v-text="$t('riportalApp.company.documents')">Dokumenti</h3>
                    <div v-for="document in company.documents">
                        <li v-if="document.type.type == 'document'">
                            <a class="text-info" 
                            :href="companyService().retrieveDocument(document.filename)" 
                            target="_blank" 
                            title="Preuzmite dokument">{{ document.filename }}
                            </a>
                        </li>
                    </div>
                </section>
                <br>
                <section>
                    <h3 v-text="$t('riportalApp.company.collaborations')">Saradnje</h3>
                </section>
                <br>
                <!-- <section>
                    <h3 v-text="'RSNIS podaci'">RSNIS podaci</h3>
                </section>
                <br>
                <section>
                    <h3 v-text="'Podeli na društvenim mrežama'">Podeli na društvenim mrežama</h3>
                </section> -->
                <br>
                <b-modal v-if="inquiryDTO" ref="adInquiry" id="adInquiry" >
                    <span slot="modal-title"><span id="riportalApp.advertisement.delete.question"> {{ $t('riportalApp.company.modal.advertisementModalTitle') }}{{ company.name }}</span></span>
                    <div class="modal-body">
                        <label name="inquiry-subject" v-text="$t('entity.form.messageTitle')">Naziv poruke:</label>
                        <b-input v-model.trim="inputSubject.value" @blur="clearValidity('inputSubject')"></b-input>
                        <p v-if="!inputSubject.isValid" class="text-danger small" v-text="$t('entity.form.validation.title')">Naziv poruke ne može biti prazan.</p>
                        <br>
                        <label name="inquiry-content" v-text="$t('entity.form.messageContent')">Sadržaj poruke:</label>
                        <b-textarea v-model.trim="textareaContent.value" @blur="clearValidity('textareaContent')"></b-textarea>
                        <p v-if="!textareaContent.isValid" class="text-danger small" v-text="$t('entity.form.validation.content')">Sadržaj poruke ne može biti prazan.</p>
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

.jh-entity-details > dd  {
    justify-self: left;
    margin-left: 5rem !important;
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
    background-color: rgb(128,128,128);
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

.img-thumbnail {
  width: 100%; /* Ensure images fill their container */
  height: 200px; /* Maintain aspect ratio */
  cursor: pointer; /* Change cursor to pointer on hover */
  padding: 0;
  transition: all 0.4s;
  box-shadow: 0 0.6rem 1.2rem rgba(0, 0, 0, 0.075);
  object-fit: cover; /* Ensure images fill the entire card */
}

.img-thumbnail:hover {
  transform: translateY(-0.3rem);
  box-shadow: 0 0.8rem 1.6rem rgba(0, 0, 0, 0.06);
}

h1,
h2 {
  color: #004b90;
}

</style>


<script lang="ts" src="./company-details.component.ts">
</script>
