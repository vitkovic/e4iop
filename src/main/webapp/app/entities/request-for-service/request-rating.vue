<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.requestForService.home.title')" id="request-for-service-heading">Request For Services</span>
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
                <tr v-if="previewRespond(requestForService)">
                    <!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    -->
                    <th nowrap v-on:click="changeOrder('date')"><span v-text="$t('riportalApp.requestForService.date')">Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('description')"><span v-text="$t('riportalApp.requestForService.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    -->
                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.requestForService.title')">title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th nowrap v-on:click="changeOrder('startTime')"><span v-text="$t('riportalApp.requestForService.startTime')">Start Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startTime'"></jhi-sort-indicator></th>
                    <th nowrap v-on:click="changeOrder('endTime')"><span v-text="$t('riportalApp.requestForService.endTime')">End Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endTime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('services.id')"><span v-text="$t('riportalApp.requestForService.services')">Services</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'services.id'"></jhi-sort-indicator></th>
                    <th v-if="hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN')" v-on:click="changeOrder('users.id')"><span v-text="$t('riportalApp.requestForService.user')">Users</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'users.id'"></jhi-sort-indicator></th>
                    <th v-if="hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN')" v-on:click="changeOrder('organizations.id')"><span v-text="$t('riportalApp.requestForService.organizations')">Organizations</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organizations.id'"></jhi-sort-indicator></th>
                    <th nowrap v-on:click="changeOrder('endTime')"><span v-text="$t('riportalApp.requestForService.rating')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endTime'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(requestForService, index) in requestForServices"
                    :key="requestForService.id">
                    <!--
                    <td>
                        <router-link :to="{name: 'RequestForServiceView', params: {requestForServiceId: requestForService.id}}">{{requestForService.id}}</router-link>
                    </td>
                    -->
                    <td>{{requestForService.date ? $d(Date.parse(requestForService.date), 'shortWithoutMinutes') : ''}}</td>
                    <td>{{requestForService.title}}</td>
                    <td>{{requestForService.startTime ? $d(Date.parse(requestForService.startTime), 'short') : ''}}</td>

                    <td>{{requestForService.endTime ? $d(Date.parse(requestForService.endTime), 'short') : ''}}</td>
                    
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
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: requestForService.users.id}}">{{requestForService.users.user.lastName}} {{requestForService.users.user.firstName}}</router-link>
                        </div>
                    </td>
                    <td v-if="hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN')">
                        <div v-if="requestForService.organizations">
                            <router-link :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: requestForService.organizations.id}}">
                                <span v-if="currentLanguage === 'sr'">{{requestForService.organizations.legalNameSr}}</span>
                                <span v-if="currentLanguage === 'en'">{{requestForService.organizations.legalNameEn}}</span>
                                <span v-if="currentLanguage === 'src'">{{requestForService.organizations.legalNameSrCyr}}</span>
                            </router-link>
                        </div>
                    </td>
                    <td v-if="previewRespond(requestForService)">
                        <b-form-rating v-if="account.id == requestForService.users.user.id" 
                            v-model="requestForService.rating" inline no-border show-clear variant="warning" class="mb-2">
                        </b-form-rating>
                        <b-form-rating v-if="account.id != requestForService.users.user.id" 
                            v-model="requestForService.rating" inline no-border readonly variant="warning" class="mb-2">
                        </b-form-rating>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RequestForServiceView', params: {requestForServiceId: requestForService.id}}" tag="button" class="btn btn-info btn-sm details">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <button type="button" id="save-entity" v-if="account.id == requestForService.users.user.id" 
                            @click="save(index)" class="btn btn-primary">
                                <span v-text="$t('entity.action.vote')"></span>
                            </button>
                            
                            
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>        
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

<script lang="ts" src="./request-rating.component.ts">
</script>
