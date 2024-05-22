<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="thread">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.thread.detail.title')">Thread</span> {{thread.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.thread.subject')">Subject</span>
                    </dt>
                    <dd>
                        <span>{{thread.subject}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.thread.advertisement')">Advertisement</span>
                    </dt>
                    <dd>
                        <span v-for="(advertisement, i) in thread.advertisements" :key="advertisement.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisement.id}}">{{advertisement.id}}</router-link>
                        </span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.thread.company')">Company</span>
                    </dt>
                    <dd>
                        <div v-if="thread.company">
                            <router-link :to="{name: 'CompanyView', params: {companyId: thread.company.id}}">{{thread.company.name}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="thread.id" :to="{name: 'ThreadEdit', params: {threadId: thread.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./thread-details.component.ts">
</script>
