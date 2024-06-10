<template>
    <div class="row justify-content-center">
        <div class="col-8">
        <div v-if="meetingParticipantStatus">
            <h2 class="jh-entity-heading">
            <span v-text="$t('riportalApp.meetingParticipantStatus.detail.title')">MeetingParticipantStatus</span> {{ meetingParticipantStatus.id }}
            </h2>
            <dl class="row jh-entity-details">
            <dt>
                <span v-text="$t('riportalApp.meetingParticipantStatus.statusSr')">StatusSr</span>
            </dt>
            <dd>
                <span>{{ meetingParticipantStatus.statusSr }}</span>
            </dd>
            <dt>
                <span v-text="$t('riportalApp.meetingParticipantStatus.statusSrc')">StatusSrc</span>
            </dt>
            <dd>
                <span>{{ meetingParticipantStatus.statusSrc }}</span>
            </dd>
            <dt>
                <span v-text="$t('riportalApp.meetingParticipantStatus.statusEn')">StatusEn</span>
            </dt>
            <dd>
                <span>{{ meetingParticipantStatus.statusEn }}</span>
            </dd>
            <dt>
                <span v-text="$t('riportalApp.meetingParticipantStatus.isVisible')">Is Visible</span>
            </dt>
            <dd>
                <span>{{ meetingParticipantStatus.isVisible }}</span>
            </dd>
            <dt>
                <span v-text="$t('riportalApp.meetingParticipantStatus.isDeleted')">Is Deleted</span>
            </dt>
            <dd>
                <span>{{ meetingParticipantStatus.isDeleted }}</span>
            </dd>
            </dl>
            <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
            </button>
            <router-link v-if="meetingParticipantStatus.id" :to="{ name: 'MeetingParticipantStatusEdit', params: { meetingParticipantStatusId: meetingParticipantStatus.id }}" tag="button" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
            </router-link>
        </div>
        </div>
    </div>
</template>
  
<script lang="ts" src="./meeting-participant-status-details.component.ts"></script>
