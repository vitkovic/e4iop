<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="serviceAvailability">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.serviceAvailability.detail.title')">ServiceAvailability</span> {{serviceAvailability.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.serviceAvailability.availability')">Availability</span>
                    </dt>
                    <dd>
                        <span>{{serviceAvailability.availability}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAvailability.meaning')">Meaning</span>
                    </dt>
                    <dd>
                        <span>{{serviceAvailability.meaning}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAvailability.availabilityEn')">Availability En</span>
                    </dt>
                    <dd>
                        <span>{{serviceAvailability.availabilityEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAvailability.availabilityCyr')">Availability Cyr</span>
                    </dt>
                    <dd>
                        <span>{{serviceAvailability.availabilityCyr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAvailability.meaningEn')">Meaning En</span>
                    </dt>
                    <dd>
                        <span>{{serviceAvailability.meaningEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAvailability.meaningCyr')">Meaning Cyr</span>
                    </dt>
                    <dd>
                        <span>{{serviceAvailability.meaningCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="serviceAvailability.id" :to="{name: 'ServiceAvailabilityEdit', params: {serviceAvailabilityId: serviceAvailability.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./service-availability-details.component.ts">
</script>
