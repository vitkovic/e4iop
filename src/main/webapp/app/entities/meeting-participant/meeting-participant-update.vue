<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.meetingParticipant.home.createOrEditLabel" v-text="$t('riportalApp.meetingParticipant.home.createOrEditLabel')">Create or edit a MeetingParticipant</h2>
                <div>
                    <div class="form-group" v-if="meetingParticipant.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="meetingParticipant.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meetingParticipant.hasAccepted')" for="meeting-participant-hasAccepted">Has Accepted</label>
                        <input type="checkbox" class="form-check" name="hasAccepted" id="meeting-participant-hasAccepted"
                            :class="{'valid': !$v.meetingParticipant.hasAccepted.$invalid, 'invalid': $v.meetingParticipant.hasAccepted.$invalid }" v-model="$v.meetingParticipant.hasAccepted.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meetingParticipant.meeting')" for="meeting-participant-meeting">Meeting</label>
                        <select class="form-control" id="meeting-participant-meeting" name="meeting" v-model="meetingParticipant.meeting">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="meetingParticipant.meeting && meetingOption.id === meetingParticipant.meeting.id ? meetingParticipant.meeting : meetingOption" v-for="meetingOption in meetings" :key="meetingOption.id">{{meetingOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meetingParticipant.portalUser')" for="meeting-participant-portalUser">Portal User</label>
                        <select class="form-control" id="meeting-participant-portalUser" name="portalUser" v-model="meetingParticipant.portalUser">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="meetingParticipant.portalUser && portalUserOption.id === meetingParticipant.portalUser.id ? meetingParticipant.portalUser : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.meetingParticipant.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./meeting-participant-update.component.ts">
</script>
