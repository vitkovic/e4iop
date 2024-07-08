<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate @submit.prevent="save">
        <h2
          v-if="cmsNewsTitleHasID === false"
          id="riportalApp.cmsNews.home.createTitleLabel"
          v-text="$t('riportalApp.cmsNews.home.createTitleLabel')"
        >
          Create a CmsQuestion
        </h2>
        <h2 v-else id="riportalApp.cmsNews.home.editTitleLabel" v-text="$t('riportalApp.cmsNews.home.editTitleLabel')">
          Edit a CmsQuestion
        </h2>
        <div>
          <div class="form-group" v-if="cmsNews.id">
            <label for="id">{{ $t('global.field.id') }}</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cmsNews.id" readonly />
          </div>

          <div class="form-group">
            <label class="form-control-label" :for="'cms-news-title'">{{ $t('riportalApp.cmsNews.title') }}</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="cms-news-title"
              :class="{ valid: !$v.cmsNews.title.$invalid, invalid: $v.cmsNews.title.$invalid }"
              v-model="$v.cmsNews.title.$model"
              required
            />
            <div v-if="$v.cmsNews.title.$anyDirty && $v.cmsNews.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.cmsNews.title.required">{{ $t('entity.validation.required') }}</small>
            </div>
          </div>

          <div class="form-group">
            <label class="form-control-label" :for="'cms-news-content'">{{ $t('riportalApp.cmsNews.content') }}</label>
            <b-textarea
              type="text"
              class="form-control"
              name="content"
              id="cms-news-content"
              :class="{ valid: !$v.cmsNews.content.$invalid, invalid: $v.cmsNews.content.$invalid }"
              v-model="$v.cmsNews.content.$model"
            ></b-textarea>
          </div>

          <div class="form-group">
            <label class="form-control-label" :for="'cms-news-sequenceNumber'">{{ $t('riportalApp.cmsNews.sequenceNumber') }}</label>
            <input
              type="number"
              class="form-control"
              name="sequenceNumber"
              id="cms-news-sequenceNumber"
              :class="{ valid: !$v.cmsNews.sequenceNumber.$invalid, invalid: $v.cmsNews.sequenceNumber.$invalid }"
              v-model="$v.cmsNews.sequenceNumber.$model"
            />
          </div>

          <hr class="mt-5" />
          <div class="row mt-4 mb-4 justify-content-between">
            <div class="col-5">
              <FileUpload
                ref="imageUpload"
                :parentRef="'imageUpload'"
                :fileType="documentTypeOptions.IMAGE"
                :includedFiles="cmsNewsImages"
                @delete-file="deleteFile"
              >
              </FileUpload>
            </div>
            <div class="col-5">
              <FileUpload
                ref="documentUpload"
                :parentRef="'documentUpload'"
                :fileType="documentTypeOptions.DOCUMENT"
                :includedFiles="cmsNewsDocuments"
                @delete-file="deleteFile"
              >
              </FileUpload>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" @click="previousState">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>{{ $t('entity.action.cancel') }}</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.cmsNews.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>{{ $t('entity.action.save') }}</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts" src="./cms-news-update.component.ts"></script>
