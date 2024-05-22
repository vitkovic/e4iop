<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.meetingParticipant.home.title')" id="meeting-participant-heading">Meeting Participants</span>
            <router-link :to="{name: 'MeetingParticipantCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-meeting-participant">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.meetingParticipant.home.createLabel')">
                    Create a new Meeting Participant
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
        <div class="alert alert-warning" v-if="!isFetching && meetingParticipants && meetingParticipants.length === 0">
            <span v-text="$t('riportalApp.meetingParticipant.home.notFound')">No meetingParticipants found</span>
        </div>
        <div class="table-responsive" v-if="meetingParticipants && meetingParticipants.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipant.hasAccepted')">Has Accepted</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipant.meeting')">Meeting</span></th>
                    <th><span v-text="$t('riportalApp.meetingParticipant.portalUser')">Portal User</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="meetingParticipant in meetingParticipants"
                    :key="meetingParticipant.id">
                    <td>
                        <router-link :to="{name: 'MeetingParticipantView', params: {meetingParticipantId: meetingParticipant.id}}">{{meetingParticipant.id}}</router-link>
                    </td>
                    <td>{{meetingParticipant.hasAccepted}}</td>
                    <td>
                        <div v-if="meetingParticipant.meeting">
                            <router-link :to="{name: 'MeetingView', params: {meetingId: meetingParticipant.meeting.id}}">{{meetingParticipant.meeting.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="meetingParticipant.portalUser">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: meetingParticipant.portalUser.id}}">{{meetingParticipant.portalUser.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MeetingParticipantView', params: {meetingParticipantId: meetingParticipant.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MeetingParticipantEdit', params: {meetingParticipantId: meetingParticipant.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(meetingParticipant)"
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
            <span slot="modal-title"><span id="riportalApp.meetingParticipant.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-meetingParticipant-heading" v-text="$t('riportalApp.meetingParticipant.delete.question', {'id': removeId})">Are you sure you want to delete this Meeting Participant?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-meetingParticipant" v-text="$t('entity.action.delete')" v-on:click="removeMeetingParticipant()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./meeting-participant.component.ts">
</script>
