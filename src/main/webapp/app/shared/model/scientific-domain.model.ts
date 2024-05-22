import { IScientificSubdomain } from '@/shared/model/scientific-subdomain.model';

export interface IScientificDomain {
  id?: number;
  domain?: string;
  domainEn?: string;
  domainCyr?: string;
  subdomains?: IScientificSubdomain[];
}

export class ScientificDomain implements IScientificDomain {
  constructor(
    public id?: number,
    public domain?: string,
    public domainEn?: string,
    public domainCyr?: string,
    public subdomains?: IScientificSubdomain[]
  ) {}
}
