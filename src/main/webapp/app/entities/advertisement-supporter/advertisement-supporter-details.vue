<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="advertisementSupporter">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.advertisementSupporter.detail.title')">AdvertisementSupporter</span> {{advertisementSupporter.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporter.hasAccepted')">Has Accepted</span>
                    </dt>
                    <dd>
                        <span>{{advertisementSupporter.hasAccepted}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporter.advertisement')">Advertisement</span>
                    </dt>
                    <dd>
                        <div v-if="advertisementSupporter.advertisement">
                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisementSupporter.advertisement.id}}">{{advertisementSupporter.advertisement.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.advertisementSupporter.company')">Company</span>
                    </dt>
                    <dd>
                        <div v-if="advertisementSupporter.company">
                            <router-link :to="{name: 'CompanyView', params: {companyId: advertisementSupporter.company.id}}">{{advertisementSupporter.company.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="advertisementSupporter.id" :to="{name: 'AdvertisementSupporterEdit', params: {advertisementSupporterId: advertisementSupporter.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./advertisement-supporter-details.component.ts">
</script>
