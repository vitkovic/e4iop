<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.scientificDomain.home.title')" id="scientific-domain-heading">Scientific Domains</span>
            <router-link :to="{name: 'ScientificDomainCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-scientific-domain">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.scientificDomain.home.createLabel')">
                    Create a new Scientific Domain
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
        <div class="alert alert-warning" v-if="!isFetching && scientificDomains && scientificDomains.length === 0">
            <span v-text="$t('riportalApp.scientificDomain.home.notFound')">No scientificDomains found</span>
        </div>
        <div class="table-responsive" v-if="scientificDomains && scientificDomains.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.scientificDomain.domain')">Domain</span></th>
                    <th><span v-text="$t('riportalApp.scientificDomain.domainEn')">Domain En</span></th>
                    <th><span v-text="$t('riportalApp.scientificDomain.domainCyr')">Domain Cyr</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="scientificDomain in scientificDomains"
                    :key="scientificDomain.id">
                    <td>
                        <router-link :to="{name: 'ScientificDomainView', params: {scientificDomainId: scientificDomain.id}}">{{scientificDomain.id}}</router-link>
                    </td>
                    <td>{{scientificDomain.domain}}</td>
                    <td>{{scientificDomain.domainEn}}</td>
                    <td>{{scientificDomain.domainCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ScientificDomainView', params: {scientificDomainId: scientificDomain.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ScientificDomainEdit', params: {scientificDomainId: scientificDomain.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(scientificDomain)"
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
            <span slot="modal-title"><span id="riportalApp.scientificDomain.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-scientificDomain-heading" v-text="$t('riportalApp.scientificDomain.delete.question', {'id': removeId})">Are you sure you want to delete this Scientific Domain?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-scientificDomain" v-text="$t('entity.action.delete')" v-on:click="removeScientificDomain()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./scientific-domain.component.ts">
</script>
