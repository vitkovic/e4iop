<template>
    <div>
      <div class="row justify-content-center">
          <div class="col-md-8">
            <h3 class="mb-5" v-text="'B2B CMS Reports'"></h3>
  
				  <div>
				  <b-tabs content-class="mt-3">
				    <b-tab title="Advertisements" active>
						
						
					 <div class="d-flex">
						    
			                <div class="form-group">
			                <input
			                  id="advCountId"
			                  type="text"
			                  class="form-control"
			                  name="advCountName"
			                  v-model="advCount"
			                />
			                </div>
			                <div>&nbsp;</div>
			                <div class="form-group">
					              <label class="form-control-label" v-text="$t('riportalApp.reports.category')" for="kat">Category</label>
					                <select class="form-control" style="width:100%;max-width:100%" v-model="mainSearchCategory" id="kat" name="kat" @change="retrieveAdvertisementsByCategory()" >
                    					<option  v-for="element in advCategList" :key="element.id" :value="element.id" >{{element.name}}</option>
                					</select>
            			</div> 
            			 
            			  <div>&nbsp;</div>
			                <div class="form-group">
					              <label class="form-control-label" v-text="$t('riportalApp.reports.type')" for="typ">Type</label>
					                <select class="form-control" style="width:100%;max-width:100%" v-model="advType" id="typ" name="typ" @change="retrieveAdvertisementsByType()" >
                    					<option  v-for="element in advTypeList" :key="element.id" :value="element.id" >{{element.type}}</option>
                					</select>
            			</div> 
            			  <div>&nbsp;</div>
			                <div class="form-group">
					              <label class="form-control-label" v-text="$t('riportalApp.reports.kind')" for="kin">Kind</label>
					                <select class="form-control" style="width:100%;max-width:100%" v-model="advKind" id="kin" name="kin" @change="retrieveAdvertisementsByKind()" >
                    					<option  v-for="element in advKindList" :key="element.id" :value="element.id" >{{element.kind}}</option>
                					</select>
            			</div>
            			 <div>&nbsp;</div>
			                <div class="form-group">
					              <label class="form-control-label" v-text="$t('riportalApp.reports.company')" for="comp">Company</label>
					                <select class="form-control" style="width:100%;max-width:100%" v-model="advCompany" id="comp" name="comp" @change="retrieveAdvertisementsByCompany()" >
                    					<option  v-for="element in advCompanyList" :key="element.id" :value="element.id" >{{element.name}}</option>
                					</select>
            			</div>
            			   <div  class="form-group">
				              <label
				                class="form-control-label"
				                v-text="$t('riportalApp.reports.activationDatetimeFrom')"
				                for="activationDatetimeFrom"
				                >Activation Datetime From</label
				              >
				              <div class="d-flex">
				                <input
				                  id="activationDatetimeFrom"
				                  v-model="activationDatetimeFrom"
				                  type="datetime-local"
				                  class="form-control"
				                  name="activationDatetimeFrom"
				                  @change="updateInstantFieldFrom($event)"
				                />
				              </div>
            			  
              				</div>
              				 <div  class="form-group">
				              <label
				                class="form-control-label"
				                v-text="$t('riportalApp.reports.activationDatetimeТо')"
				                for="activationDatetimeTo"
				                >Activation Datetime To</label
				              >
				              <div class="d-flex">
				                <input
				                  id="activationDatetimeTo"
				                  type="datetime-local"
				                  v-model="activationDatetimeTo"
				                  class="form-control"
				                  name="activationDatetimeTo"
				                  @change="updateInstantFieldTo($event)"
				                />
				              </div>
            			  
              				</div>
              			</div>
						<div>
							
							  <b-button
							          v-text="$t('riportalApp.reports.exportcsv')"
							          v-on:click="exportCSVFile('advertisements', 'advertisements')"
							          >Cancel</b-button>
							
						</div>
					<div class="custom-table-responsive" v-if="advertisements && advertisements.length > 0">
					  <table class="table table-striped">
					    <thead>
					      <tr>
				
					        <th v-on:click="changeOrder('title')">
					          <span v-text="$t('riportalApp.advertisement.title')">Title</span>
					          <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
					        </th>
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
					      
					      </tr>
					    </thead>
					    <tbody>
					      <tr v-for="advertisement in advertisements" :key="advertisement.id">
					        <td>{{ advertisement.title }}</td>
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
						  </tr>
						  </tbody>
						 </table>
					   </div>			
								
							  
						</b-tab>
				    <b-tab title="Collaborations"><p>Collaborations</p>
				    
				    
				     <div class="d-flex">
						    
			                <div class="form-group">
			                <input
			                  id="collabCountId"
			                  type="text"
			                  class="form-control"
			                  name="collabCountId"
			                  v-model="collabCount"
			                />
			                </div>
			              
			                
			                <div>&nbsp;</div>
			                <div class="form-group">
					              <label class="form-control-label" v-text="$t('riportalApp.reports.collaborations')" for="kat">Coolaborations</label>
					                <select class="form-control" style="width:100%;max-width:100%" v-model="collabStatus" id="collab" name="collab" @change="retrieveCollaborations()" >
                    					<option  v-for="element in collabStatusList" :key="element.id" :value="element.id" >{{element.status}}</option>
                					</select>
            				</div>
			                
			                 <div  class="form-group">
				              <label
				                class="form-control-label"
				                v-text="$t('riportalApp.reports.creationCollabDatetimeFrom')"
				                for="activationDatetimeFrom"
				                >Activation Datetime From</label
				              >
				              <div class="d-flex">
				                <input
				                  id="activationDatetimeFrom"
				                  v-model="activationDatetimeFrom"
				                  type="datetime-local"
				                  class="form-control"
				                  name="activationDatetimeFrom"
				                  @change="updateInstantFieldFromCollab($event)"
				                />
				              </div>
            			  
              				</div>
              				 <div  class="form-group">
				              <label
				                class="form-control-label"
				                v-text="$t('riportalApp.reports.creationCollabDatetimeТо')"
				                for="activationDatetimeTo"
				                >Activation Datetime To</label
				              >
				              <div class="d-flex">
				                <input
				                  id="activationDatetimeTo"
				                  type="datetime-local"
				                  v-model="activationDatetimeTo"
				                  class="form-control"
				                  name="activationDatetimeTo"
				                  @change="updateInstantFieldToCollab($event)"
				                />
				              </div>
            			  
              				</div>
              			
            			  
            			
            		
            		</div>
						
            			
            			
            			  <b-button
							          v-text="$t('riportalApp.reports.exportcsv')"
							          v-on:click="exportCSVFile('collaborations', 'collaborations')"
							          >Export</b-button>
							
            	    				
            	    		<div class="table-responsive" v-if="collaborations && collaborations.length > 0">
				            <table class="table table-striped">
				                <thead>
				                <tr>
				                    <th v-on:click="changeOrderCollab('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('datetime')"><span v-text="$t('riportalApp.collaboration.datetime')">Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('commentOffer')"><span v-text="$t('riportalApp.collaboration.commentOffer')">Comment Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'commentOffer'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('commentRequest')"><span v-text="$t('riportalApp.collaboration.commentRequest')">Comment Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'commentRequest'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('companyOffer.id')"><span v-text="$t('riportalApp.collaboration.companyOffer')">Company Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyOffer.id'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('companyRequest.id')"><span v-text="$t('riportalApp.collaboration.companyRequest')">Company Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyRequest.id'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('advertisement.id')"><span v-text="$t('riportalApp.collaboration.advertisement')">Advertisement</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'advertisement.id'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('ratingOffer.id')"><span v-text="$t('riportalApp.collaboration.ratingOffer')">Rating Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingOffer.id'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('ratingRequest.id')"><span v-text="$t('riportalApp.collaboration.ratingRequest')">Rating Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingRequest.id'"></jhi-sort-indicator></th>
				                    <th v-on:click="changeOrderCollab('status.id')"><span v-text="'Status'">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.id'"></jhi-sort-indicator></th>
				                   
				                </tr>
				                </thead>
				                <tbody>
				                <tr v-for="collaboration in collaborations"
				                    :key="collaboration.id">
				                    <td>
				                        <router-link :to="{name: 'CollaborationView', params: {collaborationId: collaboration.id}}">{{collaboration.id}}</router-link>
				                    </td>
				                    <td>{{collaboration.datetime ? $d(Date.parse(collaboration.datetime), 'short') : ''}}</td>
				                    <td>{{collaboration.commentOffer}}</td>
				                    <td>{{collaboration.commentRequest}}</td>
				                    <td>
				                        <div v-if="collaboration.companyOffer">
				                            <router-link :to="{name: 'CompanyView', params: {companyId: collaboration.companyOffer.id}}">{{collaboration.companyOffer.name}}</router-link>
				                        </div>
				                    </td>
				                    <td>
				                        <div v-if="collaboration.companyRequest">
				                            <router-link :to="{name: 'CompanyView', params: {companyId: collaboration.companyRequest.id}}">{{collaboration.companyRequest.name}}</router-link>
				                        </div>
				                    </td>
				                    <td>
				                        <div v-if="collaboration.advertisement">
				                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: collaboration.advertisement.id}}">{{collaboration.advertisement.title}}</router-link>
				                        </div>
				                    </td>
				                    <td>
				                        <div v-if="collaboration.ratingOffer">
				                            <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaboration.ratingOffer.id}}">{{collaboration.ratingOffer.number}}</router-link>
				                        </div>
				                    </td>
				                    <td>
				                        <div v-if="collaboration.ratingRequest">
				                            <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaboration.ratingRequest.id}}">{{collaboration.ratingRequest.number}}</router-link>
				                        </div>
				                    </td>
				                    <td>
				                        <div v-if="collaboration.status">
				                            {{collaboration.status.status}}
				                        </div>
				                    </td>
				                   
				                </tr>
				                </tbody>
				            </table>
				        </div>		
            	    				
           	    
				    </b-tab>
				    <b-tab title="Users">
						  <div class="d-flex">
						    
			                <div class="form-group">
			                <input
			                  id="collabCountId"
			                  type="text"
			                  class="form-control"
			                  name="collabCountId"
			                  v-model="usersCount"
			                />
			                </div>
		  		
            		</div>
						
            			
            			
            			  <b-button
							          v-text="$t('riportalApp.reports.exportcsv')"
							          v-on:click="exportCSVFile('users', 'users')"
							          >Export</b-button>
							
											
						 <div class="table-responsive" v-if="portalUsers && portalUsers.length > 0">
					            <table class="table table-striped">
					                <thead>
					                <tr>
					                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
					                    <th v-on:click="changeOrder('userFirstName')"><span v-text="$t('riportalApp.portalUser.firstName')">First Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator></th>
					                    <th v-on:click="changeOrder('user.lastName')"><span v-text="$t('riportalApp.portalUser.familyName')">Family Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'familyName'"></jhi-sort-indicator></th>
					                    <th v-on:click="changeOrder('phone')"><span v-text="$t('riportalApp.portalUser.phone')">Phone</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator></th>
					                    <th v-on:click="changeOrder('position')"><span v-text="$t('riportalApp.portalUser.position')">Position</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'position'"></jhi-sort-indicator></th>
					                    <!--
					                    <th v-on:click="changeOrder('researcher.id')"><span v-text="$t('riportalApp.portalUser.researcher')">Researcher</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'researcher.id'"></jhi-sort-indicator></th>
					                    -->
					                    <th v-on:click="changeOrder('userOrganization.legalNameSr')"><span v-text="$t('riportalApp.portalUser.userOrganization')">User Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userOrganization.id'"></jhi-sort-indicator></th>
					                    <th></th>
					                </tr>
					                </thead>
					                <tbody>
					                <tr v-for="portalUser in portalUsers"
					                    :key="portalUser.id">
					                    <td>
					                        <router-link class="link-style" :to="{name: 'PortalUserView', params: {portalUserId: portalUser.id}}">{{portalUser.id}}</router-link>
					                    </td>
					                    <td>{{portalUser.user.firstName}}</td>
					                    <td>{{portalUser.user.lastName}}</td>
					                    <td>{{portalUser.phone}}</td>
					                    <td>{{portalUser.position}}</td>
					                    <!--
					                    <td>
					                        <div v-if="portalUser.researcher">
					                            <router-link :to="{name: 'ResearcherView', params: {researcherId: portalUser.researcher.id}}">{{portalUser.researcher.id}}</router-link>
					                        </div>
					                    </td>
					                    -->
					                    <td>
					                        <div v-if="portalUser.userOrganization">
					                            <router-link class="link-style" :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: portalUser.userOrganization.id}}">
					                                <span v-if="currentLanguage == 'sr'">{{portalUser.userOrganization.legalNameSr}}</span>
					                                <span v-if="currentLanguage == 'en'">{{portalUser.userOrganization.legalNameEn}}</span>
					                                <span v-if="currentLanguage == 'src'">{{portalUser.userOrganization.legalNameSrCyr}}</span>
					                            </router-link>
					                        </div>
					                    </td>
					                  
					                </tr>
					                </tbody>
					            </table>
					        </div>
											
						
					</b-tab>
				    <b-tab title="Grades"><p>Grades</p></b-tab>
				  </b-tabs>
				</div>
  
          </div>
      </div>
    </div>
  </template>
      
  <script lang="ts" src="./cms-b2b-reports.component.ts">
  </script>
      