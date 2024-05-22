<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementSubcategory">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementSubcategory.detail.title')">AdvertisementSubcategory</span> {{advertisementSubcategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubcategory.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{advertisementSubcategory.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubcategory.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{advertisementSubcategory.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubcategory.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementSubcategory.isDeleted}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubcategory.advertisementCategory')">Advertisement Category</span>
                    </dt>
                    <dd>
                        <div v-if="advertisementSubcategory.advertisementCategory">
                            <router-link :to="{name: 'AdvertisementCategoryView', params: {advertisementCategoryId: advertisementSubcategory.advertisementCategory.id}}">{{advertisementSubcategory.advertisementCategory.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementSubcategory.id" :to="{name: 'AdvertisementSubcategoryEdit', params: {advertisementSubcategoryId: advertisementSubcategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-subcategory-details.component.ts">
</script>
