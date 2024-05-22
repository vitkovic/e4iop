<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="serviceProposal">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.serviceProposal.detail.title')">ServiceProposal</span> {{serviceProposal.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.serviceProposal.date')">Date</span>
                    </dt>
                    <dd>
                        <span v-if="serviceProposal.date">{{$d(Date.parse(serviceProposal.date), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceProposal.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{serviceProposal.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceProposal.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{serviceProposal.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceProposal.purpose')">Purpose</span>
                    </dt>
                    <dd>
                        <span>{{serviceProposal.purpose}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceProposal.portalUser')">Portal User</span>
                    </dt>
                    <dd>
                        <div v-if="serviceProposal.portalUser">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: serviceProposal.portalUser.id}}">{{serviceProposal.portalUser.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="serviceProposal.id" :to="{name: 'ServiceProposalEdit', params: {serviceProposalId: serviceProposal.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./service-proposal-details.component.ts">
</script>
