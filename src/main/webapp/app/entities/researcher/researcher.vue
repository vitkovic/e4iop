<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.researcher.home.title')" id="researcher-heading">Researchers</span>
            <router-link :to="{name: 'ResearcherCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-researcher">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.researcher.home.createLabel')">
                    Create a new Researcher
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
        <div class="alert alert-warning" v-if="!isFetching && researchers && researchers.length === 0">
            <span v-text="$t('riportalApp.researcher.home.notFound')">No researchers found</span>
        </div>
        <div class="table-responsive" v-if="researchers && researchers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.researcher.firstName')">First Name</span></th>
                    <th><span v-text="$t('riportalApp.researcher.lastName')">Last Name</span></th>
                    <th><span v-text="$t('riportalApp.researcher.middleName')">Middle Name</span></th>
                    <th><span v-text="$t('riportalApp.researcher.birthDate')">Birth Date</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="researcher in researchers"
                    :key="researcher.id">
                    <td>
                        <router-link :to="{name: 'ResearcherView', params: {researcherId: researcher.id}}">{{researcher.id}}</router-link>
                    </td>
                    <td>{{researcher.firstName}}</td>
                    <td>{{researcher.lastName}}</td>
                    <td>{{researcher.middleName}}</td>
                    <td>{{researcher.birthDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ResearcherView', params: {researcherId: researcher.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ResearcherEdit', params: {researcherId: researcher.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(researcher)"
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
            <span slot="modal-title"><span id="riportalApp.researcher.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-researcher-heading" v-text="$t('riportalApp.researcher.delete.question', {'id': removeId})">Are you sure you want to delete this Researcher?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-researcher" v-text="$t('entity.action.delete')" v-on:click="removeResearcher()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./researcher.component.ts">
</script>
