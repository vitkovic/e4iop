<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riCategory">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riCategory.detail.title')">RiCategory</span> {{riCategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riCategory.category')">Category</span>
                    </dt>
                    <dd>
                        <span>{{riCategory.category}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCategory.categoryEn')">Category En</span>
                    </dt>
                    <dd>
                        <span>{{riCategory.categoryEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riCategory.categoryCyr')">Category Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riCategory.categoryCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riCategory.id" :to="{name: 'RiCategoryEdit', params: {riCategoryId: riCategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-category-details.component.ts">
</script>
