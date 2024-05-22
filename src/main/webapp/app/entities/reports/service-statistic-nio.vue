<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.reports.serviceStatisticNioTitle')" ></span>
        </h2>
        <b-row class="mt-4">
            <b-col sm="6">
                <b-input-group>
                    <b-form-input type="text" v-model="search.searchTerms"></b-form-input>
                    <b-input-group-append>                        
                        <b-button variant="primary" v-on:click="searchOrganizations()">
                            <b-icon icon="search" ></b-icon>
                        </b-button>                    
                    </b-input-group-append>
                </b-input-group>                         
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
            <b-col  class="mt-2" sm="4"><span>Naziv</span></b-col>
            <b-col  class="mt-2" sm="4">Adresa</b-col>            
        </b-row>
        <div v-for="(organization, index) in organizations" :key="organization.id">
            <b-row 
                :class="{'gray-light': index%2 == 0, 'gray-dark': index%2 != 0 }">                
                <b-col class="mt-2" sm="4">{{organization.name}}</b-col>
                <b-col class="mt-2" sm="4">{{organization.address}}</b-col>
                <b-col class="mt-2" sm="4">
                    <b-button variant="link" style="color:blue" v-on:click="showDetails(index)">
                        Detalji
                    </b-button>
                </b-col>
            </b-row>
            <b-row  class="mt-2 " >                
                <b-col sm="11" offset="1">
                    <b-row v-for="serv in organization.services" :key="serv.id">
                        <b-col sm="3">
                            {{serv.nameSr}}   
                        </b-col>
                    </b-row>
                </b-col>      
            </b-row>           
        </div>
           
        
        
    </div>
</template>

<script lang="ts" src="./service-statistic-nio.component.ts">
</script>
