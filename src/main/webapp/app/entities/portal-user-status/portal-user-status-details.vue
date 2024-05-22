<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="portalUserStatus">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.portalUserStatus.detail.title')">portalUserStatus</span> {{portalUserStatus.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.portalUserStatus.status')"></span>
                    </dt>
                    <dd>
                        <span>{{portalUserStatus.valueSr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.portalUserStatus.valueEn')"></span>
                    </dt>
                    <dd>
                        <span>{{portalUserStatus.valueEn}}</span>
                    </dd>                    
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="portalUserStatus.id" :to="{name: 'portalUserStatusEdit', params: {portalUserStatusId: portalUserStatus.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./portal-user-status-details.component.ts">
</script>
