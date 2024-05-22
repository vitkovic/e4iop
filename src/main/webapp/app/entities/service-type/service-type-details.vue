<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="serviceType">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.serviceType.detail.title')">ServiceType</span> {{serviceType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.serviceType.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{serviceType.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceType.typeEn')">Type En</span>
                    </dt>
                    <dd>
                        <span>{{serviceType.typeEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceType.typeCyr')">Type Cyr</span>
                    </dt>
                    <dd>
                        <span>{{serviceType.typeCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="serviceType.id" :to="{name: 'ServiceTypeEdit', params: {serviceTypeId: serviceType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./service-type-details.component.ts">
</script>
