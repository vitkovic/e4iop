<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.researcher.home.createOrEditLabel" v-text="$t('riportalApp.researcher.home.createOrEditLabel')">Create or edit a Researcher</h2>
                <div>
                    <div class="form-group" v-if="researcher.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="researcher.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.researcher.firstName')" for="researcher-firstName">First Name</label>
                        <input type="text" class="form-control" name="firstName" id="researcher-firstName"
                            :class="{'valid': !$v.researcher.firstName.$invalid, 'invalid': $v.researcher.firstName.$invalid }" v-model="$v.researcher.firstName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.researcher.lastName')" for="researcher-lastName">Last Name</label>
                        <input type="text" class="form-control" name="lastName" id="researcher-lastName"
                            :class="{'valid': !$v.researcher.lastName.$invalid, 'invalid': $v.researcher.lastName.$invalid }" v-model="$v.researcher.lastName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.researcher.middleName')" for="researcher-middleName">Middle Name</label>
                        <input type="text" class="form-control" name="middleName" id="researcher-middleName"
                            :class="{'valid': !$v.researcher.middleName.$invalid, 'invalid': $v.researcher.middleName.$invalid }" v-model="$v.researcher.middleName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.researcher.birthDate')" for="researcher-birthDate">Birth Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="researcher-birthDate"
                                    v-model="$v.researcher.birthDate.$model"
                                    name="birthDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="researcher-birthDate" type="text" class="form-control" name="birthDate"  :class="{'valid': !$v.researcher.birthDate.$invalid, 'invalid': $v.researcher.birthDate.$invalid }"
                            v-model="$v.researcher.birthDate.$model"  />
                        </b-input-group>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.researcher.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./researcher-update.component.ts">
</script>
