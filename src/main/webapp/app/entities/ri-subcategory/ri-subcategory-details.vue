<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riSubcategory">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riSubcategory.detail.title')">RiSubcategory</span> {{riSubcategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riSubcategory.subcategory')">Subcategory</span>
                    </dt>
                    <dd>
                        <span>{{riSubcategory.subcategory}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riSubcategory.subcategoryEn')">Subcategory En</span>
                    </dt>
                    <dd>
                        <span>{{riSubcategory.subcategoryEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riSubcategory.subCategoryCyr')">Sub Category Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riSubcategory.subCategoryCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riSubcategory.id" :to="{name: 'RiSubcategoryEdit', params: {riSubcategoryId: riSubcategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-subcategory-details.component.ts">
</script>
