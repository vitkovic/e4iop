<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsSlider.home.title')" id="cms-slider-heading">Cms Sliders</span>
            <router-link :to="{name: 'CmsSliderCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-slider">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.cmsSlider.home.createLabel')">
                    Create a new Cms Slider
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
        <div class="alert alert-warning" v-if="!isFetching && cmsSliders && cmsSliders.length === 0">
            <span v-text="$t('riportalApp.cmsSlider.home.notFound')">No cmsSliders found</span>
        </div>
        <div class="table-responsive" v-if="cmsSliders && cmsSliders.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.cmsSlider.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.cmsSlider.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.cmsSlider.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('link')"><span v-text="$t('riportalApp.cmsSlider.link')">Link</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'link'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('newTab')"><span v-text="$t('riportalApp.cmsSlider.newTab')">New Tab</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'newTab'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('ordinalNumber')"><span v-text="$t('riportalApp.cmsSlider.ordinalNumber')">Ordinal Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ordinalNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.cmsSlider.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.cmsSlider.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('image.id')"><span v-text="$t('riportalApp.cmsSlider.image')">Image</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsSlider in cmsSliders"
                    :key="cmsSlider.id">
                    <td>
                        <router-link :to="{name: 'CmsSliderView', params: {cmsSliderId: cmsSlider.id}}">{{cmsSlider.id}}</router-link>
                    </td>
                    <td>{{cmsSlider.createdAt ? $d(Date.parse(cmsSlider.createdAt), 'short') : ''}}</td>
                    <td>{{cmsSlider.modifiedAt ? $d(Date.parse(cmsSlider.modifiedAt), 'short') : ''}}</td>
                    <td>{{cmsSlider.title}}</td>
                    <td>{{cmsSlider.link}}</td>
                    <td>{{cmsSlider.newTab}}</td>
                    <td>{{cmsSlider.ordinalNumber}}</td>
                    <td>
                        <div v-if="cmsSlider.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsSlider.createdBy.id}}">{{cmsSlider.createdBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsSlider.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsSlider.modifiedBy.id}}">{{cmsSlider.modifiedBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsSlider.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: cmsSlider.image.id}}">{{cmsSlider.image.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CmsSliderView', params: {cmsSliderId: cmsSlider.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CmsSliderEdit', params: {cmsSliderId: cmsSlider.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cmsSlider)"
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
            <span slot="modal-title"><span id="riportalApp.cmsSlider.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cmsSlider-heading" v-text="$t('riportalApp.cmsSlider.delete.question', {'id': removeId})">Are you sure you want to delete this Cms Slider?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsSlider" v-text="$t('entity.action.delete')" v-on:click="removeCmsSlider()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cmsSliders && cmsSliders.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-slider.component.ts">
</script>
