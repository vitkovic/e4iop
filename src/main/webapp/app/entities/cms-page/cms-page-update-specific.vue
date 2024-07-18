h<template>
  <div class="row justify-content-center">
    <div v-if="cmsPage.id" class="col10 col-md-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="riportalApp.cmsPage.home.createOrEditLabel">
          <span v-text="$t('riportalApp.cmsPage.titleChange')">Create or edit a CmsPage</span>
          <span v-text="textHeading">Stranica</span>
        </h2>
        <div>
          <!-- <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.type')" for="cms-page-type">Type</label>
                        <select class="form-control" id="cms-page-type" name="type" v-model="cmsPage.type" required>
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsPage.type && cmsPageTypeOption.id === cmsPage.type.id ? cmsPage.type : cmsPageTypeOption" v-for="cmsPageTypeOption in cmsPageTypes" :key="cmsPageTypeOption.id">{{cmsPageTypeOption.type}}</option>
                        </select>
                    </div> -->

          <div class="form-group">
            <label class="form-control-label" v-text="$t('riportalApp.cmsPage.title')" for="cms-page-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="cms-page-title"
              :class="{ valid: !$v.cmsPage.title.$invalid, invalid: $v.cmsPage.title.$invalid }"
              v-model="$v.cmsPage.title.$model"
              required
            />
            <div v-if="$v.cmsPage.title.$anyDirty && $v.cmsPage.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.cmsPage.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('riportalApp.cmsPage.content')" for="cms-page-content">Content</label>
            <!-- <b-textarea type="text" class="form-control" name="content" id="cms-page-content" rows="10"
                            :class="{'valid': !$v.cmsPage.content.$invalid, 'invalid': $v.cmsPage.content.$invalid }" v-model="$v.cmsPage.content.$model" required>
                        </b-textarea> -->
            <vue-editor
              v-model="$v.cmsPage.content.$model"
              :editor-toolbar="customToolbar"
            />
          </div>
        </div>

        <hr class="mt-5" />
        <div class="row mt-4 mb-4 justify-content-between">
          <div class="col-xs-12 col-md-6 col-lg-5">
            <FileUpload
              ref="imageUpload"
              :parentRef="'imageUpload'"
              :fileType="documentTypeOptions.IMAGE"
              :includedFiles="cmsPageImages"
              @delete-file="deleteFile"
            >
            </FileUpload>
          </div>
          <div class="col-xs-12 col-md-6 col-lg-5">
            <FileUpload
              ref="documentUpload"
              :parentRef="'documentUpload'"
              :fileType="documentTypeOptions.DOCUMENT"
              :includedFiles="cmsPageDocuments"
              @delete-file="deleteFile"
            >
            </FileUpload>
          </div>
        </div>

        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.cmsPage.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>

    <div v-else class="col-8">
      <h2 v-text="'Nepostojeća stranica!'"></h2>
    </div>
  </div>
</template>
<script lang="ts" src="./cms-page-update-specific.component.ts"></script>
