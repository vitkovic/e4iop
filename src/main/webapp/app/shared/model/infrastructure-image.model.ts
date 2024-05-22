import { IResearchInfrastructure } from '@/shared/model/research-infrastructure.model';

export interface IInfrastructureImage {
  id?: number;
  filename?: string;
  researchInfrastructure?: IResearchInfrastructure;
}

export class InfrastructureImage implements IInfrastructureImage {
  constructor(public id?: number, public filename?: string, public researchInfrastructure?: IResearchInfrastructure) {}
}
