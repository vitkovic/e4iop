<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.puoLegalStatus.home.title')" id="puo-legal-status-heading">Puo Legal Statuses</span>
            <router-link :to="{name: 'PuoLegalStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-puo-legal-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.puoLegalStatus.home.createLabel')">
                    Create a new Puo Legal Status
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
        <div class="alert alert-warning" v-if="!isFetching && puoLegalStatuses && puoLegalStatuses.length === 0">
            <span v-text="$t('riportalApp.puoLegalStatus.home.notFound')">No puoLegalStatuses found</span>
        </div>
        <div class="table-responsive" v-if="puoLegalStatuses && puoLegalStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.puoLegalStatus.value')">Value</span></th>
                    <th><span v-text="$t('riportalApp.puoLegalStatus.description')">Description</span></th>
                    <th><span v-text="$t('riportalApp.puoLegalStatus.descriptionEn')">Description En</span></th>
                    <th><span v-text="$t('riportalApp.puoLegalStatus.descriptionCyr')">Description Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="puoLegalStatus in puoLegalStatuses"
                    :key="puoLegalStatus.id">
                    <td>
                        <router-link :to="{name: 'PuoLegalStatusView', params: {puoLegalStatusId: puoLegalStatus.id}}">{{puoLegalStatus.id}}</router-link>
                    </td>
                    <td>{{puoLegalStatus.value}}</td>
                    <td>{{puoLegalStatus.description}}</td>
                    <td>{{puoLegalStatus.descriptionEn}}</td>
                    <td>{{puoLegalStatus.descriptionCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PuoLegalStatusView', params: {puoLegalStatusId: puoLegalStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PuoLegalStatusEdit', params: {puoLegalStatusId: puoLegalStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(puoLegalStatus)"
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
            <span slot="modal-title"><span id="riportalApp.puoLegalStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-puoLegalStatus-heading" v-text="$t('riportalApp.puoLegalStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Puo Legal Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-puoLegalStatus" v-text="$t('entity.action.delete')" v-on:click="removePuoLegalStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./puo-legal-status.component.ts">
</script>
