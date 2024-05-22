<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.meeting.home.title')" id="meeting-heading">Meetings</span>
            <router-link :to="{name: 'MeetingCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-meeting">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.meeting.home.createLabel')">
                    Create a new Meeting
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
        <div class="alert alert-warning" v-if="!isFetching && meetings && meetings.length === 0">
            <span v-text="$t('riportalApp.meeting.home.notFound')">No meetings found</span>
        </div>
        <div class="table-responsive" v-if="meetings && meetings.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.meeting.datetime')">Datetime</span></th>
                    <th><span v-text="$t('riportalApp.meeting.isAcepted')">Is Acepted</span></th>
                    <th><span v-text="$t('riportalApp.meeting.title')">Title</span></th>
                    <th><span v-text="$t('riportalApp.meeting.description')">Description</span></th>
                    <th><span v-text="$t('riportalApp.meeting.comment')">Comment</span></th>
                    <th><span v-text="$t('riportalApp.meeting.notes')">Notes</span></th>
                    <th><span v-text="$t('riportalApp.meeting.portalUserOrganizer')">Portal User Organizer</span></th>
                    <th><span v-text="$t('riportalApp.meeting.advertisement')">Advertisement</span></th>
                    <th><span v-text="$t('riportalApp.meeting.type')">Type</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="meeting in meetings"
                    :key="meeting.id">
                    <td>
                        <router-link :to="{name: 'MeetingView', params: {meetingId: meeting.id}}">{{meeting.id}}</router-link>
                    </td>
                    <td>{{meeting.datetime ? $d(Date.parse(meeting.datetime), 'short') : ''}}</td>
                    <td>{{meeting.isAcepted}}</td>
                    <td>{{meeting.title}}</td>
                    <td>{{meeting.description}}</td>
                    <td>{{meeting.comment}}</td>
                    <td>{{meeting.notes}}</td>
                    <td>
                        <div v-if="meeting.portalUserOrganizer">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: meeting.portalUserOrganizer.id}}">{{meeting.portalUserOrganizer.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="meeting.advertisement">
                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: meeting.advertisement.id}}">{{meeting.advertisement.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="meeting.type">
                            <router-link :to="{name: 'MeetingTypeView', params: {meetingTypeId: meeting.type.id}}">{{meeting.type.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MeetingView', params: {meetingId: meeting.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MeetingEdit', params: {meetingId: meeting.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(meeting)"
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
            <span slot="modal-title"><span id="riportalApp.meeting.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-meeting-heading" v-text="$t('riportalApp.meeting.delete.question', {'id': removeId})">Are you sure you want to delete this Meeting?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-meeting" v-text="$t('entity.action.delete')" v-on:click="removeMeeting()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./meeting.component.ts">
</script>
