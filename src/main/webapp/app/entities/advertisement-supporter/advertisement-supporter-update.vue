<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.advertisementSupporter.home.createOrEditLabel" v-text="$t('riportalApp.advertisementSupporter.home.createOrEditLabel')">Create or edit a AdvertisementSupporter</h2>
                <div>
                    <div class="form-group" v-if="advertisementSupporter.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="advertisementSupporter.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporter.hasAccepted')" for="advertisement-supporter-hasAccepted">Has Accepted</label>
                        <input type="checkbox" class="form-check" name="hasAccepted" id="advertisement-supporter-hasAccepted"
                            :class="{'valid': !$v.advertisementSupporter.hasAccepted.$invalid, 'invalid': $v.advertisementSupporter.hasAccepted.$invalid }" v-model="$v.advertisementSupporter.hasAccepted.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporter.advertisement')" for="advertisement-supporter-advertisement">Advertisement</label>
                        <select class="form-control" id="advertisement-supporter-advertisement" name="advertisement" v-model="advertisementSupporter.advertisement">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="advertisementSupporter.advertisement && advertisementOption.id === advertisementSupporter.advertisement.id ? advertisementSupporter.advertisement : advertisementOption" v-for="advertisementOption in advertisements" :key="advertisementOption.id">{{advertisementOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.advertisementSupporter.company')" for="advertisement-supporter-company">Company</label>
                        <select class="form-control" id="advertisement-supporter-company" name="company" v-model="advertisementSupporter.company">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="advertisementSupporter.company && companyOption.id === advertisementSupporter.company.id ? advertisementSupporter.company : companyOption" v-for="companyOption in companies" :key="companyOption.id">{{companyOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.advertisementSupporter.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./advertisement-supporter-update.component.ts">
</script>
