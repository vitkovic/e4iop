<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riAccessMode">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riAccessMode.detail.title')">RiAccessMode</span> {{riAccessMode.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riAccessMode.mode')">Mode</span>
                    </dt>
                    <dd>
                        <span>{{riAccessMode.mode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riAccessMode.modeEn')">Mode En</span>
                    </dt>
                    <dd>
                        <span>{{riAccessMode.modeEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riAccessMode.modeCyr')">Mode Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riAccessMode.modeCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riAccessMode.id" :to="{name: 'RiAccessModeEdit', params: {riAccessModeId: riAccessMode.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-access-mode-details.component.ts">
</script>
