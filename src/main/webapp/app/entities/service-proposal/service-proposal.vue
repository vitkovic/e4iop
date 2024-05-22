<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.serviceProposal.home.title')" id="service-proposal-heading">Service Proposals</span>
            <router-link :to="{name: 'ServiceProposalCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-service-proposal">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.serviceProposal.home.createLabel')">
                    Create a new Service Proposal
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && serviceProposalsDTO && serviceProposalsDTO.length === 0">
            <span v-text="$t('riportalApp.serviceProposal.home.notFound')">No serviceProposals found</span>
        </div>
        <div class="table-responsive" v-if="serviceProposalsDTO && serviceProposalsDTO.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span> </th>
                    <th ><span v-text="$t('riportalApp.serviceProposal.date')">Date</span> </th>
                    <th ><span v-text="$t('riportalApp.serviceProposal.name')">Name</span> </th>
                    <th ><span v-text="$t('riportalApp.serviceProposal.description')">Description</span> </th>
                    <th ><span v-text="$t('riportalApp.serviceProposal.purpose')">Purpose</span> </th>
                    <th ><span v-text="$t('riportalApp.serviceProposal.portalUser')">Portal User</span> </th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="serviceProposal in serviceProposalsDTO"
                    :key="serviceProposal.id">
                    <td>
                        <router-link :to="{name: 'ServiceProposalView', params: {serviceProposalId: serviceProposal.id}}">{{serviceProposal.id}}</router-link>
                    </td>
                    <td>{{serviceProposal.date ? $d(Date.parse(serviceProposal.date), 'short') : ''}}</td>
                    <td>{{serviceProposal.name}}</td>
                    <td>{{serviceProposal.description}}</td>
                    <td>{{serviceProposal.purpose}}</td>
                    <td>
                       
                        <div v-if="serviceProposal.portalUser">
                            <div v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
                                <router-link :to="{name: 'PortalUserView', params: {portalUserId: serviceProposal.portalUser.id}}">
                                    <span v-if="serviceProposal.portalUser.user">{{serviceProposal.portalUser.user.lastName}} {{serviceProposal.portalUser.user.firstName}}</span>
                                </router-link>
                            </div>
                            <div v-if="authenticated && hasAnyRole(['PA']) == false && hasAnyAuthority('ROLE_ADMIN') == false">
                                <span v-if="serviceProposal.portalUser.user">{{serviceProposal.portalUser.user.lastName}} {{serviceProposal.portalUser.user.firstName}}</span>
                            </div>
                        </div>
                    </td>
                    <td nowrap>
                        <img src="/content/images/like.png" v-on:click="like(serviceProposal)">  {{serviceProposal.likes}}
                    </td>
                    <td nowrap>
                        <img src="/content/images/dislike1.png" width="45%" class="mt-2" v-on:click="dislike(serviceProposal)">  {{serviceProposal.dislikes}}
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ServiceProposalView', params: {serviceProposalId: serviceProposal.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link v-if="serviceProposal.possibleEdit" :to="{name: 'ServiceProposalEdit', params: {serviceProposalId: serviceProposal.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-if="serviceProposal.possibleEdit" v-on:click="prepareRemove(serviceProposal)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.serviceProposal.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-serviceProposal-heading" v-text="$t('riportalApp.serviceProposal.delete.question', {'id': removeId})">Are you sure you want to delete this Service Proposal?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-serviceProposal" v-text="$t('entity.action.delete')" v-on:click="removeServiceProposal()">Delete</button>
            </div>
        </b-modal>
        <div v-show="serviceProposals && serviceProposals.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./service-proposal.component.ts">
</script>
