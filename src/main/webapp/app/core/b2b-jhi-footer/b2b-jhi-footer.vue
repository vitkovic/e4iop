<template>
	<div id="footer" class="footer">
		<div class="logoi">
			<img src="/content/images/vlada_republike_srbije.jpg" class="logo1" alt="">
			<img src="/content/images/NITRA logo.png" class="logo2" alt="">
			<img src="/content/images/Philip.png" class="logo3" alt="">
			<img src="/content/images/StarTech logo.jpg" class="logo4" alt="">
			<img src="/content/images/naledLogo.jpg" class="logo5" alt="">
		
		</div>
		<div class="footFootera">
			<div class="rightContent">
				<a href="../contact">Контактирајте нас</a>
				<a>Најчешћа питања</a>
				<a href="#">Услови коришћења</a>
				<a href="#">Политика колачића</a>
				<a href="#">Политика приватности</a>
			</div>
		</div>
	</div>
</template>

<script lang="ts" src="./b2b-jhi-footer.component.ts">
</script>

<style scoped>
.footer {
	width: 92%;
	margin-left: auto;
	margin-right: auto;
	background-color: rgb(255, 208, 208);
	
	height: 16em;
	background-color: white;
	border-bottom: 2em solid  rgb(11, 62, 132);
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
}
.logoi img {
	margin-right: 5px;
	text-align: center;
	box-sizing: border-box;
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
	 border-top: 8px solid rgba(102,124,150,255);
	 align-self: center;
	 height: 6em;
	 background: #1f3448 !important;
}

.rightContent {
	text-align: center;
	display: flex;
	align-items: bottom;
	justify-content: center;
	margin-top: 3em;
}


.rightContent a {
	margin-bottom: 0;
	font-size: small;
	color: rgb(149, 180, 242);
	padding: 5px;
	margin-right: 1em;
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
	}
	.logo1 {
		width: 4.5em;
		height: 100%;
	}
	.logo2 {
		width: 5em;
		height: 60%;
	}
	.logo3 {
		width: 4.5em;
		height: 80%;
		margin-top: 1em;
	}
	.logo4{
		width: 4.5em;
		height: 100%;
	}
	.logo5 {
		width: 4.5em;
		height: 30%;
		margin-top: 3em;
	}
	.rightContent a {
		font-size: 0.6em;
	}
}
</style>