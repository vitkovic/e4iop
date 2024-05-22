<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.collaborationRating.home.createOrEditLabel" v-text="$t('riportalApp.collaborationRating.home.createOrEditLabel')">Create or edit a CollaborationRating</h2>
                <div>
                    <div class="form-group" v-if="collaborationRating.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="collaborationRating.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaborationRating.number')" for="collaboration-rating-number">Number</label>
                        <input type="number" class="form-control" name="number" id="collaboration-rating-number"
                            :class="{'valid': !$v.collaborationRating.number.$invalid, 'invalid': $v.collaborationRating.number.$invalid }" v-model.number="$v.collaborationRating.number.$model"  required/>
                        <div v-if="$v.collaborationRating.number.$anyDirty && $v.collaborationRating.number.$invalid">
                            <small class="form-text text-danger" v-if="!$v.collaborationRating.number.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.collaborationRating.number.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaborationRating.description')" for="collaboration-rating-description">Description</label>
                        <input type="text" class="form-control" name="description" id="collaboration-rating-description"
                            :class="{'valid': !$v.collaborationRating.description.$invalid, 'invalid': $v.collaborationRating.description.$invalid }" v-model="$v.collaborationRating.description.$model"  required/>
                        <div v-if="$v.collaborationRating.description.$anyDirty && $v.collaborationRating.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.collaborationRating.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaborationRating.isVisible')" for="collaboration-rating-isVisible">Is Visible</label>
                        <input type="checkbox" class="form-check" name="isVisible" id="collaboration-rating-isVisible"
                            :class="{'valid': !$v.collaborationRating.isVisible.$invalid, 'invalid': $v.collaborationRating.isVisible.$invalid }" v-model="$v.collaborationRating.isVisible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaborationRating.isDeleted')" for="collaboration-rating-isDeleted">Is Deleted</label>
                        <input type="checkbox" class="form-check" name="isDeleted" id="collaboration-rating-isDeleted"
                            :class="{'valid': !$v.collaborationRating.isDeleted.$invalid, 'invalid': $v.collaborationRating.isDeleted.$invalid }" v-model="$v.collaborationRating.isDeleted.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.collaborationRating.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./collaboration-rating-update.component.ts">
</script>
