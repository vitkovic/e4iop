export enum RiFinanceSourceOptions {
  MINISTRY_OF_SCIENCE = 'Ministry responsible for science',
  INOVATION_FUND = 'Innovation Activity Fund',
  EU_FUNDS = 'EU funds',
  SCIENCE_FUND = 'Science fund',
  OTHER_FUNDS = 'Other funds',
  DONATIONS = 'Donations',
  SELF_FINANCING = 'Self-financing',
  OTHER = 'Other',
}

export interface IRiFinanceSource {
  id?: number;
  sourceSr?: string;
  sourceSrc?: string;
  sourceEn?: string;
}

export class RiFinanceSource implements IRiFinanceSource {
  constructor(public id?: number, public sourceSr?: string, public sourceSrc?: string, public sourceEn?: string) {}
}
