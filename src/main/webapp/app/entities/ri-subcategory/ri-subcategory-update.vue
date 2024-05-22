<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riSubcategory.home.createOrEditLabel" v-text="$t('riportalApp.riSubcategory.home.createOrEditLabel')">Create or edit a RiSubcategory</h2>
                <div>
                    <div class="form-group" v-if="riSubcategory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riSubcategory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riSubcategory.subcategory')" for="ri-subcategory-subcategory">Subcategory</label>
                        <input type="text" class="form-control" name="subcategory" id="ri-subcategory-subcategory"
                            :class="{'valid': !$v.riSubcategory.subcategory.$invalid, 'invalid': $v.riSubcategory.subcategory.$invalid }" v-model="$v.riSubcategory.subcategory.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riSubcategory.subcategoryEn')" for="ri-subcategory-subcategoryEn">Subcategory En</label>
                        <input type="text" class="form-control" name="subcategoryEn" id="ri-subcategory-subcategoryEn"
                            :class="{'valid': !$v.riSubcategory.subcategoryEn.$invalid, 'invalid': $v.riSubcategory.subcategoryEn.$invalid }" v-model="$v.riSubcategory.subcategoryEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riSubcategory.subCategoryCyr')" for="ri-subcategory-subCategoryCyr">Sub Category Cyr</label>
                        <input type="text" class="form-control" name="subCategoryCyr" id="ri-subcategory-subCategoryCyr"
                            :class="{'valid': !$v.riSubcategory.subCategoryCyr.$invalid, 'invalid': $v.riSubcategory.subCategoryCyr.$invalid }" v-model="$v.riSubcategory.subCategoryCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riSubcategory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-subcategory-update.component.ts">
</script>
