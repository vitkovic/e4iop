<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.serviceOfferRespond.home.title')" id="service-offer-respond-heading">Service Offer Responds</span>
            <router-link :to="{name: 'ServiceOfferRespondCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-service-offer-respond">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.serviceOfferRespond.home.createLabel')">
                    Create a new Service Offer Respond
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
        <div class="alert alert-warning" v-if="!isFetching && serviceOfferResponds && serviceOfferResponds.length === 0">
            <span v-text="$t('riportalApp.serviceOfferRespond.home.notFound')">No serviceOfferResponds found</span>
        </div>
        <div class="table-responsive" v-if="serviceOfferResponds && serviceOfferResponds.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.serviceOfferRespond.date')">Date</span></th>
                    <th><span v-text="$t('riportalApp.serviceOfferRespond.description')">Description</span></th>
                    <th><span v-text="$t('riportalApp.serviceOfferRespond.offerAnswer')">Offer Answer</span></th>
                    <th><span v-text="$t('riportalApp.serviceOfferRespond.requestAnswer')">Request Answer</span></th>
                    <th><span v-text="$t('riportalApp.serviceOfferRespond.respondsToRequest')">Responds To Request</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="serviceOfferRespond in serviceOfferResponds"
                    :key="serviceOfferRespond.id">
                    <td>
                        <router-link :to="{name: 'ServiceOfferRespondView', params: {serviceOfferRespondId: serviceOfferRespond.id}}">{{serviceOfferRespond.id}}</router-link>
                    </td>
                    <td>{{serviceOfferRespond.date ? $d(Date.parse(serviceOfferRespond.date), 'short') : ''}}</td>
                    <td>{{serviceOfferRespond.description}}</td>
                    <td>
                        <div v-if="serviceOfferRespond.offerAnswer">
                            <router-link :to="{name: 'AnswerToOfferView', params: {answerToOfferId: serviceOfferRespond.offerAnswer.id}}">{{serviceOfferRespond.offerAnswer.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="serviceOfferRespond.requestAnswer">
                            <router-link :to="{name: 'RequestAnswerView', params: {requestAnswerId: serviceOfferRespond.requestAnswer.id}}">{{serviceOfferRespond.requestAnswer.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="serviceOfferRespond.respondsToRequest">
                            <router-link :to="{name: 'RequestRespondView', params: {requestRespondId: serviceOfferRespond.respondsToRequest.id}}">{{serviceOfferRespond.respondsToRequest.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ServiceOfferRespondView', params: {serviceOfferRespondId: serviceOfferRespond.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ServiceOfferRespondEdit', params: {serviceOfferRespondId: serviceOfferRespond.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(serviceOfferRespond)"
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
            <span slot="modal-title"><span id="riportalApp.serviceOfferRespond.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-serviceOfferRespond-heading" v-text="$t('riportalApp.serviceOfferRespond.delete.question', {'id': removeId})">Are you sure you want to delete this Service Offer Respond?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-serviceOfferRespond" v-text="$t('entity.action.delete')" v-on:click="removeServiceOfferRespond()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./service-offer-respond.component.ts">
</script>
