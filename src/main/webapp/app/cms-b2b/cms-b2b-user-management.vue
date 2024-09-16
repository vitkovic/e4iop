<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <h2 class="mb-5 cms-title" v-text="$t('riportalApp.cmsB2B.titleUserManagement')"></h2>

        <div class="row">
          <div class="col-5 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.userManagement.users')"></p>
          </div>
          <div class="col-4 col-md-3">
            <router-link :to="{ name: 'CMSUsers' }" tag="button" class="btn btn-info btn-sm details">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
          </div>
        </div>

        <div class="row">
          <div class="col-5 col-md-3">
            <p v-text="$t('riportalApp.cmsB2B.userManagement.companies')"></p>
          </div>
          <div class="col-4 col-md-3">
            <router-link :to="{ name: 'CMSCompanies' }" tag="button" class="btn btn-info btn-sm details">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span v-text="$t('entity.action.view')">View</span>
            </router-link>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cms-b2b-user-management.component.ts"></script>

<style scoped>
.cms-title{
  color: #004b90;
}
</style>
