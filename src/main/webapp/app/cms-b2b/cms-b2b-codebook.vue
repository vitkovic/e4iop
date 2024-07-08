<template>
    <div>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <h2 class="mb-5" v-text="$t('riportalApp.cmsB2B.titleCoders')"></h2>

                <div class="row">
                    <div class="col-md-3">
                        <p v-text="$t('riportalApp.cmsB2B.coders.category')"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'AdvertisementCategory'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>                    
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <p v-text="$t('riportalApp.cmsB2B.coders.subcategory')"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'AdvertisementSubcategory'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>                    
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <p v-text="$t('riportalApp.cmsB2B.coders.subsubcategory')"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'AdvertisementSubsubcategory'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>                    
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <p v-text="$t('riportalApp.cmsB2B.coders.type')"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'AdvertisementType'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>                    
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <p v-text="$t('riportalApp.cmsB2B.coders.kind')"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'AdvertisementKind'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>                    
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>
    
    <script lang="ts" src="./cms-b2b-codebook.component.ts">
    </script>
    