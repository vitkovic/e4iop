<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementType">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementType.detail.title')">AdvertisementType</span> {{advertisementType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementType.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{advertisementType.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementType.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{advertisementType.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementType.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementType.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementType.id" :to="{name: 'AdvertisementTypeEdit', params: {advertisementTypeId: advertisementType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-type-details.component.ts">
</script>
