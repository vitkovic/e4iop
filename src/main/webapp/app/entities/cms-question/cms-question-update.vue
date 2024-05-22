<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsQuestion.home.createOrEditLabel" v-text="$t('riportalApp.cmsQuestion.home.createOrEditLabel')">Create or edit a CmsQuestion</h2>
                <div>
                    <div class="form-group" v-if="cmsQuestion.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsQuestion.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsQuestion.createdAt')" for="cms-question-createdAt">Created At</label>
                        <div class="d-flex">
                            <input id="cms-question-createdAt" type="datetime-local" class="form-control" name="createdAt" :class="{'valid': !$v.cmsQuestion.createdAt.$invalid, 'invalid': $v.cmsQuestion.createdAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsQuestion.createdAt.$model)"
                            @change="updateInstantField('createdAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsQuestion.modifiedAt')" for="cms-question-modifiedAt">Modified At</label>
                        <div class="d-flex">
                            <input id="cms-question-modifiedAt" type="datetime-local" class="form-control" name="modifiedAt" :class="{'valid': !$v.cmsQuestion.modifiedAt.$invalid, 'invalid': $v.cmsQuestion.modifiedAt.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.cmsQuestion.modifiedAt.$model)"
                            @change="updateInstantField('modifiedAt', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsQuestion.question')" for="cms-question-question">Question</label>
                        <input type="text" class="form-control" name="question" id="cms-question-question"
                            :class="{'valid': !$v.cmsQuestion.question.$invalid, 'invalid': $v.cmsQuestion.question.$invalid }" v-model="$v.cmsQuestion.question.$model"  required/>
                        <div v-if="$v.cmsQuestion.question.$anyDirty && $v.cmsQuestion.question.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsQuestion.question.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsQuestion.answer')" for="cms-question-answer">Answer</label>
                        <input type="text" class="form-control" name="answer" id="cms-question-answer"
                            :class="{'valid': !$v.cmsQuestion.answer.$invalid, 'invalid': $v.cmsQuestion.answer.$invalid }" v-model="$v.cmsQuestion.answer.$model"  required/>
                        <div v-if="$v.cmsQuestion.answer.$anyDirty && $v.cmsQuestion.answer.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsQuestion.answer.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsQuestion.createdBy')" for="cms-question-createdBy">Created By</label>
                        <select class="form-control" id="cms-question-createdBy" name="createdBy" v-model="cmsQuestion.createdBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsQuestion.createdBy && portalUserOption.id === cmsQuestion.createdBy.id ? cmsQuestion.createdBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsQuestion.modifiedBy')" for="cms-question-modifiedBy">Modified By</label>
                        <select class="form-control" id="cms-question-modifiedBy" name="modifiedBy" v-model="cmsQuestion.modifiedBy">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cmsQuestion.modifiedBy && portalUserOption.id === cmsQuestion.modifiedBy.id ? cmsQuestion.modifiedBy : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsQuestion.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cms-question-update.component.ts">
</script>
