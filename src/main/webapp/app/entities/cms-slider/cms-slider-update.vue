<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsSlider.home.createOrEditLabel" v-text="$t('riportalApp.cmsSlider.home.createOrEditLabel')">Create or edit a CmsSlider</h2>
                <div>
                    <div class="form-group" v-if="cmsSlider.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsSlider.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.createdAt')" for="cms-slider-createdAt">Created At</label>
                        <div class="d-flex">
                            <input id="cms-slider-createdAt" type="datetime-local" class="form-control" name="createdAt" :class="{'valid': !$v.cmsSlider.createdAt.$invalid, 'invalid': $v.cmsSlider.createdAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsSlider.createdAt.$model)"
                            @change="updateInstantField('createdAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.modifiedAt')" for="cms-slider-modifiedAt">Modified At</label>
                        <div class="d-flex">
                            <input id="cms-slider-modifiedAt" type="datetime-local" class="form-control" name="modifiedAt" :class="{'valid': !$v.cmsSlider.modifiedAt.$invalid, 'invalid': $v.cmsSlider.modifiedAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsSlider.modifiedAt.$model)"
                            @change="updateInstantField('modifiedAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.title')" for="cms-slider-title">Title</label>
                        <input type="text" class="form-control" name="title" id="cms-slider-title"
                            :class="{'valid': !$v.cmsSlider.title.$invalid, 'invalid': $v.cmsSlider.title.$invalid }" v-model="$v.cmsSlider.title.$model"  required/>
                        <div v-if="$v.cmsSlider.title.$anyDirty && $v.cmsSlider.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsSlider.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.link')" for="cms-slider-link">Link</label>
                        <input type="text" class="form-control" name="link" id="cms-slider-link"
                            :class="{'valid': !$v.cmsSlider.link.$invalid, 'invalid': $v.cmsSlider.link.$invalid }" v-model="$v.cmsSlider.link.$model"  required/>
                        <div v-if="$v.cmsSlider.link.$anyDirty && $v.cmsSlider.link.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsSlider.link.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.newTab')" for="cms-slider-newTab">New Tab</label>
                        <input type="checkbox" class="form-check" name="newTab" id="cms-slider-newTab"
                            :class="{'valid': !$v.cmsSlider.newTab.$invalid, 'invalid': $v.cmsSlider.newTab.$invalid }" v-model="$v.cmsSlider.newTab.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.ordinalNumber')" for="cms-slider-ordinalNumber">Ordinal Number</label>
                        <input type="number" class="form-control" name="ordinalNumber" id="cms-slider-ordinalNumber"
                            :class="{'valid': !$v.cmsSlider.ordinalNumber.$invalid, 'invalid': $v.cmsSlider.ordinalNumber.$invalid }" v-model.number="$v.cmsSlider.ordinalNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.createdBy')" for="cms-slider-createdBy">Created By</label>
                        <select class="form-control" id="cms-slider-createdBy" name="createdBy" v-model="cmsSlider.createdBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsSlider.createdBy && portalUserOption.id === cmsSlider.createdBy.id ? cmsSlider.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.modifiedBy')" for="cms-slider-modifiedBy">Modified By</label>
                        <select class="form-control" id="cms-slider-modifiedBy" name="modifiedBy" v-model="cmsSlider.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsSlider.modifiedBy && portalUserOption.id === cmsSlider.modifiedBy.id ? cmsSlider.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.image')" for="cms-slider-image">Image</label>
                        <select class="form-control" id="cms-slider-image" name="image" v-model="cmsSlider.image">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsSlider.image && documentOption.id === cmsSlider.image.id ? cmsSlider.image : documentOption" v-for="documentOption in documents" :key="documentOption.id">{{documentOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsSlider.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cms-slider-update.component.ts">
</script>
