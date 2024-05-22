<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementKind">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementKind.detail.title')">AdvertisementKind</span> {{advertisementKind.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementKind.kind')">Kind</span>
                    </dt>
                    <dd>
                        <span>{{advertisementKind.kind}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementKind.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{advertisementKind.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementKind.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementKind.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementKind.id" :to="{name: 'AdvertisementKindEdit', params: {advertisementKindId: advertisementKind.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-kind-details.component.ts">
</script>
