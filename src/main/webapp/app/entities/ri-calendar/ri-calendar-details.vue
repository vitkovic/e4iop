<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riCalendar">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riCalendar.detail.title')">RiCalendar</span> {{riCalendar.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.startTime')">Start Time</span>
                    </dt>
                    <dd>
                        <span v-if="riCalendar.startTime">{{$d(Date.parse(riCalendar.startTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.endTime')">End Time</span>
                    </dt>
                    <dd>
                        <span v-if="riCalendar.endTime">{{$d(Date.parse(riCalendar.endTime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.calNote')">Cal Note</span>
                    </dt>
                    <dd>
                        <span>{{riCalendar.calNote}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.calRating')">Cal Rating</span>
                    </dt>
                    <dd>
                        <span>{{riCalendar.calRating}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.calComment')">Cal Comment</span>
                    </dt>
                    <dd>
                        <span>{{riCalendar.calComment}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.requestRespond')">Request Respond</span>
                    </dt>
                    <dd>
                        <div v-if="riCalendar.requestRespond">
                            <router-link :to="{name: 'RequestRespondView', params: {requestRespondId: riCalendar.requestRespond.id}}">{{riCalendar.requestRespond.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.serviceStatus')">Service Status</span>
                    </dt>
                    <dd>
                        <div v-if="riCalendar.serviceStatus">
                            <router-link :to="{name: 'RiServiceStatusView', params: {riServiceStatusId: riCalendar.serviceStatus.id}}">{{riCalendar.serviceStatus.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.portalUsers')">Portal Users</span>
                    </dt>
                    <dd>
                        <div v-if="riCalendar.portalUsers">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: riCalendar.portalUsers.id}}">{{riCalendar.portalUsers.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.researchInfrastructure')">Research Infrastructure</span>
                    </dt>
                    <dd>
                        <div v-if="riCalendar.researchInfrastructure">
                            <router-link :to="{name: 'ResearchInfrastructureView', params: {researchInfrastructureId: riCalendar.researchInfrastructure.id}}">{{riCalendar.researchInfrastructure.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCalendar.service')">Service</span>
                    </dt>
                    <dd>
                        <div v-if="riCalendar.service">
                            <router-link :to="{name: 'RiServiceView', params: {riServiceId: riCalendar.service.id}}">{{riCalendar.service.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riCalendar.id" :to="{name: 'RiCalendarEdit', params: {riCalendarId: riCalendar.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-calendar-details.component.ts">
</script>
