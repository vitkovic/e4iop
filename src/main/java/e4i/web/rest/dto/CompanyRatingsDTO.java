package e4i.web.rest.dto;

public class CompanyRatingsDTO {

	private String companyName;
	private Long companyId;
	
	private Double averageRating;
	private Double averageRatingAdvertiser;
	private Double averageRatingRequester;
	
	private Long totalRatings;
	private Long totalRatingsAdvertiser;
	private Long totalRatingsRequester;
	
	private Long totalRatings1;
	private Long totalRatings1Advertiser;
	private Long totalRatings1Requester;
	
	private Long totalRatings2;
	private Long totalRatings2Advertiser;
	private Long totalRatings2Requester;
	
	private Long totalRatings3;
	private Long totalRatings3Advertiser;
	private Long totalRatings3Requester;
	
	private Long totalRatings4;
	private Long totalRatings4Advertiser;
	private Long totalRatings4Requester;
	
	private Double percentageRating1;
	private Double percentageRating1Advertiser;
	private Double percentageRating1Requester;
	
	private Double percentageRating2;
	private Double percentageRating2Advertiser;
	private Double percentageRating2Requester;
	
	private Double percentageRating3;
	private Double percentageRating3Advertiser;
	private Double percentageRating3Requester;
	
	private Double percentageRating4;
	private Double percentageRating4Advertiser;
	private Double percentageRating4Requester;
	
	public CompanyRatingsDTO() {
	}

