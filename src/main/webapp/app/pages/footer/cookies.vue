<template>
  <section class="terms-of-use bg-white">
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <h1 v-text="$t('footer.cookie')">Cookie policy</h1>
        </div>
        <div class="col-md-12">
          <p class="lh-1">
            Za vreme korišćenja bilo koje stranice na sajtu <a class="privacy-link" href="#" target="_blank"> www.b2bportal.rs</a>, B2B može
            da sačuva određene informacije na korisnikov uređaj, putem "kolačića" (eng. "cookies").
          </p>
          <p class="lh-1">
            Kolačići omogućavaju da se sadržaj stranica prilagodi potrebama korisnika, pružajući na taj način unapređenu funkcionalnost i
            poboljšano korisničko iskustvo. Kolačići se mogu koristiti i za analizu korišćenja internet stranica. Klikom na "Slažem se"
            dajete saglasnost za korišćenje kolačića u analitičke, funkcionalne i reklamne svrhe.
          </p>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
h1,
h2 {
  color: #004b90;
}

.privacy-link,
.privacy-link:link,
.privacy-link:visited,
.privacy-link:hover,
.privacy-link:active {
  color: blue;
  text-decoration: none;
}
</style>
