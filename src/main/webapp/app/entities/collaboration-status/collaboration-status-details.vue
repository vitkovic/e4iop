<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="collaborationStatus">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.collaborationStatus.detail.title')">CollaborationStatus</span> {{collaborationStatus.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.collaborationStatus.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{collaborationStatus.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaborationStatus.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{collaborationStatus.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaborationStatus.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{collaborationStatus.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="collaborationStatus.id" :to="{name: 'CollaborationStatusEdit', params: {collaborationStatusId: collaborationStatus.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./collaboration-status-details.component.ts">
</script>
