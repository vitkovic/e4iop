<template>
  <section class="bg-white">
    <div class="container">
      <div class="row mb-2">
        <div class="col-xs-12">
          <h1>VESTI</h1>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6 col-lg-4 mb-4" v-for="(newsItem, index) in news" :key="index">
          <div class="card bg-white shadow">
            <img :src="newsItem.img" alt="Placeholder picture" class="card-img-top img-fluid" style="height: 200px;" />
            <div class="card-body">
              <h3 class="card-title font-weight-bold text-white">{{ newsItem.title }}</h3>
              <p class="card-subtitle text-sm text-white">Datum: {{ newsItem.date }}</p>
              <a href="#" class="btn" @click.prevent>Pročitaj vest</a>
            </div>
          </div>
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="col-xs-12">
          <nav aria-label="Demo nav">
            <ul class="pagination">
              <li class="page-item">
                <a href="#" class="page-link" @click.prevent>Prethodna</a>
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
                <a href="" class="page-link" @click.prevent>Sledeća</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </section>
</template>

<script src="./news.ts"></script>

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
  background-color: #004b90;
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
  position: absolute;
  left: 20px;
  bottom: 20px;
  background-color: #E74C3C;
  color: #fff;
  transition: all 0.3s;
}

.btn:hover {
  background-color: #fff;
  color: #E74C3C;
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
