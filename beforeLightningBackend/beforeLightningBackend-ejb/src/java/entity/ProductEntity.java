/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Entity
public class ProductEntity extends ProductTypeEntity implements Serializable {
    @Column(nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String productName;
    
    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(min = 7, max = 7)
    private String skuCode;
    
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String description;
    
    @Column(length = 1000)
    @Size(max = 1000)
    private String productOverview;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2) 
    private BigDecimal basePrice;
    
    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Max(5)
    private Integer productRating;
    
    @ManyToMany(mappedBy = "productEntities",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<PartEntity> parts;

    public ProductEntity() {
        this.parts = new ArrayList<>();
    }

    public ProductEntity(String productName, String skuCode, String description, String productOverview, BigDecimal basePrice, Integer productRating) {
        this();
        this.productName = productName;
        this.skuCode = skuCode;
        this.description = description;
        this.productOverview = productOverview;
        this.basePrice = basePrice;
        this.productRating = productRating;
    }
    
    

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductOverview() {
        return productOverview;
    }

    public void setProductOverview(String productOverview) {
        this.productOverview = productOverview;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public List<PartEntity> getParts() {
        return parts;
    }

    public void setParts(List<PartEntity> parts) {
        this.parts = parts;
    }
    
    

    
    
}
