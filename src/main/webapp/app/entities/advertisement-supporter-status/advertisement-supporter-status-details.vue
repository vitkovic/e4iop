<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementSupporterStatus">
                <h2 class="jh-entity-heading">
                    <span v-text="$t('riportalApp.advertisementSupporterStatus.detail.title')">AdvertisementSupporterStatus</span> {{ advertisementSupporterStatus.id }}
                </h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporterStatus.nameSr')">NameSr</span>
                    </dt>
                    <dd>
                        <span>{{ advertisementSupporterStatus.nameSr }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporterStatus.nameSrc')">NameSrc</span>
                    </dt>
                    <dd>
                        <span>{{ advertisementSupporterStatus.nameSrc }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporterStatus.nameEn')">NameEn</span>
                    </dt>
                    <dd>
                        <span>{{ advertisementSupporterStatus.nameEn }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporterStatus.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{ advertisementSupporterStatus.isVisible }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporterStatus.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{ advertisementSupporterStatus.isDeleted }}</span>
                    </dd>
                </dl>
                <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementSupporterStatus.id" :to="{ name: 'AdvertisementSupporterStatusEdit', params: { advertisementSupporterStatusId: advertisementSupporterStatus.id }}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-supporter-status-details.component.ts"></script>
