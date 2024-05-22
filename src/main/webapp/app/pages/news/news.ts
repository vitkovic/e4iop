import Vue from 'vue';
import { Component } from 'vue-property-decorator';

@Component
export default class NewsComponent extends Vue {
  private news = [
    {
      title: 'Uspešno ostvarena saradnja sa predstavnicima Japana i Koreje',
      img:
        'https://plus.unsplash.com/premium_photo-1683121109318-0fe08a05907e?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
      date: '12.04.2024',
    },
    {
      title: 'Otvorena mogućnost frilenserima da ponude svoje usluge na B2B portalu',
      img:
        'https://images.unsplash.com/photo-1664575262619-b28fef7a40a4?q=80&w=1664&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
      date: '08.03.2024',
    },
    {
      title: 'B2B sajam',
      img:
        'https://images.unsplash.com/photo-1573757056004-065ad36e2cf4?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
      date: '01.02.2024',
    },
    {
      title: 'Upotreba AI kod B2B biznisa',
      img:
        'https://images.unsplash.com/photo-1677442136019-21780ecad995?q=80&w=1632&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
      date: '22.12.2023',
    },
  ];
}
