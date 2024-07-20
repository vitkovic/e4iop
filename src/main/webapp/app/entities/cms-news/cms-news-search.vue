<template>
  <section class="bg-white">
    <div class="container">
      <div class="row mb-4">
        <div class="col-xs-12">
          <h1 v-text="$t('riportalApp.cmsNews.home.title')">Vesti</h1>
        </div>
      </div>

      <div v-show="cmsNews && cmsNews.length > 0">
        <div class="row justify-content-center">
          <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
        </div>
        <div class="row justify-content-center">
          <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @change="loadPage(page)"></b-pagination>
        </div>
      </div>

      <div class="row mb-4">
        <div class="col-12">
          <label for="searchInput" class="font-weight-bold" v-text="$t('riportalApp.cmsNews.search.title')">Pretražite pitanja</label>
          <div class="input-group">
            <input
              type="text"
              v-model="txtsearchNav"
              @keyup="autoQ()"
              @keyup.enter="searchQ()"
              class="form-control"
              :placeholder="$t('faq.searchPlaceholder')"
            />
            <div class="input-group-append">
              <button class="btn btn-search pt-0 pb-0" @click="searchQ()" v-text="$t('entity.action.searchBar')" type="button">
                Pretraži
              </button>
            </div>
          </div>
        </div>
      </div>

      <div v-if="cmsNews && cmsNews.length > 0" class="row">
        <div class="col-md-6 col-lg-4 mb-4" v-for="(news, index) in cmsNews" :key="index">
          <div class="card bg-white shadow">
            <img
              :src="retrieveImage(news.titleImage.filename)"
              alt="Placeholder picture"
              class="card-img-top img-fluid"
              style="height: 200px;"
            />
            <div class="card-body d-flex flex-column justify-content-between">
              <router-link :to="{ name: 'NewsView', params: { cmsNewsId: news.id } }" class="text-white" @click.prevent>
                <h3 class="card-title font-weight-bold">{{ news.title }}</h3>
              </router-link>
              <div class="d-flex justify-content-between">
                <router-link :to="{ name: 'NewsView', params: { cmsNewsId: news.id } }" class="btn" @click.prevent
                  >{{ $t('riportalApp.cmsNews.search.buttonContent') }}</router-link
                >
                <div class="d-flex align-items-center" style="height: 100%;">
                  <p class="text-sm text-white mb-0" style="text-align: center;">
                    <font-awesome-icon icon="calendar-alt" />
                    <span>
                      {{ news.date ? $d(Date.parse(news.date.toString()), { dateStyle: 'short' }) : '' }}
                    </span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script lang="ts" src="./cms-news-search.component.ts"></script>

<style scoped>
.card {
  height: 420px; /* Set fixed height for the card */
  position: relative;
  border: 0;
  transition: transform 0.4s ease;
}

.card:hover {
  transform: scale(1.02);
}

.card-body {
  background-color: #1f3448;
  border-bottom-left-radius: calc(0.25rem - 1px);
  border-bottom-right-radius: calc(0.25rem - 1px);
}

.card img {
  object-fit: cover; /* Ensure images fill the entire card */
}

h1 {
  color: #004b90;
}

h3 {
  font-size: 1.25rem;
}

.btn {
  /* position: absolute; */
  /* left: 20px; */
  /* bottom: 20px; */
  background-color: #e74c3c;
  color: #fff;
  transition: all 0.3s;
}

.btn:hover {
  background-color: #fff;
  color: #e74c3c;
  font-weight: normal;
}

.page-link {
  color: #004b90;
  background-color: #fff;
  border: 0.1px solid;
  margin-right: 2px;
}

.page-link:hover {
  color: #fff;
  background-color: #004b90;
}
</style>
