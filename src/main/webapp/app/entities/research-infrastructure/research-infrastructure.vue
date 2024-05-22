<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.researchInfrastructure.home.title')" id="research-infrastructure-heading">Research Infrastructures</span>
            <router-link :to="{name: 'ResearchInfrastructureCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-research-infrastructure">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.researchInfrastructure.home.createLabel')">
                    Create a new Research Infrastructure
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
        <div class="alert alert-warning" v-if="!isFetching && researchInfrastructures && researchInfrastructures.length === 0">
            <span v-text="$t('riportalApp.researchInfrastructure.home.notFound')">No researchInfrastructures found</span>
        </div>
        <div class="table-responsive" v-if="researchInfrastructures && researchInfrastructures.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
<!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
-->    
                    <th class="sticky" v-if="currentLanguage=='sr'" v-on:click="changeOrder('nameSr')"><span v-text="$t('riportalApp.researchInfrastructure.name')">Name Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameSr'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage=='src'"  v-on:click="changeOrder('nameSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.name')">Name Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameSrCyr'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage=='en'"  v-on:click="changeOrder('nameEn')"><span v-text="$t('riportalApp.researchInfrastructure.name')">Name En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameEn'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage=='sr'" v-on:click="changeOrder('descriptionSr')"><span v-text="$t('riportalApp.researchInfrastructure.description')">Description Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionSr'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage=='en'" v-on:click="changeOrder('descriptionEn')"><span v-text="$t('riportalApp.researchInfrastructure.description')">Description En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionEn'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage=='src'" v-on:click="changeOrder('descriptionSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.description')">Description Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionSrCyr'"></jhi-sort-indicator></th>

                    <!--
                    <th v-on:click="changeOrder('departmentName')"><span v-text="$t('riportalApp.researchInfrastructure.departmentName')">Department Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'departmentName'"></jhi-sort-indicator></th>                        
                    <th v-on:click="changeOrder('riLogo')"><span v-text="$t('riportalApp.researchInfrastructure.riLogo')">Ri Logo</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'riLogo'"></jhi-sort-indicator></th>                     
                    
                    <th v-on:click="changeOrder('riWebsite')"><span v-text="$t('riportalApp.researchInfrastructure.riWebsite')">Ri Website</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'riWebsite'"></jhi-sort-indicator></th>   
                   
                    <th v-if="currentLanguage=='sr'" v-on:click="changeOrder('technicalSpecificationSr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationSr')">Technical Specification Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationSr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='en'" v-on:click="changeOrder('technicalSpecificationEn')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationEn')">Technical Specification En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationEn'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='src'" v-on:click="changeOrder('technicalSpecificationSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationSrCyr')">Technical Specification Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationSrCyr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='sr'" v-on:click="changeOrder('technicalSpecificationLinkSr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkSr')">Technical Specification Link Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationLinkSr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='en'" v-on:click="changeOrder('technicalSpecificationLinkEn')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkEn')">Technical Specification Link En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationLinkEn'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='src'" v-on:click="changeOrder('technicalSpecificationLinkSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkSrCyr')">Technical Specification Link Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationLinkSrCyr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='sr'" v-on:click="changeOrder('userManualLinkSr')"><span v-text="$t('riportalApp.researchInfrastructure.userManualLinkSr')">User Manual Link Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userManualLinkSr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='en'" v-on:click="changeOrder('userManualLinkEn')"><span v-text="$t('riportalApp.researchInfrastructure.userManualLinkEn')">User Manual Link En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userManualLinkEn'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='src'" v-on:click="changeOrder('userManualLinkSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.userManualLinkSrCyr')">User Manual Link Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userManualLinkSrCyr'"></jhi-sort-indicator></th>
                   -->     
                    <th class="sticky" v-on:click="changeOrder('keywords')"><span v-text="$t('riportalApp.researchInfrastructure.keywordsNoLanguage')">Keywords</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'keywords'"></jhi-sort-indicator></th>

                    <th class="sticky" v-on:click="changeOrder('status.id')"><span v-text="$t('riportalApp.researchInfrastructure.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.id'"></jhi-sort-indicator></th>
                    <th class="sticky" nowrap v-on:click="changeOrder('accessMode.id')"><span v-text="$t('riportalApp.researchInfrastructure.accessMode')">Access Mode</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessMode.id'"></jhi-sort-indicator></th>
                    <th class="sticky" nowrap v-on:click="changeOrder('accessType.id')"><span v-text="$t('riportalApp.researchInfrastructure.accessType')">Access Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessType.id'"></jhi-sort-indicator></th>
                    <th class="sticky" nowrap v-on:click="changeOrder('category.id')"><span v-text="$t('riportalApp.researchInfrastructure.category')">Category</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.id'"></jhi-sort-indicator></th>
                    <th class="sticky" v-on:click="changeOrder('owner.id')"><span v-text="$t('riportalApp.researchInfrastructure.owner')">Owner</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'owner.id'"></jhi-sort-indicator></th>
                    <th class="sticky" nowrap v-on:click="changeOrder('manager.id')"><span v-text="$t('riportalApp.researchInfrastructure.manager')">Manager</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'manager.id'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('contactPerson.id')"><span v-text="$t('riportalApp.researchInfrastructure.contactPerson')">Contact Person</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contactPerson.id'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(researchInfrastructure, index) in researchInfrastructures"
                    :key="researchInfrastructure.id">
