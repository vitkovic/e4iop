<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsCarousel.home.createOrEditLabel" v-text="$t('riportalApp.cmsCarousel.home.createOrEditLabel')">Create or edit a CmsCarousel</h2>
                <div>
                    <div class="form-group" v-if="cmsCarousel.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsCarousel.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.createdAt')" for="cms-carousel-createdAt">Created At</label>
                        <div class="d-flex">
                            <input id="cms-carousel-createdAt" type="datetime-local" class="form-control" name="createdAt" :class="{'valid': !$v.cmsCarousel.createdAt.$invalid, 'invalid': $v.cmsCarousel.createdAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsCarousel.createdAt.$model)"
                            @change="updateInstantField('createdAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.modifiedAt')" for="cms-carousel-modifiedAt">Modified At</label>
                        <div class="d-flex">
                            <input id="cms-carousel-modifiedAt" type="datetime-local" class="form-control" name="modifiedAt" :class="{'valid': !$v.cmsCarousel.modifiedAt.$invalid, 'invalid': $v.cmsCarousel.modifiedAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsCarousel.modifiedAt.$model)"
                            @change="updateInstantField('modifiedAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.title')" for="cms-carousel-title">Title</label>
                        <input type="text" class="form-control" name="title" id="cms-carousel-title"
                            :class="{'valid': !$v.cmsCarousel.title.$invalid, 'invalid': $v.cmsCarousel.title.$invalid }" v-model="$v.cmsCarousel.title.$model"  required/>
                        <div v-if="$v.cmsCarousel.title.$anyDirty && $v.cmsCarousel.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsCarousel.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.link')" for="cms-carousel-link">Link</label>
                        <input type="text" class="form-control" name="link" id="cms-carousel-link"
                            :class="{'valid': !$v.cmsCarousel.link.$invalid, 'invalid': $v.cmsCarousel.link.$invalid }" v-model="$v.cmsCarousel.link.$model"  required/>
                        <div v-if="$v.cmsCarousel.link.$anyDirty && $v.cmsCarousel.link.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsCarousel.link.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.newTab')" for="cms-carousel-newTab">New Tab</label>
                        <input type="checkbox" class="form-check" name="newTab" id="cms-carousel-newTab"
                            :class="{'valid': !$v.cmsCarousel.newTab.$invalid, 'invalid': $v.cmsCarousel.newTab.$invalid }" v-model="$v.cmsCarousel.newTab.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.ordinalNumber')" for="cms-carousel-ordinalNumber">Ordinal Number</label>
                        <input type="number" class="form-control" name="ordinalNumber" id="cms-carousel-ordinalNumber"
                            :class="{'valid': !$v.cmsCarousel.ordinalNumber.$invalid, 'invalid': $v.cmsCarousel.ordinalNumber.$invalid }" v-model.number="$v.cmsCarousel.ordinalNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.createdBy')" for="cms-carousel-createdBy">Created By</label>
                        <select class="form-control" id="cms-carousel-createdBy" name="createdBy" v-model="cmsCarousel.createdBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsCarousel.createdBy && portalUserOption.id === cmsCarousel.createdBy.id ? cmsCarousel.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.modifiedBy')" for="cms-carousel-modifiedBy">Modified By</label>
                        <select class="form-control" id="cms-carousel-modifiedBy" name="modifiedBy" v-model="cmsCarousel.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsCarousel.modifiedBy && portalUserOption.id === cmsCarousel.modifiedBy.id ? cmsCarousel.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsCarousel.image')" for="cms-carousel-image">Image</label>
                        <select class="form-control" id="cms-carousel-image" name="image" v-model="cmsCarousel.image">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsCarousel.image && documentOption.id === cmsCarousel.image.id ? cmsCarousel.image : documentOption" v-for="documentOption in documents" :key="documentOption.id">{{documentOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsCarousel.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cms-carousel-update.component.ts">
</script>
