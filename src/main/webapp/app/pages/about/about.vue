<template>
  <section class="bg-white">
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <h1>O NAMA</h1>
        </div>
        <div class="col-md-12">
          <p class="lh-1">
            Portal B2B (<em>Business to Business</em>) predstavlja virtuelnu platformu za povezivanje velike i male privrede. Reč je o
            digitalnom rešenju koje omogućava preduzećima da međusobno oglašavaju potrebe, proizvode ili usluge po principu ponude i
            tražnje.
          </p>
          <p>
            Portal je kreiran sa ciljem da podstakne saradnju između velike i male privrede uključivanjem male privrede u lance vrednosti
            velikih privrednih subjekata, te rešavanje problema/izazova velikih firmi od strane male privrede i plasiranje proizvoda male
            privrede putem portala.
          </p>
          <p>
            Portal pomaže velikim, srednjim i malim privrednim subjektima, ali i startapima, preduzetnicima, frilenserima i neformalnim
            timovima (npr. studentima) da sa jedne strane oglase ponudu svojih usluga, ekspertize, opreme, potreba i sl. (oglašivači), a sa
            druge strane da pronađu ono što im nedostaje za dalji razvoj svog poslovanja (tražioci).
          </p>
          <p>
            Portal obezbeđuje registraciju korisnika (oglašivača i tražilaca), uz verodostojnu proveru identiteta, u svrhe postavljanja
            oglasa, pretrage oglasa po raznim kriterijumima, uspostavljanje komunikacije između privrednih subjekata i ocenjivanje
            zadovoljstva ostvarenom saradnjom.
          </p>
          <p>
            Portal B2B je izradio Mašinski fakultet Univerziteta u Nišu na inicijativu Ministarstva nauke, tehnološkog razvoja i inovacija,
            dok nadzor and realizacijom i implementacijom projekta sprovodi NALED u okviru StarTech projekta za promociju inovacija i
            tehnološkog razvoja u Srbiji, a uz finansijsku podršku kompanije Philip Morris.
          </p>
          <p>
            StarTech predstavlja trogodišnji projekat za promociju inovacija i tehnološkog razvoja u Srbiji koji sprovodi NALED u saradnji
            sa Vladom Republike Srbije.
          </p>
          <p>
            StarTech je prvi program podrške inovativnim privrednicima koji u potpunosti finansira jedna privatna, društveno-odgovorna
            kompanija – Philip Morris u Srbiji, sredstvima ukupne vrednosti od pet miliona dolara.
          </p>
          <p>
            Cilj projekta je ubrzanje rasta srpskih kompanija i transformacija tradicionalne ekonomije u digitalnu, uz pružanje podrške u
            vidu sredstava i ekspertize, rada na unapređenju uslova za podsticanje inovacija i promocije domaćih inovativnih biznisa.
          </p>
          <p>
            Više informacija o StarTech projektu možete pronaći na sajtu
            <a class="startech-link" href="https://www.startech.org.rs/" target="_blank">www.startech.org.rs</a>.
          </p>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <h2>GALERIJA</h2>
        </div>
      </div>
      <div class="row images-container mt-2" v-viewer="viewerOptions">
        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 mb-4" v-for="(src, index) in images" :key="index">
          <img :src="src" class="img-thumbnail img-fluid" alt="Placeholder picture" @click="openViewer(src)" />
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <h2>KORISNI DOKUMENTI</h2>
        </div>
      </div>
      <div class="col-md-12">
        <ol>
          <li v-for="(doc, index) in documents" :key="index">
            <a class="text-info" :href="doc.src" target="_blank" title="Preuzmite dokument"
              >{{ doc.title }}
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512" style="width: 16px; height: 16px; fill: #3498db;">
                <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                <path
                  d="M224 136V0H24C10.7 0 0 10.7 0 24v464c0 13.3 10.7 24 24 24h336c13.3 0 24-10.7 24-24V160H248c-13.2 0-24-10.8-24-24zm76.5 211.4l-96.4 95.7c-6.7 6.6-17.4 6.6-24 0l-96.4-95.7C73.4 337.3 80.5 320 94.8 320H160v-80c0-8.8 7.2-16 16-16h32c8.8 0 16 7.2 16 16v80h65.2c14.3 0 21.4 17.3 11.3 27.4zM377 105L279.1 7c-4.5-4.5-10.6-7-17-7H256v128h128v-6.1c0-6.3-2.5-12.4-7-16.9z"
                /></svg
            ></a>
          </li>
        </ol>
      </div>
    </div>
  </section>
</template>

<script lang="ts" src="./about.ts"></script>
<style scoped>
.startech-link,
.startech-link:link,
.startech-link:visited,
.startech-link:hover,
.startech-link:active {
  color: blue;
  text-decoration: none;
}

.img-thumbnail {
  width: 100%; /* Ensure images fill their container */
  height: 200px; /* Maintain aspect ratio */
  cursor: pointer; /* Change cursor to pointer on hover */
  padding: 0;
  transition: all 0.4s;
  box-shadow: 0 0.6rem 1.2rem rgba(0, 0, 0, 0.075);
  object-fit: cover; /* Ensure images fill the entire card */
}

.img-thumbnail:hover {
  transform: translateY(-0.3rem);
  box-shadow: 0 0.8rem 1.6rem rgba(0, 0, 0, 0.06);
}

h1,
h2 {
  color: #004b90;
}
</style>
