<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.badge.home.title')" id="badge-heading">Badges</span>
            <router-link :to="{name: 'BadgeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-badge">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.badge.home.createLabel')">
                    Create a new Badge
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
        <div class="alert alert-warning" v-if="!isFetching && badges && badges.length === 0">
            <span v-text="$t('riportalApp.badge.home.notFound')">No badges found</span>
        </div>
        <div class="table-responsive" v-if="badges && badges.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('riportalApp.badge.title')">Title</span></th>
                    <th><span v-text="$t('riportalApp.badge.description')">Description</span></th>
                    <th><span v-text="$t('riportalApp.badge.image')">Image</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="badge in badges"
                    :key="badge.id">
                    <td>
                        <router-link :to="{name: 'BadgeView', params: {badgeId: badge.id}}">{{badge.id}}</router-link>
                    </td>
                    <td>{{badge.title}}</td>
                    <td>{{badge.description}}</td>
                    <td>
                        <div v-if="badge.image">
                            <router-link :to="{name: 'DocumentView', params: {documentId: badge.image.id}}">{{badge.image.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'BadgeView', params: {badgeId: badge.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'BadgeEdit', params: {badgeId: badge.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(badge)"
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
            <span slot="modal-title"><span id="riportalApp.badge.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-badge-heading" v-text="$t('riportalApp.badge.delete.question', {'id': removeId})">Are you sure you want to delete this Badge?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-badge" v-text="$t('entity.action.delete')" v-on:click="removeBadge()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./badge.component.ts">
</script>
