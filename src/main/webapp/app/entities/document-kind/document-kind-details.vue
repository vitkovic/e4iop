<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documentKind">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.documentKind.detail.title')">DocumentKind</span> {{documentKind.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.documentKind.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{documentKind.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.documentKind.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{documentKind.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.documentKind.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{documentKind.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documentKind.id" :to="{name: 'DocumentKindEdit', params: {documentKindId: documentKind.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./document-kind-details.component.ts">
</script>
