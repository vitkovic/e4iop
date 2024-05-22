<template>
    <div style="margin-top:-10px;padding:0px;">
	<div  id="searchbox" >		   
	   <b-input-group  class="mb-2" style="padding:50px;padding-top:30px;">
      							<b-input-group-prepend is-text>
						        <b-icon icon="search" scale="1.3"></b-icon>
      							</b-input-group-prepend>
      							<b-form-input type="search"  placeholder="Traži..." ></b-form-input>
    						</b-input-group>		   
			   
	</div>	   
	
<div style="margin:0px;margin-top:-30px;padding-top:15px;background-color: #abbbc960;">
	
	
  <b-jumbotron class="text-center" style="padding:40px;padding-bottom:70px;" >
 
    <template #header>Portal otvorene istraživačke infrastrukture Srbije</template>
    <template #lead >
      <strong>Pregledajte i pretražite istraživačku infrastrukturu Srbije!</strong>
    </template>
    <b-button variant="primary" href="#">Saznaj više...</b-button>

  </b-jumbotron>
</div>
 <div style="margin:0px;margin-top:-35px;background-color: #f1f5f8;">
		<b-container fluid="sm" style="margin-top:0px;padding-top:0px;">
			
			<b-row>
				<b-col sm="4" class="d-flex align-items-center justify-content-center" style="margin-top:-40px;">
					<div class="d-flex align-items-center justify-content-center" style="position:relative;border-radius: 25px;background-image:url('/content/images/white300x188.jpg');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
						
							<b-container fluid="sm" style="margin-top:0px;">
								<b-row><b-col class="d-flex align-items-center justify-content-center"><h5>INFRASTRUKTURA</h5></b-col></b-row>
								<b-row style="height:20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
								<b-row><b-col class="d-flex align-items-center justify-content-center"><h4><span class="badge badge-pill badge-warning">Info</span></h4></b-col></b-row>
							</b-container>
						
						
					</div>		
				</b-col>
				<b-col sm="4" class="d-flex align-items-center justify-content-center" style="margin-top:-40px;">
					<div class="d-flex align-items-center justify-content-center" style="position:relative;border-radius: 25px;background-image:url('/content/images/white300x188.jpg');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
							
							<b-container fluid="sm" style="margin-top:0px;">
								<b-row><b-col class="d-flex align-items-center justify-content-center"><h5>USLUGE</h5></b-col></b-row>
								<b-row style="height:20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
								<b-row><b-col class="d-flex align-items-center justify-content-center"><h4><span class="badge badge-pill badge-warning">Info</span></h4></b-col></b-row>
							</b-container>
					</div>
				</b-col>
				<b-col sm="4" class="d-flex align-items-center justify-content-center" style="margin-top:-40px;">
					<div class="d-flex align-items-center justify-content-center" style="position:relative;border-radius: 25px;background-image:url('/content/images/white300x188.jpg');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
							<b-container fluid="sm" style="margin-top:0px;">
								<b-row><b-col class="d-flex align-items-center justify-content-center"><h5>ORGANIZACIJE</h5></b-col></b-row>
								<b-row style="height:20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
								<b-row><b-col class="d-flex align-items-center justify-content-center"><h4><span class="badge badge-pill badge-warning">Info</span></h4></b-col></b-row>
							</b-container>
					</div>	
							
				</b-col>
				
			</b-row>
			<b-row style="height:50px;">
				<b-col sm="12" >
						
				</b-col>
			</b-row>
			</b-container>
			
		</div>
			
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
    
</template>

<script lang="ts" src="./home.component.ts">
</script>

<style scoped>
.UniqueFullWidth .input-group-text {
  width: 48px;
  border-right: none;
  background-color: #ffffff;
}
.UniqueFullWidth [class^="fa-"], [class*=" fa-"] {
  display: inline-block;
  width: 100%;
}
.UniqueFullWidth .LoginInput {
  border-left: none;
}
.jumbotron {
  background-image: url("/content/images/research_find_loupe_search1.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  border-radius: 0 !important;
}
::placeholder {
  color: rgb(226, 226, 226);
  opacity: 1; /* Firefox */
}
::-ms-input-placeholder { /* Edge 12 -18 */
  color: rgb(226, 226, 226);
}
#searchbox {
	background-color:#1f3448;}
</style>