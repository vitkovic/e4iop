<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisementSupporter.home.title')" id="advertisement-supporter-heading">Advertisement Supporters</span>
            <router-link :to="{name: 'AdvertisementSupporterCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-advertisement-supporter">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.advertisementSupporter.home.createLabel')">
                    Create a new Advertisement Supporter
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
        <div class="alert alert-warning" v-if="!isFetching && advertisementSupporters && advertisementSupporters.length === 0">
            <span v-text="$t('riportalApp.advertisementSupporter.home.notFound')">No advertisementSupporters found</span>
        </div>
        <div class="table-responsive" v-if="advertisementSupporters && advertisementSupporters.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporter.hasAccepted')">Has Accepted</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporter.advertisement')">Advertisement</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporter.company')">Company</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSupporter.status')">Status</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisementSupporter in advertisementSupporters"
                    :key="advertisementSupporter.id">
                    <td>
                        <router-link :to="{name: 'AdvertisementSupporterView', params: {advertisementSupporterId: advertisementSupporter.id}}">{{advertisementSupporter.id}}</router-link>
                    </td>
                    <td>{{advertisementSupporter.hasAccepted}}</td>
                    <td>
                        <div v-if="advertisementSupporter.advertisement">
                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisementSupporter.advertisement.id}}">{{advertisementSupporter.advertisement.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisementSupporter.company">
                            <router-link :to="{name: 'CompanyView', params: {companyId: advertisementSupporter.company.id}}">{{advertisementSupporter.company.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisementSupporter.status">
                            <span>{{ advertisementSupporter.status.nameSr }}</span>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementSupporterView', params: {advertisementSupporterId: advertisementSupporter.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdvertisementSupporterEdit', params: {advertisementSupporterId: advertisementSupporter.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(advertisementSupporter)"
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
            <span slot="modal-title"><span id="riportalApp.advertisementSupporter.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-advertisementSupporter-heading" v-text="$t('riportalApp.advertisementSupporter.delete.question', {'id': removeId})">Are you sure you want to delete this Advertisement Supporter?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisementSupporter" v-text="$t('entity.action.delete')" v-on:click="removeAdvertisementSupporter()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./advertisement-supporter.component.ts">
</script>
