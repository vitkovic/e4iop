<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riServiceStatus.home.title')" id="ri-service-status-heading">Ri Service Statuses</span>
            <router-link :to="{name: 'RiServiceStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-service-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riServiceStatus.home.createLabel')">
                    Create a new Ri Service Status
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
        <div class="alert alert-warning" v-if="!isFetching && riServiceStatuses && riServiceStatuses.length === 0">
            <span v-text="$t('riportalApp.riServiceStatus.home.notFound')">No riServiceStatuses found</span>
        </div>
        <div class="table-responsive" v-if="riServiceStatuses && riServiceStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.riServiceStatus.status')">Status</span></th>
                    <th><span v-text="$t('riportalApp.riServiceStatus.description')">Description</span></th>
                    <th><span v-text="$t('riportalApp.riServiceStatus.statusEn')">Status En</span></th>
                    <th><span v-text="$t('riportalApp.riServiceStatus.statusCyr')">Status Cyr</span></th>
                    <th><span v-text="$t('riportalApp.riServiceStatus.descriptionEn')">Description En</span></th>
                    <th><span v-text="$t('riportalApp.riServiceStatus.descriptionCyr')">Description Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riServiceStatus in riServiceStatuses"
                    :key="riServiceStatus.id">
                    <td>
                        <router-link :to="{name: 'RiServiceStatusView', params: {riServiceStatusId: riServiceStatus.id}}">{{riServiceStatus.id}}</router-link>
                    </td>
                    <td>{{riServiceStatus.status}}</td>
                    <td>{{riServiceStatus.description}}</td>
                    <td>{{riServiceStatus.statusEn}}</td>
                    <td>{{riServiceStatus.statusCyr}}</td>
                    <td>{{riServiceStatus.descriptionEn}}</td>
                    <td>{{riServiceStatus.descriptionCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RiServiceStatusView', params: {riServiceStatusId: riServiceStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiServiceStatusEdit', params: {riServiceStatusId: riServiceStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riServiceStatus)"
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
            <span slot="modal-title"><span id="riportalApp.riServiceStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riServiceStatus-heading" v-text="$t('riportalApp.riServiceStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Service Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riServiceStatus" v-text="$t('entity.action.delete')" v-on:click="removeRiServiceStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./ri-service-status.component.ts">
</script>
