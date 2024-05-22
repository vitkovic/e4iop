<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riStatus">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riStatus.detail.title')">RiStatus</span> {{riStatus.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riStatus.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{riStatus.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riStatus.meaning')">Meaning</span>
                    </dt>
                    <dd>
                        <span>{{riStatus.meaning}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riStatus.statusEn')">Status En</span>
                    </dt>
                    <dd>
                        <span>{{riStatus.statusEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riStatus.statusCyr')">Status Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riStatus.statusCyr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riStatus.meaningEn')">Meaning En</span>
                    </dt>
                    <dd>
                        <span>{{riStatus.meaningEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riStatus.meaningCyr')">Meaning Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riStatus.meaningCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riStatus.id" :to="{name: 'RiStatusEdit', params: {riStatusId: riStatus.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-status-details.component.ts">
</script>
