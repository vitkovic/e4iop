export interface IServiceType {
  id?: number;
  type?: string;
  typeEn?: string;
  typeCyr?: string;
}

export class ServiceType implements IServiceType {
  constructor(public id?: number, public type?: string, public typeEn?: string, public typeCyr?: string) {}
}
