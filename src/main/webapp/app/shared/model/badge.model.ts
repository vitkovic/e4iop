import { IDocument } from '@/shared/model/document.model';
import { ICompany } from '@/shared/model/company.model';

export interface IBadge {
  id?: number;
  title?: string;
  description?: string;
  image?: IDocument;
  companies?: ICompany[];
}

export class Badge implements IBadge {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public image?: IDocument,
    public companies?: ICompany[]
  ) {}
}
