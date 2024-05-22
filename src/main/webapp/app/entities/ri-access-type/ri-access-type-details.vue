<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riAccessType">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riAccessType.detail.title')">RiAccessType</span> {{riAccessType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riAccessType.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{riAccessType.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riAccessType.typeEn')">Type En</span>
                    </dt>
                    <dd>
                        <span>{{riAccessType.typeEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riAccessType.typeCyr')">Type Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riAccessType.typeCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riAccessType.id" :to="{name: 'RiAccessTypeEdit', params: {riAccessTypeId: riAccessType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-access-type-details.component.ts">
</script>
