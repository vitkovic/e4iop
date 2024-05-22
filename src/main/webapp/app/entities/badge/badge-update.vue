<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.badge.home.createOrEditLabel" v-text="$t('riportalApp.badge.home.createOrEditLabel')">Create or edit a Badge</h2>
                <div>
                    <div class="form-group" v-if="badge.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="badge.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.badge.title')" for="badge-title">Title</label>
                        <input type="text" class="form-control" name="title" id="badge-title"
                            :class="{'valid': !$v.badge.title.$invalid, 'invalid': $v.badge.title.$invalid }" v-model="$v.badge.title.$model"  required/>
                        <div v-if="$v.badge.title.$anyDirty && $v.badge.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.badge.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.badge.description')" for="badge-description">Description</label>
                        <input type="text" class="form-control" name="description" id="badge-description"
                            :class="{'valid': !$v.badge.description.$invalid, 'invalid': $v.badge.description.$invalid }" v-model="$v.badge.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.badge.image')" for="badge-image">Image</label>
                        <select class="form-control" id="badge-image" name="image" v-model="badge.image">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="badge.image && documentOption.id === badge.image.id ? badge.image : documentOption" v-for="documentOption in documents" :key="documentOption.id">{{documentOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.badge.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./badge-update.component.ts">
</script>
