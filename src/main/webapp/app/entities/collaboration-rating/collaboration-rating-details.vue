<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="collaborationRating">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.collaborationRating.detail.title')">CollaborationRating</span> {{collaborationRating.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.collaborationRating.number')">Number</span>
                    </dt>
                    <dd>
                        <span>{{collaborationRating.number}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaborationRating.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{collaborationRating.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaborationRating.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{collaborationRating.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaborationRating.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{collaborationRating.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="collaborationRating.id" :to="{name: 'CollaborationRatingEdit', params: {collaborationRatingId: collaborationRating.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./collaboration-rating-details.component.ts">
</script>
