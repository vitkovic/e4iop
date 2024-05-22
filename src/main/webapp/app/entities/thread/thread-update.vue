<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.thread.home.createOrEditLabel" v-text="$t('riportalApp.thread.home.createOrEditLabel')">Create or edit a Thread</h2>
                <div>
                    <div class="form-group" v-if="thread.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="thread.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.thread.subject')" for="thread-subject">Subject</label>
                        <input type="text" class="form-control" name="subject" id="thread-subject"
                            :class="{'valid': !$v.thread.subject.$invalid, 'invalid': $v.thread.subject.$invalid }" v-model="$v.thread.subject.$model"  required/>
                        <div v-if="$v.thread.subject.$anyDirty && $v.thread.subject.$invalid">
                            <small class="form-text text-danger" v-if="!$v.thread.subject.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('riportalApp.thread.advertisement')" for="thread-advertisement">Advertisement</label>
                        <select class="form-control" id="thread-advertisement" multiple name="advertisement" v-model="thread.advertisements">
                            <option v-bind:value="getSelected(thread.advertisements, advertisementOption)" v-for="advertisementOption in advertisements" :key="advertisementOption.id">{{advertisementOption.title}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="'Company Sender'" for="thread-company-sender">Company Sender</label>
                        <select class="form-control" id="thread-company-sender" name="company-sender" v-model="thread.companySender">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="thread.companySender && companyOption.id === thread.companySender.id ? thread.companySender : companyOption" v-for="companyOption in companies" :key="companyOption.id">{{companyOption.name}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="'Company Receiver'" for="thread-company-receiver">Company Sender</label>
                        <select class="form-control" id="thread-company-receiver" name="company-receiver" v-model="thread.companyReceiver">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="thread.companyReceiver && companyOption.id === thread.companyReceiver.id ? thread.companyReceiver : companyOption" v-for="companyOption in companies" :key="companyOption.id">{{companyOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.thread.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./thread-update.component.ts">
</script>
