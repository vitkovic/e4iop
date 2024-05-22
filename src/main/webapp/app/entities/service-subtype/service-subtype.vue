<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.serviceSubtype.home.title')" id="service-subtype-heading">Service Subtypes</span>
            <router-link :to="{name: 'ServiceSubtypeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-service-subtype">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.serviceSubtype.home.createLabel')">
                    Create a new Service Subtype
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
        <div class="alert alert-warning" v-if="!isFetching && serviceSubtypes && serviceSubtypes.length === 0">
            <span v-text="$t('riportalApp.serviceSubtype.home.notFound')">No serviceSubtypes found</span>
        </div>
        <div class="table-responsive" v-if="serviceSubtypes && serviceSubtypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.serviceSubtype.subtype')">Subtype</span></th>
                    <th><span v-text="$t('riportalApp.serviceSubtype.subtypeEn')">Subtype En</span></th>
                    <th><span v-text="$t('riportalApp.serviceSubtype.subtypeCyr')">Subtype Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="serviceSubtype in serviceSubtypes"
                    :key="serviceSubtype.id">
                    <td>
                        <router-link :to="{name: 'ServiceSubtypeView', params: {serviceSubtypeId: serviceSubtype.id}}">{{serviceSubtype.id}}</router-link>
                    </td>
                    <td>{{serviceSubtype.subtype}}</td>
                    <td>{{serviceSubtype.subtypeEn}}</td>
                    <td>{{serviceSubtype.subtypeCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ServiceSubtypeView', params: {serviceSubtypeId: serviceSubtype.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ServiceSubtypeEdit', params: {serviceSubtypeId: serviceSubtype.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(serviceSubtype)"
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
            <span slot="modal-title"><span id="riportalApp.serviceSubtype.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-serviceSubtype-heading" v-text="$t('riportalApp.serviceSubtype.delete.question', {'id': removeId})">Are you sure you want to delete this Service Subtype?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-serviceSubtype" v-text="$t('entity.action.delete')" v-on:click="removeServiceSubtype()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./service-subtype.component.ts">
</script>
