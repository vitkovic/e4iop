<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.serviceType.home.createOrEditLabel" v-text="$t('riportalApp.serviceType.home.createOrEditLabel')">Create or edit a ServiceType</h2>
                <div>
                    <div class="form-group" v-if="serviceType.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="serviceType.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceType.type')" for="service-type-type">Type</label>
                        <input type="text" class="form-control" name="type" id="service-type-type"
                            :class="{'valid': !$v.serviceType.type.$invalid, 'invalid': $v.serviceType.type.$invalid }" v-model="$v.serviceType.type.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceType.typeEn')" for="service-type-typeEn">Type En</label>
                        <input type="text" class="form-control" name="typeEn" id="service-type-typeEn"
                            :class="{'valid': !$v.serviceType.typeEn.$invalid, 'invalid': $v.serviceType.typeEn.$invalid }" v-model="$v.serviceType.typeEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceType.typeCyr')" for="service-type-typeCyr">Type Cyr</label>
                        <input type="text" class="form-control" name="typeCyr" id="service-type-typeCyr"
                            :class="{'valid': !$v.serviceType.typeCyr.$invalid, 'invalid': $v.serviceType.typeCyr.$invalid }" v-model="$v.serviceType.typeCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.serviceType.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./service-type-update.component.ts">
</script>
