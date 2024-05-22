<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.portalUserStatus.home.title')" id="portal-user-role-heading">Portal User Roles</span>
            <router-link :to="{name: 'PortalUserStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-portal-user-role">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.portalUserStatus.home.createLabel')"></span>
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
        <div class="alert alert-warning" v-if="!isFetching && portalUserStatuss && portalUserStatuss.length === 0">
            <span v-text="$t('riportalApp.portalUserStatus.home.notFound')">No portalUserStatuss found</span>
        </div>
        <div class="table-responsive" v-if="portalUserStatuss && portalUserStatuss.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.portalUserStatus.valueSr')"></span></th>
                    <th><span v-text="$t('riportalApp.portalUserStatus.valueEn')"></span></th>                    
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="portalUserStatus in portalUserStatuss"
                    :key="portalUserStatus.id">
                    <td>
                        <router-link :to="{name: 'portalUserStatusView', params: {portalUserStatusId: portalUserStatus.id}}">{{portalUserStatus.id}}</router-link>
                    </td>
                    <td>{{portalUserStatus.valueSr}}</td>
                    <td>{{portalUserStatus.valueEn}}</td>                    
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PortalUserStatusView', params: {portalUserStatusId: portalUserStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PortalUserStatusEdit', params: {portalUserStatusId: portalUserStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(portalUserStatus)"
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
            <span slot="modal-title"><span id="riportalApp.portalUserStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-portalUserStatus-heading" v-text="$t('riportalApp.portalUserStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Portal User Role?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-portalUserStatus" v-text="$t('entity.action.delete')" v-on:click="removeportalUserStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./portal-user-status.component.ts">
</script>
