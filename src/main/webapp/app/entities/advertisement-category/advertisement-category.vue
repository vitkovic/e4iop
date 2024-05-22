<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisementCategory.home.title')" id="advertisement-category-heading">Advertisement Categories</span>
            <router-link :to="{name: 'AdvertisementCategoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-advertisement-category">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.advertisementCategory.home.createLabel')">
                    Create a new Advertisement Category
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
        <div class="alert alert-warning" v-if="!isFetching && advertisementCategories && advertisementCategories.length === 0">
            <span v-text="$t('riportalApp.advertisementCategory.home.notFound')">No advertisementCategories found</span>
        </div>
        <div class="table-responsive" v-if="advertisementCategories && advertisementCategories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.advertisementCategory.name')">Name</span></th>
                    <th><span v-text="$t('riportalApp.advertisementCategory.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.advertisementCategory.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisementCategory in advertisementCategories"
                    :key="advertisementCategory.id">
                    <td>
                        <router-link :to="{name: 'AdvertisementCategoryView', params: {advertisementCategoryId: advertisementCategory.id}}">{{advertisementCategory.id}}</router-link>
                    </td>
                    <td>{{advertisementCategory.name}}</td>
                    <td>{{advertisementCategory.isVisible}}</td>
                    <td>{{advertisementCategory.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementCategoryView', params: {advertisementCategoryId: advertisementCategory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdvertisementCategoryEdit', params: {advertisementCategoryId: advertisementCategory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(advertisementCategory)"
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
            <span slot="modal-title"><span id="riportalApp.advertisementCategory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-advertisementCategory-heading" v-text="$t('riportalApp.advertisementCategory.delete.question', {'id': removeId})">Are you sure you want to delete this Advertisement Category?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisementCategory" v-text="$t('entity.action.delete')" v-on:click="removeAdvertisementCategory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./advertisement-category.component.ts">
</script>
