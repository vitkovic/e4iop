<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          v-if="advertisementSubSubCategoryTitleHasID === false"
          id="riportalApp.advertisementSubsubcategory.home.createOrEditLabel"
          v-text="$t('riportalApp.advertisementSubsubcategory.home.createTitleLabel')"
        >
          Create or edit a AdvertisementSubsubcategory
        </h2>
        <h2
          v-else
          id="riportalApp.advertisementSubsubcategory.home.createOrEditLabel"
          v-text="$t('riportalApp.advertisementSubsubcategory.home.editTitleLabel')"
        >
          Create or edit a AdvertisementSubsubcategory
        </h2>
        <div>
          <div class="form-group" v-if="advertisementSubsubcategory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="advertisementSubsubcategory.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('riportalApp.advertisementSubsubcategory.name')"
              for="advertisement-subsubcategory-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="advertisement-subsubcategory-name"
              :class="{ valid: !$v.advertisementSubsubcategory.name.$invalid, invalid: $v.advertisementSubsubcategory.name.$invalid }"
              v-model="$v.advertisementSubsubcategory.name.$model"
              required
            />
            <div v-if="$v.advertisementSubsubcategory.name.$anyDirty && $v.advertisementSubsubcategory.name.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.advertisementSubsubcategory.name.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisementSubsubcategory.nameSrc')" for="advertisement-category-nameSrc">nameSrc</label>
              <input type="text" class="form-control" name="name" id="advertisement-category-nameSrc"
                  :class="{'valid': !$v.advertisementSubsubcategory.nameSrc.$invalid, 'invalid': $v.advertisementSubsubcategory.nameSrc.$invalid }" v-model="$v.advertisementSubsubcategory.nameSrc.$model"  required/>
              <div v-if="$v.advertisementSubsubcategory.nameSrc.$anyDirty && $v.advertisementSubsubcategory.nameSrc.$invalid">
                  <small class="form-text text-danger" v-if="!$v.advertisementSubsubcategory.nameSrc.required" v-text="$t('entity.validation.required')">
                      This field is required.
                  </small>
              </div>
          </div>
          <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.advertisementSubsubcategory.nameEn')" for="advertisement-category-nameEn">nameEn</label>
              <input type="text" class="form-control" name="nameEn" id="advertisement-category-nameEn"
                  :class="{'valid': !$v.advertisementSubsubcategory.nameEn.$invalid, 'invalid': $v.advertisementSubsubcategory.nameEn.$invalid }" v-model="$v.advertisementSubsubcategory.nameEn.$model"  required/>
              <div v-if="$v.advertisementSubsubcategory.nameEn.$anyDirty && $v.advertisementSubsubcategory.nameEn.$invalid">
                  <small class="form-text text-danger" v-if="!$v.advertisementSubsubcategory.nameEn.required" v-text="$t('entity.validation.required')">
                      This field is required.
                  </small>
              </div>
          </div>
          <!-- <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('riportalApp.advertisementSubsubcategory.isVisible')"
              for="advertisement-subsubcategory-isVisible"
              >Is Visible</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isVisible"
              id="advertisement-subsubcategory-isVisible"
              :class="{
                valid: !$v.advertisementSubsubcategory.isVisible.$invalid,
                invalid: $v.advertisementSubsubcategory.isVisible.$invalid,
              }"
              v-model="$v.advertisementSubsubcategory.isVisible.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('riportalApp.advertisementSubsubcategory.isDeleted')"
              for="advertisement-subsubcategory-isDeleted"
              >Is Deleted</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isDeleted"
              id="advertisement-subsubcategory-isDeleted"
              :class="{
                valid: !$v.advertisementSubsubcategory.isDeleted.$invalid,
                invalid: $v.advertisementSubsubcategory.isDeleted.$invalid,
              }"
              v-model="$v.advertisementSubsubcategory.isDeleted.$model"
            />
          </div> -->
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('riportalApp.advertisementSubsubcategory.advertisementSubcategory')"
              for="advertisement-subsubcategory-advertisementSubcategory"
              >Advertisement Subcategory</label
            >
            <select
              class="form-control"
              id="advertisement-subsubcategory-advertisementSubcategory"
              name="advertisementSubcategory"
              :class="{
                valid: !$v.advertisementSubsubcategory.advertisementSubcategory.$invalid,
                invalid: $v.advertisementSubsubcategory.advertisementSubcategory.$invalid,
              }"
              v-model="advertisementSubsubcategory.advertisementSubcategory"
              required
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  advertisementSubsubcategory.advertisementSubcategory &&
                  advertisementSubcategoryOption.id === advertisementSubsubcategory.advertisementSubcategory.id
                    ? advertisementSubsubcategory.advertisementSubcategory
                    : advertisementSubcategoryOption
                "
                v-for="advertisementSubcategoryOption in advertisementSubcategories"
                :key="advertisementSubcategoryOption.id"
                >{{ advertisementSubcategoryOption.name }}</option
              >
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.advertisementSubsubcategory.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./advertisement-subsubcategory-update.component.ts"></script>
