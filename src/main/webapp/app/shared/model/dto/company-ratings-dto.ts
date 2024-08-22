export interface ICompanyRatingsDTO {
  companyName?: string;
  companyId?: number;
  averageRating?: number;
  averageRatingAdvertiser?: number;
  averageRatingRequester?: number;
  totalRatings?: number;
  totalRatingsAdvertiser?: number;
  totalRatingsRequester?: number;
  totalRatings1?: number;
  totalRatings1Advertiser?: number;
  totalRatings1Requester?: number;
  totalRatings2?: number;
  totalRatings2Advertiser?: number;
  totalRatings2Requester?: number;
  totalRatings3?: number;
  totalRatings3Advertiser?: number;
  totalRatings3Requester?: number;
  totalRatings4?: number;
  totalRatings4Advertiser?: number;
  totalRatings4Requester?: number;
  percentageRating1?: number;
  percentageRating1Advertiser?: number;
  percentageRating1Requester?: number;
  percentageRating2?: number;
  percentageRating2Advertiser?: number;
  percentageRating2Requester?: number;
  percentageRating3?: number;
  percentageRating3Advertiser?: number;
  percentageRating3Requester?: number;
  percentageRating4?: number;
  percentageRating4Advertiser?: number;
  percentageRating4Requester?: number;
}

export class CompanyRatingsDTO implements ICompanyRatingsDTO {
  constructor(
    public companyName?: string,
    public companyId?: number,
    public averageRating?: number,
    public averageRatingAdvertiser?: number,
    public averageRatingRequester?: number,
    public totalRatings?: number,
    public totalRatingsAdvertiser?: number,
    public totalRatingsRequester?: number,
    public totalRatings1?: number,
    public totalRatings1Advertiser?: number,
    public totalRatings1Requester?: number,
    public totalRatings2?: number,
    public totalRatings2Advertiser?: number,
    public totalRatings2Requester?: number,
    public totalRatings3?: number,
    public totalRatings3Advertiser?: number,
    public totalRatings3Requester?: number,
    public totalRatings4?: number,
    public totalRatings4Advertiser?: number,
    public totalRatings4Requester?: number,
    public percentageRating1?: number,
    public percentageRating1Advertiser?: number,
    public percentageRating1Requester?: number,
    public percentageRating2?: number,
    public percentageRating2Advertiser?: number,
    public percentageRating2Requester?: number,
    public percentageRating3?: number,
    public percentageRating3Advertiser?: number,
    public percentageRating3Requester?: number,
    public percentageRating4?: number,
    public percentageRating4Advertiser?: number,
    public percentageRating4Requester?: number
  ) {}
}
