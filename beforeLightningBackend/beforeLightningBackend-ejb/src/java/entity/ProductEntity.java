/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productEntityId;

    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String productName;
    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(min = 7, max = 7)
    private String skuCode;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(5)
    private Double productRating;
    @Column(nullable = false)
    @NotNull
    private String description;
    @Column(nullable = false)
    @NotNull
    private String productOverview;
    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String imageLink;
    @NotNull
    private Boolean isDisabled;

    @ManyToMany(mappedBy = "productEntities")
    private List<PartEntity> partEntities;

    public ProductEntity() {
        this.partEntities = new ArrayList<>();
        this.isDisabled = false;
        this.imageLink = "";
    }

    public ProductEntity(String productName, String skuCode, Double productRating, String description, String productOverview) {
        this();
        this.productName = productName;
        this.skuCode = skuCode;
        this.productRating = productRating;
        this.description = description;
        this.productOverview = productOverview;
    }

    public Long getProductEntityId() {
        return productEntityId;
    }

    public void setProductEntityId(Long productEntityId) {
        this.productEntityId = productEntityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productEntityId != null ? productEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productEntityId fields are not set
        if (!(object instanceof ProductEntity)) {
            return false;
        }
        ProductEntity other = (ProductEntity) object;
        if ((this.productEntityId == null && other.productEntityId != null) || (this.productEntityId != null && !this.productEntityId.equals(other.productEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductEntity[ productEntityId=" + productEntityId + " ]";
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the skuCode
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    /**
     * @return the productOverview
     */
    public String getProductOverview() {
        return productOverview;
    }

    /**
     * @param productOverview the productOverview to set
     */
    public void setProductOverview(String productOverview) {
        this.productOverview = productOverview;
    }

    /**
     * @return the productRating
     */
    public Double getProductRating() {
        return productRating;
    }

    /**
     * @param productRating the productRating to set
     */
    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    /**
     * @return the imageLink
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * @param imageLink the imageLink to set
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<PartEntity> getPartEntities() {
        return partEntities;
    }

    public void setPartEntities(List<PartEntity> partEntities) {
        this.partEntities = partEntities;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

}
