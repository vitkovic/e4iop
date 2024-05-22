export interface IServiceAvailability {
  id?: number;
  availability?: string;
  meaning?: string;
  availabilityEn?: string;
  availabilityCyr?: string;
  meaningEn?: string;
  meaningCyr?: string;
}

export class ServiceAvailability implements IServiceAvailability {
  constructor(
    public id?: number,
    public availability?: string,
    public meaning?: string,
    public availabilityEn?: string,
    public availabilityCyr?: string,
    public meaningEn?: string,
    public meaningCyr?: string
  ) {}
}
