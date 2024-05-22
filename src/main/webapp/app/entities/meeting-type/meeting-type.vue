<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.meetingType.home.title')" id="meeting-type-heading">Meeting Types</span>
            <router-link :to="{name: 'MeetingTypeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-meeting-type">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.meetingType.home.createLabel')">
                    Create a new Meeting Type
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
        <div class="alert alert-warning" v-if="!isFetching && meetingTypes && meetingTypes.length === 0">
            <span v-text="$t('riportalApp.meetingType.home.notFound')">No meetingTypes found</span>
        </div>
        <div class="table-responsive" v-if="meetingTypes && meetingTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.meetingType.type')">Type</span></th>
                    <th><span v-text="$t('riportalApp.meetingType.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.meetingType.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="meetingType in meetingTypes"
                    :key="meetingType.id">
                    <td>
                        <router-link :to="{name: 'MeetingTypeView', params: {meetingTypeId: meetingType.id}}">{{meetingType.id}}</router-link>
                    </td>
                    <td>{{meetingType.type}}</td>
                    <td>{{meetingType.isVisible}}</td>
                    <td>{{meetingType.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MeetingTypeView', params: {meetingTypeId: meetingType.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MeetingTypeEdit', params: {meetingTypeId: meetingType.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(meetingType)"
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
            <span slot="modal-title"><span id="riportalApp.meetingType.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-meetingType-heading" v-text="$t('riportalApp.meetingType.delete.question', {'id': removeId})">Are you sure you want to delete this Meeting Type?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-meetingType" v-text="$t('entity.action.delete')" v-on:click="removeMeetingType()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./meeting-type.component.ts">
</script>
