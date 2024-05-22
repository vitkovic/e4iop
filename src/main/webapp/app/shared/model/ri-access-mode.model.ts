export interface IRiAccessMode {
  id?: number;
  mode?: string;
  modeEn?: string;
  modeCyr?: string;
}

export class RiAccessMode implements IRiAccessMode {
  constructor(public id?: number, public mode?: string, public modeEn?: string, public modeCyr?: string) {}
}
