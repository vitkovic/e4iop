<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cmsCarousel">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.cmsCarousel.detail.title')">CmsCarousel</span> {{cmsCarousel.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.createdAt')">Created At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsCarousel.createdAt">{{$d(Date.parse(cmsCarousel.createdAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.modifiedAt')">Modified At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsCarousel.modifiedAt">{{$d(Date.parse(cmsCarousel.modifiedAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{cmsCarousel.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.link')">Link</span>
                    </dt>
                    <dd>
                        <span>{{cmsCarousel.link}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.newTab')">New Tab</span>
                    </dt>
                    <dd>
                        <span>{{cmsCarousel.newTab}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.ordinalNumber')">Ordinal Number</span>
                    </dt>
                    <dd>
                        <span>{{cmsCarousel.ordinalNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsCarousel.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsCarousel.createdBy.id}}">{{cmsCarousel.createdBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.modifiedBy')">Modified By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsCarousel.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsCarousel.modifiedBy.id}}">{{cmsCarousel.modifiedBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsCarousel.image')">Image</span>
                    </dt>
                    <dd>
                        <div v-if="cmsCarousel.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: cmsCarousel.image.id}}">{{cmsCarousel.image.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cmsCarousel.id" :to="{name: 'CmsCarouselEdit', params: {cmsCarouselId: cmsCarousel.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-carousel-details.component.ts">
</script>
