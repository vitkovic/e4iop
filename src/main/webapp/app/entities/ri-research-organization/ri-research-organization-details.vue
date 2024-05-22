<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riResearchOrganization">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riResearchOrganization.detail.title')">RiResearchOrganization</span> {{riResearchOrganization.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{riResearchOrganization.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.descriptionSr')">Description Sr</span>
                    </dt>
                    <dd>
                        <span>{{riResearchOrganization.descriptionSr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.decsriptionEn')">Decsription En</span>
                    </dt>
                    <dd>
                        <span>{{riResearchOrganization.decsriptionEn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.logo')">Logo</span>
                    </dt>
                    <dd>
                        <span>{{riResearchOrganization.logo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.descriptionSrCyr')">Description Sr Cyr</span>
                    </dt>
                    <dd>
                        <span>{{riResearchOrganization.descriptionSrCyr}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.puOrganization')">Pu Organization</span>
                    </dt>
                    <dd>
                        <div v-if="riResearchOrganization.puOrganization">
                            <router-link :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: riResearchOrganization.puOrganization.id}}">{{riResearchOrganization.puOrganization.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.researchInstitution')">Research Institution</span>
                    </dt>
                    <dd>
                        <div v-if="riResearchOrganization.researchInstitution">
                            <router-link :to="{name: 'ResearchInstitutionView', params: {researchInstitutionId: riResearchOrganization.researchInstitution.id}}">{{riResearchOrganization.researchInstitution.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.activityStatus')">Activity Status</span>
                    </dt>
                    <dd>
                        <div v-if="riResearchOrganization.activityStatus">
                            <router-link :to="{name: 'RipoActivityStatusView', params: {ripoActivityStatusId: riResearchOrganization.activityStatus.id}}">{{riResearchOrganization.activityStatus.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.subdomains')">Subdomains</span>
                    </dt>
                    <dd>
                        <span v-for="(subdomains, i) in riResearchOrganization.subdomains" :key="subdomains.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'ScientificSubdomainView', params: {scientificSubdomainId: subdomains.id}}">{{subdomains.id}}</router-link>
                        </span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.manager')">Manager</span>
                    </dt>
                    <dd>
                        <div v-if="riResearchOrganization.manager">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: riResearchOrganization.manager.id}}">{{riResearchOrganization.manager.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riResearchOrganization.contactPerson')">Contact Person</span>
                    </dt>
                    <dd>
                        <div v-if="riResearchOrganization.contactPerson">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: riResearchOrganization.contactPerson.id}}">{{riResearchOrganization.contactPerson.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riResearchOrganization.id" :to="{name: 'RiResearchOrganizationEdit', params: {riResearchOrganizationId: riResearchOrganization.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-research-organization-details.component.ts">
</script>
