<template>
    <div id="footer" class="footer">
		
		
		<b-container fluid="sm">
			<b-row style="height:100px;">
			
				<b-col sm="12" class="d-flex align-items-center justify-content-center">
					<div class="text-center">
						<span v-text="$t('home.footerText')"></span>
					</div>
				</b-col>
			</b-row>
			<b-row>
			
				
			</b-row>
			<b-row style="background-color: #f7efde;">
			
				<b-col sm="12" class="d-flex align-items-center justify-content-center">
					<div>
						<b-list-group class="list-group-horizontal">
  							<b-list-group-item style="background-color:transparent;border: 0;"><b-icon icon="caret-right" scale="1" ></b-icon><span v-text="$t('home.oProjektu')"> O projektu</span></b-list-group-item>
  							<b-list-group-item style="background-color:transparent;border: 0;"><b-icon icon="caret-right" scale="1" ></b-icon><span v-text="$t('home.politikaPrivatnosti')"></span> </b-list-group-item>
						    <b-list-group-item style="background-color:transparent;border: 0;"><b-icon icon="caret-right" scale="1" ></b-icon><span v-text="$t('home.uputstvoZaUpotrebu')"></span></b-list-group-item>
							<b-list-group-item style="background-color:transparent;border: 0;"><b-icon icon="caret-right" scale="1" ></b-icon><span v-text="$t('home.kontaktIPodsrka')"></span></b-list-group-item>
						</b-list-group>
					</div>
					&nbsp;
				</b-col>
			</b-row>
		</b-container>
		<b-container style="padding-top:30px;padding-bottom:30px;">
				<b-row class="d-flex align-items-center justify-content-center">
					<b-col sm="2" class="d-flex align-items-center justify-content-center">
						<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/logo-nitra.png');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
						
							&nbsp;
						
						</div>		
					</b-col>
					<b-col sm="2" class="d-flex align-items-center justify-content-center">
						<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/logo-euprava.png');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
						
							&nbsp;
						
						</div>		
					</b-col>
					<b-col sm="2" class="d-flex align-items-center justify-content-center">
						<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/logo-world-bank.png');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
						
							&nbsp;
						
						</div>		
					</b-col>
					
					<b-col sm="2" class="d-flex align-items-center justify-content-center">
						<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/undp-logo-blue.png');background-repeat: no-repeat;background-size:contain;width:30px;height:188px;">&nbsp;
						
							&nbsp;
						
						</div>		
					</b-col>
					<b-col sm="2" class="d-flex align-items-center justify-content-center" >
						<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/cube_logo.png');background-repeat: no-repeat;background-size:contain;width:119px;height:188px;">&nbsp;
						
							&nbsp;
						
						</div>		
					</b-col>
					
				</b-row>
			</b-container>
		
        <!--
        <p v-text="$t('footer')">This is your footer</p>
        -->
    </div>
</template>

<script lang="ts" src="./jhi-footer.component.ts">
</script>

<style scoped>
    .footer {
        text-align: center;
        height:auto;
        margin:0;
        padding-top:10px;
        padding-bottom: 0;
      /*  background-color: #f7efde;*/
    }
</style>
