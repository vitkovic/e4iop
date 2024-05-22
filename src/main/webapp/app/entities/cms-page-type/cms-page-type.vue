<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsPageType.home.title')" id="cms-page-type-heading">Cms Page Types</span>
            <router-link :to="{name: 'CmsPageTypeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-page-type">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.cmsPageType.home.createLabel')">
                    Create a new Cms Page Type
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
        <div class="alert alert-warning" v-if="!isFetching && cmsPageTypes && cmsPageTypes.length === 0">
            <span v-text="$t('riportalApp.cmsPageType.home.notFound')">No cmsPageTypes found</span>
        </div>
        <div class="table-responsive" v-if="cmsPageTypes && cmsPageTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.cmsPageType.type')">Type</span></th>
                    <th><span v-text="$t('riportalApp.cmsPageType.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.cmsPageType.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsPageType in cmsPageTypes"
                    :key="cmsPageType.id">
                    <td>
                        <router-link :to="{name: 'CmsPageTypeView', params: {cmsPageTypeId: cmsPageType.id}}">{{cmsPageType.id}}</router-link>
                    </td>
                    <td>{{cmsPageType.type}}</td>
                    <td>{{cmsPageType.isVisible}}</td>
                    <td>{{cmsPageType.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CmsPageTypeView', params: {cmsPageTypeId: cmsPageType.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CmsPageTypeEdit', params: {cmsPageTypeId: cmsPageType.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cmsPageType)"
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
            <span slot="modal-title"><span id="riportalApp.cmsPageType.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cmsPageType-heading" v-text="$t('riportalApp.cmsPageType.delete.question', {'id': removeId})">Are you sure you want to delete this Cms Page Type?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsPageType" v-text="$t('entity.action.delete')" v-on:click="removeCmsPageType()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./cms-page-type.component.ts">
</script>
