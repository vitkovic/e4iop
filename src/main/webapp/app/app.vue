<template>
  <div id="app">
   
    <div id="app-header" class="fixed">

		 <!-- <vue-editor v-model="content" :editor-toolbar="customToolbar" />-->
		
      <b2b-jhi-navbar v-if="checkB2Bdomain()" ref="b2bnavbar" @adv:change="changeAdv" 
      @news:change="changeNews"  @quests:change="changeQuestions" @companies:change="changeCompanies" ></b2b-jhi-navbar>
     
      <jhi-navbar v-else></jhi-navbar> 
     
      <div id="hide" v-show='toggle' v-if="checkB2Bdomain()">
     <b-button v-if="checkB2Bdomain() && showbutt" @click='hideAll()'  variant="info" style="margin-left:5px;margin-bottom:10px;">{{ $t('home.showHideButton') }}</b-button>
      		<b2b-searchsection v-bind:advertisements="advertisements" @hide-all="hideAll" @click.native=''
      		v-bind:cmsnews="cmsnews" v-bind:cmsquestions="cmsquestions" v-bind:companies="companies"></b2b-searchsection>
      		
   <!--    <ag-grid-vue 
    			style="width: 500px; height: 200px"
    			class="ag-theme-alpine"
    			:columnDefs="columnDefs"
    			:rowData="rowData"
  		>
  		</ag-grid-vue> -->		
      		
      		
   	  </div>
      
   
      
      
      
      
      
      
    </div>
    <notifications width="300px" classes="vue-notification notification-font" position="top center"/> 
    	 
    <div class="container-fluid">
		
      <div class="card jh-card marg">
        <router-view :key="$route.fullPath"></router-view>
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

.marg {
  margin-top: 48px;
}

@media screen and (max-width: 788px) {
  .marg {
    margin-top: 88px;
  }
}
</style>