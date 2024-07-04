<template>
  <b-button
    v-show="showScrollTopButton"
    @click="scrollToTop"
    variant="primary"
    class="scroll-to-top"
  >
    Top
  </b-button>
</template>

<script lang="ts" src="./ScrollToTop.component.ts"></script>

<style scoped>
.scroll-to-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
}
</style>