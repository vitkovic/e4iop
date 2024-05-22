<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riCategory.home.createOrEditLabel" v-text="$t('riportalApp.riCategory.home.createOrEditLabel')">Create or edit a RiCategory</h2>
                <div>
                    <div class="form-group" v-if="riCategory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riCategory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCategory.category')" for="ri-category-category">Category</label>
                        <input type="text" class="form-control" name="category" id="ri-category-category"
                            :class="{'valid': !$v.riCategory.category.$invalid, 'invalid': $v.riCategory.category.$invalid }" v-model="$v.riCategory.category.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCategory.categoryEn')" for="ri-category-categoryEn">Category En</label>
                        <input type="text" class="form-control" name="categoryEn" id="ri-category-categoryEn"
                            :class="{'valid': !$v.riCategory.categoryEn.$invalid, 'invalid': $v.riCategory.categoryEn.$invalid }" v-model="$v.riCategory.categoryEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCategory.categoryCyr')" for="ri-category-categoryCyr">Category Cyr</label>
                        <input type="text" class="form-control" name="categoryCyr" id="ri-category-categoryCyr"
                            :class="{'valid': !$v.riCategory.categoryCyr.$invalid, 'invalid': $v.riCategory.categoryCyr.$invalid }" v-model="$v.riCategory.categoryCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riCategory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-category-update.component.ts">
</script>
