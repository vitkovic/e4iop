<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 v-if="advertisementSubCategoryTitleHasID === false" id="riportalApp.advertisementSubcategory.home.createOrEditLabel" v-text="$t('riportalApp.advertisementSubcategory.home.createTitleLabel')">Create or edit a AdvertisementSubcategory</h2>
                <h2 v-else id="riportalApp.advertisementSubcategory.home.createOrEditLabel" v-text="$t('riportalApp.advertisementSubcategory.home.editTitleLabel')">Create or edit a AdvertisementSubcategory</h2>
                <div>
                    <div class="form-group" v-if="advertisementSubcategory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="advertisementSubcategory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSubcategory.name')" for="advertisement-subcategory-name">Name</label>
                        <input type="text" class="form-control" name="name" id="advertisement-subcategory-name"
                            :class="{'valid': !$v.advertisementSubcategory.name.$invalid, 'invalid': $v.advertisementSubcategory.name.$invalid }" v-model="$v.advertisementSubcategory.name.$model"  required/>
                        <div v-if="$v.advertisementSubcategory.name.$anyDirty && $v.advertisementSubcategory.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementSubcategory.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSubcategory.nameSrc')" for="advertisement-category-nameSrc">nameSrc</label>
                        <input type="text" class="form-control" name="name" id="advertisement-category-nameSrc"
                            :class="{'valid': !$v.advertisementSubcategory.nameSrc.$invalid, 'invalid': $v.advertisementSubcategory.nameSrc.$invalid }" v-model="$v.advertisementSubcategory.nameSrc.$model"  required/>
                        <div v-if="$v.advertisementSubcategory.nameSrc.$anyDirty && $v.advertisementSubcategory.nameSrc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementSubcategory.nameSrc.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSubcategory.nameEn')" for="advertisement-category-nameEn">nameEn</label>
                        <input type="text" class="form-control" name="nameEn" id="advertisement-category-nameEn"
                            :class="{'valid': !$v.advertisementSubcategory.nameEn.$invalid, 'invalid': $v.advertisementSubcategory.nameEn.$invalid }" v-model="$v.advertisementSubcategory.nameEn.$model"  required/>
                        <div v-if="$v.advertisementSubcategory.nameEn.$anyDirty && $v.advertisementSubcategory.nameEn.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementSubcategory.nameEn.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <!-- <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSubcategory.isVisible')" for="advertisement-subcategory-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="advertisement-subcategory-isVisible"
                            :class="{'valid': !$v.advertisementSubcategory.isVisible.$invalid, 'invalid': $v.advertisementSubcategory.isVisible.$invalid }" v-model="$v.advertisementSubcategory.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSubcategory.isDeleted')" for="advertisement-subcategory-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="advertisement-subcategory-isDeleted"
                            :class="{'valid': !$v.advertisementSubcategory.isDeleted.$invalid, 'invalid': $v.advertisementSubcategory.isDeleted.$invalid }" v-model="$v.advertisementSubcategory.isDeleted.$model" />
                    </div> -->
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSubcategory.advertisementCategory')" for="advertisement-subcategory-advertisementCategory">Advertisement Category</label>
                        <select class="form-control" id="advertisement-subcategory-advertisementCategory" name="advertisementCategory" :class="{'valid': !$v.advertisementSubcategory.advertisementCategory.$invalid, 'invalid': $v.advertisementSubcategory.advertisementCategory.$invalid }" v-model="advertisementSubcategory.advertisementCategory" required>
                            <option v-bind:value="null"></option>
                            <option v-bind:value="advertisementSubcategory.advertisementCategory && advertisementCategoryOption.id === advertisementSubcategory.advertisementCategory.id ? advertisementSubcategory.advertisementCategory : advertisementCategoryOption" v-for="advertisementCategoryOption in advertisementCategories" :key="advertisementCategoryOption.id">{{advertisementCategoryOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.advertisementSubcategory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./advertisement-subcategory-update.component.ts">
</script>
