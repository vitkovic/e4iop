export interface IAnswerToOffer {
  id?: number;
  answer?: string;
  answerEn?: string;
  answerCyr?: string;
}

export class AnswerToOffer implements IAnswerToOffer {
  constructor(public id?: number, public answer?: string, public answerEn?: string, public answerCyr?: string) {}
}
