import { IDocument } from '@/shared/model/document.model';

export interface IDocumentType {
  id?: number;
  type?: string;
  isVisible?: boolean;
  isDeleted?: boolean;
  documents?: IDocument[];
}

export class DocumentType implements IDocumentType {
  constructor(
    public id?: number,
    public type?: string,
    public isVisible?: boolean,
    public isDeleted?: boolean,
    public documents?: IDocument[]
  ) {
    this.isVisible = this.isVisible || false;
    this.isDeleted = this.isDeleted || false;
  }
}
