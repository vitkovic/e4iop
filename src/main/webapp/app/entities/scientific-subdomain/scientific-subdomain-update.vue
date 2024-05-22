<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.scientificSubdomain.home.createOrEditLabel" v-text="$t('riportalApp.scientificSubdomain.home.createOrEditLabel')">Create or edit a ScientificSubdomain</h2>
                <div>
                    <div class="form-group" v-if="scientificSubdomain.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="scientificSubdomain.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.scientificSubdomain.subdomain')" for="scientific-subdomain-subdomain">Subdomain</label>
                        <input type="text" class="form-control" name="subdomain" id="scientific-subdomain-subdomain"
                            :class="{'valid': !$v.scientificSubdomain.subdomain.$invalid, 'invalid': $v.scientificSubdomain.subdomain.$invalid }" v-model="$v.scientificSubdomain.subdomain.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.scientificSubdomain.subdomainEn')" for="scientific-subdomain-subdomainEn">Subdomain En</label>
                        <input type="text" class="form-control" name="subdomainEn" id="scientific-subdomain-subdomainEn"
                            :class="{'valid': !$v.scientificSubdomain.subdomainEn.$invalid, 'invalid': $v.scientificSubdomain.subdomainEn.$invalid }" v-model="$v.scientificSubdomain.subdomainEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.scientificSubdomain.subdomainCyr')" for="scientific-subdomain-subdomainCyr">Subdomain Cyr</label>
                        <input type="text" class="form-control" name="subdomainCyr" id="scientific-subdomain-subdomainCyr"
                            :class="{'valid': !$v.scientificSubdomain.subdomainCyr.$invalid, 'invalid': $v.scientificSubdomain.subdomainCyr.$invalid }" v-model="$v.scientificSubdomain.subdomainCyr.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.scientificSubdomain.domain')" for="scientific-subdomain-domain">Domain</label>
                        <select class="form-control" id="scientific-subdomain-domain" name="domain" v-model="scientificSubdomain.domain">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="scientificSubdomain.domain && scientificDomainOption.id === scientificSubdomain.domain.id ? scientificSubdomain.domain : scientificDomainOption" v-for="scientificDomainOption in scientificDomains" :key="scientificDomainOption.id">{{scientificDomainOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.scientificSubdomain.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./scientific-subdomain-update.component.ts">
</script>
