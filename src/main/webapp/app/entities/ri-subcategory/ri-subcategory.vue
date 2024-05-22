<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riSubcategory.home.title')" id="ri-subcategory-heading">Ri Subcategories</span>
            <router-link :to="{name: 'RiSubcategoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-subcategory">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riSubcategory.home.createLabel')">
                    Create a new Ri Subcategory
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
        <div class="alert alert-warning" v-if="!isFetching && riSubcategories && riSubcategories.length === 0">
            <span v-text="$t('riportalApp.riSubcategory.home.notFound')">No riSubcategories found</span>
        </div>
        <div class="table-responsive" v-if="riSubcategories && riSubcategories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.riSubcategory.subcategory')">Subcategory</span></th>
                    <th><span v-text="$t('riportalApp.riSubcategory.subcategoryEn')">Subcategory En</span></th>
                    <th><span v-text="$t('riportalApp.riSubcategory.subCategoryCyr')">Sub Category Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riSubcategory in riSubcategories"
                    :key="riSubcategory.id">
                    <td>
                        <router-link :to="{name: 'RiSubcategoryView', params: {riSubcategoryId: riSubcategory.id}}">{{riSubcategory.id}}</router-link>
                    </td>
                    <td>{{riSubcategory.subcategory}}</td>
                    <td>{{riSubcategory.subcategoryEn}}</td>
                    <td>{{riSubcategory.subCategoryCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RiSubcategoryView', params: {riSubcategoryId: riSubcategory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiSubcategoryEdit', params: {riSubcategoryId: riSubcategory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riSubcategory)"
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
            <span slot="modal-title"><span id="riportalApp.riSubcategory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riSubcategory-heading" v-text="$t('riportalApp.riSubcategory.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Subcategory?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riSubcategory" v-text="$t('entity.action.delete')" v-on:click="removeRiSubcategory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./ri-subcategory.component.ts">
</script>
