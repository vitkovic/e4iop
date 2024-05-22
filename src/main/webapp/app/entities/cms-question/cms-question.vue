<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsQuestion.home.title')" id="cms-question-heading">Cms Questions</span>
            <router-link :to="{name: 'CmsQuestionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-question">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.cmsQuestion.home.createLabel')">
                    Create a new Cms Question
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
        <div class="alert alert-warning" v-if="!isFetching && cmsQuestions && cmsQuestions.length === 0">
            <span v-text="$t('riportalApp.cmsQuestion.home.notFound')">No cmsQuestions found</span>
        </div>
        <div class="table-responsive" v-if="cmsQuestions && cmsQuestions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.cmsQuestion.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.cmsQuestion.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('question')"><span v-text="$t('riportalApp.cmsQuestion.question')">Question</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'question'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('answer')"><span v-text="$t('riportalApp.cmsQuestion.answer')">Answer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'answer'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.cmsQuestion.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.cmsQuestion.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsQuestion in cmsQuestions"
                    :key="cmsQuestion.id">
                    <td>
                        <router-link :to="{name: 'CmsQuestionView', params: {cmsQuestionId: cmsQuestion.id}}">{{cmsQuestion.id}}</router-link>
                    </td>
                    <td>{{cmsQuestion.createdAt ? $d(Date.parse(cmsQuestion.createdAt), 'short') : ''}}</td>
                    <td>{{cmsQuestion.modifiedAt ? $d(Date.parse(cmsQuestion.modifiedAt), 'short') : ''}}</td>
                    <td>{{cmsQuestion.question}}</td>
                    <td>{{cmsQuestion.answer}}</td>
                    <td>
                        <div v-if="cmsQuestion.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsQuestion.createdBy.id}}">{{cmsQuestion.createdBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsQuestion.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsQuestion.modifiedBy.id}}">{{cmsQuestion.modifiedBy.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CmsQuestionView', params: {cmsQuestionId: cmsQuestion.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CmsQuestionEdit', params: {cmsQuestionId: cmsQuestion.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cmsQuestion)"
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
            <span slot="modal-title"><span id="riportalApp.cmsQuestion.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cmsQuestion-heading" v-text="$t('riportalApp.cmsQuestion.delete.question', {'id': removeId})">Are you sure you want to delete this Cms Question?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsQuestion" v-text="$t('entity.action.delete')" v-on:click="removeCmsQuestion()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cmsQuestions && cmsQuestions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-question.component.ts">
</script>
