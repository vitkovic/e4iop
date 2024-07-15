<template>
  <section class="bg-white">
    <div class="container">
      <div class="row mb-4">
        <div class="col-xs-12">
          <h1 v-text="$t('faq.title')">NAJČEŠĆA PITANJA</h1>
        </div>
      </div>

      <div v-show="cmsQuestions && cmsQuestions.length > 0">
        <div class="row justify-content-center">
            <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
        </div>
        <div class="row justify-content-center">
            <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
        </div>
      </div>

      <div class="row mb-4 ml-1">
        <div class="col-xs-12 w-100">
          <label for="searchInput" class="font-weight-bold" v-text="$t('faq.searchQuestions')" >Pretražite pitanja</label>
          <div class="input-group">
            <input type="text"  v-model="txtsearchNav" class="form-control" :placeholder="$t('faq.searchPlaceholder')" />
            <div class="input-group-append">
              <button class="btn btn-search pt-0 pb-0" @click="searchQ()" v-text="$t('entity.action.search')" type="button">Pretraži</button>
            </div>
          </div>
        </div>
      </div>
      <div v-if="cmsQuestions.length > 0" class="row ml-1">
        <div class="card col-12 mb-2" v-for="(question, index) in cmsQuestions" :key="index">
          <div class="card-header p-0 border-0">
            <button class="btn text-left w-100 font-weight-bold text-white rounded" @click="toggleAnswer(index)">
              {{ question.question }}
            </button>
          </div>
          <div class="card-body" v-show="isAnswerShown(index)">
            <p v-text="question.answer" style="white-space: preserve;"></p>
          </div>
        </div>
      </div>

      <!-- <div class="row justify-content-center">
        <div class="col-xs-12">
          <nav aria-label="Demo nav">
            <ul class="pagination">
              <li class="page-item">
                <a href="#" class="page-link" v-text="$t('faq.pagginationPrev')" @click.prevent>Prethodna</a>
              </li>
              <li class="page-item">
                <a href="" class="page-link" @click.prevent>1</a>
              </li>
              <li class="page-item">
                <a href="" class="page-link" @click.prevent>2</a>
              </li>
              <li class="page-item">
                <a href="" class="page-link" @click.prevent>3</a>
              </li>
              <li class="page-item">
                <a href="" class="page-link" v-text="$t('faq.pagginationNext')" @click.prevent>Sledeća</a>
              </li>
            </ul>
          </nav>
        </div>
      </div> -->
    </div>
  </section>
</template>

<script lang="ts" src="./questions.ts"></script>

<style scoped>
.card {
  padding: 0;
}

.card-body {
  box-shadow: 0 1.2rem 2.4rem rgba(0, 0, 0, 0.09);
}

.btn {
  background-color: #004b90;
}

.btn:focus {
  box-shadow: none;
}

a {
  color: #004b90;
  background-color: #fff;
  border: 0.1px solid;
  margin-right: 2px;
}

a:hover {
  color: #fff;
  background-color: #004b90;
}

.form-control:focus {
 box-shadow: none;
}

label {
  color: #004b90;
}

.btn-search {
  background-color: #E74C3C;
  color: #fff;
}
</style>
