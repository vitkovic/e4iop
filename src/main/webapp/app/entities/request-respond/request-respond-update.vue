<template>
    <div class="row justify-content-center">
        <div class="col-10">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 style="display: inline" id="riportalApp.requestRespond.home.createOrEditLabel" v-text="$t('riportalApp.requestRespond.home.createOrEditLabel')">Create or edit a RequestRespond</h2>    
                <router-link class="link-style" v-if="requestRespond.requestForService" :to="{name: 'RequestForServiceView', params: {requestForServiceId: requestRespond.requestForService.id}}">
                    <h2  style="display: inline;">{{requestRespond.requestForService.id}}</h2>
                </router-link>
                <b-form-row class="mt-4">
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.requestRespond.date')" for="request-respond-date">Date</label>
                            <div class="d-flex"> 
                                <date-picker  style="width: -webkit-fill-available;" format="DD.MM.YYYY HH:mm" v-model="$v.requestRespond.date.$model" type="datetime"
                                    required                                     
                                    >
                                </date-picker>                                                               
                            </div> 
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.requestRespond.startTime')" for="request-respond-startTime">Start Time</label>
                            <div class="d-flex"> 
                                <date-picker  style="width: -webkit-fill-available;" format="DD.MM.YYYY HH:mm" v-model="$v.requestRespond.startTime.$model" type="datetime"
                                    required                                     
                                    >
                                </date-picker>                                                               
                            </div> 
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.requestRespond.endTime')" for="request-respond-endTime">End Time</label>
                            <div class="d-flex"> 
                                <date-picker  style="width: -webkit-fill-available;" format="DD.MM.YYYY HH:mm" v-model="$v.requestRespond.endTime.$model" type="datetime"
                                    required                                     
                                    >
                                </date-picker>                                                               
                            </div> 
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.requestRespond.price')" for="request-respond-price">Price</label>
                            <input type="number" class="form-control" name="price" id="request-respond-price"
                                :class="{'valid': !$v.requestRespond.price.$invalid, 'invalid': $v.requestRespond.price.$invalid }" v-model.number="$v.requestRespond.price.$model" />
                        </div>
                    </b-col>
                   
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.requestAnswer.answer')" for="service-offer-respond-requestAnswer">Request Answer</label>
                            <select class="form-control" id="service-offer-respond-requestAnswer" name="requestAnswer" v-model="requestRespond.requestAnswer">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="requestRespond.requestAnswer && requestAnswerOption.id === requestRespond.requestAnswer.id ? requestRespond.requestAnswer : requestAnswerOption" v-for="requestAnswerOption in requestAnswers" :key="requestAnswerOption.id">{{requestAnswerOption.answer}}</option>
                            </select>
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row>
                    <b-col sm="12">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.requestRespond.message')" for="request-respond-message">Message</label>
                            <b-form-textarea id="request-respond-message" v-model="$v.requestRespond.message.$model"  rows="4" 
                                max-rows="6"                                
                                :class="{'valid': !$v.requestRespond.message.$invalid, 'invalid': $v.requestRespond.message.$invalid }">
                            </b-form-textarea>

                        </div>
                    </b-col>
                </b-form-row>
                



                <div>
                    <!--
                    <div class="form-group" v-if="requestRespond.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="requestRespond.id" readonly />
                    </div>
                    -->
                    
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.requestRespond.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                    <button type="button" v-if="hasAnyRole(['RPRI','RPRIPO'])" id="cancel-save" class="btn btn-info" v-on:click="sendToServiceOfferRespond()">
                        <span v-text="$t('riportalApp.requestRespond.respondsToOffer')"></span>
                    </button>
                    <router-link  
                        :to="{name: 'RequestForServiceZbirno', params: {requestForServiceId: requestRespond.requestForService.id}}"  tag="button" class="btn btn-info btn-md edit">
                        <span class="d-none d-md-inline" v-text="$t('entity.action.zbirniPrikaz')">Edit</span>
                    </router-link>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./request-respond-update.component.ts">
</script>
