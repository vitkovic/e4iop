<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.requestAnswer.home.createOrEditLabel" v-text="$t('riportalApp.requestAnswer.home.createOrEditLabel')">Create or edit a RequestAnswer</h2>
                <div>
                    <div class="form-group" v-if="requestAnswer.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="requestAnswer.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.requestAnswer.answer')" for="request-answer-answer">Answer</label>
                        <input type="text" class="form-control" name="answer" id="request-answer-answer"
                            :class="{'valid': !$v.requestAnswer.answer.$invalid, 'invalid': $v.requestAnswer.answer.$invalid }" v-model="$v.requestAnswer.answer.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.requestAnswer.answerEn')" for="request-answer-answerEn">Answer</label>
                        <input type="text" class="form-control" name="answerEn" id="request-answer-answerEn"
                            :class="{'valid': !$v.requestAnswer.answerEn.$invalid, 'invalid': $v.requestAnswer.answerEn.$invalid }" v-model="$v.requestAnswer.answerEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.requestAnswer.answerCyr')" for="request-answer-answerCyr">Answer</label>
                        <input type="text" class="form-control" name="answerCyr" id="request-answer-answerCyr"
                            :class="{'valid': !$v.requestAnswer.answerCyr.$invalid, 'invalid': $v.requestAnswer.answerCyr.$invalid }" v-model="$v.requestAnswer.answerCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.requestAnswer.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./request-answer-update.component.ts">
</script>
