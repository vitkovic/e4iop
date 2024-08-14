<template>
  <div>
    <div class="mb-4 mb-sm-3">
      <h2>
        <span v-text="$t('riportalApp.riProcurementProposal.home.title')" id="ri-procurement-proposal-heading"
          >Ri Procurement Proposals</span
        >
      </h2>
    </div>
    <div id="page-heading">
      <router-link
        :to="{ name: 'RiProcurementProposalCreate' }"
        tag="button"
        id="jh-create-entity"
        class="btn btn-primary float-right jh-create-entity create-ri-procurement-proposal mb-3"
      >
        <font-awesome-icon icon="plus"></font-awesome-icon>
        <span v-text="$t('riportalApp.riProcurementProposal.home.createLabel')">
          Create a new Ri Procurement Proposal
        </span>
      </router-link>
    </div>
    <b-alert
      :show="dismissCountDown"
      dismissible
      :variant="alertType"
      @dismissed="dismissCountDown = 0"
      @dismiss-count-down="countDownChanged"
    >
      {{ alertMessage }}
    </b-alert>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && riProposalsDTO && riProposalsDTO.length === 0">
      <span v-text="$t('riportalApp.riProcurementProposal.home.notFound')">No riProcurementProposals found</span>
    </div>
    <div class="table-responsive" v-if="riProposalsDTO && riProposalsDTO.length > 0">
      <table class="table table-striped">
        <thead>
          <tr>
            <!--
                    <th ><span v-text="$t('global.field.id')">ID</span> </th>
                    -->
            <th><span v-text="$t('riportalApp.riProcurementProposal.date')">Date</span></th>
            <th><span v-text="$t('riportalApp.riProcurementProposal.name')">Name</span></th>
            <th><span v-text="$t('riportalApp.riProcurementProposal.technicalSpecification')">Technical Specification</span></th>
            <th><span v-text="$t('riportalApp.riProcurementProposal.purpose')">Purpose</span></th>
            <th><span v-text="$t('riportalApp.riProcurementProposal.portalUser')">Portal User</span></th>
            <th></th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="riProcurementProposal in riProposalsDTO" :key="riProcurementProposal.id">
            <!--
                    <td>
                        <router-link :to="{name: 'RiProcurementProposalView', params: {riProcurementProposalId: riProcurementProposal.id}}">{{riProcurementProposal.id}}</router-link>
                    </td>
                    -->
            <td nowrap>{{ riProcurementProposal.date ? $d(Date.parse(riProcurementProposal.date), 'shortWithoutMinutes') : '' }}</td>
            <td>{{ riProcurementProposal.name }}</td>
            <td>{{ riProcurementProposal.technicalSpecification }}</td>
            <td>{{ riProcurementProposal.purpose }}</td>
            <td>
              <div v-if="riProcurementProposal.portalUser">
                <div v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
                  <router-link :to="{ name: 'PortalUserView', params: { portalUserId: riProcurementProposal.portalUser.id } }">
                    <span v-if="riProcurementProposal.portalUser.user"
                      >{{ riProcurementProposal.portalUser.user.lastName }} {{ riProcurementProposal.portalUser.user.firstName }}</span
                    >
                  </router-link>
                </div>
                <div v-if="authenticated && hasAnyRole(['PA']) == false && hasAnyAuthority('ROLE_ADMIN') == false">
                  <span v-if="riProcurementProposal.portalUser.user"
                    >{{ riProcurementProposal.portalUser.user.lastName }} {{ riProcurementProposal.portalUser.user.firstName }}</span
                  >
                </div>
              </div>
            </td>
            <td nowrap>
              <div class="img-box">
                <img src="/content/images/like.png" class="img-content" v-on:click="like(riProcurementProposal)" /> {{ riProcurementProposal.likes }}
              </div>
            </td>
            <td nowrap>
                <div class="img-box">

              <img src="/content/images/dislike1.png" class="img-content mr-2" v-on:click="dislike(riProcurementProposal)" />
              {{ riProcurementProposal.dislikes }}
                </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RiProcurementProposalView', params: { riProcurementProposalId: riProcurementProposal.id } }"
                  tag="button"
                  class="btn btn-info btn-sm details"
                >
                  <span v-text="$t('entity.action.view')">View</span>
                </router-link>
                <router-link
                  v-if="riProcurementProposal.possibleEdit"
                  :to="{ name: 'RiProcurementProposalEdit', params: { riProcurementProposalId: riProcurementProposal.id } }"
                  tag="button"
                  class="btn btn-primary btn-sm edit"
                >
                  <span v-text="$t('entity.action.edit')">Edit</span>
                </router-link>
                <b-button
                  v-if="riProcurementProposal.possibleEdit"
                  v-on:click="prepareRemove(riProcurementProposal)"
                  variant="danger"
                  class="btn btn-sm"
                  v-b-modal.removeEntity
                >
                  <span v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="riportalApp.riProcurementProposal.delete.question" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-riProcurementProposal-heading" v-text="$t('riportalApp.riProcurementProposal.delete.question', { id: removeId })">
          Are you sure you want to delete this Ri Procurement Proposal?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-riProcurementProposal"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRiProcurementProposal()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="riProcurementProposals && riProcurementProposals.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ri-procurement-proposal.component.ts"></script>

<style scoped>
    .img-box{
        width: 32px;
        height: 27px;
        display: flex;
        align-items: center;
    }

    .img-content{
        width: 100%;
        height: 100%;
        object-fit: contain;
    }
</style>
