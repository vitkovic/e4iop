export interface IRiCategory {
  id?: number;
  category?: string;
  categoryEn?: string;
  categoryCyr?: string;
}

export class RiCategory implements IRiCategory {
  constructor(public id?: number, public category?: string, public categoryEn?: string, public categoryCyr?: string) {}
}
