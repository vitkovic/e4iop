<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsPage.home.title')" id="cms-page-heading">Cms Pages</span>
            <router-link :to="{name: 'CmsPageCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-page">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.cmsPage.home.createLabel')">
                    Create a new Cms Page
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
        <div class="alert alert-warning" v-if="!isFetching && cmsPages && cmsPages.length === 0">
            <span v-text="$t('riportalApp.cmsPage.home.notFound')">No cmsPages found</span>
        </div>
        <div class="table-responsive" v-if="cmsPages && cmsPages.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.cmsPage.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.cmsPage.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.cmsPage.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('riportalApp.cmsPage.content')">Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('type.id')"><span v-text="$t('riportalApp.cmsPage.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.cmsPage.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.cmsPage.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsPage in cmsPages"
                    :key="cmsPage.id">
                    <td>
                        <router-link :to="{name: 'CmsPageView', params: {cmsPageId: cmsPage.id}}">{{cmsPage.id}}</router-link>
                    </td>
                    <td>{{cmsPage.createdAt ? $d(Date.parse(cmsPage.createdAt), 'short') : ''}}</td>
                    <td>{{cmsPage.modifiedAt ? $d(Date.parse(cmsPage.modifiedAt), 'short') : ''}}</td>
                    <td>{{cmsPage.title}}</td>
                    <td>{{cmsPage.content}}</td>
                    <td>
                        <div v-if="cmsPage.type">
                            <router-link :to="{name: 'CmsPageTypeView', params: {cmsPageTypeId: cmsPage.type.id}}">{{cmsPage.type.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsPage.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsPage.createdBy.id}}">{{cmsPage.createdBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsPage.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsPage.modifiedBy.id}}">{{cmsPage.modifiedBy.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CmsPageView', params: {cmsPageId: cmsPage.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CmsPageEdit', params: {cmsPageId: cmsPage.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cmsPage)"
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
            <span slot="modal-title"><span id="riportalApp.cmsPage.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cmsPage-heading" v-text="$t('riportalApp.cmsPage.delete.question', {'id': removeId})">Are you sure you want to delete this Cms Page?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsPage" v-text="$t('entity.action.delete')" v-on:click="removeCmsPage()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cmsPages && cmsPages.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-page.component.ts">
</script>
