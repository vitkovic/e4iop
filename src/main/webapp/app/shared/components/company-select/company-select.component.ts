import { Component, Vue, Prop, Inject } from 'vue-property-decorator';
import { ICompany } from '@/shared/model/company.model';

import CompanyService from '@/entities/company/company.service';

@Component
export default class CompanySelect extends Vue {
  @Inject('companyService')
  private companyService: () => CompanyService;

  @Prop({ default: 'Potražite kompaniju' }) labelText!: string;
  @Prop({ default: () => [] }) includedCompanies!: ICompany[];
  @Prop({ default: () => [] }) excludedCompanies!: ICompany[];

  public companySearchText = '';
  public companiesSearch: ICompany[] = [];
  public selectedCompanies: ICompany[] = [];
  public showCompaniesSearch = false;

  created(): void {
    this.$store.watch(
      () => this.includedCompanies,
      () => {
        if (this.includedCompanies) {
          this.selectedCompanies = this.includedCompanies;
        }
      }
    );
  }

  public getCompaniesBySearchText(): void {
    // This is neccessary as Java side, for some reason,
    // cannot handle empty string in path variable.
    // Java side will check if string only consists of blanks,
    // and choose appropriate repository method to call.
    let searchText = this.companySearchText;
    if (this.companySearchText === '') {
      searchText = ' ';
    }

    let excludedCompanyIds = this.excludedCompanies.map(company => company.id);
    let selectedCompanyIds = this.selectedCompanies.map(company => company.id);
    let excludedIds = excludedCompanyIds.concat(selectedCompanyIds);

    // This is neccessary as Java side, for some reason,
    // cannot handle empty array in path variable.
    // No Company ID can be negative, so this ensures that
    // No Company is ommited from search in this case.
    if (!excludedIds || excludedIds.length === 0) {
      excludedIds = [-1];
    }

    this.companyService()
      .getAllAutocompleteByNameWithoutExlcuded(searchText, excludedIds)
      .then(res => {
        this.companiesSearch = res.data;
      });
  }

  public addCompany(company: ICompany) {
    this.selectedCompanies.push(company);
    this.companySearchText = '';
  }

  public removeCompany(company: ICompany) {
    let index = this.selectedCompanies.indexOf(company);
    if (index > -1) {
      this.selectedCompanies.splice(index, 1);
    }
  }

  public retriveCompanyLogo(company: ICompany): string {
    return this.companyService().retrieveImage(company.logo.filename);
  }

  public getCompanyInitials(company: ICompany): string {
    return company.name
      .split(' ')
      .map(word => word[0])
      .join('')
      .toUpperCase();
  }

  public toggleSearchList(event, toggleControl: string) {
    if (event.type === 'focusout') {
      this[toggleControl] = false;
    } else if (event.type === 'focusin') {
      this[toggleControl] = true;
      this.getCompaniesBySearchText();
    } else {
      this[toggleControl] = !this[toggleControl];
    }
  }
}
