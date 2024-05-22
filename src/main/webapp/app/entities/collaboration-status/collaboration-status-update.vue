<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.collaborationStatus.home.createOrEditLabel" v-text="$t('riportalApp.collaborationStatus.home.createOrEditLabel')">Create or edit a CollaborationStatus</h2>
                <div>
                    <div class="form-group" v-if="collaborationStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="collaborationStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaborationStatus.status')" for="collaboration-status-status">Status</label>
                        <input type="text" class="form-control" name="status" id="collaboration-status-status"
                            :class="{'valid': !$v.collaborationStatus.status.$invalid, 'invalid': $v.collaborationStatus.status.$invalid }" v-model="$v.collaborationStatus.status.$model"  required/>
                        <div v-if="$v.collaborationStatus.status.$anyDirty && $v.collaborationStatus.status.$invalid">
                            <small class="form-text text-danger" v-if="!$v.collaborationStatus.status.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaborationStatus.isVisible')" for="collaboration-status-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="collaboration-status-isVisible"
                            :class="{'valid': !$v.collaborationStatus.isVisible.$invalid, 'invalid': $v.collaborationStatus.isVisible.$invalid }" v-model="$v.collaborationStatus.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaborationStatus.isDeleted')" for="collaboration-status-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="collaboration-status-isDeleted"
                            :class="{'valid': !$v.collaborationStatus.isDeleted.$invalid, 'invalid': $v.collaborationStatus.isDeleted.$invalid }" v-model="$v.collaborationStatus.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.collaborationStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./collaboration-status-update.component.ts">
</script>
