<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riAccessType.home.title')" id="ri-access-type-heading">Ri Access Types</span>
            <router-link :to="{name: 'RiAccessTypeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-access-type">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riAccessType.home.createLabel')">
                    Create a new Ri Access Type
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
        <div class="alert alert-warning" v-if="!isFetching && riAccessTypes && riAccessTypes.length === 0">
            <span v-text="$t('riportalApp.riAccessType.home.notFound')">No riAccessTypes found</span>
        </div>
        <div class="table-responsive" v-if="riAccessTypes && riAccessTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.riAccessType.type')">Type</span></th>
                    <th><span v-text="$t('riportalApp.riAccessType.typeEn')">Type En</span></th>
                    <th><span v-text="$t('riportalApp.riAccessType.typeCyr')">Type Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riAccessType in riAccessTypes"
                    :key="riAccessType.id">
                    <td>
                        <router-link :to="{name: 'RiAccessTypeView', params: {riAccessTypeId: riAccessType.id}}">{{riAccessType.id}}</router-link>
                    </td>
                    <td>{{riAccessType.type}}</td>
                    <td>{{riAccessType.typeEn}}</td>
                    <td>{{riAccessType.typeCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RiAccessTypeView', params: {riAccessTypeId: riAccessType.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiAccessTypeEdit', params: {riAccessTypeId: riAccessType.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riAccessType)"
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
            <span slot="modal-title"><span id="riportalApp.riAccessType.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riAccessType-heading" v-text="$t('riportalApp.riAccessType.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Access Type?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riAccessType" v-text="$t('entity.action.delete')" v-on:click="removeRiAccessType()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./ri-access-type.component.ts">
</script>
