<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="document">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.document.detail.title')">Document</span> {{document.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.document.filename')">Filename</span>
                    </dt>
                    <dd>
                        <span>{{document.filename}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.document.type')">Type</span>
                    </dt>
                    <dd>
                        <div v-if="document.type">
                            <router-link :to="{name: 'DocumentTypeView', params: {documentTypeId: document.type.id}}">{{document.type.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.document.kind')">Kind</span>
                    </dt>
                    <dd>
                        <div v-if="document.kind">
                            <router-link :to="{name: 'DocumentKindView', params: {documentKindId: document.kind.id}}">{{document.kind.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="document.id" :to="{name: 'DocumentEdit', params: {documentId: document.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./document-details.component.ts">
</script>
