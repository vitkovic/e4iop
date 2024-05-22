<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="serviceSubtype">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.serviceSubtype.detail.title')">ServiceSubtype</span> {{serviceSubtype.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.serviceSubtype.subtype')">Subtype</span>
                    </dt>
                    <dd>
                        <span>{{serviceSubtype.subtype}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceSubtype.subtypeEn')">Subtype En</span>
                    </dt>
                    <dd>
                        <span>{{serviceSubtype.subtypeEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceSubtype.subtypeCyr')">Subtype Cyr</span>
                    </dt>
                    <dd>
                        <span>{{serviceSubtype.subtypeCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="serviceSubtype.id" :to="{name: 'ServiceSubtypeEdit', params: {serviceSubtypeId: serviceSubtype.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./service-subtype-details.component.ts">
</script>
