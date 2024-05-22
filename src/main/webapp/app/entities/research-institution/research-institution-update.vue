<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.researchInstitution.home.createOrEditLabel" v-text="$t('riportalApp.researchInstitution.home.createOrEditLabel')">Create or edit a ResearchInstitution</h2>
                <div>
                    <div class="form-group" v-if="researchInstitution.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="researchInstitution.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.researchInstitution.name')" for="research-institution-name">Name</label>
                        <input type="text" class="form-control" name="name" id="research-institution-name"
                            :class="{'valid': !$v.researchInstitution.name.$invalid, 'invalid': $v.researchInstitution.name.$invalid }" v-model="$v.researchInstitution.name.$model" />
                    </div>
                    <div class="form-group">
                        <label v-text="$t('riportalApp.researchInstitution.researchers')" for="research-institution-researchers">Researchers</label>
                        <select class="form-control" id="research-institution-researchers" multiple name="researchers" v-model="researchInstitution.researchers">
                            <option v-bind:value="getSelected(researchInstitution.researchers, researcherOption)" v-for="researcherOption in researchers" :key="researcherOption.id">{{researcherOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.researchInstitution.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./research-institution-update.component.ts">
</script>
