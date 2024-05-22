<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.serviceProposal.home.createOrEditLabel" v-text="$t('riportalApp.serviceProposal.home.createOrEditLabel')">Create or edit a ServiceProposal</h2>
                <div>
                    <div class="form-group" v-if="serviceProposal.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="serviceProposal.id" readonly />
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceProposal.date')" for="service-proposal-date">Date</label>
                        <div class="d-flex">
                            <input id="service-proposal-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.serviceProposal.date.$invalid, 'invalid': $v.serviceProposal.date.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.serviceProposal.date.$model)"
                            @change="updateZonedDateTimeField('date', $event)"/>
                        </div>
                    </div>
                    -->
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceProposal.name')" for="service-proposal-name">Name</label>
                        <input type="text" class="form-control" name="name" id="service-proposal-name"
                            :class="{'valid': !$v.serviceProposal.name.$invalid, 'invalid': $v.serviceProposal.name.$invalid }" v-model="$v.serviceProposal.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceProposal.description')" for="service-proposal-description">Description</label>
                        <input type="text" class="form-control" name="description" id="service-proposal-description"
                            :class="{'valid': !$v.serviceProposal.description.$invalid, 'invalid': $v.serviceProposal.description.$invalid }" v-model="$v.serviceProposal.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceProposal.purpose')" for="service-proposal-purpose">Purpose</label>
                        <input type="text" class="form-control" name="purpose" id="service-proposal-purpose"
                            :class="{'valid': !$v.serviceProposal.purpose.$invalid, 'invalid': $v.serviceProposal.purpose.$invalid }" v-model="$v.serviceProposal.purpose.$model" />
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceProposal.portalUser')" for="service-proposal-portalUser">Portal User</label>
                        <select class="form-control" id="service-proposal-portalUser" name="portalUser" v-model="serviceProposal.portalUser">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="serviceProposal.portalUser && portalUserOption.id === serviceProposal.portalUser.id ? serviceProposal.portalUser : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.id}}</option>
                        </select>
                    </div>
                    -->
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.serviceProposal.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./service-proposal-update.component.ts">
</script>
