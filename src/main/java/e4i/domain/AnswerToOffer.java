package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AnswerToOffer.
 */
@Entity
@Table(name = "answer_to_offer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnswerToOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Offer accepted, Offer denied, Offer should be discussed
     */
    @ApiModelProperty(value = "Offer accepted, Offer denied, Offer should be discussed")
    @Column(name = "answer")
    private String answer;
    
    @Column(name = "answer_en")
    private String answerEn;
    
    @Column(name = "answer_cyr")
    private String answerCyr;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public AnswerToOffer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getAnswerEn() {
		return answerEn;
	}

	public void setAnswerEn(String answerEn) {
		this.answerEn = answerEn;
	}

	public String getAnswerCyr() {
		return answerCyr;
	}

	public void setAnswerCyr(String answerCyr) {
		this.answerCyr = answerCyr;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerToOffer)) {
            return false;
        }
        return id != null && id.equals(((AnswerToOffer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerToOffer{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
