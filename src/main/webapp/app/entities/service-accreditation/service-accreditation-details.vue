<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="serviceAccreditation">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.serviceAccreditation.detail.title')">ServiceAccreditation</span> {{serviceAccreditation.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.serviceAccreditation.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{serviceAccreditation.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAccreditation.meaning')">Meaning</span>
                    </dt>
                    <dd>
                        <span>{{serviceAccreditation.meaning}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAccreditation.statusEn')">Status En</span>
                    </dt>
                    <dd>
                        <span>{{serviceAccreditation.statusEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAccreditation.statusCyr')">Status Cyr</span>
                    </dt>
                    <dd>
                        <span>{{serviceAccreditation.statusCyr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAccreditation.meaningEn')">Meaning En</span>
                    </dt>
                    <dd>
                        <span>{{serviceAccreditation.meaningEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceAccreditation.meaningCyr')">Meaning Cyr</span>
                    </dt>
                    <dd>
                        <span>{{serviceAccreditation.meaningCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="serviceAccreditation.id" :to="{name: 'ServiceAccreditationEdit', params: {serviceAccreditationId: serviceAccreditation.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./service-accreditation-details.component.ts">
</script>