	public CompanyRatingsDTO(
			String companyName, Long companyId,
			Long totalRatings, Long totalRatingsAdvertiser, Long totalRatingsRequester,
			Double averageRating, Double averageRatingAdvertiser, Double averageRatingRequester,
			Long totalRatings1, Long totalRatings1Advertiser, Long totalRatings1Requester,
			Long totalRatings2, Long totalRatings2Advertiser, Long totalRatings2Requester,
			Long totalRatings3, Long totalRatings3Advertiser, Long totalRatings3Requester,
			Long totalRatings4, Long totalRatings4Advertiser, Long totalRatings4Requester,
			Double percentageRating1, Double percentageRating1Advertiser, Double percentageRating1Requester,
			Double percentageRating2, Double percentageRating2Advertiser, Double percentageRating2Requester,
			Double percentageRating3, Double percentageRating3Advertiser, Double percentageRating3Requester,
			Double percentageRating4, Double percentageRating4Advertiser, Double percentageRating4Requester
			) {
		super();
		this.companyName = companyName;
		this.companyId = companyId;
		this.averageRating = averageRating;
		this.averageRatingAdvertiser = averageRatingAdvertiser;
		this.averageRatingRequester = averageRatingRequester;
		this.totalRatings = totalRatings;
		this.totalRatingsAdvertiser = totalRatingsAdvertiser;
		this.totalRatingsRequester = totalRatingsRequester;
		this.totalRatings1 = totalRatings1;
		this.totalRatings1Advertiser = totalRatings1Advertiser;
		this.totalRatings1Requester = totalRatings1Requester;
		this.totalRatings2 = totalRatings2;
		this.totalRatings2Advertiser = totalRatings2Advertiser;
		this.totalRatings2Requester = totalRatings2Requester;
		this.totalRatings3 = totalRatings3;
		this.totalRatings3Advertiser = totalRatings3Advertiser;
		this.totalRatings3Requester = totalRatings3Requester;
		this.totalRatings4 = totalRatings4;
		this.totalRatings4Advertiser = totalRatings4Advertiser;
		this.totalRatings4Requester = totalRatings4Requester;
		this.percentageRating1 = percentageRating1;
		this.percentageRating1Advertiser = percentageRating1Advertiser;
		this.percentageRating1Requester = percentageRating1Requester;
		this.percentageRating2 = percentageRating2;
		this.percentageRating2Advertiser = percentageRating2Advertiser;
		this.percentageRating2Requester = percentageRating2Requester;
		this.percentageRating3 = percentageRating3;
		this.percentageRating3Advertiser = percentageRating3Advertiser;
		this.percentageRating3Requester = percentageRating3Requester;
		this.percentageRating4 = percentageRating4;
		this.percentageRating4Advertiser = percentageRating4Advertiser;
		this.percentageRating4Requester = percentageRating4Requester;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Double getAverageRatingAdvertiser() {
		return averageRatingAdvertiser;
	}

	public void setAverageRatingAdvertiser(Double averageRatingAdvertiser) {
		this.averageRatingAdvertiser = averageRatingAdvertiser;
	}

	public Double getAverageRatingRequester() {
		return averageRatingRequester;
	}

	public void setAverageRatingRequester(Double averageRatingRequester) {
		this.averageRatingRequester = averageRatingRequester;
	}

	public Long getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(Long totalRatings) {
		this.totalRatings = totalRatings;
	}

	public Long getTotalRatingsAdvertiser() {
		return totalRatingsAdvertiser;
	}

	public void setTotalRatingsAdvertiser(Long totalRatingsAdvertiser) {
		this.totalRatingsAdvertiser = totalRatingsAdvertiser;
	}

	public Long getTotalRatingsRequester() {
		return totalRatingsRequester;
	}

	public void setTotalRatingsRequester(Long totalRatingsRequester) {
		this.totalRatingsRequester = totalRatingsRequester;
	}

	public Long getTotalRatings1() {
		return totalRatings1;
	}

	public void setTotalRatings1(Long totalRatings1) {
		this.totalRatings1 = totalRatings1;
	}

	public Long getTotalRatings1Advertiser() {
		return totalRatings1Advertiser;
	}

	public void setTotalRatings1Advertiser(Long totalRatings1Advertiser) {
		this.totalRatings1Advertiser = totalRatings1Advertiser;
	}

	public Long getTotalRatings1Requester() {
		return totalRatings1Requester;
	}

	public void setTotalRatings1Requester(Long totalRatings1Requester) {
		this.totalRatings1Requester = totalRatings1Requester;
	}

	public Long getTotalRatings2() {
		return totalRatings2;
	}

	public void setTotalRatings2(Long totalRatings2) {
		this.totalRatings2 = totalRatings2;
	}

	public Long getTotalRatings2Advertiser() {
		return totalRatings2Advertiser;
	}

	public void setTotalRatings2Advertiser(Long totalRatings2Advertiser) {
		this.totalRatings2Advertiser = totalRatings2Advertiser;
	}

	public Long getTotalRatings2Requester() {
		return totalRatings2Requester;
	}

	public void setTotalRatings2Requester(Long totalRatings2Requester) {
		this.totalRatings2Requester = totalRatings2Requester;
	}

	public Long getTotalRatings3() {
		return totalRatings3;
	}

	public void setTotalRatings3(Long totalRatings3) {
		this.totalRatings3 = totalRatings3;
	}

	public Long getTotalRatings3Advertiser() {
		return totalRatings3Advertiser;
	}

	public void setTotalRatings3Advertiser(Long totalRatings3Advertiser) {
		this.totalRatings3Advertiser = totalRatings3Advertiser;
	}

	public Long getTotalRatings3Requester() {
		return totalRatings3Requester;
	}

	public void setTotalRatings3Requester(Long totalRatings3Requester) {
		this.totalRatings3Requester = totalRatings3Requester;
	}

	public Long getTotalRatings4() {
		return totalRatings4;
	}

	public void setTotalRatings4(Long totalRatings4) {
		this.totalRatings4 = totalRatings4;
	}

	public Long getTotalRatings4Advertiser() {
		return totalRatings4Advertiser;
	}

	public void setTotalRatings4Advertiser(Long totalRatings4Advertiser) {
		this.totalRatings4Advertiser = totalRatings4Advertiser;
	}

	public Long getTotalRatings4Requester() {
		return totalRatings4Requester;
	}

	public void setTotalRatings4Requester(Long totalRatings4Requester) {
		this.totalRatings4Requester = totalRatings4Requester;
	}

	public Double getPercentageRating1() {
		return percentageRating1;
	}

	public void setPercentageRating1(Double percentageRating1) {
		this.percentageRating1 = percentageRating1;
	}

	public Double getPercentageRating1Advertiser() {
		return percentageRating1Advertiser;
	}

	public void setPercentageRating1Advertiser(Double percentageRating1Advertiser) {
		this.percentageRating1Advertiser = percentageRating1Advertiser;
	}

	public Double getPercentageRating1Requester() {
		return percentageRating1Requester;
	}

	public void setPercentageRating1Requester(Double percentageRating1Requester) {
		this.percentageRating1Requester = percentageRating1Requester;
	}

	public Double getPercentageRating2() {
		return percentageRating2;
	}

	public void setPercentageRating2(Double percentageRating2) {
		this.percentageRating2 = percentageRating2;
	}

	public Double getPercentageRating2Advertiser() {
		return percentageRating2Advertiser;
	}

	public void setPercentageRating2Advertiser(Double percentageRating2Advertiser) {
		this.percentageRating2Advertiser = percentageRating2Advertiser;
	}

	public Double getPercentageRating2Requester() {
		return percentageRating2Requester;
	}

	public void setPercentageRating2Requester(Double percentageRating2Requester) {
		this.percentageRating2Requester = percentageRating2Requester;
	}

	public Double getPercentageRating3() {
		return percentageRating3;
	}

	public void setPercentageRating3(Double percentageRating3) {
		this.percentageRating3 = percentageRating3;
	}

	public Double getPercentageRating3Advertiser() {
		return percentageRating3Advertiser;
	}

	public void setPercentageRating3Advertiser(Double percentageRating3Advertiser) {
		this.percentageRating3Advertiser = percentageRating3Advertiser;
	}

	public Double getPercentageRating3Requester() {
		return percentageRating3Requester;
	}

	public void setPercentageRating3Requester(Double percentageRating3Requester) {
		this.percentageRating3Requester = percentageRating3Requester;
	}

	public Double getPercentageRating4() {
		return percentageRating4;
	}

	public void setPercentageRating4(Double percentageRating4) {
		this.percentageRating4 = percentageRating4;
	}

	public Double getPercentageRating4Advertiser() {
		return percentageRating4Advertiser;
	}

	public void setPercentageRating4Advertiser(Double percentageRating4Advertiser) {
		this.percentageRating4Advertiser = percentageRating4Advertiser;
	}

	public Double getPercentageRating4Requester() {
		return percentageRating4Requester;
	}

	public void setPercentageRating4Requester(Double percentageRating4Requester) {
		this.percentageRating4Requester = percentageRating4Requester;
	}
}
