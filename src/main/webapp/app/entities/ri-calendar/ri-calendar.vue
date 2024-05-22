<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riCalendar.home.title')" id="ri-calendar-heading">Ri Calendars</span>
            <router-link :to="{name: 'RiCalendarCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-calendar">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riCalendar.home.createLabel')">
                    Create a new Ri Calendar
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
        <div class="alert alert-warning" v-if="!isFetching && riCalendars && riCalendars.length === 0">
            <span v-text="$t('riportalApp.riCalendar.home.notFound')">No riCalendars found</span>
        </div>
        <div class="table-responsive" v-if="riCalendars && riCalendars.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('startTime')"><span v-text="$t('riportalApp.riCalendar.startTime')">Start Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startTime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('endTime')"><span v-text="$t('riportalApp.riCalendar.endTime')">End Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endTime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('calNote')"><span v-text="$t('riportalApp.riCalendar.calNote')">Cal Note</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'calNote'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('calRating')"><span v-text="$t('riportalApp.riCalendar.calRating')">Cal Rating</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'calRating'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('calComment')"><span v-text="$t('riportalApp.riCalendar.calComment')">Cal Comment</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'calComment'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('requestRespond.id')"><span v-text="$t('riportalApp.riCalendar.requestRespond')">Request Respond</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'requestRespond.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('serviceStatus.id')"><span v-text="$t('riportalApp.riCalendar.serviceStatus')">Service Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'serviceStatus.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('portalUsers.id')"><span v-text="$t('riportalApp.riCalendar.portalUsers')">Portal Users</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'portalUsers.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('researchInfrastructure.id')"><span v-text="$t('riportalApp.riCalendar.researchInfrastructure')">Research Infrastructure</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'researchInfrastructure.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('service.id')"><span v-text="$t('riportalApp.riCalendar.service')">Service</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'service.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riCalendar in riCalendars"
                    :key="riCalendar.id">
                    <td>
                        <router-link :to="{name: 'RiCalendarView', params: {riCalendarId: riCalendar.id}}">{{riCalendar.id}}</router-link>
                    </td>
                    <td>{{riCalendar.startTime ? $d(Date.parse(riCalendar.startTime), 'short') : ''}}</td>
                    <td>{{riCalendar.endTime ? $d(Date.parse(riCalendar.endTime), 'short') : ''}}</td>
                    <td>{{riCalendar.calNote}}</td>
                    <td>{{riCalendar.calRating}}</td>
                    <td>{{riCalendar.calComment}}</td>
                    <td>
                        <div v-if="riCalendar.requestRespond">
                            <router-link :to="{name: 'RequestRespondView', params: {requestRespondId: riCalendar.requestRespond.id}}">{{riCalendar.requestRespond.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="riCalendar.serviceStatus">
                            <router-link :to="{name: 'RiServiceStatusView', params: {riServiceStatusId: riCalendar.serviceStatus.id}}">{{riCalendar.serviceStatus.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="riCalendar.portalUsers">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: riCalendar.portalUsers.id}}">{{riCalendar.portalUsers.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="riCalendar.researchInfrastructure">
                            <router-link :to="{name: 'ResearchInfrastructureView', params: {researchInfrastructureId: riCalendar.researchInfrastructure.id}}">{{riCalendar.researchInfrastructure.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="riCalendar.service">
                            <router-link :to="{name: 'RiServiceView', params: {riServiceId: riCalendar.service.id}}">{{riCalendar.service.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RiCalendarView', params: {riCalendarId: riCalendar.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiCalendarEdit', params: {riCalendarId: riCalendar.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riCalendar)"
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
            <span slot="modal-title"><span id="riportalApp.riCalendar.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riCalendar-heading" v-text="$t('riportalApp.riCalendar.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Calendar?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riCalendar" v-text="$t('entity.action.delete')" v-on:click="removeRiCalendar()">Delete</button>
            </div>
        </b-modal>
        <div v-show="riCalendars && riCalendars.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-calendar.component.ts">
</script>
