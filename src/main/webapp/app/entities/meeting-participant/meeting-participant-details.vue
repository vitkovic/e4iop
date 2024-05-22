<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="meetingParticipant">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.meetingParticipant.detail.title')">MeetingParticipant</span> {{meetingParticipant.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.meetingParticipant.hasAccepted')">Has Accepted</span>
                    </dt>
                    <dd>
                        <span>{{meetingParticipant.hasAccepted}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meetingParticipant.meeting')">Meeting</span>
                    </dt>
                    <dd>
                        <div v-if="meetingParticipant.meeting">
                            <router-link :to="{name: 'MeetingView', params: {meetingId: meetingParticipant.meeting.id}}">{{meetingParticipant.meeting.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meetingParticipant.portalUser')">Portal User</span>
                    </dt>
                    <dd>
                        <div v-if="meetingParticipant.portalUser">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: meetingParticipant.portalUser.id}}">{{meetingParticipant.portalUser.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="meetingParticipant.id" :to="{name: 'MeetingParticipantEdit', params: {meetingParticipantId: meetingParticipant.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./meeting-participant-details.component.ts">
</script>
