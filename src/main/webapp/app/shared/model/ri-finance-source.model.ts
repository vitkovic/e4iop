export interface IRiFinanceSource {
  id?: number;
  sourceSr?: string;
  sourceSrc?: string;
  sourceEn?: string;
}

export class RiFinanceSource implements IRiFinanceSource {
  constructor(public id?: number, public sourceSr?: string, public sourceSrc?: string, public sourceEn?: string) {}
}
