<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementSubsubcategory">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementSubsubcategory.detail.title')">AdvertisementSubsubcategory</span> {{advertisementSubsubcategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubsubcategory.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{advertisementSubsubcategory.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubsubcategory.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{advertisementSubsubcategory.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubsubcategory.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementSubsubcategory.isDeleted}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSubsubcategory.advertisementSubcategory')">Advertisement Subcategory</span>
                    </dt>
                    <dd>
                        <div v-if="advertisementSubsubcategory.advertisementSubcategory">
                            <router-link :to="{name: 'AdvertisementSubcategoryView', params: {advertisementSubcategoryId: advertisementSubsubcategory.advertisementSubcategory.id}}">{{advertisementSubsubcategory.advertisementSubcategory.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementSubsubcategory.id" :to="{name: 'AdvertisementSubsubcategoryEdit', params: {advertisementSubsubcategoryId: advertisementSubsubcategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-subsubcategory-details.component.ts">
</script>
