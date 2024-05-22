<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riStatus.home.title')" id="ri-status-heading">Ri Statuses</span>
            <router-link :to="{name: 'RiStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riStatus.home.createLabel')">
                    Create a new Ri Status
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
        <div class="alert alert-warning" v-if="!isFetching && riStatuses && riStatuses.length === 0">
            <span v-text="$t('riportalApp.riStatus.home.notFound')">No riStatuses found</span>
        </div>
        <div class="table-responsive" v-if="riStatuses && riStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.riStatus.status')">Status</span></th>
                    <th><span v-text="$t('riportalApp.riStatus.meaning')">Meaning</span></th>
                    <th><span v-text="$t('riportalApp.riStatus.statusEn')">Status En</span></th>
                    <th><span v-text="$t('riportalApp.riStatus.statusCyr')">Status Cyr</span></th>
                    <th><span v-text="$t('riportalApp.riStatus.meaningEn')">Meaning En</span></th>
                    <th><span v-text="$t('riportalApp.riStatus.meaningCyr')">Meaning Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riStatus in riStatuses"
                    :key="riStatus.id">
                    <td>
                        <router-link :to="{name: 'RiStatusView', params: {riStatusId: riStatus.id}}">{{riStatus.id}}</router-link>
                    </td>
                    <td>{{riStatus.status}}</td>
                    <td>{{riStatus.meaning}}</td>
                    <td>{{riStatus.statusEn}}</td>
                    <td>{{riStatus.statusCyr}}</td>
                    <td>{{riStatus.meaningEn}}</td>
                    <td>{{riStatus.meaningCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RiStatusView', params: {riStatusId: riStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiStatusEdit', params: {riStatusId: riStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riStatus)"
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
            <span slot="modal-title"><span id="riportalApp.riStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riStatus-heading" v-text="$t('riportalApp.riStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riStatus" v-text="$t('entity.action.delete')" v-on:click="removeRiStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./ri-status.component.ts">
</script>
