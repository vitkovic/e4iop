export interface IRipoActivityStatus {
  id?: number;
  status?: string;
  statusEn?: string;
  statusCyr?: string;
}

export class RipoActivityStatus implements IRipoActivityStatus {
  constructor(public id?: number, public status?: string, public statusEn?: string, public statusCyr?: string) {}
}
