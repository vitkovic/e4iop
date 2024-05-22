<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.documentKind.home.title')" id="document-kind-heading">Document Kinds</span>
            <router-link :to="{name: 'DocumentKindCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-document-kind">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.documentKind.home.createLabel')">
                    Create a new Document Kind
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
        <div class="alert alert-warning" v-if="!isFetching && documentKinds && documentKinds.length === 0">
            <span v-text="$t('riportalApp.documentKind.home.notFound')">No documentKinds found</span>
        </div>
        <div class="table-responsive" v-if="documentKinds && documentKinds.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.documentKind.type')">Type</span></th>
                    <th><span v-text="$t('riportalApp.documentKind.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.documentKind.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="documentKind in documentKinds"
                    :key="documentKind.id">
                    <td>
                        <router-link :to="{name: 'DocumentKindView', params: {documentKindId: documentKind.id}}">{{documentKind.id}}</router-link>
                    </td>
                    <td>{{documentKind.type}}</td>
                    <td>{{documentKind.isVisible}}</td>
                    <td>{{documentKind.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DocumentKindView', params: {documentKindId: documentKind.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DocumentKindEdit', params: {documentKindId: documentKind.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(documentKind)"
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
            <span slot="modal-title"><span id="riportalApp.documentKind.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-documentKind-heading" v-text="$t('riportalApp.documentKind.delete.question', {'id': removeId})">Are you sure you want to delete this Document Kind?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-documentKind" v-text="$t('entity.action.delete')" v-on:click="removeDocumentKind()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./document-kind.component.ts">
</script>
