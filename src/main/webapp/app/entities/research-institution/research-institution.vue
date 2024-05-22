<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.researchInstitution.home.title')" id="research-institution-heading">Research Institutions</span>
            <router-link :to="{name: 'ResearchInstitutionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-research-institution">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.researchInstitution.home.createLabel')">
                    Create a new Research Institution
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
        <div class="alert alert-warning" v-if="!isFetching && researchInstitutions && researchInstitutions.length === 0">
            <span v-text="$t('riportalApp.researchInstitution.home.notFound')">No researchInstitutions found</span>
        </div>
        <div class="table-responsive" v-if="researchInstitutions && researchInstitutions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.researchInstitution.name')">Name</span></th>
                    <th><span v-text="$t('riportalApp.researchInstitution.researchers')">Researchers</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="researchInstitution in researchInstitutions"
                    :key="researchInstitution.id">
                    <td>
                        <router-link :to="{name: 'ResearchInstitutionView', params: {researchInstitutionId: researchInstitution.id}}">{{researchInstitution.id}}</router-link>
                    </td>
                    <td>{{researchInstitution.name}}</td>
                    <td>
                        <span v-for="(researchers, i) in researchInstitution.researchers" :key="researchers.id">{{i > 0 ? ', ' : ''}}
                            <router-link class="form-control-static" :to="{name: 'ResearcherView', params: {researcherId: researchers.id}}">{{researchers.id}}</router-link>
                        </span>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ResearchInstitutionView', params: {researchInstitutionId: researchInstitution.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ResearchInstitutionEdit', params: {researchInstitutionId: researchInstitution.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(researchInstitution)"
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
            <span slot="modal-title"><span id="riportalApp.researchInstitution.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-researchInstitution-heading" v-text="$t('riportalApp.researchInstitution.delete.question', {'id': removeId})">Are you sure you want to delete this Research Institution?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-researchInstitution" v-text="$t('entity.action.delete')" v-on:click="removeResearchInstitution()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./research-institution.component.ts">
</script>
