<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.documentKind.home.createOrEditLabel" v-text="$t('riportalApp.documentKind.home.createOrEditLabel')">Create or edit a DocumentKind</h2>
                <div>
                    <div class="form-group" v-if="documentKind.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentKind.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.documentKind.type')" for="document-kind-type">Type</label>
                        <input type="text" class="form-control" name="type" id="document-kind-type"
                            :class="{'valid': !$v.documentKind.type.$invalid, 'invalid': $v.documentKind.type.$invalid }" v-model="$v.documentKind.type.$model"  required/>
                        <div v-if="$v.documentKind.type.$anyDirty && $v.documentKind.type.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentKind.type.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.documentKind.isVisible')" for="document-kind-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="document-kind-isVisible"
                            :class="{'valid': !$v.documentKind.isVisible.$invalid, 'invalid': $v.documentKind.isVisible.$invalid }" v-model="$v.documentKind.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.documentKind.isDeleted')" for="document-kind-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="document-kind-isDeleted"
                            :class="{'valid': !$v.documentKind.isDeleted.$invalid, 'invalid': $v.documentKind.isDeleted.$invalid }" v-model="$v.documentKind.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentKind.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./document-kind-update.component.ts">
</script>