<!--
                    <td>
                        <router-link :to="{name: 'ResearchInfrastructureView', params: {researchInfrastructureId: researchInfrastructure.id}}">{{researchInfrastructure.id}}</router-link>
                    </td>
-->                    
                    <td v-if="currentLanguage=='sr'" >{{researchInfrastructure.nameSr}}</td>
                    <td v-if="currentLanguage=='src'" >{{researchInfrastructure.nameSrCyr}}</td>
                    <td v-if="currentLanguage=='en'" >{{researchInfrastructure.nameEn}}</td>

                    <td>
                        {{ shortenedDescription(index) }}
                        <span v-if="getDescriptionByLanguage(researchInfrastructure).length > 90">
                            <a href="#" @click.prevent="toggleDescription(index)">
                                {{ researchInfrastructure.showFullDescription ? $t('entity.action.readLess') : $t('entity.action.readMore') }}
                            </a>
                        </span>
                    </td>


                    <!-- <td v-if="currentLanguage=='sr'">{{researchInfrastructure.descriptionSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.descriptionEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.descriptionSrCyr}}</td> -->
                    
                    <!--
                    <td>{{researchInfrastructure.departmentName}}</td>
                    <td>{{researchInfrastructure.riLogo}}</td>
                    
                    <td>{{researchInfrastructure.riWebsite}}</td>
                   
                    <td v-if="currentLanguage=='sr'">{{researchInfrastructure.technicalSpecificationSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.technicalSpecificationEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.technicalSpecificationSrCyr}}</td>
                    <td v-if="currentLanguage=='sr'">{{researchInfrastructure.technicalSpecificationLinkSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.technicalSpecificationLinkEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.technicalSpecificationLinkSrCyr}}</td>

                    <td v-if="currentLanguage=='sr'">{{researchInfrastructure.userManualLinkSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.userManualLinkEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.userManualLinkSrCyr}}</td>
                    -->
                    <td>{{researchInfrastructure.keywords}}</td>                    
                    
                    
                    
                    
                    <td>
                        <div v-if="researchInfrastructure.status">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.status.status}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.status.statusEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.status.statusCyr}}</span>
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.accessMode">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.accessMode.mode}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.accessMode.modeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.accessMode.modeCyr}}</span>    
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.accessType">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.accessType.type}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.accessType.typeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.accessType.typeCyr}}</span> 
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.category">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.category.category}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.category.categoryEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.category.categoryCyr}}</span> 
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.owner">
                            <router-link :to="{name: 'RiResearchOrganizationView', params: {riResearchOrganizationId: researchInfrastructure.owner.id}}">
                                {{researchInfrastructure.owner.name}}
                            </router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.manager">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: researchInfrastructure.manager.id}}">{{researchInfrastructure.manager.user.lastName}} {{researchInfrastructure.manager.user.firstName}}</router-link>
                        </div>
                    </td>
                    <!--
                    <td>
                        <div v-if="researchInfrastructure.contactPerson">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: researchInfrastructure.contactPerson.id}}">{{researchInfrastructure.contactPerson.user.lastName}} {{researchInfrastructure.contactPerson.user.firstName}}</router-link>
                        </div>
                    </td>
                    -->
                    <td nowrap class="text-right">
                        
                            <router-link :to="{name: 'ResearchInfrastructureView', params: {researchInfrastructureId: researchInfrastructure.id}}" tag="button" class="btn btn-info btn-sm details">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ResearchInfrastructureEdit', params: {researchInfrastructureId: researchInfrastructure.id}}"  tag="button" class="btn btn-primary btn-sm edit"
                            v-if="authenticated && (hasAnyRole(['PA', 'RPRI']) || hasAnyAuthority('ROLE_ADMIN'))"
                            >
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(researchInfrastructure)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity
                                   v-if="authenticated && (hasAnyRole(['PA', 'RPRI']) || hasAnyAuthority('ROLE_ADMIN'))"
                                   >
                        
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.researchInfrastructure.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-researchInfrastructure-heading" v-text="$t('riportalApp.researchInfrastructure.delete.question', {'id': removeId})">Are you sure you want to delete this Research Infrastructure?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-researchInfrastructure" v-text="$t('entity.action.delete')" v-on:click="removeResearchInfrastructure()">Delete</button>
            </div>
        </b-modal>
        <div v-show="researchInfrastructures && researchInfrastructures.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./research-infrastructure.component.ts">
</script>
