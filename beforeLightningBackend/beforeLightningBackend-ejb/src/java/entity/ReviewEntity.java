/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class ReviewEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewEntityId;
    @Column(nullable = false)
    @NotNull
    private String customerUsername;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    @Column(nullable = false)
    @NotNull
    private String description;

    public ReviewEntity() {
    }

    public ReviewEntity(String customerUsername, Integer rating, String description) {
        this();
        this.customerUsername = customerUsername;
        this.rating = rating;
        this.description = description;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewEntityId != null ? reviewEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the reviewEntityId fields are not set
        if (!(object instanceof ReviewEntity)) {
            return false;
        }
        ReviewEntity other = (ReviewEntity) object;
        if ((this.reviewEntityId == null && other.reviewEntityId != null) || (this.reviewEntityId != null && !this.reviewEntityId.equals(other.reviewEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReviewEntity[ reviewEntityId=" + reviewEntityId + " ]";
    }

    /**
     * @return the customerUsername
     */
    public String getCustomerUsername() {
        return customerUsername;
    }

    /**
     * @param customerUsername the customerUsername to set
     */
    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    /**
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getReviewEntityId() {
        return reviewEntityId;
    }

    public void setReviewEntityId(Long reviewEntityId) {
        this.reviewEntityId = reviewEntityId;
    }

}
