<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.company.home.title')" id="company-heading">Companies</span>
            <router-link :to="{name: 'CompanyCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-company">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.company.home.createLabel')">
                    Create a new Company
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
        <div class="alert alert-warning" v-if="!isFetching && companies && companies.length === 0">
            <span v-text="$t('riportalApp.company.home.notFound')">No companies found</span>
        </div>
        <div class="table-responsive" v-if="companies && companies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!-- <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('name')"><span v-text="$t('riportalApp.company.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rsnisId')"><span v-text="$t('riportalApp.company.rsnisId')">Rsnis Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rsnisId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('aprId')"><span v-text="$t('riportalApp.company.aprId')">Apr Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aprId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fields')"><span v-text="$t('riportalApp.company.fields')">Fields</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fields'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('about')"><span v-text="$t('riportalApp.company.about')">About</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'about'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.company.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.company.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.company.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.company.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('logo.id')"><span v-text="$t('riportalApp.company.logo')">Logo</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'logo.id'"></jhi-sort-indicator></th> -->
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="company in companies"
                    :key="company.id">
                    <!-- <td>
                        <router-link :to="{name: 'CompanyView', params: {companyId: company.id}}">{{company.id}}</router-link>
                    </td> -->
                    <td>{{company.name}}</td>
                    <td>{{company.rsnisId}}</td>
                    <td>{{company.aprId}}</td>
                    <!-- <td>{{company.about}}</td> -->
                    <td>{{company.fields}}</td>
                    <td>{{company.createdAt ? $d(Date.parse(company.createdAt), 'short') : ''}}</td>
                    <td>{{company.modifiedAt ? $d(Date.parse(company.modifiedAt), 'short') : ''}}</td>
                    <td>
                        <div v-if="company.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: company.createdBy.id}}">{{company.createdBy.firstname}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="company.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: company.modifiedBy.id}}">{{company.modifiedBy.firstname}}</router-link>
                        </div>
                    </td>
                    <!-- <td>
                        <div v-if="company.logo">
                            <router-link :to="{name: 'DocumentView', params: {documentId: company.logo.id}}">{{company.logo.id}}</router-link>
                        </div>
                    </td> -->
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompanyView', params: {companyId: company.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompanyEdit', params: {companyId: company.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(company)"
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
            <span slot="modal-title"><span id="riportalApp.company.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-company-heading" v-text="$t('riportalApp.company.delete.question', {'id': removeId})">Are you sure you want to delete this Company?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-company" v-text="$t('entity.action.delete')" v-on:click="removeCompany()">Delete</button>
            </div>
        </b-modal>
        <div v-show="companies && companies.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company.component.ts">
</script>
