<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.requestRespond.home.title')" id="request-respond-heading">Request Responds</span>
            <router-link :to="{name: 'RequestRespondCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-request-respond">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.requestRespond.home.createLabel')">
                    Create a new Request Respond
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
        <div class="alert alert-warning" v-if="!isFetching && requestResponds && requestResponds.length === 0">
            <span v-text="$t('riportalApp.requestRespond.home.notFound')">No requestResponds found</span>
        </div>
        <div class="table-responsive" v-if="requestResponds && requestResponds.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.requestRespond.date')">Date</span></th>
                    <th><span v-text="$t('riportalApp.requestRespond.startTime')">Start Time</span></th>
                    <th><span v-text="$t('riportalApp.requestRespond.endTime')">End Time</span></th>
                    <th><span v-text="$t('riportalApp.requestRespond.price')">Price</span></th>
                    <th><span v-text="$t('riportalApp.requestRespond.message')">Message</span></th>
                    <th><span v-text="$t('riportalApp.requestRespond.requestForService')">Request For Service</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="requestRespond in requestResponds"
                    :key="requestRespond.id">
                    <td>
                        <router-link :to="{name: 'RequestRespondView', params: {requestRespondId: requestRespond.id}}">{{requestRespond.id}}</router-link>
                    </td>
                    <td>{{requestRespond.date ? $d(Date.parse(requestRespond.date), 'short') : ''}}</td>
                    <td>{{requestRespond.startTime ? $d(Date.parse(requestRespond.startTime), 'short') : ''}}</td>
                    <td>{{requestRespond.endTime ? $d(Date.parse(requestRespond.endTime), 'short') : ''}}</td>
                    <td>{{requestRespond.price}}</td>
                    <td>{{requestRespond.message}}</td>
                    <td>
                        <div v-if="requestRespond.requestForService">
                            <router-link :to="{name: 'RequestForServiceView', params: {requestForServiceId: requestRespond.requestForService.id}}">{{requestRespond.requestForService.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RequestRespondView', params: {requestRespondId: requestRespond.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RequestRespondEdit', params: {requestRespondId: requestRespond.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(requestRespond)"
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
            <span slot="modal-title"><span id="riportalApp.requestRespond.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-requestRespond-heading" v-text="$t('riportalApp.requestRespond.delete.question', {'id': removeId})">Are you sure you want to delete this Request Respond?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-requestRespond" v-text="$t('entity.action.delete')" v-on:click="removeRequestRespond()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./request-respond.component.ts">
</script>
