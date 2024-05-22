<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="puoLegalStatus">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.puoLegalStatus.detail.title')">PuoLegalStatus</span> {{puoLegalStatus.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.puoLegalStatus.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{puoLegalStatus.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.puoLegalStatus.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{puoLegalStatus.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.puoLegalStatus.descriptionEn')">Description En</span>
                    </dt>
                    <dd>
                        <span>{{puoLegalStatus.descriptionEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.puoLegalStatus.descriptionCyr')">Description Cyr</span>
                    </dt>
                    <dd>
                        <span>{{puoLegalStatus.descriptionCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="puoLegalStatus.id" :to="{name: 'PuoLegalStatusEdit', params: {puoLegalStatusId: puoLegalStatus.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./puo-legal-status-details.component.ts">
</script>
