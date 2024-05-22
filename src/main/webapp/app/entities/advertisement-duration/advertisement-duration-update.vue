<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.advertisementDuration.home.createOrEditLabel" v-text="$t('riportalApp.advertisementDuration.home.createOrEditLabel')">Create or edit a AdvertisementDuration</h2>
                <div>
                    <div class="form-group" v-if="advertisementDuration.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="advertisementDuration.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementDuration.duration')" for="advertisement-duration-duration">Duration</label>
                        <input type="number" class="form-control" name="duration" id="advertisement-duration-duration"
                            :class="{'valid': !$v.advertisementDuration.duration.$invalid, 'invalid': $v.advertisementDuration.duration.$invalid }" v-model.number="$v.advertisementDuration.duration.$model"  required/>
                        <div v-if="$v.advertisementDuration.duration.$anyDirty && $v.advertisementDuration.duration.$invalid">
                            <small class="form-text text-danger" v-if="!$v.advertisementDuration.duration.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.advertisementDuration.duration.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementDuration.isVisible')" for="advertisement-duration-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="advertisement-duration-isVisible"
                            :class="{'valid': !$v.advertisementDuration.isVisible.$invalid, 'invalid': $v.advertisementDuration.isVisible.$invalid }" v-model="$v.advertisementDuration.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementDuration.isDeleted')" for="advertisement-duration-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="advertisement-duration-isDeleted"
                            :class="{'valid': !$v.advertisementDuration.isDeleted.$invalid, 'invalid': $v.advertisementDuration.isDeleted.$invalid }" v-model="$v.advertisementDuration.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.advertisementDuration.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./advertisement-duration-update.component.ts">
</script>
