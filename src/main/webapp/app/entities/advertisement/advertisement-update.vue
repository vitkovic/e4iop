<template>
  <div class="row justify-content-center">
    <p>
      
    </p>
    <div class="col-10 col-md-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          v-if="advertisementTitleHasID === true"
          id="riportalApp.advertisement.home.createOrEditLabel"
          v-text="$t('riportalApp.advertisement.home.createTitleLabel')"
        >
          Create an Advertisement
        </h2>
        <h2 v-else id="riportalApp.advertisement.home.createOrEditLabel" v-text="$t('riportalApp.advertisement.home.editTitleLabel')">
          Edit an Advertisement
        </h2>

        <div class="row justify-content-between">
          <!-- <div class="form-group" v-if="advertisement.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="advertisement.id" readonly />
                    </div> -->
          <div class="col-xs-12 col-md-7 col-lg-8 order-2 order-md-1">
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.type')" for="advertisement-type">Type</label>
              <select
                class="form-control"
                id="adve	rtisement-type"
                name="type"
                v-model="advertisement.type"
                :class="{ valid: !$v.advertisement.type.$invalid, invalid: $v.advertisement.type.$invalid }"
                @blur="$v.advertisement.type.$touch()"
                required
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    advertisement.type && advertisementTypeOption.id === advertisement.type.id
                      ? advertisement.type
                      : advertisementTypeOption
                  "
                  v-for="advertisementTypeOption in advertisementTypes"
                  :key="advertisementTypeOption.id"
                  >
                  <span v-if="$store.getters.currentLanguage === 'sr'">{{ advertisementTypeOption.type }}</span>
                  <span v-else-if="$store.getters.currentLanguage === 'src'">{{ advertisementTypeOption.typeSrc }}</span>
                  <span v-else-if="$store.getters.currentLanguage === 'en'">{{ advertisementTypeOption.typeEn }}</span>
                </option
                >
              </select>
              <div v-if="$v.advertisement.type.$anyDirty && $v.advertisement.type.$invalid">
                <small class="form-text text-danger" v-if="!$v.advertisement.type.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
              </div>
            </div>

            <div class="form-group" v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.company')" for="advertisement-company">Company</label>
              <select
                class="form-control"
                id="advertisement-company"
                name="company"
                :class="{ valid: !$v.advertisement.company.$invalid, invalid: $v.advertisement.company.$invalid }"
                v-model="advertisement.company"
                required
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    advertisement.company && companyOption.id === advertisement.company.id ? advertisement.company : companyOption
                  "
                  v-for="companyOption in companies"
                  :key="companyOption.id"
                  >{{ companyOption.name }}</option
                >
              </select>
            </div>
            <div  class="form-group">
              <label
                class="form-control-label"
                v-text="$t('riportalApp.advertisement.activationDatetime')"
                for="advertisement-activationDatetime"
                >Activation Datetime</label
              >
              <div class="d-flex">
                <input
                  id="advertisement-activationDatetime"
                  type="datetime-local"
                  class="form-control"
                  name="activationDatetime"
                  :class="{ valid: !$v.advertisement.activationDatetime.$invalid, invalid: $v.advertisement.activationDatetime.$invalid }"
                  :value="convertDateTimeFromServer($v.advertisement.activationDatetime.$model)"
                  @change="updateInstantField('activationDatetime', $event)"
                  
                />
              </div>
              <div v-if="$v.advertisement.activationDatetime.$anyDirty && $v.advertisement.activationDatetime.$invalid">
                <small
                  class="form-text text-danger"
                  v-if="!$v.advertisement.activationDatetime.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
                <small
                  class="form-text text-danger"
                  v-if="!$v.advertisement.activationDatetime.ZonedDateTimelocal"
                  v-text="$t('entity.validation.ZonedDateTimelocal')"
                >
                  This field should be a date and time.
                </small>
              </div>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.title')" for="advertisement-title">Title</label>
              <input
                type="text"
                class="form-control"
                name="title"
                id="advertisement-title"
                :class="{ valid: !$v.advertisement.title.$invalid, invalid: $v.advertisement.title.$invalid }"
                v-model="$v.advertisement.title.$model"
                @blur="$v.advertisement.title.$touch()"
                required
              />
              <div v-if="$v.advertisement.title.$anyDirty && $v.advertisement.title.$invalid">
                <small class="form-text text-danger" v-if="!$v.advertisement.title.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
              </div>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.description')" for="advertisement-description"
                >Description</label
              >
              <!-- <textarea
                type="text"
                class="form-control"
                name="description"
                id="advertisement-description"
                :class="{ valid: !$v.advertisement.description.$invalid, invalid: $v.advertisement.description.$invalid }"
                v-model="$v.advertisement.description.$model"
                @blur="$v.advertisement.description.$touch()"
                required
              /> -->
              <vue-editor v-model="$v.advertisement.description.$model" :editor-toolbar="customToolbar" @blur="$v.advertisement.description.$touch()" />
              <div v-if="$v.advertisement.description.$anyDirty && $v.advertisement.description.$invalid">
                <small
                  class="form-text text-danger"
                  v-if="!$v.advertisement.description.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
              </div>
            </div>

            <div class="form-group">
              <label class="form-control-label" for="advertisement-kind">
                <span v-text="$t('riportalApp.advertisement.kind')">Kind</span>
                <span v-text="'(' + $t('entity.detail.multipleChoice') + ')'">(višestruki izbor)</span>
              </label>
              <multiselect
                v-model="advertisement.kinds"
                :options="advertisementKinds"
                :multiple="true"
                :close-on-select="false"
                :clear-on-select="false"
                :custom-label="customKindLabel"
                placeholder=""
                :selectLabel="$t('multiselect.selectLabel')"
                :selectedLabel="$t('multiselect.selectedLabel')"
                :deselectLabel="$t('multiselect.deselectLabel')"
                label="kind"
                track-by="kind"
                :class="{ valid: !$v.advertisement.kinds.$invalid, invalid: $v.advertisement.kinds.$invalid }"
                @blur="$v.advertisement.kinds.$touch()"
                required
              >
              </multiselect>
              <div v-if="$v.advertisement.kinds.$anyDirty && $v.advertisement.kinds.$invalid">
                <small class="form-text text-danger" v-if="!$v.advertisement.kinds.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
              </div>
            </div>

            <!-- <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.kind')" for="advertisement-kind">Kind</label>
              <select
                class="form-control"
                id="advertisement-kind"
                name="kind"
                v-model="advertisement.kind"
                :class="{ valid: !$v.advertisement.kind.$invalid, invalid: $v.advertisement.kind.$invalid }"
                @blur="$v.advertisement.kind.$touch()"
                required
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    advertisement.kind && advertisementKindOption.id === advertisement.kind.id
                      ? advertisement.kind
                      : advertisementKindOption
                  "
                  v-for="advertisementKindOption in advertisementKinds"
                  :key="advertisementKindOption.id"
                  >{{ advertisementKindOption.kind }}</option
                >
              </select>
              <div v-if="$v.advertisement.kind.$anyDirty && $v.advertisement.kind.$invalid">
                <small class="form-text text-danger" v-if="!$v.advertisement.kind.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
              </div>
            </div> -->

            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.categorization')" for="advertisement-subsubcategory"
                >Subsubcategory</label
              >
              <select
                class="form-control"
                id="advertisement-subsubcategory"
                name="subsubcategory"
                v-model="advertisement.subsubcategory"
                :class="{ valid: !$v.advertisement.subsubcategory.$invalid, invalid: $v.advertisement.subsubcategory.$invalid }"
                @blur="$v.advertisement.subsubcategory.$touch()"
                required
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    advertisement.subsubcategory && advertisementSubsubcategoryOption.id === advertisement.subsubcategory.id
                      ? advertisement.subsubcategory
                      : advertisementSubsubcategoryOption
                  "
                  v-for="advertisementSubsubcategoryOption in advertisementSubsubcategories"
                  :key="advertisementSubsubcategoryOption.id"
                  >{{ advertisementCategorizationBranch(advertisementSubsubcategoryOption) }}</option
                >
              </select>
              <div v-if="$v.advertisement.subsubcategory.$anyDirty && $v.advertisement.subsubcategory.$invalid">
                <small
                  class="form-text text-danger"
                  v-if="!$v.advertisement.subsubcategory.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
              </div>
            </div>
            <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.advertisement.status')" for="advertisement-status">Status</label>
                <select class="form-control" id="advertisement-status" name="status" v-model="advertisement.status" 
                  :class="{ valid: !$v.advertisement.status.$invalid, invalid: $v.advertisement.status.$invalid }"
                >
                  <option v-bind:value="null"></option>
                  <option
                    v-bind:value="
                      advertisement.status && advertisementStatusOption.id === advertisement.status.id
                        ? advertisement.status
                        : advertisementStatusOption
                    "
                    v-for="advertisementStatusOption in advertisementStatuses"
                    :key="advertisementStatusOption.id" 
                    >{{ advertisementStatusOption.status }}</option
                  >
                </select>
                <div v-if="$v.advertisement.status.$anyDirty && $v.advertisement.status.$invalid">
                <small
                  class="form-text text-danger"
                  v-if="!$v.advertisement.status.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
              </div>
              </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.duration')" for="advertisement-duration"
                >Duration</label
              >
              <select
                class="form-control"
                id="advertisement-duration"
                name="duration"
                v-model="advertisement.duration"
                :class="{ valid: !$v.advertisement.duration.$invalid, invalid: $v.advertisement.duration.$invalid }"
                @blur="$v.advertisement.duration.$touch()"
                required
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    advertisement.duration && advertisementDurationOption.id === advertisement.duration.id
                      ? advertisement.duration
                      : advertisementDurationOption
                  "
                  v-for="advertisementDurationOption in advertisementDurations"
                  :key="advertisementDurationOption.id"
                  >{{ advertisementDurationOption.duration }}</option
                >
              </select>
              <div v-if="$v.advertisement.duration.$anyDirty && $v.advertisement.duration.$invalid">
                <small class="form-text text-danger" v-if="!$v.advertisement.duration.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
              </div>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.budget')" for="advertisement-budget">Budget</label>
              <input
                type="text"
                class="form-control"
                name="budget"
                id="advertisement-budget"
                :class="{ valid: !$v.advertisement.budget.$invalid, invalid: $v.advertisement.budget.$invalid }"
                v-model="$v.advertisement.budget.$model"
                @input="budgetInputFormatter($event)"
                maxlength="16"
              />
            </div>

            <div class="form-group">
              <label class="form-control-label" for="advertisement-conditions"
                >{{ $t('riportalApp.advertisement.conditions') }}
                <span class="text-info" v-b-tooltip.hover.v-info :title="$t('riportalApp.advertisement.conditionsInfo')"
                  ><font-awesome-icon icon="question-circle"></font-awesome-icon></span
              ></label>
              <!-- <textarea
                type="text"
                class="form-control"
                name="conditions"
                id="advertisement-conditions"
                :class="{ valid: !$v.advertisement.conditions.$invalid, invalid: $v.advertisement.conditions.$invalid }"
                v-model="$v.advertisement.conditions.$model"
              /> -->
              <vue-editor v-model="advertisement.conditions" :editor-toolbar="customToolbar" />
            </div>

          </div>

          <div class="col-xs-12 col-md-5 col-lg-4 col-xl-3 order-1 order-md-2 mb-4">
            <div class="border-line border p-3" v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.advertisement.createdAt')" for="advertisement-createdAt"
                  >Created At</label
                >
                <div class="d-flex">
                  <input
                    id="advertisement-createdAt"
                    type="datetime-local"
                    class="form-control"
                    name="createdAt"
                    :class="{ valid: !$v.advertisement.createdAt.$invalid, invalid: $v.advertisement.createdAt.$invalid }"
                    :value="convertDateTimeFromServer($v.advertisement.createdAt.$model)"
                    @change="updateInstantField('createdAt', $event)"
                    disabled="diabled"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.advertisement.modifiedAt')" for="advertisement-modifiedAt"
                  >Modified At</label
                >
                <div class="d-flex">
                  <input
                    id="advertisement-modifiedAt"
                    type="datetime-local"
                    class="form-control"
                    name="modifiedAt"
                    :class="{ valid: !$v.advertisement.modifiedAt.$invalid, invalid: $v.advertisement.modifiedAt.$invalid }"
                    :value="convertDateTimeFromServer($v.advertisement.modifiedAt.$model)"
                    @change="updateInstantField('modifiedAt', $event)"
                    disabled="disabled"
                  />
                </div>
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.advertisement.createdBy')" for="advertisement-createdBy"
                  >Created By</label
                >
                <input
                  v-if="advertisement.createdBy != null"
                  type="text"
                  class="form-control"
                  id="id"
                  name="id"
                  v-model="advertisement.createdBy.firstname"
                  readonly
                />
                <input v-else type="text" class="form-control" id="id" name="id" disabled="true" />
                <!-- <select class="form-control" id="advertisement-createdBy" name="createdBy" v-model="advertisement.createdBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="advertisement.createdBy && portalUserOption.id === advertisement.createdBy.id ? advertisement.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select> -->
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.advertisement.modifiedBy')" for="advertisement-modifiedBy"
                  >Modified By</label
                >
                <input
                  v-if="advertisement.modifiedBy != null"
                  type="text"
                  class="form-control"
                  id="id"
                  name="id"
                  v-model="advertisement.modifiedBy.firstName"
                  readonly
                />
                <input v-else type="text" class="form-control" id="id" name="id" disabled="true" />
                <!-- <select class="form-control" id="advertisement-modifiedBy" name="modifiedBy" v-model="advertisement.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="advertisement.modifiedBy && portalUserOption.id === advertisement.modifiedBy.id ? advertisement.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select> -->
              </div>
              <!-- <div class="form-group">
                <label
                  class="form-control-label"
                  v-text="$t('riportalApp.advertisement.activationDatetime')"
                  for="advertisement-activationDatetime"
                  >Activation Datetime</label
                >
                <div class="d-flex">
                  <input
                    id="advertisement-activationDatetime"
                    type="datetime-local"
                    class="form-control"
                    name="activationDatetime"
                    :class="{ valid: !$v.advertisement.activationDatetime.$invalid, invalid: $v.advertisement.activationDatetime.$invalid }"
                    :value="convertDateTimeFromServer($v.advertisement.activationDatetime.$model)"
                    @change="updateInstantField('activationDatetime', $event)"
                    readonly
                  />
                </div>
                <div v-if="$v.advertisement.activationDatetime.$anyDirty && $v.advertisement.activationDatetime.$invalid">
                  <small
                    class="form-text text-danger"
                    v-if="!$v.advertisement.activationDatetime.required"
                    v-text="$t('entity.validation.required')"
                  >
                    This field is required.
                  </small>
                  <small
                    class="form-text text-danger"
                    v-if="!$v.advertisement.activationDatetime.ZonedDateTimelocal"
                    v-text="$t('entity.validation.ZonedDateTimelocal')"
                  >
                    This field should be a date and time.
                  </small>
                </div>
              </div> -->
              
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-9 col-md-5">
            <hr />
            <company-select
              class="mt-3"
              ref="companySelect"
              :labelText="$t('riportalApp.advertisement.additionalSupporters')"
              :includedCompanies="includedCompanies"
              :excludedCompanies="excludedCompanies"
            ></company-select>
          </div>
        </div>

        <hr />
        <div class="row mt-4 justify-content-between">
          <div class="col-xs-12 col-md-6 col-lg-5">
            <div class="form-group">
              <label
                class="form-control-label position-relative font-weight-bold"
                v-text="$t('riportalApp.advertisement.upload.imgUpload')"
              ></label>
              <b-form-file
                style="margin-bottom: 5px;"
                v-model="formImages"
                class="customPlaceholder"
                @input="appendImageFiles()"
                :state="Boolean(formImages)"
                :placeholder="placeholdertext"
                multiple
                drop-placeholder="Drop file here..."
                :browse-text="browseButtonText"
                accept=".jpg, .jpeg, .png, .svg"
                :file-name-formatter="formatNames"
                :disabled="isUploadImageFilesDisabled"
              >
              </b-form-file>
              <div>
                <p
                  v-if="isUploadImageFilesDisabled"
                  class="text-danger small mb-2"
                  v-text="$t('riportalApp.advertisement.upload.imgInfo.imgLimit')"
                ></p>
                <p
                  v-else
                  class="small mb-0 text-info"
                  v-text="`${$t('riportalApp.advertisement.upload.imgInfo.imgNumber')} ${availableNumberOfImagesToAdd}.`"
                ></p>
                <p class="small mb-0 text-info" v-text="$t('riportalApp.advertisement.upload.imgInfo.imgSize')"></p>
                <!-- <p class="small mb-0 text-info">* Dozvoljene dimenzije slike su 50 x 50.</p> -->
              </div>
              <div class="p-0">
                <p class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.currentImg')">Current images:</p>
                <div class="d-flex flex-wrap">
                  <div v-for="document in advertisement.documents" v-if="document.type.type == 'image'" class="image-item mb-4 mr-2">
                    <div class="img-box">
                      <img :src="advertisementService().retrieveImage(document.filename)" />
                    </div>
                    <!-- {{ document.filename }} -->
                    <button
                      type="button"
                      class="btn btn-sm btn-danger mt-3"
                      @click="openDeleteImageModal(document.id)"
                      v-b-modal.deleteImageModal
                    >
                      <span v-text="$t('entity.action.delete')">Save</span>
                    </button>
                  </div>
                </div>
              </div>
              <ol class="p-0">
                <p v-if="imageFiles.length > 0" class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.newImg')">
                  New images:
                </p>
                <li v-for="image in imageFiles" class="ml-4 mb-3 document-content">
                  {{ image.name }}
                  <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="removeImage(image.name)">
                    <span v-text="$t('entity.action.remove')">Save</span>
                  </button>
                </li>
              </ol>
            </div>
            <!-- <b-row>
              <b-col sm="11">
                <button v-if="imageFiles.length > 0" type="button" id="save-entity" class="btn btn-primary mb-5" v-on:click="saveImages()">
                  <span v-text="$t('entity.action.saveImages')">Save</span>
                </button>
              </b-col>
            </b-row> -->
          </div>

          <hr />
          <div class="col-xs-12 col-md-6 col-lg-5">
            <div class="form-group">
              <label
                class="form-control-label position-relative font-weight-bold"
                v-text="$t('riportalApp.advertisement.upload.documentUpload')"
              ></label>
              <b-form-file
                style="margin-bottom: 5px;"
                v-model="formDocuments"
                class="customPlaceholder"
                @input="appendDocumentFiles()"
                :state="Boolean(formDocuments)"
                :placeholder="placeholdertext"
                multiple
                drop-placeholder="Drop file here..."
                :browse-text="browseButtonText"
                accept="application/pdf"
                :file-name-formatter="formatNames"
                :disabled="isUploadDocumentFilesDisabled"
              >
              </b-form-file>
              <div>
                <p
                  v-if="isUploadDocumentFilesDisabled"
                  class="text-danger small mb-2"
                  v-text="$t('riportalApp.advertisement.upload.documentInfo.documentLimit')"
                ></p>
                <p
                  v-else
                  class="small mb-0 text-info"
                  v-text="`${$t('riportalApp.advertisement.upload.documentInfo.documentNumber')} ${availableNumberOfDocumentsToAdd}.`"
                ></p>
                <p class="small mb-0 text-info" v-text="$t('riportalApp.advertisement.upload.documentInfo.documentSize')"></p>
              </div>
              <ol class="p-0">
                <p class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.currentDoc')">Current documents:</p>
                <div v-for="document in advertisement.documents">
                  <li v-if="document.type.type == 'document'" class="ml-4 mb-3">
                    <a
                      class="text-info document-content"
                      :href="advertisementService().retrieveDocument(document.filename)"
                      target="_blank"
                      title="Preuzmite dokument"
                      >{{ documentFileName(document.filename) }}
                    </a>
                    <button
                      type="button"
                      class="btn btn-sm btn-danger ml-3"
                      v-on:click="openDeleteDocumentModal(document.id)"
                      v-b-modal.deleteDocumentModal
                    >
                      <span v-text="$t('entity.action.delete')">Save</span>
                    </button>
                  </li>
                </div>
              </ol>
              <ol class="p-0">
                <p v-if="documentFiles.length > 0" class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.newDoc')">
                  New documents:
                </p>
                <li v-for="document in documentFiles" class="ml-4 mb-3 document-content">
                  {{ document.name }}
                  <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="removeDocument(document.name)">
                    <span v-text="$t('entity.action.remove')">Save</span>
                  </button>
                </li>
              </ol>
            </div>
            <!-- <b-row>
              <b-col sm="11">
                <button
                  v-if="documentFiles.length > 0"
                  type="button"
                  id="save-entity"
                  class="btn btn-primary mb-5"
                  v-on:click="saveDocuments()"
                >
                  <span v-text="$t('entity.action.saveDocuments')">Save</span>
                </button>
              </b-col>
            </b-row> -->
          </div>
          <!-- <div class="form-group">
                        <label v-text="$t('riportalApp.advertisement.document')" for="advertisement-document">Document</label>
                        <select class="form-control" id="advertisement-document" multiple name="document" v-model="advertisement.documents">
                            <option v-bind:value="getSelected(advertisement.documents, documentOption)" v-for="documentOption in documents" :key="documentOption.id">{{documentOption.id}}</option>
                        </select>
                    </div> -->
        </div>

        <hr />
        <div id="appc">
          <input v-model="inputCode" placeholder="Please Input Captcha" />
          <captcha-code @click="handleClick()" ref="code"></captcha-code>
          <!--  <button @click="handleConfirm()"  class="btn btn-primary" style="margin-top:0px;padding-top:0px">Confirm</button>-->
        </div>

        <div class="mt-4">
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.advertisement.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
          <b-spinner v-if="isLoading" small label="Loading..." type="border" class="d-inline-block ml-2"></b-spinner>
        </div>
      </form>
      <b-modal ref="deleteImageModal" id="deleteImageModal">
        <span slot="modal-title" v-text="$t('riportalApp.advertisement.upload.deleteImage.title')"></span>
        <div class="modal-body">
          <p v-text="$t('riportalApp.advertisement.upload.deleteImage.question')"></p>
        </div>
        <div slot="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeDeleteImageDialog()" v-text="$t('entity.action.cancel')">
            Odustani
          </button>
          <button type="button" class="btn btn-danger" @click="deleteImage()" v-text="$t('entity.action.delete')">Obriši</button>
        </div>
      </b-modal>
      <b-modal ref="deleteDocumentModal" id="deleteDocumentModal">
        <span slot="modal-title" v-text="$t('riportalApp.advertisement.upload.deleteDocument.title')"></span>
        <div class="modal-body">
          <p v-text="$t('riportalApp.advertisement.upload.deleteDocument.question')"></p>
        </div>
        <div slot="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeDeleteDocumentDialog()" v-text="$t('entity.action.cancel')">
            Odustani
          </button>
          <button type="button" class="btn btn-danger" @click="deleteDocument()" v-text="$t('entity.action.delete')">Obriši</button>
        </div>
      </b-modal>
    </div>
  </div>
</template>
<script lang="ts" src="./advertisement-update.component.ts"></script>

<style scoped>
.form-group {
  margin-bottom: 0.8rem;
}

.form-group label {
  margin-bottom: 0.3rem;
}

.form-control {
  height: 25px;
  padding: 0 0.55rem;
  font-size: 0.85rem;
}

.form-group textarea {
  height: 40px;
}

.customPlaceholder {
  overflow: hidden;
}

.image-item {
  width: 70px;
  margin-bottom: 10px;
  list-style-type: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.img-box {
  width: 50px;
  height: 50px;
}

.img-box img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}


</style>

