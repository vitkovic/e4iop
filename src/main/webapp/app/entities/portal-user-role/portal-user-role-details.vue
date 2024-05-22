<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="portalUserRole">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.portalUserRole.detail.title')">PortalUserRole</span> {{portalUserRole.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.portalUserRole.role')">Role</span>
                    </dt>
                    <dd>
                        <span>{{portalUserRole.role}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.portalUserRole.abbreviation')">Abbreviation</span>
                    </dt>
                    <dd>
                        <span>{{portalUserRole.abbreviation}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.portalUserRole.fullName')">Full Name</span>
                    </dt>
                    <dd>
                        <span>{{portalUserRole.fullName}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="portalUserRole.id" :to="{name: 'PortalUserRoleEdit', params: {portalUserRoleId: portalUserRole.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./portal-user-role-details.component.ts">
</script>
