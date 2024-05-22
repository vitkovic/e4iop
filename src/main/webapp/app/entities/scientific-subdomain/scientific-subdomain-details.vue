<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="scientificSubdomain">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.scientificSubdomain.detail.title')">ScientificSubdomain</span> {{scientificSubdomain.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.scientificSubdomain.subdomain')">Subdomain</span>
                    </dt>
                    <dd>
                        <span>{{scientificSubdomain.subdomain}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.scientificSubdomain.subdomainEn')">Subdomain En</span>
                    </dt>
                    <dd>
                        <span>{{scientificSubdomain.subdomainEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.scientificSubdomain.subdomainCyr')">Subdomain Cyr</span>
                    </dt>
                    <dd>
                        <span>{{scientificSubdomain.subdomainCyr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.scientificSubdomain.domain')">Domain</span>
                    </dt>
                    <dd>
                        <div v-if="scientificSubdomain.domain">
                            <router-link :to="{name: 'ScientificDomainView', params: {scientificDomainId: scientificSubdomain.domain.id}}">{{scientificSubdomain.domain.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="scientificSubdomain.id" :to="{name: 'ScientificSubdomainEdit', params: {scientificSubdomainId: scientificSubdomain.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./scientific-subdomain-details.component.ts">
</script>
