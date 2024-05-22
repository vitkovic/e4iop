import Vue from 'vue';
import { Component } from 'vue-property-decorator';

@Component
export default class QuestionsComponent extends Vue {
  public activeIndex: number | null = null;

  public questions = [
    {
      question: '1. Ko može da se registruje na portal?',
      answer:
        'Na portalu se registruju pravna lica, preduzetnici i frilenseri.<br><br>Registraciju pravnog lica može da obavi samo zakonski zastupnik koristeći svoj kvalifikovani elektronski sertifikat ili Consent ID.<br><br>Nakon potvrde identiteta, sistem će automatski preuzeti podatke o pravnom licu i preusmeriti na “Moj profil” stranicu. Potrebno je da unesete svoj PIB/MB, nakon čega će se automatski dopuniti ostali podaci. Dodatne podatke možete dodati sami uz potvrdu istinitosti i verodostojnosti podataka.',
    },
    {
      question: '2. Kako da se registrujem?',
      answer:
        'Registraciju pravnog lica može da obavi samo zakonski zastupnik koristeći svoj kvalifikovani elektronski sertifikat ili Consent ID.<br><br> Nakon potvrde identiteta, sistem će automatski preuzeti podatke o pravnom licu i preusmeriti na “Moj profil” stranicu. Potrebno je da unesete svoj PIB/MB, nakon čega će se automatski dopuniti ostali podaci. Dodatne podatke možete dodati sami uz potvrdu istinitosti i verodostojnosti podataka.',
    },
    {
      question: '3. Šta ako sam već registrovan?',
      answer:
        'Prilikom registracije se proverava da li je pravno lice već registrovano, ukoliko se utvrdi da jeste - sistem će ispisati obaveštenje da nalog za datu firmu/preduzetnika već postoji i uputiće Vas na formu za prijavu, te posle prijave preusmeriti na “Moj profil” stranicu.',
    },
    {
      question: '4.	Da li korisnik može biti i oglašivač i tražilac?',
      answer: 'Korisnik može biti istovremeno oglašivač i tražilac, ili samo jedno od ponuđenih. ',
    },
    {
      question: '5.	Da li mogu da pogledam spisak korisnika koji imaju pristup mom nalogu?',
      answer: 'Samo Administrator može da pogleda spisak korisnika koji imaju pristup Vašem nalogu.',
    },
    {
      question: '6.	Da li mogu da dodam nove korisnike naloga?',
      answer: 'Možete dodati nove korisnike ukoliko ste administrator.',
    },
    {
      question: '7.	Kako da dodam nove korisnike naloga?',
      answer:
        'Novi korisnik se dodaje unosom email-a na koji će biti poslata pozivnica koju primalac mora da prihvati klikom na aktivacioni link u email-u. Aktivacioni link traje 72h i vodi na stranicu gde novi korisnik treba da potvrdi nalog.',
    },
    {
      question: '8.	Da li mogu da izbrišem dodate korisnike mog naloga?',
      answer: 'Možete izbrisati dodatne korisnike ali samo ukoliko ste administrator.',
    },
    {
      question: '9.	Šta predstavlja saradnju?',
      answer:
        'Saradnja predstavlja obavljenu transakciju između oglašivača i tražioca, preko oglasa koji je oglašivač postavio, a na koji se tražilac javio.',
    },
    {
      question: '10.	Kako da ostvarim kontakt?',
      answer:
        'Tražilac kroz poruku na platformi može da pošalje upite oglašivaču za razjašnjenje nedoumica i otvorenih pitanja vezanih za dati oglas.<br><br>Poruka se može poslati direktno sa oglasa.',
    },
  ];

  public toggleAnswer(index: number): void {
    this.activeIndex = this.activeIndex === index ? null : index;
  }

  public isAnswerShown(index: number): boolean {
    return this.activeIndex === index;
  }
}
