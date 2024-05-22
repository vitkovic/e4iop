<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riStatus.home.createOrEditLabel" v-text="$t('riportalApp.riStatus.home.createOrEditLabel')">Create or edit a RiStatus</h2>
                <div>
                    <div class="form-group" v-if="riStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riStatus.status')" for="ri-status-status">Status</label>
                        <input type="text" class="form-control" name="status" id="ri-status-status"
                            :class="{'valid': !$v.riStatus.status.$invalid, 'invalid': $v.riStatus.status.$invalid }" v-model="$v.riStatus.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riStatus.meaning')" for="ri-status-meaning">Meaning</label>
                        <input type="text" class="form-control" name="meaning" id="ri-status-meaning"
                            :class="{'valid': !$v.riStatus.meaning.$invalid, 'invalid': $v.riStatus.meaning.$invalid }" v-model="$v.riStatus.meaning.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riStatus.statusEn')" for="ri-status-statusEn">Status En</label>
                        <input type="text" class="form-control" name="statusEn" id="ri-status-statusEn"
                            :class="{'valid': !$v.riStatus.statusEn.$invalid, 'invalid': $v.riStatus.statusEn.$invalid }" v-model="$v.riStatus.statusEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riStatus.statusCyr')" for="ri-status-statusCyr">Status Cyr</label>
                        <input type="text" class="form-control" name="statusCyr" id="ri-status-statusCyr"
                            :class="{'valid': !$v.riStatus.statusCyr.$invalid, 'invalid': $v.riStatus.statusCyr.$invalid }" v-model="$v.riStatus.statusCyr.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riStatus.meaningEn')" for="ri-status-meaningEn">Meaning En</label>
                        <input type="text" class="form-control" name="meaningEn" id="ri-status-meaningEn"
                            :class="{'valid': !$v.riStatus.meaningEn.$invalid, 'invalid': $v.riStatus.meaningEn.$invalid }" v-model="$v.riStatus.meaningEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riStatus.meaningCyr')" for="ri-status-meaningCyr">Meaning Cyr</label>
                        <input type="text" class="form-control" name="meaningCyr" id="ri-status-meaningCyr"
                            :class="{'valid': !$v.riStatus.meaningCyr.$invalid, 'invalid': $v.riStatus.meaningCyr.$invalid }" v-model="$v.riStatus.meaningCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-status-update.component.ts">
</script>
