<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.serviceAvailability.home.createOrEditLabel" v-text="$t('riportalApp.serviceAvailability.home.createOrEditLabel')">Create or edit a ServiceAvailability</h2>
                <div>
                    <div class="form-group" v-if="serviceAvailability.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="serviceAvailability.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAvailability.availability')" for="service-availability-availability">Availability</label>
                        <input type="text" class="form-control" name="availability" id="service-availability-availability"
                            :class="{'valid': !$v.serviceAvailability.availability.$invalid, 'invalid': $v.serviceAvailability.availability.$invalid }" v-model="$v.serviceAvailability.availability.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAvailability.meaning')" for="service-availability-meaning">Meaning</label>
                        <input type="text" class="form-control" name="meaning" id="service-availability-meaning"
                            :class="{'valid': !$v.serviceAvailability.meaning.$invalid, 'invalid': $v.serviceAvailability.meaning.$invalid }" v-model="$v.serviceAvailability.meaning.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAvailability.availabilityEn')" for="service-availability-availabilityEn">Availability En</label>
                        <input type="text" class="form-control" name="availabilityEn" id="service-availability-availabilityEn"
                            :class="{'valid': !$v.serviceAvailability.availabilityEn.$invalid, 'invalid': $v.serviceAvailability.availabilityEn.$invalid }" v-model="$v.serviceAvailability.availabilityEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAvailability.availabilityCyr')" for="service-availability-availabilityCyr">Availability Cyr</label>
                        <input type="text" class="form-control" name="availabilityCyr" id="service-availability-availabilityCyr"
                            :class="{'valid': !$v.serviceAvailability.availabilityCyr.$invalid, 'invalid': $v.serviceAvailability.availabilityCyr.$invalid }" v-model="$v.serviceAvailability.availabilityCyr.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAvailability.meaningEn')" for="service-availability-meaningEn">Meaning En</label>
                        <input type="text" class="form-control" name="meaningEn" id="service-availability-meaningEn"
                            :class="{'valid': !$v.serviceAvailability.meaningEn.$invalid, 'invalid': $v.serviceAvailability.meaningEn.$invalid }" v-model="$v.serviceAvailability.meaningEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceAvailability.meaningCyr')" for="service-availability-meaningCyr">Meaning Cyr</label>
                        <input type="text" class="form-control" name="meaningCyr" id="service-availability-meaningCyr"
                            :class="{'valid': !$v.serviceAvailability.meaningCyr.$invalid, 'invalid': $v.serviceAvailability.meaningCyr.$invalid }" v-model="$v.serviceAvailability.meaningCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.serviceAvailability.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./service-availability-update.component.ts">
</script>
