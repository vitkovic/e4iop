<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.requestForService.home.title')" id="request-for-service-heading">Request For Services</span>
            <router-link :to="{name: 'RequestForServiceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-request-for-service">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.requestForService.home.createLabel')">
                    Create a new Request For Service
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
        <div class="alert alert-warning" v-if="!isFetching && requestForServices && requestForServices.length === 0">
            <span v-text="$t('riportalApp.requestForService.home.notFound')">No requestForServices found</span>
        </div>
        <div class="table-responsive" v-if="requestForServices && requestForServices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky" nowrap v-on:click="changeOrder('date')"><span v-text="$t('riportalApp.requestForService.date')">Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator></th>
                    
                    <th class="sticky" v-on:click="changeOrder('description')"><span v-text="$t('riportalApp.requestForService.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <!--
                    <th class="sticky" v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.requestForService.title')">title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky" nowrap v-on:click="changeOrder('startTime')"><span v-text="$t('riportalApp.requestForService.startTime')">Start Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startTime'"></jhi-sort-indicator></th>
                    <th class="sticky" nowrap v-on:click="changeOrder('endTime')"><span v-text="$t('riportalApp.requestForService.endTime')">End Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endTime'"></jhi-sort-indicator></th>
                    <th class="sticky" nowrap v-on:click="changeOrder('deadline')"><span v-text="$t('riportalApp.requestForService.deadline')">Deadline</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deadline'"></jhi-sort-indicator></th>
                    <th class="sticky" v-on:click="changeOrder('services.id')"><span v-text="$t('riportalApp.requestForService.services')">Services</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'services.id'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN')" v-on:click="changeOrder('users.id')"><span v-text="$t('riportalApp.requestForService.user')">Users</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'users.id'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN')" v-on:click="changeOrder('organizations.id')"><span v-text="$t('riportalApp.requestForService.organizations')">Organizations</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organizations.id'"></jhi-sort-indicator></th>
                    <th class="sticky"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="requestForService in requestForServices"
                    :key="requestForService.id">
                    <!--
                    <td>
                        <router-link :to="{name: 'RequestForServiceView', params: {requestForServiceId: requestForService.id}}">{{requestForService.id}}</router-link>
                    </td>
                    -->
                    <td>{{requestForService.date ? $d(Date.parse(requestForService.date), 'shortWithoutMinutes', 'sr') : ''}}</td>
                    <td>{{requestForService.description}}</td>
                    <!--
                    <td>{{requestForService.title}}</td>
                    -->
                    <td>{{requestForService.startTime ? $d(Date.parse(requestForService.startTime), 'shortWithoutMinutes', 'sr') : ''}}</td>

                    <td>{{requestForService.endTime ? $d(Date.parse(requestForService.endTime), 'shortWithoutMinutes', 'sr') : ''}}</td>
                    <td>{{requestForService.deadline ? $d(Date.parse(requestForService.deadline), 'shortWithoutMinutes', 'sr') : ''}}</td>
                    <td>
                        <div v-if="requestForService.services">
                            <router-link :to="{name: 'RiServiceView', params: {riServiceId: requestForService.services.id}}">
                                <span v-if="currentLanguage === 'sr'">{{requestForService.services.nameSr}}</span>
                                <span v-if="currentLanguage === 'en'">{{requestForService.services.nameEn}}</span>
                                <span v-if="currentLanguage === 'src'">{{requestForService.services.nameSrCyr}}</span>
                            </router-link>
                        </div>
                    </td>
                    <td v-if="hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN')">
                        <div v-if="requestForService.users">
                            <router-link  class="link-style" :to="{name: 'PortalUserView', params: {portalUserId: requestForService.users.id}}">{{requestForService.users.user.lastName}} {{requestForService.users.user.firstName}}</router-link>
                        </div>
                    </td>
                    <td nowrap v-if="hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN')">
                        <div v-if="requestForService.organizations">
                            <router-link class="link-style" :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: requestForService.organizations.id}}">
                                <span v-if="currentLanguage === 'sr'">{{requestForService.organizations.legalNameSr}}</span>
                                <span v-if="currentLanguage === 'en'">{{requestForService.organizations.legalNameEn}}</span>
                                <span v-if="currentLanguage === 'src'">{{requestForService.organizations.legalNameSrCyr}}</span>
                            </router-link>
                        </div>
                    </td>
                    <td nowrap class="text-right">
                        
                            <router-link :to="{name: 'RequestForServiceView', params: {requestForServiceId: requestForService.id}}" tag="button" class="btn btn-info btn-sm details">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link  v-if="account.id == requestForService.users.user.id || (hasAnyRole(['PA' ]) || hasAnyAuthority('ROLE_ADMIN'))"
                                :to="{name: 'RequestForServiceEdit', params: {requestForServiceId: requestForService.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(requestForService)" v-if="account.id == requestForService.users.user.id || (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                            <b-button v-on:click="sendToRespond(requestForService)" v-if="(hasAnyRole(['PA','RPRI']) || hasAnyAuthority('ROLE_ADMIN'))"
                                   variant="primary"
                                   class="btn btn-sm"
                                   >                                
                                <span class="d-none d-md-inline" v-text="$t('riportalApp.requestForService.respond')"></span>
                            </b-button>
                            <b-button v-on:click="previewRespond(requestForService)" v-if="account.id == requestForService.users.user.id"
                                   variant="info"
                                   class="btn btn-sm"
                                   >                                
                                <span class="d-none d-md-inline" v-text="$t('riportalApp.requestForService.offer')"></span>
                            </b-button>
                        
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.requestForService.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-requestForService-heading" v-text="$t('riportalApp.requestForService.delete.question', {'id': removeId})">Are you sure you want to delete this Request For Service?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-requestForService" v-text="$t('entity.action.delete')" v-on:click="removeRequestForService()">Delete</button>
            </div>
        </b-modal>
        <div v-show="requestForServices && requestForServices.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./request-for-service.component.ts">
</script>
