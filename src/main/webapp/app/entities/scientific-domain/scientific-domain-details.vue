<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="scientificDomain">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.scientificDomain.detail.title')">ScientificDomain</span> {{scientificDomain.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.scientificDomain.domain')">Domain</span>
                    </dt>
                    <dd>
                        <span>{{scientificDomain.domain}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.scientificDomain.domainEn')">Domain En</span>
                    </dt>
                    <dd>
                        <span>{{scientificDomain.domainEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.scientificDomain.domainCyr')">Domain Cyr</span>
                    </dt>
                    <dd>
                        <span>{{scientificDomain.domainCyr}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="scientificDomain.id" :to="{name: 'ScientificDomainEdit', params: {scientificDomainId: scientificDomain.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./scientific-domain-details.component.ts">
</script>
