export interface IPuoLegalStatus {
  id?: number;
  value?: string;
  description?: string;
  descriptionEn?: string;
  descriptionCyr?: string;
}

export class PuoLegalStatus implements IPuoLegalStatus {
  constructor(
    public id?: number,
    public value?: string,
    public description?: string,
    public descriptionEn?: string,
    public descriptionCyr?: string
  ) {}
}
