<template>
  <div class="row justify-content-center">
    <div class="col-10 col-md-8">
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
            <!-- <b-textarea
              type="text"
              class="form-control"
              name="content"
              id="cms-news-content"
              :class="{ valid: !$v.cmsNews.content.$invalid, invalid: $v.cmsNews.content.$invalid }"
              v-model="$v.cmsNews.content.$model"
            ></b-textarea> -->
            <vue-editor
              v-model="$v.cmsNews.content.$model"
              :editor-toolbar="customToolbar"
              :class="{ valid: !$v.cmsNews.content.$invalid, invalid: $v.cmsNews.content.$invalid }"
            />
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

          <br />
          <div class="form-group row">
            <div class="col-xs-12 col-sm-6">
              <label class="form-control-label position-relative font-weight-bold" v-text="$t('riportalApp.cmsNews.titleImage')"></label>
              <b-form-file
                style="margin-bottom: 5px;"
                @change="handleFileChange"
                class="customPlaceholder"
                :state="Boolean(titleImage)"
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
              <div v-if="cmsNews.titleImage">
                <p class="logo-img-label font-weight-bold" v-text="$t('riportalApp.cmsNews.upload.currentTitleImage')">Current title i:</p>
                <div class="mb-3 pl-1">
                  <img :src="retrieveImage(cmsNews.titleImage.filename)" width="50" height="50" />
                  <button
                    type="button"
                    class="btn btn-sm btn-danger ml-3"
                    v-on:click="openDeleteTitleImageModal()"
                    v-b-modal.deleteTitleImageModal
                  >
                    <span v-text="$t('entity.action.delete')">Save</span>
                  </button>
                </div>
              </div>
              <div v-else v-text="$t('riportalApp.cmsNews.upload.noTitleImage')">There is no title image found</div>
            </div>
          </div>
          <div v-if="titleImage != null" class="mb-4">
            <p class="font-weight-bold" v-text="$t('riportalApp.cmsNews.upload.newTitleImage')">New logo:</p>
            {{ titleImage.name }}
            <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="removeLogo()">
              <span v-text="$t('entity.action.remove')">Save</span>
            </button>
          </div>

          <hr class="mt-5" />
          <div class="row mt-4 mb-4 justify-content-between">
            <div class="col-xs-12 col-md-6 col-lg-5">
              <FileUpload
                ref="imageUpload"
                :parentRef="'imageUpload'"
                :fileType="documentTypeOptions.IMAGE"
                :includedFiles="cmsNewsImages"
                @delete-file="deleteFile"
              >
              </FileUpload>
            </div>
            <div class="col-xs-12 col-md-6 col-lg-5">
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
      <b-modal ref="deleteTitleImageModal" id="deleteTitleImageModal">
        <span slot="modal-title" v-text="$t('riportalApp.company.upload.deleteLogo.title')"></span>
        <div class="modal-body">
          <p v-text="$t('riportalApp.company.upload.deleteLogo.question')"></p>
        </div>
        <div slot="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeDeleteTitleImageModal()" v-text="$t('entity.action.cancel')">
            Odustani
          </button>
          <button type="button" class="btn btn-danger" @click="deleteTitleImage()" v-text="$t('entity.action.delete')">Obriši</button>
        </div>
      </b-modal>
    </div>
  </div>
</template>

<script lang="ts" src="./cms-news-update.component.ts"></script>

<style scoped>
h2 {
  color: #004b90;
}
</style>
