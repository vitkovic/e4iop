<template>
    <div class="row justify-content-center">
      <div class="col-8">
        <div v-if="riFinanceSource">
          <h2 class="jh-entity-heading">
            <span v-text="$t('riportalApp.riFinanceSource.detail.title')">RiFinanceSource</span> {{ riFinanceSource.id }}
          </h2>
          <dl class="row jh-entity-details">
            <dt>
              <span v-text="$t('riportalApp.riFinanceSource.sourceSr')">Source SR</span>
            </dt>
            <dd>
              <span>{{ riFinanceSource.sourceSr }}</span>
            </dd>
            <dt>
              <span v-text="$t('riportalApp.riFinanceSource.sourceSrc')">Source SRC</span>
            </dt>
            <dd>
              <span>{{ riFinanceSource.sourceSrc }}</span>
            </dd>
            <dt>
              <span v-text="$t('riportalApp.riFinanceSource.sourceEn')">Source EN</span>
            </dt>
            <dd>
              <span>{{ riFinanceSource.sourceEn }}</span>
            </dd>
          </dl>
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>
          <router-link v-if="riFinanceSource.id" :to="{ name: 'RiFinanceSourceEdit', params: { riFinanceSourceId: riFinanceSource.id } }" tag="button" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </router-link>
        </div>
      </div>
    </div>
  </template>

<script lang="ts" src="./ri-finance-source-details.component.ts">
</script>
  