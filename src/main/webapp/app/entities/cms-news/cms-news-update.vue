<template>
    <div class="row justify-content-center">
      <div class="col-8">
        <form name="editForm" role="form" novalidate @submit.prevent="save">
          <h2 id="riportalApp.cmsNews.home.createOrEditLabel">{{ $t('riportalApp.cmsNews.home.createOrEditLabel') }}</h2>
          <div>
            <div class="form-group" v-if="cmsNews.id">
              <label for="id">{{ $t('global.field.id') }}</label>
              <input type="text" class="form-control" id="id" name="id" v-model="cmsNews.id" readonly />
            </div>

            <div class="form-group">
              <label class="form-control-label" :for="'cms-news-title'">{{ $t('riportalApp.cmsNews.title') }}</label>
              <input type="text" class="form-control" name="title" id="cms-news-title"
                :class="{'valid': !$v.cmsNews.title.$invalid, 'invalid': $v.cmsNews.title.$invalid }" v-model="$v.cmsNews.title.$model" required />
              <div v-if="$v.cmsNews.title.$anyDirty && $v.cmsNews.title.$invalid">
                <small class="form-text text-danger" v-if="!$v.cmsNews.title.required">{{ $t('entity.validation.required') }}</small>
              </div>
            </div>

            <div class="form-group">
              <label class="form-control-label" :for="'cms-news-content'">{{ $t('riportalApp.cmsNews.content') }}</label>
              <b-textarea type="text" class="form-control" name="content" id="cms-news-content"
                :class="{'valid': !$v.cmsNews.content.$invalid, 'invalid': $v.cmsNews.content.$invalid }" v-model="$v.cmsNews.content.$model"></b-textarea>
            </div>

            <div class="form-group">
              <label class="form-control-label" :for="'cms-news-sequenceNumber'">{{ $t('riportalApp.cmsNews.sequenceNumber') }}</label>
              <input type="number" class="form-control" name="sequenceNumber" id="cms-news-sequenceNumber"
                :class="{'valid': !$v.cmsNews.sequenceNumber.$invalid, 'invalid': $v.cmsNews.sequenceNumber.$invalid }" v-model="$v.cmsNews.sequenceNumber.$model" />
            </div>

            <ImageUpload ref="imageUpload"></ImageUpload>

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
  

<script lang="ts" src="./cms-news-update.component.ts">
</script>
