<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.meetingType.home.createOrEditLabel" v-text="$t('riportalApp.meetingType.home.createOrEditLabel')">Create or edit a MeetingType</h2>
                <div>
                    <div class="form-group" v-if="meetingType.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="meetingType.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meetingType.type')" for="meeting-type-type">Type</label>
                        <input type="text" class="form-control" name="type" id="meeting-type-type"
                            :class="{'valid': !$v.meetingType.type.$invalid, 'invalid': $v.meetingType.type.$invalid }" v-model="$v.meetingType.type.$model"  required/>
                        <div v-if="$v.meetingType.type.$anyDirty && $v.meetingType.type.$invalid">
                            <small class="form-text text-danger" v-if="!$v.meetingType.type.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meetingType.isVisible')" for="meeting-type-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="meeting-type-isVisible"
                            :class="{'valid': !$v.meetingType.isVisible.$invalid, 'invalid': $v.meetingType.isVisible.$invalid }" v-model="$v.meetingType.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.meetingType.isDeleted')" for="meeting-type-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="meeting-type-isDeleted"
                            :class="{'valid': !$v.meetingType.isDeleted.$invalid, 'invalid': $v.meetingType.isDeleted.$invalid }" v-model="$v.meetingType.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.meetingType.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./meeting-type-update.component.ts">
</script>
