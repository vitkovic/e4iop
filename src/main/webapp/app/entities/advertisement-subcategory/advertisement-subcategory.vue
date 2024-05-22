<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisementSubcategory.home.title')" id="advertisement-subcategory-heading">Advertisement Subcategories</span>
            <router-link :to="{name: 'AdvertisementSubcategoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-advertisement-subcategory">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.advertisementSubcategory.home.createLabel')">
                    Create a new Advertisement Subcategory
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
        <div class="alert alert-warning" v-if="!isFetching && advertisementSubcategories && advertisementSubcategories.length === 0">
            <span v-text="$t('riportalApp.advertisementSubcategory.home.notFound')">No advertisementSubcategories found</span>
        </div>
        <div class="table-responsive" v-if="advertisementSubcategories && advertisementSubcategories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubcategory.name')">Name</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubcategory.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubcategory.isDeleted')">Is Deleted</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubcategory.advertisementCategory')">Advertisement Category</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisementSubcategory in advertisementSubcategories"
                    :key="advertisementSubcategory.id">
                    <td>
                        <router-link :to="{name: 'AdvertisementSubcategoryView', params: {advertisementSubcategoryId: advertisementSubcategory.id}}">{{advertisementSubcategory.id}}</router-link>
                    </td>
                    <td>{{advertisementSubcategory.name}}</td>
                    <td>{{advertisementSubcategory.isVisible}}</td>
                    <td>{{advertisementSubcategory.isDeleted}}</td>
                    <td>
                        <div v-if="advertisementSubcategory.advertisementCategory">
                            <router-link :to="{name: 'AdvertisementCategoryView', params: {advertisementCategoryId: advertisementSubcategory.advertisementCategory.id}}">{{advertisementSubcategory.advertisementCategory.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementSubcategoryView', params: {advertisementSubcategoryId: advertisementSubcategory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdvertisementSubcategoryEdit', params: {advertisementSubcategoryId: advertisementSubcategory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(advertisementSubcategory)"
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
            <span slot="modal-title"><span id="riportalApp.advertisementSubcategory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-advertisementSubcategory-heading" v-text="$t('riportalApp.advertisementSubcategory.delete.question', {'id': removeId})">Are you sure you want to delete this Advertisement Subcategory?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisementSubcategory" v-text="$t('entity.action.delete')" v-on:click="removeAdvertisementSubcategory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./advertisement-subcategory.component.ts">
</script>
