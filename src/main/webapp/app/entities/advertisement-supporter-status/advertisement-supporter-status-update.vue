<template>
  <div class="row justify-content-center">
      <div class="col-8">
          <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
              <h2 id="riportalApp.advertisementSupporterStatus.home.createOrEditLabel" v-text="$t('riportalApp.advertisementSupporterStatus.home.createOrEditLabel')">Create or edit an AdvertisementSupporterStatus</h2>
              <div>
                  <div class="form-group" v-if="advertisementSupporterStatus.id">
                      <label for="id" v-text="$t('global.field.id')">ID</label>
                      <input type="text" class="form-control" id="id" name="id" v-model="advertisementSupporterStatus.id" readonly />
                  </div>
                  <div class="form-group">
                      <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporterStatus.nameSr')" for="advertisement-supporter-status-nameSr">NameSr</label>
                      <input type="text" class="form-control" name="nameSr" id="advertisement-supporter-status-nameSr" :class="{'valid': !$v.advertisementSupporterStatus.nameSr.$invalid, 'invalid': $v.advertisementSupporterStatus.nameSr.$invalid }" v-model="$v.advertisementSupporterStatus.nameSr.$model" required />
                      <div v-if="$v.advertisementSupporterStatus.nameSr.$anyDirty && $v.advertisementSupporterStatus.nameSr.$invalid">
                          <small class="form-text text-danger" v-if="!$v.advertisementSupporterStatus.nameSr.required" v-text="$t('entity.validation.required')">This field is required.</small>
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporterStatus.nameSrc')" for="advertisement-supporter-status-nameSrc">NameSrc</label>
                      <input type="text" class="form-control" name="nameSrc" id="advertisement-supporter-status-nameSrc" :class="{'valid': !$v.advertisementSupporterStatus.nameSrc.$invalid, 'invalid': $v.advertisementSupporterStatus.nameSrc.$invalid }" v-model="$v.advertisementSupporterStatus.nameSrc.$model" required />
                      <div v-if="$v.advertisementSupporterStatus.nameSrc.$anyDirty && $v.advertisementSupporterStatus.nameSrc.$invalid">
                          <small class="form-text text-danger" v-if="!$v.advertisementSupporterStatus.nameSrc.required" v-text="$t('entity.validation.required')">This field is required.</small>
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporterStatus.nameEn')" for="advertisement-supporter-status-nameEn">NameEn</label>
                      <input type="text" class="form-control" name="nameEn" id="advertisement-supporter-status-nameEn" :class="{'valid': !$v.advertisementSupporterStatus.nameEn.$invalid, 'invalid': $v.advertisementSupporterStatus.nameEn.$invalid }" v-model="$v.advertisementSupporterStatus.nameEn.$model" required />
                      <div v-if="$v.advertisementSupporterStatus.nameEn.$anyDirty && $v.advertisementSupporterStatus.nameEn.$invalid">
                          <small class="form-text text-danger" v-if="!$v.advertisementSupporterStatus.nameEn.required" v-text="$t('entity.validation.required')">This field is required.</small>
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporterStatus.isVisible')" for="advertisement-supporter-status-isVisible">Is Visible</label>
                      <input type="checkbox" class="form-check" name="isVisible" id="advertisement-supporter-status-isVisible" :class="{'valid': !$v.advertisementSupporterStatus.isVisible.$invalid, 'invalid': $v.advertisementSupporterStatus.isVisible.$invalid }" v-model="$v.advertisementSupporterStatus.isVisible.$model" />
                  </div>
                  <div class="form-group">
                      <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporterStatus.isDeleted')" for="advertisement-supporter-status-isDeleted">Is Deleted</label>
                      <input type="checkbox" class="form-check" name="isDeleted" id="advertisement-supporter-status-isDeleted" :class="{'valid': !$v.advertisementSupporterStatus.isDeleted.$invalid, 'invalid': $v.advertisementSupporterStatus.isDeleted.$invalid }" v-model="$v.advertisementSupporterStatus.isDeleted.$model" />
                  </div>
              </div>
              <div>
                  <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                      <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                  </button>
                  <button type="submit" id="save-entity" :disabled="$v.advertisementSupporterStatus.$invalid || isSaving" class="btn btn-primary">
                      <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                  </button>
              </div>
          </form>
      </div>
  </div>
</template>

<script lang="ts" src="./advertisement-supporter-status-update.component.ts"></script>
