<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="requestAnswer">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.requestAnswer.detail.title')">RequestAnswer</span> {{requestAnswer.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.requestAnswer.answer')">Answer</span>
                    </dt>
                    <dd>
                        <span>{{requestAnswer.answer}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="requestAnswer.id" :to="{name: 'RequestAnswerEdit', params: {requestAnswerId: requestAnswer.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./request-answer-details.component.ts">
</script>
