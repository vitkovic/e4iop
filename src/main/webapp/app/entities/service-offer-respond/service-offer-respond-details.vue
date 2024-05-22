<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="serviceOfferRespond">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.serviceOfferRespond.detail.title')">ServiceOfferRespond</span> {{serviceOfferRespond.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.serviceOfferRespond.date')">Date</span>
                    </dt>
                    <dd>
                        <span v-if="serviceOfferRespond.date">{{$d(Date.parse(serviceOfferRespond.date), 'short') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceOfferRespond.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{serviceOfferRespond.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.serviceOfferRespond.offerAnswer')">Offer Answer</span>
                    </dt>
                    <dd>
                        <div v-if="serviceOfferRespond.offerAnswer">
                            {{serviceOfferRespond.offerAnswer.answer}}
                        </div>
                    </dd>                                      
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <!--
                <router-link v-if="serviceOfferRespond.id" :to="{name: 'ServiceOfferRespondEdit', params: {serviceOfferRespondId: serviceOfferRespond.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
                -->
                <router-link  
                    :to="{name: 'RequestForServiceZbirno', params: {requestForServiceId: serviceOfferRespond.requestRespond.requestForService.id}}"  tag="button" class="btn btn-info btn-md edit">
                    <span class="d-none d-md-inline" v-text="$t('entity.action.zbirniPrikaz')">Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./service-offer-respond-details.component.ts">
</script>
