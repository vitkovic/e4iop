<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementDuration">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementDuration.detail.title')">AdvertisementDuration</span> {{advertisementDuration.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementDuration.duration')">Duration</span>
                    </dt>
                    <dd>
                        <span>{{advertisementDuration.duration}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementDuration.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{advertisementDuration.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementDuration.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementDuration.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementDuration.id" :to="{name: 'AdvertisementDurationEdit', params: {advertisementDurationId: advertisementDuration.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-duration-details.component.ts">
</script>
