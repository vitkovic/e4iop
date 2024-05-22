<template>
    <div class="row justify-content-center">
        <div class="col-10">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riProcurementProposal.home.createOrEditLabel" v-text="$t('riportalApp.riProcurementProposal.home.createOrEditLabel')">Create or edit a RiProcurementProposal</h2>
                <div>
                    <div class="form-group" v-if="riProcurementProposal.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riProcurementProposal.id" readonly />
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riProcurementProposal.date')" for="ri-procurement-proposal-date">Date</label>
                        <div class="d-flex">
                            <input id="ri-procurement-proposal-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.riProcurementProposal.date.$invalid, 'invalid': $v.riProcurementProposal.date.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.riProcurementProposal.date.$model)"
                            @change="updateZonedDateTimeField('date', $event)"/>
                        </div>
                    </div>
                    -->
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riProcurementProposal.name')" for="ri-procurement-proposal-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ri-procurement-proposal-name"
                            :class="{'valid': !$v.riProcurementProposal.name.$invalid, 'invalid': $v.riProcurementProposal.name.$invalid }" v-model="$v.riProcurementProposal.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riProcurementProposal.technicalSpecification')" for="technicalSpecification">Technical Specification</label>
                        <b-form-textarea id="technicalSpecification" v-model="$v.riProcurementProposal.technicalSpecification.$model" placeholder="" rows="4" 
                                max-rows="4"
                                :class="{'valid': !$v.riProcurementProposal.technicalSpecification.$invalid, 'invalid': $v.riProcurementProposal.technicalSpecification.$invalid }"
                        ></b-form-textarea>
                    </div>

                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riProcurementProposal.purpose')" for="ri-procurement-proposal-purpose">Purpose</label>
                        <input type="text" class="form-control" name="purpose" id="ri-procurement-proposal-purpose"
                            :class="{'valid': !$v.riProcurementProposal.purpose.$invalid, 'invalid': $v.riProcurementProposal.purpose.$invalid }" v-model="$v.riProcurementProposal.purpose.$model" />
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riProcurementProposal.portalUser')" for="ri-procurement-proposal-portalUser">Portal User</label>
                        <select class="form-control" id="ri-procurement-proposal-portalUser" name="portalUser" v-model="riProcurementProposal.portalUser">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riProcurementProposal.portalUser && portalUserOption.id === riProcurementProposal.portalUser.id ? riProcurementProposal.portalUser : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    -->
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riProcurementProposal.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-procurement-proposal-update.component.ts">
</script>
