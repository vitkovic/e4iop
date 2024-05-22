import Vue from 'vue';
import { Component, Inject } from 'vue-property-decorator';
import { email, helpers, maxLength, minLength, required, sameAs } from 'vuelidate/lib/validators';
import LoginService from '@/account/login.service';
import RegisterService from '@/account/register/register.service';
import {
  EMAIL_ALREADY_USED_TYPE,
  LOGIN_ALREADY_USED_TYPE,
  REGISTRATION_NUMBER_ALREADY_EXISTS_TYPE,
  PIB_ALREADY_EXISTS_TYPE,
} from '@/constants';

import PuoLegalStatusService from '@/entities/puo-legal-status/puo-legal-status.service';
import { IPuoLegalStatus } from '@/shared/model/puo-legal-status.model';

import PortalUserStatusService from '@/entities/portal-user-status/portal-user-status.service';
import { IPortalUserStatus, PortalUserStatus } from '@/shared/model/portal-user-status.model';

import PortalUserOrganizationService from '@/entities/portal-user-organization/portal-user-organization.service';
import { IPortalUserOrganization, PortalUserOrganization } from '@/shared/model/portal-user-organization.model';

import ResearchInstitutionService from '@/entities/research-institution/research-institution.service';
import { IResearchInstitution, ResearchInstitution } from '@/shared/model/research-institution.model';

import ResearcherService from '@/entities/researcher/researcher.service';
import { IResearcher, Researcher } from '@/shared/model/researcher.model';
import axios from 'axios';
import { Interface } from 'readline';

const loginPattern = helpers.regex('alpha', /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/);
const validations: any = {
  registerAccount: {
    login: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(50),
      pattern: loginPattern,
    },
    email: {
      required,
      minLength: minLength(5),
      maxLength: maxLength(254),
      email,
    },
    password: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(254),
    },
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    phone: {},
    position: {},
    portalUserStatus: {
      required,
    },
    organizationNameSr: {},
    organizationNameEn: {},
    organizationShortName: {},
    organizationWebAddress: {},
    organizationLegalStatus: {},
    organizationPib: {
      minLength: minLength(9),
      maxLength: maxLength(9),
    },
    organizationMaticniBroj: {
      minLength: minLength(8),
      maxLength: maxLength(8),
    },
    organizationKeywords: {},
    organizationAddress: {},
    organizationAddressPostalCode: {},
    organizationAddressCity: {},
    organizationAddressCountry: {},
  },
  confirmPassword: {
    required,
    minLength: minLength(4),
    maxLength: maxLength(50),
    // prettier-ignore
    sameAsPassword: sameAs(function() {
      return this.registerAccount.password;
    })
  },
};

interface ResearcherSearchFormat {
  // Ovo mozda nije bas najbolje resenje.
  // Posto se sa modela istrazivaca u bazi prelazi na model pogodan za pretrazivanje,
  // da ovo nije uradjeno izgubila bi se veza izmedju dva modela.
  originalResearcher: IResearcher;

  fullName: string;
  fullNameReversed: string;
  firstName: string;
  middleName: string;
  lastName: string;
  birthDate: Date;
}

@Component({
  validations,
})
export default class Register extends Vue {
  //props: ['formn','text_field'];

  props: {
    formn: any;
    text_field: any;
  };

  @Inject('registerService') private registerService: () => RegisterService;
  @Inject('loginService') private loginService: () => LoginService;

  @Inject('puoLegalStatusService') private puoLegalStatusService: () => PuoLegalStatusService;
  @Inject('portalUserStatusService') private portalUserStatusService: () => PortalUserStatusService;

  public puoLegalStatuses: IPuoLegalStatus[] = [];
  public portalUserStatuses: IPortalUserStatus[] = [];
  public selectedPortalUserStatus: IPortalUserStatus = new PortalUserStatus();

  @Inject('portalUserOrganizationService') private portalUserOrganizationService: () => PortalUserOrganizationService;

  public portalUserOrganizations: IPortalUserOrganization[] = [];

