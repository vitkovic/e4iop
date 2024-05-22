<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cmsPage">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.cmsPage.detail.title')">CmsPage</span> {{cmsPage.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.createdAt')">Created At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsPage.createdAt">{{$d(Date.parse(cmsPage.createdAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.modifiedAt')">Modified At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsPage.modifiedAt">{{$d(Date.parse(cmsPage.modifiedAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{cmsPage.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.content')">Content</span>
                    </dt>
                    <dd>
                        <span>{{cmsPage.content}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.document')">Document</span>
                    </dt>
                    <dd>
                        <span v-for="(document, i) in cmsPage.documents" :key="document.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'DocumentView', params: {documentId: document.id}}">{{document.id}}</router-link>
                        </span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.type')">Type</span>
                    </dt>
                    <dd>
                        <div v-if="cmsPage.type">
                            <router-link :to="{name: 'CmsPageTypeView', params: {cmsPageTypeId: cmsPage.type.id}}">{{cmsPage.type.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsPage.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsPage.createdBy.id}}">{{cmsPage.createdBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsPage.modifiedBy')">Modified By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsPage.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsPage.modifiedBy.id}}">{{cmsPage.modifiedBy.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cmsPage.id" :to="{name: 'CmsPageEdit', params: {cmsPageId: cmsPage.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-page-details.component.ts">
</script>
