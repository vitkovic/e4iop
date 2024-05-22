<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="meeting">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.meeting.detail.title')">Meeting</span> {{meeting.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.meeting.datetime')">Datetime</span>
                    </dt>
                    <dd>
                        <span v-if="meeting.datetime">{{$d(Date.parse(meeting.datetime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.isAcepted')">Is Acepted</span>
                    </dt>
                    <dd>
                        <span>{{meeting.isAcepted}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{meeting.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{meeting.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.comment')">Comment</span>
                    </dt>
                    <dd>
                        <span>{{meeting.comment}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.notes')">Notes</span>
                    </dt>
                    <dd>
                        <span>{{meeting.notes}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.portalUserOrganizer')">Portal User Organizer</span>
                    </dt>
                    <dd>
                        <div v-if="meeting.portalUserOrganizer">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: meeting.portalUserOrganizer.id}}">{{meeting.portalUserOrganizer.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.advertisement')">Advertisement</span>
                    </dt>
                    <dd>
                        <div v-if="meeting.advertisement">
                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: meeting.advertisement.id}}">{{meeting.advertisement.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meeting.type')">Type</span>
                    </dt>
                    <dd>
                        <div v-if="meeting.type">
                            <router-link :to="{name: 'MeetingTypeView', params: {meetingTypeId: meeting.type.id}}">{{meeting.type.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="meeting.id" :to="{name: 'MeetingEdit', params: {meetingId: meeting.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./meeting-details.component.ts">
</script>
