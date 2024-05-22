export interface IServiceSubtype {
  id?: number;
  subtype?: string;
  subtypeEn?: string;
  subtypeCyr?: string;
}

export class ServiceSubtype implements IServiceSubtype {
  constructor(public id?: number, public subtype?: string, public subtypeEn?: string, public subtypeCyr?: string) {}
}
