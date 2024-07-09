<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisementSupporterStatus.home.title')" id="advertisement-supporter-status-heading">Advertisement Supporter Statuses</span>
            <router-link :to="{name: 'AdvertisementSupporterStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-advertisement-supporter-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span v-text="$t('riportalApp.advertisementSupporterStatus.home.createLabel')">
                    Create a new Advertisement Supporter Status
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
        <div class="alert alert-warning" v-if="!isFetching && advertisementSupporterStatuses && advertisementSupporterStatuses.length === 0">
            <span v-text="$t('riportalApp.advertisementSupporterStatus.home.notFound')">No advertisementSupporterStatuses found</span>
        </div>
        <div class="table-responsive" v-if="advertisementSupporterStatuses && advertisementSupporterStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporterStatus.nameSr')">NameSr</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporterStatus.nameSrc')">NameSrc</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporterStatus.nameEn')">NameEn</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporterStatus.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporterStatus.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisementSupporterStatus in advertisementSupporterStatuses"
                    :key="advertisementSupporterStatus.id">
                    <td>
                        <router-link :to="{name: 'AdvertisementSupporterStatusView', params: {advertisementSupporterStatusId: advertisementSupporterStatus.id}}">{{advertisementSupporterStatus.id}}</router-link>
                    </td>
                    <td>{{advertisementSupporterStatus.nameSr}}</td>
                    <td>{{advertisementSupporterStatus.nameSrc}}</td>
                    <td>{{advertisementSupporterStatus.nameEn}}</td>
                    <td>{{advertisementSupporterStatus.isVisible}}</td>
                    <td>{{advertisementSupporterStatus.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementSupporterStatusView', params: {advertisementSupporterStatusId: advertisementSupporterStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdvertisementSupporterStatusEdit', params: {advertisementSupporterStatusId: advertisementSupporterStatus.id}}" tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(advertisementSupporterStatus)"
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
            <span slot="modal-title"><span id="riportalApp.advertisementSupporterStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-advertisementSupporterStatus-heading" v-text="$t('riportalApp.advertisementSupporterStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Advertisement Supporter Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisementSupporterStatus" v-text="$t('entity.action.delete')" v-on:click="removeAdvertisementSupporterStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./advertisement-supporter-status.component.ts">
</script>
