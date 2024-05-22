import Vue from 'vue';
import { Component } from 'vue-property-decorator';
// import 'viewerjs/dist/viewer.css';
// import VueViewer from 'v-viewer';

@Component
export default class AboutComponent extends Vue {
  private images: string[] = [
    'https://images.unsplash.com/photo-1577962917302-cd874c4e31d2?q=80&w=1632&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    'https://images.unsplash.com/photo-1511174511562-5f7f18b874f8?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
    'https://images.unsplash.com/photo-1522542550221-31fd19575a2d?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
  ];

  private documents = [
    {
      src: 'https://www.startech.org.rs/htdocs/Files/02145/StarTech_Brosura_210x210+3mm_26042023.pdf',
      title: 'StarTech: Innovation aiming for the starts',
    },
    {
      src: 'https://www.startech.org.rs/htdocs/Files/02144/Startech-brosura-ENG-web.pdf',
      title: 'StarTech: Od inovacija do zvezda',
    },
    {
      src: 'https://betterbusinesscontent.com/_ebooks/prince_albert/pdfs/eBook-Starting-a-Business-101.pdf',
      title: 'Starting a business 101',
    },
  ];

  private viewerOptions: any = {
    movable: false,
    toolbar: {
      zoomIn: false,
      zoomOut: false,
      oneToOne: false,
      reset: false,
      prev: true,
      play: false,
      next: true,
      rotateLeft: false,
      rotateRight: false,
      flipHorizontal: false,
      flipVertical: false,
    },
    title: false,
  };

  // Method to open the viewer
  public openViewer(index: number): void {
    // const viewer = this.$el.querySelector('.images').$viewer;
    // viewer.view(index); // Open the viewer on the clicked image
  }
}
