<template>
	
	 <div id = "advertisements" v-model="advertisements" v-if="advertisements && advertisements.length > 0">
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.advertisement.home.title')" id="advertisement-heading">Advertisements</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && advertisements && advertisements.length === 0">
            <span v-text="$t('riportalApp.advertisement.home.notFound')">No advertisements found</span>
        </div>
        <div class="table-responsive" v-if="advertisements && advertisements.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('type.id')"><span v-text="$t('riportalApp.advertisement.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('kind.id')"><span v-text="$t('riportalApp.advertisement.kind')">Kind</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('riportalApp.advertisement.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('riportalApp.advertisement.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('subsubcategory.name')"><span v-text="$t('riportalApp.advertisement.subsubcategory')">Subsubcategory</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subsubcategory.name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('duration.id')"><span v-text="'Expiration Date'">Duration</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duration.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('company.id')"><span v-text="$t('riportalApp.advertisement.company')">Company</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.id'"></jhi-sort-indicator></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="advertisement in selectedAdvertisements"
                    :key="advertisement.id">
                    <td>
                        <div v-if="advertisement.type">
                            {{advertisement.type.type}}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.kind">
                            {{advertisement.kind.kind}}
                        </div>
                    </td>
                    <td>
                        <router-link :to="{name: 'AdvertisementView', params: {advertisementId: advertisement.id}}" class="font-weight-normal text-body">{{advertisement.title}}</router-link>
                    </td>
                    <td>{{advertisement.description}}</td>
                    <td>
                        <div v-if="advertisement.subsubcategory">
                            {{advertisement.subsubcategory.name}}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.duration && advertisement.activationDatetime">
                            <!-- {{advertisement.duration.duration}} -->
                            {{ $d(Date.parse(getExpirationDate(advertisement)), 'short') }}
                        </div>
                    </td>
                    <td>
                        <div v-if="advertisement.company">
                            <router-link :to="{name: 'CompanyView', params: {companyId: advertisement.company.id}}" class="font-weight-normal text-body">{{advertisement.company.name}}</router-link>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div v-show="advertisements && advertisements.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>

	
	
	
	
	
	
	
	<div class="home"  v-else="!advertisements && advertisements.length == 0">
		<div class="div1">
			<div class="divZaA">
				<b-link :to="{ name: 'AdvertisementCreate', query: { type: 'offer' } }" class="kvadrat kvOne">
					<span class="velikiTekst" v-text="$t('home.b2b.square.createOfferSmall')">Желиш да</span>
					<span class="maliTekst" v-text="$t('home.b2b.square.createOfferLarge')">огласиш понуду?</span>
				</b-link>
				<b-link :to="{ name: 'AdvertisementCreate', query: { type: 'demand' } }" class="kvadrat kvTwo">
					<span class="velikiTekst" v-text="$t('home.b2b.square.createDemandSmall')">Желиш да</span>
					<span class="maliTekst" v-text="$t('home.b2b.square.createDemandLarge')">огласиш потребу?</span>
				</b-link>
				<b-link :to="{ name: 'AdvertisementBrowse' }" class="kvadrat kvThree">
					<span class="velikiTekst" v-text="$t('home.b2b.square.findAllOffersSmall')">Погледај све</span> 
					<span class="maliTekst" v-text="$t('home.b2b.square.findAllOffersLarge')">понуде</span>
				</b-link>
				<b-link :to="{ name: 'AdvertisementBrowse' }" class="kvadrat kvFour">
					<span class="velikiTekst" v-text="$t('home.b2b.square.findAllDemandsSmall')">Пoгледај све</span> 
					<span class="maliTekst" v-text="$t('home.b2b.square.findAllDemandsLarge')">потребе</span>
				</b-link>
			</div>
		</div>

		<p v-text="$t('home.advertisements.offers.title')" class="pTag">Актуелне понуде</p>

		<div class="divZaPonudeiPotraznje">
			<div class="divOne">
				<h6>4 {{ $t('home.advertisements.advertisements') }}</h6>

				<img src="/content/images/cat1.jpg" alt="" class="background-imazh">
				<button v-text="$t('home.advertisements.offers.button')">Погледај понуду</button>
			</div>
			<div class="divTwo">
				<h6>9 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat2.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.offers.button')">Погледај понуду</button>
			</div>
			<div class="divThree">
				<h6>3 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat3.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.offers.button')">Погледај понуду</button>
			</div>
			<div class="divFour">
				<h6>11 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat4.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.offers.button')">Погледај понуду</button>
			</div>
			<div class="divFive">
				<h6>24 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat4.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.offers.button')">Погледај понуду</button>
			</div>
		</div>


		<p class="pTag1" v-text="$t('home.advertisements.demands.title')">Актуелне потражње</p>

		<div class="divZaPonudeiPotraznje">
			<div class="divOne">
				<h6>4 {{ $t('home.advertisements.advertisements') }}</h6>

				<img src="/content/images/cat1.jpg" alt="" class="background-imazh">
				<button v-text="$t('home.advertisements.demands.button')">Погледај потражњу</button>
			</div>
			<div class="divTwo">
				<h6>9 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat2.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.demands.button')">Погледај потражњу</button>
			</div>
			<div class="divThree">
				<h6>3 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat3.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.demands.button')">Погледај потражњу</button>
			</div>
			<div class="divFour">
				<h6>11 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat4.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.demands.button')">Погледај потражњу</button>
			</div>
			<div class="divFive">
				<h6>24 {{ $t('home.advertisements.advertisements') }}</h6>
				<img src="/content/images/cat4.jpg" class="background-imazh" alt="">
				<button v-text="$t('home.advertisements.demands.button')">Погледај потражњу</button>
			</div>
		</div>
	</div>
</template>

<script lang="ts" src="./b2b-home.component.ts">


</script>



<style>
.div1 {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: 30em;
	margin-top: 2em;
}

.divZaA {
	display: flex;
	flex-wrap: wrap;
	/*width: 30em;
	height: 30em;*/
	width: 60em;
	height: 30em;
}

.divZaA a {
	font-size: 1.4em;
}

.kvadrat {
	flex: 1 0 50%;
	height: 50%;
	text-decoration: none;
	display: flex;
	justify-content: center;
	align-items: center;
	color: rgba(255, 255, 255, 0.7);
	/*transition: transform 2s ease;*/
	box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
	text-align: center;

	flex-direction: column;
	position: relative;
}


.kvadrat:hover {
	text-decoration: none;
	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
	/*transform: rotate(360deg);*/
	color: white;

	text-shadow: -0.5px -0.5px 0 rgb(128, 0, 0),
		0.5px -0.5px 0 rgb(128, 0, 0),
		-0.5px 0.5px 0 rgb(128, 0, 0),
		0.5px 0.5px 0 rgb(128, 0, 0);
}

.kvOne {
	/*background: linear-gradient(to bottom right, rgb(255, 164, 72), rgb(255, 198, 140)); */
	/*background: linear-gradient(to bottom right, rgb(127, 127, 127), rgb(184, 184, 184));*/
	border-top-left-radius: 30px;
	background-color: rgb(255, 171, 16);
	/*background-image: url('../../../content/images/hexagon.png');*/
	background-size: cover;

}

.kvOne:hover {
	transform: translateX(-2em);
	transition: transform 0.4s ease;
}

.kvOne:hover span {
	color: rgb(255, 255, 255);
	text-shadow: none;
	text-shadow: -0.5px -0.5px 0 rgb(236, 236, 236),
		0.5px -0.5px 0 rgb(236, 236, 236),
		-0.5px 0.5px 0 rgb(236, 236, 236),
		0.5px 0.5px 0 rgb(236, 236, 236);
}

.kvOne:not(:hover) {
	transform: translateX(0);
	transition: 0.3s;
}

.velikiTekst {
	font-size: 1.1em;
	color: white;
	opacity: 0.9;
	z-index: 100;
}

.maliTekst {
	font-size: 1.9em;
	color: white;
	opacity: 0.9;
	z-index: 100;

}

.kvTwo {
	background-color: #1f3448;
	/*background-image: url('../../../content/images/buss.png');*/
	background-size: contain;
	background-repeat: no-repeat;
	background-position: center;
	border-top-right-radius: 30px;
}

.kvTwo:hover span {
	color: rgb(255, 255, 255);
	text-shadow: none;
	text-shadow: -0.5px -0.5px 0 rgb(236, 236, 236),
		0.5px -0.5px 0 rgb(236, 236, 236),
		-0.5px 0.5px 0 rgb(236, 236, 236),
		0.5px 0.5px 0 rgb(236, 236, 236);
}

.kvTwo:hover {
	transform: translateX(2em);
	transition: transform 0.4s ease;
}

.kvTwo:not(:hover) {
	transform: translateX(0);
	transition: 0.3s;
}


.kvThree {

	border-bottom-left-radius: 30px;

	background-color: rgb(153, 153, 153);
	/*background-image: url('../../../content/images/science.png');*/
	background-size: contain;
	background-position: left;
	background-repeat: no-repeat;
	color: white;
	text-shadow: -0.5px -0.5px 0 rgb(174, 174, 174),
		0.5px -0.5px 0 rgb(174, 174, 174),
		-0.5px 0.5px 0 rgb(174, 174, 174),
		0.5px 0.5px 0 rgb(174, 174, 174);

}

.kvThree:hover span {
	color: rgb(255, 255, 255);
	text-shadow: none;
	text-shadow: -0.5px -0.5px 0 rgb(236, 236, 236),
		0.5px -0.5px 0 rgb(236, 236, 236),
		-0.5px 0.5px 0 rgb(236, 236, 236),
		0.5px 0.5px 0 rgb(236, 236, 236);
}


.kvThree:hover {
	transform: translateX(-2em);
	transition: transform 0.4s ease;
}

.kvThree:not(:hover) {
	transform: translateX(0);
	transition: 0.3s;
	/* Dodajemo kašnjenje za početak tranzicije */
}


.kvFour {
	/*background: linear-gradient(to top left, rgb(255, 102, 102), rgb(255, 115, 115));*/
	border-bottom-right-radius: 30px;
	/*background-image: url('../../../content/images/hexagon.png');*/
	background-size: cover;
	width: 100%;
	background-repeat: no-repeat;
	background-position: center;
	background-color: rgb(236, 41, 50);

}

.kvFour:hover {
	text-shadow: -0.5px -0.5px 0 rgb(64, 0, 0),
		0.5px -0.5px 0 rgb(64, 0, 0),
		-0.5px 0.5px 0 rgb(64, 0, 0),
		0.5px 0.5px 0 rgb(64, 0, 0);
}



.kvFour:hover {
	transform: translateX(2em);
	transition: transform 0.4s ease;
}

.kvFour:hover span {
	color: rgb(255, 255, 255);
	text-shadow: none;
	text-shadow: -0.5px -0.5px 0 rgb(236, 236, 236),
		0.5px -0.5px 0 rgb(236, 236, 236),
		-0.5px 0.5px 0 rgb(236, 236, 236),
		0.5px 0.5px 0 rgb(236, 236, 236);
}

.kvFour:not(:hover) {
	transform: translateX(0);
	transition: 0.3s;
	/* Dodajemo kašnjenje za početak tranzicije */
}

.pTag {
	height: 3em;
	background-color: rgb(248, 248, 248);
	/*background: linear-gradient(to bottom, rgb(218, 218, 218), rgb(255, 255, 255));*/
	margin-top: 1.4em;
	text-align: center;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: large;
	letter-spacing: 3px;
	border-bottom: 5px solid white;
	/*border-bottom: 2px solid rgb(255, 128, 128);*/
	margin-bottom: 0;
	margin-left: 4em;
	margin-right: 4em;
	align-self: center;
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
}

.pTag1 {
	height: 3em;
	background-color: rgb(248, 248, 248);
	/*background: linear-gradient(to bottom, rgb(218, 218, 218), rgb(255, 255, 255));*/
	margin-top: 3em;
	text-align: center;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: large;
	letter-spacing: 3px;
	border-bottom: 5px solid white;
	/*border-bottom: 2px solid rgb(255, 128, 128);*/
	margin-bottom: 0;
	margin-left: 4em;
	margin-right: 4em;
	align-self: center;
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
}

.divZaPonudeiPotraznje {
	background-color: rgb(246, 246, 246);
	margin-left: 4.5em;
	margin-right: 4.5em;
	display: flex;
	justify-content: center;
	flex-wrap: wrap;
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
}


.divZaPonudeiPotraznje h6 {
	text-align: center;
	margin-top: 7px;
}

.background-imazh {
	height: 8em !important;
	display: flex;
	justify-content: center;
	width: 9em;
	opacity: 0.9;
	margin-left: 0.5em;
	margin-right: 0.5em;
	max-width: 100%;
}

.divTwo div {
	margin-left: 22px;
}

.divTwo button {
	margin-top: 1.5em;
	margin-left: 4.2em;
	border-radius: 10px;
	padding: 5px;
	border: 1px solid gray;
	font-size: small;
}

.divTwo button:hover {
	padding: 6px;
	transition: 0.1s;
}

/*novo*/
.divZaPonudeiPotraznje div {
	text-align: center;
	position: relative;
	display: flex;
	flex-direction: column;
	align-items: center;
	height: 250px;
	background-color: white;
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
	margin-right: 1em;
	margin-top: 1em;
	margin-top: 1em;
	border-radius: 10px;
	position: relative;
	

   


}

.divZaPonudeiPotraznje div img {
	max-width: 100%;
	height: auto;
	display: block;
	
	border-radius: 10px;
	
}

.divZaPonudeiPotraznje div button {
	border-radius: 10px;
	padding: 5px;
	border: 1px solid gray;
	font-size: small;
	display: block;
	margin-top: 2.3em;
	margin-left: 1em;
	margin-right: 1em;
}

.divOne div {
	margin-left: 22px;
}




.divOne button:hover {
	padding: 6px;
	transition: 0.1s;
}


.divThree div {
	margin-left: 22px;
}


.divThree button:hover {
	padding: 6px;
	transition: 0.1s;
}


.divFour div {
	margin-left: 22px;
}

.divFour button:hover {
	padding: 6px;
	transition: 0.1s;
}


.divFive div {
	margin-left: 22px;
}

.divFive button:hover {
	padding: 6px;
	transition: 0.1s;
}



@media screen and (max-width: 768px) {
	.divZaPonudeiPotraznje {
		margin-left: 2em;
		margin-right: 2em;
		margin-top: 1em;
		padding-top: 1.5em;
		padding-bottom: 1.5em;
		
	}


	.pTag {
		margin-left: 1.7em;
		margin-right: 1.7em;
	}

	.pTag1 {
		margin-left: 1.7em;
		margin-right: 1.7em;
	}

	.divZaA a {
		font-size: 0.8em;
		max-width: calc(50% - 2em);
	}

	.kvOne {
		margin-left: 2em;
	}

	.kvThree {
		margin-left: 2em;
	}

	.div1 {
		margin-top: 5em;
	}
	.background-imazh {
		margin-left: 1.5em;
		margin-right: 1.5em;
	}
}

@media screen and (max-width: 310px) {
	.divZaPonudeiPotraznje div {
		width: 35%;
	}
	.divZaPonudeiPotraznje div img {
        margin-left: 2em; 
        margin-right: 2em;
    }
}

@media screen and (max-width: 450px) {
    .divZaPonudeiPotraznje div {
        width: 40%;
    }
    .divZaPonudeiPotraznje div img {
        height: 7em !important;
    }
 
}

@media screen and (min-width: 451px) and (max-width: 890px) {
	.background-imazh {
		margin-left: 1.5em;
		margin-right: 1.5em;
	}
}


</style>
