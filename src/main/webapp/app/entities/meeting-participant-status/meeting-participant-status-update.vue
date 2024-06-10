<template>
    <div class="row justify-content-center">
      <div class="col-8">
        <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
          <h2 id="riportalApp.meetingParticipantStatus.home.createOrEditLabel" v-text="$t('riportalApp.meetingParticipantStatus.home.createOrEditLabel')">Create or edit a MeetingParticipantStatus</h2>
          <div>
            <div class="form-group" v-if="meetingParticipantStatus.id">
              <label for="id" v-text="$t('global.field.id')">ID</label>
              <input type="text" class="form-control" id="id" name="id" v-model="meetingParticipantStatus.id" readonly />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.meetingParticipantStatus.statusSr')" for="meeting-participant-status-statusSr">StatusSr</label>
              <input type="text" class="form-control" name="statusSr" id="meeting-participant-status-statusSr" :class="{'valid': !$v.meetingParticipantStatus.statusSr.$invalid, 'invalid': $v.meetingParticipantStatus.statusSr.$invalid }" v-model="$v.meetingParticipantStatus.statusSr.$model" required />
              <div v-if="$v.meetingParticipantStatus.statusSr.$anyDirty && $v.meetingParticipantStatus.statusSr.$invalid">
                <small class="form-text text-danger" v-if="!$v.meetingParticipantStatus.statusSr.required" v-text="$t('entity.validation.required')">This field is required.</small>
              </div>
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.meetingParticipantStatus.statusSrc')" for="meeting-participant-status-statusSrc">StatusSrc</label>
              <input type="text" class="form-control" name="statusSrc" id="meeting-participant-status-statusSrc" :class="{'valid': !$v.meetingParticipantStatus.statusSrc.$invalid, 'invalid': $v.meetingParticipantStatus.statusSrc.$invalid }" v-model="$v.meetingParticipantStatus.statusSrc.$model" required />
              <div v-if="$v.meetingParticipantStatus.statusSrc.$anyDirty && $v.meetingParticipantStatus.statusSrc.$invalid">
                <small class="form-text text-danger" v-if="!$v.meetingParticipantStatus.statusSrc.required" v-text="$t('entity.validation.required')">This field is required.</small>
              </div>
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.meetingParticipantStatus.statusEn')" for="meeting-participant-status-statusEn">StatusEn</label>
              <input type="text" class="form-control" name="statusEn" id="meeting-participant-status-statusEn" :class="{'valid': !$v.meetingParticipantStatus.statusEn.$invalid, 'invalid': $v.meetingParticipantStatus.statusEn.$invalid }" v-model="$v.meetingParticipantStatus.statusEn.$model" required />
              <div v-if="$v.meetingParticipantStatus.statusEn.$anyDirty && $v.meetingParticipantStatus.statusEn.$invalid">
                <small class="form-text text-danger" v-if="!$v.meetingParticipantStatus.statusEn.required" v-text="$t('entity.validation.required')">This field is required.</small>
              </div>
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.meetingParticipantStatus.isVisible')" for="meeting-participant-status-isVisible">Is Visible</label>
              <input type="checkbox" class="form-check" name="isVisible" id="meeting-participant-status-isVisible" :class="{'valid': !$v.meetingParticipantStatus.isVisible.$invalid, 'invalid': $v.meetingParticipantStatus.isVisible.$invalid }" v-model="$v.meetingParticipantStatus.isVisible.$model" />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.meetingParticipantStatus.isDeleted')" for="meeting-participant-status-isDeleted">Is Deleted</label>
              <input type="checkbox" class="form-check" name="isDeleted" id="meeting-participant-status-isDeleted" :class="{'valid': !$v.meetingParticipantStatus.isDeleted.$invalid, 'invalid': $v.meetingParticipantStatus.isDeleted.$invalid }" v-model="$v.meetingParticipantStatus.isDeleted.$model" />
            </div>
          </div>
          <div>
            <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
              <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
            </button>
            <button type="submit" id="save-entity" :disabled="$v.meetingParticipantStatus.$invalid || isSaving" class="btn btn-primary">
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </template>
  <script lang="ts" src="./meeting-participant-status-update.component.ts"></script>
  