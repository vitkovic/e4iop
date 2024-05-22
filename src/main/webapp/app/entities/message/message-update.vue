<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.message.home.createOrEditLabel" v-text="$t('riportalApp.message.home.createOrEditLabel')">Create or edit a Message</h2>
                <div>
                    <div class="form-group" v-if="message.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="message.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.datetime')" for="message-datetime">Datetime</label>
                        <div class="d-flex">
                            <input id="message-datetime" type="datetime-local" class="form-control" name="datetime" :class="{'valid': !$v.message.datetime.$invalid, 'invalid': $v.message.datetime.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.message.datetime.$model)"
                            @change="updateInstantField('datetime', $event)"/>
                        </div>
                        <div v-if="$v.message.datetime.$anyDirty && $v.message.datetime.$invalid">
                            <small class="form-text text-danger" v-if="!$v.message.datetime.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.message.datetime.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.content')" for="message-content">Content</label>
                        <input type="text" class="form-control" name="content" id="message-content"
                            :class="{'valid': !$v.message.content.$invalid, 'invalid': $v.message.content.$invalid }" v-model="$v.message.content.$model"  required/>
                        <div v-if="$v.message.content.$anyDirty && $v.message.content.$invalid">
                            <small class="form-text text-danger" v-if="!$v.message.content.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.isRead')" for="message-isRead">Is Read</label>
                        <input type="checkbox" class="form-check" name="isRead" id="message-isRead"
                            :class="{'valid': !$v.message.isRead.$invalid, 'invalid': $v.message.isRead.$invalid }" v-model="$v.message.isRead.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.isDeletedSender')" for="message-isDeletedSender">Is Deleted Sender</label>
                        <input type="checkbox" class="form-check" name="isDeletedSender" id="message-isDeletedSender"
                            :class="{'valid': !$v.message.isDeletedSender.$invalid, 'invalid': $v.message.isDeletedSender.$invalid }" v-model="$v.message.isDeletedSender.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.isDeletedReceiver')" for="message-isDeletedReceiver">Is Deleted Receiver</label>
                        <input type="checkbox" class="form-check" name="isDeletedReceiver" id="message-isDeletedReceiver"
                            :class="{'valid': !$v.message.isDeletedReceiver.$invalid, 'invalid': $v.message.isDeletedReceiver.$invalid }" v-model="$v.message.isDeletedReceiver.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.thread')" for="message-thread">Thread</label>
                        <select class="form-control" id="message-thread" name="thread" v-model="message.thread">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="message.thread && threadOption.id === message.thread.id ? message.thread : threadOption" v-for="threadOption in threads" :key="threadOption.id">{{threadOption.subject}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.portalUserSender')" for="message-portalUserSender">Portal User Sender</label>
                        <select class="form-control" id="message-portalUserSender" name="portalUserSender" v-model="message.portalUserSender">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="message.portalUserSender && portalUserOption.id === message.portalUserSender.id ? message.portalUserSender : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.firstname}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.message.portalUserReceiver')" for="message-portalUserReceiver">Portal User Receiver</label>
                        <select class="form-control" id="message-portalUserReceiver" name="portalUserReceiver" v-model="message.portalUserReceiver">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="message.portalUserReceiver && portalUserOption.id === message.portalUserReceiver.id ? message.portalUserReceiver : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.firstname}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.message.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./message-update.component.ts">
</script>
