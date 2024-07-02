<template>
    <div>
      <div class="row justify-content-center">
          <div class="col-md-8">
            <div v-if="success">
                <h1>
                    <span><font-awesome-icon icon="check" style="color: green;"/></span>
                    <span>{{ message }}</span>
                </h1>
            </div>
            <div v-else-if="error">
                <h1>
                    <span>{{ message }}</span>
                </h1>
            </div>
            <div v-else>
                <h1>
                    <span v-text="'Sistemska greška'"></span>
                </h1>
            </div>
            <br>
            <br>
            <div v-if="advertisementSupporter">
                <div class="d-flex">
                    <strong class="mr-2" v-text="'Oglas: '">Oglas</strong>
                    <router-link v-if="advertisementId" class="d-flex flex-wrap align-items-center" :to="{ name: 'AdvertisementView', params: { advertisementId: advertisementId } }"><span>{{ advertisementSupporter.advertisement.title }}</span></router-link>
                </div>
                <div class="d-flex">
                    <strong class="mr-2" v-text="'Oglašivač: '">Oglašivač</strong>
                    <router-link v-if="companyId" class="d-flex flex-wrap align-items-center" :to="{ name: 'CompanyView', params: { companyId: advertisementSupporter.advertisement.company.id } }"><span>{{ advertisementSupporter.advertisement.company.name }}</span></router-link>
                </div>
            </div>
        </div>
      </div>
    </div>
    </template>
    
    <script lang="ts" src="./advertisement-supporter-accept.component.ts">
    </script>
    