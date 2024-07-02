<template>
  <div id="footer" class="footer">
    <div class="logoi">
      <img src="/content/images/vlada_republike_srbije.jpg" class="logo1" alt="" />
      <img src="/content/images/NITRA logo.png" class="logo2" alt="" />
      <img src="/content/images/Philip.png" class="logo3" alt="" />
      <img src="/content/images/StarTech logo.jpg" class="logo4" alt="" />
      <img src="/content/images/naledLogo.jpg" class="logo5" alt="" />
    </div>
    <div class="footFootera">
      <div class="rightContent">
        <router-link to="/b2b/contact-us">{{ $t('footer.contact') }}</router-link>
        <router-link to="/b2b/questions">{{ $t('global.menu.faq') }}</router-link>
        <router-link to="/b2b/terms-of-use">{{ $t('footer.terms') }}</router-link>
        <router-link to="/b2b/cookie-policy">{{ $t('footer.cookie') }}</router-link>
        <router-link to="/b2b/privacy-policy">{{ $t('footer.privacy') }}</router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./b2b-jhi-footer.component.ts"></script>

<style scoped>
.footer {
  width: 92%;
  margin-left: auto;
  margin-right: auto;
  background-color: rgb(255, 208, 208);

  height: 16em;
  background-color: white;
  border-bottom: 2em solid rgb(11, 62, 132);
  box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.2);
  position: relative;
  margin-top: 8em;
  display: flex;
  flex-direction: column;
  border: none !important;
}

.logoi {
  height: 70%;
  width: 90%;
  display: flex;
  justify-content: space-between;
  padding: 20px;
  margin-left: 5em;
  max-width: 100%;
}

.logoi img {
  margin-right: 5px;
  text-align: center;
  box-sizing: border-box;
  max-width: 100%;
}

.logo2 {
  height: 5em;
  margin-top: 1.4em;
}

.logo3 {
  height: 8em;
}

.logo4 {
  height: 7em;
}

.logo5 {
  height: 3em;
  margin-top: 2.5em;
}

.footFootera {
  width: 100%;
  border-top: 8px solid rgba(102, 124, 150, 255);
  align-self: center;
  height: 6em;
  background: #1f3448 !important;
  justify-content: center;
  align-items: flex-end;
}

.rightContent {
  text-align: center;
  display: flex;
  justify-content: center;
  align-self: flex-end;
  margin-top: 2em;
}

.rightContent a {
  margin-bottom: 0;
  font-size: small;
  color: rgb(149, 180, 242);
  padding: 5px;
  margin-right: 1em;
  display: flex;
}

.rightContent a:hover {
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 10%;
  transition: 0.3s;
  text-decoration: none;
  color: rgb(149, 180, 242);
}

@media screen and (max-width: 768px) {
  .logoi {
    margin: 0;
    flex-wrap: wrap;
    display: flex;
  }

  .logoi img {
    width: 50%;
    height: 20%;
    max-width: none;
  }

  .rightContent a {
    font-size: 0.5em;
    display: flex;
  }
}

@media screen and (max-width: 1250px) {
  .logoi {
    margin: 0;
    flex-wrap: wrap;
    display: flex;
    align-items: flex-end;
    padding: 0;
    width: 100%;
  }
  .footer {
    height: 30em;
  }
  .footFootera {
    margin-top: auto;
    margin-bottom: none;
  }

  .logoi img {
    width: 48%;
    height: 25%;
    max-width: none;
  }
  .logo1 {
    height: 40% !important;
  }
  .logo2 {
    margin-bottom: 1.5em !important;
    height: 25% !important;
  }
  .logo3 {
    width: 40% !important;
    height: 40% !important;
    margin-left: 1em !important;
  }
  .logo4 {
    height: 30% !important;
    margin-bottom: 1em !important;
    margin-right: 1em !important;
  }
  .logo5 {
    margin-top: 0 !important;
    margin-left: 6em !important;
    width: 40% !important;
    height: 20% !important;
  }

  .rightContent a {
    font-size: 0.5em;
    display: flex;
  }
}
</style>
