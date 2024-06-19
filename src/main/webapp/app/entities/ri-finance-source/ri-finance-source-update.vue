<template>
    <div class="row justify-content-center">
      <div class="col-8">
        <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
          <h2 id="riportalApp.riFinanceSource.home.createOrEditLabel" v-text="$t('riportalApp.riFinanceSource.home.createOrEditLabel')">Create or edit a Ri Finance Source</h2>
          <div>
            <div class="form-group" v-if="riFinanceSource.id">
              <label for="id" v-text="$t('global.field.id')">ID</label>
              <input type="text" class="form-control" id="id" name="id"
                     v-model="riFinanceSource.id" readonly />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.riFinanceSource.sourceSr')" for="ri-finance-source-source-sr">Source Sr</label>
              <input type="text" class="form-control" name="sourceSr" id="ri-finance-source-source-sr"
                     :class="{'valid': !$v.riFinanceSource.sourceSr.$invalid, 'invalid': $v.riFinanceSource.sourceSr.$invalid }"
                     v-model="$v.riFinanceSource.sourceSr.$model" />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.riFinanceSource.sourceSrc')" for="ri-finance-source-source-src">Source Src</label>
              <input type="text" class="form-control" name="sourceSrc" id="ri-finance-source-source-src"
                     :class="{'valid': !$v.riFinanceSource.sourceSrc.$invalid, 'invalid': $v.riFinanceSource.sourceSrc.$invalid }"
                     v-model="$v.riFinanceSource.sourceSrc.$model" />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('riportalApp.riFinanceSource.sourceEn')" for="ri-finance-source-source-en">Source En</label>
              <input type="text" class="form-control" name="sourceEn" id="ri-finance-source-source-en"
                     :class="{'valid': !$v.riFinanceSource.sourceEn.$invalid, 'invalid': $v.riFinanceSource.sourceEn.$invalid }"
                     v-model="$v.riFinanceSource.sourceEn.$model" />
            </div>
          </div>
          <div>
            <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
              <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
            </button>
            <button type="submit" id="save-entity" :disabled="$v.riFinanceSource.$invalid || isSaving" class="btn btn-primary">
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
            </button>
          </div>
        </form>
      </div>
    </div>
</template>
<script lang="ts" src="./ri-finance-source-update.component.ts">
</script>
