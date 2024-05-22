<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.serviceAccreditation.home.createOrEditLabel" v-text="$t('riportalApp.serviceAccreditation.home.createOrEditLabel')">Create or edit a ServiceAccreditation</h2>
                <div>
                    <div class="form-group" v-if="serviceAccreditation.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="serviceAccreditation.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAccreditation.status')" for="service-accreditation-status">Status</label>
                        <input type="text" class="form-control" name="status" id="service-accreditation-status"
                            :class="{'valid': !$v.serviceAccreditation.status.$invalid, 'invalid': $v.serviceAccreditation.status.$invalid }" v-model="$v.serviceAccreditation.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAccreditation.meaning')" for="service-accreditation-meaning">Meaning</label>
                        <input type="text" class="form-control" name="meaning" id="service-accreditation-meaning"
                            :class="{'valid': !$v.serviceAccreditation.meaning.$invalid, 'invalid': $v.serviceAccreditation.meaning.$invalid }" v-model="$v.serviceAccreditation.meaning.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAccreditation.statusEn')" for="service-accreditation-statusEn">Status En</label>
                        <input type="text" class="form-control" name="statusEn" id="service-accreditation-statusEn"
                            :class="{'valid': !$v.serviceAccreditation.statusEn.$invalid, 'invalid': $v.serviceAccreditation.statusEn.$invalid }" v-model="$v.serviceAccreditation.statusEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAccreditation.statusCyr')" for="service-accreditation-statusCyr">Status Cyr</label>
                        <input type="text" class="form-control" name="statusCyr" id="service-accreditation-statusCyr"
                            :class="{'valid': !$v.serviceAccreditation.statusCyr.$invalid, 'invalid': $v.serviceAccreditation.statusCyr.$invalid }" v-model="$v.serviceAccreditation.statusCyr.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAccreditation.meaningEn')" for="service-accreditation-meaningEn">Meaning En</label>
                        <input type="text" class="form-control" name="meaningEn" id="service-accreditation-meaningEn"
                            :class="{'valid': !$v.serviceAccreditation.meaningEn.$invalid, 'invalid': $v.serviceAccreditation.meaningEn.$invalid }" v-model="$v.serviceAccreditation.meaningEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAccreditation.meaningCyr')" for="service-accreditation-meaningCyr">Meaning Cyr</label>
                        <input type="text" class="form-control" name="meaningCyr" id="service-accreditation-meaningCyr"
                            :class="{'valid': !$v.serviceAccreditation.meaningCyr.$invalid, 'invalid': $v.serviceAccreditation.meaningCyr.$invalid }" v-model="$v.serviceAccreditation.meaningCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.serviceAccreditation.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./service-accreditation-update.component.ts">
</script>
