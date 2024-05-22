<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.puoLegalStatus.home.createOrEditLabel" v-text="$t('riportalApp.puoLegalStatus.home.createOrEditLabel')">Create or edit a PuoLegalStatus</h2>
                <div>
                    <div class="form-group" v-if="puoLegalStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="puoLegalStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.puoLegalStatus.value')" for="puo-legal-status-value">Value</label>
                        <input type="text" class="form-control" name="value" id="puo-legal-status-value"
                            :class="{'valid': !$v.puoLegalStatus.value.$invalid, 'invalid': $v.puoLegalStatus.value.$invalid }" v-model="$v.puoLegalStatus.value.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.puoLegalStatus.description')" for="puo-legal-status-description">Description</label>
                        <input type="text" class="form-control" name="description" id="puo-legal-status-description"
                            :class="{'valid': !$v.puoLegalStatus.description.$invalid, 'invalid': $v.puoLegalStatus.description.$invalid }" v-model="$v.puoLegalStatus.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.puoLegalStatus.descriptionEn')" for="puo-legal-status-descriptionEn">Description En</label>
                        <input type="text" class="form-control" name="descriptionEn" id="puo-legal-status-descriptionEn"
                            :class="{'valid': !$v.puoLegalStatus.descriptionEn.$invalid, 'invalid': $v.puoLegalStatus.descriptionEn.$invalid }" v-model="$v.puoLegalStatus.descriptionEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.puoLegalStatus.descriptionCyr')" for="puo-legal-status-descriptionCyr">Description Cyr</label>
                        <input type="text" class="form-control" name="descriptionCyr" id="puo-legal-status-descriptionCyr"
                            :class="{'valid': !$v.puoLegalStatus.descriptionCyr.$invalid, 'invalid': $v.puoLegalStatus.descriptionCyr.$invalid }" v-model="$v.puoLegalStatus.descriptionCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.puoLegalStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./puo-legal-status-update.component.ts">
</script>
