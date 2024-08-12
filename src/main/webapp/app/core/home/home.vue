<template>
  <div style="margin-top: -10px; padding: 0px;">
    <div id="searchbox">
      <b-input-group class="mb-2" style="padding: 50px; padding-top: 30px;">
        <b-input-group-prepend is-text>
          <b-icon icon="search" scale="1.3" @click="searchRiService()"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          type="text"
          :placeholder="$t('global.menu.pregled.search')"
          v-model="search.searchTerms"
          @keydown.enter.native="searchRiService()"
        ></b-form-input>
      </b-input-group>
    </div>

    <div style="margin: 0px; margin-top: -30px; padding-top: 15px; background-color: #abbbc960;">
      <b-jumbotron class="text-left text-sm-center">
        <template #header
          ><span v-text="$t('home.title1')" class="headerTitle">Portal otvorene istraživačke infrastrukture Srbije</span></template
        >
        <template #lead>
          <strong><span v-text="$t('home.pregledajte')">Pregledajte i pretražite istraživačku infrastrukturu Srbije!</span></strong>
        </template>
        <b-button variant="primary" v-b-modal.seeMore><span v-text="$t('home.saznajVise')">Saznaj više...</span></b-button>
      </b-jumbotron>
    </div>
    <div class="middlePart">
      <div class="cards-box pl-4 pr-4">
        <b-row class="d-flex align-items-center justify-content-around">
          <div class="first col-10 col-sm-4 col-md-3 mb-4 mb-sm-0">
            <img src="/content/images/01_sm_sm.jpg" alt="Infractures card background image" class="img-box" />
            <div class="content">
              <router-link :to="{ name: 'ResearchInfrastructurePregled' }" class="text-body">
                <h5 v-text="$t('home.infrastructure')">INFRASTRUKTURA</h5>
              </router-link>
              <div class="d-flex align-items-center justify-content-center">
                <span class="badge badge-pill badge-warning badge-cards">{{ researchInfrastructureCount }}</span>
              </div>
            </div>
          </div>
          <div class="second col-10 col-sm-4 col-md-3 mb-4 mb-sm-0">
            <img src="/content/images/02_n_sm_sm.jpg" alt="Services card background image" class="img-box" />
            <div class="content">
              <router-link :to="{ name: 'RiServicePregled' }" class="text-body">
                <h5 v-text="$t('home.services')">USLUGE</h5>
              </router-link>
              <div class="d-flex align-items-center justify-content-center">
                <span class="badge badge-pill badge-warning badge-cards">{{ riServicesCount }}</span>
              </div>
            </div>
          </div>
          <div class="third col-10 col-sm-4 col-md-3">
            <img src="/content/images/03_sm_sm.jpg" alt="Organizations card background image" class="img-box" />
            <div class="content">
              <router-link :to="{ name: 'PortalUserOrganizationPregled' }" class="text-body">
                <h5 v-text="$t('home.organizations')">ORGANIZACIJE</h5>
              </router-link>
              <div class="d-flex align-items-center justify-content-center">
                <span class="badge badge-pill badge-warning badge-cards">{{ portalOrganizationsCount }}</span>
              </div>
            </div>
          </div>
        </b-row>
      </div>
      <!-- <b-container fluid="sm">
        <b-row>
          <b-col sm="4" class="d-flex align-items-center justify-content-center">
            <div
              class="d-flex align-items-center justify-content-center"
              style="
                position: relative;
                background-image: url('/content/images/01_sm_sm.jpg');
                background-repeat: no-repeat;
                background-size: contain;
                width: 300px;
                height: 188px;
              "
            >
              &nbsp;

              <b-container fluid="sm" style="margin-top: 0px;">
                <b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <router-link :to="{ name: 'ResearchInfrastructurePregled' }">
                      <h5 v-text="$t('home.infrastructure')">INFRASTRUKTURA</h5>
                    </router-link>
                  </b-col>
                </b-row>
                <b-row style="height: 20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
                <b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <h4>
                      <span class="badge badge-pill badge-warning">{{ researchInfrastructureCount }}</span>
                    </h4>
                  </b-col>
                </b-row>
              </b-container>
            </div>
          </b-col>
          <b-col sm="4" class="d-flex align-items-center justify-content-center">
            <div
              class="d-flex align-items-center justify-content-center"
              style="
                position: relative;
                background-image: url('/content/images/02_n_sm_sm.jpg');
                background-repeat: no-repeat;
                background-size: contain;
                width: 300px;
                height: 188px;
              "
            >
              &nbsp;

              <b-container fluid="sm" style="margin-top: 0px;">
                <b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <router-link :to="{ name: 'RiServicePregled' }">
                      <h5 v-text="$t('home.services')">USLUGE</h5>
                    </router-link>
                  </b-col>
                </b-row>
                <b-row style="height: 20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
                <b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <h4>
                      <span class="badge badge-pill badge-warning">{{ riServicesCount }}</span>
                    </h4>
                  </b-col>
                </b-row>
              </b-container>
            </div>
          </b-col>
          <b-col sm="4" class="d-flex align-items-center justify-content-center">
            <div
              class="d-flex align-items-center justify-content-center"
              style="
                position: relative;
                background-image: url('/content/images/03_sm_sm.jpg');
                background-repeat: no-repeat;
                background-size: contain;
                width: 300px;
                height: 188px;
              "
            >
              &nbsp;
              <b-container fluid="sm" style="margin-top: 0px;">
                <b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <router-link :to="{ name: 'PortalUserOrganizationPregled' }">
                      <h5 v-text="$t('home.organizations')">ORGANIZACIJE</h5>
                    </router-link>
                  </b-col>
                </b-row>
                <b-row style="height: 20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
                <b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <h4>
                      <span class="badge badge-pill badge-warning">{{ portalOrganizationsCount }}</span>
                    </h4>
                  </b-col>
                </b-row>
              </b-container>
            </div>
          </b-col>
        </b-row>
        <b-row style="height: 50px;">
          <b-col sm="12"> </b-col>
        </b-row>
      </b-container> -->
    </div>

    <!-- Rezultat pretrage, ako postoji -->
    <div class="table-responsive mt-4" v-if="services.length > 0">
      <h3><span v-text="$t('riportalApp.riService.searchResults')" style="font-weight: bold;"></span></h3>
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th class="col-sm-2"><span v-text="$t('riportalApp.riService.serviceName')"></span></th>
            <th class="col-sm-6"><span v-text="$t('riportalApp.riService.serviceDescription')"></span></th>
            <th class="col-sm-2"><span v-text="$t('riportalApp.researchInfrastructure.home.title')"></span></th>
            <th class="col-sm-2"><span v-text="$t('register.organizacija')"></span></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="service in services" :key="service.serviceId">
            <td>
              <router-link
                class="link-style"
                :to="{
                  name: 'RiServicePregledDetails',
                  params: { riServiceId: service.serviceId, pathInformation: { search: search, path: 'Home' } },
                }"
              >
                <span v-if="currentLanguage === 'sr'">{{ service.serviceNameSr }}</span>
                <span v-if="currentLanguage === 'en'">{{ service.serviceNameEn }}</span>
                <span v-if="currentLanguage === 'src'">{{ service.serviceNameSrCyr }}</span>
              </router-link>
            </td>
            <td>
              <span v-if="currentLanguage === 'sr'">{{ service.serviceDescriptionSr }}</span>
              <span v-if="currentLanguage === 'en'">{{ service.serviceDescriptionEn }}</span>
              <span v-if="currentLanguage === 'src'">{{ service.serviceDescriptionSrCyr }}</span>
            </td>
            <td>
              <router-link
                class="link-style"
                :to="{
                  name: 'ResearchInfrastructurePregledDetails',
                  params: { researchInfrastructureId: service.infraStructureId, pathInformation: { search: search, path: 'Home' } },
                }"
              >
                <span v-if="currentLanguage === 'sr'">{{ service.infrastructureNameSr }}</span>
                <span v-if="currentLanguage === 'en'">{{ service.infrastructureNameEn }}</span>
                <span v-if="currentLanguage === 'src'">{{ service.infrastructureNameSrCyr }}</span>
              </router-link>
            </td>
            <td>
              <router-link
                class="link-style"
                :to="{
                  name: 'PortalUserOrganizationPregledDetails',
                  params: { portalUserOrganizationId: service.organizationId, pathInformation: { search: search, path: 'Home' } },
                }"
              >
                <span v-if="currentLanguage === 'sr'">{{ service.organizationNameSr }}</span>
                <span v-if="currentLanguage === 'en'">{{ service.organizationNameEn }}</span>
                <span v-if="currentLanguage === 'src'">{{ service.organizationNameSrCyr }}</span>
              </router-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal id="seeMore" scrollable ok-only :ok-title="$t('entity.action.close')">
      <span slot="modal-title"
        ><span v-text="$t('home.aboutModalTitle')"
          >Namena portala</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisement-heading">
          Portal otvorene istraživačke infrastrukture Srbije je zamišljen kao jedinstveno mesto za nalaženje infrastruktura i njihovih usluga potrebnih u naučnoistraživačkom radu i razvoju inovativnih proizvoda.
        </p>
        <p>
          Po zvaničnoj definiciji Evropske unije, istraživačke infrastrukture su svi resursi koji pružaju usluge istraživačkoj zajednici u cilju sprovođenja istraživanja i podržavanja inovacija. Mogu se koristiti i van istraživanja, npr. za obrazovanje ili javne usluge. Mogu biti jednostrani, distribuirani ili virtuelni. Tu su uključeni i važna naučna oprema ili skupovi instrumenata, zbirke, arhive ili naučni podaci, računarski sistemi i komunikacione mreže, bilo koja druga istraživačka i inovativna infrastruktura jedinstvene prirode koja je otvorena za eksterne korisnike. Neki od primera istraživačke infrastrukture su:
        </p>
        <ul>
          <li>DNK sekvencioner.</li>
          <li>Kidalica za merenje mehaničkih karakteristika materijala.</li>
          <li>Laboratorijska pivara.</li>
          <li>Ogledno poljoprivredno dobro.</li>
          <li>Meteorološka baza podataka Srbije.</li>
          <li>Repozitorijum medicinskih slika (rentgen, CT, MRI).</li>
          <li>Data centar.</li>
          <li>Tim finansijskih eksperata.</li>
          <li>Program za simulaciju ponašanja struktura.</li>
        </ul>
        <p>Otvorena istraživačka infrastruktura je sva infrastruktura kojoj imaju pristup istraživači iz drugih organizacija (i laboratorija). Pristup infrastrukturi može biti:</p>
        <ul>
          <li><strong>Vođen istraživanjem</strong> - Pristup na osnovu relevantnosti, odobren nakon plaćanja dodatnih troškova u vezi sa pristupom u odnosu na normalan rad istraživačke infrastrukture</li>
          <li><strong>Vođen tržištem</strong> – Tržišno-orijentisan pristup, odobrava se nakon plaćanja pune naknade za pristup.</li>
          <li><strong>Vođen obrazovanjem</strong> – Privlačenje mladih talenata.</li>
        </ul>
        <p>Svrha portala se ogleda u sledećem:</p>
        <ul>
          <li>Portal omogućuje da naučnoistraživačke organizacije (NIO) izlože postojeću istraživačku infrastrukturu i usluge koje pružaju.</li>
          <li>Da zainteresovane organizacije pronađu usluge zasnovane na istraživačkoj infrastrukturi i saznaju pod kojim uslovima se mogu dobiti.</li>
        </ul>
        <p>Koristi od upotrebe portala koju mogu imati <strong>privredne organizacije i preduzetnici</strong> su:</p>
        <ul>
          <li>Nalaženje opreme koja je potrebna za razvoj i testiranje inovativnih proizvoda i usluga u zemlji.</li>
          <li>Nalaženje usluga kojim se ubrzava proces razvoja, optimizacije i testiranja proizvoda i usluga u zemlji.</li>
          <li>Bolje planiranje razvoja inovativnih proizvoda i usluga.</li>
          <li>Skraćivanje vremena od ideje za novi proizvod ili uslugu do tržišta.</li>
          <li>Poboljšanje kvaliteta proizvoda i usluga efikasnije korišćenje sopstvenih razvojnih kapaciteta.</li>
        </ul>
        <p>Koristi od upotrebe portala koju mogu imati <strong>naučno istraživačke organizacije</strong> su:</p>
        <ul>
          <li>Proširivanje saradnje sa privrednim organizacijama, društvenim institucijama i drugim NIO iz Srbije i inostranstva.</li>
          <li>Dolaženje do ideja i partnera za projekte.</li>
          <li>Podizanje kapaciteta tehničkog i naučno-istraživačkog osoblja.</li>
          <li>Dodatni prihodi od pružanja usluga.</li>
          <li>Olakšano pokrivanje troškova amortizacije i održavanja opreme.</li>
          <li>Shvatanje potreba za novim uslugama istraživanja i razvoja.</li>
          <li>Povećanje vidljivosti i ugleda NIO.</li>
          <li>Priliv talenata iz zemalja u okruženju i šire.</li>
        </ul>
        <p>Koristi od upotrebe portala koju mogu imati <strong>istraživači iz NIO</strong> su:</p>
        <ul>
          <li>Rad na realnim istraživačko-razvojnim izazovima u privrednim i društvenim organizacijama.</li>
          <li>Tešnja saradnja sa privredom, drugim NIO i društvenim organizacijama.</li>
          <li>Otkrivanje realnih razvojnih izazova i kreiranje naučnih i inovativnih projekata za njihovo rešavanje.</li>
          <li>Podizanje sopstvenih istraživačkih kapaciteta.</li>
          <li>Zadovoljstvo primene stečenog znanja i veština u rešavanju realnih razvojnih problema.</li>
          <li>Dodatni prihodi.</li>
        </ul>
        <p>Koristi od upotrebe portala koju mogu imati <strong>Ministarstvo nauke, tehnološkog razvoja i inovacija, Fond za nauku i Fond za inovacionu delatnost</strong> su:</p>
        <ul>
          <li>Praćenje efikasnosti korišćenja postojeće istraživačke infrastrukture.</li>
          <li>Bolje razumevanje potreba naučnih, privrednih i društvenih organizacija za novom istraživačkom infrastrukturom.</li>
          <li>Donošenje odluka po zahtevima za nabavku nove opreme bazirano na činjenicama.</li>
          <li>Izbegavanje dupliranja kapaciteta.</li>
          <li>Bolji naučno-istraživački rezultati.</li>
          <li>Digitalizovana podrška inovacionom eko sistemu Srbije.</li>
        </ul>
        <p>Koristi od upotrebe portala koju može imati <strong>država i društvo u celini</strong> su:</p>
        <ul>
          <li>Efikasnije korišćenje opreme i povratak investicije pre nego što oprema zastari.</li>
          <li>Brži razvoj malih i srednjih preduzeća i privrednih organizacija generalno.</li>
          <li>Ubrzani privredni rast.</li>
          <li>Podrška implementacije pametne specijalizacije i građanske nauke.</li>
          <li>Zaustavljanje odliva visokostručnog kadra iz zemlje.</li>
          <li>Bolji ugled države u okruženju i svetu.</li>
          <li>Bolje razumevanje građana o opravdanosti ulaganja u nauku i istraživanje.</li>
        </ul>
        <p>Suština portala je da se intenziviranjem korišćenja postojeće istraživačke infrastrukture znatno
