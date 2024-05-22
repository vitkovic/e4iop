<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsAdvertisementCategory.home.title')" id="cms-advertisement-category-heading">Cms Advertisement Categories</span>
            <router-link :to="{name: 'CmsAdvertisementCategoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-advertisement-category">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.cmsAdvertisementCategory.home.createLabel')">
                    Create a new Cms Advertisement Category
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
        <div class="alert alert-warning" v-if="!isFetching && cmsAdvertisementCategories && cmsAdvertisementCategories.length === 0">
            <span v-text="$t('riportalApp.cmsAdvertisementCategory.home.notFound')">No cmsAdvertisementCategories found</span>
        </div>
        <div class="table-responsive" v-if="cmsAdvertisementCategories && cmsAdvertisementCategories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.cmsAdvertisementCategory.createdAt')">Created At</span></th>
                    <th><span v-text="$t('riportalApp.cmsAdvertisementCategory.modifiedAt')">Modified At</span></th>
                    <th><span v-text="$t('riportalApp.cmsAdvertisementCategory.createdBy')">Created By</span></th>
                    <th><span v-text="$t('riportalApp.cmsAdvertisementCategory.modifiedBy')">Modified By</span></th>
                    <th><span v-text="$t('riportalApp.cmsAdvertisementCategory.advertisementCategory')">Advertisement Category</span></th>
                    <th><span v-text="$t('riportalApp.cmsAdvertisementCategory.image')">Image</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsAdvertisementCategory in cmsAdvertisementCategories"
                    :key="cmsAdvertisementCategory.id">
                    <td>
                        <router-link :to="{name: 'CmsAdvertisementCategoryView', params: {cmsAdvertisementCategoryId: cmsAdvertisementCategory.id}}">{{cmsAdvertisementCategory.id}}</router-link>
                    </td>
                    <td>{{cmsAdvertisementCategory.createdAt ? $d(Date.parse(cmsAdvertisementCategory.createdAt), 'short') : ''}}</td>
                    <td>{{cmsAdvertisementCategory.modifiedAt ? $d(Date.parse(cmsAdvertisementCategory.modifiedAt), 'short') : ''}}</td>
                    <td>
                        <div v-if="cmsAdvertisementCategory.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsAdvertisementCategory.createdBy.id}}">{{cmsAdvertisementCategory.createdBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsAdvertisementCategory.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsAdvertisementCategory.modifiedBy.id}}">{{cmsAdvertisementCategory.modifiedBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsAdvertisementCategory.advertisementCategory">
                            <router-link :to="{name: 'AdvertisementCategoryView', params: {advertisementCategoryId: cmsAdvertisementCategory.advertisementCategory.id}}">{{cmsAdvertisementCategory.advertisementCategory.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsAdvertisementCategory.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: cmsAdvertisementCategory.image.id}}">{{cmsAdvertisementCategory.image.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CmsAdvertisementCategoryView', params: {cmsAdvertisementCategoryId: cmsAdvertisementCategory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CmsAdvertisementCategoryEdit', params: {cmsAdvertisementCategoryId: cmsAdvertisementCategory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cmsAdvertisementCategory)"
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
            <span slot="modal-title"><span id="riportalApp.cmsAdvertisementCategory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cmsAdvertisementCategory-heading" v-text="$t('riportalApp.cmsAdvertisementCategory.delete.question', {'id': removeId})">Are you sure you want to delete this Cms Advertisement Category?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsAdvertisementCategory" v-text="$t('entity.action.delete')" v-on:click="removeCmsAdvertisementCategory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./cms-advertisement-category.component.ts">
</script>
