<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riServiceStatus">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riServiceStatus.detail.title')">RiServiceStatus</span> {{riServiceStatus.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riServiceStatus.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{riServiceStatus.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riServiceStatus.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{riServiceStatus.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riServiceStatus.statusEn')">Status En</span>
                    </dt>
                    <dd>
                        <span>{{riServiceStatus.statusEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riServiceStatus.statusCyr')">Status Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riServiceStatus.statusCyr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riServiceStatus.descriptionEn')">Description En</span>
                    </dt>
                    <dd>
                        <span>{{riServiceStatus.descriptionEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riServiceStatus.descriptionCyr')">Description Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riServiceStatus.descriptionCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riServiceStatus.id" :to="{name: 'RiServiceStatusEdit', params: {riServiceStatusId: riServiceStatus.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-service-status-details.component.ts">
</script>