poveća inovacioni kapacitet srpske privrede, čime bi se poboljšali i naučni rezultati NIO i to bez
dodatnih ulaganja.</p>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>

<style scoped>
.UniqueFullWidth .input-group-text {
  width: 48px;
  border-right: none;
  background-color: #ffffff;
}
.UniqueFullWidth [class^='fa-'],
[class*=' fa-'] {
  display: inline-block;
  width: 100%;
}
.UniqueFullWidth .LoginInput {
  border-left: none;
}
.jumbotron {
  background-image: url('/content/images/research_find_loupe_search1.jpg');
  background-size: cover;
  background-repeat: no-repeat;
  border-radius: 0 !important;
  padding: 30px;
  margin-bottom: 0px;
}
::placeholder {
  color: rgb(226, 226, 226);
  opacity: 1; /* Firefox */
}
::-ms-input-placeholder {
  /* Edge 12 -18 */
  color: rgb(226, 226, 226);
}
#searchbox {
  background-color: #1f3448;
}

/* MIDDLE PART */

.middlePart {
  background-color: #f1f5f8;
  padding-top: 50px;
  padding-bottom: 50px;
}

.first {
  position: relative;
  height: 188px;
  padding: 0px;
}

.second {
  position: relative;

  height: 188px;
  padding: 0px;
}

.third {
  position: relative;

  height: 188px;
  padding: 0px;
}

.img-box {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.content {
  top: 50%;
  left: 50%;
  position: absolute;
  transform: translate(-50%, -50%);
  text-align: center;
}

.badge-cards {
  width: 52px;
  padding: 8px 0px;
  font-size: 0.9375rem;
  font-weight: 400;
}

.button-cards:hover {
  background-color: #f39c12;
  border-color: #f39c12;
}

.button-cards:active {
  background-color: #f39c12;
}

.button-cards:hover {
  background-color: #f39c12;
}

.link-style:hover{
  color: blue;
}

/* MIDDLE PART END */

@media screen and (max-width: 576px) {
  .jumbotron {
    padding: 16px;
  }

  .display-3 {
    font-size: 3.4rem;
    word-wrap: normal;
    line-height: 1;
    letter-spacing: normal;
  }
}

@media screen and (max-width: 446px) {
  .display-3 {
    font-size: 2.8rem;
    word-wrap: normal;
    line-height: 1;
    letter-spacing: normal;
  }
}

@media screen and (max-width: 380px) {
  .display-3 {
    font-size: 2rem;
    word-wrap: normal;
    line-height: 1;
    letter-spacing: normal;
  }
}
</style>
