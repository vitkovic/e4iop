<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.message.home.title')" id="message-heading">Messages</span>
            <router-link :to="{name: 'MessageCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-message">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.message.home.createLabel')">
                    Create a new Message
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
        <div class="alert alert-warning" v-if="!isFetching && messages && messages.length === 0">
            <span v-text="$t('riportalApp.message.home.notFound')">No messages found</span>
        </div>
        <div class="table-responsive" v-if="messages && messages.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('datetime')"><span v-text="$t('riportalApp.message.datetime')">Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('riportalApp.message.content')">Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isRead')"><span v-text="$t('riportalApp.message.isRead')">Is Read</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isRead'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isDeletedSender')"><span v-text="$t('riportalApp.message.isDeletedSender')">Is Deleted Sender</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isDeletedSender'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isDeletedReceiver')"><span v-text="$t('riportalApp.message.isDeletedReceiver')">Is Deleted Receiver</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isDeletedReceiver'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('thread.id')"><span v-text="$t('riportalApp.message.thread')">Thread</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'thread.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('portalUserSender.id')"><span v-text="$t('riportalApp.message.portalUserSender')">Portal User Sender</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'portalUserSender.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('portalUserReceiver.id')"><span v-text="$t('riportalApp.message.portalUserReceiver')">Portal User Receiver</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'portalUserReceiver.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="message in messages"
                    :key="message.id">
                    <td>
                        <router-link :to="{name: 'MessageView', params: {messageId: message.id}}">{{message.id}}</router-link>
                    </td>
                    <td>{{message.datetime ? $d(Date.parse(message.datetime), 'short') : ''}}</td>
                    <td>{{message.content}}</td>
                    <td>{{message.isRead}}</td>
                    <td>{{message.isDeletedSender}}</td>
                    <td>{{message.isDeletedReceiver}}</td>
                    <td>
                        <div v-if="message.thread">
                            <router-link :to="{name: 'ThreadView', params: {threadId: message.thread.id}}">{{message.thread.subject}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="message.portalUserSender">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: message.portalUserSender.id}}">{{message.portalUserSender.firstname}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="message.portalUserReceiver">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: message.portalUserReceiver.id}}">{{message.portalUserReceiver.firstname}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MessageView', params: {messageId: message.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MessageEdit', params: {messageId: message.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(message)"
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
            <span slot="modal-title"><span id="riportalApp.message.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-message-heading" v-text="$t('riportalApp.message.delete.question', {'id': removeId})">Are you sure you want to delete this Message?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-message" v-text="$t('entity.action.delete')" v-on:click="removeMessage()">Delete</button>
            </div>
        </b-modal>
        <div v-show="messages && messages.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./message.component.ts">
</script>
