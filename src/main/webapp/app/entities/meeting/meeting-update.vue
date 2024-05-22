<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.meeting.home.createOrEditLabel" v-text="$t('riportalApp.meeting.home.createOrEditLabel')">Create or edit a Meeting</h2>
                <div>
                    <div class="form-group" v-if="meeting.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="meeting.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.datetime')" for="meeting-datetime">Datetime</label>
                        <div class="d-flex">
                            <input id="meeting-datetime" type="datetime-local" class="form-control" name="datetime" :class="{'valid': !$v.meeting.datetime.$invalid, 'invalid': $v.meeting.datetime.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.meeting.datetime.$model)"
                            @change="updateInstantField('datetime', $event)"/>
                        </div>
                        <div v-if="$v.meeting.datetime.$anyDirty && $v.meeting.datetime.$invalid">
                            <small class="form-text text-danger" v-if="!$v.meeting.datetime.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.meeting.datetime.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.isAcepted')" for="meeting-isAcepted">Is Acepted</label>
                        <input type="checkbox" class="form-check" name="isAcepted" id="meeting-isAcepted"
                            :class="{'valid': !$v.meeting.isAcepted.$invalid, 'invalid': $v.meeting.isAcepted.$invalid }" v-model="$v.meeting.isAcepted.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.title')" for="meeting-title">Title</label>
                        <input type="text" class="form-control" name="title" id="meeting-title"
                            :class="{'valid': !$v.meeting.title.$invalid, 'invalid': $v.meeting.title.$invalid }" v-model="$v.meeting.title.$model"  required/>
                        <div v-if="$v.meeting.title.$anyDirty && $v.meeting.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.meeting.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.description')" for="meeting-description">Description</label>
                        <input type="text" class="form-control" name="description" id="meeting-description"
                            :class="{'valid': !$v.meeting.description.$invalid, 'invalid': $v.meeting.description.$invalid }" v-model="$v.meeting.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.comment')" for="meeting-comment">Comment</label>
                        <input type="text" class="form-control" name="comment" id="meeting-comment"
                            :class="{'valid': !$v.meeting.comment.$invalid, 'invalid': $v.meeting.comment.$invalid }" v-model="$v.meeting.comment.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.notes')" for="meeting-notes">Notes</label>
                        <input type="text" class="form-control" name="notes" id="meeting-notes"
                            :class="{'valid': !$v.meeting.notes.$invalid, 'invalid': $v.meeting.notes.$invalid }" v-model="$v.meeting.notes.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.portalUserOrganizer')" for="meeting-portalUserOrganizer">Portal User Organizer</label>
                        <select class="form-control" id="meeting-portalUserOrganizer" name="portalUserOrganizer" v-model="meeting.portalUserOrganizer">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="meeting.portalUserOrganizer && portalUserOption.id === meeting.portalUserOrganizer.id ? meeting.portalUserOrganizer : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.advertisement')" for="meeting-advertisement">Advertisement</label>
                        <select class="form-control" id="meeting-advertisement" name="advertisement" v-model="meeting.advertisement">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="meeting.advertisement && advertisementOption.id === meeting.advertisement.id ? meeting.advertisement : advertisementOption" v-for="advertisementOption in advertisements" :key="advertisementOption.id">{{advertisementOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meeting.type')" for="meeting-type">Type</label>
                        <select class="form-control" id="meeting-type" name="type" v-model="meeting.type">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="meeting.type && meetingTypeOption.id === meeting.type.id ? meeting.type : meetingTypeOption" v-for="meetingTypeOption in meetingTypes" :key="meetingTypeOption.id">{{meetingTypeOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.meeting.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./meeting-update.component.ts">
</script>
