<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riCategory.home.title')" id="ri-category-heading">Ri Categories</span>
            <router-link :to="{name: 'RiCategoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-category">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riCategory.home.createLabel')">
                    Create a new Ri Category
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
        <div class="alert alert-warning" v-if="!isFetching && riCategories && riCategories.length === 0">
            <span v-text="$t('riportalApp.riCategory.home.notFound')">No riCategories found</span>
        </div>
        <div class="table-responsive" v-if="riCategories && riCategories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.riCategory.category')">Category</span></th>
                    <th><span v-text="$t('riportalApp.riCategory.categoryEn')">Category En</span></th>
                    <th><span v-text="$t('riportalApp.riCategory.categoryCyr')">Category Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riCategory in riCategories"
                    :key="riCategory.id">
                    <td>
                        <router-link :to="{name: 'RiCategoryView', params: {riCategoryId: riCategory.id}}">{{riCategory.id}}</router-link>
                    </td>
                    <td>{{riCategory.category}}</td>
                    <td>{{riCategory.categoryEn}}</td>
                    <td>{{riCategory.categoryCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RiCategoryView', params: {riCategoryId: riCategory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiCategoryEdit', params: {riCategoryId: riCategory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riCategory)"
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
            <span slot="modal-title"><span id="riportalApp.riCategory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riCategory-heading" v-text="$t('riportalApp.riCategory.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Category?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riCategory" v-text="$t('entity.action.delete')" v-on:click="removeRiCategory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./ri-category.component.ts">
</script>
