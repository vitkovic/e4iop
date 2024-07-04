<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsNews.home.title')" id="cms-news-heading">Cms News</span>
            <router-link :to="{ name: 'CMSNewsCreate' }" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-news">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span v-text="$t('riportalApp.cmsNews.home.createLabel')">Create a new Cms News</span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown = 0"
            @dismiss-count-down="countDownChanged">
            {{ alertMessage }}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && cmsNews && cmsNews.length === 0">
            <span v-text="$t('riportalApp.cmsNews.home.notFound')">No cmsNews found</span>
        </div>
        <div class="table-responsive" v-if="cmsNews && cmsNews.length > 0">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th @click="changeOrder('id')">
                            <span v-text="$t('global.field.id')">ID</span>
                            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
                        </th>
                        <th @click="changeOrder('date')">
                            <span v-text="$t('riportalApp.cmsNews.date')">Date</span>
                            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
                        </th>
                        <th @click="changeOrder('title')">
                            <span v-text="$t('riportalApp.cmsNews.title')">Title</span>
                            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
                        </th>
                        <th @click="changeOrder('sequenceNumber')">
                            <span v-text="$t('riportalApp.cmsNews.sequenceNumber')">Sequence Number</span>
                            <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sequenceNumber'"></jhi-sort-indicator>
                        </th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="news in cmsNews" :key="news.id">
                        <td>
                            <router-link :to="{ name: 'CMSNewsDetails', params: { cmsNewsId: news.id }}">{{ news.id }}</router-link>
                        </td>
                        <td>{{ news.date ? $d(Date.parse(news.date), 'short') : '' }}</td>
                        <td>{{ news.title }}</td>
                        <td>{{ news.sequenceNumber }}</td>
                        <td class="text-right">
                            <div class="btn-group">
                                <router-link :to="{ name: 'CMSNewsDetails', params: { cmsNewsId: news.id }}" tag="button" class="btn btn-info btn-sm details">
                                    <font-awesome-icon icon="eye"></font-awesome-icon>
                                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                                </router-link>
                                <router-link :to="{ name: 'CMSNewsEdit', params: { cmsNewsId: news.id }}" tag="button" class="btn btn-primary btn-sm edit">
                                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                                </router-link>
                                <b-button @click="prepareRemove(news)"
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
        <b-modal ref="removeEntity" id="removeEntity">
            <span slot="modal-title">
                <span id="riportalApp.cmsNews.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span>
            </span>
            <div class="modal-body">
                <p id="jhi-delete-cmsNews-heading" v-text="$t('riportalApp.cmsNews.delete.question', { 'id': removeId })">
                    Are you sure you want to delete this Cms News?
                </p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" @click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsNews" v-text="$t('entity.action.delete')" @click="removeCmsNews()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cmsNews && cmsNews.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-news-search.component.ts">
</script>
