<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsPage.home.createOrEditLabel" v-text="$t('riportalApp.cmsPage.home.createOrEditLabel')">Create or edit a CmsPage</h2>
                <div>
                    <div class="form-group" v-if="cmsPage.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsPage.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.createdAt')" for="cms-page-createdAt">Created At</label>
                        <div class="d-flex">
                            <input id="cms-page-createdAt" type="datetime-local" class="form-control" name="createdAt" :class="{'valid': !$v.cmsPage.createdAt.$invalid, 'invalid': $v.cmsPage.createdAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsPage.createdAt.$model)"
                            @change="updateInstantField('createdAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.modifiedAt')" for="cms-page-modifiedAt">Modified At</label>
                        <div class="d-flex">
                            <input id="cms-page-modifiedAt" type="datetime-local" class="form-control" name="modifiedAt" :class="{'valid': !$v.cmsPage.modifiedAt.$invalid, 'invalid': $v.cmsPage.modifiedAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsPage.modifiedAt.$model)"
                            @change="updateInstantField('modifiedAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.title')" for="cms-page-title">Title</label>
                        <input type="text" class="form-control" name="title" id="cms-page-title"
                            :class="{'valid': !$v.cmsPage.title.$invalid, 'invalid': $v.cmsPage.title.$invalid }" v-model="$v.cmsPage.title.$model"  required/>
                        <div v-if="$v.cmsPage.title.$anyDirty && $v.cmsPage.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsPage.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.content')" for="cms-page-content">Content</label>
                        <input type="text" class="form-control" name="content" id="cms-page-content"
                            :class="{'valid': !$v.cmsPage.content.$invalid, 'invalid': $v.cmsPage.content.$invalid }" v-model="$v.cmsPage.content.$model" />
                    </div>
                    <div class="form-group">
                        <label v-text="$t('riportalApp.cmsPage.document')" for="cms-page-document">Document</label>
                        <select class="form-control" id="cms-page-document" multiple name="document" v-model="cmsPage.documents">
                            <option v-bind:value="getSelected(cmsPage.documents, documentOption)" v-for="documentOption in documents" :key="documentOption.id">{{documentOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.type')" for="cms-page-type">Type</label>
                        <select class="form-control" id="cms-page-type" name="type" v-model="cmsPage.type">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsPage.type && cmsPageTypeOption.id === cmsPage.type.id ? cmsPage.type : cmsPageTypeOption" v-for="cmsPageTypeOption in cmsPageTypes" :key="cmsPageTypeOption.id">{{cmsPageTypeOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.createdBy')" for="cms-page-createdBy">Created By</label>
                        <select class="form-control" id="cms-page-createdBy" name="createdBy" v-model="cmsPage.createdBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsPage.createdBy && portalUserOption.id === cmsPage.createdBy.id ? cmsPage.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsPage.modifiedBy')" for="cms-page-modifiedBy">Modified By</label>
                        <select class="form-control" id="cms-page-modifiedBy" name="modifiedBy" v-model="cmsPage.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsPage.modifiedBy && portalUserOption.id === cmsPage.modifiedBy.id ? cmsPage.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsPage.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cms-page-update.component.ts">
</script>
