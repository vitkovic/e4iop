<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="collaboration">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.collaboration.detail.title')">Collaboration</span> {{collaboration.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.datetime')">Datetime</span>
                    </dt>
                    <dd>
                        <span v-if="collaboration.datetime">{{$d(Date.parse(collaboration.datetime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="'Is Accepted'">Is Accepted</span>
                    </dt>
                    <dd>
                        <span>{{collaboration.isAccepted}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.commentOffer')">Comment Offer</span>
                    </dt>
                    <dd>
                        <span>{{collaboration.commentOffer}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.commentRequest')">Comment Request</span>
                    </dt>
                    <dd>
                        <span>{{collaboration.commentRequest}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.companyOffer')">Company Offer</span>
                    </dt>
                    <dd>
                        <div v-if="collaboration.companyOffer">
                            <router-link :to="{name: 'CompanyView', params: {companyId: collaboration.companyOffer.id}}">{{collaboration.companyOffer.name}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.companyRequest')">Company Request</span>
                    </dt>
                    <dd>
                        <div v-if="collaboration.companyRequest">
                            <router-link :to="{name: 'CompanyView', params: {companyId: collaboration.companyRequest.id}}">{{collaboration.companyRequest.name}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.advertisement')">Advertisement</span>
                    </dt>
                    <dd>
                        <div v-if="collaboration.advertisement">
                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: collaboration.advertisement.id}}">{{collaboration.advertisement.title}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.ratingOffer')">Rating Offer</span>
                    </dt>
                    <dd>
                        <div v-if="collaboration.ratingOffer">
                            <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaboration.ratingOffer.id}}">{{collaboration.ratingOffer.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.collaboration.ratingRequest')">Rating Request</span>
                    </dt>
                    <dd>
                        <div v-if="collaboration.ratingRequest">
                            <router-link :to="{name: 'CollaborationRatingView', params: {collaborationRatingId: collaboration.ratingRequest.id}}">{{collaboration.ratingRequest.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="'Status'">Status</span>
                    </dt>
                    <dd>
                        <div v-if="collaboration.status">
                            <span>{{ collaboration.status.status }}</span>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="collaboration.id" :to="{name: 'CollaborationEdit', params: {collaborationId: collaboration.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./collaboration-details.component.ts">
</script>