  @Inject('researcherService') private researcherService: () => ResearcherService;
  public researchers: IResearcher[] = [];
  public researchersWithFullNames: ResearcherSearchFormat[] = [];

  @Inject('researchInstitutionService') private researchInstitutionService: () => ResearchInstitutionService;
  public researchInstitutions: IResearchInstitution[] = [];

  public registerAccount: any = {
    login: undefined,
    email: undefined,
    password: undefined,
    phone: undefined,
    position: undefined,
    portalUserStatus: undefined,
    firstName: undefined,
    lastName: undefined,
    organizationNameSr: undefined,
    organizationNameEn: undefined,
    organizationShortName: undefined,
    organizationWebAddress: undefined,
    organizationLegalStatus: undefined,
    organizationPib: undefined,
    organizationMaticniBroj: undefined,
    organizationKeywords: undefined,
    organizationAddress: undefined,
    organizationAddressPostalCode: undefined,
    organizationAddressCity: undefined,
    organizationAddressCountry: undefined,
    organizationNioInstitution: undefined,
    organizationRI: undefined,
    organizationNioInstitutionId: undefined,
    portalUserOrganization: undefined,
    researchInstitution: undefined,
    setRI: undefined,
    researcher: undefined,
    statuses: undefined,
  };
  public confirmPassword: any = null;
  public error = '';
  public errorEmailExists = '';
  public errorUserExists = '';
  private search_data = [];
  private search_data_researchers: ResearcherSearchFormat[] = [];

  public success = false;
  public statuses = 0;
  public portalUserOrganization: IPortalUserOrganization = new PortalUserOrganization();

  public instid = 0;
  public firstOptionResearcher = null;
  public firstOptionReseacrherText = '';
  public text_field = null; // for Companies search by No or PIB - refers to refs on form
  public tempPUO = null;

  public researchInstitution: IResearchInstitution = new ResearchInstitution();

  public researcher: IResearcher = new Researcher();

  public prikaziNovuOrganizaciju = false; // definise prikaz polja za unos nove organizacije
  public prikazOrganizacije = false; // definise prikaz izbora organizacije
  public prikazInstitucije = false; // definise prikaz institucije

  public currentLanguage = '';
  private keystrokesNUM = 0;

