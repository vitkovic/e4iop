<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisementStatus.home.title')" id="advertisement-status-heading">Advertisement Statuses</span>
            <router-link :to="{name: 'AdvertisementStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-advertisement-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.advertisementStatus.home.createLabel')">
                    Create a new Advertisement Status
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
        <div class="alert alert-warning" v-if="!isFetching && advertisementStatuses && advertisementStatuses.length === 0">
            <span v-text="$t('riportalApp.advertisementStatus.home.notFound')">No advertisementStatuses found</span>
        </div>
        <div class="table-responsive" v-if="advertisementStatuses && advertisementStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.advertisementStatus.status')">Status</span></th>
                    <th><span v-text="$t('riportalApp.advertisementStatus.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.advertisementStatus.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisementStatus in advertisementStatuses"
                    :key="advertisementStatus.id">
                    <td>
                        <router-link :to="{name: 'AdvertisementStatusView', params: {advertisementStatusId: advertisementStatus.id}}">{{advertisementStatus.id}}</router-link>
                    </td>
                    <td>{{advertisementStatus.status}}</td>
                    <td>{{advertisementStatus.isVisible}}</td>
                    <td>{{advertisementStatus.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementStatusView', params: {advertisementStatusId: advertisementStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdvertisementStatusEdit', params: {advertisementStatusId: advertisementStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(advertisementStatus)"
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
            <span slot="modal-title"><span id="riportalApp.advertisementStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-advertisementStatus-heading" v-text="$t('riportalApp.advertisementStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Advertisement Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisementStatus" v-text="$t('entity.action.delete')" v-on:click="removeAdvertisementStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./advertisement-status.component.ts">
</script>
