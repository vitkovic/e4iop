<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 v-if="advertisementTitleHasID === true" id="riportalApp.advertisement.home.createOrEditLabel" v-text="$t('riportalApp.advertisement.home.createTitleLabel')">
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
          <div class="col-8">
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
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.title')" for="advertisement-title">Title</label>
              <input
                type="text"
                class="form-control"
                name="title"
                id="advertisement-title"
                :class="{ valid: !$v.advertisement.title.$invalid, invalid: $v.advertisement.title.$invalid }"
                v-model="$v.advertisement.title.$model"
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
              <textarea
                type="text"
                class="form-control"
                name="description"
                id="advertisement-description"
                :class="{ valid: !$v.advertisement.description.$invalid, invalid: $v.advertisement.description.$invalid }"
                v-model="$v.advertisement.description.$model"
                required
              />
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
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.type')" for="advertisement-type">Type</label>
              <select class="form-control" id="advertisement-type" name="type" v-model="advertisement.type">
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    advertisement.type && advertisementTypeOption.id === advertisement.type.id
                      ? advertisement.type
                      : advertisementTypeOption
                  "
                  v-for="advertisementTypeOption in advertisementTypes"
                  :key="advertisementTypeOption.id"
                  >{{ advertisementTypeOption.type }}</option
                >
              </select>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.kind')" for="advertisement-kind">Kind</label>
              <select class="form-control" id="advertisement-kind" name="kind" v-model="advertisement.kind">
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
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.subsubcategory')" for="advertisement-subsubcategory"
                >Subsubcategory</label
              >
              <select class="form-control" id="advertisement-subsubcategory" name="subsubcategory" v-model="advertisement.subsubcategory">
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    advertisement.subsubcategory && advertisementSubsubcategoryOption.id === advertisement.subsubcategory.id
                      ? advertisement.subsubcategory
                      : advertisementSubsubcategoryOption
                  "
                  v-for="advertisementSubsubcategoryOption in advertisementSubsubcategories"
                  :key="advertisementSubsubcategoryOption.id"
                  >{{ advertisementSubsubcategoryOption.name }}</option
                >
              </select>
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.duration')" for="advertisement-duration"
                >Duration</label
              >
              <select class="form-control" id="advertisement-duration" name="duration" v-model="advertisement.duration">
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
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.budget')" for="advertisement-budget">Budget</label>
              <input
                type="number"
                class="form-control"
                name="budget"
                id="advertisement-budget"
                :class="{ valid: !$v.advertisement.budget.$invalid, invalid: $v.advertisement.budget.$invalid }"
                v-model.number="$v.advertisement.budget.$model"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisement.conditions')" for="advertisement-conditions"
                >Conditions</label
              >
              <textarea
                type="text"
                class="form-control"
                name="conditions"
                id="advertisement-conditions"
                :class="{ valid: !$v.advertisement.conditions.$invalid, invalid: $v.advertisement.conditions.$invalid }"
                v-model="$v.advertisement.conditions.$model"
              />
            </div>
          </div>
          <div class="col-3">
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
                    disabled="true"
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
                    disabled="true"
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
                  v-model="advertisement.modifiedBy.firstname"
                  readonly
                />
                <input v-else type="text" class="form-control" id="id" name="id" disabled="true" />
                <!-- <select class="form-control" id="advertisement-modifiedBy" name="modifiedBy" v-model="advertisement.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="advertisement.modifiedBy && portalUserOption.id === advertisement.modifiedBy.id ? advertisement.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select> -->
              </div>
              <div class="form-group">
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
              </div>
              <div class="form-group">
                <label class="form-control-label" v-text="$t('riportalApp.advertisement.status')" for="advertisement-status">Status</label>
                <select class="form-control" id="advertisement-status" name="status" v-model="advertisement.status" disabled="true">
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
              </div>
            </div>
          </div>
        </div>
        <hr />
        <div class="row mt-4 justify-content-between">
          <div class="col-5">
            <div class="form-group">
              <label class="form-control-label position-relative" v-text="$t('riportalApp.advertisement.upload.imgUpload')"></label>
              <b-form-file
                style="margin-bottom: 5px;"
                v-model="formImages"
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
              <ol class="p-0">
                <p class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.currentImg')">Current images:</p>
                <div v-for="document in advertisement.documents">
                  <li v-if="document.type.type == 'image'" class="ml-4 mb-3 pl-2">
                    <img :src="advertisementService().retrieveImage(document.filename)" width="50" />
                    <!-- {{ document.filename }} -->
                    <button
                      type="button"
                      class="btn btn-sm btn-danger ml-3"
                      @click="openDeleteImageModal(document.id)"
                      v-b-modal.deleteImageModal
                    >
                      <span v-text="$t('entity.action.delete')">Save</span>
                    </button>
                  </li>
                </div>
              </ol>
              <ol class="p-0">
                <p v-if="imageFiles.length > 0" class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.newImg')">
                  New images:
                </p>
                <li v-for="image in imageFiles" class="ml-4 mb-3">
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
          <div class="col-5">
            <div class="form-group">
              <label class="form-control-label position-relative" v-text="$t('riportalApp.advertisement.upload.documentUpload')"></label>
              <b-form-file
                style="margin-bottom: 5px;"
                v-model="formDocuments"
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
                      class="text-info"
                      :href="advertisementService().retrieveDocument(document.filename)"
                      target="_blank"
                      title="Preuzmite dokument"
                      >{{ document.filename }}
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
                <li v-for="document in documentFiles" class="ml-4 mb-3">
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
        <div class="mt-4">
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.advertisement.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
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
</style>
