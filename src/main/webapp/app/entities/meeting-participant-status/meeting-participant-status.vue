<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.meetingParticipantStatus.home.title')" id="meeting-participant-status-heading">Meeting Participant Statuses</span>
            <router-link :to="{name: 'MeetingParticipantStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-meeting-participant-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span v-text="$t('riportalApp.meetingParticipantStatus.home.createLabel')">
                    Create a new Meeting Participant Status
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
        <div class="alert alert-warning" v-if="!isFetching && meetingParticipantStatuses && meetingParticipantStatuses.length === 0">
            <span v-text="$t('riportalApp.meetingParticipantStatus.home.notFound')">No meetingParticipantStatuses found</span>
        </div>
        <div class="table-responsive" v-if="meetingParticipantStatuses && meetingParticipantStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipantStatus.statusSr')">StatusSr</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipantStatus.statusSrc')">StatusSrc</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipantStatus.statusEn')">StatusEn</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipantStatus.isVisible')">Is Visible</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipantStatus.isDeleted')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="meetingParticipantStatus in meetingParticipantStatuses"
                    :key="meetingParticipantStatus.id">
                    <td>
                        <router-link :to="{name: 'MeetingParticipantStatusView', params: {meetingParticipantStatusId: meetingParticipantStatus.id}}">{{meetingParticipantStatus.id}}</router-link>
                    </td>
                    <td>{{meetingParticipantStatus.statusSr}}</td>
                    <td>{{meetingParticipantStatus.statusSrc}}</td>
                    <td>{{meetingParticipantStatus.statusEn}}</td>
                    <td>{{meetingParticipantStatus.isVisible}}</td>
                    <td>{{meetingParticipantStatus.isDeleted}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MeetingParticipantStatusView', params: {meetingParticipantStatusId: meetingParticipantStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MeetingParticipantStatusEdit', params: {meetingParticipantStatusId: meetingParticipantStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(meetingParticipantStatus)"
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
            <span slot="modal-title"><span id="riportalApp.meetingParticipantStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-meetingParticipantStatus-heading" v-text="$t('riportalApp.meetingParticipantStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Meeting Participant Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-meetingParticipantStatus" v-text="$t('entity.action.delete')" v-on:click="removeMeetingParticipantStatus()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./meeting-participant-status.component.ts">
</script>
