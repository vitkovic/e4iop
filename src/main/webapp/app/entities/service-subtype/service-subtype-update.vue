<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.serviceSubtype.home.createOrEditLabel" v-text="$t('riportalApp.serviceSubtype.home.createOrEditLabel')">Create or edit a ServiceSubtype</h2>
                <div>
                    <div class="form-group" v-if="serviceSubtype.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="serviceSubtype.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceSubtype.subtype')" for="service-subtype-subtype">Subtype</label>
                        <input type="text" class="form-control" name="subtype" id="service-subtype-subtype"
                            :class="{'valid': !$v.serviceSubtype.subtype.$invalid, 'invalid': $v.serviceSubtype.subtype.$invalid }" v-model="$v.serviceSubtype.subtype.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceSubtype.subtypeEn')" for="service-subtype-subtypeEn">Subtype En</label>
                        <input type="text" class="form-control" name="subtypeEn" id="service-subtype-subtypeEn"
                            :class="{'valid': !$v.serviceSubtype.subtypeEn.$invalid, 'invalid': $v.serviceSubtype.subtypeEn.$invalid }" v-model="$v.serviceSubtype.subtypeEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceSubtype.subtypeCyr')" for="service-subtype-subtypeCyr">Subtype Cyr</label>
                        <input type="text" class="form-control" name="subtypeCyr" id="service-subtype-subtypeCyr"
                            :class="{'valid': !$v.serviceSubtype.subtypeCyr.$invalid, 'invalid': $v.serviceSubtype.subtypeCyr.$invalid }" v-model="$v.serviceSubtype.subtypeCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.serviceSubtype.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./service-subtype-update.component.ts">
</script>
