<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.collaboration.home.createOrEditLabel" v-text="$t('riportalApp.collaboration.home.createOrEditLabel')">Create or edit a Collaboration</h2>
                <div>
                    <div class="form-group" v-if="collaboration.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="collaboration.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.datetime')" for="collaboration-datetime">Datetime</label>
                        <div class="d-flex">
                            <input id="collaboration-datetime" type="datetime-local" class="form-control" name="datetime" :class="{'valid': !$v.collaboration.datetime.$invalid, 'invalid': $v.collaboration.datetime.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.collaboration.datetime.$model)"
                            @change="updateInstantField('datetime', $event)"/>
                        </div>
                        <div v-if="$v.collaboration.datetime.$anyDirty && $v.collaboration.datetime.$invalid">
                            <small class="form-text text-danger" v-if="!$v.collaboration.datetime.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.collaboration.datetime.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="'Is Accepted'" for="collaboration-isAccepted">Is Accepted</label>
                        <input type="checkbox" class="form-check" name="isAccepted" id="collaboration-isAccepted"
                            :class="{'valid': !$v.collaboration.isAccepted.$invalid, 'invalid': $v.collaboration.isAccepted.$invalid }" v-model="$v.collaboration.isAccepted.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.commentOffer')" for="collaboration-commentOffer">Comment Offer</label>
                        <input type="text" class="form-control" name="commentOffer" id="collaboration-commentOffer"
                            :class="{'valid': !$v.collaboration.commentOffer.$invalid, 'invalid': $v.collaboration.commentOffer.$invalid }" v-model="$v.collaboration.commentOffer.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.commentRequest')" for="collaboration-commentRequest">Comment Request</label>
                        <input type="text" class="form-control" name="commentRequest" id="collaboration-commentRequest"
                            :class="{'valid': !$v.collaboration.commentRequest.$invalid, 'invalid': $v.collaboration.commentRequest.$invalid }" v-model="$v.collaboration.commentRequest.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.companyOffer')" for="collaboration-companyOffer">Company Offer</label>
                        <select class="form-control" id="collaboration-companyOffer" name="companyOffer" v-model="collaboration.companyOffer">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="collaboration.companyOffer && companyOption.id === collaboration.companyOffer.id ? collaboration.companyOffer : companyOption" v-for="companyOption in companies" :key="companyOption.id">{{companyOption.name}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.companyRequest')" for="collaboration-companyRequest">Company Request</label>
                        <select class="form-control" id="collaboration-companyRequest" name="companyRequest" v-model="collaboration.companyRequest">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="collaboration.companyRequest && companyOption.id === collaboration.companyRequest.id ? collaboration.companyRequest : companyOption" v-for="companyOption in companies" :key="companyOption.id">{{companyOption.name}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.advertisement')" for="collaboration-advertisement">Advertisement</label>
                        <select class="form-control" id="collaboration-advertisement" name="advertisement" v-model="collaboration.advertisement">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="collaboration.advertisement && advertisementOption.id === collaboration.advertisement.id ? collaboration.advertisement : advertisementOption" v-for="advertisementOption in advertisements" :key="advertisementOption.id">{{advertisementOption.title}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.ratingOffer')" for="collaboration-ratingOffer">Rating Offer</label>
                        <select class="form-control" id="collaboration-ratingOffer" name="ratingOffer" v-model="collaboration.ratingOffer">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="collaboration.ratingOffer && collaborationRatingOption.id === collaboration.ratingOffer.id ? collaboration.ratingOffer : collaborationRatingOption" v-for="collaborationRatingOption in collaborationRatings" :key="collaborationRatingOption.id">{{collaborationRatingOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.collaboration.ratingRequest')" for="collaboration-ratingRequest">Rating Request</label>
                        <select class="form-control" id="collaboration-ratingRequest" name="ratingRequest" v-model="collaboration.ratingRequest">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="collaboration.ratingRequest && collaborationRatingOption.id === collaboration.ratingRequest.id ? collaboration.ratingRequest : collaborationRatingOption" v-for="collaborationRatingOption in collaborationRatings" :key="collaborationRatingOption.id">{{collaborationRatingOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="'Status'" for="collaboration-status">Status</label>
                        <select class="form-control" id="collaboration-status" name="status" v-model="collaboration.status">
                        <option v-bind:value="null"></option>
                        <option
                            v-bind:value="
                            collaboration.status && collaborationStatusOption.id === collaboration.status.id
                                ? collaboration.status
                                : collaborationStatusOption
                            "
                            v-for="collaborationStatusOption in collaborationStatuses"
                            :key="collaborationStatusOption.id"
                            >{{ collaborationStatusOption.status }}</option
                        >
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.collaboration.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./collaboration-update.component.ts">
</script>
