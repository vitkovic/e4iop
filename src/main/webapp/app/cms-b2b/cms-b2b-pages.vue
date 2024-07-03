<template>
    <div>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <h2 class="mb-5" v-text="'Upravljanje stranicama'"></h2>

                <div class="row">
                    <div class="col-md-3">
                        <p v-text="'O nama'"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'About'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>
                        <router-link :to="{name: 'CMSPageEditSpecific', params: {pageType: cmsPageTypeOptions.ABOUT}}" tag="button" class="btn btn-secondary btn-sm details">
                            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">View</span>
                        </router-link>               
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <p v-text="'Politika privatnosti'"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'PrivacyPolicy'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>                    
                        <router-link :to="{name: 'CMSPageEditSpecific', params: {pageType: cmsPageTypeOptions.PRIVACY_POLICY}}" tag="button" class="btn btn-secondary btn-sm details">
                            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">View</span>
                        </router-link>                    
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <p v-text="'Uslovi koriščenja'"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'TermsOfUse'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link> 
                        <router-link :to="{name: 'CMSPageEditSpecific', params: {pageType: cmsPageTypeOptions.TERMS_OF_USE}}" tag="button" class="btn btn-secondary btn-sm details">
                            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">View</span>
                        </router-link>           
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <p v-text="'Politika kolačića'"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'CookiePolicy'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>        
                        <router-link :to="{name: 'CMSPageEditSpecific', params: {pageType: cmsPageTypeOptions.COOKIES}}" tag="button" class="btn btn-secondary btn-sm details">
                            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">View</span>
                        </router-link>              
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <p v-text="'Najčešća pitanja'"></p>
                    </div>
                    <div class="col-md-3">
                        <router-link :to="{name: 'Questions'}" tag="button" class="btn btn-info btn-sm details">
                            <font-awesome-icon icon="eye"></font-awesome-icon>
                            <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                        </router-link>        
                        <router-link :to="{name: 'CmsQuestion'}" tag="button" class="btn btn-primary btn-sm details">
                            <!-- <font-awesome-icon icon="plus"></font-awesome-icon> -->
                            <span class="d-none d-md-inline" v-text="'Upravljanje pitanjima'">View</span>
                        </router-link>              
                    </div>
                </div>


            </div>
        </div>
    </div>
</template>
    
    <script lang="ts" src="./cms-b2b-pages.component.ts">
    </script>
    