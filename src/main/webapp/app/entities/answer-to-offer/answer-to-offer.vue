<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.answerToOffer.home.title')" id="answer-to-offer-heading">Answer To Offers</span>
            <router-link :to="{name: 'AnswerToOfferCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-answer-to-offer">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.answerToOffer.home.createLabel')">
                    Create a new Answer To Offer
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
        <div class="alert alert-warning" v-if="!isFetching && answerToOffers && answerToOffers.length === 0">
            <span v-text="$t('riportalApp.answerToOffer.home.notFound')">No answerToOffers found</span>
        </div>
        <div class="table-responsive" v-if="answerToOffers && answerToOffers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.answerToOffer.answer')">Answer</span></th>
                    <th><span v-text="$t('riportalApp.answerToOffer.answerEn')">Answer</span></th>
                    <th><span v-text="$t('riportalApp.answerToOffer.answerCyr')">Answer</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="answerToOffer in answerToOffers"
                    :key="answerToOffer.id">
                    <td>
                        <router-link :to="{name: 'AnswerToOfferView', params: {answerToOfferId: answerToOffer.id}}">{{answerToOffer.id}}</router-link>
                    </td>
                    <td>{{answerToOffer.answer}}</td>
                    <td>{{answerToOffer.answerEn}}</td>
                    <td>{{answerToOffer.answerCyr}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AnswerToOfferView', params: {answerToOfferId: answerToOffer.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AnswerToOfferEdit', params: {answerToOfferId: answerToOffer.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(answerToOffer)"
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
            <span slot="modal-title"><span id="riportalApp.answerToOffer.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-answerToOffer-heading" v-text="$t('riportalApp.answerToOffer.delete.question', {'id': removeId})">Are you sure you want to delete this Answer To Offer?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-answerToOffer" v-text="$t('entity.action.delete')" v-on:click="removeAnswerToOffer()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./answer-to-offer.component.ts">
</script>
