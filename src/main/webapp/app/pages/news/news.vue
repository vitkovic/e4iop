<template>
  <section class="bg-white">
    <div v-if="cmsNews" class="container">
      <div class="row">
        <div class="col-xs-12 mb-5">
          <h1 v-text="cmsNews.title"></h1>
        </div>
        <div class="col-md-12">
          <div v-html="cmsNews.content" class="contentText p-2"></div>

        </div>
      </div>
       
      <section v-if="cmsNewsImages.length > 0" class="section-gallery mb-4">
        <div class="prev-box mr-2">
          <b-button variant="none" class="prevButton" @click="scrollPrev">
            <font-awesome-icon icon="caret-left" class="fa-lg"></font-awesome-icon>
          </b-button>
        </div>
        <div class="wrapper">
          <div class="carousel" ref="carousel" v-if="companyImagesArray">
            <img v-for="(image, index) in cmsNewsImages" :key="image.id" @click="onPreviewImage(index)" :src="retrieveFile(image)" alt="img" @load="onImageLoad" />
          </div>
          <div
            v-if="showMask"
            ref="lightbox"
            class="lightbox"
            tabindex="0"
            @keydown.left="prev"
            @keydown.right="next"
            @keydown.esc="onClosePreviewImage"
            @click="onClosePreviewImage"
          >
            <div class="light-box-contain d-flex align-items-center justify-content-center">
              <button v-if="previewImage" class="close-btn" @click="onClosePreviewImage()">
                <font-awesome-icon icon="times" class="fa-lg"></font-awesome-icon>
              </button>
              <button class="btn-lightbox-carousel btn-prev" @click.stop="prev()">
                <font-awesome-icon icon="caret-left" class="fa-3x icon-lightbox-carousel icon-prev"></font-awesome-icon>
              </button>
              <button class="btn-lightbox-carousel btn-next" @click.stop="next()">
                <font-awesome-icon icon="caret-right" class="fa-3x icon-lightbox-carousel icon-next"></font-awesome-icon>
              </button>
              <div v-if="previewImage" class="lightbox-img d-flex align-items-center justify-content-center" @click.stop>
                <img :src="retrieveFile(cmsNewsImages[currentIndex])" alt="Image description" class="img-fluid" />
              </div>
            </div>
          </div>
        </div>
        <div class="next-box ml-2">
          <b-button variant="none" class="nextButton" @click="scrollNext">
            <font-awesome-icon icon="caret-right" class="fa-lg"></font-awesome-icon>
          </b-button>
        </div>
      </section>

      <section v-if="cmsNewsDocuments.length > 0">
        <div class="row">
          <div class="col-xs-12">
            <h2 v-text="$t('about.documents')">KORISNI DOKUMENTI</h2>
          </div>
        </div>
        <div class="col-md-12">
          <ol class="p-0 p-md-3">
            <li v-for="document in cmsNewsDocuments" :key="document.id">
              <a
                class="text-info"
                :href="retrieveFile(document)"
                target="_blank"
                title="Preuzmite dokument"
                >{{ document.filename }}
              </a>

            </li>
          </ol>
        </div>
      </section>

    </div>
  </section>
</template>

<script lang="ts" src="./news.ts"></script>
<style scoped>

::v-deep .contentText p a {
  color: #3498db;
}

.img-thumbnail {
  width: 100%; /* Ensure images fill their container */
  height: 200px; /* Maintain aspect ratio */
  cursor: pointer; /* Change cursor to pointer on hover */
  padding: 0;
  transition: all 0.4s;
  box-shadow: 0 0.6rem 1.2rem rgba(0, 0, 0, 0.075);
  object-fit: cover; /* Ensure images fill the entire card */
}

.img-thumbnail:hover {
  transform: translateY(-0.3rem);
  box-shadow: 0 0.8rem 1.6rem rgba(0, 0, 0, 0.06);
}

h1,
h2 {
  color: #004b90;
}

/* LIGHTBOX & GALLERY  */

.lightbox {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.85);
  z-index: 1000;
  padding-right: 50px;
  padding-left: 50px;
  padding-top: 50px;
  padding-bottom: 50px;
}

.light-box-contain {
  width: 100%;
  height: 100%;
}

.lightbox-img {
  width: 100%;
  height: 100%;
}

.lightbox-img img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.close-btn {
  position: absolute;
  top: 20px;
  right: 30px;
  padding: 0;
  color: #fff;
  background: 0 0;
  border: 0;
  cursor: pointer;
}

.close-btn:focus {
  outline: none;
}

.btn-lightbox-carousel {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 0;
  color: #fff;
  background: 0 0;
  border: 0;
  cursor: pointer;
  z-index: 1001;
}

.btn-lightbox-carousel:focus {
  outline: none;
}

.btn-prev {
  left: 20px;
}

.btn-next {
  right: 20px;
}

/* LIGHTBOX & GALLERY END  */

.section-gallery {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.wrapper {
  max-width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wrapper .carousel {
  font-size: 0px;
  width: 100%;
  overflow: hidden;
  cursor: pointer;
  white-space: nowrap;
}

.carousel img {
  height: 240px;
  padding: 16px;
  object-fit: cover;
}

.carousel img:first-child {
  margin-left: 0px;
}

.prev-box {
  width: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.next-box {
  width: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.conditionsText p {
  margin-bottom: 8px;
}
</style>
