<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.answerToOffer.home.createOrEditLabel" v-text="$t('riportalApp.answerToOffer.home.createOrEditLabel')">Create or edit a AnswerToOffer</h2>
                <div>
                    <div class="form-group" v-if="answerToOffer.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="answerToOffer.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.answerToOffer.answer')" for="answer-to-offer-answer">Answer</label>
                        <input type="text" class="form-control" name="answer" id="answer-to-offer-answer"
                            :class="{'valid': !$v.answerToOffer.answer.$invalid, 'invalid': $v.answerToOffer.answer.$invalid }" v-model="$v.answerToOffer.answer.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.answerToOffer.answerEn')" for="answer-to-offer-answerEn">Answer</label>
                        <input type="text" class="form-control" name="answerEn" id="answer-to-offer-answerEn"
                            :class="{'valid': !$v.answerToOffer.answerEn.$invalid, 'invalid': $v.answerToOffer.answerEn.$invalid }" v-model="$v.answerToOffer.answerEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.answerToOffer.answerCyr')" for="answer-to-offer-answerCyr">Answer</label>
                        <input type="text" class="form-control" name="answerCyr" id="answer-to-offer-answerCyr"
                            :class="{'valid': !$v.answerToOffer.answerCyr.$invalid, 'invalid': $v.answerToOffer.answerCyr.$invalid }" v-model="$v.answerToOffer.answerCyr.$model" />
                    </div>

                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.answerToOffer.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./answer-to-offer-update.component.ts">
</script>