  public getData(): void {
    this.text_field = this.$refs.namePUO as HTMLInputElement;
    let serchValue = this.text_field.value;
    let checkvalue = '';
    this.resetOrganization();
    if (!this.checkValues(serchValue)) return; // validate pib or registration number
    if (serchValue.length == 9) {
      this.registerAccount.organizationPib = serchValue;
      checkvalue = this.registerAccount.organizationPib;
      //this.registerAccount.organizationMaticniBroj = '';
    } else if (serchValue.length == 8) {
      this.registerAccount.organizationMaticniBroj = serchValue;
      checkvalue = this.registerAccount.organizationMaticniBroj;
      //this.registerAccount.organizationPib = '';
    }
    if (this.portalUserOrganizations != null && this.portalUserOrganizations.length > 0) {
      // check if PUOS are loaded
      this.portalUserOrganizations.forEach(
        // if they are find organisation
        organisation => {
          if (organisation.registrationNumber == checkvalue || organisation.pib == checkvalue) {
            this.portalUserOrganization = organisation;
            console.log(organisation);
            this.setOrganization();
          }
        }
      );
      if (this.portalUserOrganization != null) {
        // if organisation is found set user PUO
        this.$notify({
          text: JSON.stringify(this.$t('register.organizationfounddb', { param: serchValue })).replace(/["]/g, ''),
          type: 'success',
          duration: 2000,
        });
        return;
      }
    }
    // if organisation is not found in db look on web db - APR data
    var urlstr = '';
    if (serchValue.length == 8 || serchValue.length == 9) {
      urlstr = '/api/portal-user-organizations-from-api/' + checkvalue;
      this.$notify({ text: JSON.stringify(this.$t('register.loadingdata')) }); // as ajax loader / simulatiion
      axios
        .get(urlstr)
        .then(response => {
          console.log(response.data);
          this.tempPUO = response.data;
          this.keystrokesNUM = 0;
          this.prikaziNovuOrganizaciju = true; // this is now a new organization for the current / potential user
          this.selectedPortalUserStatus.valueEn = 'Employee'; // user is an Employee. Maybe not required but we will see.
          if (this.tempPUO != null && this.tempPUO.legalNameSr != null) {
            this.$notify({ clean: true });
            this.$notify({
              text: JSON.stringify(this.$t('register.organizationfound', { param: checkvalue })).replace(/["]/g, ''), // stop loader and show user that organization was found
              type: 'success',
              duration: 2000,
            });
            //this.portalUserOrganization = this.tempPUO; // this is only set for existing organizations not new.
            this.registerAccount.organizationNameSr = this.tempPUO.legalNameSr;
            this.registerAccount.organizationNameEn = this.tempPUO.legalNameEn;
            this.registerAccount.organizationPib = this.tempPUO.pib;
            this.registerAccount.organizationMaticniBroj = this.tempPUO.registrationNumber;

            return;
          } else {
            this.$notify({ clean: true });
            this.$notify({
              text: JSON.stringify(this.$t('register.organizationNOTfound', { param: checkvalue })).replace(/["]/g, ''),
              type: 'error',
              duration: 2000,
            });
          }
        })
        .catch(error => {
          this.tempPUO = null;
          this.keystrokesNUM = 0;
        });
    }
  }
  public checkValues(txt): boolean {
    if (txt) {
      if (txt.length != 8 && txt.length != 9) {
        const por = this.$t('register.form.maticniBrojLength') + '<br>' + this.$t('register.form.pibLength');
        this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
        return false;
      } else return true;
    }
  }
  public setOrganization(index = null): void {
    this.text_field = this.$refs.namePUO as HTMLInputElement; // used for search of companies - not existing for nio
    if (this.portalUserOrganization == null) return;
    if (this.text_field != null) this.text_field.value = this.portalUserOrganization.registrationNumber;
    this.registerAccount.organizationMaticniBroj = this.portalUserOrganization.registrationNumber;
    if (this.portalUserOrganization.registrationNumber != null && this.portalUserOrganization.registrationNumber != '') {
      this.registerAccount.organizationNameSr = this.portalUserOrganization.legalNameSr;
      this.registerAccount.organizationNameEn = this.portalUserOrganization.legalNameEn;
      this.registerAccount.organizationPib = this.portalUserOrganization.pib;
      this.registerAccount.organizationMaticniBroj = this.portalUserOrganization.registrationNumber;
      this.registerAccount.organizationAddress = this.portalUserOrganization.address;
      this.registerAccount.organizationWebAddress = this.portalUserOrganization.webAddress;
      this.registerAccount.organizationAddressCity = this.portalUserOrganization.city;
      this.registerAccount.organizationAddressCountry = this.portalUserOrganization.country;
      this.registerAccount.organizationShortName = this.portalUserOrganization.shortName;
      this.registerAccount.organizationNioInstitution = this.portalUserOrganization.nioInstitution;
      this.registerAccount.organizationRI = this.portalUserOrganization.nioInstitution;
      //console.log(this.registerAccount.organizationNioInstitution)
      //  this.registerAccount.organizationNioInstitutionId = this.portalUserOrganization.NioInstitutionId
      if (index != null) {
        this.prikaziNovuOrganizaciju = true;
        this.prikazOrganizacije = false;
      } else {
        this.prikaziNovuOrganizaciju = false;
        this.prikazOrganizacije = true;
      }
    }
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    // console.log(this.currentLanguage);
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
        //this.manipulateLatCyr();
      }
    );
  }

  public checkForm(): boolean {
    if (
      this.selectedPortalUserStatus.valueEn == 'Employee' ||
      this.selectedPortalUserStatus.valueEn == 'Research institution employee' ||
      this.selectedPortalUserStatus.valueEn == 'Physical person'
    ) {
      if (this.prikazOrganizacije == true) {
        if (this.prikaziNovuOrganizaciju == false) {
          if (this.portalUserOrganization && this.portalUserOrganization.id) {
            console.log('postoji');
            this.registerAccount.portalUserOrganization = this.portalUserOrganization;
            return true;
          } else {
            // ako je prikazano polje izaberi postojecu organizaciju, a ona nije izabrana
            const por = this.$t('register.form.podaciNisuUneti');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
            return false;
          }
        } else {
          // ako je prikazan kontejner unesi novu organizaciju
          if (this.registerAccount.organizationMaticniBroj) {
            if (this.registerAccount.organizationMaticniBroj.length != 8) {
              const por = this.$t('register.form.maticniBrojLength');
              this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
              return false;
            }
          } else {
            const por = this.$t('register.form.maticniBrojObavezanUnos');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
            return false;
          }
          if (this.registerAccount.organizationPib) {
            if (this.registerAccount.organizationPib.length != 9) {
              const por = this.$t('register.form.pibLength');
              this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
              return false;
            }
          } else {
            const por = this.$t('register.form.pibObavezanUnos');
            this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
            return false;
          }
        }
      }
    }

    return true;
  }
  public register(): void {
    if (this.checkForm() == false) {
      return;
    }
    this.error = null;
    this.errorUserExists = null;
    this.errorEmailExists = null;
    this.registerAccount.langKey = this.$store.getters.currentLanguage;
    console.log(this.registerAccount);
    this.registerService()
      .processRegistration(this.registerAccount)
      .then(() => {
        this.success = true;
      })
      .catch(error => {
        this.success = null;
        if (error.response.status === 400 && error.response.data.type === LOGIN_ALREADY_USED_TYPE) {
          this.errorUserExists = 'ERROR';
        } else if (error.response.status === 400 && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
          this.errorEmailExists = 'ERROR';
        } else if (error.response.status === 400 && error.response.data.type === REGISTRATION_NUMBER_ALREADY_EXISTS_TYPE) {
          const por = this.$t('riportalApp.portalUserOrganization.errors.registrationNumberAlreadyExists');
          this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
        } else if (error.response.status === 400 && error.response.data.type === PIB_ALREADY_EXISTS_TYPE) {
          const por = this.$t('riportalApp.portalUserOrganization.errors.pibAlreadyExists');
          this.$notify({ text: JSON.stringify(por).replace(/["]/g, ''), type: 'error', duration: 10000 });
        } else {
          this.error = 'ERROR';
        }
      });
  }
  public goUP(): void {
    console.log(document.getElementById('topjumper'));
    if (document.getElementById('topjumper')) document.getElementById('topjumper').scrollIntoView({ behavior: 'smooth' });
  }
  public portalUserStatusCheck(event): void {
    this.selectedPortalUserStatus = event;
    this.$notify({ clean: true });
    //console.log(this.selectedPortalUserStatus.id + this.selectedPortalUserStatus.valueEn)
    if (this.selectedPortalUserStatus.valueEn == 'Employee (not within research institution)') {
      this.search_data = this.portalUserOrganizations;

      this.resetOrganization(true);
      this.researchInstitution = null;
      this.portalUserOrganization = new Object();
      this.prikazOrganizacije = true;
      this.prikazInstitucije = false;
      this.prikaziNovuOrganizaciju = false;
      this.patternRI = false;

      // Iz nekog razloga polja za unos Maticnog broja i istrazivaca su povezana.
      // Prilikom promene "Statusa korisnika portala" izmedju "Zaposleni" i "Zaposleni u NIO" vrednost jednog polja se prenosi na drugo.
      // Dok se ne utvrdi zasto, ovde se resetuje polje za unos istrazivaca.
      if (this.$refs.researchersField) {
        this.txtfield = this.$refs.researchersField as HTMLInputElement;
        this.txtfield.value = '';
      }

      document.getElementById('jumper').scrollIntoView({ behavior: 'smooth' });
    }

    if (this.selectedPortalUserStatus.valueEn == 'Research institution employee') {
      this.search_data = this.researchInstitutions; // filling div with all RI
      this.keystrokesNUM = 0; // reset keypresses
      this.patternRI = false; // don't display div until clicked
      this.resetOrganization();
      this.portalUserOrganization = null;
      this.researcher = null;
      this.prikazInstitucije = true;
      this.prikazOrganizacije = false;
      this.prikaziNovuOrganizaciju = false;
      this.researchInstitution = new Object();

      // Iz nekog razloga polja za unos Maticnog broja i istrazivaca su povezana.
      // Prilikom promene "Statusa korisnika portala" izmedju "Zaposleni" i "Zaposleni u NIO" vrednost jednog polja se prenosi na drugo.
      // Dok se ne utvrdi zasto, ovde se resetuje polje za unos Maticnog broja.
      if (this.$refs.namePUO) {
        this.text_field = this.$refs.namePUO as HTMLInputElement;
        this.text_field.value = '';
      }

      document.getElementById('jumper').scrollIntoView({ behavior: 'smooth' });
    }

    if (this.selectedPortalUserStatus.valueEn == 'Physical person') {
      this.resetOrganization();
      this.researchInstitution = null;
      this.portalUserOrganization = null;
      this.prikazInstitucije = false;
      this.prikazOrganizacije = false;
      this.prikaziNovuOrganizaciju = false;
    }
    /*
  
    if(this.selectedPortalUserStatus.valueEn == 'Employee'){
      this.prikaziNovuOrganizaciju = true;
    }else{
      this.prikazOrganizacije = true;
      this.portalUserOrganization = null;
    }
   */
  }

  public fillForm(data): void {
    if (this.researcher != null) {
      this.registerAccount.firstName = data.firstName;
      this.registerAccount.lastName = data.lastName;
      this.registerAccount.researcher = data.originalResearcher;
      this.registerAccount.portalUserOrganization = this.portalUserOrganization;
    }
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public prikaziOrganizaciju(value: boolean): void {
    this.prikaziNovuOrganizaciju = value;
    this.resetOrganization(true);
    this.portalUserOrganization = null;
    /*
    if (value == true) {
      this.portalUserOrganization = null;
    } else {
      this.resetOrganization();
    }
    */
  }

  public resetOrganization(what = false): void {
    // reseting the organization; true just eliminates the search text field value
    this.registerAccount.organizationNameSr = null;
    this.registerAccount.organizationNameEn = null;
    this.registerAccount.organizationShortName = null;
    this.registerAccount.organizationWebAddress = null;
    this.registerAccount.organizationLegalStatus = null;
    this.registerAccount.organizationPib = null;
    this.registerAccount.organizationMaticniBroj = null;
    this.registerAccount.organizationKeywords = null;
    this.registerAccount.organizationAddress = null;
    this.registerAccount.organizationAddressPostalCode = null;
    this.registerAccount.organizationAddressCity = null;
    this.registerAccount.organizationAddressCountry = null;
    this.registerAccount.organizationNioInstitution = null;
    //this.registerAccount.organizationNioInstitutionId = null;
    this.portalUserOrganization = null;
    if (what) {
      this.text_field = this.$refs.namePUO as HTMLInputElement;
      if (this.text_field != null) this.text_field.value = '';
    }
  }

  public initRelationships(): void {
    this.puoLegalStatusService()
      .retrieve()
      .then(res => {
        this.puoLegalStatuses = res.data;
      });

    this.portalUserStatusService()
      .retrieve()
      .then(res => {
        this.portalUserStatuses = res.data;
        this.registerAccount.portalUserStatus = 2;
      });

    this.portalUserOrganizationService()
      .retrieveWithoutPagination()
      .then(res => {
        //onsole.log(res.data);
        this.portalUserOrganizations = res.data;
      });

    this.researchInstitutionService()
      .retrieveOrdered(this.currentLanguage)
      .then(res => {
        this.researchInstitutions = res.data;
        this.tempRIS = res.data;
        this.search_data = res.data;
      })
      .catch(e => {
        console.log(e);
      });
  }
  private tempRI = [];
  private tempRIS = [];
  public findReseacrhersForResearchInstitution() {
    if (this.researchInstitution != null) {
      this.instid = this.researchInstitution.id;
      this.researcherService()
        .retrieveOrdered(this.currentLanguage, this.instid)
        .then(res => {
          this.researchers = res.data;
          this.createResearchersWithFullNames();
          if (this.researchers != null && this.researchers.length > 0) {
            this.researcher = this.researchers[0];
            this.retrivePortalUserOrganisationForInstitution(this.instid);
            this.$notify({
              text: JSON.stringify(this.$t('register.researchersfounddb', { param: this.researchers.length })).replace(/["]/g, ''),
              type: 'success',
              duration: 3000,
            });
          } else
            this.$notify({
              text: JSON.stringify(this.$t('register.researchersNOTfounddb', { param: 0 })).replace(/["]/g, ''),
              type: 'success',
              duration: 3000,
            });
        })
        .catch(e => {
          console.log(e);
        });
    }
  }

  public retrivePortalUserOrganisationForInstitution(id) {
    //console.log(id)
    this.portalUserOrganizationService()
      .retrieveByInstitution(id)
      .then(res => {
        this.portalUserOrganization = res.data;
        if (this.portalUserOrganization == null) {
          this.setOrganisationInstitution();
          //console.log(this.portalUserOrganization);
        }
        this.setOrganization(1);
      })
      .catch(e => {
        if (this.portalUserOrganization == null) {
          // kada nema institucije on vraca 404 i null,  ovo ne bi trebalo da se desi i pitanje da li postaviti ovako, ali videcemo.
          this.setOrganisationInstitution();
        }
        console.log(e);
        this.$notify({
          text: JSON.stringify(this.$t('register.companyForInstitutionNOTfounddb', { param: 0 })).replace(/["]/g, ''),
          type: 'success',
          duration: 3000,
        });
        this.setOrganisationInstitution();
      });
  }
  public setOrganisationInstitution(): void {
    this.portalUserOrganization = new Object();
    this.portalUserOrganization.registrationNumber = this.researchInstitution.registerNumber;
    this.portalUserOrganization.legalNameEn = this.researchInstitution.nameEn;
    this.portalUserOrganization.legalNameSr = this.researchInstitution.name;
    this.portalUserOrganization.legalNameSrCyr = this.researchInstitution.nameCyr;
    this.portalUserOrganization.pib = this.researchInstitution.vatNumber;
    this.portalUserOrganization.address = this.researchInstitution.address;
    this.portalUserOrganization.webAddress = this.researchInstitution.webAdress;
    this.portalUserOrganization.nioInstitution = this.researchInstitution;
    // this.portalUserOrganization.NioInstitutionId = this.researchInstitution.id;
    this.setOrganization(1);
    this.portalUserOrganization = null; // Hocemo da dodamo instituciju kao PUO u bazu prilikom registracije korisnika zato setujemo objekat na null. Ako je setovan to znaci da postoji PUO za instituciju.
  }
  public txtfield = null;
  // for autocomplete of RI
  public getDataAuto(): void {
    this.txtfield = this.$refs.researchInstitutionField as HTMLInputElement;
    if (
      (this.researchInstitution.name != null && this.researchInstitution.name.length == 0) ||
      (this.txtfield.value != null && this.txtfield.value.trim() == '')
    ) {
      this.researchInstitutions = this.tempRIS;
      this.search_data = this.researchInstitutions;
      this.keystrokesNUMSr = 0;
      this.patternRI = true;
      return;
    }
    if (this.keystrokesNUMSr > 2) {
      this.researchInstitutionService()
        .retrieveAutocomplete(this.txtfield.value)
        .then(res => {
          //console.log(res.data)
          this.researchInstitutions = res.data;
          this.search_data = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    } else {
      this.keystrokesNUMSr++; // 4 or more charachters
    }
  }

  // private search_data = [];
  private patternRI = false;
  private keystrokesNUMSr = 0;

  getRISr(data, txt): void {
    this.txtfield = this.$refs.researchInstitutionField as HTMLInputElement;
    this.researchInstitution = data;
    this.txtfield.value = data.name;
    this.findReseacrhersForResearchInstitution();
    this.search_data = [];
    this.keystrokesNUMSr = 0;
  }

  public showDiv(): void {
    if (this.patternRI) this.patternRI = false;
    else this.patternRI = true;
  }

  public hideDivOutOfFocus() {
    this.patternRI = false;
  }

  public getDataAutoForPUO(): void {
    this.patternRI = true;
    this.txtfield = this.$refs.portalUserOrganisationField as HTMLInputElement;

    if (this.txtfield.value != '') {
      this.portalUserOrganizationService()
        .retrieveAutocomplete(this.txtfield.value, this.currentLanguage)
        .then(res => {
          this.portalUserOrganizations = res.data;
          this.search_data = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    } else {
      this.portalUserOrganizationService()
        .retrieveWithoutPagination()
        .then(res => {
          this.portalUserOrganizations = res.data;
          this.search_data = res.data;
        });
    }
  }

  getPUOs(data): void {
    this.txtfield = this.$refs.portalUserOrganisationField as HTMLInputElement;

    if (this.currentLanguage == 'sr') {
      this.txtfield.value = data.legalNameSr;
    } else if (this.currentLanguage == 'src') {
      this.txtfield.value = data.legalNameSrCyr;
    } else if (this.currentLanguage == 'en') {
      this.txtfield.value = data.legalNameEn;
    }

    this.portalUserOrganization = data;
    // this.search_data = [];
    this.setOrganization();
  }

  private patternResearchers = false;

  public getDataAutoForResearchers(): void {
    this.patternResearchers = true;
    this.txtfield = this.$refs.researchersField as HTMLInputElement;

    if (this.txtfield.value != '') {
      // This search is brute force, could be smarter.
      this.search_data_researchers = [];
      for (var researcher of this.researchersWithFullNames) {
        if (
          researcher.fullName.includes(this.txtfield.value.toUpperCase()) ||
          researcher.fullNameReversed.includes(this.txtfield.value.toUpperCase())
        ) {
          this.search_data_researchers.push(researcher);
        }
      }
    } else {
      this.search_data_researchers = this.researchersWithFullNames;
    }
  }

  getResearchers(data): void {
    this.txtfield = this.$refs.researchersField as HTMLInputElement;
    this.txtfield.value = data.firstName + ' ';
    if (data.middleName) {
      this.txtfield.value += data.middleName + ' ';
    }
    this.txtfield.value += data.lastName;
    this.txtfield.value += ' - ' + data.birthDate;

    this.fillForm(data);
  }

  public toggleSearchList(event, toggleControl: string) {
    if (event.type === 'focusout') {
      this[toggleControl] = false;
    } else {
      this[toggleControl] = !this[toggleControl];
    }
  }

  public createResearchersWithFullNames() {
    var fullName: string;
    var fullNameReversed: string;

    this.researchersWithFullNames = [];
    for (var researcher of this.researchers) {
      fullName = researcher.firstName + ' ';
      fullNameReversed = researcher.lastName + ' ';
      if (researcher.middleName) {
        fullName += researcher.middleName + ' ';
        fullNameReversed += researcher.middleName + ' ';
      }
      fullName += researcher.lastName;
      fullNameReversed += researcher.firstName;

      fullName = fullName.toUpperCase();
      fullNameReversed = fullNameReversed.toUpperCase();

      this.researchersWithFullNames.push({
        originalResearcher: researcher,
        fullName: fullName,
        fullNameReversed: fullNameReversed,
        firstName: researcher.firstName,
        middleName: researcher.middleName,
        lastName: researcher.lastName,
        birthDate: researcher.birthDate,
      });
    }

    this.search_data_researchers = this.researchersWithFullNames;
  }
}
