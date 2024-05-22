<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="researcher">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.researcher.detail.title')">Researcher</span> {{researcher.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.researcher.firstName')">First Name</span>
                    </dt>
                    <dd>
                        <span>{{researcher.firstName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.researcher.lastName')">Last Name</span>
                    </dt>
                    <dd>
                        <span>{{researcher.lastName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.researcher.middleName')">Middle Name</span>
                    </dt>
                    <dd>
                        <span>{{researcher.middleName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.researcher.birthDate')">Birth Date</span>
                    </dt>
                    <dd>
                        <span>{{researcher.birthDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="researcher.id" :to="{name: 'ResearcherEdit', params: {researcherId: researcher.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./researcher-details.component.ts">
</script>
