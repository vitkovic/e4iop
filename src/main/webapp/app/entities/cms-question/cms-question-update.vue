<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.cmsQuestion.home.createOrEditLabel" v-text="$t('riportalApp.cmsQuestion.home.createOrEditLabel')">Create or edit a CmsQuestion</h2>
                <div>

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
                        <b-textarea type="text" class="form-control" name="answer" id="cms-question-answer" rows="10"
                            :class="{'valid': !$v.cmsQuestion.answer.$invalid, 'invalid': $v.cmsQuestion.answer.$invalid }" v-model="$v.cmsQuestion.answer.$model"  required>
                        </b-textarea>
                        <div v-if="$v.cmsQuestion.answer.$anyDirty && $v.cmsQuestion.answer.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsQuestion.answer.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
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
