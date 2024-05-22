<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisementType.home.title')" id="advertisement-type-heading">Advertisement Types</span>
            <router-link :to="{name: 'AdvertisementTypeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-advertisement-type">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.advertisementType.home.createLabel')">
                    Create a new Advertisement Type
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
        <div class="alert alert-warning" v-if="!isFetching && advertisementTypes && advertisementTypes.length === 0">
            <span v-text="$t('riportalApp.advertisementType.home.notFound')">No advertisementTypes found</span>
        </div>
        <div class="table-responsive" v-if="advertisementTypes && advertisementTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.advertisementType.type')">Type</span></th>
                    <th><span v-text="$t('riportalApp.advertisementType.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.advertisementType.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisementType in advertisementTypes"
                    :key="advertisementType.id">
                    <td>
                        <router-link :to="{name: 'AdvertisementTypeView', params: {advertisementTypeId: advertisementType.id}}">{{advertisementType.id}}</router-link>
                    </td>
                    <td>{{advertisementType.type}}</td>
                    <td>{{advertisementType.isVisible}}</td>
                    <td>{{advertisementType.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementTypeView', params: {advertisementTypeId: advertisementType.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdvertisementTypeEdit', params: {advertisementTypeId: advertisementType.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(advertisementType)"
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
            <span slot="modal-title"><span id="riportalApp.advertisementType.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-advertisementType-heading" v-text="$t('riportalApp.advertisementType.delete.question', {'id': removeId})">Are you sure you want to delete this Advertisement Type?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisementType" v-text="$t('entity.action.delete')" v-on:click="removeAdvertisementType()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./advertisement-type.component.ts">
</script>
