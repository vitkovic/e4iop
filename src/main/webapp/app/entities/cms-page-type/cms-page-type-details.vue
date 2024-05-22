<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cmsPageType">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.cmsPageType.detail.title')">CmsPageType</span> {{cmsPageType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.cmsPageType.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{cmsPageType.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPageType.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{cmsPageType.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPageType.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{cmsPageType.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cmsPageType.id" :to="{name: 'CmsPageTypeEdit', params: {cmsPageTypeId: cmsPageType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-page-type-details.component.ts">
</script>
