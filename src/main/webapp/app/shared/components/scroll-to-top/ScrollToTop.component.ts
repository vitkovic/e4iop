import { Component, Vue } from 'vue-property-decorator';

@Component
export default class ScrollToTop extends Vue {
  showScrollTopButton: boolean = false;

  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  }

  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  }

  handleScroll() {
    this.showScrollTopButton = window.scrollY > 200;
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
