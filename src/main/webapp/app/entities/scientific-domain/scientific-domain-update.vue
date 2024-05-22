<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.scientificDomain.home.createOrEditLabel" v-text="$t('riportalApp.scientificDomain.home.createOrEditLabel')">Create or edit a ScientificDomain</h2>
                <div>
                    <div class="form-group" v-if="scientificDomain.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="scientificDomain.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.scientificDomain.domain')" for="scientific-domain-domain">Domain</label>
                        <input type="text" class="form-control" name="domain" id="scientific-domain-domain"
                            :class="{'valid': !$v.scientificDomain.domain.$invalid, 'invalid': $v.scientificDomain.domain.$invalid }" v-model="$v.scientificDomain.domain.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.scientificDomain.domainEn')" for="scientific-domain-domainEn">Domain En</label>
                        <input type="text" class="form-control" name="domainEn" id="scientific-domain-domainEn"
                            :class="{'valid': !$v.scientificDomain.domainEn.$invalid, 'invalid': $v.scientificDomain.domainEn.$invalid }" v-model="$v.scientificDomain.domainEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.scientificDomain.domainCyr')" for="scientific-domain-domainCyr">Domain Cyr</label>
                        <input type="text" class="form-control" name="domainCyr" id="scientific-domain-domainCyr"
                            :class="{'valid': !$v.scientificDomain.domainCyr.$invalid, 'invalid': $v.scientificDomain.domainCyr.$invalid }" v-model="$v.scientificDomain.domainCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.scientificDomain.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./scientific-domain-update.component.ts">
</script>
