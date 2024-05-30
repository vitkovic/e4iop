<template>
    <div>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div v-if="company" class="d-flex mb-3">
            <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)"
                alt="company logo" 
                style="height: 100px; margin-right: 1rem">
            <h2 id="page-heading"
                class="jh-entity-heading"
                style="align-self: center; text-wrap: nowrap;"> 
                {{company.name}}
            </h2>
        </div>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && collaborations && collaborations.length === 0">
            <span v-text="$t('riportalApp.collaboration.home.notFound')">No collaborations found</span>
        </div>

        <div class="ml-3 mb-3" style="display: flex;">
            <h3 v-text="$t('riportalApp.collaboration.home.title')" class="mr-5">Saradnje</h3>
            <b-form-group class="pr-5 border-left pl-5" label-size="lg" :label="$t('riportalApp.collaboration.filters.role')" v-slot="{ ariaDescribedby }">
                <b-form-checkbox v-model="companyOfferFlag">
                    <span v-text="$t('riportalApp.collaboration.filters.companyOffer')">Oglašivač</span>
                </b-form-checkbox>
                <b-form-checkbox  v-model="companyRequestFlag">
                    <span v-text="$t('riportalApp.collaboration.filters.companyRequest')">Tražilac</span>
                </b-form-checkbox>
            </b-form-group>

            <b-form-group class="border-left pl-5" label-size="lg" :label="$t('riportalApp.collaboration.filters.status')" v-slot="{ ariaDescribedby }">
            <b-form-checkbox-group
                id="checkbox-group-2"
                v-model="selectedStatusIds"
                :aria-describedby="ariaDescribedby"
                name="flavour-2"
                stacked
            >
                <b-form-checkbox v-for="option in collaborationsStatusChoices" :key="option.id" :value="option.id">{{ option.status }}</b-form-checkbox>
            </b-form-checkbox-group>
            </b-form-group>
        </div>
        
        <div class="table-responsive" v-if="collaborations && collaborations.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('datetime')"><span v-text="$t('riportalApp.collaboration.datetime')">Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('status.id')"><span v-text="'Status'">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status.status'"></jhi-sort-indicator></th>
                    <!-- <th v-on:click="changeOrder('commentOffer')"><span v-text="$t('riportalApp.collaboration.commentOffer')">Comment Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'commentOffer'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('commentRequest')"><span v-text="$t('riportalApp.collaboration.commentRequest')">Comment Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'commentRequest'"></jhi-sort-indicator></th> -->
                    <th v-on:click="changeOrder('companyOffer.id')"><span v-text="$t('riportalApp.collaboration.companyOffer')">Company Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyOffer.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companyRequest.id')"><span v-text="$t('riportalApp.collaboration.companyRequest')">Company Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyRequest.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('advertisement.name')"><span v-text="$t('riportalApp.collaboration.advertisement')">Advertisement</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'advertisement.name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('advertisement.type.type')"><span v-text="$t('riportalApp.collaboration.advertisementType')">Advertisement</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'advertisement.type.type'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('advertisement.kind.kind')"><span v-text="$t('riportalApp.collaboration.advertisementKind')">Advertisement</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'advertisement.kind.kind'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('advertisement.budget')"><span v-text="$t('riportalApp.collaboration.advertisementBudget')">Advertisement</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'advertisement.budget'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('advertisement.duration.duration')"><span v-text="$t('riportalApp.collaboration.advertisementDuration')">Advertisement</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'advertisement.duration.duration'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('ratingOffer.id')"><span v-text="$t('riportalApp.collaboration.ratingOffer')">Rating Offer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingOffer.number'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('ratingRequest.id')"><span v-text="$t('riportalApp.collaboration.ratingRequest')">Rating Request</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ratingRequest.number'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="collaboration in collaborations"
                    :key="collaboration.id">
                    <!-- <td>
                        <router-link :to="{name: 'CollaborationView', params: {collaborationId: collaboration.id}}">{{collaboration.id}}</router-link>
                    </td> -->
                    <td>{{collaboration.datetime ? $d(Date.parse(collaboration.datetime), { dateStyle: 'short' }) : ''}}</td>
                    <td>
                        <div v-if="collaboration.status">
                            {{collaboration.status.status}}
                        </div>
                    </td>
                    <!-- <td>{{collaboration.commentOffer}}</td>
                    <td>{{collaboration.commentRequest}}</td> -->
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
                        <div v-if="collaboration.advertisement.type">
                            {{collaboration.advertisement.type.type}}
                        </div>
                    </td>
                    <td>
                        <div v-if="collaboration.advertisement.kind">
                            {{collaboration.advertisement.kind.kind}}
                        </div>
                    </td>
                    <td>{{collaboration.advertisement.budget}}</td>
                    <td>{{collaboration.advertisement.duration.duration}}</td>
                    <td>
                        <div v-if="collaboration.ratingOffer">
                            <b-form-rating 
                                disabled
                                :value="collaboration.ratingOffer.number"                 
                                stars="4" 
                                inline
                                size="sm">
                            </b-form-rating>
                        </div>
                    </td>
                    <td>
                        <div v-if="collaboration.ratingRequest">
                            <b-form-rating 
                                disabled
                                :value="collaboration.ratingRequest.number"                 
                                stars="4" 
                                inline
                                size="sm">
                            </b-form-rating>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <b-button
                                v-if="collaboration 
                                && collaboration.status 
                                && (collaboration.status.status === collaborationStatusOptions.PENDING)
                                && collaboration.companyOffer.id === company.id
                                "
                                v-on:click.stop="prepareConfirmCollaboration(collaboration)"
                                variant="success"
                                class="btn btn-sm spacing-subject-btn mr-1 pl-1 pr-1"
                                v-b-modal.confirmCollaboration
                                >
                                <span class="d-none d-md-inline" v-text="$t('riportalApp.collaboration.actions.confirmCollaboration')"
                                    >Potvrdi saradnju</span
                                >
                                </b-button>
                                <b-button
                                v-if="collaboration 
                                    && collaboration.status 
                                    && (collaboration.status.status === collaborationStatusOptions.PENDING)
                                    && collaboration.companyOffer.id === company.id
                                    "
                                v-on:click.stop="prepareCancelCollaboration(collaboration)"
                                variant="danger"
                                class="btn btn-sm spacing-subject-btn mr-1 pl-1 pr-1"
                                v-b-modal.cancelCollaboration
                                >
                                <span class="d-none d-md-inline" v-text="$t('riportalApp.collaboration.actions.cancelCollaboration')"
                                    >Otkaži saradnju</span
                                >
                                </b-button>


                            <b-button v-if="(collaboration.status.status === collaborationStatusOptions.ACCEPTED) && !ratingExists(collaboration)"  v-on:click="prepareRating(collaboration)"
                                   variant="primary"
                                   class="btn btn-sm mr-1"
                                   v-b-modal.ratingEntity>
                                <span class="d-none d-md-inline" v-text="$t('riportalApp.collaboration.actions.rate')">Oceni</span>
                            </b-button>
                            <b-button v-if="(company.id === collaboration.companyOffer.id) && (collaboration.advertisement.status.status === advertisementStatusOptions.INACTIVE)" v-on:click="prepareCopyAd(collaboration)"
                                   variant="primary"
                                   class="btn btn-sm mr-1"
                                   v-b-modal.copyAdModal>
                                <span class="d-none d-md-inline" v-text="$t('riportalApp.collaboration.actions.renewAd')">Obnovi oglas</span>
                            </b-button>
                            <!-- <b-button v-if="ratingExists(collaboration) && company.id === collaboration.advertisement.company.id" v-on:click="prepareRemove(collaboration)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button> -->
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <b-modal v-if="collaborationToChangeStatus" ref="confirmCollaboration" id="confirmCollaboration">
            <span slot="modal-title"
                ><span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.thread.modalConfirm.title')"
                >Da li želite da potvrdite zahtev za saradnju?</span
                ></span
            >
            <div class="modal-body">
                <p id="jhi-delete-advertisement-heading">
                <b>{{ $t('riportalApp.thread.modalConfirm.advertisement') }} </b>{{ collaborationToChangeStatus.advertisement.title }}
                </p>
                <p id="jhi-delete-advertisement-heading">
                <b>{{ $t('riportalApp.thread.modalConfirm.company') }} </b>{{ collaborationToChangeStatus.companyRequest.name }}
                </p>
                <hr v-if="pendingCollaborationsCount > 1"/>
                <b-form-group
                v-if="pendingCollaborationsCount > 1"
                :label="$t('riportalApp.thread.modalConfirm.bodyTitle', { title: collaborationToChangeStatus.advertisement.title })"
                v-slot="{ ariaDescribedby }"
                >
                <b-form-radio v-model="selectedCollRadioBtn" :aria-describedby="ariaDescribedby" name="collaboration-radios" value="da">{{
                    $t('entity.action.yes')
                }}</b-form-radio>
                <b-form-radio v-model="selectedCollRadioBtn" :aria-describedby="ariaDescribedby" name="collaboration-radios" value="ne">{{
                    $t('entity.action.no')
                }}</b-form-radio>
                </b-form-group>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeConfirmCollaboration()">
                Otkaži
                </button>
                <button
                type="button"
                class="btn btn-success"
                id="jhi-confirm-delete-advertisement"
                v-text="$t('entity.action.confirm')"
                v-on:click="confirmCollaboration()"
                >
                Potvrdi
                </button>
            </div>
        </b-modal>
    
        <b-modal v-if="collaborationToChangeStatus" ref="cancelCollaboration" id="cancelCollaboration">
            <span slot="modal-title"
                ><span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.thread.modalCancel.title')"
                >Da li želite da odbijete zahtev za saradnju?</span
                ></span
            >
            <div class="modal-body">
                <p id="jhi-delete-advertisement-heading">
                <b>{{ $t('riportalApp.thread.modalCancel.advertisement') }} </b>{{ collaborationToChangeStatus.advertisement.title }}
                </p>
                <p id="jhi-delete-advertisement-heading">
                <b>{{ $t('riportalApp.thread.modalCancel.company') }} </b>{{ collaborationToChangeStatus.companyRequest.name }}
                </p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeCancelCollaboration()">
                Otkaži
                </button>
                <button
                type="button"
                class="btn btn-success"
                id="jhi-confirm-delete-advertisement"
                v-text="$t('entity.action.confirm')"
                v-on:click="cancelCollaboration()"
                >
                Potvrdi
                </button>
            </div>
        </b-modal>

        <b-modal v-if="collaborationToRate" ref="ratingEntity" id="ratingEntity" >
            <span slot="modal-title"><span v-text="'Ocenite saradnju'">Ocenite saradnju</span></span>
            <div class="modal-body">       
                <p v-if="company.id != collaborationToRate.companyOffer.id"><b>Oglašivač: </b>{{ collaborationToRate.companyOffer.name }}</p>
                <p v-if="company.id != collaborationToRate.companyRequest.id"><b>Tražilac: </b>{{ collaborationToRate.companyRequest.name }}</p>
                <p><b>Oglas: </b>{{ collaborationToRate.advertisement.title }}</p>
                <div>
                <b-dropdown text="Izaberite ocenu" class="mb-3">
                    <b-dropdown-item v-for="rating in collaborationRatings" :key="rating.id" @click="selectRating(rating)">
                        <b-form-rating
                            disabled
                            :value="rating.number"                 
                            stars="4" 
                            inline
                            size="sm">
                        </b-form-rating>
                        <span class="ml-2">{{ rating.description }}</span>
                    </b-dropdown-item>
                </b-dropdown>
                <div v-if="selectedRating" class="mb-3">
                    <b-form-rating
                            disabled
                            :value="selectedRating.number"                 
                            stars="4" 
                            inline
                            size="sm">
                        </b-form-rating>
                        <span class="ml-2">{{ selectedRating.description }}</span>
                </div>
                <div v-else class="mb-3">
                    <span style="color: red;">Ocena nije izabrana</span>
                </div>
                </div>
                <b-form-textarea
                    id="textarea"
                    v-model="ratingComment"
                    placeholder="Komentar saradnje...">
                </b-form-textarea>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="'Oceni'" v-on:click="rateCollaboration()">Oceni</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-collaboration" v-text="'Otkaži'" v-on:click="closeRatingDialog()">Otkaži</button>
            </div>
        </b-modal>

        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.collaboration.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-collaboration-heading" v-text="$t('riportalApp.collaboration.delete.question', {'id': removeId})">Are you sure you want to delete this Collaboration?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-collaboration" v-text="$t('entity.action.delete')" v-on:click="removeCollaboration()">Delete</button>
            </div>
        </b-modal>

        <b-modal ref="copyAdModal" id="copyAdModal" >
            <span slot="modal-title"><span id="riportalApp.collaboration.delete.question" v-text="'Potvrdite obnovu oglasa'">Potvrdite obnovu oglasa</span></span>
            <div class="modal-body">
                <p v-if="advertisement" id="jhi-delete-collaboration-heading" v-text="'Da li želite da obnovite oglas ' + advertisement.title + '?'">Da li želite da obnovite oglas?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeCopyAd()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-collaboration" v-text="'Potvrdi'" v-on:click="confirmCopyAd()">Delete</button>
            </div>
        </b-modal>
        <div v-show="collaborations && collaborations.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company-collaborations.component.ts">
</script>

<style scoped>
.b-rating.disabled {
  color: rgb(40, 40, 56);
}
</style>
