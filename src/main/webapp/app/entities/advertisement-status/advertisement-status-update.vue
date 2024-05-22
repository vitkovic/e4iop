<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.advertisementStatus.home.createOrEditLabel" v-text="$t('riportalApp.advertisementStatus.home.createOrEditLabel')">Create or edit a AdvertisementStatus</h2>
                <div>
                    <div class="form-group" v-if="advertisementStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="advertisementStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementStatus.status')" for="advertisement-status-status">Status</label>
                        <input type="text" class="form-control" name="status" id="advertisement-status-status"
                            :class="{'valid': !$v.advertisementStatus.status.$invalid, 'invalid': $v.advertisementStatus.status.$invalid }" v-model="$v.advertisementStatus.status.$model"  required/>
                        <div v-if="$v.advertisementStatus.status.$anyDirty && $v.advertisementStatus.status.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementStatus.status.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementStatus.isVisible')" for="advertisement-status-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="advertisement-status-isVisible"
                            :class="{'valid': !$v.advertisementStatus.isVisible.$invalid, 'invalid': $v.advertisementStatus.isVisible.$invalid }" v-model="$v.advertisementStatus.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementStatus.isDeleted')" for="advertisement-status-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="advertisement-status-isDeleted"
                            :class="{'valid': !$v.advertisementStatus.isDeleted.$invalid, 'invalid': $v.advertisementStatus.isDeleted.$invalid }" v-model="$v.advertisementStatus.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.advertisementStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./advertisement-status-update.component.ts">
</script>
