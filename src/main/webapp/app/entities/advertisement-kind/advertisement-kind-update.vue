<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.advertisementKind.home.createOrEditLabel" v-text="$t('riportalApp.advertisementKind.home.createOrEditLabel')">Create or edit a AdvertisementKind</h2>
                <div>
                    <div class="form-group" v-if="advertisementKind.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="advertisementKind.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementKind.kind')" for="advertisement-kind-kind">Kind</label>
                        <input type="text" class="form-control" name="kind" id="advertisement-kind-kind"
                            :class="{'valid': !$v.advertisementKind.kind.$invalid, 'invalid': $v.advertisementKind.kind.$invalid }" v-model="$v.advertisementKind.kind.$model"  required/>
                        <div v-if="$v.advertisementKind.kind.$anyDirty && $v.advertisementKind.kind.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementKind.kind.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementKind.isVisible')" for="advertisement-kind-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="advertisement-kind-isVisible"
                            :class="{'valid': !$v.advertisementKind.isVisible.$invalid, 'invalid': $v.advertisementKind.isVisible.$invalid }" v-model="$v.advertisementKind.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementKind.isDeleted')" for="advertisement-kind-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="advertisement-kind-isDeleted"
                            :class="{'valid': !$v.advertisementKind.isDeleted.$invalid, 'invalid': $v.advertisementKind.isDeleted.$invalid }" v-model="$v.advertisementKind.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.advertisementKind.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./advertisement-kind-update.component.ts">
</script>
