<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cmsSlider">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.cmsSlider.detail.title')">CmsSlider</span> {{cmsSlider.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.cmsSlider.createdAt')">Created At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsSlider.createdAt">{{$d(Date.parse(cmsSlider.createdAt), 'long') }}</span>
                    </dd>
                    <!-- <dt>
                        <span v-text="$t('riportalApp.cmsSlider.modifiedAt')">Modified At</span>
                    </dt>
                    <dd>
                        <span v-if="cmsSlider.modifiedAt">{{$d(Date.parse(cmsSlider.modifiedAt), 'long') }}</span>
                    </dd> -->
                    <dt>
                        <span v-text="$t('riportalApp.cmsSlider.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{cmsSlider.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsSlider.link')">Link</span>
                    </dt>
                    <dd>
                        <b-link :href="cmsSlider.link" :target="cmsSlider.newTab ? '_blank' : '_self'">{{ cmsSlider.link }}</b-link>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsSlider.newTab')">New Tab</span>
                    </dt>
                    <dd>
                        <span>{{cmsSlider.newTab}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsSlider.ordinalNumber')">Ordinal Number</span>
                    </dt>
                    <dd>
                        <span>{{cmsSlider.ordinalNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.cmsSlider.image')">Image</span>
                    </dt>
                    <dd>
                        <div v-if="cmsSlider.image">
                            <img :src="retrieveFile(cmsSlider.image)" width="50" height="50" />
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cmsSlider.id" :to="{name: 'CmsSliderEdit', params: {cmsSliderId: cmsSlider.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-slider-details.component.ts">
</script>
