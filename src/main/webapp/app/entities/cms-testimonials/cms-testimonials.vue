<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.cmsTestimonials.home.title')" id="cms-testimonials-heading">Cms Testimonials</span>
            <router-link :to="{name: 'CmsTestimonialsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cms-testimonials">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.cmsTestimonials.home.createLabel')">
                    Create a new Cms Testimonials
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
        <div class="alert alert-warning" v-if="!isFetching && cmsTestimonials && cmsTestimonials.length === 0">
            <span v-text="$t('riportalApp.cmsTestimonials.home.notFound')">No cmsTestimonials found</span>
        </div>
        <div class="table-responsive" v-if="cmsTestimonials && cmsTestimonials.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.cmsTestimonials.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.cmsTestimonials.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.cmsTestimonials.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('riportalApp.cmsTestimonials.content')">Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.cmsTestimonials.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.cmsTestimonials.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('image.id')"><span v-text="$t('riportalApp.cmsTestimonials.image')">Image</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cmsTestimonials in cmsTestimonials"
                    :key="cmsTestimonials.id">
                    <td>
                        <router-link :to="{name: 'CmsTestimonialsView', params: {cmsTestimonialsId: cmsTestimonials.id}}">{{cmsTestimonials.id}}</router-link>
                    </td>
                    <td>{{cmsTestimonials.createdAt ? $d(Date.parse(cmsTestimonials.createdAt), 'short') : ''}}</td>
                    <td>{{cmsTestimonials.modifiedAt ? $d(Date.parse(cmsTestimonials.modifiedAt), 'short') : ''}}</td>
                    <td>{{cmsTestimonials.title}}</td>
                    <td>{{cmsTestimonials.content}}</td>
                    <td>
                        <div v-if="cmsTestimonials.createdBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsTestimonials.createdBy.id}}">{{cmsTestimonials.createdBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsTestimonials.modifiedBy">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: cmsTestimonials.modifiedBy.id}}">{{cmsTestimonials.modifiedBy.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cmsTestimonials.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: cmsTestimonials.image.id}}">{{cmsTestimonials.image.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CmsTestimonialsView', params: {cmsTestimonialsId: cmsTestimonials.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CmsTestimonialsEdit', params: {cmsTestimonialsId: cmsTestimonials.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cmsTestimonials)"
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
            <span slot="modal-title"><span id="riportalApp.cmsTestimonials.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cmsTestimonials-heading" v-text="$t('riportalApp.cmsTestimonials.delete.question', {'id': removeId})">Are you sure you want to delete this Cms Testimonials?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cmsTestimonials" v-text="$t('entity.action.delete')" v-on:click="removeCmsTestimonials()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cmsTestimonials && cmsTestimonials.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cms-testimonials.component.ts">
</script>
