<template>
    <div>        
        <b-row>
            <b-col sm="8">
                <h2 id="page-heading">
                    <span v-text="$t('riportalApp.reports.serviceStatistic')" id="ri-service-heading"></span>
                </h2>
            </b-col>
            <b-col sm="4"  class="text-right">
                <button  type="button" id="stampanje" class="btn btn-primary" 
                    v-on:click="stampanje()">
                    <span v-text="$t('riportalApp.reports.downloadExcel')"></span>
                </button>
            </b-col>
        </b-row>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>                     
        <b-row style="margin-top:10px">
            <b-col>
                <b-table striped 
                    :items="services" 
                    :filter="filter" 
                    :per-page="perPage" 
                    :fields="fields"
                    :current-page="currentPage" >

                     <template #cell(requestRating)="data">                        
                        <b-form-rating  
                             inline no-border v-model="data.item.requestRating" readonly show-value precision="1" 
                             show-value-max variant="warning" class="mb-2">
                        </b-form-rating>
                    </template>

                </b-table>
                <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage"></b-pagination>
            </b-col>
        </b-row>
       
        
    </div>
</template>

<script lang="ts" src="./service-statistic.component.ts">
</script>
