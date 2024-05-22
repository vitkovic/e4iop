<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riAccessType.home.createOrEditLabel" v-text="$t('riportalApp.riAccessType.home.createOrEditLabel')">Create or edit a RiAccessType</h2>
                <div>
                    <div class="form-group" v-if="riAccessType.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riAccessType.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riAccessType.type')" for="ri-access-type-type">Type</label>
                        <input type="text" class="form-control" name="type" id="ri-access-type-type"
                            :class="{'valid': !$v.riAccessType.type.$invalid, 'invalid': $v.riAccessType.type.$invalid }" v-model="$v.riAccessType.type.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riAccessType.typeEn')" for="ri-access-type-typeEn">Type En</label>
                        <input type="text" class="form-control" name="typeEn" id="ri-access-type-typeEn"
                            :class="{'valid': !$v.riAccessType.typeEn.$invalid, 'invalid': $v.riAccessType.typeEn.$invalid }" v-model="$v.riAccessType.typeEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riAccessType.typeCyr')" for="ri-access-type-typeCyr">Type Cyr</label>
                        <input type="text" class="form-control" name="typeCyr" id="ri-access-type-typeCyr"
                            :class="{'valid': !$v.riAccessType.typeCyr.$invalid, 'invalid': $v.riAccessType.typeCyr.$invalid }" v-model="$v.riAccessType.typeCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riAccessType.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-access-type-update.component.ts">
</script>
