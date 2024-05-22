<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riCalendar.home.createOrEditLabel" v-text="$t('riportalApp.riCalendar.home.createOrEditLabel')">Create or edit a RiCalendar</h2>
                <div>
                    <div class="form-group" v-if="riCalendar.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riCalendar.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.startTime')" for="ri-calendar-startTime">Start Time</label>
                        <div class="d-flex">
                            <input id="ri-calendar-startTime" type="datetime-local" class="form-control" name="startTime" :class="{'valid': !$v.riCalendar.startTime.$invalid, 'invalid': $v.riCalendar.startTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.riCalendar.startTime.$model)"
                            @change="updateZonedDateTimeField('startTime', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.endTime')" for="ri-calendar-endTime">End Time</label>
                        <div class="d-flex">
                            <input id="ri-calendar-endTime" type="datetime-local" class="form-control" name="endTime" :class="{'valid': !$v.riCalendar.endTime.$invalid, 'invalid': $v.riCalendar.endTime.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.riCalendar.endTime.$model)"
                            @change="updateZonedDateTimeField('endTime', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.calNote')" for="ri-calendar-calNote">Cal Note</label>
                        <input type="text" class="form-control" name="calNote" id="ri-calendar-calNote"
                            :class="{'valid': !$v.riCalendar.calNote.$invalid, 'invalid': $v.riCalendar.calNote.$invalid }" v-model="$v.riCalendar.calNote.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.calRating')" for="ri-calendar-calRating">Cal Rating</label>
                        <input type="text" class="form-control" name="calRating" id="ri-calendar-calRating"
                            :class="{'valid': !$v.riCalendar.calRating.$invalid, 'invalid': $v.riCalendar.calRating.$invalid }" v-model="$v.riCalendar.calRating.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.calComment')" for="ri-calendar-calComment">Cal Comment</label>
                        <input type="text" class="form-control" name="calComment" id="ri-calendar-calComment"
                            :class="{'valid': !$v.riCalendar.calComment.$invalid, 'invalid': $v.riCalendar.calComment.$invalid }" v-model="$v.riCalendar.calComment.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.requestRespond')" for="ri-calendar-requestRespond">Request Respond</label>
                        <select class="form-control" id="ri-calendar-requestRespond" name="requestRespond" v-model="riCalendar.requestRespond">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riCalendar.requestRespond && requestRespondOption.id === riCalendar.requestRespond.id ? riCalendar.requestRespond : requestRespondOption" v-for="requestRespondOption in requestResponds" :key="requestRespondOption.id">{{requestRespondOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.serviceStatus')" for="ri-calendar-serviceStatus">Service Status</label>
                        <select class="form-control" id="ri-calendar-serviceStatus" name="serviceStatus" v-model="riCalendar.serviceStatus">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riCalendar.serviceStatus && riServiceStatusOption.id === riCalendar.serviceStatus.id ? riCalendar.serviceStatus : riServiceStatusOption" v-for="riServiceStatusOption in riServiceStatuses" :key="riServiceStatusOption.id">{{riServiceStatusOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.portalUsers')" for="ri-calendar-portalUsers">Portal Users</label>
                        <select class="form-control" id="ri-calendar-portalUsers" name="portalUsers" v-model="riCalendar.portalUsers">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riCalendar.portalUsers && portalUserOption.id === riCalendar.portalUsers.id ? riCalendar.portalUsers : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.researchInfrastructure')" for="ri-calendar-researchInfrastructure">Research Infrastructure</label>
                        <select class="form-control" id="ri-calendar-researchInfrastructure" name="researchInfrastructure" v-model="riCalendar.researchInfrastructure">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riCalendar.researchInfrastructure && researchInfrastructureOption.id === riCalendar.researchInfrastructure.id ? riCalendar.researchInfrastructure : researchInfrastructureOption" v-for="researchInfrastructureOption in researchInfrastructures" :key="researchInfrastructureOption.id">{{researchInfrastructureOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riCalendar.service')" for="ri-calendar-service">Service</label>
                        <select class="form-control" id="ri-calendar-service" name="service" v-model="riCalendar.service">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riCalendar.service && riServiceOption.id === riCalendar.service.id ? riCalendar.service : riServiceOption" v-for="riServiceOption in riServices" :key="riServiceOption.id">{{riServiceOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riCalendar.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-calendar-update.component.ts">
</script>
