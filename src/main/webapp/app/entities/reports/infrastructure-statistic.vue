<template>
    <div>
        <b-row>
            <b-col sm="8"
                <h2 id="page-heading">
                    <span v-text="$t('riportalApp.reports.infrastructureStatistic')" ></span>
                </h2>
            </b-col>            
            <b-col sm="4"  class="text-right">
                <button  type="button" id="stampanje" class="btn btn-primary" 
                    v-on:click="stampanjeExcel()">
                    <span v-text="$t('riportalApp.reports.downloadExcel')"></span>
                </button>
            </b-col>
        </b-row>


        <div v-if="infrastructureStatistic.infrastructureNumberDto">
        <b-row>
            <b-col sm="4" class="text-right mt-1">
                <span  v-text="$t('riportalApp.reports.totalNumberOfRegisteredInfrastructures')" ></span>:
            </b-col>
            <b-col sm="1" class="mt-1">
                {{infrastructureStatistic.infrastructureNumberDto.infrastructureNumber}}
            </b-col>
            <b-col sm="2">
                <b-button variant="link" size="sm" style="color:blue; text-decoration:underline" v-on:click="showDetailsF()">
                    <span style="font-size:16px" v-text="$t('riportalApp.reports.detaljnije')"></span>
                </b-button>
            </b-col>
        </b-row>
        <div v-if="showDetails">
            <b-row class="mt-2">
                <b-col sm="3" offset="3" class="mr-2" >
                    <b-row>
                        <b-col sm="12" class="text-center" style="background-color:#143263; color:white">
                            <span v-text="$t('riportalApp.reports.byStatus')" ></span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-2 mb-2" v-for="status in infrastructureStatistic.infrastructureNumberDto.numberPerStatuses" :key="status.id">
                        <b-col sm="9" class="text-right light-blue-color">
                            {{status.status.status}}:
                        </b-col>
                        <b-col sm="3" class="text-right light-blue-color">
                            {{status.statusNumber}}
                        </b-col>
                    </b-row>                
                </b-col>
                <b-col sm="3"  class="mr-2">
                    <b-row>
                        <b-col sm="12" class="text-center" style="background-color:#143263; color:white">
                            <span v-text="$t('riportalApp.reports.byAccessType')" ></span>                        
                        </b-col>
                    </b-row>
                    <b-row class="mt-2" v-for="accessType in infrastructureStatistic.infrastructureNumberDto.numberPerAccessTypes" :key="accessType.id">
                        <b-col sm="8" class="text-right light-blue-color">
                            {{accessType.riAccessType.type}}:
                        </b-col>
                        <b-col sm="4" class="text-right light-blue-color">
                            {{accessType.riAccessTypeNumber}}
                        </b-col>
                    </b-row>                               
                </b-col>
            </b-row>
            <b-row class="mt-2">
                <b-col sm="3" offset="3" class="mr-2" >
                    <b-row>
                        <b-col sm="12" class="text-center" style="background-color:#143263; color:white" >
                            <span v-text="$t('riportalApp.reports.byAccessMode')" ></span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-2" v-for="accessMode in infrastructureStatistic.infrastructureNumberDto.numberPeraccessModes" :key="accessMode.id">
                        <b-col sm="9" class="text-right light-blue-color">
                            {{accessMode.riAccessMode.mode}}:
                        </b-col>
                        <b-col sm="3" class="text-right light-blue-color">
                            {{accessMode.riAccessModeNUmber}}
                        </b-col>
                    </b-row>                
                </b-col>
                <b-col sm="3" class="mr-2" >
                    <b-row>
                        <b-col sm="12" class="text-center" style="background-color:#143263; color:white">
                            <span v-text="$t('riportalApp.reports.byCategories')" ></span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-2" v-for="category in infrastructureStatistic.infrastructureNumberDto.numberPerCategories" :key="category.id">
                        <b-col sm="9" class="text-right light-blue-color">
                            {{category.riCategoryDto.category}}:
                        </b-col>
                        <b-col sm="3" class="text-right light-blue-color">
                            {{category.riCategoryNumber}}
                        </b-col>
                    </b-row>
                </b-col>                
            </b-row>
        </div>
        <b-row class="mt-2">
            <b-col sm="4" class="text-right">
                <span v-text="$t('riportalApp.reports.totalNumberOfServices')" ></span>:
            </b-col>
            <b-col sm="2">
                {{infrastructureStatistic.serviceNumber}}
            </b-col>
        </b-row>
        <b-row class="mt-2">
            <b-col sm="4" class="text-right">
                <span v-text="$t('riportalApp.reports.totalNumberOfRequestForServices')" ></span>:                
            </b-col>
            <b-col sm="2">
                {{infrastructureStatistic.requestForServiceNumber}}
            </b-col>
        </b-row>
        <b-row class="mt-2">
            <b-col sm="4" class="text-right">
                <span v-text="$t('riportalApp.reports.totalNumberOfRespondToRequestForServices')" ></span>:
            </b-col>
            <b-col sm="2">
                {{infrastructureStatistic.requestRespondNUmber}}
            </b-col>
        </b-row>
        <b-row class="mt-2">
            <b-col sm="4" class="text-right">
                <span v-text="$t('riportalApp.reports.totalNumberOfRealizedRequestForServices')" ></span>:                
            </b-col>
            <b-col sm="2">
                {{infrastructureStatistic.realizedRequestForServceNUmber}}
            </b-col>
        </b-row>
        
        
        </div>
    </div>
</template>

<script lang="ts" src="./infrastructure-statistic.component.ts">
</script>
