<template>
    <div>
	
        <div class="container-fluid">
        <b-row align-v="center" class="homePageRow">
            <b-col sm="12" align-self="center">
                <span v-text="$t('global.messages.welcome')"></span>
            </b-col>
        </b-row>
        <b-row class="mt-4">                                     
            <b-col sm="3">
            	<router-link :to="{path: '/research-infrastructure-pregled'}">
                <b-card
                    border-variant="info"                                       
                    header-bg-variant="info"
                    header-text-variant="white"
                    align="center"
                    footer-bg-variant="info"
                    footer-text-variant="white"    
                >
                    <!--  opcionalno moze da se doda klasa za header i da se izbaci header bg variant
                    header-class="blue-background"              
                    -->

                    <template v-slot:header>
                        <span v-text="$t('home.infrastructures')"></span>
                    </template>
                    <template v-slot:footer>
                        <span >{{researchInfrastructureCount}}</span>
                    </template>
                    <img src="/content/images/infrastructure.jpg" width="30%"><br>                    
                </b-card>     
                </router-link>                           
            </b-col>
            <b-col sm="3">
				<router-link :to="{path: '/ri-service-pregled'}">
                <b-card
                    border-variant="info"                                       
                    header-bg-variant="info"
                    header-text-variant="white"
                    align="center"
                    footer-bg-variant="info"
                    footer-text-variant="white"                    
                >
                    <template v-slot:header>
                        <span v-text="$t('home.services')"></span>
                    </template>
                    <template v-slot:footer>
                        <span v-if="riServicesCount">{{riServicesCount}}</span>
                    </template>
                    <img src="/content/images/services1.png" width="30%"><br>                    
                </b-card>
                </router-link>  
            </b-col>
            <b-col sm="3">
            	<router-link :to="{path: '/portal-user-organization-pregled'}">
                <b-card
                    border-variant="info"                                       
                    header-bg-variant="info"
                    header-text-variant="white"
                    align="center"
                    footer-bg-variant="info"
                    footer-text-variant="white"                    
                >
                    <template v-slot:header>
                        <span v-text="$t('home.organizations')"></span>
                    </template>
                    <template v-slot:footer>
                        <span >{{portalOrganizationsCount}}</span>
                    </template>
                    <img src="/content/images/organizations1.png" width="30%"><br>                    
                </b-card>
                </router-link> 
            </b-col>
            <b-col sm="3">
                <b-card
                    border-variant="info"                                       
                    header-bg-variant="info"
                    header-text-variant="white"
                    align="center"
                    footer-bg-variant="info"
                    footer-text-variant="white"                    
                >
                    <template v-slot:header>
                        <span v-text="$t('home.users')"></span>
                    </template>
                    <template v-slot:footer>
                        <span >{{portalUsersCount}}</span>
                    </template>
                    <img src="/content/images/users1.png" width="30%"><br>                    
                </b-card>
            </b-col>
        </b-row>
        <p v-text="$t('home.text')" class="mt-4 ml-4" style="font-size:larger; text-align:center;">           
        </p>
        
        </div>
        
        
    
   
    
    <!-- <auto-comp></auto-comp>
            <div>
                <div class="alert alert-success" v-if="authenticated">
                    <span v-if="username" v-text="$t('home.logged.message', { 'username': username})">You are logged in as user "{{username}}"</span>
                </div>

                <div class="alert alert-warning" v-if="!authenticated">
                    <span v-text="$t('global.messages.info.authenticated.prefix')">If you want to </span>
                    <a class="alert-link" v-on:click="openLogin()" v-text="$t('global.messages.info.authenticated.link')">sign in</a><span v-html="$t('global.messages.info.authenticated.suffix')">, you can try the default accounts:<br/>- Administrator (login="admin" and password="admin") <br/>- User (login="user" and password="user").</span>
                </div>
                <div class="alert alert-warning" v-if="!authenticated">
                    <span v-text="$t('global.messages.info.register.noaccount')">You don't have an account yet?</span>&nbsp;
                    <router-link class="alert-link" to="/register" v-text="$t('global.messages.info.register.link')">Register a new account</router-link>
                </div>
            </div>

            <p v-text="$t('home.question')">
                If you have any question on JHipster:
            </p>

            <ul>
                <li><a href="https://www.jhipster.tech/" target="_blank" rel="noopener" v-text="$t('home.link.homepage')">JHipster homepage</a></li>
                <li><a href="http://stackoverflow.com/tags/jhipster/info" target="_blank" rel="noopener" v-text="$t('home.link.stackoverflow')">JHipster on Stack Overflow</a></li>
                <li><a href="https://github.com/jhipster/generator-jhipster/issues?state=open" target="_blank" rel="noopener" v-text="$t('home.link.bugtracker')">JHipster bug tracker</a></li>
                <li><a href="https://gitter.im/jhipster/generator-jhipster" target="_blank" rel="noopener" v-text="$t('home.link.chat')">JHipster public chat room</a></li>
                <li><a href="https://twitter.com/jhipster" target="_blank" rel="noopener" v-text="$t('home.link.follow')">follow @jhipster on Twitter</a></li>
            </ul>

            <p>
                <span v-text="$t('home.like')">If you like JHipster, don't forget to give us a star on</span> <a href="https://github.com/jhipster/generator-jhipster" target="_blank" rel="noopener" v-text="$t('home.github')">GitHub</a>!
            </p>
        -->  
    </div>
</template>

<script lang="ts" src="./home.component.ts">
</script>
<!--    
<script lang="ts" src="./axioshttp.ts">
</script>

<script>
import UploadFiles from "../../utility/upload/uploadfiles";
import AutoComp from "../../utility/autocomplete/autocomponent"

export default {
  name: "App",
  components: {
     UploadFiles,
     AutoComp
  }
};
</script>
-->