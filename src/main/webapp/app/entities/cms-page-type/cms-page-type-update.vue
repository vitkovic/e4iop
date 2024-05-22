<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsPageType.home.createOrEditLabel" v-text="$t('riportalApp.cmsPageType.home.createOrEditLabel')">Create or edit a CmsPageType</h2>
                <div>
                    <div class="form-group" v-if="cmsPageType.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsPageType.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPageType.type')" for="cms-page-type-type">Type</label>
                        <input type="text" class="form-control" name="type" id="cms-page-type-type"
                            :class="{'valid': !$v.cmsPageType.type.$invalid, 'invalid': $v.cmsPageType.type.$invalid }" v-model="$v.cmsPageType.type.$model"  required/>
                        <div v-if="$v.cmsPageType.type.$anyDirty && $v.cmsPageType.type.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsPageType.type.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPageType.isVisible')" for="cms-page-type-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="cms-page-type-isVisible"
                            :class="{'valid': !$v.cmsPageType.isVisible.$invalid, 'invalid': $v.cmsPageType.isVisible.$invalid }" v-model="$v.cmsPageType.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPageType.isDeleted')" for="cms-page-type-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="cms-page-type-isDeleted"
                            :class="{'valid': !$v.cmsPageType.isDeleted.$invalid, 'invalid': $v.cmsPageType.isDeleted.$invalid }" v-model="$v.cmsPageType.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsPageType.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cms-page-type-update.component.ts">
</script>
