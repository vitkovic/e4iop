<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 v-if="advertisementCategoryTitleHasID === false" id="riportalApp.advertisementCategory.home.createOrEditLabel" v-text="$t('riportalApp.advertisementCategory.home.createTitleLabel')">Create or edit a AdvertisementCategory</h2>
                <h2 v-else id="riportalApp.advertisementCategory.home.createOrEditLabel" v-text="$t('riportalApp.advertisementCategory.home.editTitleLabel')">Create or edit a AdvertisementCategory</h2>
                <div>
                    <div class="form-group" v-if="advertisementCategory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="advertisementCategory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementCategory.name')" for="advertisement-category-name">Name</label>
                        <input type="text" class="form-control" name="name" id="advertisement-category-name"
                            :class="{'valid': !$v.advertisementCategory.name.$invalid, 'invalid': $v.advertisementCategory.name.$invalid }" v-model="$v.advertisementCategory.name.$model"  required/>
                        <div v-if="$v.advertisementCategory.name.$anyDirty && $v.advertisementCategory.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementCategory.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementCategory.nameSrc')" for="advertisement-category-nameSrc">nameSrc</label>
                        <input type="text" class="form-control" name="name" id="advertisement-category-nameSrc"
                            :class="{'valid': !$v.advertisementCategory.nameSrc.$invalid, 'invalid': $v.advertisementCategory.nameSrc.$invalid }" v-model="$v.advertisementCategory.nameSrc.$model"  required/>
                        <div v-if="$v.advertisementCategory.nameSrc.$anyDirty && $v.advertisementCategory.nameSrc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementCategory.nameSrc.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementCategory.nameEn')" for="advertisement-category-nameEn">nameEn</label>
                        <input type="text" class="form-control" name="nameEn" id="advertisement-category-nameEn"
                            :class="{'valid': !$v.advertisementCategory.nameEn.$invalid, 'invalid': $v.advertisementCategory.nameEn.$invalid }" v-model="$v.advertisementCategory.nameEn.$model"  required/>
                        <div v-if="$v.advertisementCategory.nameEn.$anyDirty && $v.advertisementCategory.nameEn.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementCategory.nameEn.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <!-- <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementCategory.isVisible')" for="advertisement-category-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="advertisement-category-isVisible"
                            :class="{'valid': !$v.advertisementCategory.isVisible.$invalid, 'invalid': $v.advertisementCategory.isVisible.$invalid }" v-model="$v.advertisementCategory.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementCategory.isDeleted')" for="advertisement-category-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="advertisement-category-isDeleted"
                            :class="{'valid': !$v.advertisementCategory.isDeleted.$invalid, 'invalid': $v.advertisementCategory.isDeleted.$invalid }" v-model="$v.advertisementCategory.isDeleted.$model" />
                    </div> -->
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.advertisementCategory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./advertisement-category-update.component.ts">
</script>
