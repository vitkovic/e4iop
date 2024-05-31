<template>
  <div id="app">
   
    <div id="app-header" class="fixed">
      <b2b-jhi-navbar v-if="checkB2Bdomain()"></b2b-jhi-navbar>
      <jhi-navbar v-else></jhi-navbar>

    </div>
    <notifications width="300px" classes="vue-notification notification-font" position="top center"/> 
    
    <div class="container-fluid">
		
      <div class="card jh-card mt-5">
        <router-view :key="$route.path"></router-view>
      </div>
      <b-modal id="login-page" hide-footer lazy>
        <span slot="modal-title" id="login-title" v-text="$t('login.title')">Sign in</span>
        <login-form></login-form>
      </b-modal>

      <b2b-jhi-footer v-if="checkB2Bdomain()"></b2b-jhi-footer>
      <jhi-footer v-else></jhi-footer>
    </div>
    
  </div>
</template>

<script lang="ts" src="./app.component.ts">

</script>

<style scoped>
.fixed{
	position: fixed;
	z-index: 999;
	background-color: white;
	width: 100%;
	box-shadow: 2px 2px 5px rgba(0,0,0, 0.3);
}

.container-fluid{
	padding-top: 9.3rem;
}
</style>