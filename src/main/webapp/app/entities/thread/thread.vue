<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.thread.home.title')" id="thread-heading">Threads</span>
            <router-link :to="{name: 'ThreadCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-thread">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.thread.home.createLabel')">
                    Create a new Thread
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
        <div class="alert alert-warning" v-if="!isFetching && threads && threads.length === 0">
            <span v-text="$t('riportalApp.thread.home.notFound')">No threads found</span>
        </div>
        <div class="table-responsive" v-if="threads && threads.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('subject')"><span v-text="$t('riportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companySender.id')"><span v-text="'Company Sender'">Company Sender</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companySender.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companyReceiver.id')"><span v-text="'Company Receiver'">Company Receiver</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyReceiver.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="thread in threads"
                    :key="thread.id">
                    <td>
                        <router-link :to="{name: 'ThreadView', params: {threadId: thread.id}}">{{thread.id}}</router-link>
                    </td>
                    <td>{{thread.subject}}</td>
                    <td>
                        <div v-if="thread.companySender">
                            <router-link :to="{name: 'CompanyView', params: {companyId: thread.companySender.id}}">{{thread.companySender.name}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="thread.companyReceiver">
                            <router-link :to="{name: 'CompanyView', params: {companyId: thread.companyReceiver.id}}">{{thread.companyReceiver.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ThreadView', params: {threadId: thread.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ThreadEdit', params: {threadId: thread.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(thread)"
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
            <span slot="modal-title"><span id="riportalApp.thread.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-thread-heading" v-text="$t('riportalApp.thread.delete.question', {'id': removeId})">Are you sure you want to delete this Thread?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-thread" v-text="$t('entity.action.delete')" v-on:click="removeThread()">Delete</button>
            </div>
        </b-modal>
        <div v-show="threads && threads.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./thread.component.ts">
</script>
