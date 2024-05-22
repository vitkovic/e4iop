<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsAdvertisementCategory.home.createOrEditLabel" v-text="$t('riportalApp.cmsAdvertisementCategory.home.createOrEditLabel')">Create or edit a CmsAdvertisementCategory</h2>
                <div>
                    <div class="form-group" v-if="cmsAdvertisementCategory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsAdvertisementCategory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsAdvertisementCategory.createdAt')" for="cms-advertisement-category-createdAt">Created At</label>
                        <div class="d-flex">
                            <input id="cms-advertisement-category-createdAt" type="datetime-local" class="form-control" name="createdAt" :class="{'valid': !$v.cmsAdvertisementCategory.createdAt.$invalid, 'invalid': $v.cmsAdvertisementCategory.createdAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsAdvertisementCategory.createdAt.$model)"
                            @change="updateInstantField('createdAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsAdvertisementCategory.modifiedAt')" for="cms-advertisement-category-modifiedAt">Modified At</label>
                        <div class="d-flex">
                            <input id="cms-advertisement-category-modifiedAt" type="datetime-local" class="form-control" name="modifiedAt" :class="{'valid': !$v.cmsAdvertisementCategory.modifiedAt.$invalid, 'invalid': $v.cmsAdvertisementCategory.modifiedAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsAdvertisementCategory.modifiedAt.$model)"
                            @change="updateInstantField('modifiedAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsAdvertisementCategory.createdBy')" for="cms-advertisement-category-createdBy">Created By</label>
                        <select class="form-control" id="cms-advertisement-category-createdBy" name="createdBy" v-model="cmsAdvertisementCategory.createdBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsAdvertisementCategory.createdBy && portalUserOption.id === cmsAdvertisementCategory.createdBy.id ? cmsAdvertisementCategory.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsAdvertisementCategory.modifiedBy')" for="cms-advertisement-category-modifiedBy">Modified By</label>
                        <select class="form-control" id="cms-advertisement-category-modifiedBy" name="modifiedBy" v-model="cmsAdvertisementCategory.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsAdvertisementCategory.modifiedBy && portalUserOption.id === cmsAdvertisementCategory.modifiedBy.id ? cmsAdvertisementCategory.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsAdvertisementCategory.advertisementCategory')" for="cms-advertisement-category-advertisementCategory">Advertisement Category</label>
                        <select class="form-control" id="cms-advertisement-category-advertisementCategory" name="advertisementCategory" v-model="cmsAdvertisementCategory.advertisementCategory">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsAdvertisementCategory.advertisementCategory && advertisementCategoryOption.id === cmsAdvertisementCategory.advertisementCategory.id ? cmsAdvertisementCategory.advertisementCategory : advertisementCategoryOption" v-for="advertisementCategoryOption in advertisementCategories" :key="advertisementCategoryOption.id">{{advertisementCategoryOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsAdvertisementCategory.image')" for="cms-advertisement-category-image">Image</label>
                        <select class="form-control" id="cms-advertisement-category-image" name="image" v-model="cmsAdvertisementCategory.image">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsAdvertisementCategory.image && documentOption.id === cmsAdvertisementCategory.image.id ? cmsAdvertisementCategory.image : documentOption" v-for="documentOption in documents" :key="documentOption.id">{{documentOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsAdvertisementCategory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cms-advertisement-category-update.component.ts">
</script>
