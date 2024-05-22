<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementCategory">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementCategory.detail.title')">AdvertisementCategory</span> {{advertisementCategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementCategory.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{advertisementCategory.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementCategory.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{advertisementCategory.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementCategory.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementCategory.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementCategory.id" :to="{name: 'AdvertisementCategoryEdit', params: {advertisementCategoryId: advertisementCategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-category-details.component.ts">
</script>
