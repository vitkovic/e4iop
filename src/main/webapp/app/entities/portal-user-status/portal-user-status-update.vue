<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.portalUserStatus.home.createOrEditLabel" v-text="$t('riportalApp.portalUserStatus.home.createOrEditLabel')">Create or edit a portalUserStatus</h2>
                <div>
                    <div class="form-group" v-if="portalUserStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="portalUserStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserStatus.valueSr')" for="portal-user-status-valueSr"></label>
                        <input type="text" class="form-control" name="role" id="portal-user-status-valueSr"
                            :class="{'valid': !$v.portalUserStatus.valueSr.$invalid, 'invalid': $v.portalUserStatus.valueSr.$invalid }" v-model="$v.portalUserStatus.valueSr.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserStatus.valueEn')" for="portal-user-status-valueEn">Abbreviation</label>
                        <input type="text" class="form-control" name="valueEn" id="portal-user-status-valueSr"
                            :class="{'valid': !$v.portalUserStatus.valueEn.$invalid, 'invalid': $v.portalUserStatus.valueEn.$invalid }" v-model="$v.portalUserStatus.valueEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserStatus.valueSrCyr')" for="portal-user-status-valueSrCyr"></label>
                        <input type="text" class="form-control" name="valueSrCyr" id="portal-user-status-valueSrCyr"
                            :class="{'valid': !$v.portalUserStatus.valueSrCyr.$invalid, 'invalid': $v.portalUserStatus.valueSrCyr.$invalid }" v-model="$v.portalUserStatus.valueSrCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.portalUserStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./portal-user-status-update.component.ts">
</script>
