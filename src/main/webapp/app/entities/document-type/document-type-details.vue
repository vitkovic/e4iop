<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documentType">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.documentType.detail.title')">DocumentType</span> {{documentType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.documentType.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{documentType.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.documentType.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{documentType.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.documentType.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{documentType.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documentType.id" :to="{name: 'DocumentTypeEdit', params: {documentTypeId: documentType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./document-type-details.component.ts">
</script>
