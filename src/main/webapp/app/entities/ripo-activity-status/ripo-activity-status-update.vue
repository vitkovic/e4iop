<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.ripoActivityStatus.home.createOrEditLabel" v-text="$t('riportalApp.ripoActivityStatus.home.createOrEditLabel')">Create or edit a RipoActivityStatus</h2>
                <div>
                    <div class="form-group" v-if="ripoActivityStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="ripoActivityStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.ripoActivityStatus.status')" for="ripo-activity-status-status">Status</label>
                        <input type="text" class="form-control" name="status" id="ripo-activity-status-status"
                            :class="{'valid': !$v.ripoActivityStatus.status.$invalid, 'invalid': $v.ripoActivityStatus.status.$invalid }" v-model="$v.ripoActivityStatus.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.ripoActivityStatus.statusEn')" for="ripo-activity-status-statusEn">Status</label>
                        <input type="text" class="form-control" name="statusEn" id="ripo-activity-status-statusEn"
                            :class="{'valid': !$v.ripoActivityStatus.statusEn.$invalid, 'invalid': $v.ripoActivityStatus.statusEn.$invalid }" v-model="$v.ripoActivityStatus.statusEn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.ripoActivityStatus.statusCyr')" for="ripo-activity-status-statusCyr">Status</label>
                        <input type="text" class="form-control" name="status" id="ripo-activity-status-statusCyr"
                            :class="{'valid': !$v.ripoActivityStatus.statusCyr.$invalid, 'invalid': $v.ripoActivityStatus.statusCyr.$invalid }" v-model="$v.ripoActivityStatus.statusCyr.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.ripoActivityStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ripo-activity-status-update.component.ts">
</script>
