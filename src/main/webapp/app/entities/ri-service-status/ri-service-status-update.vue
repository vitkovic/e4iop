<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riServiceStatus.home.createOrEditLabel" v-text="$t('riportalApp.riServiceStatus.home.createOrEditLabel')">Create or edit a RiServiceStatus</h2>
                <div>
                    <div class="form-group" v-if="riServiceStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riServiceStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riServiceStatus.status')" for="ri-service-status-status">Status</label>
                        <input type="text" class="form-control" name="status" id="ri-service-status-status"
                            :class="{'valid': !$v.riServiceStatus.status.$invalid, 'invalid': $v.riServiceStatus.status.$invalid }" v-model="$v.riServiceStatus.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riServiceStatus.description')" for="ri-service-status-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ri-service-status-description"
                            :class="{'valid': !$v.riServiceStatus.description.$invalid, 'invalid': $v.riServiceStatus.description.$invalid }" v-model="$v.riServiceStatus.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riServiceStatus.statusEn')" for="ri-service-status-statusEn">Status En</label>
                        <input type="text" class="form-control" name="statusEn" id="ri-service-status-statusEn"
                            :class="{'valid': !$v.riServiceStatus.statusEn.$invalid, 'invalid': $v.riServiceStatus.statusEn.$invalid }" v-model="$v.riServiceStatus.statusEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riServiceStatus.statusCyr')" for="ri-service-status-statusCyr">Status Cyr</label>
                        <input type="text" class="form-control" name="statusCyr" id="ri-service-status-statusCyr"
                            :class="{'valid': !$v.riServiceStatus.statusCyr.$invalid, 'invalid': $v.riServiceStatus.statusCyr.$invalid }" v-model="$v.riServiceStatus.statusCyr.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riServiceStatus.descriptionEn')" for="ri-service-status-descriptionEn">Description En</label>
                        <input type="text" class="form-control" name="descriptionEn" id="ri-service-status-descriptionEn"
                            :class="{'valid': !$v.riServiceStatus.descriptionEn.$invalid, 'invalid': $v.riServiceStatus.descriptionEn.$invalid }" v-model="$v.riServiceStatus.descriptionEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riServiceStatus.descriptionCyr')" for="ri-service-status-descriptionCyr">Description Cyr</label>
                        <input type="text" class="form-control" name="descriptionCyr" id="ri-service-status-descriptionCyr"
                            :class="{'valid': !$v.riServiceStatus.descriptionCyr.$invalid, 'invalid': $v.riServiceStatus.descriptionCyr.$invalid }" v-model="$v.riServiceStatus.descriptionCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riServiceStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-service-status-update.component.ts">
</script>
