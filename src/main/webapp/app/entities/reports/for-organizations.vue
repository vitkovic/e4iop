<template>
    <div>
        <b-row>
            <b-col sm="8">
                <h2 id="page-heading">
                    <span v-text="$t('riportalApp.reports.statisticForOrganizationsOnPortal')" ></span>
                </h2>
            </b-col>            
            <b-col sm="4"  class="text-right mt-3">
                <button  type="button" id="stampanje" class="btn btn-primary" 
                    v-on:click="stampanjeExcel()">
                    <span v-text="$t('riportalApp.reports.downloadExcel')"></span>
                </button>
            </b-col>
        </b-row>

        <b-row class="mt-4">
            <b-col sm="6">
                <b-row>
                    <b-col sm="12">
                        <span v-text="$t('riportalApp.reports.searchOrganizations')"></span>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col sm="12">
                        <b-input-group>
                            <b-form-input type="text" v-model="search.searchTerms"></b-form-input>
                            <b-input-group-append>                        
                                <b-button variant="primary" v-on:click="searchOrganizations()">
                                    <b-icon icon="search" ></b-icon>
                                </b-button>                    
                            </b-input-group-append>
                        </b-input-group>                         
                    </b-col>
                </b-row>
                <b-row class="mt-2">
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.reports.fromDate')" for="datepicker-dateformat1"></label>
                            <b-form-datepicker
                                id="datepicker-dateformat1"
                                :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                                v-model="search.fromDate"
                                locale="sr"                            
                            ></b-form-datepicker>                               
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.reports.toDate')" for="datepicker-dateformat2"></label>
                            <b-form-datepicker
                                id="datepicker-dateformat2"
                                :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                                v-model="search.toDate"
                                locale="sr"                            
                            ></b-form-datepicker>                               
                        </div>
                    </b-col>
                </b-row>
            </b-col>
            <b-col sm="3" offset="3">
                <b-card 
                    header-class="filter-class"
                    align="left"
                    footer-bg-variant="info"
                    footer-text-variant="white"    
                >
                    <!--  opcionalno moze da se doda klasa za header i da se izbaci header bg variant
                    header-class="blue-background"              
                    -->

                    <template v-slot:header>
                        <span v-text="$t('riportalApp.reports.filteri')"></span>
                    </template>
                    <b-form-radio v-model="search.selType"   
                        name="some-radios" :value="1" 
                        ><span v-text="$t('riportalApp.reports.nioOnPortal')"></span>
                    </b-form-radio>
                    <b-form-radio v-model="search.selType"   
                        name="some-radios" :value="2" >
                        <span v-text="$t('riportalApp.reports.noNioOnPortal')"></span>
                    </b-form-radio>    
                    <button type="button" class="btn btn-primary mt-4" style="width:40%"  @click="clear()" v-text="$t('riportalApp.reports.clear')"></button>
                </b-card>  
            </b-col>
        </b-row>
        
        <b-row class="table-header mt-4">
            <b-col  class="mt-2" sm="4"><span v-text="$t('riportalApp.reports.naziv')"></span></b-col>
            <b-col  class="mt-2" sm="4"><span v-text="$t('riportalApp.reports.adresa')"></span></b-col>            
        </b-row>
        <div v-for="(organization, index) in organizations" :key="organization.id">
            <b-row 
                :class="{'gray-light': index%2 == 0, 'gray-dark': index%2 != 0 }">                
                <b-col class="mt-2" sm="4">{{organization.name}}</b-col>
                <b-col class="mt-2" sm="4">{{organization.address}}</b-col>
                <b-col class="mt-2" sm="2">
                    <b-button variant="link" style="color:blue" v-on:click="showDetails(index)">
                        <span v-text="$t('riportalApp.reports.detaljiZbirno')"></span>
                    </b-button>
                </b-col>
                <b-col class="mt-2" sm="2">
                    <b-button variant="link" style="color:blue" v-on:click="showServices(index)">
                        <span v-text="$t('riportalApp.reports.detaljiZaService')"></span>
                    </b-button>
                </b-col>
            </b-row>
            <!--Servisi -->
            <div v-if="organization.services">
                <div  v-if="organization.services">
                    <div class="table-responsive" v-if="organization.services.length > 0">                    
                        <table class="table table-sm table-striped table-bordered ">
                            <thead>
                            <tr style="background-color:#1b457d;color:white;">
                            <th class="text-center"><span v-text="$t('riportalApp.reports.name')"></span></th>
                            <th class="text-center"><span v-text="$t('riportalApp.reports.description')"></span></th>
                            <th class="text-center"><span v-text="$t('riportalApp.reports.infrastructureName')"></span></th>
                            <th class="text-center"><span v-text="$t('riportalApp.reports.availability')"></span></th>
                            <th class="text-center"><span v-text="$t('riportalApp.reports.type')"></span></th>
                            <th class="text-center"><span v-text="$t('riportalApp.reports.numberOfRequests')"></span></th>
                            <th class="text-center"><span v-text="$t('riportalApp.reports.realizedNumberOfRequests')"></span></th>
                            </tr>
                            </thead>
                            <tbody>
                                <tr   v-for="service in organization.services" :key="service.id">
                                    <td  style="border-width:2px"><span>{{service.nameSr}}</span></td>
                                    <td><span>{{service.descriptionSr}}</span></td>
                                    <td><span>{{service.researchInfrastructure.nameSr}}</span></td>
                                    <td><span>{{service.availability.availability}}</span></td>
                                    <td><span>{{service.subtypes.subtype}}</span></td>
                                    <td><span>{{service.requestForServiceNumber}}</span></td>
                                    <td><span>{{service.realizedRequestsForServiceNumber}}</span></td>                                
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div  v-if="organization.services && organization.services.length == 0">
                    <span v-text="$t('riportalApp.reports.organizationDoesntOfferServices')" style="background-color:#F03914;color:white"></span>
                </div>
                
            </div>
            <!-- Zbirno -->
            <div  v-if="organization.details">  
                <div v-if="organization.details && organization.details.infrastructureNumber != 0">
                    <b-row  class="mt-2 " >                
                        <b-col sm="4" offset="1" class="mt-2 light-blue-color">
                            <span style="line-height:40px" v-text="$t('riportalApp.reports.infrastructureNumber')" ></span>:
                        </b-col>
                        <b-col sm="1" class="mt-2 light-blue-color">
                            <span style="line-height:40px">{{organization.details.infrastructureNumber}}</span>
                        </b-col>                
                    </b-row>
                    <b-row  class="mt-2 ">
                        <b-col sm="4" offset="1" style="line-height:40px" class=" light-blue-color">
                            <span v-text="$t('riportalApp.reports.serviceNumber')"></span>:
                        </b-col>
                        <b-col sm="1" style="line-height:40px"  class=" light-blue-color">{{organization.details.serviceNumber}}</b-col>                
                    </b-row>
                    <b-row  class="mt-2 ">
                        <b-col sm="4" offset="1" class=" light-blue-color" style="line-height:40px">
                            <span v-text="$t('riportalApp.reports.requiredServiceNumber')"></span>:
                        </b-col>
                        <b-col sm="1" style="line-height:40px"  class=" light-blue-color">{{organization.details.requiredServiceNumber}}</b-col>                
                    </b-row>
                    <b-row  class="mt-2 ">
                        <b-col sm="4" offset="1" style="line-height:40px" class=" light-blue-color">
                            <span v-text="$t('riportalApp.reports.realizedServiceNumber')"></span>:
                        </b-col>
                        <b-col sm="1" style="line-height:40px" class=" light-blue-color">{{organization.details.realizedServiceNumber}}</b-col>                
                    </b-row>
                    <b-row  class="mt-2 ">
                        <b-col sm="4" offset="1" style="line-height:40px" class=" light-blue-color">
                            <span v-text="$t('riportalApp.reports.requestedServiceNumber')"></span>:
                        </b-col>
                        <b-col sm="1" style="line-height:40px" class=" light-blue-color">{{organization.details.requestedServiceNumber}}</b-col>                
                    </b-row>
                    <b-row  class="mt-2 ">
                        <b-col sm="4" offset="1" style="line-height:40px" class=" light-blue-color">
                            <span v-text="$t('riportalApp.reports.offeredServiceNumber')"></span>:
                        </b-col>
                        <b-col sm="1" style="line-height:40px" class=" light-blue-color">{{organization.details.offeredServiceNumber}}</b-col>                
                    </b-row>
                    <b-row  class="mt-2 ">
                        <b-col sm="4" offset="1" style="line-height:40px" class=" light-blue-color">
                            <span v-text="$t('riportalApp.reports.requestedAndRealizedServiceNumber')"></span>:
                        </b-col>
                        <b-col sm="1" style="line-height:40px" class=" light-blue-color">{{organization.details.realizedServiceNumber1}}</b-col>                
                    </b-row>
                </div>    
                <div  v-if="organization.details && organization.details.infrastructureNumber == 0">
                    <span v-text="$t('riportalApp.reports.organizationDoesntOfferServices')" style="background-color:#F03914;color:white"></span>
                </div> 
            </div>
               
            

        </div>
           
        
        
    </div>
</template>

<script lang="ts" src="./report-for-organizations.component.ts">
</script>
