<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="message">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.message.detail.title')">Message</span> {{message.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.message.datetime')">Datetime</span>
                    </dt>
                    <dd>
                        <span v-if="message.datetime">{{$d(Date.parse(message.datetime), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.message.content')">Content</span>
                    </dt>
                    <dd>
                        <span>{{message.content}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.message.isRead')">Is Read</span>
                    </dt>
                    <dd>
                        <span>{{message.isRead}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.message.isDeletedSender')">Is Deleted Sender</span>
                    </dt>
                    <dd>
                        <span>{{message.isDeletedSender}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.message.isDeletedReceiver')">Is Deleted Receiver</span>
                    </dt>
                    <dd>
                        <span>{{message.isDeletedReceiver}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.message.thread')">Thread</span>
                    </dt>
                    <dd>
                        <div v-if="message.thread">
                            <router-link :to="{name: 'ThreadView', params: {threadId: message.thread.id}}">{{message.thread.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.message.portalUserSender')">Portal User Sender</span>
                    </dt>
                    <dd>
                        <div v-if="message.portalUserSender">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: message.portalUserSender.id}}">{{message.portalUserSender.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.message.portalUserReceiver')">Portal User Receiver</span>
                    </dt>
                    <dd>
                        <div v-if="message.portalUserReceiver">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: message.portalUserReceiver.id}}">{{message.portalUserReceiver.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="message.id" :to="{name: 'MessageEdit', params: {messageId: message.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./message-details.component.ts">
</script>
