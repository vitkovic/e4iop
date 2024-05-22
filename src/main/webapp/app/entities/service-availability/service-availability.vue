<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.serviceAvailability.home.title')" id="service-availability-heading">Service Availabilities</span>
            <router-link :to="{name: 'ServiceAvailabilityCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-service-availability">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.serviceAvailability.home.createLabel')">
                    Create a new Service Availability
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && serviceAvailabilities && serviceAvailabilities.length === 0">
            <span v-text="$t('riportalApp.serviceAvailability.home.notFound')">No serviceAvailabilities found</span>
        </div>
        <div class="table-responsive" v-if="serviceAvailabilities && serviceAvailabilities.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.serviceAvailability.availability')">Availability</span></th>
                    <th><span v-text="$t('riportalApp.serviceAvailability.meaning')">Meaning</span></th>
                    <th><span v-text="$t('riportalApp.serviceAvailability.availabilityEn')">Availability En</span></th>
                    <th><span v-text="$t('riportalApp.serviceAvailability.availabilityCyr')">Availability Cyr</span></th>
                    <th><span v-text="$t('riportalApp.serviceAvailability.meaningEn')">Meaning En</span></th>
                    <th><span v-text="$t('riportalApp.serviceAvailability.meaningCyr')">Meaning Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="serviceAvailability in serviceAvailabilities"
                    :key="serviceAvailability.id">
                    <td>
                        <router-link :to="{name: 'ServiceAvailabilityView', params: {serviceAvailabilityId: serviceAvailability.id}}">{{serviceAvailability.id}}</router-link>
                    </td>
                    <td>{{serviceAvailability.availability}}</td>
                    <td>{{serviceAvailability.meaning}}</td>
                    <td>{{serviceAvailability.availabilityEn}}</td>
                    <td>{{serviceAvailability.availabilityCyr}}</td>
                    <td>{{serviceAvailability.meaningEn}}</td>
                    <td>{{serviceAvailability.meaningCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ServiceAvailabilityView', params: {serviceAvailabilityId: serviceAvailability.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ServiceAvailabilityEdit', params: {serviceAvailabilityId: serviceAvailability.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(serviceAvailability)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.serviceAvailability.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-serviceAvailability-heading" v-text="$t('riportalApp.serviceAvailability.delete.question', {'id': removeId})">Are you sure you want to delete this Service Availability?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-serviceAvailability" v-text="$t('entity.action.delete')" v-on:click="removeServiceAvailability()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./service-availability.component.ts">
</script>
