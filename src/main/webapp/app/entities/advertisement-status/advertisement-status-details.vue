<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementStatus">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementStatus.detail.title')">AdvertisementStatus</span> {{advertisementStatus.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementStatus.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{advertisementStatus.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementStatus.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{advertisementStatus.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementStatus.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementStatus.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementStatus.id" :to="{name: 'AdvertisementStatusEdit', params: {advertisementStatusId: advertisementStatus.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-status-details.component.ts">
</script>
