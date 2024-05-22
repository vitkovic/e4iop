import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

export interface IRiSubcategory {
  id?: number;
  subcategory?: string;
  subcategoryEn?: string;
  subCategoryCyr?: string;
  infrastructures?: IResearchInfrastructure[];
}

export class RiSubcategory implements IRiSubcategory {
  constructor(
    public id?: number,
    public subcategory?: string,
    public subcategoryEn?: string,
    public subCategoryCyr?: string,
    public infrastructures?: IResearchInfrastructure[]
  ) {}
}
