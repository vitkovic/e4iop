export interface IRequestAnswer {
  id?: number;
  answer?: string;
  answerEn?: string;
  answerCyr?: string;
}

export class RequestAnswer implements IRequestAnswer {
  constructor(public id?: number, public answer?: string, public answerEn?: string, public answerCyr?: string) {}
}
