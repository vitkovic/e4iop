<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riAccessMode.home.createOrEditLabel" v-text="$t('riportalApp.riAccessMode.home.createOrEditLabel')">Create or edit a RiAccessMode</h2>
                <div>
                    <div class="form-group" v-if="riAccessMode.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riAccessMode.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riAccessMode.mode')" for="ri-access-mode-mode">Mode</label>
                        <input type="text" class="form-control" name="mode" id="ri-access-mode-mode"
                            :class="{'valid': !$v.riAccessMode.mode.$invalid, 'invalid': $v.riAccessMode.mode.$invalid }" v-model="$v.riAccessMode.mode.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riAccessMode.modeEn')" for="ri-access-mode-modeEn">Mode En</label>
                        <input type="text" class="form-control" name="modeEn" id="ri-access-mode-modeEn"
                            :class="{'valid': !$v.riAccessMode.modeEn.$invalid, 'invalid': $v.riAccessMode.modeEn.$invalid }" v-model="$v.riAccessMode.modeEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riAccessMode.modeCyr')" for="ri-access-mode-modeCyr">Mode Cyr</label>
                        <input type="text" class="form-control" name="modeCyr" id="ri-access-mode-modeCyr"
                            :class="{'valid': !$v.riAccessMode.modeCyr.$invalid, 'invalid': $v.riAccessMode.modeCyr.$invalid }" v-model="$v.riAccessMode.modeCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riAccessMode.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-access-mode-update.component.ts">
</script>
