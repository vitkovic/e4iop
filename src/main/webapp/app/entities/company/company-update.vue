<template>
  <div class="row justify-content-center">
    <div class="col-10 col-md-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          v-if="companyTitleHasID === true"
          id="riportalApp.company.home.createOrEditLabel"
          v-text="$t('riportalApp.company.home.createTitleLabel')"
        >
          Create a Company
        </h2>
        <h2 v-else id="riportalApp.company.home.createOrEditLabel" v-text="$t('riportalApp.company.home.editTitleLabel')">
          Edit a Company
        </h2>
        <div class="row justify-content-between">
          <div class="col-xs-12 col-md-7 col-lg-8 order-2 order-md-1">
            <!-- <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.company.rsnisId')" for="company-rsnisId">Rsnis Id</label>
              <input
                type="number"
                class="form-control"
                name="rsnisId"
                id="company-rsnisId"
                :class="{ valid: !$v.company.rsnisId.$invalid, invalid: $v.company.rsnisId.$invalid }"
                v-model.number="$v.company.rsnisId.$model"
                required
              />
              <div v-if="$v.company.rsnisId.$anyDirty && $v.company.rsnisId.$invalid">
                <small class="form-text text-danger" v-if="!$v.company.rsnisId.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
                <small class="form-text text-danger" v-if="!$v.company.rsnisId.numeric" v-text="$t('entity.validation.number')">
                  This field should be a number.
                </small>
              </div>
            </div> -->
            <!-- <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.company.aprId')" for="company-aprId">Apr Id</label>
              <input
                type="number"
                class="form-control"
                name="aprId"
                id="company-aprId"
                :class="{ valid: !$v.company.aprId.$invalid, invalid: $v.company.aprId.$invalid }"
                v-model.number="$v.company.aprId.$model"
                required
              />
              <div v-if="$v.company.aprId.$anyDirty && $v.company.aprId.$invalid">
                <small class="form-text text-danger" v-if="!$v.company.aprId.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
                <small class="form-text text-danger" v-if="!$v.company.aprId.numeric" v-text="$t('entity.validation.number')">
                  This field should be a number.
                </small>
              </div>
            </div> -->
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.company.name')" for="company-name">Name</label>
              <input
                type="text"
                class="form-control"
                name="name"
                id="company-name"
                :class="{ valid: !$v.company.name.$invalid, invalid: $v.company.name.$invalid }"
                v-model="$v.company.name.$model"
                required
              />
              <div v-if="$v.company.name.$anyDirty && $v.company.name.$invalid">
                <small class="form-text text-danger" v-if="!$v.company.name.required" v-text="$t('entity.validation.required')">
                  This field is required.
                </small>
              </div>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.company.about')" for="company-about">About</label>
              <!-- <textarea
                type="text"
                class="form-control"
                name="about"
                id="company-about"
                :class="{ valid: !$v.company.about.$invalid, invalid: $v.company.about.$invalid }"
                v-model="$v.company.about.$model"
              /> -->
              <vue-editor
                v-model="$v.company.about.$model"
                :editor-toolbar="customToolbar"
                :class="{ valid: !$v.company.about.$invalid, invalid: $v.company.about.$invalid }"
              />
            </div>
            <!-- <div class="form-group mb-4">
              <label class="form-control-label" v-text="$t('riportalApp.company.fields')" for="company-fields">Fields</label>
              <input
                type="text"
                class="form-control"
                name="fields"
                id="company-fields"
                :class="{ valid: !$v.company.fields.$invalid, invalid: $v.company.fields.$invalid }"
                v-model="$v.company.fields.$model"
              />
            </div> -->
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.company.isOnEinovations')" for="company-isEinovator"
                >Is registered on portal eInovations</label
              >
              <input
                type="checkbox"
                class="form-check"
                name="isInRSNIS"
                id="company-isEinovator"
                :class="{ valid: !$v.company.isEinovator.$invalid, invalid: $v.company.isEinovator.$invalid }"
                v-model="$v.company.isEinovator.$model"
              />
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.company.website')" for="company-website">website</label>
              <input
                type="text"
                class="form-control"
                name="name"
                id="company-website"
                :class="{ valid: !$v.company.website.$invalid, invalid: $v.company.website.$invalid }"
                v-model="$v.company.website.$model"
              />
              <div v-if="$v.company.website.$anyDirty && $v.company.website.$invalid">
                <small class="form-text text-danger" v-if="!$v.company.website.required" v-text="$t('entity.validation.url')">
                  This field is required.
                </small>
              </div>
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.company.email')" for="company-email">email</label>
              <input
                type="text"
                class="form-control"
                name="name"
                id="company-email"
                :class="{ valid: !$v.company.email.$invalid, invalid: $v.company.email.$invalid }"
                v-model="$v.company.email.$model"
              />
              <div v-if="$v.company.email.$anyDirty && $v.company.email.$invalid">
                <small class="form-text text-danger" v-if="!$v.company.email.required" v-text="$t('entity.validation.email')">
                  This field is required.
                </small>
              </div>
            </div>

            <hr />

            <div class="documents-section" v-show="showDocumentsSection">
              <div class="form-group row">
                <div class="col-xs-12 col-sm-6">
                  <label
                    class="form-control-label position-relative font-weight-bold"
                    v-text="$t('riportalApp.company.upload.logoUpload')"
                  ></label>
                  <b-form-file
                    style="margin-bottom: 5px;"
                    @change="handleFileChange"
                    class="customPlaceholder"
                    :state="Boolean(imageLogo)"
                    :placeholder="placeholdertext"
                    drop-placeholder="Drop file here..."
                    :browse-text="browseButtonText"
                    accept=".jpg, .jpeg, .png, .svg"
                    :file-name-formatter="formatNames"
                  >
                  </b-form-file>
                  <div>
                    <p class="small mb-0 text-info" v-text="$t('riportalApp.company.upload.imgInfo.imgSize')"></p>
                  </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                  <div v-if="company.logo">
                    <p class="logo-img-label font-weight-bold" v-text="$t('riportalApp.company.upload.currentLogo')">Current logo:</p>
                    <div class="mb-3 pl-1">
                      <img :src="companyService().retrieveImage(company.logo.filename)" width="50" height="50" />
                      <!-- {{ company.logo.filename }} -->
                      <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="openDeleteLogoModal()" v-b-modal.deleteLogoModal>
                        <span v-text="$t('entity.action.delete')">Save</span>
                      </button>
                    </div>
                  </div>
                  <div v-else>There is no logo found</div>
                </div>
              </div>
              <div v-if="imageLogo != null" class="mb-4">
                <p class="font-weight-bold" v-text="$t('riportalApp.company.upload.newLogo')">New logo:</p>
                {{ imageLogo.name }}
                <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="removeLogo()">
                  <span v-text="$t('entity.action.remove')">Save</span>
                </button>
              </div>
              <b-row>
                <b-col sm="11">
                  <button
                    v-if="imageLogo != null"
                    type="button"
                    id="save-entity"
                    class="btn btn-primary mb-5"
                    v-on:click="saveCompanyLogo()"
                  >
                    <span v-text="$t('entity.action.saveLogo')">Save</span>
                  </button>
                </b-col>
              </b-row>
            </div>
          </div>
          <div class="col-xs-12 col-md-5 col-lg-4 col-xl-3 order-1 order-md-2 mb-4">
            <div class="border-line border p-3">
              <div class="form-group" v-if="company.id && authenticated && hasAnyAuthority('ROLE_ADMIN')">
                <label for="id" v-text="$t('global.field.id')">ID</label>
                <input type="text" class="form-control" id="id" name="id" v-model="company.id" readonly />
              </div>
              <div class="form-group" v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
                <label class="form-control-label" v-text="$t('riportalApp.company.createdAt')" for="company-createdAt">Created At</label>
                <div class="d-flex">
                  <input
                    id="company-createdAt"
                    type="datetime-local"
                    class="form-control"
                    name="createdAt"
                    :class="{ valid: !$v.company.createdAt.$invalid, invalid: $v.company.createdAt.$invalid }"
                    :value="convertDateTimeFromServer($v.company.createdAt.$model)"
                    @change="updateInstantField('createdAt', $event)"
                    disabled="true"
                  />
                </div>
              </div>
              <div class="form-group" v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
                <label class="form-control-label" v-text="$t('riportalApp.company.createdBy')" for="company-createdBy">Created By</label>
                <input
                  v-if="company.createdBy != null"
                  type="text"
                  class="form-control"
                  id="id"
                  name="id"
                  v-model="company.createdBy.user.firstName"
                  readonly
                />
                <input v-else type="text" class="form-control" id="id" name="id" readonly />
                <!-- <select class="form-control" id="company-createdBy" name="createdBy" v-model="company.createdBy" disabled="true">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="company.createdBy && portalUserOption.id === company.createdBy.id ? company.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select> -->
              </div>
              <div class="form-group" v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
                <label class="form-control-label" v-text="$t('riportalApp.company.modifiedAt')" for="company-modifiedAt">Modified At</label>
                <div class="d-flex">
                  <input
                    id="company-modifiedAt"
                    type="datetime-local"
                    class="form-control"
                    name="modifiedAt"
                    :class="{ valid: !$v.company.modifiedAt.$invalid, invalid: $v.company.modifiedAt.$invalid }"
                    :value="convertDateTimeFromServer($v.company.modifiedAt.$model)"
                    @change="updateInstantField('modifiedAt', $event)"
                    disabled="true"
                  />
                </div>
              </div>
              <div class="form-group" v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
                <label class="form-control-label" v-text="$t('riportalApp.company.modifiedBy')" for="company-modifiedBy">Modified By</label>
                <input
                  v-if="company.modifiedBy != null"
                  type="text"
                  class="form-control"
                  id="id"
                  name="id"
                  v-model="company.modifiedBy.user.firstName"
                  readonly
                />
                <input v-else type="text" class="form-control" id="id" name="id" readonly />
                <!-- <select class="form-control" id="company-modifiedBy" name="modifiedBy" v-model="company.modifiedBy" disabled="true">
                            <option v-bind:value="company.modifiedBy"></option>
                            <option v-bind:value="company.modifiedBy && portalUserOption.id === company.modifiedBy.id ? company.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.firstname}}</option>
                        </select> -->
              </div>
            </div>
          </div>
        </div>
        <hr />

        <div class="row mt-4 mb-4 justify-content-between">
          <div class="col-xs-12 col-md-6 col-lg-5">
            <FileUpload
              ref="imageUpload"
              :parentRef="'imageUpload'"
              :fileType="documentTypeOptions.IMAGE"
              :includedFiles="companyImages"
              @delete-file="deleteFile"
            >
            </FileUpload>
          </div>
          <div class="col-xs-12 col-md-6 col-lg-5">
            <FileUpload
              ref="documentUpload"
              :parentRef="'documentUpload'"
              :fileType="documentTypeOptions.DOCUMENT"
              :includedFiles="companyDocuments"
              @delete-file="deleteFile"
            >
            </FileUpload>
          </div>
        </div>

        <hr />
        <div class="mt-4">
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.company.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
      <b-modal ref="deleteLogoModal" id="deleteLogoModal">
        <span slot="modal-title" v-text="$t('riportalApp.company.upload.deleteLogo.title')"></span>
        <div class="modal-body">
          <p v-text="$t('riportalApp.company.upload.deleteLogo.question')"></p>
        </div>
        <div slot="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeDeleteLogoDialog()" v-text="$t('entity.action.cancel')">
            Odustani
          </button>
          <button type="button" class="btn btn-danger" @click="deleteLogo()" v-text="$t('entity.action.delete')">Obriši</button>
        </div>
      </b-modal>
    </div>
  </div>
</template>
<script lang="ts" src="./company-update.component.ts"></script>

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
  height: 50px;
}

.logo-img-label {
  margin-bottom: 0.3rem;
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

.document-content {
  font-size: 0.8rem;
}
</style>
