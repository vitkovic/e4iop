<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="meetingType">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.meetingType.detail.title')">MeetingType</span> {{meetingType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.meetingType.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{meetingType.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meetingType.isVisible')">Is Visible</span>
                    </dt>
                    <dd>
                        <span>{{meetingType.isVisible}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.meetingType.isDeleted')">Is Deleted</span>
                    </dt>
                    <dd>
                        <span>{{meetingType.isDeleted}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="meetingType.id" :to="{name: 'MeetingTypeEdit', params: {meetingTypeId: meetingType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./meeting-type-details.component.ts">
</script>
