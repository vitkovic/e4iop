<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.serviceAccreditation.home.title')" id="service-accreditation-heading">Service Accreditations</span>
            <router-link :to="{name: 'ServiceAccreditationCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-service-accreditation">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.serviceAccreditation.home.createLabel')">
                    Create a new Service Accreditation
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
        <div class="alert alert-warning" v-if="!isFetching && serviceAccreditations && serviceAccreditations.length === 0">
            <span v-text="$t('riportalApp.serviceAccreditation.home.notFound')">No serviceAccreditations found</span>
        </div>
        <div class="table-responsive" v-if="serviceAccreditations && serviceAccreditations.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.serviceAccreditation.status')">Status</span></th>
                    <th><span v-text="$t('riportalApp.serviceAccreditation.meaning')">Meaning</span></th>
                    <th><span v-text="$t('riportalApp.serviceAccreditation.statusEn')">Status En</span></th>
                    <th><span v-text="$t('riportalApp.serviceAccreditation.statusCyr')">Status Cyr</span></th>
                    <th><span v-text="$t('riportalApp.serviceAccreditation.meaningEn')">Meaning En</span></th>
                    <th><span v-text="$t('riportalApp.serviceAccreditation.meaningCyr')">Meaning Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="serviceAccreditation in serviceAccreditations"
                    :key="serviceAccreditation.id">
                    <td>
                        <router-link :to="{name: 'ServiceAccreditationView', params: {serviceAccreditationId: serviceAccreditation.id}}">{{serviceAccreditation.id}}</router-link>
                    </td>
                    <td>{{serviceAccreditation.status}}</td>
                    <td>{{serviceAccreditation.meaning}}</td>
                    <td>{{serviceAccreditation.statusEn}}</td>
                    <td>{{serviceAccreditation.statusCyr}}</td>
                    <td>{{serviceAccreditation.meaningEn}}</td>
                    <td>{{serviceAccreditation.meaningCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ServiceAccreditationView', params: {serviceAccreditationId: serviceAccreditation.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ServiceAccreditationEdit', params: {serviceAccreditationId: serviceAccreditation.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(serviceAccreditation)"
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
            <span slot="modal-title"><span id="riportalApp.serviceAccreditation.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-serviceAccreditation-heading" v-text="$t('riportalApp.serviceAccreditation.delete.question', {'id': removeId})">Are you sure you want to delete this Service Accreditation?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-serviceAccreditation" v-text="$t('entity.action.delete')" v-on:click="removeServiceAccreditation()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./service-accreditation.component.ts">
</script>
