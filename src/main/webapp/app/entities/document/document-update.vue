<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.document.home.createOrEditLabel" v-text="$t('riportalApp.document.home.createOrEditLabel')">Create or edit a Document</h2>
                <div>
                    <div class="form-group" v-if="document.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="document.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.document.filename')" for="document-filename">Filename</label>
                        <input type="text" class="form-control" name="filename" id="document-filename"
                            :class="{'valid': !$v.document.filename.$invalid, 'invalid': $v.document.filename.$invalid }" v-model="$v.document.filename.$model"  required/>
                        <div v-if="$v.document.filename.$anyDirty && $v.document.filename.$invalid">
                            <small class="form-text text-danger" v-if="!$v.document.filename.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.document.type')" for="document-type">Type</label>
                        <select class="form-control" id="document-type" name="type" v-model="document.type">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="document.type && documentTypeOption.id === document.type.id ? document.type : documentTypeOption" v-for="documentTypeOption in documentTypes" :key="documentTypeOption.id">{{documentTypeOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.document.kind')" for="document-kind">Kind</label>
                        <select class="form-control" id="document-kind" name="kind" v-model="document.kind">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="document.kind && documentKindOption.id === document.kind.id ? document.kind : documentKindOption" v-for="documentKindOption in documentKinds" :key="documentKindOption.id">{{documentKindOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.document.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./document-update.component.ts">
</script>
