<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsTestimonials.home.createOrEditLabel" v-text="$t('riportalApp.cmsTestimonials.home.createOrEditLabel')">Create or edit a CmsTestimonials</h2>
                <div>
                    <div class="form-group" v-if="cmsTestimonials.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsTestimonials.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsTestimonials.createdAt')" for="cms-testimonials-createdAt">Created At</label>
                        <div class="d-flex">
                            <input id="cms-testimonials-createdAt" type="datetime-local" class="form-control" name="createdAt" :class="{'valid': !$v.cmsTestimonials.createdAt.$invalid, 'invalid': $v.cmsTestimonials.createdAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsTestimonials.createdAt.$model)"
                            @change="updateInstantField('createdAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsTestimonials.modifiedAt')" for="cms-testimonials-modifiedAt">Modified At</label>
                        <div class="d-flex">
                            <input id="cms-testimonials-modifiedAt" type="datetime-local" class="form-control" name="modifiedAt" :class="{'valid': !$v.cmsTestimonials.modifiedAt.$invalid, 'invalid': $v.cmsTestimonials.modifiedAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsTestimonials.modifiedAt.$model)"
                            @change="updateInstantField('modifiedAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsTestimonials.title')" for="cms-testimonials-title">Title</label>
                        <input type="text" class="form-control" name="title" id="cms-testimonials-title"
                            :class="{'valid': !$v.cmsTestimonials.title.$invalid, 'invalid': $v.cmsTestimonials.title.$invalid }" v-model="$v.cmsTestimonials.title.$model"  required/>
                        <div v-if="$v.cmsTestimonials.title.$anyDirty && $v.cmsTestimonials.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsTestimonials.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsTestimonials.content')" for="cms-testimonials-content">Content</label>
                        <input type="text" class="form-control" name="content" id="cms-testimonials-content"
                            :class="{'valid': !$v.cmsTestimonials.content.$invalid, 'invalid': $v.cmsTestimonials.content.$invalid }" v-model="$v.cmsTestimonials.content.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsTestimonials.createdBy')" for="cms-testimonials-createdBy">Created By</label>
                        <select class="form-control" id="cms-testimonials-createdBy" name="createdBy" v-model="cmsTestimonials.createdBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsTestimonials.createdBy && portalUserOption.id === cmsTestimonials.createdBy.id ? cmsTestimonials.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsTestimonials.modifiedBy')" for="cms-testimonials-modifiedBy">Modified By</label>
                        <select class="form-control" id="cms-testimonials-modifiedBy" name="modifiedBy" v-model="cmsTestimonials.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsTestimonials.modifiedBy && portalUserOption.id === cmsTestimonials.modifiedBy.id ? cmsTestimonials.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsTestimonials.image')" for="cms-testimonials-image">Image</label>
                        <select class="form-control" id="cms-testimonials-image" name="image" v-model="cmsTestimonials.image">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsTestimonials.image && documentOption.id === cmsTestimonials.image.id ? cmsTestimonials.image : documentOption" v-for="documentOption in documents" :key="documentOption.id">{{documentOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsTestimonials.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cms-testimonials-update.component.ts">
</script>
