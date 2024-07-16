import { IDocument } from '@/shared/model/document.model';

export interface ICmsNews {
  id?: number;
  title?: string;
  content?: string;
  sequenceNumber?: number;
  date?: Date;
  documents?: IDocument[];
  titleImage?: IDocument;
}

export class CmsNews implements ICmsNews {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public sequenceNumber?: number,
    public date?: Date,
    public documents?: IDocument[],
    public titleImage?: IDocument
  ) {}
}
