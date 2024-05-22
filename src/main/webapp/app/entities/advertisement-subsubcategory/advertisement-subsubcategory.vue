<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisementSubsubcategory.home.title')" id="advertisement-subsubcategory-heading">Advertisement Subsubcategories</span>
            <router-link :to="{name: 'AdvertisementSubsubcategoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-advertisement-subsubcategory">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.advertisementSubsubcategory.home.createLabel')">
                    Create a new Advertisement Subsubcategory
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
        <div class="alert alert-warning" v-if="!isFetching && advertisementSubsubcategories && advertisementSubsubcategories.length === 0">
            <span v-text="$t('riportalApp.advertisementSubsubcategory.home.notFound')">No advertisementSubsubcategories found</span>
        </div>
        <div class="table-responsive" v-if="advertisementSubsubcategories && advertisementSubsubcategories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubsubcategory.name')">Name</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubsubcategory.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubsubcategory.isDeleted')">Is Deleted</span></th>
                    <th><span v-text="$t('riportalApp.advertisementSubsubcategory.advertisementSubcategory')">Advertisement Subcategory</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisementSubsubcategory in advertisementSubsubcategories"
                    :key="advertisementSubsubcategory.id">
                    <td>
                        <router-link :to="{name: 'AdvertisementSubsubcategoryView', params: {advertisementSubsubcategoryId: advertisementSubsubcategory.id}}">{{advertisementSubsubcategory.id}}</router-link>
                    </td>
                    <td>{{advertisementSubsubcategory.name}}</td>
                    <td>{{advertisementSubsubcategory.isVisible}}</td>
                    <td>{{advertisementSubsubcategory.isDeleted}}</td>
                    <td>
                        <div v-if="advertisementSubsubcategory.advertisementSubcategory">
                            <router-link :to="{name: 'AdvertisementSubcategoryView', params: {advertisementSubcategoryId: advertisementSubsubcategory.advertisementSubcategory.id}}">{{advertisementSubsubcategory.advertisementSubcategory.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdvertisementSubsubcategoryView', params: {advertisementSubsubcategoryId: advertisementSubsubcategory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdvertisementSubsubcategoryEdit', params: {advertisementSubsubcategoryId: advertisementSubsubcategory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(advertisementSubsubcategory)"
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
            <span slot="modal-title"><span id="riportalApp.advertisementSubsubcategory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-advertisementSubsubcategory-heading" v-text="$t('riportalApp.advertisementSubsubcategory.delete.question', {'id': removeId})">Are you sure you want to delete this Advertisement Subsubcategory?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-advertisementSubsubcategory" v-text="$t('entity.action.delete')" v-on:click="removeAdvertisementSubsubcategory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./advertisement-subsubcategory.component.ts">
</script>
