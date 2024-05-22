package e4i.web.rest.dto;

public class InfrastructureProposalsLikesDTO {
	
	private Long portalUserId;
	private Long likes;
	private Long dislikes;
	
	public InfrastructureProposalsLikesDTO(Long portalUserId, Long likes, Long dislikes) {
		super();
		this.portalUserId = portalUserId;
		this.likes = likes;
		this.dislikes = dislikes;
	}
	public InfrastructureProposalsLikesDTO(Long likes, Long dislikes) {
		super();
		this.likes = likes;
		this.dislikes = dislikes;
	}
	public InfrastructureProposalsLikesDTO() {
		super();
	}
	public Long getPortalUserId() {
		return portalUserId;
	}
	public void setPortalUserId(Long portalUserId) {
		this.portalUserId = portalUserId;
	}
	public Long getLikes() {
		return likes;
	}
	public void setLikes(Long likes) {
		this.likes = likes;
	}
	public Long getDislikes() {
		return dislikes;
	}
	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}
	

}
