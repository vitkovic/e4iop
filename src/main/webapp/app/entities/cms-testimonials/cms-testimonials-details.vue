<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cmsTestimonials">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.cmsTestimonials.detail.title')">CmsTestimonials</span> {{cmsTestimonials.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.cmsTestimonials.createdAt')">Created At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsTestimonials.createdAt">{{$d(Date.parse(cmsTestimonials.createdAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsTestimonials.modifiedAt')">Modified At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsTestimonials.modifiedAt">{{$d(Date.parse(cmsTestimonials.modifiedAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsTestimonials.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{cmsTestimonials.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsTestimonials.content')">Content</span>
                    </dt>
                    <dd>
                        <span>{{cmsTestimonials.content}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsTestimonials.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsTestimonials.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsTestimonials.createdBy.id}}">{{cmsTestimonials.createdBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsTestimonials.modifiedBy')">Modified By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsTestimonials.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsTestimonials.modifiedBy.id}}">{{cmsTestimonials.modifiedBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsTestimonials.image')">Image</span>
                    </dt>
                    <dd>
                        <div v-if="cmsTestimonials.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: cmsTestimonials.image.id}}">{{cmsTestimonials.image.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cmsTestimonials.id" :to="{name: 'CmsTestimonialsEdit', params: {cmsTestimonialsId: cmsTestimonials.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-testimonials-details.component.ts">
</script>
