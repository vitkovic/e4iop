<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.documentType.home.createOrEditLabel" v-text="$t('riportalApp.documentType.home.createOrEditLabel')">Create or edit a DocumentType</h2>
                <div>
                    <div class="form-group" v-if="documentType.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentType.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.documentType.type')" for="document-type-type">Type</label>
                        <input type="text" class="form-control" name="type" id="document-type-type"
                            :class="{'valid': !$v.documentType.type.$invalid, 'invalid': $v.documentType.type.$invalid }" v-model="$v.documentType.type.$model"  required/>
                        <div v-if="$v.documentType.type.$anyDirty && $v.documentType.type.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentType.type.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.documentType.isVisible')" for="document-type-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="document-type-isVisible"
                            :class="{'valid': !$v.documentType.isVisible.$invalid, 'invalid': $v.documentType.isVisible.$invalid }" v-model="$v.documentType.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.documentType.isDeleted')" for="document-type-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="document-type-isDeleted"
                            :class="{'valid': !$v.documentType.isDeleted.$invalid, 'invalid': $v.documentType.isDeleted.$invalid }" v-model="$v.documentType.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentType.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./document-type-update.component.ts">
</script>
