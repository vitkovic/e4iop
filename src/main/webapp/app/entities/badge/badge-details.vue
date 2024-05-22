<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="badge">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.badge.detail.title')">Badge</span> {{badge.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.badge.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{badge.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.badge.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{badge.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.badge.image')">Image</span>
                    </dt>
                    <dd>
                        <div v-if="badge.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: badge.image.id}}">{{badge.image.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="badge.id" :to="{name: 'BadgeEdit', params: {badgeId: badge.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./badge-details.component.ts">
</script>
