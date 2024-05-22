<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.portalUserRole.home.createOrEditLabel" v-text="$t('riportalApp.portalUserRole.home.createOrEditLabel')">Create or edit a PortalUserRole</h2>
                <div>
                    <div class="form-group" v-if="portalUserRole.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="portalUserRole.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserRole.role')" for="portal-user-role-role">Role</label>
                        <input type="text" class="form-control" name="role" id="portal-user-role-role"
                            :class="{'valid': !$v.portalUserRole.role.$invalid, 'invalid': $v.portalUserRole.role.$invalid }" v-model="$v.portalUserRole.role.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserRole.abbreviation')" for="portal-user-role-abbreviation">Abbreviation</label>
                        <input type="text" class="form-control" name="abbreviation" id="portal-user-role-abbreviation"
                            :class="{'valid': !$v.portalUserRole.abbreviation.$invalid, 'invalid': $v.portalUserRole.abbreviation.$invalid }" v-model="$v.portalUserRole.abbreviation.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserRole.fullName')" for="portal-user-role-fullName">Full Name</label>
                        <input type="text" class="form-control" name="fullName" id="portal-user-role-fullName"
                            :class="{'valid': !$v.portalUserRole.fullName.$invalid, 'invalid': $v.portalUserRole.fullName.$invalid }" v-model="$v.portalUserRole.fullName.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.portalUserRole.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./portal-user-role-update.component.ts">
</script>
