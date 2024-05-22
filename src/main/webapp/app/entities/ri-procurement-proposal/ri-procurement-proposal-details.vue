<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="riProcurementProposal">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riProcurementProposal.detail.title')">RiProcurementProposal</span> {{riProcurementProposal.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.riProcurementProposal.date')">Date</span>
                    </dt>
                    <dd>
                        <span v-if="riProcurementProposal.date">{{$d(Date.parse(riProcurementProposal.date), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riProcurementProposal.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{riProcurementProposal.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riProcurementProposal.technicalSpecification')">Technical Specification</span>
                    </dt>
                    <dd>
                        <span>{{riProcurementProposal.technicalSpecification}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riProcurementProposal.purpose')">Purpose</span>
                    </dt>
                    <dd>
                        <span>{{riProcurementProposal.purpose}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.riProcurementProposal.portalUser')">Portal User</span>
                    </dt>
                    <dd>                        
                        {{riProcurementProposal.portalUser.user.lastName}} {{riProcurementProposal.portalUser.user.firstName}}                                               
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="riProcurementProposal.id" :to="{name: 'RiProcurementProposalEdit', params: {riProcurementProposalId: riProcurementProposal.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-procurement-proposal-details.component.ts">
</script>
