<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.scientificSubdomain.home.title')" id="scientific-subdomain-heading">Scientific Subdomains</span>
            <router-link :to="{name: 'ScientificSubdomainCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-scientific-subdomain">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.scientificSubdomain.home.createLabel')">
                    Create a new Scientific Subdomain
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
        <div class="alert alert-warning" v-if="!isFetching && scientificSubdomains && scientificSubdomains.length === 0">
            <span v-text="$t('riportalApp.scientificSubdomain.home.notFound')">No scientificSubdomains found</span>
        </div>
        <div class="table-responsive" v-if="scientificSubdomains && scientificSubdomains.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.scientificSubdomain.subdomain')">Subdomain</span></th>
                    <th><span v-text="$t('riportalApp.scientificSubdomain.subdomainEn')">Subdomain En</span></th>
                    <th><span v-text="$t('riportalApp.scientificSubdomain.subdomainCyr')">Subdomain Cyr</span></th>
                    <th><span v-text="$t('riportalApp.scientificSubdomain.domain')">Domain</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="scientificSubdomain in scientificSubdomains"
                    :key="scientificSubdomain.id">
                    <td>
                        <router-link :to="{name: 'ScientificSubdomainView', params: {scientificSubdomainId: scientificSubdomain.id}}">{{scientificSubdomain.id}}</router-link>
                    </td>
                    <td>{{scientificSubdomain.subdomain}}</td>
                    <td>{{scientificSubdomain.subdomainEn}}</td>
                    <td>{{scientificSubdomain.subdomainCyr}}</td>
                    <td>
                        <div v-if="scientificSubdomain.domain">
                            <router-link :to="{name: 'ScientificDomainView', params: {scientificDomainId: scientificSubdomain.domain.id}}">{{scientificSubdomain.domain.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ScientificSubdomainView', params: {scientificSubdomainId: scientificSubdomain.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ScientificSubdomainEdit', params: {scientificSubdomainId: scientificSubdomain.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(scientificSubdomain)"
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
            <span slot="modal-title"><span id="riportalApp.scientificSubdomain.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-scientificSubdomain-heading" v-text="$t('riportalApp.scientificSubdomain.delete.question', {'id': removeId})">Are you sure you want to delete this Scientific Subdomain?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-scientificSubdomain" v-text="$t('entity.action.delete')" v-on:click="removeScientificSubdomain()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./scientific-subdomain.component.ts">
</script>
