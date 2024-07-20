<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          v-if="advertisementTypeTitleHasID === false"
          id="riportalApp.advertisementType.home.createOrEditLabel"
          v-text="$t('riportalApp.advertisementType.home.createTitleLabel')"
        >
          Create or edit a AdvertisementType
        </h2>
        <h2
          v-else
          id="riportalApp.advertisementType.home.createOrEditLabel"
          v-text="$t('riportalApp.advertisementType.home.editTitleLabel')"
        >
          Create or edit a AdvertisementType
        </h2>
        <div>
          <div class="form-group" v-if="advertisementType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="advertisementType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('riportalApp.advertisementType.type')" for="advertisement-type-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="advertisement-type-type"
              :class="{ valid: !$v.advertisementType.type.$invalid, invalid: $v.advertisementType.type.$invalid }"
              v-model="$v.advertisementType.type.$model"
              required
            />
            <div v-if="$v.advertisementType.type.$anyDirty && $v.advertisementType.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.advertisementType.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('riportalApp.advertisementType.typeSrc')" for="advertisement-type-typeSrc">typeSrc</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="advertisement-type-typeSrc"
              :class="{ valid: !$v.advertisementType.typeSrc.$invalid, invalid: $v.advertisementType.typeSrc.$invalid }"
              v-model="$v.advertisementType.typeSrc.$model"
              required
            />
            <div v-if="$v.advertisementType.typeSrc.$anyDirty && $v.advertisementType.typeSrc.$invalid">
              <small class="form-text text-danger" v-if="!$v.advertisementType.typeSrc.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('riportalApp.advertisementType.typeEn')" for="advertisement-type-typeEn">typeEn</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="advertisement-type-typeEn"
              :class="{ valid: !$v.advertisementType.typeEn.$invalid, invalid: $v.advertisementType.typeEn.$invalid }"
              v-model="$v.advertisementType.typeEn.$model"
              required
            />
            <div v-if="$v.advertisementType.typeEn.$anyDirty && $v.advertisementType.typeEn.$invalid">
              <small class="form-text text-danger" v-if="!$v.advertisementType.typeEn.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('riportalApp.advertisementType.isVisible')" for="advertisement-type-isVisible"
              >Is Visible</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isVisible"
              id="advertisement-type-isVisible"
              :class="{ valid: !$v.advertisementType.isVisible.$invalid, invalid: $v.advertisementType.isVisible.$invalid }"
              v-model="$v.advertisementType.isVisible.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('riportalApp.advertisementType.isDeleted')" for="advertisement-type-isDeleted"
              >Is Deleted</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isDeleted"
              id="advertisement-type-isDeleted"
              :class="{ valid: !$v.advertisementType.isDeleted.$invalid, invalid: $v.advertisementType.isDeleted.$invalid }"
              v-model="$v.advertisementType.isDeleted.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.advertisementType.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./advertisement-type-update.component.ts"></script>
