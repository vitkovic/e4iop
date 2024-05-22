<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cmsAdvertisementCategory">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.cmsAdvertisementCategory.detail.title')">CmsAdvertisementCategory</span> {{cmsAdvertisementCategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.cmsAdvertisementCategory.createdAt')">Created At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsAdvertisementCategory.createdAt">{{$d(Date.parse(cmsAdvertisementCategory.createdAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsAdvertisementCategory.modifiedAt')">Modified At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsAdvertisementCategory.modifiedAt">{{$d(Date.parse(cmsAdvertisementCategory.modifiedAt), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsAdvertisementCategory.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsAdvertisementCategory.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsAdvertisementCategory.createdBy.id}}">{{cmsAdvertisementCategory.createdBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsAdvertisementCategory.modifiedBy')">Modified By</span>
                    </dt>
                    <dd>
                        <div v-if="cmsAdvertisementCategory.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsAdvertisementCategory.modifiedBy.id}}">{{cmsAdvertisementCategory.modifiedBy.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsAdvertisementCategory.advertisementCategory')">Advertisement Category</span>
                    </dt>
                    <dd>
                        <div v-if="cmsAdvertisementCategory.advertisementCategory">
                            <router-link :to="{name: 'AdvertisementCategoryView', params: {advertisementCategoryId: cmsAdvertisementCategory.advertisementCategory.id}}">{{cmsAdvertisementCategory.advertisementCategory.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsAdvertisementCategory.image')">Image</span>
                    </dt>
                    <dd>
                        <div v-if="cmsAdvertisementCategory.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: cmsAdvertisementCategory.image.id}}">{{cmsAdvertisementCategory.image.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cmsAdvertisementCategory.id" :to="{name: 'CmsAdvertisementCategoryEdit', params: {cmsAdvertisementCategoryId: cmsAdvertisementCategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-advertisement-category-details.component.ts">
</script>
