export interface IRiStatus {
  id?: number;
  status?: string;
  meaning?: string;
  statusEn?: string;
  statusCyr?: string;
  meaningEn?: string;
  meaningCyr?: string;
}

export class RiStatus implements IRiStatus {
  constructor(
    public id?: number,
    public status?: string,
    public meaning?: string,
    public statusEn?: string,
    public statusCyr?: string,
    public meaningEn?: string,
    public meaningCyr?: string
  ) {}
}
