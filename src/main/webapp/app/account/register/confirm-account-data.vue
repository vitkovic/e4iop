<template>
    <div class="row justify-content-center">
        <div class="col-11 col-md-10">

            <div class="mt-5 mb-5">
                <h2 class="jh-entity-heading mb-3">
                    <span v-text="$t('register.confirm.title')">Potvrda podataka</span>
                </h2>
                <div style="width: 50%;">
                    <p v-html="$t('register.confirm.infoText')"></p>
                </div>
            </div>

            <div v-if="portalUser" class="mt-4">
                <h3 class="jh-entity-heading mb-3">
                    <span v-text="$t('register.confirm.userInfo')">Podaci o korisniku</span>
                </h3>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                        <span class="span-display" v-text="$t('riportalApp.portalUser.firstName')">First Name</span>
                    </dt>
                    <dd class="col-sm-7">
                        <span>{{portalUser.user.firstName}}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                        <span class="span-display"  v-text="$t('riportalApp.portalUser.familyName')">Family Name</span>
                    </dt>
                    <dd class="col-sm-7">
                        <span>{{portalUser.user.lastName}}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                        <span class="span-display"  v-text="$t('global.form[\'email.label\']')">Family Name</span>
                    </dt>
                    <dd class="col-sm-7">
                        <span>{{portalUser.user.email}}</span>
                    </dd>
                </dl>
            </div>

            <div v-if="company" class="mt-4">
                <h3 class="jh-entity-heading mb-3">
                    <span v-text="$t('register.confirm.companyInfo')">Podaci o kompaniji</span>
                </h3>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.name')">Vrsta:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.name }}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.businessName')">Vrsta:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.businessName }}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.foundationDate')">Podaci:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.foundationDate ? $d(Date.parse(company.foundationDate.toString()), { dateStyle: 'short' }) : '' }}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.address')">Vrsta:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.address }}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.primaryActivity')">Spisak delatnosti:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.primaryActivity }}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.pib')">Vrsta:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.pib }}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.registrationNumber')">Vrsta:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.registrationNumber }}</span>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.website')">Vrsta:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <b-link v-if="company.website" :to="`//${company.website}`" target="_blank" class="text-body">{{
                        company.website.replace(/^https?:\/\//, '')
                    }}</b-link>
                    </dd>
                </dl>
                <dl class="row d-flex flex-wrap mb-1">
                    <dt class="col-sm-4 col-lg-3 mr-4">
                    <span v-text="$t('riportalApp.company.email')">Vrsta:</span>
                    </dt>
                    <dd class="col-sm-7">
                    <span>{{ company.email }}</span>
                    </dd>
                </dl>
            </div>

            <router-link
                :to="{ name: 'B2BHome' }"
                tag="button"
                class="btn btn-info mt-4"
              >
                <span v-text="$t('entity.action.confirm')"></span>
            </router-link>

        </div>
    </div>
</template>

<script lang="ts" src="./confirm-account-data.component.ts">
</script>
