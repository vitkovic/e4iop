<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="researchInstitution">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.researchInstitution.detail.title')">ResearchInstitution</span> {{researchInstitution.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('riportalApp.researchInstitution.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{researchInstitution.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('riportalApp.researchInstitution.researchers')">Researchers</span>
                    </dt>
                    <dd>
                        <span v-for="(researchers, i) in researchInstitution.researchers" :key="researchers.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'ResearcherView', params: {researcherId: researchers.id}}">{{researchers.id}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="researchInstitution.id" :to="{name: 'ResearchInstitutionEdit', params: {researchInstitutionId: researchInstitution.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./research-institution-details.component.ts">
</script>
