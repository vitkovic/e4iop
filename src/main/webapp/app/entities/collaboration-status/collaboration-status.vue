<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.collaborationStatus.home.title')" id="collaboration-status-heading">Collaboration Statuses</span>
            <router-link :to="{name: 'CollaborationStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-collaboration-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.collaborationStatus.home.createLabel')">
                    Create a new Collaboration Status
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
        <div class="alert alert-warning" v-if="!isFetching && collaborationStatuses && collaborationStatuses.length === 0">
            <span v-text="$t('riportalApp.collaborationStatus.home.notFound')">No collaborationStatuses found</span>
        </div>
        <div class="table-responsive" v-if="collaborationStatuses && collaborationStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.collaborationStatus.status')">Status</span></th>
                    <th><span v-text="$t('riportalApp.collaborationStatus.statusSrc')">Status</span></th>
                    <th><span v-text="$t('riportalApp.collaborationStatus.statusEn')">Status</span></th>
                    <th><span v-text="$t('riportalApp.collaborationStatus.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.collaborationStatus.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="collaborationStatus in collaborationStatuses"
                    :key="collaborationStatus.id">
                    <td>
                        <router-link :to="{name: 'CollaborationStatusView', params: {collaborationStatusId: collaborationStatus.id}}">{{collaborationStatus.id}}</router-link>
                    </td>
                    <td>{{collaborationStatus.status}}</td>
                    <td>{{collaborationStatus.statusSrc}}</td>
                    <td>{{collaborationStatus.statusEn}}</td>
                    <td>{{collaborationStatus.isVisible}}</td>
                    <td>{{collaborationStatus.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CollaborationStatusView', params: {collaborationStatusId: collaborationStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CollaborationStatusEdit', params: {collaborationStatusId: collaborationStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(collaborationStatus)"
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
            <span slot="modal-title"><span id="riportalApp.collaborationStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-collaborationStatus-heading" v-text="$t('riportalApp.collaborationStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Collaboration Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-collaborationStatus" v-text="$t('entity.action.delete')" v-on:click="removeCollaborationStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./collaboration-status.component.ts">
</script>
