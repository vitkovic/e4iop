<template>
  <div id="footer" class="footer mt-5">
    <b-container fluid="md">
      <b-row class="mt-4 mb-4">
        <b-col sm="12" class="d-flex align-items-center justify-content-center">
          <div class="text-center">
            <span v-text="$t('home.footerText')"></span>
          </div>
        </b-col>
      </b-row>
      <b-row> </b-row>
      <b-row class="d-flex align-items-center justify-content-around mb-3 pt-3 pb-3 footerLinks">
        <div class="col-3">
          <router-link to="/about" class="text-body link">
            <span
              ><b-icon icon="caret-right" scale="1"></b-icon
              ><span class="font-weight-normal" v-text="$t('home.oProjektu')"> O projektu</span></span
            >
          </router-link>
        </div>
        <div class="col-3">
          <router-link to="/privacy-policy" class="text-body link">
            <span
              ><b-icon icon="caret-right" scale="1"></b-icon
              ><span class="font-weight-normal" v-text="$t('home.politikaPrivatnosti')"> O projektu</span></span
            >
          </router-link>
        </div>
        <div class="col-3">
          <router-link to="/instructions" class="text-body link">
            <span
              ><b-icon icon="caret-right" scale="1"></b-icon
              ><span class="font-weight-normal" v-text="$t('home.uputstvoZaUpotrebu')"> O projektu</span></span
            >
          </router-link>
        </div>
        <div class="col-3">
          <router-link to="/contact" class="text-body link">
            <span
              ><b-icon icon="caret-right" scale="1"></b-icon
              ><span class="font-weight-normal" v-text="$t('home.kontaktIPodsrka')"> O projektu</span></span
            >
          </router-link>
        </div>
      </b-row>
      <!-- <b-row style="background-color: #f7efde;">
        <b-col sm="12" class="d-flex align-items-center justify-content-center">
          <div>
            <b-list-group class="list-group-horizontal">
              <b-list-group-item style="background-color: transparent; border: 0;"
                ><b-icon icon="caret-right" scale="1"></b-icon><span v-text="$t('home.oProjektu')"> O projektu</span></b-list-group-item
              >
              <b-list-group-item style="background-color: transparent; border: 0;"
                ><b-icon icon="caret-right" scale="1"></b-icon><span v-text="$t('home.politikaPrivatnosti')"></span>
              </b-list-group-item>
              <b-list-group-item style="background-color: transparent; border: 0;"
                ><b-icon icon="caret-right" scale="1"></b-icon><span v-text="$t('home.uputstvoZaUpotrebu')"></span
              ></b-list-group-item>
              <b-list-group-item style="background-color: transparent; border: 0;"
                ><b-icon icon="caret-right" scale="1"></b-icon><span v-text="$t('home.kontaktIPodsrka')"></span
              ></b-list-group-item>
            </b-list-group>
          </div>
          &nbsp;
        </b-col>
      </b-row> -->
    </b-container>
    <b-container fluid="md" class="mb-3">
      <div class="partnersFooter">
        <a href="https://nitra.gov.rs/cir" target="_blank">
          <img src="/content/images/logo-nitra.png" alt="Ministarstvo nauke, tehnoološkog razvoja i inovacija logo" />
        </a>
        <a href="https://euprava.gov.rs/" target="_blank">
          <img src="/content/images/logo-euprava.png" alt="eUprava logo" />
        </a>
        <a href="https://www.undp.org/" target="_blank">
          <img src="/content/images/undp-logo-blue.png" alt="UNDP logo" />
        </a>
        <a href="https://www.worldbank.org/en/home" target="_blank">
          <img src="/content/images/logo-world-bank.png" alt="The World Band Group logo" />
        </a>
        <a href="https://cubeteam.com/sr/naslovna" target="_blank">
          <img src="/content/images/cube_logo.png" alt="CUBE Team logo" />
        </a>
      </div>
    </b-container>

    <!-- <b-container style="padding-top: 30px; padding-bottom: 30px;">
      <b-row class="d-flex align-items-center justify-content-center">
        <b-col sm="2" class="d-flex align-items-center justify-content-center">
          <div
            class="d-flex align-items-center justify-content-center"
            style="
              position: relative;
              background-image: url('/content/images/logo-nitra.png');
              background-repeat: no-repeat;
              background-size: contain;
              width: 300px;
              height: 188px;
            "
          >
            &nbsp; &nbsp;
          </div>
        </b-col>
        <b-col sm="2" class="d-flex align-items-center justify-content-center">
          <div
            class="d-flex align-items-center justify-content-center"
            style="
              position: relative;
              background-image: url('/content/images/logo-euprava.png');
              background-repeat: no-repeat;
              background-size: contain;
              width: 300px;
              height: 188px;
            "
          >
            &nbsp; &nbsp;
          </div>
        </b-col>
        <b-col sm="2" class="d-flex align-items-center justify-content-center">
          <div
            class="d-flex align-items-center justify-content-center"
            style="
              position: relative;
              background-image: url('/content/images/logo-world-bank.png');
              background-repeat: no-repeat;
              background-size: contain;
              width: 300px;
              height: 188px;
            "
          >
            &nbsp; &nbsp;
          </div>
        </b-col>

        <b-col sm="2" class="d-flex align-items-center justify-content-center">
          <div
            class="d-flex align-items-center justify-content-center"
            style="
              position: relative;
              background-image: url('/content/images/undp-logo-blue.png');
              background-repeat: no-repeat;
              background-size: contain;
              width: 30px;
              height: 188px;
            "
          >
            &nbsp; &nbsp;
          </div>
        </b-col>
        <b-col sm="2" class="d-flex align-items-center justify-content-center">
          <div
            class="d-flex align-items-center justify-content-center"
            style="
              position: relative;
              background-image: url('/content/images/cube_logo.png');
              background-repeat: no-repeat;
              background-size: contain;
              width: 119px;
              height: 188px;
            "
          >
            &nbsp; &nbsp;
          </div>
        </b-col>
      </b-row>
    </b-container> -->

    <!--
        <p v-text="$t('footer')">This is your footer</p>
        -->
  </div>
</template>

<script lang="ts" src="./jhi-footer.component.ts"></script>

<style scoped>
.footer {
  text-align: center;
  height: auto;
  margin: 0;
  padding-top: 10px;
  padding-bottom: 0;
  /*  background-color: #f7efde;*/
}

.footerLinks {
  background-color: #f7efde;
}

.partnersFooter {
  width: 100%;
  display: flex;
  justify-content: space-around;
  padding: 24px 0;
}

.partnersFooter a img {
  height: 3rem;
}

@media screen and (max-width: 768px) {
  .link {
    font-size: 14px;
  }
}

@media screen and (max-width: 495px) {
  .link {
    font-size: 12px;
  }
}

@media screen and (max-width: 430px) {
  .link {
    font-size: 10px;
  }
}

/* ----------------- */

@media screen and (max-width: 1320px) {
  .partnersFooter a img {
    height: 3rem;
  }
}

@media screen and (max-width: 992px) {
  .partnersFooter a img {
    height: 2.4rem;
  }
}

@media screen and (max-width: 700px) {
  .partnersFooter a img {
    height: 2.2rem;
  }
}

@media screen and (max-width: 580px) {
  .partnersFooter {
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 16px;
  }

  .partnersFooter a img {
    height: 3rem;
  }
}
</style>
