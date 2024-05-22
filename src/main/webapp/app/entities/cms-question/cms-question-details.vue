<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cmsQuestion">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.cmsQuestion.detail.title')">CmsQuestion</span> {{cmsQuestion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.cmsQuestion.createdAt')">Created At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsQuestion.createdAt">{{$d(Date.parse(cmsQuestion.createdAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsQuestion.modifiedAt')">Modified At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsQuestion.modifiedAt">{{$d(Date.parse(cmsQuestion.modifiedAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsQuestion.question')">Question</span>
                    </dt>
                    <dd>
                        <span>{{cmsQuestion.question}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsQuestion.answer')">Answer</span>
                    </dt>
                    <dd>
                        <span>{{cmsQuestion.answer}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsQuestion.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsQuestion.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsQuestion.createdBy.id}}">{{cmsQuestion.createdBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsQuestion.modifiedBy')">Modified By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsQuestion.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsQuestion.modifiedBy.id}}">{{cmsQuestion.modifiedBy.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cmsQuestion.id" :to="{name: 'CmsQuestionEdit', params: {cmsQuestionId: cmsQuestion.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-question-details.component.ts">
</script>
