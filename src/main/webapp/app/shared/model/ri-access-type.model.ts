export interface IRiAccessType {
  id?: number;
  type?: string;
  typeEn?: string;
  typeCyr?: string;
}

export class RiAccessType implements IRiAccessType {
  constructor(public id?: number, public type?: string, public typeEn?: string, public typeCyr?: string) {}
}
