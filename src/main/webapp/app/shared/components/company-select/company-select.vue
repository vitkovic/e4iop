<template>
  <div>
    <label for="" v-text="labelText"></label>
    <input
      type="text"
      ref="company-name"
      class="form-control mb-3"
      name="company-name"
      id="company-name"
      :placeholder="$t('component.companySelect.searchCompany')"
      @keyup="getCompaniesBySearchText()"
      @focusout="toggleSearchList($event, 'showCompaniesSearch')"
      @focusin="toggleSearchList($event, 'showCompaniesSearch')"
      v-model="companySearchText"
      autocomplete="off"
    />
    <div
      class="form-control"
      style="text-align: left; height: 150px; overflow-y: scroll; position: absolute; z-index: 9999;"
      v-if="companiesSearch.length && showCompaniesSearch"
    >
      <ul class="list-group" @click.stop>
        <div v-for="company in companiesSearch" :key="company.id">
          <a class="list-group-item" @mousedown="addCompany(company)">
            <span>{{ company.name }}</span>
          </a>
        </div>
      </ul>
    </div>

    <div
      v-for="company in selectedCompanies"
      :key="company.id"
      class="d-flex align-items-center justify-content-between mb-3"
    >
      <div class="d-flex align-items-center">
        <div v-if="company.logo" class="company-logo-container position-relative">
          <img
            :src="retriveCompanyLogo(company)"
            alt="company logo"
            style="width: 100%; max-height: 100%;"
            class="company-logo"
          />
        </div>
        <div v-else class="placeholder-logo">{{ getCompanyInitials(company) }}</div>
        <span>{{ company.name }}</span>
      </div>
      <b-button @click="removeCompany(company)" variant="primary" class="close" style="color:grey">x</b-button>
    </div>
  </div>
</template>

<script lang="ts" src="./company-select.component.ts"></script>

<style scoped>
.company-logo-container {
  margin-right: 1rem;
  width: 40px;
  height: 40px;
  margin-right: 1rem;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #ccc;
  align-content: center;
}

.company-logo {
  border-radius: 50%;
}

.placeholder-logo {
  width: 40px;
  height: 40px;
  margin-right: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ccc;
  border-radius: 50%;
  color: black;
  text-align: center;
}
</style>
