export interface IRiServiceStatus {
  id?: number;
  status?: string;
  description?: string;
  statusEn?: string;
  statusCyr?: string;
  descriptionEn?: string;
  descriptionCyr?: string;
}

export class RiServiceStatus implements IRiServiceStatus {
  constructor(
    public id?: number,
    public status?: string,
    public description?: string,
    public statusEn?: string,
    public statusCyr?: string,
    public descriptionEn?: string,
    public descriptionCyr?: string
  ) {}
}
