<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.collaborationRating.home.title')" id="collaboration-rating-heading">Collaboration Ratings</span>
            <router-link :to="{name: 'CollaborationRatingCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-collaboration-rating">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.collaborationRating.home.createLabel')">
                    Create a new Collaboration Rating
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
        <div class="alert alert-warning" v-if="!isFetching && collaborationRatings && collaborationRatings.length === 0">
            <span v-text="$t('riportalApp.collaborationRating.home.notFound')">No collaborationRatings found</span>
        </div>
        <div class="table-responsive" v-if="collaborationRatings && collaborationRatings.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.collaborationRating.number')">Number</span></th>
                    <th><span v-text="$t('riportalApp.collaborationRating.description')">Description</span></th>
                    <th><span v-text="$t('riportalApp.collaborationRating.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.collaborationRating.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="collaborationRating in collaborationRatings"
                    :key="collaborationRating.id">
                    <td>
                        <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaborationRating.id}}">{{collaborationRating.id}}</router-link>
                    </td>
                    <td>{{collaborationRating.number}}</td>
                    <td>{{collaborationRating.description}}</td>
                    <td>{{collaborationRating.isVisible}}</td>
                    <td>{{collaborationRating.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaborationRating.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CollaborationRatingEdit', params: {collaborationRatingId: collaborationRating.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(collaborationRating)"
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
            <span slot="modal-title"><span id="riportalApp.collaborationRating.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-collaborationRating-heading" v-text="$t('riportalApp.collaborationRating.delete.question', {'id': removeId})">Are you sure you want to delete this Collaboration Rating?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-collaborationRating" v-text="$t('entity.action.delete')" v-on:click="removeCollaborationRating()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./collaboration-rating.component.ts">
</script>
