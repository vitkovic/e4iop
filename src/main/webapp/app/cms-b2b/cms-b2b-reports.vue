<template>
    <div>
      <div class="row justify-content-center">
          <div class="col-md-8">
            <h3 class="mb-5" v-text="'B2B CMS Reports'"></h3>
  
				  <div>
				  <b-tabs content-class="mt-3">
				    <b-tab title="Advertisements" active>
						
						
						
						
					<div class="custom-table-responsive" v-if="advertisements && advertisements.length > 0">
					  <table class="table table-striped">
					    <thead>
					      <tr>
					        <!-- <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th> -->
					        <!-- <th v-on:click="changeOrder('createdAt')"><span v-text="$t('riportalApp.advertisement.createdAt')">Created At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator></th> -->
					        <!-- <th v-on:click="changeOrder('modifiedAt')"><span v-text="$t('riportalApp.advertisement.modifiedAt')">Modified At</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator></th> -->
					
					        <th v-on:click="changeOrder('title')">
					          <span v-text="$t('riportalApp.advertisement.title')">Title</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
					        </th>
					        <!-- <th v-on:click="changeOrder('description')"><span v-text="$t('riportalApp.advertisement.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th> -->
					        <!-- <th v-on:click="changeOrder('conditions')"><span v-text="$t('riportalApp.advertisement.conditions')">Conditions</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'conditions'"></jhi-sort-indicator></th> -->
					        <!-- <th v-on:click="changeOrder('createdBy.id')"><span v-text="$t('riportalApp.advertisement.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.id'"></jhi-sort-indicator></th> -->
					        <!-- <th v-on:click="changeOrder('modifiedBy.id')"><span v-text="$t('riportalApp.advertisement.modifiedBy')">Modified By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy.id'"></jhi-sort-indicator></th> -->
					        <th v-on:click="changeOrder('status.id')">
					          <span v-text="$t('riportalApp.advertisement.status')">Status</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.id'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('type.id')">
					          <span v-text="$t('riportalApp.advertisement.type')">Type</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('kind.id')">
					          <span v-text="$t('riportalApp.advertisement.kind')">Kind</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.id'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('subsubcategory.name')">
					          <span v-text="$t('riportalApp.advertisement.subsubcategory')">Subsubcategory</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subsubcategory.name'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('budget')">
					          <span v-text="$t('riportalApp.advertisement.budget')">Budget</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'budget'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('company.id')">
					          <span v-text="$t('riportalApp.advertisement.company')">Company</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.id'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('activationDatetime')">
					          <span v-text="$t('riportalApp.advertisement.activationDatetime')">Activation Datetime</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationDatetime'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('expirationDatetime')">
					          <span v-text="$t('riportalApp.advertisement.expirationDatetime')">Expiration Date</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expirationDatetime'"></jhi-sort-indicator>
					        </th>
					        <th v-on:click="changeOrder('deletionDatetime')">
					          <span v-text="$t('riportalApp.advertisement.deletionDatetime')">Deletion Date</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deletionDatetime'"></jhi-sort-indicator>
					        </th>
					        <th></th>
					      </tr>
					    </thead>
					    <tbody>
					      <tr v-for="advertisement in advertisements" :key="advertisement.id">
					        <!-- <td>
					                    <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisement.id}}">{{advertisement.id}}</router-link>
					                </td> -->
					        <!-- <td>{{advertisement.createdAt ? $d(Date.parse(advertisement.createdAt), 'short') : ''}}</td> -->
					        <!-- <td>{{advertisement.modifiedAt ? $d(Date.parse(advertisement.modifiedAt), 'short') : ''}}</td> -->
					        <td>{{ advertisement.title }}</td>
					        <!-- <td>{{advertisement.description}}</td> -->
					
					        <!-- <td>{{advertisement.conditions}}</td> -->
					        <!-- <td>
					                    <div v-if="advertisement.createdBy">
					                        <router-link :to="{name: 'PortalUserView', params: {portalUserId: advertisement.createdBy.id}}">{{advertisement.createdBy.firstname}}</router-link>
					                    </div>
					                </td> -->
					        <!-- <td>
					                    <div v-if="advertisement.modifiedBy">
					                        <router-link :to="{name: 'PortalUserView', params: {portalUserId: advertisement.modifiedBy.id}}">{{advertisement.modifiedBy.firstname}}</router-link>
					                    </div>
					                </td> -->
					
					        <td>
					          <div v-if="advertisement.status">
					            {{ advertisement.status.status }}
					          </div>
					        </td>
					        <td>
					          <div v-if="advertisement.type">
					            {{ advertisement.type.type }}
					          </div>
					        </td>
					        <td>
					          <div v-if="advertisement.kind">
					            {{ advertisement.kind.kind }}
					          </div>
					        </td>
					        <td>
					          <div v-if="advertisement.subsubcategory">
					            {{ advertisement.subsubcategory.name }}
					          </div>
					        </td>
					        <td> <div v-if="advertisement.budget">
                            {{ advertisement.budget.toLocaleString('us-US', { style: 'currency', currency: 'RSD' }) }}
                        </div></td>
					        <td>
					          <div v-if="advertisement.company">
					            <router-link :to="{ name: 'CompanyView', params: { companyId: advertisement.company.id } }" class="text-body">{{
					              advertisement.company.name
					            }}</router-link>
					          </div>
					        </td>
					        <td>
					          {{
					            advertisement.activationDatetime ? $d(Date.parse(advertisement.activationDatetime.toString()), { dateStyle: 'short' }) : ''
					          }}
					        </td>
					        <td>
					          {{
					            advertisement.expirationDatetime ? $d(Date.parse(advertisement.expirationDatetime.toString()), { dateStyle: 'short' }) : ''
					          }}
					        </td>
					        <td>
					          {{ advertisement.deletionDatetime ? $d(Date.parse(advertisement.deletionDatetime.toString()), { dateStyle: 'short' }) : '' }}
					        </td>
						  </tr>
						  </tbody>
						 </table>
					   </div>			
						
						</b-tab>
				    <b-tab title="Second"><p>I'm the second tab</p></b-tab>
				    <b-tab title="Disabled" disabled><p>I'm a disabled tab!</p></b-tab>
				  </b-tabs>
				</div>
  
          </div>
      </div>
    </div>
  </template>
      
  <script lang="ts" src="./cms-b2b-reports.component.ts">
  </script>
      