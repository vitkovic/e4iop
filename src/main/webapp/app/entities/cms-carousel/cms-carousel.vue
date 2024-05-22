<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsCarousel.home.title')" id="cms-carousel-heading">Cms Carousels</span>
            <router-link :to="{name: 'CmsCarouselCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-carousel">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.cmsCarousel.home.createLabel')">
                    Create a new Cms Carousel
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
        <div class="alert alert-warning" v-if="!isFetching && cmsCarousels && cmsCarousels.length === 0">
            <span v-text="$t('riportalApp.cmsCarousel.home.notFound')">No cmsCarousels found</span>
        </div>
        <div class="table-responsive" v-if="cmsCarousels && cmsCarousels.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.cmsCarousel.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.cmsCarousel.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.cmsCarousel.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('link')"><span v-text="$t('riportalApp.cmsCarousel.link')">Link</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'link'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('newTab')"><span v-text="$t('riportalApp.cmsCarousel.newTab')">New Tab</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'newTab'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('ordinalNumber')"><span v-text="$t('riportalApp.cmsCarousel.ordinalNumber')">Ordinal Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ordinalNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.cmsCarousel.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.cmsCarousel.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('image.id')"><span v-text="$t('riportalApp.cmsCarousel.image')">Image</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsCarousel in cmsCarousels"
                    :key="cmsCarousel.id">
                    <td>
                        <router-link :to="{name: 'CmsCarouselView', params: {cmsCarouselId: cmsCarousel.id}}">{{cmsCarousel.id}}</router-link>
                    </td>
                    <td>{{cmsCarousel.createdAt ? $d(Date.parse(cmsCarousel.createdAt), 'short') : ''}}</td>
                    <td>{{cmsCarousel.modifiedAt ? $d(Date.parse(cmsCarousel.modifiedAt), 'short') : ''}}</td>
                    <td>{{cmsCarousel.title}}</td>
                    <td>{{cmsCarousel.link}}</td>
                    <td>{{cmsCarousel.newTab}}</td>
                    <td>{{cmsCarousel.ordinalNumber}}</td>
                    <td>
                        <div v-if="cmsCarousel.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsCarousel.createdBy.id}}">{{cmsCarousel.createdBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsCarousel.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsCarousel.modifiedBy.id}}">{{cmsCarousel.modifiedBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsCarousel.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: cmsCarousel.image.id}}">{{cmsCarousel.image.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CmsCarouselView', params: {cmsCarouselId: cmsCarousel.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CmsCarouselEdit', params: {cmsCarouselId: cmsCarousel.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cmsCarousel)"
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
            <span slot="modal-title"><span id="riportalApp.cmsCarousel.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cmsCarousel-heading" v-text="$t('riportalApp.cmsCarousel.delete.question', {'id': removeId})">Are you sure you want to delete this Cms Carousel?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsCarousel" v-text="$t('entity.action.delete')" v-on:click="removeCmsCarousel()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cmsCarousels && cmsCarousels.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-carousel.component.ts">
</script>
